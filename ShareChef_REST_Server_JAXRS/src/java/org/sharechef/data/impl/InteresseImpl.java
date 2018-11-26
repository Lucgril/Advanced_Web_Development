package org.sharechef.data.impl;

import org.sharechef.data.DataLayerException;
import org.sharechef.data.model.Interesse;
import org.sharechef.data.model.ShareChefDataLayer;


public class InteresseImpl implements Interesse {
    
    int id;
    private String nome;
    private ShareChefDataLayer ownerdatalayer;
    
    public InteresseImpl(ShareChefDataLayer ownerdatalayer) {
        
        this.ownerdatalayer = ownerdatalayer;
        this.id = 0;
        this.nome = "";
    }
    
    public InteresseImpl() {
        
        this.ownerdatalayer = ownerdatalayer;
        this.id = 0;
        this.nome = "";
    }
    
    
    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    protected void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;    
    }
    
    @Override
    public void copyFrom(Interesse interesse) throws DataLayerException {
        id = interesse.getId();
        nome = interesse.getNome();
    }   
}
