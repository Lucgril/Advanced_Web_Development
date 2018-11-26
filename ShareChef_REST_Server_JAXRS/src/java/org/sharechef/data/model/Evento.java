/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sharechef.data.model;

import java.util.Date;
import org.sharechef.data.DataLayerException;

/**
 *
 * @author Luca
 */
public interface Evento {
    
    int getId();

    String getNome();
    
    String getImmagine();

    String getTipo();
    
    String getOra();
    
    int getEtàMinima();
    
    String getMenù();
    
    int getNumeroInvitati();
    
    String getCittà();
    
    String getVia();
    
    String getDescrizione();
    
    double getPrezzo();
    
    Date getData();
    
    Utente getOrganizzatore();
    
    void setId(int id);
    
    void setImmagine(String immagine);

    void setNome(String nome);

    void setTipo(String tipo);
    
    void setOra(String ora);
    
    void setEtàMinima(int età_minima);
    
    void setMenù(String menù);
        
    void setNumeroInvitati(int numero_invitati);
    
    void setCittà(String città);
    
    void setVia(String via);
    
    void setDescrizione(String descrizione);
    
    void setPrezzo(double prezzo);
    
    void setData(Date data);
    
    int getIdOrganizzatore();
    
    void setOrganizzatore(Utente organizzatore);
    
    void setIdOrganizzatore(int id_organizzatore);
    
    void copyFrom(Evento evento) throws DataLayerException;

    
}
