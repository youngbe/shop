package get_my_items;

import Exception.Shop_exception_not_login;
import Exception.Shop_exception_login;
import Login.Login_manager;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.Item;
import db.User;
import jakarta.persistence.*;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;

@WebServlet(value="/api/get_my_items")
public class Get_my_items extends HttpServlet
{
    private final static EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory( "myjpa" );
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("application/json;charset=utf-8");
        Output output=new Output();
        output.ret=-1;
        ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter pw=resp.getWriter();
        EntityManager entityManager = sessionFactory.createEntityManager();
        try {
            // 验证是否已经登陆
            User user=null;
            {
                Cookie[] cookies= req.getCookies();
                if (cookies != null) {
                    for (Cookie i : cookies) {
                        if (i.getName().equals("user"))
                        {
                            user=Login_manager.judge_fix_login(i, entityManager, req, resp);
                            break;
                        }
                    }
                    if (user==null)
                    {
                        throw new Shop_exception_not_login();
                    }
                }
            }
            Query query=entityManager.createQuery("FROM Item WHERE user = ?1");
            query.setParameter(1, user);
            List<Item> items=query.getResultList();
            output.items=new long[items.size()];
            for (int i=0; i<items.size(); ++i)
            {
                output.items[i]=items.get(i).id;
            }

            output.ret = 0;
        }
        catch(Shop_exception_login x)
        {
            output.ret = -2;
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

class Output
{
    public int ret;
    public long[] items;
}
