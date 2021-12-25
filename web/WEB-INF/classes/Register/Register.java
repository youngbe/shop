package Register;

import Exception.Shop_exception_format;
import Exception.Shop_exception_already_login;
import Exception.Shop_exception_login;
import Login.Login_manager;
import db.*;
import jakarta.persistence.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;


@WebServlet(value="/api/regist", loadOnStartup = 0)
public class Register extends HttpServlet
{
    private final static EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory( "myjpa" );

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
            if(Login_manager.judge_fix_login(entityManager, req, resp)!=null)
            {
                throw new Shop_exception_already_login();
            }
            Input input = objectMapper.readValue(req.getReader(), Input.class);
            if (input.nick_name == null || input.nick_name.length() > 100) {
                throw new Shop_exception_format();
            }
            if (input.password == null || input.password.length() > 100) {
                throw new Shop_exception_format();
            }

            User new_user = new User();
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            try
            {
                new_user.nick_name = input.nick_name;
                new_user.password = input.password;
                entityManager.persist(new_user);
                output.id = new_user.id;

                entityTransaction.commit();
            }
            catch (Throwable x)
            {
                entityTransaction.rollback();
                throw x;
            }

            Cookie cookie=new Cookie("user", new_user.id.toString() + '-' + DigestUtils.md5Hex(req.getRemoteAddr() + req.getHeader("User-Agent") + new_user.password));
            cookie.setMaxAge(60*60*24*365);
            cookie.setAttribute("SameSite", "Strict");
            cookie.setPath(req.getContextPath());
            resp.addCookie(cookie);


            output.ret = 0;
        }
        catch(Shop_exception_login x)
        {
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
    public String nick_name;
    public String password;
}

class Output
{
    public int ret;
    public Long id;
}


