package Login;

import db.User;
import cookie.Cookie_manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;

public class Login_manager
{
    // 传入参数：name为user的cookie
    public static boolean judge_fix_login(Cookie cookie, EntityManager entityManager, HttpServletRequest req, HttpServletResponse resp)
    {
        String[] value = cookie.getValue().split("-");
        if (value.length !=2)
        {
            Cookie_manager.remove_cookie(cookie, resp);
            return false;
        }
        Long id;
        try {
            id = Long.parseLong(value[0]);
        }
        catch (NumberFormatException x)
        {
            Cookie_manager.remove_cookie(cookie, resp);
            return false;
        }
        User user=entityManager.find(User.class, id);
        if (user != null && value[1].equals( DigestUtils.md5Hex(req.getRemoteAddr()+req.getHeader("User-Agent")+user.password) ))
        {
            return true;
        }
        Cookie_manager.remove_cookie(cookie, resp);
        return false;
    }
}
