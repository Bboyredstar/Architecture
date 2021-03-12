//package ru.sfedu.my_pckg.lab5.manyToMany.api;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.hibernate.*;
//import org.hibernate.criterion.Projections;
//import org.hibernate.criterion.Restrictions;
//import ru.sfedu.my_pckg.Constants;
//import ru.sfedu.my_pckg.enums.Status;
//import ru.sfedu.my_pckg.lab4.mapCollection.model.Section;
//import ru.sfedu.my_pckg.lab5.manyToMany.model.Course;
//import ru.sfedu.my_pckg.lab5.manyToMany.model.CourseActivity;
//import ru.sfedu.my_pckg.utils.ConfigurationUtil;
//import ru.sfedu.my_pckg.utils.HibernateUtil;
//
//import java.io.IOException;
//import java.io.PushbackInputStream;
//import java.sql.*;
//import java.util.*;
//
//public class HibernateProvider implements IHibernateProvider {
//    public static Logger log = LogManager.getLogger(HibernateProvider.class);
//    static Session session;
//
//
//    public Long createCourse(String courseName, String courseDescription, List<CourseActivity> courseActivity) {
//        if (!checkName(courseName)) {
//            log.error(Constants.BAD_NAME);
//            return null;
//        }
//
//        Course course = new Course();
//        course.setName(courseName);
//        course.setDescription(courseDescription);
//        course.setCourseActivities(courseActivity);
//        log.debug("Initializing object: " + course);
//        Long id = this.save(course);
//        log.debug("Saving object with id: " + id);
//        return id;
//    }
//
//    public Status updateCourse(Long id, String newName, String newDescription, List<CourseActivity> courseActivities) {
//        log.debug("In updateCourse method");
//        if (!checkName(newName)) {
//            log.error(Constants.BAD_NAME);
//            return Status.FAIL;
//        }
//        if (checkListNames(courseActivities)) {
//            log.error(Constants.BAD_NAME);
//            return Status.FAIL;
//        }
//        try {
//            Course course = this.getByID(Course.class, id).get();
//            course.setName(newName);
//            course.setDescription(newDescription);
//            course.setCourseActivities(courseActivities);
//            log.debug("Updating bean: " + course.toString());
//            this.update(course);
//            return Status.SUCCESSFUL;
//        } catch (NoSuchElementException e) {
//            log.error(e);
//            return Status.FAIL;
//        }
//    }
//
//    public Status deleteCourse(Long Id) {
//        log.debug("On deleteCourse method");
//        try {
//            session = this.getSession();
//            Course course = this.getByID(Course.class, Id).get();
//            Transaction transaction = session.beginTransaction();
//            session.delete(course);
//            transaction.commit();
//            return Status.SUCCESSFUL;
//        } catch (IOException | NoSuchElementException e) {
//            log.error(e);
//            return Status.FAIL;
//
//        } finally {
//            if (session != null) session.close();
//        }
//    }
//
//
//    public boolean checkName(String name) {
//        if (name.trim().isEmpty()) {
//            log.error(Constants.BAD_NAME);
//            return false;
//        }
//        if (name.length() < 2) {
//            log.error(Constants.BAD_NAME_LENGTH);
//            return false;
//        }
//        return true;
//    }
//
//    public Session getSession() throws IOException {
//        SessionFactory factory = HibernateUtil.getSessionFactory();
//        return factory.openSession();
//    }
//
//
//    public <T> void update(T bean) {
//        try {
//            session = this.getSession();
//            Transaction transaction = session.beginTransaction();
//            session.update(bean);
//            transaction.commit();
//        } catch (IOException | NonUniqueObjectException e) {
//            log.error(e);
//        } finally {
//            if (session != null) session.close();
//        }
//    }
//
//
//    public <T> Optional<T> getByID(Class<T> bean, long id) {
//        try {
//            Session session = this.getSession();
//            T resultBean = session.get(bean, id);
//            log.debug("Returned entity: " + resultBean.toString());
//            session.close();
//            return Optional.of(resultBean);
//        } catch (IOException | NullPointerException e) {
//            log.error(e);
//            return Optional.empty();
//        }
//    }
//
//    public <T> Long save(T bean) {
//        try {
//            session = this.getSession();
//            Transaction transaction = session.beginTransaction();
//            Long id = (Long) session.save(bean);
//            transaction.commit();
//            return id;
//        } catch (IOException e) {
//            log.error(e);
//            return null;
//        } finally {
//            if (session != null) session.close();
//        }
//    }
//
//    public Connection initConnection() throws SQLException, IOException {
//        String url = ConfigurationUtil.getConfigurationEntry("URL_POSTGRES");
//        String user = ConfigurationUtil.getConfigurationEntry("USER_POSTGRES");
//        String password = ConfigurationUtil.getConfigurationEntry("USER_PASSWORD");
//        String driver = ConfigurationUtil.getConfigurationEntry("DRIVER_POSTGRES");
//        try {
//            Class.forName(driver);
//        } catch (ClassNotFoundException e) {
//            log.error(e);
//            log.fatal(Constants.DRIVER_ERROR + driver);
//            System.exit(1);
//        }
//        Connection connection = DriverManager.getConnection(url, user, password);
//        return connection;
//    }
//
//    public String gettingSummaryInformationNative(){
//        try {
//            Connection conn = this.initConnection();
//            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery(Constants.NATIVE_SQL);
//            StringBuilder records  = new StringBuilder();
//            ResultSetMetaData rsmd = rs.getMetaData();
//            while(rs.next()) {
//                int numColumns = rsmd.getColumnCount();
//                for (int i=1; i<=numColumns; i++) {
//                    String column_name = rsmd.getColumnName(i);
//                    records.append(column_name);
//                    records.append(" ");
//                    records.append(rs.getObject(column_name));
//                    records.append(" \n");
//                }
//            }
//            log.debug(records);
//            conn.close();
//            return " ";
//        }
//        catch (SQLException | IOException e){
//            log.error(e);
//            return null;
//        }
//
//    }
//
//    public String gettingSummaryInformationCriteria(){
//        try{
//            Session session = this.getSession();
//            Transaction transaction = session.beginTransaction();
//            Criteria criteria = session.createCriteria(Course.class).createCriteria("courseActivities");
//            criteria.setProjection(Projections.count("id"));
//            List totalCount = criteria.list();
//            transaction.commit();
//            log.debug("Result: "+totalCount.get(0).toString());
//            return totalCount.toString();
//
//        } catch (IOException e) {
//            log.error(e);
//            return " ";
//        }
//    }
//
//
//
//
//
//}
