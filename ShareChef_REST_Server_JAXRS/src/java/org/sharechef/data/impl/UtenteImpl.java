package org.sharechef.data.impl;

import java.util.Date;
import org.sharechef.data.DataLayerException;
import org.sharechef.data.model.ShareChefDataLayer;
import org.sharechef.data.model.Utente;



public class UtenteImpl implements Utente {
    
    private String immagine, nome, cognome, città, provincia, email, password, telefono;
    private int id;
    private Date dataNascita;
    private ShareChefDataLayer ownerdatalayer;
    
    public UtenteImpl(ShareChefDataLayer ownerdatalayer) {
        
        this.ownerdatalayer = ownerdatalayer;
        this.id = 0;
        this.immagine = "";
        this.nome = "";
        this.cognome = "";
        this.città = "";
        this.provincia = "";
        this.email = "";
        this.password = "";
        this.dataNascita = null;
        this.telefono = "";
    }
    
    public UtenteImpl() {
        
        this.ownerdatalayer = ownerdatalayer;
        this.id = 0;
        this.immagine = "";
        this.nome = "";
        this.cognome = "";
        this.città = "";
        this.provincia = "";
        this.email = "";
        this.password = "";
        this.dataNascita = null;
        this.telefono = "";    
    }
    
    @Override
    public String getImmagine() {
        return immagine;
    }
    
    @Override
    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
    
    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getCognome() {
        return cognome;
    }

    @Override
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

   @Override
    public String getCittà() {
        return città;
    }

    @Override
    public void setCittà(String città) {
        this.città = città;
    }

    @Override
    public String getProvincia() {
        return provincia;
    }

    @Override
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

   @Override
    public String getTelefono() {
        return telefono;
    }

    @Override
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    
    @Override
    public Date getDataNascita() {
        return dataNascita;
    }

    @Override
    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }
    
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;    
    }
    
    @Override
    public void copyFrom(Utente utente) throws DataLayerException {
        id = utente.getId();
        immagine = utente.getImmagine();
        nome = utente.getNome();;
        cognome = utente.getCognome();
        città = utente.getCittà();
        provincia = utente.getProvincia();
        email = utente.getEmail();
        password = utente.getPassword();
        telefono = utente.getTelefono();
        dataNascita = utente.getDataNascita();
    }
}
