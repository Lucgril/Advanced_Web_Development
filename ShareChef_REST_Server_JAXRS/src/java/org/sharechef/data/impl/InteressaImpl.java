package org.sharechef.data.impl;


import org.sharechef.data.DataLayerException;
import org.sharechef.data.model.Interessa;
import org.sharechef.data.model.Interesse;
import org.sharechef.data.model.ShareChefDataLayer;
import org.sharechef.data.model.Utente;


public class InteressaImpl implements Interessa {
    
    private int id, id_utente, id_interesse;
    private Utente utente;
    private Interesse interesse;
    private ShareChefDataLayer ownerdatalayer;
    
    public InteressaImpl(ShareChefDataLayer ownerdatalayer) {
        
        this.id = 0;
        this.ownerdatalayer = ownerdatalayer;
        this.utente = null;
        this.id_interesse = 0;
        this.interesse = null;
        this.id_interesse = 0;
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
    public void setUtente(Utente utente) {
        this.utente = utente;
    }
    
    @Override
    public void setIdUtente(int id_utente) {
        this.id_utente = id_utente;
    }

    @Override
    public Interesse getInteresse() {
        return interesse;
    }
    
    @Override
    public int getIdInteresse() {
        return id_interesse;
    }

    @Override
    public void setInteresse(Interesse interesse) {
        this.interesse = interesse;
    }
    
    protected void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;    
    }
    
    @Override
    public void setIdInteresse(int id_interesse) {
        this.id_interesse = id_interesse;
        this.interesse = null;
    }
    
    
    
    @Override
    public void copyFrom(Interessa interessa) throws DataLayerException {
        id_utente = interessa.getIdUtente();
        id_interesse = interessa.getIdInteresse();
    }
   
}
