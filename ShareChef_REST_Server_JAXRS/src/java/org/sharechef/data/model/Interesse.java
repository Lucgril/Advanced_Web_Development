package org.sharechef.data.model;

import org.sharechef.data.DataLayerException;


public interface Interesse {
    
    int getId();
    
    String getNome();
    
    void setNome(String nome);
    
    void copyFrom(Interesse interesse) throws DataLayerException;
    
}
