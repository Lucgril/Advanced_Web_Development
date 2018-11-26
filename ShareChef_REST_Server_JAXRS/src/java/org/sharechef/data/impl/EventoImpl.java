package org.sharechef.data.impl;

import java.util.Date;
import org.sharechef.data.DataLayerException;
import org.sharechef.data.model.Evento;
import org.sharechef.data.model.ShareChefDataLayer;
import org.sharechef.data.model.Utente;


public class EventoImpl implements Evento {
    
    private String nome, immagine, tipo, ora, menù, città, via, descrizione;
    private Date data;
    private int id, etàMinima, numeroInvitati, id_organizzatore;
    private double prezzo;
    private Utente organizzatore;
    private ShareChefDataLayer ownerdatalayer;
    
    public EventoImpl(ShareChefDataLayer ownerdatalayer) {
        
        this.ownerdatalayer = ownerdatalayer;
        this.id = 0;
        this.nome = "";
        this.immagine = "";
        this.tipo = "";
        this.ora = "";
        this.etàMinima = 0;
        this.menù = "";
        this.numeroInvitati = 0;
        this.città = "";
        this.via = "";
        this.descrizione = "";
        this.prezzo = 0;
        this.data = null;
        this.organizzatore = null;
        this.id_organizzatore = 0;    
    }
    
    public EventoImpl() {
        
        this.ownerdatalayer = ownerdatalayer;
        this.id = 0;
        this.nome = "";
        this.immagine = "";
        this.tipo = "";
        this.ora = "";
        this.etàMinima = 0;
        this.menù = "";
        this.numeroInvitati = 0;
        this.città = "";
        this.via = "";
        this.descrizione = "";
        this.prezzo = 0;
        this.data = null;
        this.organizzatore = null;
        this.id_organizzatore = 0;    
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
    public String getImmagine() {
        return immagine;
    }
    
    @Override
    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    @Override
    public String getMenù() {
        return menù;
    }

    @Override
    public void setMenù(String menù) {
        this.menù = menù;
    }

    @Override
    public String getCittà() {
        return città;
    }

    @Override
    public void setCittà(String luogo) {
        this.città = città;
    }
    
    @Override
    public String getVia() {
        return via;
    }

    @Override
    public void setVia(String via) {
        this.via = via;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
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
    public Date getData() {
        return data;
    }

    @Override
    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public int getEtàMinima() {
        return etàMinima;
    }

    @Override
    public void setEtàMinima(int etàMinima) {
        this.etàMinima = etàMinima;
    }

    @Override
    public int getNumeroInvitati() {
        return numeroInvitati;
    }

    @Override
    public void setNumeroInvitati(int numeroInvitati) {
        this.numeroInvitati = numeroInvitati;
    }

    @Override
    public double getPrezzo() {
        return prezzo;
    }

    @Override
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
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
    public String getTipo() {
        return this.tipo;
    }

    @Override
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public Utente getOrganizzatore() {
        return this.organizzatore;
        }

    @Override
    public void setOrganizzatore(Utente organizzatore) {
        this.organizzatore = organizzatore;
    }
    
    @Override
    public int getIdOrganizzatore() {
        return id_organizzatore;
    } 
    
    @Override
    public void setIdOrganizzatore(int id_organizzatore) {
        this.id_organizzatore = id_organizzatore;
        this.organizzatore = null;
    }
    
    @Override
    public void copyFrom(Evento evento) throws DataLayerException {
        id = evento.getId();
        nome = evento.getNome();
        immagine = evento.getImmagine();
        tipo = evento.getTipo();
        ora = evento.getOra();
        etàMinima = evento.getEtàMinima();
        menù = evento.getMenù();
        numeroInvitati = evento.getNumeroInvitati();
        città = evento.getCittà();
        via = evento.getVia();
        descrizione = evento.getDescrizione();
        prezzo = evento.getPrezzo();
        data = evento.getData();
        id_organizzatore = evento.getIdOrganizzatore();
    }
    
}
