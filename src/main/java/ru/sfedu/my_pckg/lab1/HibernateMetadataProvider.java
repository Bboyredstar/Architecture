package ru.sfedu.my_pckg.lab1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import ru.sfedu.my_pckg.Constants;
import ru.sfedu.my_pckg.utils.HibernateUtil;

import java.io.IOException;
import java.util.List;

public class HibernateMetadataProvider implements IMetaDataProvider {
    public static Logger log = LogManager.getLogger(HibernateMetadataProvider.class);

    @Override
    public String getCurrentUser() {
        try {
            Session session = this.getSession();
            NativeQuery query = session.createSQLQuery(Constants.SQL_CURRENT_USER);
            String user = query.getResultList().get(0).toString();
            log.debug("Current user: "+ user);
            session.close();
            return user;
        } catch (IOException e) {
            log.error(e);
            return null;
        }

    }

    @Override
    public List getTablesSize() {
        try {
            Session session = this.getSession();
            NativeQuery query = session.createSQLQuery(Constants.SQL_SIZE_TABLES);
            List sizes = query.getResultList();
            session.close();
            log.debug("Tables size: "+sizes.toString());
            return sizes;
        }
        catch (IOException e) {
            log.error(e);
            return null;
        }

    }

    @Override
    public List getTablesName() {
        try {
            Session session = this.getSession();
            NativeQuery query = session.createSQLQuery(Constants.SQL_TABLES_NAME);
            List names = query.getResultList();
            session.close();
            log.debug("Tables names: " + names.toString());
            return names;
        }
        catch (IOException e) {
            log.error(e);
            return null;
        }

    }

    @Override
    public String getCatalogName() {
        try {
            Session session = this.getSession();
            NativeQuery query = session.createSQLQuery(Constants.SQL_CATALOG_NAME);
            String catalog = query.getResultList().get(0).toString();
            session.close();
            log.debug("Catalog name: "+catalog);
            return catalog;
        }
        catch (IOException e) {
           log.error(e);
           return null;
        }
    }

    @Override
    public List getAllSchemas() {
        try {
            Session session = this.getSession();
            NativeQuery query = session.createSQLQuery(Constants.SQL_ALL_SCHEMAS);
            List resList = query.getResultList();
            log.debug(resList!=null?resList.size():null);
            log.debug(resList);
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
