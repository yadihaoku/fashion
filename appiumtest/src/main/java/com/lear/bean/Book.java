package com.lear.bean;

/**
 * Created by YanYadi on 2017/12/30.
 */
public class Book {
    private int no;
    private String bookName;
    private String isbn;
    private String author;
    private int bookCount;

    //重载Book()函数,只传必须的参数，其他的默认值
    public Book(String bookName,String isbn){
        this(1,bookName,isbn,"郑玉秀",1);
    }
    //构造函数
    public Book(int no, String bName, String isbn, String author, int bCount){
        this.no=no;
        this.bookName = bName;
        this.isbn=isbn;
        this.author=author;
        this.bookCount = bCount;
    }

    public Book(){}

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }



}