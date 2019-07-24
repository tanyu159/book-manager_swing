package com.zuikaku.controller;

import com.zuikaku.dao.BookDao;
import com.zuikaku.pojo.Book;
import com.zuikaku.utils.C3P0DataSource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UpdateBookListener implements ActionListener {

    private JTextField name;
    private JTextField isbn;
    private JTextField price;
    private JTextField author;
    private JTextField publisher;
    private JTextField pub_Date;

    private int needUpdateBookid=-1;

    public void setNeedUpdateBookid(int needUpdateBookid) {
        this.needUpdateBookid = needUpdateBookid;
    }

    public void setName(JTextField name) {
        this.name = name;
    }

    public void setIsbn(JTextField isbn) {
        this.isbn = isbn;
    }

    public void setPrice(JTextField price) {
        this.price = price;
    }

    public void setAuthor(JTextField author) {
        this.author = author;
    }

    public void setPublisher(JTextField publisher) {
        this.publisher = publisher;
    }

    public void setPub_Date(JTextField pub_Date) {
        this.pub_Date = pub_Date;
    }
    public void actionPerformed(ActionEvent e) {
        Connection conn= C3P0DataSource.getConnection();
        if(e.getActionCommand().equals("update"))
        {
            Book newbook=new Book();
            newbook.setName(name.getText());
            newbook.setAuthor(author.getText());
            newbook.setIsbn(isbn.getText());
            newbook.setPrice(Double.parseDouble(price.getText()));
            newbook.setPublisher(publisher.getText());
            try {
                newbook.setPub_date(new SimpleDateFormat("yyyy-MM-dd").parse(pub_Date.getText()));
            } catch (ParseException e1) {
                System.out.println("输入日期格式不正确，出现异常");
                e1.printStackTrace();
            }
           boolean isOk=BookDao.updateBookInfo(conn,newbook,needUpdateBookid);
           if(isOk){
                JOptionPane.showMessageDialog(null,"更新成功","更新结果",JOptionPane.INFORMATION_MESSAGE);
           }else{
               JOptionPane.showMessageDialog(null,"更新失败","更新结果",JOptionPane.ERROR_MESSAGE);
           }
        }


        //归还连接池
        C3P0DataSource.releaseConnection(conn);
    }
}
