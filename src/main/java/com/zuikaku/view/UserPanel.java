package com.zuikaku.view;

import com.zuikaku.UserManager;
import com.zuikaku.controller.UserListener;
import com.zuikaku.pojo.Book;
import com.zuikaku.pojo.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 用户登陆后的界面：用户个人信息查看更新，查书，借书，还书，查看字节借了哪些书功能，
 */
public class UserPanel extends JFrame {
    private JLabel idLabel;
    private JLabel idValueLabel;
    private JLabel nameLabel;
    private JLabel nameValueLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel sexLabel;
    private JLabel sexValueLabel;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel registerDateLabel;
    private JLabel registerDateValueLabel;
    private JLabel stateLabel;
    private JLabel stateValueLabel;
    //按钮
    private JButton updateUserInfoBtn;//更新用户数据按钮
    //横向BOX
    private Box idBox;
    private Box nameBox;
    private Box passwordBox;
    private Box sexBox;
    private Box phoneBox;
    private Box registerDateBox;
    private Box stateBox;
    //纵向BOX
    private Box userBox;
    private JPanel userInfoPanel;

    //todo 已借书的表格
    private JTable lendedBookTable;//该用户已经借了的书的表格
    private JButton reflashBtn;//刷新按钮
    private JTextField inputText;//用于还书的输入框，输入书的id
    private JButton backBookBtn;//还书按钮
    private JButton lendBookBtn;//借书系统进入按钮


    //监听器
    private UserListener userListener;
    public UserPanel(){
        //GUI部分
        setResizable(false);
        setTitle("用户界面");
        userInfoPanel=new JPanel();
        idLabel=new JLabel("用户id：");
        nameLabel=new JLabel("用户名：");
        passwordLabel =new JLabel("密码：");
        sexLabel=new JLabel("性别：");
        phoneLabel=new JLabel("电话号码：");
        registerDateLabel=new JLabel("注册时间：");
        stateLabel=new JLabel("账户状态：");
        idValueLabel=new JLabel("123");
        nameValueLabel=new JLabel();
        passwordField=new JPasswordField(10);
        sexValueLabel=new JLabel();
        phoneTextField=new JTextField(10);
        registerDateValueLabel=new JLabel();
        stateValueLabel=new JLabel();

        updateUserInfoBtn=new JButton("更新我的账户");
        updateUserInfoBtn.setActionCommand("updateUser");
        //实例化盒子
        idBox=Box.createHorizontalBox();
        nameBox=Box.createHorizontalBox();
        passwordBox=Box.createHorizontalBox();
        sexBox=Box.createHorizontalBox();
        phoneBox=Box.createHorizontalBox();
        registerDateBox=Box.createHorizontalBox();
        stateBox=Box.createHorizontalBox();
        userBox=Box.createVerticalBox();
        //加入盒子
        idBox.add(idLabel);
        idBox.add(Box.createVerticalStrut(1));
        idBox.add(idValueLabel);
        nameBox.add(nameLabel);
        nameBox.add(Box.createVerticalStrut(5));
        nameBox.add(nameValueLabel);
        passwordBox.add(passwordLabel);
        passwordBox.add(Box.createVerticalStrut(5));
        passwordBox.add(passwordField);
        sexBox.add(sexLabel);
        sexBox.add(Box.createVerticalStrut(5));
        sexBox.add(sexValueLabel);
        phoneBox.add(phoneLabel);
        phoneBox.add(Box.createVerticalStrut(5));
        phoneBox.add(phoneTextField);
        registerDateBox.add(registerDateLabel);
        registerDateBox.add(Box.createVerticalStrut(5));
        registerDateBox.add(registerDateValueLabel);
        stateBox.add(stateLabel);
        stateBox.add(Box.createVerticalStrut(5));
        stateBox.add(stateValueLabel);
        //加入大盒子(user部分)
        userBox.add(idBox);
        userBox.add(Box.createVerticalStrut(5));
        userBox.add(nameBox);
        userBox.add(Box.createVerticalStrut(5));
        userBox.add(passwordBox);
        userBox.add(Box.createVerticalStrut(5));
        userBox.add(sexBox);
        userBox.add(Box.createVerticalStrut(5));
        userBox.add(phoneBox);
        userBox.add(Box.createVerticalStrut(5));
        userBox.add(registerDateBox);
        userBox.add(Box.createVerticalStrut(5));
        userBox.add(stateBox);
        userBox.add(Box.createVerticalStrut(5));
        userBox.add(updateUserInfoBtn);
        userBox.add(new JLabel("已借书的情况"));
        userInfoPanel.add(userBox);
        userInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        userInfoPanel.setPreferredSize(new Dimension(200,200));
        add(userInfoPanel,BorderLayout.NORTH);

        // 借书还书，交互部分GUI部分
        reflashBtn =new JButton("刷新");
        reflashBtn.setActionCommand("reflash");
        inputText=new JTextField(20);
        backBookBtn=new JButton("还书");
        backBookBtn.setActionCommand("back");
        lendBookBtn=new JButton("进入借书系统");
        lendBookBtn.setActionCommand("lend");
        JPanel bookFunctionPanel=new JPanel();
        bookFunctionPanel.add(inputText);
        bookFunctionPanel.add(reflashBtn);
        bookFunctionPanel.add(backBookBtn);
        bookFunctionPanel.add(lendBookBtn);
        add(bookFunctionPanel,BorderLayout.SOUTH);

        setBounds(500,500,800,600);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        reflashUserShowValue(UserManager.getInstance().getLoginedUser());//将登录用户的信息刷新显示在用户面板上

        //首次进入时，将该用户借书的列表显示出来
        reflashTable(UserManager.getInstance().getLoginedUser().getLendedBookList());


        //设置监听器
        userListener=new UserListener(this);
        userListener.setPasswordField(passwordField);
        userListener.setPhoneTextField(phoneTextField);
        userListener.setInputTextField(inputText);//还书输入框
        //添加监听器
        updateUserInfoBtn.addActionListener(userListener);
        reflashBtn.addActionListener(userListener);
        backBookBtn.addActionListener(userListener);
        lendBookBtn.addActionListener(userListener);

    }

