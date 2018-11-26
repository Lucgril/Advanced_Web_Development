package org.sharechef.data;


public interface DataLayer extends AutoCloseable {
    
    void init() throws DataLayerException;

    void destroy() throws DataLayerException;
    
}
