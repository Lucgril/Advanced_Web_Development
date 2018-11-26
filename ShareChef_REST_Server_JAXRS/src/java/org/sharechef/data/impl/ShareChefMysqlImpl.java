package org.sharechef.data.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.sharechef.data.DataLayerException;
import org.sharechef.data.DataLayerMysqlImpl;
import org.sharechef.data.model.Evento;
import org.sharechef.data.model.Interessa;
import org.sharechef.data.model.Interesse;
import org.sharechef.data.model.Messaggio;
import org.sharechef.data.model.Partecipare;
import org.sharechef.data.model.Recensione;
import org.sharechef.data.model.Sessione;
import org.sharechef.data.model.ShareChefDataLayer;
import org.sharechef.data.model.Utente;


public class ShareChefMysqlImpl extends DataLayerMysqlImpl implements ShareChefDataLayer {
    

    private PreparedStatement iSessione, dSessione;
    private PreparedStatement iUtente, uUtente;
    private PreparedStatement iInteresse, dInteresse, uInteresse;
    private PreparedStatement iInteressa, dInteressa, uInteressa;
    private PreparedStatement iEvento, dEvento, uEvento;
    private PreparedStatement iMessaggio;
    private PreparedStatement iPartecipare, dPartecipare;
    private PreparedStatement iRecensione, dRecensione;
    private PreparedStatement sInteressi;
    private PreparedStatement sEventi, sEventiPartecipati, sEventiByUtente, sEventiByEtà, sEventiByCittà, sEventiByPrezzo, sEventiByTipo;
    private PreparedStatement sMessaggiByEvento;
    private PreparedStatement sRecensioniByUtente, sPartecipareByUtenteEVento;
    private PreparedStatement sSessioneById, sUtenteById, sEventoById, sInteresseById, sRecensioneById, sMessaggioById, sInteressaById, sPartecipareById, sUtenteByEvento;
    private PreparedStatement sUtenteByEmail, sSessioneByUtente, sSessioneByToken, sPartecipanti;
    
