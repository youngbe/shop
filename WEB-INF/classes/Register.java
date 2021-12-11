import jakarta.persistence.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;


@WebServlet(value="/api/regist", loadOnStartup = 0)
public class Register extends HttpServlet
{
    private static EntityManagerFactory sessionFactory;
    static
    {
        sessionFactory = Persistence.createEntityManagerFactory( "myjpa" );
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        try {
            ObjectMapper objectMapper=new ObjectMapper();
            Input input=null;
            try {
                input = objectMapper.readValue(req.getReader(), Input.class);
            }
            catch(Exception  y)
            {
                System.out.printf("error obj\n");
                return;
            }

            Output output=new Output();
            EntityManager entityManager=sessionFactory.createEntityManager();
            EntityTransaction entityTransaction =entityManager.getTransaction();
            entityTransaction.begin();
            {
                User new_user= new User();
                new_user.nick_name=input.nick_name;
                new_user.password= input.password;
                entityManager.persist(new_user);
                output.id= new_user.id;
                output.ret=0;
            }
            entityTransaction.commit();
            entityManager.close();

            resp.setContentType("application/json;charset=utf-8");
            objectMapper.writeValue(resp.getWriter(), output);
        }
        catch(Exception x)
        {
            System.out.printf("Error\n");
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


