package Login;

import Exception.Shop_exception_already_login;
import Exception.Shop_exception_login;
import Exception.Shop_exception_format;
import db.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.digest.DigestUtils;


@WebServlet(value="/api/login", loadOnStartup = 0)
public class Login extends HttpServlet
{
    private static final EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory( "myjpa" );

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("application/json;charset=utf-8");
        Output output=new Output();
        output.ret=-1;
        ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter pw=resp.getWriter();
        EntityManager entityManager = sessionFactory.createEntityManager();
        try
        {
            // 验证是否已经登陆
            {
                Cookie[] cookies= req.getCookies();
                if (cookies != null) {
                    for (Cookie i : cookies) {
                        if (i.getName().equals("user"))
                        {
                            if(Login_manager.judge_fix_login(i, entityManager, req, resp)!=null)
                            {
                                //已经登陆
                                throw new Shop_exception_already_login();
                            }
                            break;
                        }
                    }
                }
            }

            Input input = objectMapper.readValue(req.getReader(), Input.class);
            if (input.id == null || input.password == null) {
                throw new Shop_exception_format();
            }
            User user=entityManager.find(User.class, input.id);
            if (user != null && user.password.equals( input.password)) {
                Cookie cookie=new Cookie("user", input.id.toString() + '-' + DigestUtils.md5Hex(req.getRemoteAddr() + req.getHeader("User-Agent") + input.password));
                cookie.setMaxAge(60*60*24*365);
                cookie.setAttribute("SameSite", "Strict");
                cookie.setPath(req.getContextPath());
                resp.addCookie(cookie);
                output.ret = 0;
            }
            else
            {
                output.ret=-4;
            }
        }
        catch(Shop_exception_login x)
        {
            //已经登陆
            output.ret = -2;
        }
        catch (Shop_exception_format x)
        {
            output.ret = -3;
        }
        finally {
            entityManager.close();
            objectMapper.writeValue(pw, output);
        }
    }


    public void destroy() {
        sessionFactory.close();
        super.destroy();
    }
}

class Input {
    public String id;
    public String password;
}

class Output
{
    public int ret;
}


