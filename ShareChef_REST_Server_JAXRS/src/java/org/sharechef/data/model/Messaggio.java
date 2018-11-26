package org.sharechef.data.model;

import org.sharechef.data.DataLayerException;


public interface Messaggio {
    
    int getId();
    
    String getTesto();
    
    String getData();
    
    String getOra();
    
    Utente getMittente();
    
    int getIdMittente();
    
    Evento getEvento();
    
    int getIdEvento();
    
    void setTesto(String testo);
    
    void setData(String data);
    
    void setOra(String ora);
    
    void setMittente(Utente mittente);
    
    void setEvento(Evento evento);
    
    void setIdEvento(int id_evento);
    
    void setIdMittente(int id_mittente);
        
    void copyFrom(Messaggio messaggio) throws DataLayerException;
    
}
