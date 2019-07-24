package com.zuikaku.view;

import com.zuikaku.UserManager;
import com.zuikaku.controller.LendBookListener;
import com.zuikaku.dao.BookDao;
import com.zuikaku.pojo.Book;
import com.zuikaku.utils.C3P0DataSource;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class LendBookPanel extends JFrame {
    private JButton executeLendBtn;//执行借阅
    private JButton reflashBtn;//刷新按钮
    private JTextField textField;//输入要借的id

    private JTable booksTable;//可被借阅的书的表

    private LendBookListener lendBookListener;
    public LendBookPanel(){
        setTitle("借书面板");
        setResizable(false);
        textField=new JTextField(20);
        reflashBtn=new JButton("刷新");
        reflashBtn.setActionCommand("reflash");
        executeLendBtn=new JButton("进行借阅");
        executeLendBtn.setActionCommand("lend");
        JPanel topPanel=new JPanel();
        topPanel.add(textField);
        topPanel.add(reflashBtn);
        topPanel.add(executeLendBtn);
        add(topPanel, BorderLayout.NORTH);
        setBounds(500,500,800,600);
        setVisible(true);
        //设置监听器
        lendBookListener=new LendBookListener(this);
        lendBookListener.setInputText(textField);
        //添加监听器
        reflashBtn.addActionListener(lendBookListener);
        executeLendBtn.addActionListener(lendBookListener);
        Connection conn= C3P0DataSource.getConnection();
        List<Book> books= BookDao.queryAllBooks(conn);
        reflashTable(books);
    }

    /**
     * 刷新表格
     * @param bookList
     */
    public void reflashTable(List<Book> bookList)
    {

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
        booksTable=new JTable(data,columnName){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        booksTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane=new JScrollPane(booksTable);
        add(new JScrollPane(scrollPane),BorderLayout.CENTER);
        validate();
    }
}
