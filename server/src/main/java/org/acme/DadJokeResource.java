package org.acme;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/joke/v2")
public class DadJokeResource {
    List<DadJoke> jokes = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DadJoke> getAll() {
        return jokes;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addJoke(DadJoke joke) {
        jokes.add(joke);
        return "OK";
    }

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DadJoke> search(@QueryParam("q") @DefaultValue("") String searchTerm) {
        return jokes.stream().filter(joke -> joke.joke().contains(searchTerm)).toList();
    }
}
