package com.zuikaku.view;

import com.zuikaku.UserManager;
import com.zuikaku.controller.ManagerBookListener;
import com.zuikaku.dao.BookDao;
import com.zuikaku.pojo.Book;
import com.zuikaku.utils.C3P0DataSource;

import java.sql.Connection;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class ManagerBookPanel extends JPanel {
    private JButton insertNewBookBtn;//插入新书按钮
    private JButton deleteBookBtn;//删除书的按钮
    private JButton updateBookInfoBtn;//修改图书信息
    private JButton queryAllBookBtn;//列出所有书
    private JButton reflashBtn;//刷新按钮
    private JButton fuzzyQueryBtn;//模糊查询
    private JTable booksTable;//列出书的表格
    private JTextField inputTextField;//输入框（可用于模糊查询（书名））也可用于删除书和修改书（输入id号）

    //监听器
    private ManagerBookListener managerBookListener;
    private Box topBox;
    public ManagerBookPanel()
    {
        setLayout(new BorderLayout());
        insertNewBookBtn=new JButton("添加书籍");
        insertNewBookBtn.setActionCommand("insert");
        deleteBookBtn=new JButton("删除书籍");
        deleteBookBtn.setActionCommand("delete");
        updateBookInfoBtn=new JButton("修改书籍");
        updateBookInfoBtn.setActionCommand("update");
        queryAllBookBtn=new JButton("列出所有");
        queryAllBookBtn.setActionCommand("queryAll");
        reflashBtn=new JButton("刷新");
        reflashBtn.setActionCommand("reflash");
        fuzzyQueryBtn=new JButton("模糊查询");
        fuzzyQueryBtn.setActionCommand("fuzzyQuery");
        inputTextField=new JTextField(10);
        topBox=Box.createHorizontalBox();
        topBox.add(inputTextField);
        topBox.add(Box.createHorizontalStrut(5));
        topBox.add(insertNewBookBtn);
        topBox.add(Box.createHorizontalStrut(5));
        topBox.add(deleteBookBtn);
        topBox.add(Box.createHorizontalStrut(5));
        topBox.add(updateBookInfoBtn);
        topBox.add(Box.createHorizontalStrut(5));
        topBox.add(queryAllBookBtn);
        topBox.add(Box.createHorizontalStrut(5));
        topBox.add(reflashBtn);
        topBox.add(Box.createHorizontalStrut(5));
        topBox.add(fuzzyQueryBtn);
        topBox.add(Box.createHorizontalStrut(5));
        //盒子加入面板
        add(topBox,BorderLayout.NORTH);


        //首次进入该后台管理时，书表进行列出
        Connection conn= C3P0DataSource.getConnection();
        List<Book> bookList= BookDao.queryAllBooks(conn);//获取书表
        reflashTable(bookList);

        //释放数据库连接
        C3P0DataSource.releaseConnection(conn);
        setVisible(true);
        setBounds(500,500,800,600);
        //设置监听器
        managerBookListener=new ManagerBookListener(this);
        managerBookListener.setInputText(inputTextField);
        //添加监听器
        insertNewBookBtn.addActionListener(managerBookListener);
        deleteBookBtn.addActionListener(managerBookListener);
        updateBookInfoBtn.addActionListener(managerBookListener);
        queryAllBookBtn.addActionListener(managerBookListener);
        reflashBtn.addActionListener(managerBookListener);
        fuzzyQueryBtn.addActionListener(managerBookListener);

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
