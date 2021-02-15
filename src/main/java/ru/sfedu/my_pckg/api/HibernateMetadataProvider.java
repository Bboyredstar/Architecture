package ru.sfedu.my_pckg.api;

import com.sun.org.apache.xalan.internal.res.XSLTErrorResources_es;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import ru.sfedu.my_pckg.Constants;
import ru.sfedu.my_pckg.utils.ConfigurationUtil;
import ru.sfedu.my_pckg.utils.HibernateUtil;

import java.io.IOException;
import java.util.List;

public class HibernateMetadataProvider implements IMetaDataProvider{
    public static Logger log = LogManager.getLogger(HibernateMetadataProvider.class);


    @Override
    public List getAllSchemas() {
        try {
            Session session = this.getSession();
            NativeQuery query = session.createSQLQuery(Constants.SQL_ALL_SCHEMAS);
            List resList = query.getResultList();
            log.debug(resList!=null?resList.size():null);
            session.close();
            return resList;
        }
        catch (IOException e) {
            log.error(e);
            return null;
        }
    }

    private Session getSession() throws IOException {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        return factory.openSession();
    }
}
