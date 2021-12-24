package get_file;

import Exception.Shop_exception_format;
import Exception.Shop_exception_item_not_found;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.File_pool;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;


@WebServlet("/api/get_file")
public class Get_file extends HttpServlet {
    private static final EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory( "myjpa" );

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("application/octet-stream");
        ServletOutputStream outputStream=resp.getOutputStream();
        try
        {

            long id=Long.parseLong(req.getParameter("id"));
            FileInputStream fileInputStream;
            EntityManager entityManager = sessionFactory.createEntityManager();
            try {
                File_pool item = entityManager.find(File_pool.class, id);
                if (item == null) {
                    throw new Shop_exception_item_not_found();
                }
                fileInputStream = new FileInputStream(File_pool.path + item.id);
            }
            finally {
                entityManager.close();
            }
            int i;
            while ( (i=fileInputStream.read())!=-1 )
            {
                outputStream.write(i);
            }
            fileInputStream.close();
            outputStream.close();
        }
        catch(Throwable x)
        {
            return;
        }
    }


    public void destroy() {
        sessionFactory.close();
        super.destroy();
    }
}
