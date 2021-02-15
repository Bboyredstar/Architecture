package ru.sfedu.my_pckg.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.File;
import java.io.IOException;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static Logger log = LogManager.getLogger(SessionFactory.class);

    public static SessionFactory getSessionFactory() throws IOException {
        if (sessionFactory == null) {
            // loads configuration and mappings
            File file = new File(ConfigurationUtil.getConfigurationEntry("PATH_TO_CFG_XML"));
            Configuration configuration = new Configuration().configure(file);
            ServiceRegistry serviceRegistry
                    = new StandardServiceRegistryBuilder()
                    .configure().build();
            MetadataSources metadataSources =
                    new MetadataSources(serviceRegistry);
            Metadata metadata =  metadataSources.getMetadataBuilder().build();
            sessionFactory =   metadata.getSessionFactoryBuilder().build();
        }

        return sessionFactory;
    }
}
