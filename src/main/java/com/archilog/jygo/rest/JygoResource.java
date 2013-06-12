package com.archilog.jygo.rest;

import com.archilog.jygo.JygoServer;
import com.archilog.jygo.data.Player;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


@Stateless
@Path("/go")
public class JygoResource {
    private static HashMap<Player, JygoServer> players = new HashMap<Player, JygoServer>();
    
    public Player checkPlayer(int id) {
        Player thePlayer = new Player();
        for (Player p : players.keySet()) {
            if (id == p.getId()) {
                thePlayer = p;
            }
        }
        return thePlayer;
    }
    
    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
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
        JSONObject jObj;
        int cases = 10;
        try {
            jObj = new JSONObject(input);
            cases = Integer.valueOf(jObj.get("cases").toString());
        } catch (JSONException ex) {
            Logger.getLogger(JygoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
            String test = players.get(player).launchCommand(js.getIncrement() + " boardsize " + cases);
            js.setIncrement(js.getIncrement() + 1);
            String test2 = players.get(player).launchCommand(js.getIncrement() + " clear_board");
            js.setIncrement(js.getIncrement() + 1);
        }
        StringBuilder sb = new StringBuilder();
        return sb.append("{\"id\": ").append(player.getId()).append("}").toString();
    }
    
    @POST
    @Path("/command")
    @Produces(MediaType.TEXT_HTML)
    public String getResponse(final String input) {
        JSONObject jObj;
        try {
            jObj = new JSONObject(input);
            Boolean amoi = Boolean.valueOf(jObj.get("human").toString());
            Player player = checkPlayer(Integer.valueOf(jObj.get("id").toString()));
            if (player != null) {
                String command;
                System.out.println(amoi);
                if (amoi == Boolean.TRUE) {
                    int x = Integer.valueOf(jObj.get("x").toString()) + 1;
                    int y = Integer.valueOf(jObj.get("y").toString()) + 1;
                    if (x >= 9) x++;
                    String coord = getCharForNumber(x) + y;
                    System.out.println(coord);
                    command = players.get(player).launchCommand(players.get(player).getIncrement() + " play black " + coord);
                } else {
                    command = players.get(player).launchCommand(players.get(player).getIncrement() + " genmove white");
                }
                players.get(player).setIncrement(players.get(player).getIncrement() + 1);
                System.out.println(command);
                return command;
            }
        } catch (JSONException ex) {
            Logger.getLogger(JygoResource.class.getName()).log(Level.SEVERE, null, ex);
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
