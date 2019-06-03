package cn.yyd.fashiontech;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity {

    static final String video_url = "http://static.zhenbaobaiku.com/videos/goods/jiangzhenxiang1.mp4";
    @BindView(R.id.videoView) VideoView mVideo;
    @BindView(R.id.tv_loading) TextView mTvLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        mVideo.setMediaController(new MediaController(this));
        mVideo.setVideoURI (Uri.parse(video_url));
        mVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override public void onPrepared(MediaPlayer mp) {
                mTvLoading.setVisibility(View.GONE);
                System.out.println("prepard.......");
            }
        });
        mVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override public void onCompletion(MediaPlayer mp) {
                mTvLoading.setVisibility(View.VISIBLE);
            }
        });
        mVideo.start();
    }
    private void resetCallback(){
        mVideo.setOnCompletionListener(null);
        mVideo.setOnPreparedListener(null);
    }
}
