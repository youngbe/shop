package get_random_item;

import Exception.Shop_exception_format;
import Exception.Shop_exception_item_not_found;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.File_pool;
import db.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

@WebServlet("/api/get_random_items")
public class Get_random_items extends HttpServlet {
    private static final EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory( "myjpa" );

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        Output output = new Output();
        output.ret = -1;
        ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter pw = resp.getWriter();
        try {
            final int num;
            try {
                num = Integer.parseInt(req.getParameter("num"));
            }
            catch (NumberFormatException x) {
                throw new Shop_exception_format();
            }
            EntityManager entityManager = sessionFactory.createEntityManager();
            try {
                Query query = entityManager.createQuery("FROM Item WHERE on_market = 0");
                query.setMaxResults(num);
                List<Item> items = query.getResultList();
                output.items=new long[items.size()];
                for (int i=0; i<items.size(); i++) {
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