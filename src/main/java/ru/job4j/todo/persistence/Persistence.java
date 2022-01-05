package ru.job4j.todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

import java.util.List;

public class Persistence {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    private Persistence() { }

    private static class Holder {
        private static final Persistence INSTANCE = new Persistence();
    }

    public static Persistence getInstance() {
        return Holder.INSTANCE;
    }

    public void save(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
    }

    public List<Item> showAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.todo.model.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public void modifyDone(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.find(Item.class, id);
        item.setDone(!item.isDone());
        session.update(item);
        session.getTransaction().commit();
        session.close();
    }
}