    public ShareChefMysqlImpl(DataSource datasource) throws SQLException, NamingException {
        super(datasource);
    }

    
    @Override
    public void init() throws DataLayerException {
        try {
            super.init();
            
            iSessione = connection.prepareStatement("INSERT INTO sessione (utente,token) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            dSessione = connection.prepareStatement("DELETE FROM sessione WHERE token=?");
            
            iUtente = connection.prepareStatement("INSERT INTO utente (nome,cognome,email,password,telefono) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uUtente = connection.prepareStatement("UPDATE utente SET città=?, provincia=?, email=?, password=?, telefono=?, immagine=? WHERE id=?", Statement.RETURN_GENERATED_KEYS);
            
            iEvento = connection.prepareStatement("INSERT INTO evento (nome,tipo,ora,età_minima,menù,numero_invitati,città,via,descrizione,prezzo,data,organizzatore,immagine) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            dEvento = connection.prepareStatement("DELETE FROM evento WHERE id=?");
            uEvento = connection.prepareStatement("UPDATE evento SET nome=?, tipo=?, ora=?, età_minima=?, menù=?, numero_invitati=?, città=?, via=?, descrizione=?, prezzo=?, data=?, immagine=? WHERE id=? AND organizzatore=?");
            
            iInteresse = connection.prepareStatement("INSERT INTO interesse (nome) VALUE(?)", Statement.RETURN_GENERATED_KEYS);
            dInteresse = connection.prepareStatement("DELETE FROM interesse WHERE id=?");
            uInteresse = connection.prepareStatement("UPDATE interesse SET nome=? WHERE id=?");
            
            iInteressa = connection.prepareStatement("INSERT INTO interessa (utente,interesse) VALUE(?,?)", Statement.RETURN_GENERATED_KEYS);
            dInteressa = connection.prepareStatement("DELETE FROM interessa WHERE utente=? AND interesse=?");
            uInteressa = connection.prepareStatement("UPDATE interessa SET interesse=? WHERE utente=? AND id=?");
            
            iPartecipare = connection.prepareStatement("INSERT INTO partecipare (utente,evento) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            dPartecipare = connection.prepareStatement("DELETE FROM partecipare WHERE utente=? AND evento=?");

            iMessaggio = connection.prepareStatement("INSERT INTO messaggio (data,ora,testo,mittente,evento) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            
            iRecensione = connection.prepareStatement("INSERT INTO recensione (autore,evento,data,ora,testo,voto,immagine) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            dRecensione = connection.prepareStatement("DELETE FROM recensione WHERE id=?");
            
            sInteressi = connection.prepareStatement("SELECT * FROM interesse");
            
            sEventi = connection.prepareStatement("SELECT * FROM evento WHERE data >= CURDATE()");
            sEventiByUtente = connection.prepareStatement("SELECT * FROM evento WHERE organizzatore=?");
            sEventiPartecipati = connection.prepareStatement("SELECT * FROM partecipare JOIN evento ON partecipare.evento=evento.id AND partecipare.utente=?");
            sEventiByEtà = connection.prepareStatement("SELECT * FROM evento WHERE età_minima=? AND data >= CURDATE()");
            sEventiByPrezzo = connection.prepareStatement("SELECT * FROM evento WHERE prezzo=? AND data >= CURDATE()");
            sEventiByCittà = connection.prepareStatement("SELECT * FROM evento WHERE luogo=? AND data >= CURDATE()");
            sEventiByTipo = connection.prepareStatement("SELECT * FROM evento WHERE tipo=? AND data >= CURDATE()");
            
            sMessaggiByEvento = connection.prepareStatement("SELECT * FROM messaggio WHERE evento=?");
            
            sRecensioniByUtente  = connection.prepareStatement("SELECT utente.nome, utente.cognome, recensione.id, recensione.data, recensione.testo, recensione.voto, recensione.autore FROM utente JOIN recensione ON utente.id=recensione.autore JOIN evento ON recensione.evento=evento.id  AND evento.organizzatore=?");
            
            sSessioneByToken = connection.prepareStatement("SELECT * FROM sessione WHERE token=?");
            sSessioneByUtente = connection.prepareStatement("SELECT * FROM sessione WHERE utente=?");
            sSessioneById = connection.prepareStatement("SELECT * FROM sessione WHERE session_id=?");
            sUtenteById = connection.prepareStatement("SELECT * FROM utente WHERE id=?");
            sEventoById = connection.prepareStatement("SELECT * FROM evento WHERE id=?");
            sRecensioneById = connection.prepareStatement("SELECT * FROM recensione WHERE id=?");
            sMessaggioById = connection.prepareStatement("SELECT * FROM messaggio WHERE id=?");
            sInteresseById = connection.prepareStatement("SELECT * FROM interesse WHERE id=?");
            sInteressaById = connection.prepareStatement("SELECT * FROM interessa WHERE id=?");
            sPartecipareById = connection.prepareStatement("SELECT * FROM partecipare WHERE id=?");
            sUtenteByEvento = connection.prepareStatement("SELECT utente.id, utente.nome, utente.cognome, utente.immagine FROM evento JOIN utente ON evento.organizzatore=utente.id AND evento.id=?");
            sUtenteByEmail = connection.prepareStatement("SELECT * FROM utente WHERE email=?");
            sPartecipanti = connection.prepareStatement("SELECT utente.id, utente.nome, utente.cognome FROM partecipare JOIN utente ON partecipare.utente=utente.id AND partecipare.evento=?");
            sPartecipareByUtenteEVento = connection.prepareStatement("SELECT * FROM partecipare WHERE utente=? AND evento=?");
            
            
            
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'inizializzazione del SocialDevelop data layer ", ex);
        }
        
    }
    
    @Override
    public Sessione login(String email, String password) {
        
        Utente utente = null;
        Sessione newSessione = null;
        try {
            utente = getUtenteByEmail(email);
        } catch (DataLayerException ex) {
            Logger.getLogger(ShareChefMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(utente != null && utente.getPassword().equals(password)) {
            Sessione sessione = new SessioneImpl(this);
            sessione.setIdUtente(utente.getId());
            sessione.setToken(Utility.generateToken());
            try {
                storeSessione(sessione);
                newSessione = getSessioneByUtente(utente.getId());
            } catch (DataLayerException ex) {
                Logger.getLogger(ShareChefMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return newSessione;
        } else {
            return null;
        }
    }
    
    @Override
    public Sessione registrazione(Utente utente) {
        
        Sessione newSessione = null;
        
        if(utente != null) {
            Sessione sessione = new SessioneImpl(this);
            sessione.setIdUtente(utente.getId());
            String token = Utility.generateToken();
            token = token.substring(0, token.length()-3);
            sessione.setToken(token);
            try {
                storeSessione(sessione);
                newSessione = getSessioneByUtente(utente.getId());
            } catch (DataLayerException ex) {
                Logger.getLogger(ShareChefMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return newSessione;
        } else {
            return null;
        }
    }

    @Override
    public void logout(String token) {
        
        try { 
            Sessione sessione = getSessioneByToken(token);
            if(sessione != null) {
                dSessione.setString(1,token);
                dSessione.executeUpdate();
            }
        } catch (DataLayerException | SQLException ex) {
            Logger.getLogger(ShareChefMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
                     
    }
    
    @Override
    public Sessione createSessione() {
         return new SessioneImpl(this);
    }
    
    public Sessione createSessione(ResultSet rs) throws DataLayerException {
        try {
            SessioneImpl s = new SessioneImpl(this);
            s.setId(rs.getInt("session_id"));
            s.setIdUtente(rs.getInt("utente"));
            s.setToken(rs.getString("token"));
            
            
            return s;
        } catch (SQLException ex) {
            throw new DataLayerException("Incapace di creare un oggetto sessione da ResultSet", ex);
        }
    }

    @Override
    public Utente createUtente() {
         return new UtenteImpl(this);
    }
    
    public Utente createUtente(ResultSet rs) throws DataLayerException {
        try {
            UtenteImpl u = new UtenteImpl(this);
            u.setId(rs.getInt("id"));
            u.setImmagine(rs.getString("immagine"));
            u.setNome(rs.getString("nome"));
            u.setCognome(rs.getString("cognome"));
            u.setCittà(rs.getString("città"));
            u.setProvincia(rs.getString("provincia"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setDataNascita(rs.getDate("data_nascita"));
            u.setTelefono(rs.getString("telefono"));
            
            return u;
        } catch (SQLException ex) {
            throw new DataLayerException("Incapace di creare un oggetto utente da ResultSet", ex);
        }
    }
    
    @Override
    public Utente createUtenteByEvento() {
         return new UtenteImpl(this);
    }
    
    public Utente createUtenteByEvento(ResultSet rs) throws DataLayerException {
        try {
            UtenteImpl u = new UtenteImpl(this);
            u.setId(rs.getInt("id"));
            u.setNome(rs.getString("nome"));
            u.setCognome(rs.getString("cognome"));
            u.setImmagine(rs.getString("immagine"));
            
            return u;
        } catch (SQLException ex) {
            throw new DataLayerException("Incapace di creare un oggetto utente da ResultSet", ex);
        }
    }

    @Override
    public Evento createEvento() {
        return new EventoImpl(this);
    }
    
    public Evento createEvento(ResultSet rs) throws DataLayerException {
        try {
            EventoImpl e = new EventoImpl(this);
            e.setId(rs.getInt("id"));
            e.setNome(rs.getString("nome"));
            e.setImmagine(rs.getString("immagine"));
            e.setTipo(rs.getString("tipo"));
            e.setOra(rs.getString("ora"));
            e.setEtàMinima(rs.getInt("età_minima"));
            e.setMenù(rs.getString("menù"));
            e.setNumeroInvitati(rs.getInt("numero_invitati"));
            e.setCittà(rs.getString("città"));
            e.setVia(rs.getString("via"));
            e.setDescrizione(rs.getString("descrizione"));
            e.setPrezzo(rs.getDouble("prezzo"));
            e.setData(rs.getDate("data"));
            e.setIdOrganizzatore(rs.getInt("organizzatore"));
            e.setOrganizzatore(getUtente(e.getIdOrganizzatore()));
            
            return e;
        } catch (SQLException ex) {
            throw new DataLayerException("Incapace di creare un oggetto evento da ResultSet", ex);
        }
    }

    @Override
    public Interesse createInteresse() {
        return new InteresseImpl(this);
    }
    
    
    public Interesse createInteresse(ResultSet rs) throws DataLayerException {
        try {
            InteresseImpl i = new InteresseImpl(this);
            i.setId(rs.getInt("id"));
            i.setNome(rs.getString("nome"));
           
            return i;
        } catch (SQLException ex) {
            throw new DataLayerException("Incapace di creare un oggetto interesse da ResultSet", ex);
        }
    }

    @Override
    public Recensione createRecensione() {
        return new RecensioneImpl(this);
    }
    
    public Recensione createRecensione(ResultSet rs) throws DataLayerException {
        try {
            RecensioneImpl r = new RecensioneImpl(this);
            r.setId(rs.getInt("id"));
            r.setIdAutore(rs.getInt("autore"));
            r.setIdEvento(rs.getInt("evento"));
            r.setData(rs.getString("data"));
            r.setOra(rs.getString("ora"));
            r.setTesto(rs.getString("testo"));
            r.setVoto(rs.getInt("voto"));
            r.setImmagine(rs.getString("immagine"));
            
            return r;
        } catch (SQLException ex) {
            throw new DataLayerException("Incapace di creare un oggetto recensione da ResultSet", ex);
        }
    }

    @Override
    public Messaggio createMessaggio() {
        return new MessaggioImpl(this);
    }
    
    public Messaggio createMessaggio(ResultSet rs) throws DataLayerException {
        try {
            MessaggioImpl m = new MessaggioImpl(this);
            m.setId(rs.getInt("id"));
            m.setTesto(rs.getString("testo"));
            m.setData(rs.getString("data"));
            m.setOra(rs.getString("ora"));
            m.setIdMittente(rs.getInt("mittente"));
            m.setIdEvento(rs.getInt("evento"));
            
            return m;
        } catch (SQLException ex) {
            throw new DataLayerException("Incapace di creare un oggetto messaggio da ResultSet", ex);
        }
    }

    @Override
    public Interessa createInteressa() {
        return new InteressaImpl(this);
    }
    
    public Interessa createInteressa(ResultSet rs) throws DataLayerException {
        try {
            InteressaImpl i = new InteressaImpl(this);
            i.setId(rs.getInt("id"));
            i.setIdUtente(rs.getInt("utente"));
            i.setIdInteresse(rs.getInt("interesse"));
            
            return i;
        } catch (SQLException ex) {
            throw new DataLayerException("Incapace di creare un oggetto interessa da ResultSet", ex);
        }
    }

    @Override
    public Partecipare createPartecipare() {
        return new PartecipareImpl(this);
    }
    
    public Partecipare createPartecipare(ResultSet rs) throws DataLayerException {
        try {
            PartecipareImpl p = new PartecipareImpl(this);
            p.setId(rs.getInt("id"));
            p.setIdUtente(rs.getInt("utente"));
            p.setIdEvento(rs.getInt("evento"));
            
            return p;
        } catch (SQLException ex) {
            throw new DataLayerException("Incapace di creare un oggetto partecipare da ResultSet", ex);
        }
    }
    
    @Override
    public void storeSessione(Sessione sessione) throws DataLayerException {
        int id = sessione.getId();
        
        try {
            if (id > 0) { //updated
                
            } else { //insert
                
               iSessione.setInt(1, sessione.getIdUtente());
               iSessione.setString(2, sessione.getToken());
                
               
                if (iSessione.executeUpdate() == 1) {
                    
                    try (ResultSet keys = iSessione.getGeneratedKeys()) {
                        
                        if (keys.next()) {
                            
                            id = keys.getInt(1);
                        }
                    }
                }
            }
            
            if (id > 0) {
                sessione.copyFrom(getSessione(id));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare la sessione", ex);
        }
    }

    @Override
    public Utente storeUtente(Utente utente) throws DataLayerException {
        int id = utente.getId();
        
        try {
            if (id > 0) { //updated
                //non facciamo nulla se l'oggetto non ha subito modifiche
                
            } else { //insert
                
                iUtente.setString(1, utente.getNome());
                iUtente.setString(2, utente.getCognome());
                iUtente.setString(3, utente.getEmail());
                iUtente.setString(4, utente.getPassword());
                if (utente.getTelefono() != null) {
                    iUtente.setString(5, utente.getTelefono());
                } else {
                    iUtente.setNull(5, java.sql.Types.INTEGER);
                }
               
                if (iUtente.executeUpdate() == 1) {
                    
                    try (ResultSet keys = iUtente.getGeneratedKeys()) {
                        
                        if (keys.next()) {
                            
                            id = keys.getInt(1);
                        }
                    }
                }
            }
            
            if (id > 0) {
                utente.copyFrom(getUtente(id));
            }
            utente = getUtente(id);
            return utente;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare l'utente", ex);
        }
    }
    
    @Override
    public boolean updateUtente(String token, Utente utente) throws DataLayerException {
        Sessione sessione = getSessioneByToken(token);
        if (sessione != null) {
            try {
                int id = sessione.getIdUtente();
                Utente utente2 = getUtente(id);
                if (utente.getCittà() != "") uUtente.setString(1, utente.getCittà());
                else uUtente.setString(1, utente2.getCittà());
                if (utente.getProvincia() != "") uUtente.setString(2, utente.getProvincia());
                else uUtente.setString(2, utente2.getProvincia());
                if (utente.getEmail() != "") uUtente.setString(3, utente.getEmail());
                else uUtente.setString(3, utente2.getEmail());
                if (utente.getPassword() != "") uUtente.setString(4, utente.getPassword());
                else uUtente.setString(4, utente2.getPassword());
                if (utente.getTelefono() != "") uUtente.setString(5, utente.getTelefono());
                else uUtente.setString(5, utente2.getPassword());
                if (utente.getImmagine() != "") uUtente.setString(6, utente.getImmagine());
                else uUtente.setString(6, utente2.getImmagine());
                uUtente.setInt(7, id);
                if (uUtente.executeUpdate() == 1) {
                    
                    try (ResultSet keys = uUtente.getGeneratedKeys()) {
                        
                        if (keys.next()) {
                            
                            id = keys.getInt(1);
                        }
                    }
                }
            
            utente.copyFrom(getUtente(id));
            return true;
            } catch (SQLException ex) {
                throw new DataLayerException("Impossibile aggiornare l'utente", ex);
            }
        }
        return false;
    }

    @Override
    public boolean storeEvento(String token, Evento evento) throws DataLayerException {
        int id = evento.getId();
        Sessione sessione = getSessioneByToken(token);
        if (sessione != null) {
            try {
                evento.setIdOrganizzatore(sessione.getIdUtente());
                if (id > 0) { //updated
                    
                } else { //insert
                
                    iEvento.setString(1, evento.getNome());
                    iEvento.setString(2, evento.getTipo());
                    iEvento.setString(3, evento.getOra());
                    iEvento.setInt(4, evento.getEtàMinima());
                    iEvento.setString(5, evento.getMenù());
                    iEvento.setInt(6, evento.getNumeroInvitati());
                    iEvento.setString(7, evento.getCittà());
                    iEvento.setString(8, evento.getVia());
                    iEvento.setString(9, evento.getDescrizione());
                    iEvento.setDouble(10, evento.getPrezzo());
                    java.sql.Date sqlDate = new java.sql.Date(evento.getData().getTime());
                    iEvento.setDate(11, sqlDate);
                    iEvento.setInt(12, evento.getIdOrganizzatore());
                    iEvento.setString(13, evento.getImmagine());
                
                    if (iEvento.executeUpdate() == 1) {
                    
                        try (ResultSet keys = iEvento.getGeneratedKeys()) {
                            if (keys.next()) {
                                id = keys.getInt(1);
                            }
                        }
                    }   
                }
                if (id > 0) {
                    evento.copyFrom(getEvento(id));
                }
                return true;
            } catch (SQLException ex) {
                throw new DataLayerException("Impossibile caricare l'evento", ex);
            }
        }
        return false;
    }
    
    @Override
    public boolean updateEvento(String token, Evento evento) throws DataLayerException {
        Sessione sessione = getSessioneByToken(token);
        if (sessione != null) {
            evento.setIdOrganizzatore(sessione.getIdUtente());
            int id_evento = evento.getId();
            try {
                Evento evento2 = getEvento(id_evento);
                if (evento.getNome() != "") uEvento.setString(1, evento.getNome());
                else uEvento.setString(1, evento2.getNome());
                if (evento.getTipo() != "") uEvento.setString(2, evento.getTipo());
                else uEvento.setString(2, evento2.getTipo());
                if (evento.getOra() != "") uEvento.setString(3, evento.getOra());
                else uEvento.setString(3, evento2.getOra());
                if (evento.getEtàMinima() != 0)  uEvento.setInt(4, evento.getEtàMinima());
                else uEvento.setInt(4, evento2.getEtàMinima());
                if (evento.getMenù() != "") uEvento.setString(5, evento.getMenù());
                else uEvento.setString(5, evento2.getMenù());
                if (evento.getNumeroInvitati() != 0) uEvento.setInt(6, evento.getNumeroInvitati());
                else uEvento.setInt(6, evento2.getNumeroInvitati());
                if (evento.getCittà() != "") uEvento.setString(7, evento.getCittà());
                else uEvento.setString(7, evento2.getCittà());
                if (evento.getVia() != "") uEvento.setString(8, evento.getVia());
                else uEvento.setString(8, evento2.getVia());
                if (evento.getDescrizione() != "") uEvento.setString(9, evento.getDescrizione());
                else uEvento.setString(9, evento2.getDescrizione());
                if (evento.getPrezzo() != 0) uEvento.setDouble(10, evento.getPrezzo());
                else uEvento.setDouble(10, evento2.getPrezzo());
                if (evento.getData() != null) {
                    java.sql.Date sqlDate = new java.sql.Date(evento.getData().getTime());
                    uEvento.setDate(11, sqlDate);
                }
                else {
                    java.sql.Date sqlDate = new java.sql.Date(evento2.getData().getTime());
                    uEvento.setDate(11, sqlDate);
                }
                if (evento.getImmagine() != "") uEvento.setString(12, evento.getImmagine());
                else uEvento.setString(12, evento2.getImmagine());
             
                uEvento.setInt(13, evento.getId());
                uEvento.setInt(14, evento.getIdOrganizzatore());
                
                if (uEvento.executeUpdate() == 1) {
                    
                    try (ResultSet keys = uEvento.getGeneratedKeys()) {
                        
                        if (keys.next()) {
                            
                            id_evento = keys.getInt(1);
                        }
                    }
                }
            
            evento.copyFrom(getEvento(id_evento));
            return true;
            } catch (SQLException ex) {
                Logger.getLogger(ShareChefMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public boolean storeRecensione(String token, Recensione recensione, int id_utente, int id_evento) throws DataLayerException {
        int id = recensione.getId();
        Sessione sessione = getSessioneByToken(token);
        if (sessione != null) {
            recensione.setIdAutore(id_utente);
            recensione.setIdEvento(id_evento);
            try {
                if (id > 0) { //updated
                
                } else { //insert
                
                    iRecensione.setInt(1, recensione.getIdAutore());
                    iRecensione.setInt(2, recensione.getIdEvento());
                    iRecensione.setString(3, recensione.getData());
                    iRecensione.setString(4, recensione.getOra());
                    iRecensione.setString(5, recensione.getTesto());
                    iRecensione.setInt(6, recensione.getVoto());
                    iRecensione.setString(7, recensione.getImmagine());
                
                    if (iRecensione.executeUpdate() == 1) {
                    
                        try (ResultSet keys = iRecensione.getGeneratedKeys()) {
                        
                            if (keys.next()) {
                            
                                id = keys.getInt(1);
                            }
                        }
                    }
                }
            
                if (id > 0) {
                    recensione.copyFrom(getRecensione(id));
                }
                return true;
            } catch (SQLException ex) {
                throw new DataLayerException("Impossibile caricare la recensione", ex);
            }
        }
        return false;
        
    }

    @Override
    public boolean storeMessaggio(String token, int id_evento, Messaggio messaggio) throws DataLayerException {
        int id = messaggio.getId();
        Sessione sessione = getSessioneByToken(token);
        if (sessione != null) {
            try {
                messaggio.setIdMittente(sessione.getIdUtente());
                messaggio.setIdEvento(id_evento);
        
                if (id > 0) { //updated
                
                } else { //insert
                
                    iMessaggio.setString(1, messaggio.getData());
                    iMessaggio.setString(2, messaggio.getOra());
                    iMessaggio.setString(3, messaggio.getTesto());
                    iMessaggio.setInt(4, messaggio.getIdMittente());
                    iMessaggio.setInt(5, messaggio.getIdEvento());
                
                    if (iMessaggio.executeUpdate() == 1) {
                    
                        try (ResultSet keys = iMessaggio.getGeneratedKeys()) {
                        
                            if (keys.next()) {
                                id = keys.getInt(1);
                            }   
                        }
                    }
                }
            
                if (id > 0) {
                    messaggio.copyFrom(getMessaggio(id));
                }
                return true;
            } catch (SQLException ex) {
                throw new DataLayerException("Impossibile caricare il messaggio", ex);
            }
        }
        return false;
        
    }

    @Override
    public void storeInteressa(Interessa interessa) throws DataLayerException {
        int id = interessa.getId();
        
        try {
            if (id > 0) { //updated
                
            } else { //insert
                
                iInteressa.setInt(1, interessa.getIdUtente());
                iInteressa.setInt(2, interessa.getIdInteresse());
                 
                if (iInteressa.executeUpdate() == 1) {
                    
                    try (ResultSet keys = iInteressa.getGeneratedKeys()) {
                        
                        if (keys.next()) {
                            
                            id = keys.getInt(1);
                        }
                    }
                }
            }
            
            if (id > 0) {
                interessa.copyFrom(getInteressa(id));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare l'oggetto interessa", ex);
        }
    }

    @Override
    public boolean storePartecipare(String token, int id_evento) throws DataLayerException {
        Partecipare partecipare = new PartecipareImpl(this);
        Sessione sessione = getSessioneByToken(token);
        if (sessione != null) {
            try {
                Partecipare partecipare2 = getPartecipareByUtenteEvento(sessione.getIdUtente(), id_evento);
                if (partecipare2 != null) return false; 
                int id = partecipare.getId();
                partecipare.setIdEvento(id_evento);
                partecipare.setIdUtente(sessione.getIdUtente());
                if (id > 0) { //updated
                
                } else { //insert
                    iPartecipare.setInt(1, partecipare.getIdUtente());
                    iPartecipare.setInt(2, partecipare.getIdEvento());
                
                    if (iPartecipare.executeUpdate() == 1) {
                    
                        try (ResultSet keys = iPartecipare.getGeneratedKeys()) {
                        
                            if (keys.next()) {
                                id = keys.getInt(1);
                            }
                        }
                    }
                }
            
                if (id > 0) {
                    partecipare.copyFrom(getPartecipare(id));
                }
                return true;
            } catch (SQLException ex) {
                throw new DataLayerException("Impossibile caricare l'oggetto partecipare", ex);
            }
        }
        return false;
        }

    @Override
    public List<Interesse> getInteressi() throws DataLayerException {
        List<Interesse> result = new ArrayList();
        
        try (ResultSet rs = sInteressi.executeQuery()) {
            while (rs.next()) {
                result.add(getInteresse(rs.getInt("id")));

            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile Caricare gli Interessi", ex);
        }
        return result;
    }

    @Override
    public List<Evento> getEventi() throws DataLayerException {
        List<Evento> result = new ArrayList();
        Evento evento;
        try (ResultSet rs = sEventi.executeQuery()) {
            while (rs.next()) {
                evento = getEvento(rs.getInt("id"));
                evento.setOrganizzatore(getUtente(evento.getIdOrganizzatore()));
                result.add(evento);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile Caricare i Progetti", ex);
        }
    return result;
    }
    
    @Override
    public Sessione getSessione(int id_sessione) throws DataLayerException {
        try {
            sSessioneById.setInt(1, id_sessione);
            try (ResultSet rs = sSessioneById.executeQuery()) {
                if (rs.next()) {
                    
                    return createSessione(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare sessione by id", ex);
        }
        return null;
    }
    
    @Override
    public Sessione getSessioneByUtente(int id_utente) throws DataLayerException {
        try {
            sSessioneByUtente.setInt(1, id_utente);
            try (ResultSet rs = sSessioneByUtente.executeQuery()) {
                if (rs.next()) {
                    
                    return createSessione(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare sessione by id", ex);
        }
        return null;
    }
    
    @Override
    public Sessione getSessioneByToken(String token) throws DataLayerException {
        try {
            sSessioneByToken.setString(1, token);
            try (ResultSet rs = sSessioneByToken.executeQuery()) {
                if (rs.next()) {
                    
                    return createSessione(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare sessione by token", ex);
        }
        return null;
    }

    @Override
    public Utente getUtente(int id_utente) throws DataLayerException {
        try {
            sUtenteById.setInt(1, id_utente);
            try (ResultSet rs = sUtenteById.executeQuery()) {
                if (rs.next()) {
                    
                    return createUtente(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare utente by id", ex);
        }
        return null;
    }
    
    @Override
    public Utente getUtenteByEmail(String email) throws DataLayerException {
        try {
            sUtenteByEmail.setString(1, email);
            try (ResultSet rs = sUtenteByEmail.executeQuery()) {
                if (rs.next()) {
                    return createUtente(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare utente by email", ex);
        }
        return null;
    }

    @Override
    public Evento getEvento(int id_evento) throws DataLayerException {
        try {
            sEventoById.setInt(1, id_evento);
            try (ResultSet rs = sEventoById.executeQuery()) {
                if (rs.next()) {
                    
                    return createEvento(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare evento by id", ex);
        }
        return null;
    }

    @Override
    public Recensione getRecensione(int id_recensione) throws DataLayerException {
        try {
            sRecensioneById.setInt(1, id_recensione);
            try (ResultSet rs = sRecensioneById.executeQuery()) {
                if (rs.next()) {
                    
                    return createRecensione(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare recensione by id", ex);
        }
        return null;
    }

    @Override
    public Messaggio getMessaggio(int id_messaggio) throws DataLayerException {
        try {
            sMessaggioById.setInt(1, id_messaggio);
            try (ResultSet rs = sMessaggioById.executeQuery()) {
                if (rs.next()) {
                    
                    return createMessaggio(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare messaggio by id", ex);
        }
        return null;    
    }

    @Override
    public Interesse getInteresse(int id_interesse) throws DataLayerException {
        try {
            sInteresseById.setInt(1, id_interesse);
            try (ResultSet rs = sInteresseById.executeQuery()) {
                if (rs.next()) {
                    
                    return createInteresse(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare interesse by id", ex);
        }
        return null;
    }

    @Override
    public Interessa getInteressa(int id_interessa) throws DataLayerException {
        try {
            sInteressaById.setInt(1, id_interessa);
            try (ResultSet rs = sInteressaById.executeQuery()) {
                if (rs.next()) {
                    
                    return createInteressa(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare interessa by id", ex);
        }
        return null;    
    }

    @Override
    public Partecipare getPartecipare(int id_partecipare) throws DataLayerException {
        try {
            sPartecipareById.setInt(1, id_partecipare);
            try (ResultSet rs = sPartecipareById.executeQuery()) {
                if (rs.next()) {
                    
                    return createPartecipare(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare partecipare by id", ex);
        }
        return null;
    }

    @Override
    public List<Messaggio> getMessaggiByEvento(String token, int id_evento) throws DataLayerException {
        List<Messaggio> result = new ArrayList();
        Messaggio messaggio;
        Sessione sessione = getSessioneByToken(token);
        if (sessione != null) {
            try {
                sMessaggiByEvento.setInt(1, id_evento);
                try (ResultSet rs = sMessaggiByEvento.executeQuery()) {
                    while (rs.next()) {
                        messaggio = getMessaggio(rs.getInt("id"));
                        messaggio.setMittente(getUtente(messaggio.getIdMittente()));
                        result.add(messaggio);
                    }   
                }
            } catch (SQLException ex) {
                throw new DataLayerException("Impossibile caricare i messaggi dell'evento", ex);
            }
            return result;
        }
        else return null;
    }

    @Override
    public List<Recensione> getRecensioniByUtente(String token, int id_utente) throws DataLayerException {
        List<Recensione> result = new ArrayList();
        Sessione sessione = getSessioneByToken(token);
        if (sessione != null) {
            try {
                sRecensioniByUtente.setInt(1, id_utente);
                try (ResultSet rs = sRecensioniByUtente.executeQuery()) {
                    while (rs.next()) {
                        Recensione recensione = getRecensione(rs.getInt("id"));
                        recensione.setAutore(getUtente(recensione.getIdAutore()));
                        recensione.setEvento(getEvento(recensione.getIdEvento()));
                        result.add(recensione);
                    }
                }
            } catch (SQLException ex) {
                throw new DataLayerException("Impossibile caricare le recensioni dell'utente", ex);
            }
            return result;
        }
        else return null;
    }

    @Override
    public Recensione getRecensioneByUtente(String token, int id_utente, int id_recensione) throws DataLayerException {
        Sessione sessione = getSessioneByToken(token);
        if (sessione != null) {
            Recensione recensione = getRecensione(id_recensione);
            recensione.setAutore(getUtente(recensione.getIdAutore()));
            recensione.setEvento(getEvento(recensione.getIdEvento()));
            return recensione;
        }
        return null;
    }
    
    @Override
    public List<Evento> getEventiByUtente(String token, int id_utente) throws DataLayerException {
        List<Evento> result = new ArrayList();
        Sessione sessione = getSessioneByToken(token);
        if (sessione != null) {
            try {
                sEventiByUtente.setInt(1, id_utente);
                try (ResultSet rs = sEventiByUtente.executeQuery()) {
                    while (rs.next()) {
                     result.add(getEvento(rs.getInt("id")));
                    }
                }
            } catch (SQLException ex) {
                throw new DataLayerException("Impossibile caricare gli eventi dell'utente", ex);
            }
            return result;
        }
        return null;
    }
    
    @Override
    public List<Evento> getEventiPartecipati(String token, int id_utente) throws DataLayerException {
        List<Evento> result = new ArrayList();
        Sessione sessione = getSessioneByToken(token);
        if (sessione != null) {
            try {
                sEventiPartecipati.setInt(1, id_utente);
                try (ResultSet rs = sEventiPartecipati.executeQuery()) {
                    while (rs.next()) {
                        result.add(getEvento(rs.getInt("id")));
                    }
                }
            } catch (SQLException ex) {
                throw new DataLayerException("Impossibile caricare gli eventi a cui partecipa l'utente", ex);
            }
            return result;
        }
        return null;
    }
    
    @Override
    public List<Utente> getPartecipanti(String token, int id_evento) throws DataLayerException {
        List<Utente> result = new ArrayList();
        Sessione sessione = getSessioneByToken(token);
        if (sessione != null) {
            try {
                sPartecipanti.setInt(1, id_evento);
                try (ResultSet rs = sPartecipanti.executeQuery()) {
                    while (rs.next()) {
                        result.add(getUtente(rs.getInt("id")));
                    }
                }
            } catch (SQLException ex) {
                throw new DataLayerException("Impossibile caricare gli utenti partecipanti all'evento", ex);
            }
            return result;
        }
        return null;
    }

    
    @Override
    public Utente getUtenteByEvento(String token, int id_evento) throws DataLayerException {
        Sessione sessione = getSessioneByToken(token);
        if (sessione != null) {
            try {
                sUtenteByEvento.setInt(1, id_evento);
                try (ResultSet rs = sUtenteByEvento.executeQuery()) {
                    if (rs.next()) {
                        return createUtenteByEvento(rs);
                    }
                }
            } catch (SQLException ex) {
                throw new DataLayerException("Impossibile caricare l'utente", ex);
            }
            return null;
        }
        return null;
    }

    @Override
    public List<Evento> getEventiByEtà(int età) throws DataLayerException {
        List<Evento> result = new ArrayList();
        try {
            sEventiByEtà.setInt(1, età);
            try (ResultSet rs = sEventiByEtà.executeQuery()) {
                while (rs.next()) {
                    result.add(getEvento(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare gli eventi", ex);
        }
    return result;
    }

    @Override
    public List<Evento> getEventiByLuogo(String luogo) throws DataLayerException {
        List<Evento> result = new ArrayList();
        try {
            sEventiByCittà.setString(1, luogo);
            try (ResultSet rs = sEventiByCittà.executeQuery()) {
                while (rs.next()) {
                    result.add(getEvento(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
        throw new DataLayerException("Impossibile caricare gli eventi", ex);
        }
    return result;    
    }

    @Override
    public List<Evento> getEventiByPrezzo(double prezzo) throws DataLayerException {
        List<Evento> result = new ArrayList();
        try {
            sEventiByPrezzo.setDouble(1, prezzo);
            try (ResultSet rs = sEventiByPrezzo.executeQuery()) {
                while (rs.next()) {
                    result.add(getEvento(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
        throw new DataLayerException("Impossibile caricare gli eventi", ex);
        }
    return result;    
    }

    @Override
    public List<Evento> getEventiByTipo(String tipo) throws DataLayerException {
        List<Evento> result = new ArrayList();
        try {
            sEventiByTipo.setString(1, tipo);
            try (ResultSet rs = sEventiByTipo.executeQuery()) {
                while (rs.next()) {
                    result.add(getEvento(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare gli eventi", ex);
        }
    return result;
    }
    
    @Override
    public Partecipare getPartecipareByUtenteEvento(int id_utente, int id_evento) throws DataLayerException {
        try {
            sPartecipareByUtenteEVento.setInt(1, id_utente);
            sPartecipareByUtenteEVento.setInt(2, id_evento);
                try (ResultSet rs = sPartecipareByUtenteEVento.executeQuery()) {
                    if (rs.next()) {
                        return createPartecipare(rs);
                    }
                
            }
        }   catch (SQLException ex) {
            Logger.getLogger(ShareChefMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
   
    @Override
    public void destroy() {
        
        try {
            iSessione.close();
            dSessione.close();
            iUtente.close();
            uUtente.close();
            iInteresse.close();
            dInteresse.close();
            uInteresse.close();
            iInteressa.close();
            dInteressa.close();
            uInteressa.close();
            iEvento.close();
            dEvento.close();
            uEvento.close();
            iMessaggio.close();
            iPartecipare.close();
            dPartecipare.close();
            iRecensione.close();
            dRecensione.close();
            sInteressi.close();
            sEventi.close();
            sEventiPartecipati.close();
            sEventiByUtente.close();
            sEventiByEtà.close();
            sEventiByCittà.close();
            sEventiByPrezzo.close();
            sEventiByTipo.close();
            sMessaggiByEvento.close();
            sRecensioniByUtente.close();
            sSessioneById.close();
            sUtenteById.close();
            sEventoById.close();
            sInteresseById.close();
            sRecensioneById.close();
            sMessaggioById.close();
            sInteressaById.close();
            sPartecipareById.close();
            sUtenteByEvento.close();
            sUtenteByEmail.close();
            sSessioneByUtente.close();
            sSessioneByToken.close();
            sPartecipanti.close();
            sPartecipareByUtenteEVento.close();
            
        } catch (SQLException ex) {
            
        }
        super.destroy();
    }

    
}
