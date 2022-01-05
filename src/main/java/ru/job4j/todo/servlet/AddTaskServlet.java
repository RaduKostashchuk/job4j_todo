package ru.job4j.todo.servlet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.persistence.Persistence;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddTaskServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Item item = new Item();
        item.setDescription(req.getParameter("description"));
        SessionFactory sf = Persistence.getInstance().getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        resp.sendRedirect("index.jsp");
    }
}
