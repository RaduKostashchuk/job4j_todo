package ru.job4j.todo.servlet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.persistence.Persistence;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModifyDoneServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        SessionFactory sf = Persistence.getInstance().getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.find(Item.class, id);
        item.setDone(!item.isDone());
        session.update(item);
        session.getTransaction().commit();
        session.close();
    }
}
