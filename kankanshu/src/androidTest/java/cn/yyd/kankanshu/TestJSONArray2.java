package cn.yyd.kankanshu;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YanYadi on 2017/4/12.
 */

public class TestJSONArray2 {

    @Test
    public void runJsonArray(){
        TestObj testObj = new TestObj();
        testObj.age = 10;
        testObj.name = "123";
        TestObj testObj2 = new TestObj();
        testObj.age = 101;
        testObj.name = "112123";

        List list = new ArrayList();
        list.add(testObj);
        list.add(testObj2);

        JSONArray jsonArray = new JSONArray(list);
        System.out.println(jsonArray.toString());
        System.out.println(jsonArray.length());

        System.out.println(jsonArray.toString());

        try {

            JSONArray jsonArray1 = new JSONArray("[{a:10},{b:100}]");
            System.out.println(jsonArray1.length());
            System.out.println(jsonArray1.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(jsonArray);
    }

    static class TestObj{
        private int age;
        private String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
