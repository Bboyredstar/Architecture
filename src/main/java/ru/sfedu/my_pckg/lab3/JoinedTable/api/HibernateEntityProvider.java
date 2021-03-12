package ru.sfedu.my_pckg.lab3.JoinedTable.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab3.JoinedTable.model.Student;
import ru.sfedu.my_pckg.lab3.JoinedTable.model.Teacher;
import ru.sfedu.my_pckg.utils.HibernateUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

public class HibernateEntityProvider implements ITestEntityDataProvider {
    public static Logger log = LogManager.getLogger(HibernateEntityProvider.class);
    static Session session;

    @Override
    public Long createStudent(String fname, String lname, int age,
                              String email, String country, String preferences) {
        Student stud = new Student();
        stud.setFirstName(fname);
        stud.setSecondName(lname);
        stud.setAge(age);
        stud.setEmail(email);
        stud.setCountry(country);
        stud.setPreferences(preferences);
        log.debug("Initializing bean:" + stud);
        Long id = this.save(stud);
        log.debug("Saving student with id: " + id);
        return id;
    }

    @Override
    public Long createTeacher(String fname,String lname, int age,
                               String email,String country,String competence,
                               int experience){
        Teacher teacher = new Teacher();
        teacher.setFirstName(fname);
        teacher.setSecondName(lname);
        teacher.setAge(age);
        teacher.setEmail(email);
        teacher.setCountry(country);
        teacher.setCompetence(competence);
        teacher.setExperience(experience);
        log.debug("Initializing bean:" + teacher);
        Long id = this.save(teacher);
        log.debug("Saving teacher with id: " + id);
        return id;
    }

    @Override
    public Status updateStudent(Long id,String fname, String lname, int age,
                                String email, String country, String preferences) {
        try {
            log.debug("In updateStudent method");
            Student stud = getByID(Student.class, id).get();;
            stud.setFirstName(fname);
            stud.setSecondName(lname);
            stud.setAge(age);
            stud.setEmail(email);
            stud.setCountry(country);
            stud.setPreferences(preferences);
            this.update(stud);
            return Status.SUCCESSFUL;
        } catch (NoSuchElementException e) {
            log.error("Updating bean error");
            return Status.FAIL;
        }

    }

    @Override
    public Status updateTeacher(Long id,String fname,String lname, int age,
                                String email,String country,String competence,
                                int experience) {
        try {
            log.debug("In updateTeacher method");
            Teacher teacher = getByID(Teacher.class, id).get();
            teacher.setFirstName(fname);
            teacher.setSecondName(lname);
            teacher.setAge(age);
            teacher.setEmail(email);
            teacher.setCountry(country);
            teacher.setCompetence(competence);
            teacher.setExperience(experience);
            this.update(teacher);
            return Status.SUCCESSFUL;
        } catch (NoSuchElementException e) {
            log.error("Updating bean error");
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


    public Status deleteStudent(Long Id) {
        log.debug("On deleteStudent method");
        try {
            session = this.getSession();
            Student student = this.getByID(Student.class,Id).get();
            Transaction transaction = session.beginTransaction();
            session.delete(student);
            transaction.commit();
            return Status.SUCCESSFUL;
        } catch (IOException | NoSuchElementException e) {
            log.error(e);
            return Status.FAIL;

        } finally {
            if (session != null) session.close();
        }
    }

    public Status deleteTeacher(Long Id) {
        log.debug("On deleteTeacher method");
        try {
            session = this.getSession();
            Teacher teacher = this.getByID(Teacher.class,Id).get();
            Transaction transaction = session.beginTransaction();
            session.delete(teacher);
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

}


