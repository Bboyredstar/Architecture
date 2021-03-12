package ru.sfedu.my_pckg.lab4.componentCollectionMap.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sfedu.my_pckg.Constants;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab4.componentCollectionMap.model.Course;
import ru.sfedu.my_pckg.lab4.componentCollectionMap.model.Section;
import ru.sfedu.my_pckg.utils.HibernateUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;


public class HibernateEntityProvider implements ITestEntityDataProvider {
    public static Logger log = LogManager.getLogger(HibernateEntityProvider.class);
    static Session session;



    @Override
    public Long createCourse(String courseName,String courseDescription,Map<String,Section> sections){
        if(!checkName(courseName)){
            log.error("Names can't be empty or less 2 symbols!");
            return null;
        }
        Course course = new Course();
        course.setName(courseName);
        course.setDescription(courseDescription);
        course.setSections(sections);
        log.info("Initializing object: "+course);
        Long id = this.save(course);
        log.debug("Saving object with id: "+id);
        return id;
    }

    @Override
    public Status deleteCourse(Long Id) {
        log.debug("On deleteCourse method");
        try {
            session = this.getSession();
            Course course = this.getByID(Course.class,Id).get();
            Transaction transaction = session.beginTransaction();
            session.delete(course);
            transaction.commit();
            return Status.SUCCESSFUL;
        } catch (IOException | NoSuchElementException e) {
            log.error(e);
            return Status.FAIL;

        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Status updateCourse(Long id,String courseName,String courseDescription,Map<String,Section> sections ) {
        log.info("In updateCourse method");
        if(!checkName(courseName)) {
            return Status.FAIL;
        };
        try {
            Course course = this.getByID(Course.class,id).get();
            course.setName(courseName);
            course.setDescription(courseDescription);
            course.setSections(sections);
            this.update(course);
            return Status.SUCCESSFUL;
        }
        catch (NoSuchElementException e){
            log.error(e);
            return Status.FAIL;
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


