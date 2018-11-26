package org.sharechef.data.model;

import org.sharechef.data.DataLayerException;


public interface Partecipare {
    
    int getId();
    
    Utente getUtente();
    
    int getIdUtente();
    
    Evento getEvento();
    
    int getIdEvento();
    
    void setUtente(Utente utente);
    
    void setEvento(Evento evento);
    
    void setIdEvento(int id_evento);
    
    void setIdUtente(int id_utente);
        
    void copyFrom(Partecipare partecipare) throws DataLayerException;    
}
