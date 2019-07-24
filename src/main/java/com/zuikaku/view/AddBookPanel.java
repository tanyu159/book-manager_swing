package com.zuikaku.view;

import com.zuikaku.controller.AddBookListener;

import javax.swing.*;
import java.awt.*;

public class AddBookPanel extends JFrame {
    private JLabel nameLabel;
    private JLabel isbnLabel;
    private JLabel priceLabel;
    private JLabel authorLabel;
    private JLabel publisherLabel;
    private JLabel pub_DateLabel;
    private JTextField nameText;
    private JTextField isbnText;
    private JTextField priceText;
    private JTextField authorText;
    private JTextField publisherText;
    private JTextField pub_DateText;
    private JButton insertBtn;//添加按钮
    private JButton resetBtn;//重置按钮
    //大盒子（垂直排列）
    private Box bigBox;
    //label -Text 小盒子 水平排列
    private Box nameBox;
    private Box isbnBox;
    private Box priceBox;
    private Box authorBox;
    private Box publisherBox;
    private Box pub_DateBox;
    private Box buttonBox;

    //监听器
    private AddBookListener addBookListener;
    public AddBookPanel(){
        //GUI部分
        setResizable(false);
        setLayout(new FlowLayout());
        setTitle("添加图书");
        nameLabel=new JLabel("书名：");
        isbnLabel=new JLabel("ISBN号：");
        priceLabel=new JLabel("价格：");
        authorLabel=new JLabel("作者：");
        publisherLabel=new JLabel("发行商：");
        pub_DateLabel=new JLabel("发行日期：");
        nameText=new JTextField(20);
        isbnText=new JTextField(20);
        priceText=new JTextField(20);
        authorText=new JTextField(20);
        publisherText=new JTextField(20);
        pub_DateText=new JTextField(20);
        insertBtn=new JButton("插入");
        insertBtn.setActionCommand("insert");
        resetBtn=new JButton("重置");
        resetBtn.setActionCommand("reset");
        bigBox=Box.createVerticalBox();
        nameBox=Box.createHorizontalBox();
        isbnBox=Box.createHorizontalBox();
        priceBox=Box.createHorizontalBox();
        authorBox=Box.createHorizontalBox();
        publisherBox=Box.createHorizontalBox();
        pub_DateBox=Box.createHorizontalBox();
        buttonBox=Box.createHorizontalBox();
        //部件加入盒子
        nameBox.add(nameLabel);
        nameBox.add(Box.createHorizontalStrut(5));
        nameBox.add(nameText);
        isbnBox.add(isbnLabel);
        isbnBox.add(Box.createHorizontalStrut(5));
        isbnBox.add(isbnText);
        priceBox.add(priceLabel);
        priceBox.add(Box.createHorizontalStrut(5));
        priceBox.add(priceText);
        authorBox.add(authorLabel);
        authorBox.add(Box.createHorizontalStrut(5));
        authorBox.add(authorText);
        publisherBox.add(publisherLabel);
        publisherBox.add(Box.createHorizontalStrut(5));
        publisherBox.add(publisherText);
        pub_DateBox.add(pub_DateLabel);
        pub_DateBox.add(Box.createHorizontalStrut(5));
        pub_DateBox.add(pub_DateText);
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add(insertBtn);
        buttonBox.add(Box.createHorizontalStrut(5));
        buttonBox.add(resetBtn);
        //加入大盒子
        bigBox.add(nameBox);
        bigBox.add(Box.createVerticalStrut(10));
        bigBox.add(isbnBox);
        bigBox.add(Box.createVerticalStrut(10));
        bigBox.add(priceBox);
        bigBox.add(Box.createVerticalStrut(10));
        bigBox.add(authorBox);
        bigBox.add(Box.createVerticalStrut(10));
        bigBox.add(publisherBox);
        bigBox.add(Box.createVerticalStrut(10));
        bigBox.add(pub_DateBox);
        bigBox.add(Box.createVerticalStrut(10));
        bigBox.add(buttonBox);
        //加入窗口
        add(bigBox);
        setBounds(500,500,640,360);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //设置监听器
        addBookListener=new AddBookListener();
        addBookListener.setName(nameText);
        addBookListener.setAuthor(authorText);
        addBookListener.setIsbn(isbnText);
        addBookListener.setPrice(priceText);
        addBookListener.setPublisher(publisherText);
        addBookListener.setPub_Date(pub_DateText);
        //添加监听器
        insertBtn.addActionListener(addBookListener);
        resetBtn.addActionListener(addBookListener);




    }
}
