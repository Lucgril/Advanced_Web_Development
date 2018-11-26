package org.sharechef.data.model;

import java.util.List;
import org.sharechef.data.DataLayer;
import org.sharechef.data.DataLayerException;


public interface ShareChefDataLayer extends DataLayer {
    
    Sessione login(String email, String password);
    
    Sessione registrazione(Utente utente);
    
    void logout(String token);
    
    Sessione createSessione();
    
    Utente createUtente();
    
    Utente createUtenteByEvento();
    
    Evento createEvento();
    
    Interesse createInteresse();
    
    Recensione createRecensione();
    
    Messaggio createMessaggio();
    
    Interessa createInteressa();
    
    Partecipare createPartecipare();
    
    void storeSessione(Sessione sessione) throws DataLayerException;
    
    Utente storeUtente(Utente utente) throws DataLayerException;
    
    boolean storeEvento(String token, Evento evento) throws DataLayerException;
        
    boolean storeRecensione(String token, Recensione recensione, int id_utente, int id_evento) throws DataLayerException;
    
    boolean storeMessaggio(String token, int id_evento, Messaggio messaggio) throws DataLayerException;
    
    void storeInteressa(Interessa interessa)throws DataLayerException;
    
    boolean storePartecipare(String token, int id_evento) throws DataLayerException;
    
    boolean updateUtente(String token, Utente utente ) throws DataLayerException;
    
    boolean updateEvento(String token, Evento evento) throws DataLayerException;
    
    Sessione getSessione(int id_sessione) throws DataLayerException;
    
    Sessione getSessioneByUtente(int id_utente) throws DataLayerException;
    
    Sessione getSessioneByToken(String token) throws DataLayerException;
    
    Utente getUtente(int id_utente) throws DataLayerException;
    
    Utente getUtenteByEmail(String email) throws DataLayerException;
    
    Evento getEvento(int id_evento) throws DataLayerException;
    
    Recensione getRecensione(int id_recensione) throws DataLayerException;
            
    Messaggio getMessaggio(int id_messaggio) throws DataLayerException;
    
    Interesse getInteresse(int id_interesse) throws DataLayerException;
    
    Interessa getInteressa(int id_interessa) throws DataLayerException;
    
    Partecipare getPartecipare(int id_partecipare) throws DataLayerException;;
    
    List<Interesse> getInteressi() throws DataLayerException;
    
    List<Evento> getEventi() throws DataLayerException;
    
    List<Evento> getEventiPartecipati(String token, int id_utente) throws DataLayerException;
    
    List<Messaggio> getMessaggiByEvento(String token, int id_evento) throws DataLayerException;
    
    List<Recensione> getRecensioniByUtente(String token, int id_utente) throws DataLayerException;
    
    Recensione getRecensioneByUtente(String token, int id_utente, int id_recensione) throws DataLayerException;
    
    List<Evento> getEventiByUtente(String token, int id_utente) throws DataLayerException;
        
    Utente getUtenteByEvento(String token, int id_evento) throws DataLayerException;
    
    List<Utente> getPartecipanti(String token, int id_evento) throws DataLayerException;
    
    List<Evento> getEventiByEtà(int età) throws DataLayerException;
    
    List<Evento> getEventiByLuogo(String luogo) throws DataLayerException;
    
    List<Evento> getEventiByPrezzo(double prezzo) throws DataLayerException;
    
    List<Evento> getEventiByTipo(String tipo) throws DataLayerException;
    
    Partecipare getPartecipareByUtenteEvento(int id_utente, int id_evento) throws DataLayerException;
  
}
