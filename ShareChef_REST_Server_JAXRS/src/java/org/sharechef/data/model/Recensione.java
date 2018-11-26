/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sharechef.data.model;

import org.sharechef.data.DataLayerException;

/**
 *
 * @author Luca
 */
public interface Recensione {
    
    int getId();
    
    Utente getAutore();
    
    int getIdAutore();
    
    Evento getEvento();
    
    int getIdEvento();
    
    String getData();
    
    String getOra();
    
    String getTesto();
    
    int getVoto();
    
    String getImmagine();
    
    void setAutore(Utente utente);
    
    void setData(String data);
    
    void setOra(String ora);
    
    void setTesto(String testo);
    
    void setVoto(int voto);
    
    void setImmagine(String immagine);
    
    void setEvento(Evento evento);
    
    void setIdEvento(int id_evento);
    
    void setIdAutore(int id_creatore);
        
    void copyFrom(Recensione recensione) throws DataLayerException;    
}
