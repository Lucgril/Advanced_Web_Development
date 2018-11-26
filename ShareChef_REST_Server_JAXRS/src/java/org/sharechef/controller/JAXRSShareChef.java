package org.sharechef.controller;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("rest")
public class JAXRSShareChef extends Application {
    
    private final Set<Class<?>> classes;

    public JAXRSShareChef() {
        HashSet<Class<?>> c = new HashSet<Class<?>>();
        //aggiungiamo tutte le *root resurces* (cioè quelle
        //con l'annotazione Path) che vogliamo pubblicare
        c.add(RepoResource.class);
        //aggiungiamo il provider Jackson per poter
        //usare i suoi servizi di serializzazione e 
        //deserializzazione JSON
        c.add(JacksonJsonProvider.class);
        //aggiungo il provider per poter lavorare con 
        //comunicazioni HTTP di tipo multipart
        //c.add(MultiPartFeature.class);
        classes = Collections.unmodifiableSet(c);
    }

    //l'override di questo metodo deve restituire il set
    //di classi che JAX-RS utilizzerà per pubblicare il
    //servizio. Tutte le altre, anche se annotate, verranno
    //IGNORATE
    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
    
}
