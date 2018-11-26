package org.sharechef.controller;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.sharechef.data.DataLayerException;
import org.sharechef.data.impl.EventoImpl;
import org.sharechef.data.impl.MessaggioImpl;
import org.sharechef.data.model.Evento;
import org.sharechef.data.model.Messaggio;
import org.sharechef.data.model.Sessione;
import org.sharechef.data.model.ShareChefDataLayer;
import org.sharechef.data.model.Utente;


public class EventoResource {
    
    private ShareChefDataLayer datalayer;
    
    EventoResource(ShareChefDataLayer datalayer) throws DataLayerException {
        this.datalayer = datalayer;
    }
        
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response addEvento(@PathParam("SID") String token, EventoImpl evento) throws DataLayerException {
        boolean result;
        try {
            result = datalayer.storeEvento(token, evento);
            datalayer.destroy();
            if (result) return Response.ok().build();
            else return Response.status(403).build();
        } catch(DataLayerException ex) {
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }  
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getEventi(@PathParam("SID") String token, @QueryParam("prezzo") Double prezzo, @QueryParam("tipo") String tipo) throws DataLayerException {
        List<Evento> result = new ArrayList();
        Sessione sessione = datalayer.getSessioneByToken(token);
        if (sessione != null) {
            try {
                if(prezzo != null) {
                    result = datalayer.getEventiByPrezzo(prezzo);
                    
                    if(tipo != null) {
                        for(Evento evento : result) {
                            if(!evento.getTipo().equals(tipo)) result.remove(evento);
                        }
                    }
                } else {
                    if(tipo != null) {
                        result = datalayer.getEventiByTipo(tipo);
                    }
                }
                if(prezzo == null  && tipo == null) {
                    result = datalayer.getEventi();
                }
                datalayer.destroy();
                return Response.ok(result).build();
            } catch(DataLayerException ex) {
                return Response.serverError().entity(ex.getMessage()).build();
            }   
        }
        else return Response.status(403).build();
    }  
    
    @GET
    @Path("{ID: [0-9]+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getEvento(@PathParam("SID") String token, @PathParam("ID") int id) throws DataLayerException {
        try {
            Sessione sessione = datalayer.getSessioneByToken(token);
            if (sessione != null) return Response.ok(datalayer.getEvento(id)).build();
            else return Response.status(403).build();
            } catch(DataLayerException ex) {
                return Response.serverError().entity(ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("{ID: [0-9]+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response modificaEvento(@PathParam("SID") String token, @PathParam("ID") int id_evento, EventoImpl evento) throws DataLayerException {
        boolean result;
        try {
            evento.setId(id_evento);
            result = datalayer.updateEvento(token, evento);
            datalayer.destroy();
            if (result) return Response.ok().build();
            else return Response.status(403).build();
            } catch(DataLayerException ex) {
                return Response.serverError().entity(ex.getMessage()).build();
        }
    }
    
    @GET
    @Path("{ID: [0-9]+}/organizzatore")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOrganizzatore(@PathParam("SID") String token, @PathParam("ID") int id_evento) throws DataLayerException {
        try {
            Utente utente;
            utente = datalayer.getUtenteByEvento(token, id_evento);
            datalayer.destroy();
            if (utente != null) return Response.ok(utente).build();
            else return Response.status(403).build();
            } catch(DataLayerException ex) {
                return Response.serverError().entity(ex.getMessage()).build();
        }
    }
    
    @GET
    @Path("{ID: [0-9]+}/messaggi")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMessaggi(@PathParam("SID") String token, @PathParam("ID") int id_evento) throws DataLayerException {
        try {
            List<Messaggio> result = new ArrayList();
            result = datalayer.getMessaggiByEvento(token, id_evento);
            datalayer.destroy();
            if (result != null ) return Response.ok(result).build();
            else return Response.status(403).build();
        } catch(DataLayerException ex) {
            return Response.serverError().entity(ex.getMessage()).build();
        }
    } 
    
    @POST
    @Path("{ID: [0-9]+}/messaggi")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addMessaggio(@PathParam("SID") String token, @PathParam("ID") int id_evento, MessaggioImpl messaggio) throws DataLayerException {
        boolean result;
        try {
            result = datalayer.storeMessaggio(token, id_evento, messaggio);
            datalayer.destroy();
            if (result) return Response.ok().build();
            else return Response.status(403).build();
        } catch(DataLayerException ex) {
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }  
    
    @POST
    @Path("{ID: [0-9]+}/partecipanti")
    @Produces(MediaType.TEXT_PLAIN)
    public String addPartecipazione(@PathParam("SID") String token, @PathParam("ID") int id_evento) throws DataLayerException {
        boolean result; 
        try {
            result = datalayer.storePartecipare(token, id_evento);
            datalayer.destroy();
            if (result) return "La registrazione all'evento è andata a buon fine!";
            else return "Già partecipi a questo evento!";
        } catch(DataLayerException ex) {
            return "Server Error";
        }
    } 
    
    @GET
    @Path("{ID: [0-9]+}/partecipanti")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPartecipanti(@PathParam("SID") String token, @PathParam("ID") int id_evento) throws DataLayerException {
        try {
            List<Utente> result = new ArrayList();
            result = datalayer.getPartecipanti(token, id_evento);
            datalayer.destroy();
            if (result != null) return Response.ok(result).build();
            else return Response.status(403).build();
        } catch(DataLayerException ex) {
            return Response.serverError().entity(ex.getMessage()).build();
        }
    } 
}
