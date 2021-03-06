package Login;

import db.User;
import cookie.Cookie_manager;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;

public class Login_manager
{
    public static User judge_fix_login(EntityManager entityManager, HttpServletRequest req, HttpServletResponse resp)
    {
        Cookie[] cookies= req.getCookies();
        if (cookies != null) {
            for (Cookie i : cookies) {
                if (i.getName().equals("user"))
                {
                    return judge_fix_login(i, entityManager, req, resp);
                }
            }
        }
        return null;
    }
    // 传入参数：name为user的cookie
    public static User judge_fix_login(Cookie cookie, EntityManager entityManager, HttpServletRequest req, HttpServletResponse resp)
    {
        String[] value = cookie.getValue().split("-");
        if (value.length !=2)
        {
            Cookie_manager.remove_cookie("user", req, resp);
            return null;
        }
        long id;
        try {
            id = Long.parseLong(value[0]);
        }
        catch (NumberFormatException x)
        {
            Cookie_manager.remove_cookie("user", req, resp);
            return null;
        }
        User user=entityManager.find(User.class, id);
        if (user != null && value[1].equals( DigestUtils.md5Hex(req.getRemoteAddr()+req.getHeader("User-Agent")+user.password) ))
        {
            return user;
        }
        Cookie_manager.remove_cookie("user", req, resp);
        return null;
    }
}
