package com.zuikaku.controller;

import com.zuikaku.dao.BookDao;
import com.zuikaku.dao.UserDao;
import com.zuikaku.pojo.User;
import com.zuikaku.utils.C3P0DataSource;
import com.zuikaku.view.ManagerUserPanel;
import jdk.nashorn.internal.objects.NativeUint8Array;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class ManagerUserListener implements ActionListener {
    private JTextField textField;

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }


    private ManagerUserPanel managerUserPanel;
    public ManagerUserListener(ManagerUserPanel managerUserPanel)
    {
        this.managerUserPanel=managerUserPanel;
    }


    public void actionPerformed(ActionEvent e) {
        Connection conn= C3P0DataSource.getConnection();
        if(e.getActionCommand().equals("queryAll"))
        {
            List<User> userList= UserDao.queryAllUser(conn);
            managerUserPanel.reflashTable(userList);
        }else if(e.getActionCommand().equals("delete"))
        {
            if(textField.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"请输入要删除的id","警告",JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean isOk= UserDao.deleteUserById(conn,textField.getText());
            if(isOk)
            {
                JOptionPane.showMessageDialog(null,"删除成功","结果",JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null,"删除失败","结果",JOptionPane.INFORMATION_MESSAGE);
            }
        } else if(e.getActionCommand().equals("lockUser"))
        {
            if(textField.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"请输入要封锁的id","警告",JOptionPane.ERROR_MESSAGE);
                return;
            }
            // 封锁用户
            boolean isOk= UserDao.lockUserById(conn,textField.getText());
            if(isOk){
                JOptionPane.showMessageDialog(null,"封锁成功","操作结果",JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null,"封锁失败","操作结果",JOptionPane.ERROR_MESSAGE);
            }
        }else if(e.getActionCommand().equals("unlock"))
        {
            if(textField.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"请输入要封锁的id","警告",JOptionPane.ERROR_MESSAGE);
                return;
            }
            // 解锁用户
            boolean isOk= UserDao.unlockUserById(conn,textField.getText());
            if(isOk){
                JOptionPane.showMessageDialog(null,"解锁成功","操作结果",JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null,"解锁失败","操作结果",JOptionPane.ERROR_MESSAGE);
            }
        }

        //归还数据库连接
        C3P0DataSource.releaseConnection(conn);
    }
}
