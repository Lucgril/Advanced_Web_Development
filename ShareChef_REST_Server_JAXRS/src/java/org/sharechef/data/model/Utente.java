package org.sharechef.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import org.sharechef.data.DataLayerException;


public interface Utente {
    
    int getId();
    
    String getImmagine();

    String getNome();

    String getCognome();
    
    String getCittà();
    
    String getProvincia();
    
    String getEmail();
    
    String getPassword();
    
    Date getDataNascita();
    
    String getTelefono();
    
    void setId(int id);
    
    void setImmagine(String immagine);

    void setNome(String nome);

    void setCognome(String cognome);
    
    void setCittà(String città);
    
    void setProvincia(String provincia);
    
    void setEmail(String email);
    
    void setPassword(String password);
    
    void setDataNascita(Date data_nascita);
    
    void setTelefono(String telefono);

    void copyFrom(Utente utente) throws DataLayerException;
}
