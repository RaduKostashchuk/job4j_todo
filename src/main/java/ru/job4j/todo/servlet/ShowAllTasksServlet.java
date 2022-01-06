package ru.job4j.todo.servlet;

import org.json.JSONArray;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.persistence.Persistence;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ShowAllTasksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Item> items = Persistence.getInstance().showAll();
        items.forEach(i -> i.getUser().setPassword(""));
        JSONArray json = new JSONArray(items);
        String str = json.toString();
        req.setAttribute("items", items);
        resp.setContentType("application/json");
        OutputStream out = resp.getOutputStream();
        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
