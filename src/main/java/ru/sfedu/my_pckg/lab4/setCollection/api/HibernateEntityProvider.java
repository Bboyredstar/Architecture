package ru.sfedu.my_pckg.lab4.setCollection.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sfedu.my_pckg.Constants;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab4.setCollection.model.Section;
import ru.sfedu.my_pckg.utils.HibernateUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

public class HibernateEntityProvider implements ITestEntityDataProvider {
    public static Logger log = LogManager.getLogger(HibernateEntityProvider.class);
    static Session session;

    @Override
    public Status updateSection(long id, String name, String description, Set<String> videos, Set<String> materials) {
        log.info("In updateSection method");

        if(!checkName(name)) {
            return Status.FAIL;
        };
        try {
            Section section = this.getByID(Section.class, id).get();
            section.setName(name);
            section.setDescription(description);
            section.setMaterials(materials);
            section.setVideos(videos);
            this.update(section);
            return Status.SUCCESSFUL;
        }
        catch (NoSuchElementException e){
            log.error(e);
            return Status.FAIL;
        }
    }

    @Override
    public Long createSection(String name, String description, Set<String> videos, Set<String> materials) {
        log.info("In createSection method");
        if(!checkName(name)){
            return null;
        }
        Section section = new Section();
        section.setName(name);
        section.setDescription(description);
        section.setVideos(videos);
        section.setMaterials(materials);
        log.debug("Initializing section: "+ section);
        long id = this.save(section);
        log.debug("Saving section with id: "+ id);
        return id;
    }

    @Override
    public Status deleteSection(Long Id) {
        log.debug("On deleteStudent method");
        try {
            session = this.getSession();
            Section section = this.getByID(Section.class,Id).get();
            Transaction transaction = session.beginTransaction();
            session.delete(section);
            transaction.commit();
            return Status.SUCCESSFUL;
        } catch (IOException | NoSuchElementException e) {
            log.error(e);
            return Status.FAIL;

        } finally {
            if (session != null) session.close();
        }
    }

    public Session getSession() throws IOException {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        return factory.openSession();
    }


    @Override
    public <T> void update(T bean) {
        try {
            session = this.getSession();
            Transaction transaction = session.beginTransaction();
            session.update(bean);
            transaction.commit();
        } catch (IOException | NonUniqueObjectException e) {
            log.error(e);
        } finally {
            if (session != null) session.close();
        }
    }


    @Override
    @Transactional
    public <T> Optional<T> getByID(Class<T> bean, long id) {
        try {
            Session session = this.getSession();
            T resultBean = session.get(bean, id);
            log.debug("Returned entity: " + resultBean.toString());
            session.close();
            return Optional.of(resultBean);
        } catch (IOException | NullPointerException e) {
            log.error(e);
            return Optional.empty();
        }
    }

    @Override
    public <T>Long save(T bean) {
        try {
            session = this.getSession();
            Transaction transaction = session.beginTransaction();
            Long id = (Long) session.save(bean);
            transaction.commit();
            log.debug("Saving bean with the id: " + id.toString());
            return id;
        } catch (IOException e) {
            log.error(e);
            return null;
        } finally {
            if (session != null) session.close();
        }
    }
    public boolean checkName(String name){
        if(name.trim().isEmpty()){
            log.error(Constants.BAD_NAME);
            return false;
        }
        if (name.length()<2){
            log.error(Constants.BAD_NAME_LENGTH);
            return false;
        }
        return true;
    }
}


