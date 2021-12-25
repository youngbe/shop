package search_items;

import Exception.Shop_exception_format;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/search_items")
public class Search_items extends HttpServlet {
    private static final EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory( "myjpa" );

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        Output output = new Output();
        output.ret = -1;
        ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter pw = resp.getWriter();
        try {
            final String key = req.getParameter("key");
            if(key ==null)
            {
                throw new Shop_exception_format();
            }
            StringBuilder fixed_key = new StringBuilder(key);
            for (int i=0; i<fixed_key.length(); ++i)
            {
                if (fixed_key.charAt(i)=='\\' || fixed_key.charAt(i) == '%' || fixed_key.charAt(i) == '_')
                {
                    fixed_key.insert(i,'\\');
                    ++i;
                }
            }
            EntityManager entityManager = sessionFactory.createEntityManager();
            try {
                System.out.println("dsfdsf");
                Query query = entityManager.createQuery("FROM Item WHERE name LIKE ?1 ESCAPE '\\' AND on_market = 0 ");
                System.out.println("dffffff");
                System.out.println('%'+fixed_key.toString()+'%');
                query.setParameter(1, '%'+fixed_key.toString()+'%');
                System.out.println("asdsa");
                List<Item> items = query.getResultList();
                output.items=new long[items.size()];
                for (int i=0; i<items.size(); ++i) {
                    output.items[i]=items.get(i).id;
                }
            } finally {
                entityManager.close();
            }

            output.ret = 0;
        }
        catch (Shop_exception_format x)
        {
            output.ret=-3;
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
    public long[] items;
}