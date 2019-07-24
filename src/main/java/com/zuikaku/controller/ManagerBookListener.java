package com.zuikaku.controller;

import com.zuikaku.dao.BookDao;
import com.zuikaku.dao.UserDao;
import com.zuikaku.pojo.Book;
import com.zuikaku.utils.C3P0DataSource;
import com.zuikaku.view.AddBookPanel;
import com.zuikaku.view.ManagerBookPanel;
import com.zuikaku.view.UpdateBookPanel;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.util.List;

public class ManagerBookListener implements ActionListener {

    private JTextField inputText;//来自后台管理书籍管理的上面输入框
    private ManagerBookPanel managerBookPanel;
    public void setInputText(JTextField inputText) {
        this.inputText = inputText;
    }
    public ManagerBookListener(ManagerBookPanel managerBookPanel)
    {
        this.managerBookPanel= managerBookPanel;
    }

    public void actionPerformed(ActionEvent e) {
        Connection conn= C3P0DataSource.getConnection();
        if(e.getActionCommand().equals("insert"))
        {
            //打开新增表单
            new AddBookPanel();
        }else if(e.getActionCommand().equals("delete")){
            if(inputText.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"请在输入框输入要删除的id号，多个已空格隔开","警告",JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            boolean isOk= BookDao.deleteABook(conn,inputText.getText());
            if(isOk){
                JOptionPane.showMessageDialog(null,"所有操作已完成","操作结果",JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null,"操作未完成","操作结果",JOptionPane.ERROR_MESSAGE);
            }

        }else if(e.getActionCommand().equals("queryAll"))
        {
            List<Book> bookList= BookDao.queryAllBooks(conn);
            managerBookPanel.reflashTable(bookList);
        }else if(e.getActionCommand().equals("reflash"))
        {
            List<Book> bookList= BookDao.queryAllBooks(conn);
            managerBookPanel.reflashTable(bookList);
        }else if(e.getActionCommand().equals("fuzzyQuery"))
        {
            if(inputText.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"请在输入框输入关键字","警告",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            List<Book> bookList= UserDao.queryByName(conn,inputText.getText());
            managerBookPanel.reflashTable(bookList);
        }else if(e.getActionCommand().equals("update"))
        {
            if(inputText.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"请在输入框输入要修改的id","警告",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            //todo 修改图书逻辑
            new UpdateBookPanel(Integer.parseInt(inputText.getText()));
        }

        C3P0DataSource.releaseConnection(conn);
    }
}
