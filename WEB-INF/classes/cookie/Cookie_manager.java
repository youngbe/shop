package cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class Cookie_manager {
    public static void remove_cookie(Cookie cookie, HttpServletResponse resp)
    {
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }
    public static void remove_cookie(String s, HttpServletRequest req, HttpServletResponse resp)
    {
        Cookie[] cookies=req.getCookies();
        if ( cookies != null )
        {
            for (Cookie i:cookies)
            {
                if (i.getName().equals(s))
                {
                    i.setMaxAge(0);
                    resp.addCookie(i);
                }
            }
        }
    }
}
