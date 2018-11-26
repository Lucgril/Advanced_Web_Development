package org.sharechef.data.model;

import org.sharechef.data.DataLayerException;


public interface Interessa {
    
    int getId();
    
    Utente getUtente();
    
    int getIdUtente();
    
    Interesse getInteresse();
    
    int getIdInteresse();
    
    void setUtente(Utente utente);
    
    void setInteresse(Interesse interesse);
    
    void setIdInteresse(int id_interesse);
    
    void setIdUtente(int id_utente);
        
    void copyFrom(Interessa interessa) throws DataLayerException;
    
}
