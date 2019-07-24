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
import java.util.Date;

public class AddBookListener implements ActionListener {
    private JTextField name;
    private JTextField isbn;
    private JTextField price;
    private JTextField author;
    private JTextField publisher;
    private JTextField pub_Date;

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
        Connection conn = C3P0DataSource.getConnection();
        if (e.getActionCommand().equals("insert")) {
            //添加书籍的逻辑
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date pubDate = null;
            try {
                pubDate = sdf.parse(pub_Date.getText());
            } catch (ParseException e1) {
                System.out.println("插入书籍时：写发行日期解析出错");
                e1.printStackTrace();
            }
            Book book = new Book(name.getText(), isbn.getText(), Double.parseDouble(price.getText()), author.getText(), publisher.getText(), pubDate);
            boolean isOk = BookDao.addABook(conn, book);
            if (isOk) {
                JOptionPane.showMessageDialog(null, "添加成功", "添加结果", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "添加失败", "添加结果", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getActionCommand().equals("reset")) {
            //清空输入框
            name.setText("");
            isbn.setText("");
            price.setText("");
            pub_Date.setText("");
            author.setText("");
            publisher.setText("");
        }
        //归还数据库连接
        C3P0DataSource.releaseConnection(conn);

    }
}
