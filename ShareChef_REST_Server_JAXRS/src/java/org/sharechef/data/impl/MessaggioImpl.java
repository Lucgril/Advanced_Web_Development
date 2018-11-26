package org.sharechef.data.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.sharechef.data.DataLayerException;
import org.sharechef.data.model.Evento;
import org.sharechef.data.model.Messaggio;
import org.sharechef.data.model.ShareChefDataLayer;
import org.sharechef.data.model.Utente;



public class MessaggioImpl implements Messaggio {
    
    private int id, id_mittente, id_evento;
    private String testo, data, ora;
    Date oggi = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private Utente mittente;
    private Evento evento;
    private ShareChefDataLayer ownerdatalayer;
    
    public MessaggioImpl(ShareChefDataLayer ownerdatalayer) {
        this.id = 0;
        this.ownerdatalayer = ownerdatalayer;
        this.testo = "";
        this.data = sdf.format(oggi);
        this.ora = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        this.mittente = null;
        this.id_mittente = 0;
        this.evento = null;
        this.id_evento = 0;
    }
    
    public MessaggioImpl() {
        
        this.id = 0;
        this.ownerdatalayer = ownerdatalayer;
        this.testo = "";
        this.data = sdf.format(oggi);
        this.ora = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        this.mittente = null;
        this.id_mittente = 0;
        this.evento = null;
        this.id_evento = 0;
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
    public Utente getMittente() {
        return mittente;
    }

    @Override
    public void setMittente(Utente mittente) {
        this.mittente = mittente;
    }
    
    @Override
    public int getIdMittente() {
        return id_mittente;
    }
    
    @Override
    public int getIdEvento() {
        return id_evento;
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
    public void setIdEvento(int id_evento) {
        this.id_evento = id_evento;
        this.evento = null;
    }
    
    @Override
    public void setIdMittente(int id_mittente) {
        this.id_mittente= id_mittente;
        this.mittente = null;
    }

    @Override
    public void copyFrom(Messaggio messaggio) throws DataLayerException {
        id = messaggio.getId();
        testo = messaggio.getTesto();
        data = messaggio.getData();
        ora = messaggio.getOra();
        id_mittente = messaggio.getIdMittente();
        id_evento = messaggio.getIdEvento();
    }
}
