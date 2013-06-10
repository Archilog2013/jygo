package com.archilog.jygo.rest;

import com.archilog.jygo.Client;
import com.archilog.jygo.JygoServer;
import static com.archilog.jygo.JygoServer.launchCommand;
import com.archilog.jygo.data.Player;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;


@Stateless
@Path("/go")
public class JygoResource {
    private static HashMap<Player, JygoServer>  players = new HashMap<Player, JygoServer>();
    
    public Player checkPlayer(int id) {
        for (Player p : players.keySet()) {
            if (id == p.getId())
                return p;
        }
        return null;
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String authenticate(final String input) {
        ObjectMapper mapper = new ObjectMapper();
        LoginBean login = null;
        try {
            login = mapper.readValue(input, LoginBean.class);
        } catch (IOException ex) {
            Logger.getLogger(JygoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Player player = new Player(login.login);
        players.put(player, null);
        StringBuilder sb = new StringBuilder();
        return sb.append("{\"id\": ").append(player.getId()).append("}").toString();
    }
    
    @POST
    @Path("/launch_game")
    @Consumes(MediaType.APPLICATION_JSON)
    public String launchGame(final String input) {
        ObjectMapper mapper = new ObjectMapper();
        LaunchGameBean launchGame = null;
        try {
            launchGame = mapper.readValue(input, LaunchGameBean.class);
        } catch (IOException ex) {
            Logger.getLogger(JygoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Player player = checkPlayer(launchGame.id);
        if (player != null) {
            JygoServer js = new JygoServer(launchGame.id);
            players.put(player, js);
        }
        StringBuilder sb = new StringBuilder();
        return sb.append("{\"id\": ").append(player.getId()).append("}").toString();
    }
    
    @GET
    @Path("/command/{cmd}")
    @Produces(MediaType.TEXT_HTML)
    public String getResponse(@FormParam("id") int id, @PathParam("cmd") String cmd) {
        Player player = checkPlayer(id);
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
            Player key = (Player)it.next();
            sb.append("{'Tag': ").append(key.getTag()).append("}");
        }
        return sb.toString();
    }
}
