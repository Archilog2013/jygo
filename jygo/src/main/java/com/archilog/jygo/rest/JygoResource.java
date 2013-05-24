package com.archilog.jygo.rest;

import com.archilog.jygo.Client;
import com.archilog.jygo.JygoServer;
import static com.archilog.jygo.JygoServer.launchCommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/go")
public class JygoResource {
    private static HashMap<Client, JygoServer>  players = new HashMap<Client, JygoServer>();
    
    public Client checkPlayer(int id) {
        for (Client c : players.keySet()) {
            if (id == c.getId())
                return c;
        }
        return null;
    }
    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String authenticate(@FormParam("name") String name) {
        Client player = new Client(name);
        players.put(player, null);
        StringBuilder sb = new StringBuilder();
        return sb.append("{'id': ").append(player.getId()).append("}").toString();
    }
    
    @POST
    @Path("/launch_game")
    @Produces(MediaType.TEXT_HTML)
    public void launchGame(@FormParam("id") int id) {
        Client player = checkPlayer(id);
        if (player != null) {
            JygoServer js = new JygoServer(id);
            players.put(player, js);
        }
    }
    
    @GET
    @Path("/command/{cmd}")
    @Produces(MediaType.TEXT_HTML)
    public String getResponse(@FormParam("id") int id, @PathParam("cmd") String cmd) {
        Client player = checkPlayer(id);
        if (player != null) {
            return launchCommand(cmd);
        }
        return "NON.";
    }
    
    @GET
    @Path("/players")
    @Produces(MediaType.TEXT_HTML)
    public String getAllPlayers() {
        Set keys = players.keySet();
        Iterator it = keys.iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            Client key = (Client)it.next();
            sb.append("{'pseudo': ").append(key.getPseudo()).append("}");
        }
        return sb.toString();
    }
}
