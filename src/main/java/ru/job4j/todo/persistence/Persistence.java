package ru.job4j.todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.function.Function;

public class Persistence implements AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    private Persistence() { }

    private static class Holder {
        private static final Persistence INSTANCE = new Persistence();
    }

    public static Persistence getInstance() {
        return Holder.INSTANCE;
    }

    public void save(User user) {
        tx(session -> session.save(user));
    }

    public void save(Item item, String[] catIds) {
        Session session = sf.openSession();
        session.beginTransaction();
        for (String el : catIds) {
            Category category = session.find(Category.class, Integer.parseInt(el));
            item.addCategory(category);
        }
        session.save(item);
        session.getTransaction().commit();
        session.close();
    }

    public List<Item> showAllItems() {
        return tx(session -> session.createQuery("select distinct i from Item i join fetch i.categories").list());
    }

    public void modifyDone(int id) {
        Item item = tx(session -> session.find(Item.class, id));
        item.setDone(!item.isDone());
        tx(session -> {
            session.update(item);
            return true;
        });
    }

    public User findByEmail(String email) {
        User user = null;
        Session session = sf.openSession();
        session.beginTransaction();
        try {
            user = (User) session
                    .createQuery("from ru.job4j.todo.model.User where email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return user;
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return user;
    }

    public List<Category> showAllCategories() {
        return tx(session -> session.createQuery("from ru.job4j.todo.model.Category").list());
    }

    private <T> T tx(Function<Session, T> command) {
        Session session = sf.openSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            T result = command.apply(session);
            tx.commit();
            return result;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void close() {
        sf.close();
    }
}
