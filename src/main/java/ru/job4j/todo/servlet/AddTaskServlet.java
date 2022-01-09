package ru.job4j.todo.servlet;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
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
        User user = (User) req.getSession().getAttribute("user");
        item.setDescription(req.getParameter("description"));
        item.setUser(user);
        Persistence.getInstance().save(item, req.getParameterValues("catIds"));
        resp.sendRedirect("index.jsp");
    }
}
