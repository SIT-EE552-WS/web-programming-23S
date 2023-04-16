package org.acme;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/joke")
public class DadJokeServlet extends HttpServlet {
    List<DadJoke> jokes = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        super.init();
        DadJoke dadJoke = new DadJoke(
                "123",
                "Did you know that protons have mass? I didn't even know they were catholic."
        );
        jokes.add(dadJoke);
    }

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            String json = jsonb.toJson(jokes);
            resp.setContentType("application/json");

            resp.getWriter().println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            DadJoke dadJoke = jsonb.fromJson(req.getInputStream(), DadJoke.class);
            jokes.add(dadJoke);
            resp.getWriter().println("OK");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
