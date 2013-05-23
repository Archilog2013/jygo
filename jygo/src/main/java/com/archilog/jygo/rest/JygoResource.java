package com.archilog.jygo.rest;

import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Stateless
@Path("/go")
public class JygoResource {
    @GET
    @Path("{cmd}")
    @Produces("text/html")
    public String getResponse(@PathParam("cmd") String cmd) {
        return "<html><body><h1>Command : "+cmd+"</h1></body></html>";
    }
}
