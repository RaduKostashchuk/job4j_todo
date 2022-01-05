package ru.job4j.todo.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

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

    public SessionFactory getSessionFactory() {
        return sf;
    }
}
