import db.User;
import jakarta.persistence.*;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;

@WebServlet(value="/ig")
public class MyServer2 extends HttpServlet
{
    private static EntityManagerFactory sessionFactory;
    static
    {
        sessionFactory = Persistence.createEntityManagerFactory( "myjpa" );
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        System.out.printf("doPost\n");
        System.out.printf(req.getParameter("email"));
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //System.out.printf("doGet\n");
        PrintWriter pw=resp.getWriter();
        EntityManager entityManager=sessionFactory.createEntityManager();
        EntityTransaction entityTransaction =entityManager.getTransaction();
        entityTransaction.begin();



        Query query= entityManager.createQuery("from User where id > 1");
        List<User> customerList=query.getResultList();
        for (User x: customerList)
        {
            pw.printf("%s\n", x.nick_name);
        }

        pw.printf("ignb!\n");


        entityTransaction.commit();
        entityManager.close();
    }

    @Override
    public void destroy() {
        sessionFactory.close();
        super.destroy();
    }
}
