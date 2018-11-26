package org.sharechef.data.impl;

import org.sharechef.data.DataLayerException;
import org.sharechef.data.model.Evento;
import org.sharechef.data.model.Partecipare;
import org.sharechef.data.model.ShareChefDataLayer;
import org.sharechef.data.model.Utente;


public class PartecipareImpl implements Partecipare{
    
    private int id, id_utente, id_evento;
    private Utente utente;
    private Evento evento;
    private ShareChefDataLayer ownerdatalayer;
    
    public PartecipareImpl(ShareChefDataLayer ownerdatalayer) {
        
        this.id = 0;
        this.ownerdatalayer = ownerdatalayer;
        this.utente = null;
        this.id_utente = 0;
        this.evento = null;
        this.id_evento = 0;
    }
    
    
    @Override
    public Utente getUtente() {
        return utente;
    }

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @Override
    public Evento getEvento() {
        return evento;
    }

    @Override
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    protected void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;    
    }
    
    @Override
    public int getIdEvento() {
        return id_evento;
    }
    
    @Override
    public void setIdEvento(int id_evento) {
        this.id_evento = id_evento;
        this.evento = null;
    }
    
    @Override
    public int getIdUtente() {
        return id_utente;
    }
    
    @Override
    public void setIdUtente(int id_utente) {
        this.id_utente= id_utente;
        this.utente = null;
    }
    
    @Override
    public void copyFrom(Partecipare partecipare) throws DataLayerException {
        id_utente = partecipare.getIdUtente();
        id_evento = partecipare.getIdEvento();
    }
}
