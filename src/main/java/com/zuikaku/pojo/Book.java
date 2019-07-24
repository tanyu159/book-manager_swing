package com.zuikaku.pojo;

import java.util.Date;
import java.util.List;

public class Book {
    private int id;
    private String name;
    private String isbn;
    private double price;
    private int lended_count;//这一本书被借阅的次数
    private boolean lended;//标志位是否被借出
    private String author;//作者
    private String publisher;//发行商
    private Date pub_date;
    //private List<User> userList;//这本书被哪些人借过（暂无该需求）


    public Book(int id, String name, String isbn, double price, int lended_count, boolean lended, String author, String publisher, Date pub_date) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.price = price;
        this.lended_count = lended_count;
        this.lended = lended;
        this.author = author;
        this.publisher = publisher;
        this.pub_date = pub_date;
    }

    public Book() {
    }

    public Book(String name, String isbn, double price, String author, String publisher, Date pub_date) {
        this.name = name;
        this.isbn = isbn;
        this.price = price;
        this.author = author;
        this.publisher = publisher;
        this.pub_date = pub_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getLended_count() {
        return lended_count;
    }

    public void setLended_count(int lended_count) {
        this.lended_count = lended_count;
    }

    public boolean isLended() {
        return lended;
    }

    public void setLended(boolean lended) {
        this.lended = lended;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPub_date() {
        return pub_date;
    }

    public void setPub_date(Date pub_date) {
        this.pub_date = pub_date;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isbn='" + isbn + '\'' +
                ", price=" + price +
                ", lended_count=" + lended_count +
                ", lended=" + lended +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", pub_date=" + pub_date +
                '}';
    }
}
