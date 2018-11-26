package org.sharechef.data.impl;

import org.sharechef.data.DataLayerException;
import org.sharechef.data.model.Sessione;
import org.sharechef.data.model.ShareChefDataLayer;
import org.sharechef.data.model.Utente;


public class SessioneImpl implements Sessione {
    
    private int id, id_utente;
    private String token;
    private Utente utente;
    private ShareChefDataLayer ownerdatalayer;
    
    public SessioneImpl(ShareChefDataLayer ownerdatalayer) {
        
        this.id = 0;
        this.ownerdatalayer = ownerdatalayer;
        this.token = "";
        this.utente = null;
        this.id_utente = 0;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public Utente getUtente() {
        return utente;
    }
    
    @Override
    public int getIdUtente() {
        return id_utente;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @Override
    public void copyFrom(Sessione sessione) throws DataLayerException {
        id = sessione.getId();
        token = sessione.getToken();
        id_utente = sessione.getIdUtente();
    }
    
    protected void setId(int id) {
        this.id = id;
    }
    
    @Override
    public void setIdUtente(int id_utente) {
        this.id_utente = id_utente;
        this.utente = null;
    }
    
}
