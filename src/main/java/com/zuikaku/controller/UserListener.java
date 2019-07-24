package com.zuikaku.controller;

import com.zuikaku.UserManager;
import com.zuikaku.dao.UserDao;
import com.zuikaku.pojo.User;
import com.zuikaku.utils.C3P0DataSource;
import com.zuikaku.view.UserPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class UserListener implements ActionListener {
    private UserPanel userPanel;
    private JPasswordField passwordField;
    private JTextField phoneTextField;
    private JTextField inputTextField;

    public void setInputTextField(JTextField inputTextField) {
        this.inputTextField = inputTextField;
    }

    public UserListener(UserPanel userPanel) {
        this.userPanel = userPanel;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public void setPhoneTextField(JTextField phoneTextField) {
        this.phoneTextField = phoneTextField;
    }

    public void actionPerformed(ActionEvent e) {
        Connection conn = C3P0DataSource.getConnection();
        if (e.getActionCommand().equals("updateUser")) {
            boolean isOk = UserDao.updatePasswordAndPhone(conn, UserManager.getInstance().getLoginedUser(), new String(passwordField.getPassword()), phoneTextField.getText());
            if (isOk) {
                JOptionPane.showMessageDialog(null, "修改成功", "用户信息更新结果", JOptionPane.INFORMATION_MESSAGE);
                //然后刷新这个UserPanel,也要同步更新UserManager中的内容
                User oldUsr=UserManager.getInstance().getLoginedUser();
                User newUser=new User(oldUsr.getId(),oldUsr.getName(),new String(passwordField.getPassword()),oldUsr.getSex(),phoneTextField.getText(),oldUsr.getRegister_date(),oldUsr.isNormal());
                UserManager.getInstance().setLoginedUser(newUser);
                userPanel.reflashUserShowValue(newUser);

            } else {
                JOptionPane.showMessageDialog(null, "修改失败", "用户信息更新结果", JOptionPane.ERROR_MESSAGE);
            }

        }else if(e.getActionCommand().equals("reflash")){
            //todo 刷新用户所借表格

        }else if(e.getActionCommand().equals("back")){
            //todo 用户还书(获得inputTextField的输入)
        }else if(e.getActionCommand().equals("lend"))
        {
            //todo 进入用户借书系统 （新开窗体）
        }
    }
}
