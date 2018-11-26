package org.sharechef.controller;


import java.sql.SQLException;
import javax.naming.NamingException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.sharechef.data.DataLayerException;
import org.sharechef.data.impl.UtenteImpl;
import org.sharechef.data.model.Sessione;
import org.sharechef.data.model.Utente;


@Path("auth")

public class RepoResource extends ShareChefBaseController {
    
    
    public RepoResource() throws NamingException, SQLException, DataLayerException {
        super();
    }
       
    @POST
    @Path("login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Context UriInfo context, UtenteImpl utente) throws SQLException, NamingException, DataLayerException {
        Sessione sessione = datalayer.login(utente.getEmail(), utente.getPassword());
        datalayer.destroy();
        if(sessione != null) {
            return Response.ok(sessione.getToken()).build();
        } else {
            return Response.status(403).build();
        }
    }
    
    @POST
    @Path("logout/{SID}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response logout(@Context UriInfo context, @PathParam("SID") String token) throws SQLException, NamingException, DataLayerException {
        datalayer.logout(token);
        datalayer.destroy();
        return Response.ok().build();
    }
    
    @POST
    @Path("utenti")
    @Produces({MediaType.APPLICATION_JSON})
    public Response registrazione(@Context UriInfo context, UtenteImpl utente) throws SQLException, NamingException, DataLayerException {
        try {
            Utente utente2 = datalayer.storeUtente(utente);
            Sessione sessione = datalayer.registrazione(utente2);
            datalayer.destroy();
            if (sessione != null) return Response.ok(sessione.getToken()).build();
            return Response.status(403).build();
        } catch(DataLayerException ex) {
        return Response.serverError().entity(ex.getMessage()).build();
        }
    }
    
    @Path("{SID}/utenti")
    public UtenteResource getUtente() throws SQLException, NamingException, DataLayerException {
        return new UtenteResource(datalayer);
    }
    
    @Path("{SID}/eventi")
    public EventoResource getEventi() throws SQLException, NamingException, DataLayerException {
        return new EventoResource(datalayer);
    }
}