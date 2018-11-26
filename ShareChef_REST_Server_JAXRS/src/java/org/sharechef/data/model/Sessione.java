package org.sharechef.data.model;

import org.sharechef.data.DataLayerException;


public interface Sessione {
    
    int getId();
    
    String getToken();
    
    Utente getUtente();
    
    int getIdUtente();
    
    void setToken(String token);
    
    void setUtente(Utente utente);
    
    void setIdUtente(int id_utente);
        
    void copyFrom(Sessione sessione) throws DataLayerException;    
}
