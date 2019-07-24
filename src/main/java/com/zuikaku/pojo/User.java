package com.zuikaku.pojo;


import java.util.Date;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String sex;
    private String phone;
    private Date register_date;
    private boolean normal;//表示账号是否正常
    private List<Book> lendedBookList;

    public User(int id, String name, String password, String sex, String phone, Date register_date, boolean normal) {
        this.id = id;
        this.name = name;
        this.password=password;
        this.sex = sex;
        this.phone = phone;
        this.register_date = register_date;
        this.normal = normal;
    }

    public User() {
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }

    public boolean isNormal() {
        return normal;
    }

    public void setNormal(boolean normal) {
        this.normal = normal;
    }

    public List<Book> getLendedBookList() {
        return lendedBookList;
    }

    public void setLendedBookList(List<Book> lendedBookList) {
        this.lendedBookList = lendedBookList;
    }

    public User(int id, String name,String password ,String sex, String phone, Date register_date, boolean normal, List<Book> lendedBookList) {
        this.id = id;
        this.name = name;
        this.password=password;
        this.sex = sex;
        this.phone = phone;
        this.register_date = register_date;
        this.normal = normal;
        this.lendedBookList = lendedBookList;
    }

    /**
     * 注册时用的构造函数，不要id,不要normal，不要列表
     * @param name
     * @param sex
     * @param phone
     * @param register_date
     */
    public User(String name, String password,String sex, String phone, Date register_date) {
        this.name = name;
        this.password=password;
        this.sex = sex;
        this.phone = phone;
        this.register_date = register_date;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", register_date=" + register_date +
                ", normal=" + normal +
                ", lendedBookList=" + lendedBookList +
                '}';
    }
}
