package cn.yyd.kankanshu.dao.impl;

import cn.yyd.kankanshu.dao.BookListInterface;

/**
 * Created by YanYadi on 2017/4/18.
 */

public class Qing100BookList implements BookListInterface {
    @Override public String getBookListUrl() {
        return "清大100";
    }
}
