package com.example.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by YanYadi on 2017/4/14.
 */

public class PeopleNominal {

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

    public static final String REFERER = "http://www.7nmg.com/play/24892/1/1.html";

    public static final String ENCRYPTED = "TVRRNU1qRTBPVFk9WTI5dWRHVnVkQ3N4TkRreU1UUTVOa0JqWVc5dWFXMWhZbWs9";

    public static final String FILE_PATH = "people.txt";

    public static void main(String[] args) {
        List<String> urls = getAllPartAddress();
        final int partStart = 0;
        final int partEnd = urls.size();

        ExecutorService executor = Executors.newFixedThreadPool(10);
        VideoManager videoManager = new VideoManager(partEnd);
        CountDownLatch downloadCount = new CountDownLatch(partEnd);

        for (int i = partStart; i < partEnd; i++) {
            executor.execute(new Work(urls.get(i), i, videoManager, downloadCount));
        }

        executor.shutdown();
        new WatchThread(downloadCount, videoManager).start();
    }

    static List<String> getAllPartAddress() {
        List<String> urls = new ArrayList<>();
        Document document = getDocByUrl(REFERER);
        if (document != null) {
            Elements parts = document.getElementsByClass("dslist-group");
            if (parts.size() > 0) {
                Elements lis = parts.get(0).children();
                for (Element li : lis) {
                    urls.add(li.child(0).attr("abs:href"));
                }
            }
        }
        return urls;
    }

    /**
     * 根据 url 获取 Document
     *
     * @param url
     * @return
     */
    static Document getDocByUrl(String url) {
        try {
            Document document = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    //如果获取不到数据，需要添加 referer
                    .header("Referer", REFERER)
                    .timeout(30_000)
                    .get();
            return document;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Element getHasMovieScript(Document document) {
        return getHasMovieScript(document, ".*iframe.*");
    }

    private static Element getHasMovieScript(Document document, String featureRegExp) {
        Elements eles = document.getElementsByTag("script");
        for (Element ele : eles) {
            String text = ele.data();
            if (text != null) {
                if (Pattern.compile(featureRegExp, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE).matcher(text).find()) {
                    return ele;
                }
            }
        }
        return null;
    }

    static class WatchThread extends Thread {

        CountDownLatch downLatch;
        VideoManager videoManager;

        WatchThread(CountDownLatch latch, VideoManager manager) {
            this.downLatch = latch;
            this.videoManager = manager;
        }

        @Override public void run() {
            try {
                downLatch.await();
                System.out.println("---- 下载 完成 ----");
                videoManager.viewUrls();
                //写入文件
                videoManager.writeToFile(FILE_PATH);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Work implements Runnable {

        String url;
        int part;
        VideoManager videoManager;
        CountDownLatch downLatch;

        Work(String url, int pos, VideoManager manager, CountDownLatch downLatch) {
            this.url = url;
            this.part = pos;
            videoManager = manager;
            this.downLatch = downLatch;
        }

        @Override public void run() {
            // 获取包含 script 脚本的 iframe 标签
            try {
                Document document = getDocByUrl(url);
                if (document != null) {
                    Element scriptElement = getHasMovieScript(document);
                    if (scriptElement != null) {
                        String videoUrl = buildVideoUrl(scriptElement);
                        if (videoUrl != null) {
                            //分析 mp4 视频的真正地址
                            String movieInfo = filterRealVideoUrl(videoUrl);
                            String title = getMovieTitle(document);
                            if (movieInfo != null)
                                videoManager.setVideoUrls(part, movieInfo, title);
                            else
                                System.out.println(String.format("%s \n not found mp4 ", videoUrl));
                        }
                    } else {
                        System.out.println("未找到包含 iframe 的script 标签  url = " + url);
                    }
                } else {
                    System.out.println(" 获取 Document 失败  -- url = " + url);
                }
            } finally {
                this.downLatch.countDown();
            }
        }

        private String filterRealVideoUrl(String thirdSite) {
            Document videoSite = getDocByUrl(thirdSite);
            if (videoSite != null) {
                //分析 mp4 地址
                String pattern = "video=";
                Element script = getHasMovieScript(videoSite, pattern);
                if (script != null) {
                    String data = script.data();
                    Pattern vpattern = Pattern.compile("video=[^\\w]+([^']+)", Pattern.MULTILINE);
                    Matcher matcher = vpattern.matcher(data);
                    if (matcher.find() && matcher.groupCount() > 0) {
                        return matcher.group(1);
                    }
                } else {
                    System.out.println("未找到包含 mp4 视频的 <script>");
                }
            }
            return null;
        }

        private String getMovieTitle(Document document) {
            Elements elements = document.getElementsByClass("movie-title");
            if (elements.size() > 0) return elements.get(0).text();
            return null;
        }

        private String buildVideoUrl(Element element) {
            String text = element.data();
            StringBuilder videoUrl = new StringBuilder();

            int uStart = text.indexOf("src=\"") + 5;
            int uEnd = text.lastIndexOf("'+enc");
            videoUrl.append(text.substring(uStart, uEnd)).append(ENCRYPTED);
            return videoUrl.toString();
        }
    }

    /**
     * 抓取到 Video Url 存储类，保证每个视频的顺序
     */
    static class VideoManager {
        private Movie[] videos;

        VideoManager(int count) {
            videos = new Movie[count];
        }

        public void setVideoUrls(int pos, String url, String title) {
            if (pos < 0 || pos >= videos.length) {
                throw new IndexOutOfBoundsException();
            }
            videos[pos] = new Movie(pos, url, title);
        }

        public void viewUrls() {
            for (int i = 0; i < videos.length; i++) {
                Movie movie = videos[i];
                System.out.println(String.format("%s %s", movie.title, movie.url));
            }
        }

        public void writeToFile(String filePath) {
            File file = new File(filePath);
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file, false);
                for (int i = 0; i < videos.length; i++) {
                    Movie movie = videos[i];
                    fileWriter.write(movie.title);
                    fileWriter.write("\r\n");
                    fileWriter.write(movie.url);
                    fileWriter.write("\r\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileWriter != null)
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    static class Movie {
        public int pos;
        public String url;
        public String title;

        public Movie(int pos, String url, String title) {
            this.pos = pos;
            this.url = url;
            this.title = title;
        }
    }


}
