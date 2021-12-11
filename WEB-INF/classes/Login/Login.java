package Login;

import cookie.Cookie_manager;
import db.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.digest.DigestUtils;


@WebServlet(value="/api/login", loadOnStartup = 0)
public class Login extends HttpServlet
{
    private static EntityManagerFactory sessionFactory;
    static
    {
        sessionFactory = Persistence.createEntityManagerFactory( "myjpa" );
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException
    {
        try
        {
            resp.setContentType("application/json;charset=utf-8");
            Output output=new Output();
            ObjectMapper objectMapper = new ObjectMapper();
            PrintWriter pw=resp.getWriter();
            try {
                EntityManager entityManager = sessionFactory.createEntityManager();
                {
                    Cookie[] cookies= req.getCookies();
                    if (cookies != null) {
                        for (Cookie i : cookies) {
                            if (i.getName().equals("user"))
                            {
                                if (Login_manager.judge_fix_login(i, entityManager, req, resp)) {
                                    entityManager.close();
                                    output.ret = 1;
                                    objectMapper.writeValue(pw, output);
                                    return;
                                }
                                break;
                            }
                        }
                    }
                }
                Input input = objectMapper.readValue(req.getReader(), Input.class);
                if (input.id == null || input.password == null) {
                    entityManager.close();
                    throw new Exception();
                }
                User user=entityManager.find(User.class, input.id);
                if (user != null && user.password.equals( input.password)) {
                    output.ret = 0;
                    Cookie cookie=new Cookie("user", input.id.toString() + '-' + DigestUtils.md5Hex(req.getRemoteAddr() + req.getHeader("User-Agent") + input.password));
                    cookie.setMaxAge(60*60*24*365);
                    cookie.setPath(req.getContextPath());
                    resp.addCookie(cookie);
                }
                else
                {
                    output.ret=-1;
                }
                entityManager.close();
            }
            catch (Exception x) {
                output.ret = -1;
                objectMapper.writeValue(pw, output);
                return;
            }
            objectMapper.writeValue(pw, output);
        }
        catch (Exception p)
        {
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


