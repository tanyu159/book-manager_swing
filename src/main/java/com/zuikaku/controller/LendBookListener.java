package com.zuikaku.controller;

import com.zuikaku.UserManager;
import com.zuikaku.dao.BookDao;
import com.zuikaku.dao.UserDao;
import com.zuikaku.utils.C3P0DataSource;
import com.zuikaku.view.LendBookPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class LendBookListener implements ActionListener {
    private JTextField inputText;
    private LendBookPanel lendBookPanel;
    public LendBookListener(LendBookPanel lendBookPanel){
        this.lendBookPanel=lendBookPanel;
    }
    public void setInputText(JTextField inputText) {
        this.inputText = inputText;
    }

    public void actionPerformed(ActionEvent e) {
        Connection conn= C3P0DataSource.getConnection();
        if(e.getActionCommand().equals("reflash"))
        {
            lendBookPanel.reflashTable(BookDao.queryAllBooks(conn));

        }else if(e.getActionCommand().equals("lend")){
           boolean isOk= UserDao.lendBook(conn, UserManager.getInstance().getLoginedUser(),Integer.parseInt(inputText.getText()));
           if(isOk){
                JOptionPane.showMessageDialog(null,"借书成功","结果",JOptionPane.INFORMATION_MESSAGE);
           }else{
               JOptionPane.showMessageDialog(null,"借书失败","结果",JOptionPane.ERROR_MESSAGE);
           }
        }
        //归还数据库连接
        C3P0DataSource.releaseConnection(conn);
    }
}
