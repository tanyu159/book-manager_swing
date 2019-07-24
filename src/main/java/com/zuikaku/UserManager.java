package com.zuikaku;

import com.zuikaku.pojo.Book;
import com.zuikaku.pojo.User;

import java.util.List;

/**
 * 系统管理（不止含有用户管理）
 */
public class UserManager {
    private User loginedUser;
    private List<Book> adminBookList;//管理员管理所用，临时用来存储表格中显示的书
    private List<User> adminUserList;//管理员管理所用，临时用来存储表格中显示的用户

    public List<User> getAdminUserList() {
        return adminUserList;
    }

    public void setAdminUserList(List<User> adminUserList) {
        this.adminUserList = adminUserList;
    }

    public List<Book> getAdminBookList() {
        return adminBookList;
    }

    public void setAdminBookList(List<Book> adminBookList) {
        this.adminBookList = adminBookList;
    }

    public User getLoginedUser() {
        return loginedUser;
    }

    public void setLoginedUser(User loginedUser) {
        this.loginedUser = loginedUser;
    }

    private static UserManager instance;
    public synchronized static UserManager getInstance(){
        if(instance==null)
        {
            instance=new UserManager();
        }
        return instance;
    }
    private UserManager(){}
}