    /**
     * 用于更新用户信息的显示
     * @param user
     */
    public void reflashUserShowValue(User user){
        idValueLabel.setText(String.valueOf(user.getId()));
        nameValueLabel.setText(user.getName());
        sexValueLabel.setText(user.getSex());
        passwordField.setText(user.getPassword());
        phoneTextField.setText(user.getPhone());
        registerDateValueLabel.setText(user.getRegister_date().toString());
        stateValueLabel.setText( user.isNormal()?"正常":"受限");

        validate();
    }

    /**
     * 刷新表格
     * @param bookList
     */
    public void reflashTable(List<Book> bookList)
    {
        // 每次刷新也刷新管理器中的Book列表
        UserManager.getInstance().setAdminBookList(bookList);
        //JTable列表显示
        String[] columnName={"id","书名","ISBN","价格","借出次数","借阅状态","作者","发行商","发行日期"};
        Object [][]data=new Object[bookList.size()][columnName.length];
        for(int i=0;i<=data.length-1;i++)
        {
            for(int j=0;j<=data[i].length-1;j++)
            {
                Book book= bookList.get(i);
                switch (j)
                {
                    case 0:
                        data[i][j]=book.getId();
                        break;
                    case 1:
                        data[i][j]=book.getName();
                        break;
                    case 2:
                        data[i][j]=book.getIsbn();
                        break;
                    case 3:
                        data[i][j]=book.getPrice();
                        break;
                    case 4:
                        data[i][j]=book.getLended_count();
                        break;
                    case 5:
                        data[i][j]=book.isLended()?"已借":"未借";
                        break;
                    case 6:
                        data[i][j]=book.getAuthor();
                        break;
                    case 7:
                        data[i][j]=book.getPublisher();
                        break;
                    case 8:
                        data[i][j]=book.getPub_date();
                        break;
                }
            }
        }
        lendedBookTable=new JTable(data,columnName){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        lendedBookTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane=new JScrollPane(lendedBookTable);
        add(new JScrollPane(scrollPane),BorderLayout.CENTER);
        validate();
    }
}
