package com.zuikaku.view;

import com.zuikaku.UserManager;
import com.zuikaku.controller.ManagerUserListener;
import com.zuikaku.dao.UserDao;
import com.zuikaku.pojo.Book;
import com.zuikaku.pojo.User;
import com.zuikaku.utils.C3P0DataSource;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class ManagerUserPanel extends JPanel {

    private Box topBox;
    private JTextField inputTextField;//顶部输入框
    private JButton queryAllUserBtn;//列出所有用户
    private JButton deleteUserBtn;//删除用户
    private JButton lockUserBtn;//封锁用户（被封锁的用户不可借书）
    private JButton unLockUserBtn;//解锁用户
    private JTable userTable;
    private ManagerUserListener managerUserListener;

    public ManagerUserPanel(){
        setLayout(new BorderLayout());
        inputTextField=new JTextField(20);
        queryAllUserBtn=new JButton("刷新");
        queryAllUserBtn.setActionCommand("queryAll");
        deleteUserBtn=new JButton("删除用户");
        deleteUserBtn.setActionCommand("delete");
        lockUserBtn=new JButton("封锁用户");
        lockUserBtn.setActionCommand("lockUser");
        unLockUserBtn=new JButton("解锁用户");
        unLockUserBtn.setActionCommand("unlock");
        topBox=Box.createHorizontalBox();
        topBox.add(inputTextField);
        topBox.add(Box.createHorizontalStrut(5));
        topBox.add(queryAllUserBtn);
        topBox.add(Box.createHorizontalStrut(5));
        topBox.add(lockUserBtn);
        topBox.add(Box.createHorizontalStrut(5));
        topBox.add(deleteUserBtn);
        topBox.add(Box.createHorizontalStrut(5));
        topBox.add(unLockUserBtn);
        //大盒子加入上面
        add(topBox,BorderLayout.NORTH);
        //设置监听器
        managerUserListener=new ManagerUserListener(this);
        managerUserListener.setTextField(inputTextField);
        //添加监听器
        queryAllUserBtn.addActionListener(managerUserListener);
        deleteUserBtn.addActionListener(managerUserListener);
        lockUserBtn.addActionListener(managerUserListener);
        unLockUserBtn.addActionListener(managerUserListener);
        //首次进入该用户管理后台，先展示所有用户
        Connection conn= C3P0DataSource.getConnection();
        List<User> userList= UserDao.queryAllUser(conn);
        reflashTable(userList);
        C3P0DataSource.releaseConnection(conn);
    }

    /**
     * 刷新表格
     * @param userList
     */
    public void reflashTable(List<User> userList)
    {
        // 每次刷新也刷新管理器中的Book列表
        UserManager.getInstance().setAdminUserList(userList);
        //JTable列表显示
        String[] columnName={"id","用户名","密码","性别","电话","注册时间","用户状态"};
        Object [][]data=new Object[userList.size()][columnName.length];
        for(int i=0;i<=data.length-1;i++)
        {
            for(int j=0;j<=data[i].length-1;j++)
            {
                User user= userList.get(i);
                switch (j)
                {
                    case 0:
                        data[i][j]=user.getId();
                        break;
                    case 1:
                        data[i][j]=user.getName();
                        break;
                    case 2:
                        data[i][j]=user.getPassword();
                        break;
                    case 3:
                        data[i][j]=user.getSex();
                        break;
                    case 4:
                        data[i][j]=user.getPhone();
                        break;
                    case 5:
                        data[i][j]=user.getRegister_date();
                        break;
                    case 6:
                        data[i][j]=user.isNormal()?"正常":"封禁";
                        break;

                }
            }
        }
        userTable=new JTable(data,columnName){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane=new JScrollPane(userTable);
        add(new JScrollPane(scrollPane),BorderLayout.CENTER);
        validate();
    }


}
