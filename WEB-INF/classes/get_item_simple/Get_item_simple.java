package get_item_simple;

import Exception.Shop_exception_format;
import Exception.Shop_exception_item_not_found;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

class Input
{
    public long id;
}

@WebServlet("/api/get_item_simple")
public class Get_item_simple extends HttpServlet {
    private static final EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory( "myjpa" );

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("application/json;charset=utf-8");
        Output output=new Output();
        output.ret=-1;
        ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter pw=resp.getWriter();

        try
        {
            Input input;
            try
            {
                input = objectMapper.readValue(req.getReader(), Input.class);
            }
            catch(JsonParseException | JsonMappingException x)
            {
                throw new Shop_exception_format();
            }

            EntityManager entityManager = sessionFactory.createEntityManager();
            try {
                Item item = entityManager.find(Item.class, input.id);
                if (item == null) {
                    throw new Shop_exception_item_not_found();
                }
                output.name = item.name;
                output.cover = item.cover.id;
                output.stock = item.stock;
                output.releaser = item.user.nick_name;
                output.on_market= item.on_market;
            }
            finally {
                entityManager.close();
            }
            output.ret=0;
        }
        catch (Shop_exception_format x)
        {
            output.ret = -3;
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
    public String name;
    public long stock;
    public String releaser;
    public long cover;
    public byte on_market;
}
