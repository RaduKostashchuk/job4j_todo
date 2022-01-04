package ru.job4j.todo.servlet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModifyDoneServlet extends HttpServlet {
    private SessionFactory sf;

    @Override
    public void init() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        this.sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.find(Item.class, id);
        item.setDone(!item.isDone());
        session.update(item);
        session.getTransaction().commit();
        session.close();
    }
}
