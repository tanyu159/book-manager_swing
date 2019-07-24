package com.zuikaku.dao;

import com.zuikaku.pojo.Book;
import com.zuikaku.pojo.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 有关用户的数据库操作
 */
public class UserDao  {
    /**
     * 用户注册
     * @param conn
     * @param registerUser
     * @return
     */
    public static boolean register(Connection conn, User registerUser)
    {
        String insertSqlStr="INSERT INTO t_user(name,password,sex,phone,register_date)" +
                "VALUES (?,?,?,?,?)";
        PreparedStatement ps=null;
        int res=0;
        try {
             ps=conn.prepareStatement(insertSqlStr);
             ps.setString(1,registerUser.getName());
            ps.setString(2,registerUser.getPassword());
             ps.setString(3,registerUser.getSex());
             ps.setString(4,registerUser.getPhone());
             ps.setDate(5,new Date(registerUser.getRegister_date().getTime()));
             res=ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("注册用户时,插入数据库发生异常");
            e.printStackTrace();
        }finally {
            try {
                if(ps!=null)
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res > 0;
    }

    /**
     * 用户登录（登录成功后，同时查询其借的书，）
     * @param conn
     * @param username
     * @param password
     * @return
     */
    public static User login(Connection conn,String username,String password)
    {
        String selectSqlStr="SELECT * FROM t_user WHERE name=?AND password=?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
             ps=conn.prepareStatement(selectSqlStr);
             ps.setString(1,username);
             ps.setString(2,password);
             rs= ps.executeQuery();

             if(rs.next())
             {
                 System.out.println("查到了");
                 int id= rs.getInt("id");
                  String  sex=rs.getString("sex");
                 String phone=rs.getString("phone");
                 java.util.Date register_date=rs.getDate("register_date");
                 boolean normal=rs.getBoolean("normal");
                 List<Book> booksList=queryLendedBookByUsername(conn,username);
                 User loginUsr =new User(id,username,password,sex,phone,register_date,normal,booksList);
                 return loginUsr;
             }


        } catch (SQLException e) {
            System.err.println("用户登录时发生异常");
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
     * 改用户密码-用户只能登录后才能更改
     * @param conn
     * @param loginUsr
     * @return
     */
    public static boolean updatePasswordAndPhone(Connection conn,User loginUsr,String newPassword,String newPhone)
    {
        String updateSqlStr="UPDATE t_user SET password=?,phone=? WHERE id=?";
        PreparedStatement ps=null;
        try {
             ps=conn.prepareStatement(updateSqlStr);
             ps.setString(1,newPassword);
            ps.setString(2,newPhone);
             ps.setInt(3,loginUsr.getId());
             int res= ps.executeUpdate();
             if(res>0)
             {
                 return true;
             }else {
                 return false;
             }
        } catch (SQLException e) {
            System.err.println("用户更新密码时发生异常");
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


    //todo 用户查书，借书，还书

    /**
     * 用户对书进行模糊查询
     * @param conn
     * @param bookname
     * @return
     */
    public static List<Book> queryByName(Connection conn, String bookname)
    {
        //模糊查询书
        String selectSqlStr="SELECT * FROM t_book WHERE name LIKE ?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<Book> books=new ArrayList<Book>();
        try {
            ps=conn.prepareStatement(selectSqlStr);
            ps.setString(1,"%"+bookname+"%");
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
            System.err.println("用户模糊查询书时发生异常");
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
     *  用户借书
     * @param conn
     * @return
     */
    public static boolean lendBook(Connection conn,User user,int bookid)
    {

            PreparedStatement ps=null;
            //使用事务提交
            try {
                conn.setAutoCommit(false);

                String updateBookStateSqlStr="UPDATE t_book SET lended=true,lended_count=lended_count+1 WHERE id=?";
                ps=conn.prepareStatement(updateBookStateSqlStr);
                ps.setInt(1,bookid);
                ps.executeUpdate();//更改这本书的被借阅状态
                ps=conn.prepareStatement(updateBookStateSqlStr);
                String insertLendedSqlStr="INSERT INTO t_lend(fk_uid,fk_bid,lend_date) VALUES (?,?,?)";
                ps=conn.prepareStatement(insertLendedSqlStr);
                ps.setInt(1,user.getId());
                ps.setInt(2,bookid);
                ps.setDate(3,new Date(new java.util.Date().getTime()));
                ps.executeUpdate();//添加借书记录
                conn.commit();//提交事务
                //还原connection的事务提交
                conn.setAutoCommit(true);
                return true;
            } catch (Exception e) {
                System.err.println("用户借书时发生异常");
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
     * 用户还书
     * @param conn
     * @param user
     * @param bookid
     * @return
     */
    public static boolean backBook(Connection conn,User user,int bookid)
    {


            PreparedStatement ps=null;
            //使用事务提交
            try {
                conn.setAutoCommit(false);
                String updateBookStateSqlStr="UPDATE t_book SET lended=false WHERE id=?";
                ps=conn.prepareStatement(updateBookStateSqlStr);
                ps.setInt(1,bookid);
                ps.executeUpdate();//更改这本书的被借阅状态
                String insertLendedSqlStr="UPDATE t_lend SET back_date=? WHERE fk_uid=? AND fk_bid=? ";
                ps=conn.prepareStatement(insertLendedSqlStr);
                ps.setDate(1,new Date(new java.util.Date().getTime()));
                ps.setInt(2,user.getId());
                ps.setInt(3,bookid);
                ps.executeUpdate();//查找到该条借书记录，然后添加其还书时间
                conn.commit();//提交事务
                //还原connection的事务提交
                conn.setAutoCommit(true);
                return true;
            } catch (Exception e) {
                System.err.println("用户借书时发生异常");
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
     * 查询一个用户（名字）借阅的书（用户登录时调用）
     * @return
     */
    public static List<Book> queryLendedBookByUsername(Connection conn,String username)
    {
        String selectSqlStr="SELECT b.id,b.name,b.isbn,b.price,b.lended_count,b.lended,b.author,b.publisher,b.pub_date " +
                "FROM t_user u,t_book b,t_lend l WHERE " +
                "u.id=l.fk_uid AND l.fk_bid=b.id AND u.name=?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<Book> books=new ArrayList<Book>();
        try {
             ps=conn.prepareStatement(selectSqlStr);
             ps.setString(1,username);
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
                 java.util.Date pub_Date=rs.getDate(9);
                 Book book=new Book(id,name,isbn,price,lended_count,lended,author,publisher,pub_Date);
                 books.add(book);
             }
             return books;
        } catch (SQLException e) {
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
     * 列出所有用户（管理员功能）
     * @param connection
     * @return
     */
    public static List<User> queryAllUser(Connection connection)
    {
        String selectSqlStr="SELECT * FROM t_user";
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<User> users=new ArrayList<User>();
        try {
             ps=connection.prepareStatement(selectSqlStr);
             rs=ps.executeQuery();
             while(rs.next())
             {
                 int id= rs.getInt("id");
                 String username=rs.getString("name");
                 String password=rs.getString("password");
                 String  sex=rs.getString("sex");
                 String phone=rs.getString("phone");
                 java.util.Date register_date=rs.getDate("register_date");
                 boolean normal=rs.getBoolean("normal");
                 User user=new User(id,username,password,sex,phone,register_date,normal);
                 users.add(user);
             }
             return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过id删除用户（管理员功能），可输入多个id以空格隔开批量删除
     * @return
     */
    public static boolean deleteUserById(Connection conn,String ids)
    {
        String deleteSqlStr="DELETE FROM t_user WHERE id=?";
        PreparedStatement ps=null;
        String[] idsStrArr=ids.split("\\s+");
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
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 封锁用户
     * @param conn
     * @param ids
     * @return
     */
    public static boolean lockUserById(Connection conn,String ids)
    {
        String deleteSqlStr="UPDATE t_user SET normal=false WHERE id=?";
        PreparedStatement ps=null;
        String[] idsStrArr=ids.split("\\s+");
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
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 解锁用户
     * @param conn
     * @param ids
     * @return
     */
    public static boolean unlockUserById(Connection conn,String ids)
    {
        String deleteSqlStr="UPDATE t_user SET normal=true WHERE id=?";
        PreparedStatement ps=null;
        String[] idsStrArr=ids.split("\\s+");
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
            e.printStackTrace();
        }
        return false;
    }
}
