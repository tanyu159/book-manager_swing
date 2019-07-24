package com.zuikaku.dao;

import com.zuikaku.pojo.Book;
import com.zuikaku.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 关于书的操作
 */
public class BookDao {
    /**
     * 上架一本书（管理员功能）
     * @param conn
     * @param book
     * @return
     */
    public static boolean addABook(Connection conn, Book book)
    {
        String insertSqlStr="INSERT INTO t_book VALUES (NULL,?,?,?,?,?,?,?,?)";
        PreparedStatement ps=null;
        try {
            ps=conn.prepareStatement(insertSqlStr);
            ps.setString(1,book.getName());
            ps.setString(2,book.getIsbn());
            ps.setDouble(3,book.getPrice());
            ps.setInt(4,0);
            ps.setBoolean(5,false);
            ps.setString(6,book.getAuthor());
            ps.setString(7,book.getPublisher());
            ps.setDate(8,new Date(book.getPub_date().getTime()));
            int res= ps.executeUpdate();
            return res>0;
        } catch (SQLException e) {
            System.err.println("管理员添加书时发生异常");
            e.printStackTrace();
        }finally {
            try {
                if(ps!=null)
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 删除一本书（管理员操作）可输入多个id以空格隔开批量删除
     * @param conn
     * @param bookids
     * @return
     */
    public static boolean deleteABook(Connection conn,String bookids)
    {
        String deleteSqlStr="DELETE FROM t_book WHERE id=?";
        PreparedStatement ps=null;
        String[] idsStrArr=bookids.split("\\s+");
        int []idArr=new int[idsStrArr.length];
        int count=0;//成功删除的次数
        for(int i=0;i<=idArr.length-1;i++)
        {
            idArr[i]=Integer.parseInt(idsStrArr[i]);
        }
        try {
            ps=conn.prepareStatement(deleteSqlStr);
            for(int id:idArr)
            {
                ps.setInt(1,id);
                count+= ps.executeUpdate();
            }
            if(count==idArr.length)
            {
                return true;//说明操作全部成功
            }else{
                return false;//只成功删除了部分
            }
        } catch (SQLException e) {
            System.err.println("管理员删除书时发生异常");
            e.printStackTrace();
        }finally {
            try {
                if(ps!=null)
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 列出所有的书（管理员功能）
     * @param conn
     * @return
     */
    public static List<Book> queryAllBooks(Connection conn)
    {
        String selectSqlStr="SELECT * FROM t_book";
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<Book> books=new ArrayList<Book>();
        try {
            ps=conn.prepareStatement(selectSqlStr);
            rs=ps.executeQuery();
            while(rs.next())
            {
                int id=rs.getInt(1);
                String name=rs.getString(2);
                String isbn=rs.getString(3);
                double price=rs.getDouble(4);
                int lended_count=rs.getInt(5);
                boolean lended=rs.getBoolean(6);
                String author=rs.getString(7);
                String publisher=rs.getString(8);
                java.util.Date pub_date=rs.getDate(9);
                Book book=new Book(id,name,isbn,price,lended_count,lended,author,publisher,pub_date);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            System.err.println("管理员列出所有书时发生异常");
            e.printStackTrace();
        }finally {
            try {
                if(rs!=null)
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(ps!=null)
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 更改图书的信息(管理员)
     * @return
     */
    public static boolean updateBookInfo(Connection conn,Book newBookInfo,int bookid){
        String updateSqlStr="UPDATE t_book SET name=?,isbn=?,price=?,author=?,publisher=?,pub_date=? WHERE " +
                "id=?";
        PreparedStatement ps=null;
        try {
             ps=conn.prepareStatement(updateSqlStr);
             ps.setString(1,newBookInfo.getName());
             ps.setString(2,newBookInfo.getIsbn());
             ps.setDouble(3,newBookInfo.getPrice());
             ps.setString(4,newBookInfo.getAuthor());
             ps.setString(5,newBookInfo.getPublisher());
             ps.setDate(6,new Date(newBookInfo.getPub_date().getTime()));
             ps.setInt(7,bookid);
             return ps.executeUpdate()>0;
        } catch (SQLException e) {
            System.err.println("管理员更新书时发生异常");
            e.printStackTrace();
        }finally {
            try {
                if(ps!=null)
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
