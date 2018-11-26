package org.sharechef.controller;

import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.sharechef.data.DataLayerException;
import org.sharechef.data.impl.ShareChefMysqlImpl;
import org.sharechef.data.model.ShareChefDataLayer;


public class ShareChefBaseController {
    
    ShareChefDataLayer datalayer;
    
    public ShareChefBaseController () throws NamingException, SQLException, DataLayerException {
        Context initialContext = new InitialContext();
        DataSource ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/pippo");
        datalayer = new ShareChefMysqlImpl(ds);
        datalayer.init();
    }
}
