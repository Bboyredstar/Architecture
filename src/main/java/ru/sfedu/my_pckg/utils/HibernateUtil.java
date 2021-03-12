package ru.sfedu.my_pckg.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.sfedu.my_pckg.Constants;
import ru.sfedu.my_pckg.lab2.model.TestBean;
import ru.sfedu.my_pckg.lab3.MappedSuperclass.model.Student;
import ru.sfedu.my_pckg.lab3.MappedSuperclass.model.Teacher;
import ru.sfedu.my_pckg.lab3.MappedSuperclass.model.User;
import ru.sfedu.my_pckg.lab5.model.Answer;
import ru.sfedu.my_pckg.lab5.model.Question;
import ru.sfedu.my_pckg.lab5.model.Section;

import java.io.File;
import java.io.IOException;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static Logger log = LogManager.getLogger(SessionFactory.class);
    private static final String USER_CONFIG_PATH = System.getProperty("config");
    private static final String SELECTED_DB = System.getProperty("db");
    public static  String filepath;
    public static SessionFactory getSessionFactory() throws IOException {

        if (sessionFactory == null) {
            // loads configuration and mappings
            String db = (SELECTED_DB==null)? Constants.POSTGRES:SELECTED_DB;
            log.debug("Selected DB :" +db);
            switch (db){
                case(Constants.H2):
                    filepath = (USER_CONFIG_PATH ==null) ? (ConfigurationUtil.getConfigurationEntry("PATH_TO_CFG_XML_H2")) :(USER_CONFIG_PATH);
                case (Constants.POSTGRES):
                    filepath =  (USER_CONFIG_PATH ==null) ? (ConfigurationUtil.getConfigurationEntry("PATH_TO_CFG_XML")) :(USER_CONFIG_PATH);
                    break;

            }
            log.debug("File path to hibernate.cfg.cml: "+filepath);
            File file = new File(filepath);
            Configuration configuration = new Configuration().configure(file);
            ServiceRegistry serviceRegistry
                    = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            MetadataSources metadataSources =
                    new MetadataSources(serviceRegistry);
            addEntities(metadataSources);
            Metadata metadata =  metadataSources.getMetadataBuilder().build();
            sessionFactory =   metadata.getSessionFactoryBuilder().build();
        }

        return sessionFactory;
    }

    private static void addEntities(MetadataSources metadataSources){
        metadataSources.addAnnotatedClass(TestBean.class);
        metadataSources.addAnnotatedClass(Teacher.class);
        metadataSources.addAnnotatedClass(Student.class);
        metadataSources.addAnnotatedClass(User.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab3.TablePerClass.model.Teacher.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab3.TablePerClass.model.Student.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab3.TablePerClass.model.User.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab3.SingleTable.model.Teacher.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab3.SingleTable.model.Student.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab3.SingleTable.model.User.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab3.JoinedTable.model.Teacher.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab3.JoinedTable.model.Student.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab3.JoinedTable.model.User.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab4.listCollection.model.Section.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab4.setCollection.model.Section.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab4.mapCollection.model.Section.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab4.componentCollection.model.Course.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab4.componentCollectionMap.model.Section.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab4.componentCollectionMap.model.Course.class);
        metadataSources.addAnnotatedClass(Section.class);
        metadataSources.addAnnotatedClass(Answer.class);
        metadataSources.addAnnotatedClass(Question.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab5.model.Course.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab5.model.Student.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab5.model.Teacher.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab5.model.Section.class);
        metadataSources.addAnnotatedClass(ru.sfedu.my_pckg.lab5.model.Review.class);


    }
}
