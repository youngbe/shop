package Register;

import db.*;
import jakarta.persistence.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.databind.ObjectMapper;


@WebServlet(value="/api/regist", loadOnStartup = 0)
public class Register extends HttpServlet
{
    private static EntityManagerFactory sessionFactory;
    static
    {
        sessionFactory = Persistence.createEntityManagerFactory( "myjpa" );
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException
    {
        resp.setContentType("application/json;charset=utf-8");
        Output output=new Output();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            PrintWriter pw=resp.getWriter();
            try {
                Input input = objectMapper.readValue(req.getReader(), Input.class);
                if (input.nick_name == null || input.nick_name.length() > 100) {
                    throw new Exception();
                }
                if (input.password == null || input.password.length() > 100) {
                    throw new Exception();
                }


                EntityManager entityManager = sessionFactory.createEntityManager();
                EntityTransaction entityTransaction = entityManager.getTransaction();
                entityTransaction.begin();
                {
                    User new_user = new User();
                    new_user.nick_name = input.nick_name;
                    new_user.password = input.password;
                    entityManager.persist(new_user);
                    output.id = new_user.id;
                    output.ret = 0;
                }
                entityTransaction.commit();
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
    public String nick_name;
    public String password;
}

class Output
{
    public int ret;
    public Long id;
}


