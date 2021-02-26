package ru.sfedu.my_pckg.lab2.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab2.model.EmbeddedEntity;
import ru.sfedu.my_pckg.lab2.model.TestBean;
import ru.sfedu.my_pckg.utils.HibernateUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

public class HibernateEntityProvider implements ITestEntityDataProvider {
    public static Logger log = LogManager.getLogger(HibernateEntityProvider.class);
    static Session session;

    @Override
    public Long createBean(String beanName, String beanDescription, Date date, Boolean check, EmbeddedEntity embeddedEntity) {
        log.debug("In createBean method");
        TestBean bean = new TestBean();
        bean.setName(beanName);
        bean.setDescription(beanDescription);
        bean.setDate(date);
        bean.setChecking(check);
        bean.setTestEntity(embeddedEntity);
        log.debug("Initializing bean:" + bean.toString());
        Long id = this.save(bean);
        log.debug("Saving object with id: " + id);
        return id;
    }

    @Override
    public Status updateBean(Long id, String beanName, String beanDescription, Date date, Boolean check, EmbeddedEntity embeddedEntity) {
        try {
            log.debug("In updateBean method");
            TestBean bean = new TestBean();
            bean = getByID(TestBean.class, id).get();
            bean.setName(beanName);
            bean.setDescription(beanDescription);
            bean.setDate(date);
            bean.setChecking(check);
            bean.setTestEntity(embeddedEntity);
            this.update(bean);
            return Status.SUCCESSFUL;
        } catch (NoSuchElementException e) {
            log.error("Updating bean error");
            return Status.FAIL;
        }

    }


    private Session getSession() throws IOException {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        return factory.openSession();
    }


    @Override
    public void update(TestBean bean) {
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
    public Status delete(Long Id) {
        try {
            session = this.getSession();
            TestBean bean = getByID(TestBean.class, Id).get();
            Transaction transaction = session.beginTransaction();
            session.delete(bean);
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
    public Optional<TestBean> getByID(Class<TestBean> bean, long id) {
        try {
            Session session = this.getSession();
            TestBean testBean = session.get(bean, id);
            log.debug("Returned entity: " + testBean.toString());
            session.close();
            return Optional.of(testBean);
        } catch (IOException | NullPointerException e) {
            log.error(e);
            return Optional.empty();
        }
    }

    @Override
    public Long save(TestBean bean) {
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


