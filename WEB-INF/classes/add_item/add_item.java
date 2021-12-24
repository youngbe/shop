package add_item;

import Exception.Shop_exception_format;
import Exception.Shop_exception_not_login;
import Login.Login_manager;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.File_pool;
import db.Item_Image;
import db.Item;
import db.User;
import jakarta.persistence.*;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.PrintWriter;
import java.io.IOException;

@WebServlet(value="/api/add_item")
@MultipartConfig
public class add_item extends HttpServlet
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


            // 读取参数
            final String name = req.getParameter("name");
            if (name==null)
            {
                throw new Shop_exception_format();
            }
            final long stock;
            final double price;
            final int num;
            try {
                stock = Long.parseLong(req.getParameter("stock"));
                price = Double.parseDouble(req.getParameter("price"));
                num = Integer.parseInt(req.getParameter("files_num"));
            }
            catch(NumberFormatException x)
            {
                throw new Shop_exception_format();
            }
            if (num==0)
            {
                throw new Shop_exception_format();
            }
            final Part cover=req.getPart("cover");
            if (cover==null)
            {
                throw new Shop_exception_format();
            }
            final Part[] part = new Part[num];
            for (int i = 0; i < num; i++) {
                part[i] = req.getPart("file" + i);
                if (part[i] == null) {
                    throw new Shop_exception_format();
                }
            }

            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            try
            {
                Item new_item = new Item();
                new_item.name = name;
                new_item.stock = stock;
                new_item.price = price;
                new_item.user=user;
                new_item.on_market=0;
                File_pool cover_file = new File_pool();
                entityManager.persist(cover_file);
                try {
                    cover.write(File_pool.path + cover_file.id);
                }
                catch(IOException x)
                {
                    System.out.println("异常发生：写入磁盘失败！");
                    throw x;
                }
                new_item.cover=cover_file;
                entityManager.persist(new_item);
                for (Part i : part) {
                    File_pool new_img_file = new File_pool();
                    entityManager.persist(new_img_file);
                    try {
                        i.write(File_pool.path + new_img_file.id);
                    }
                    catch(IOException x)
                    {
                        System.out.println("异常发生：写入磁盘失败！");
                        throw x;
                    }
                    Item_Image new_img = new Item_Image();
                    new_img.img = new_img_file;
                    new_img.item = new_item;
                    entityManager.persist(new_img);
                }
                entityTransaction.commit();
            }
            catch (Exception x)
            {
                entityTransaction.rollback();
                throw x;
            }
            output.ret = 0;
        }
        catch(Shop_exception_not_login x)
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

class Output
{
    public int ret;
}
