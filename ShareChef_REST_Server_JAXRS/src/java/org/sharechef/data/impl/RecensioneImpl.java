package org.sharechef.data.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.sharechef.data.DataLayerException;
import org.sharechef.data.model.Evento;
import org.sharechef.data.model.Recensione;
import org.sharechef.data.model.ShareChefDataLayer;
import org.sharechef.data.model.Utente;


public class RecensioneImpl implements Recensione {
    
    private int id;
    private Utente autore;
    private Evento evento;
    private int voto, id_autore, id_evento;
    Date oggi = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private String immagine, testo, data, ora;
    private ShareChefDataLayer ownerdatalayer;
    
    public RecensioneImpl(ShareChefDataLayer ownerdatalayer) {
        
        this.id = 0;
        this.ownerdatalayer = ownerdatalayer;
        this.autore = null;
        this.id_autore = 0;
        this.evento = null;
        this.id_evento = 0;
        this.data = sdf.format(oggi);
        this.ora = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        this.testo = "";
        this.voto = 0;
        this.immagine = "";
    }
    
    public RecensioneImpl() {
        
        this.id = 0;
        this.ownerdatalayer = ownerdatalayer;
        this.autore = null;
        this.id_autore = 0;
        this.evento = null;
        this.id_evento = 0;
        this.data = sdf.format(oggi);
        this.ora = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        this.testo = "";
        this.voto = 0;
        this.immagine = "";
    }
    

    @Override
    public Utente getAutore() {
        return autore;
    }

    @Override
    public void setAutore(Utente autore) {
        this.autore = autore;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getOra() {
        return ora;
    }

    @Override
    public void setOra(String ora) {
        this.ora = ora;
    }

    @Override
    public int getVoto() {
        return voto;
    }

    @Override
    public void setVoto(int voto) {
        this.voto = voto;
    }

    @Override
    public String getTesto() {
        return testo;
    }

    @Override
    public void setTesto(String testo) {
        this.testo = testo;
    }
    
    @Override
    public String getImmagine() {
        return immagine;
    }
    
    @Override
    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
    
    protected void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;    
    }

    @Override
    public Evento getEvento() {
        return this.evento;
    }

    @Override
    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    
    @Override
    public int getIdEvento() {
        return id_evento;
    }
    
    @Override
    public void setIdEvento(int id_evento) {
        this.id_evento = id_evento;
        this.evento = null;
    }
    
    @Override
    public int getIdAutore() {
        return id_autore;
    }
    
    @Override
    public void setIdAutore(int id_autore) {
        this.id_autore= id_autore;
        this.autore = null;
    }
    
    @Override
    public void copyFrom(Recensione recensione) throws DataLayerException {
        id = recensione.getId();
        id_autore = recensione.getIdAutore();
        id_evento = recensione.getIdEvento();
        data = recensione.getData();
        ora = recensione.getOra();
        testo = recensione.getTesto();
        voto = recensione.getVoto();
        immagine = recensione.getImmagine();
    }
}
