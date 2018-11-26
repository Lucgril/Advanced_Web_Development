package org.sharechef.controller;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.sharechef.data.DataLayerException;
import org.sharechef.data.impl.RecensioneImpl;
import org.sharechef.data.impl.UtenteImpl;
import org.sharechef.data.model.Evento;
import org.sharechef.data.model.Recensione;
import org.sharechef.data.model.Sessione;
import org.sharechef.data.model.ShareChefDataLayer;


public class UtenteResource {
    private ShareChefDataLayer datalayer;
    
    UtenteResource(ShareChefDataLayer datalayer) throws DataLayerException {
        this.datalayer = datalayer;
    }
    
@GET
@Path("{ID: [0-9]+}")
@Produces({MediaType.APPLICATION_JSON})
public Response profilo(@PathParam("SID") String token, @PathParam("ID") int id) throws DataLayerException {
    try {
        Sessione sessione = datalayer.getSessioneByToken(token);
        if (sessione != null) {
            return Response.ok(datalayer.getUtente(id)).build();
        } 
        else return Response.status(403).build();
    }
        catch(DataLayerException ex) {
            return Response.serverError().entity(ex.getMessage()).build();
    }
}

@PUT
@Path("{ID: [0-9]+}")
@Produces({MediaType.APPLICATION_JSON})
public Response aggiornaProfilo(@PathParam("SID") String token, @PathParam("ID") int id, UtenteImpl utente) throws DataLayerException {
    boolean result;
    try {
        utente.setId(id);
        result = datalayer.updateUtente(token, utente);
        datalayer.destroy();
        if (result) return Response.ok().build();
        else return Response.status(403).build();
    } catch(DataLayerException ex) {
        return Response.serverError().entity(ex.getMessage()).build();
    }
}

@POST
@Path("{IDUTENTE: [0-9]+}/eventi/{ID}/recensioni")
@Produces({MediaType.APPLICATION_JSON})
public Response addRecensione(@PathParam("SID") String token, @PathParam("IDUTENTE") int id_utente, @PathParam("ID") int id_evento, RecensioneImpl recensione) throws DataLayerException {
    boolean result;
    try {
        result = datalayer.storeRecensione(token, recensione, id_utente, id_evento);
        datalayer.destroy();
        if (result) return Response.ok().build();
        else Response.status(403).build();
    } catch(DataLayerException ex) {
        return Response.serverError().entity(ex.getMessage()).build();
    }
        return null;
}

@GET
@Path("{ID: [0-9]+}/recensioni")
@Produces({MediaType.APPLICATION_JSON})
public Response getRecensioni(@PathParam("SID") String token, @PathParam("ID") int id) throws DataLayerException {
    try {
        List<Recensione> result = new ArrayList();
        result = datalayer.getRecensioniByUtente(token, id);
        datalayer.destroy();
        if (result != null) return Response.ok(result).build();
        else return Response.status(403).build();
    } catch(DataLayerException ex) {
        return Response.serverError().entity(ex.getMessage()).build();
    }
}

@GET
@Path("{ID: [0-9]+}/recensioni/{ID: [0-9]+}")
@Produces({MediaType.APPLICATION_JSON})
public Response getRecensione(@PathParam("SID") String token, @PathParam("ID") int id_utente, @PathParam("ID") int id_recensione) throws DataLayerException {
    try {
        Recensione result;
        result = datalayer.getRecensioneByUtente(token, id_utente, id_recensione);
        datalayer.destroy();
        if (result != null ) return Response.ok(result).build();
        else return Response.status(403).build();
    } catch(DataLayerException ex) {
        return Response.serverError().entity(ex.getMessage()).build();
    }
}

@GET
@Path("{ID: [0-9]+}/eventi")
@Produces({MediaType.APPLICATION_JSON})
public Response getEventi(@PathParam("SID") String token, @PathParam("ID") int id_utente) throws DataLayerException {
    try {
        List<Evento> result = new ArrayList();
        result = datalayer.getEventiByUtente(token, id_utente);
        datalayer.destroy();
        if (result != null) return Response.ok(result).build();
        else return Response.status(403).build();
    } catch(DataLayerException ex) {
        return Response.serverError().entity(ex.getMessage()).build();
    }
}

@GET
@Path("{ID: [0-9]+}/partecipazioni")
@Produces({MediaType.APPLICATION_JSON})
public Response getPartecipazioni(@PathParam("SID") String token, @PathParam("ID") int id_utente) throws DataLayerException {
    try {
        List<Evento> result = new ArrayList();
        result = datalayer.getEventiPartecipati(token, id_utente);
        datalayer.destroy();
        if (result != null) return Response.ok(result).build();
        else return Response.status(403).build();
    } catch(DataLayerException ex) {
        return Response.serverError().entity(ex.getMessage()).build();
    }
}

}

