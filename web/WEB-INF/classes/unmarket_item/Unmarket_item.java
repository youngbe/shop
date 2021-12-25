package unmarket_item;

import Exception.Shop_exception_login;
import Exception.Shop_exception_not_login;
import Exception.Shop_exception_format;
import Exception.Shop_exception_item_not_found;
import Login.Login_manager;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.Item;
import db.User;
import jakarta.persistence.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value="/api/unmarket_item")
public class Unmarket_item extends HttpServlet
{
    private final static EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory( "myjpa" );
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("application/json;charset=utf-8");
        Output output=new Output();
        output.ret=-1;
        ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter pw=resp.getWriter();

        try {
            final long id;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (NumberFormatException x) {
                throw new Shop_exception_format();
            }
            EntityManager entityManager = sessionFactory.createEntityManager();
            try
            {
                // 验证是否已经登陆
                User user = null;
                {
                    Cookie[] cookies = req.getCookies();
                    if (cookies != null) {
                        for (Cookie i : cookies) {
                            if (i.getName().equals("user")) {
                                user = Login_manager.judge_fix_login(i, entityManager, req, resp);
                                break;
                            }
                        }
                        if (user == null) {
                            throw new Shop_exception_not_login();
                        }
                    }
                }
                Item item=entityManager.find(Item.class, id);
                if (item ==null)
                {
                    throw new Shop_exception_item_not_found();
                }
                EntityTransaction entityTransaction=entityManager.getTransaction();
                entityTransaction.begin();
                try {
                    item.on_market=1;
                    entityTransaction.commit();
                } catch (Throwable x) {
                    entityTransaction.rollback();
                    throw x;
                }

            }
            finally {
                entityManager.close();
            }


            output.ret = 0;
        }
        catch (Shop_exception_format x)
        {
            output.ret=-3;
        }
        catch(Shop_exception_login x)
        {
            output.ret = -2;
        }
        catch (Shop_exception_item_not_found x)
        {
            output.ret=-4;
        }
        finally {
            objectMapper.writeValue(pw, output);
        }
    }
    public void destroy() {
        sessionFactory.close();
        super.destroy();
    }
}

class Output
{
    public int ret;
}
