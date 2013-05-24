/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.archilog.jygo.util;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author det0x
 */
@Path("version")
public class VersionResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of VersionResource
     */
    public VersionResource() {
    }


    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

}
