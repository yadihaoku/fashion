package com.example.spider;

import com.example.spider.net.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by YanYadi on 2017/5/18.
 */
public class ShiJinChang {


    public static void main(String[] args) {

        final CountDownLatch countDownLatch = new CountDownLatch(GroupIds.IDS.length);
        final ContactManager contactManager = new ContactManager(countDownLatch);
        contactManager.start();

        OkHttpUtils.init("E:\\cache\\");
        for (String s : GroupIds.IDS) {
            OkHttpUtils.getHttpClient().newCall(makeRequest(s)).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    System.out.println("error");
                    countDownLatch.countDown();
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    contactManager.addUsers(parseToUser(response.body().string()));
                    countDownLatch.countDown();

                }
            });
        }
    }

    private static List<User> parseToUser(String json) {
        List<User> list = new ArrayList<>();
        System.out.println("parseJson" + json);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            if (jsonObject != null) {
                JSONArray userList = jsonObject.optJSONObject("data").optJSONArray("list");
                if (userList != null) {
                    final int len = userList.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject userJson = userList.optJSONObject(i);
                        User user = new User();
                        user.userName = userJson.optString("cst_name");
                        user.mobile = userJson.optString("mobile_tel");

                        list.add(user);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    private static Request makeRequest(String uid) {
        Request request = new Request.Builder().url(getReqUrl(uid))
                .headers(makeHeaders())
                .get().build();
        return request;
    }

    private static String getReqUrl(String uid) {
        //39d624b4-36ec-9290-fb7c-10679d5132c1
        return "http://ydxs.myscrm.cn/api/index.php?r=cst-assign/get-following-csts-by-sale&token=jhxziw1456295377&proj_id=39d6242e-5b08-d55f-2711-add8910b0676&user_id="+uid+"&page_index=1&page_size=2000&hideLoading=true";
    }

    private static Headers makeHeaders() {

        Headers headers = new Headers.Builder()
                .add("Referer", "https://ydxs.myscrm.cn/static/page/customer_assign_route/customer_assign_route.html?token=jhxziw1456295377&userid=39d624b4-36ec-9290-fb7c-10679d5132c1&projid=39d6242e-5b08-d55f-2711-add8910b0676&proj_id=39d6242e-5b08-d55f-2711-add8910b0676&user_type=3&role_mode=manager")
                .add("Cookie", "gr_user_id=1b578d4b-d131-45ea-a8e9-e8bb952456c0; jhxziw1456295377_openid=oktNauC8MuEG5_nwm9Kk278pteh8; PHPSESSID=8hgg863pmig07bbi36rh8u7c71; env_orgcode=ysadmin; Sales_jhxziw1456295377_mycms_identity=think%3A%7B%22s_userId%22%3A%2239d624b4-36ec-9290-fb7c-10679d5132c1%22%2C%22user_name%22%3A%22%25E5%25B8%25B8%25E8%2589%25B3%25E6%25A3%258B%22%2C%22user_code%22%3A%22%22%2C%22account%22%3A%22%22%2C%22mobile_tel%22%3A%2213937139811%22%2C%22password%22%3A%22964ff8ac2cd0d98a7745914099bba84b%22%2C%22token%22%3A%22jhxziw1456295377%22%2C%22user_type%22%3A%221%22%7D; last_env=g2; gr_session_id_a4f46f59c1ea0610=505345cb-415f-43e0-90f8-f8c4f4bc9172; gr_cs1_505345cb-415f-43e0-90f8-f8c4f4bc9172=userId%3A39d624b4-36ec-9290-fb7c-10679d5132c1")
                .build();

        return headers;
    }

    static class ContactManager extends Thread {
        private CountDownLatch downLatch;
        private List<User> allUsers;

        ContactManager(CountDownLatch downLatch) {
            this.downLatch = downLatch;
            allUsers = new LinkedList<>();
        }

        public void addUsers(List<User> users) {
            synchronized (this) {
                allUsers.addAll(users);
            }
        }

        @Override public void run() {
            try {
                // 等待下载任务完成
                downLatch.await();
                System.out.println(" file download success ");
                System.out.println(" contact size = " + allUsers.size());
                writeToFile();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void writeToFile() {
            PrintStream printStream = null;
            try {
                printStream = new PrintStream(new FileOutputStream("E:\\allUsers.txt", false), true);
                for (User user : allUsers) {
                    printStream.println(user.userName + "," + user.mobile);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (printStream != null)
                    printStream.close();
            }
        }
    }

    static class User {
        public String userName;
        public String mobile;
    }

    static class GroupIds {
        static String[] IDS = new String[]{"39d624b4-36ec-9290-fb7c-10679d5132c1", "39de9141-aa65-aa6e-0fb7-a37e178fda23", "39dd487b-d60a-36f0-5b8e-71fae564e671", "39de8c10-e1a7-b7e3-af7f-4a72774b451b", "39dd3413-1fad-b7ea-7acf-15d7f2dfc5ad", "39d624b4-9834-9715-bfca-08b834fb06ea", "39dd5c71-0945-d832-9761-fb98826a84fd", "39de72f7-254b-ff2c-8c57-cb5f0739b531", "39de8d0d-1b8f-5b2c-8b12-89f14e8b64c8", "39dd3997-1ae0-869e-0e08-b97fd9aa2517", "39d624b4-d47c-3064-e18d-70191b7f3aad", "39dd3413-d477-4eaf-cd4d-f473f840e224", "39ded090-925a-555b-6b61-f1f6a6d41ada", "39ddfb78-d688-44b4-55ac-08f8bd84e8dd", "39dd4344-37b9-0ec7-bfa8-f15e4a1a8f86", "39dab0c3-74b7-715a-399c-c4bd8e9de7e1", "39da575e-549f-dbc6-d019-86c15ffd408b", "39de5362-36ed-9773-b443-76b6039abfda", "39de72f8-1fd9-9fca-11ab-c3917c843ee4", "39dd4344-6e6e-3f52-c5ad-35ae8ff7aabb", "39dda584-a8d4-152f-8ab6-42ea3f35fbd5", "39ddaa81-3bd4-428a-80be-1844865cb8ee", "39d7e8c6-d43d-44c2-1aa8-c38e58516a80", "39dd94b3-4554-add9-4a65-d0a94b5ad197", "39daa4ba-2b00-5768-15be-5d5e717478e8", "39de8d0e-49a5-135d-5629-84b620e950fb", "39ded090-56bf-f382-ab1e-207e9fd21862", "39de8185-9609-ed86-2309-750c6f4bba3b", "39dd393b-8994-c794-040d-6322f00493b6", "39ddd7e4-e646-c5a7-75ac-86efa33266a6", "39de7894-fc7e-fd37-3848-317e9d991baf", "39de82d1-66cc-8a7d-7e45-bb8e697d6d19", "39ddb4cf-4887-e865-a906-8214f1e8e210", "39ddc910-5f17-20dc-8985-99955e5867b0", "39ddd7e4-854a-2016-23fe-c84c286adf87", "39d624b4-5444-538c-be38-e9b00bc0b8a9", "39de3973-a5a5-c501-8a10-bf41bea0e7e0", "39dd4344-a7e7-12c1-e310-e43be5d35d2f", "39dda584-36c2-4807-4b88-8614f67f7dc9", "39dd3412-8f5f-9013-4e6d-5c98e7d31a05", "39dda584-725d-ba43-c515-bda85413ff9a", "39dd5722-2ad5-a77c-0390-c3c065a115c2", "39dd4d6f-4cb4-70e6-dda5-d3caa741b9b8", "39dc2ce3-9439-2ea8-88ff-ffe50627da1e", "39de72f6-dba1-699e-a521-b37b7499f58a", "39dea576-0d35-d834-37d8-2374cef3ad7e", "39da575e-aef8-fc2a-fa85-f4e872f853e2", "39de7881-8815-d681-bfda-cdd00002c4d5", "39dab952-1b8f-c0a0-ba29-df55bd1e0741", "39dd4c8d-e66c-bf25-efe2-840005309ed0", "39daaf4e-65f5-250a-3459-bf8a7104e4ec", "39dd612c-619c-a397-6add-700c44a68f10", "39dda4c6-2472-77ea-d174-981e0ce9c4cf", "39ddb4cf-9280-6f1f-7853-4e36f15653c8", "39d624b5-3c12-2091-4ef3-022a323b08bb", "39de5361-9821-ba29-134c-8f541f1b2f65", "39d9ed4a-1945-e7ee-75cf-98c9eb03a08e", "39daaece-a9dd-9866-2bf2-acfb5ebc4631", "39dd81a8-e6eb-c6e0-9120-17b837282d63", "39de5361-e7c0-7e9b-efd7-5db9133085ec", "39d9ed49-eba0-e4cd-12d0-a8277128f711", "39de1137-4dad-cd19-c72d-e5b260835f6d", "39dd4c8d-a349-9133-446e-832e58f82cef", "39dacf6f-3984-5a11-5818-98cfe7ff820e", "39dd393c-05c9-f6ba-109e-98e01e3cd357", "39da9b4a-e193-a3f0-ddb9-1bb7f1fc73b0", "39dacf6f-94b1-c037-e87c-1528d5872c57", "39ddc809-fdc5-8aa2-5f59-9563539caca6", "39d86515-8d52-4a7d-8a22-273842c15cd3", "39de7881-3833-ec88-1b56-389adb1d11c7", "39dacf6f-5d0a-260e-232d-7b37fe79dd85", "39dd4343-f939-1fa4-468b-bb8046f3069b", "39da956f-3db7-a2af-085f-6f2fa3639822", "39de8739-0537-a864-8c3c-91b4b93afa87", "39d624b5-9fc3-2aca-8771-cfde2a3e6f3e", "39de0615-42be-676f-0666-e3913fe54586", "39dd5858-9ab5-900d-8882-fc87485f190e", "39dd2855-b512-97db-f67f-bc9e712832a3", "39dd9ed3-77e8-e6ff-8e7c-bcbf11bd7370", "39de8185-f455-97a3-8e5b-b676d963397d", "39da956d-b5ab-c4a4-45d5-dd3d4d429577", "39daaf4e-a92e-8e96-a64b-d4efd1595ba6", "39dd9eb5-9fea-72f2-beca-2aa30f42b5fb", "39dd5858-541f-0c2b-40bc-f01a18b00fe4", "39de8d0d-5579-02aa-8f08-764612bb2539", "39d624b5-147f-461f-9f1f-32252be4c209", "39ded090-d040-3d25-1315-cf4ad10acd36", "39dabff3-43a9-4255-0986-dbba6dce1ccc", "39daaf4e-3cc6-2c78-6b0b-95891cbba384", "39d96b9a-26a1-9292-5891-8a4f84b8ede7", "39de8d0e-860e-cd68-f75c-b86a4abf80b1", "39dd2867-8d1a-b786-6bd3-518baa01198b", "39dd81a9-37ff-413d-1cf3-1f7f723e3358", "39da2985-2795-7b4f-3da9-60c5493307b7", "39d624b6-4a26-2b30-3cbb-3948a0c1ceee", "39d624b6-e652-01bd-ae3e-d6b6f2777b74", "39d8b633-0ed3-e03d-ddc9-662b298b1001", "39d624b5-c2f8-1805-03c3-0b1425ce338b", "39d97f7f-f3ce-6fae-aa50-de013f5a6575", "39d624b6-cae4-287f-217e-2564d62d7aa7", "39d624b7-66b2-94a5-be52-40cb940f934e", "39d8b6ae-33fc-488f-c2ef-2b832efbccdb", "39d6bf46-f0d9-b53a-8af5-eded88b776d1", "39d624b5-e3ca-d1af-bd70-f25f8d179550", "39da4cf5-d08e-f6c1-be50-5c897d3852f8", "39d624b8-ebe2-dbae-2f02-24188d9dff4c", "39da2986-d27d-5443-c9ab-bcfec053cc1f", "39d624b8-5bde-72b1-7eb6-d8320d21d5e3", "39d97f80-29d7-149e-a809-550f982b71cc", "39d9a510-884b-3cb8-9d03-5a8ab980cd38", "39d624b6-27c1-b3a1-fcc3-fa798a9b6b1f", "39d624b6-6a6c-db79-7f43-a04701c6a3e9", "39d624b6-87c6-69a3-1c57-90f3753ed792", "39dbea55-530a-750c-b0e6-5039155dac16", "39da7314-3bea-1934-3906-a6ff416d8158", "39d624b7-2736-35b4-c6f9-d78b5d4a06c6", "39da7314-7725-bf1d-7a78-9529a3940832", "39d9cc2a-926d-72fa-4cf4-4737b0e2eb43", "39da2985-b62e-3c63-4805-148779f802f8", "39dc7a1c-37c7-794b-5f4c-b43fe8f98ae4", "39da2987-a812-64cf-291d-08c44695f694", "39da38af-57f6-9791-ecbb-ba0461b49696", "39d624b8-39a2-b43b-783f-c0d0dbe6d29c", "39da764a-59ad-e661-6f5c-22cfb2573ca2", "39d8c21a-3736-aa58-6c3a-11f01485407d", "39da7314-0d09-780f-d723-759d21c0f346", "39da1a0b-2c39-3df1-bbf0-2f078eb808b2", "39dc9e52-3c2f-29ae-5f7a-3f2b1bd8b802", "39d624b9-0fc4-0db3-cd39-ecd9223592f9", "39d624b6-02b9-21e8-2700-c269db7c4a5f", "39da6dd2-e66f-4d47-a339-32aaf14e2d55", "39da4cf5-76c3-115c-c2a3-9d7406a626f1", "39da764a-189c-a30b-91d6-e601b0751c8e", "39d68612-1f24-0570-c52d-221efb06177a", "39da4cf5-261d-2fab-c035-3323d2533ea3", "39da7649-aaa1-a1b8-96cf-420fa48b58a2", "39d950f5-0bd8-a9ce-edab-62a749a0ec74", "39d8faa3-faa5-ba76-1855-17efe36eefc9", "39da810c-64ba-b7dc-279e-01a441464cb4", "39da2987-74aa-3650-b552-01007a7e43a9", "39da6dd3-23ac-3759-16dd-079d1c478c4b", "39da6dec-60f9-7907-c089-7f964a5c38d1", "39da19fb-7fed-d5b4-05ea-bc1d76953f51", "39d92de8-6a15-0c86-ca8a-6f564492b929", "39d624b7-83b7-3a44-10c5-8fed4d4ba9f5", "39d624b7-07be-bbc0-0809-9394fd92c3a1", "39da3852-3444-74d7-8ab5-a2ec85511f0d"};
    }
}
