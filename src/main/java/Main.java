import com.zuikaku.dao.UserDao;
import com.zuikaku.pojo.User;
import com.zuikaku.utils.C3P0DataSource;
import com.zuikaku.view.MainPanel;


import java.sql.Connection;
import java.util.Date;

public class Main {
    public static void main(String[] args)
    {
        C3P0DataSource.init();


        new MainPanel();
        //测试代码
        //测试用户注册
        //User user=new User("谭宇测试1","123456","男","13072889048",new Date());
        //Connection conn= C3P0DataSource.getConnection();
        //System.out.println(UserDao.register(conn,user));

        //测试用户登录
        //User loginUsr=UserDao.login(conn,"谭宇测试1","123456");
        //System.out.println(loginUsr);

        //测试用户更新密码
        //UserDao.updatePassword(conn,loginUsr,"456");
    }
}
