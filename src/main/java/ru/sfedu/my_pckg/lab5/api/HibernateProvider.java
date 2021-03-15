package ru.sfedu.my_pckg.lab5.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import ru.sfedu.my_pckg.Constants;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab5.model.*;
import ru.sfedu.my_pckg.utils.ConfigurationUtil;
import ru.sfedu.my_pckg.utils.HibernateUtil;
import ru.sfedu.my_pckg.utils.helpers.Helper;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class HibernateProvider implements  IHibernateProvider{

    public static Logger log = LogManager.getLogger(HibernateProvider.class);
    static Session session;


    public Long createEntity(String classname, List<String> parameters) {
        Long id;
        switch (classname.trim()) {
           case (Constants.TEACHER):
                try {
                    id = createTeacher(parameters.get(0),parameters.get(1),Integer.parseInt(parameters.get(2)),
                            parameters.get(3),parameters.get(4),parameters.get(5), Integer.parseInt(parameters.get(6)));
                    log.debug("Returned value id: "+ id);
                    return id;
                }
                catch (NoSuchElementException | NullPointerException | TypeNotPresentException e){
                    log.error(e);
                    return null;
                }
            case (Constants.STUDENT):
                try {
                    id = createStudent(parameters.get(0),parameters.get(1),Integer.parseInt(parameters.get(2)),
                            parameters.get(3),parameters.get(4),parameters.get(5));
                    log.debug("Returned value id: "+ id);
                    return id;
                }
                catch (NoSuchElementException | NullPointerException | TypeNotPresentException e){
                    log.error(e);
                    return null;
                }
            case (Constants.COURSE):
                try {
                    id = createCourse(parameters.get(0),Long.valueOf(parameters.get(1)),parameters.get(2));
                    log.debug("Returned value id: "+ id);
                    return id;
                }
                catch (NoSuchElementException | NullPointerException | TypeNotPresentException e){
                    log.error(e);
                    return null;
                }
            case (Constants.SECTION):
                try {
                    id = createSection(parameters.get(0),parameters.get(1),Long.valueOf(parameters.get(2)), Helper.stringToListString(parameters.get(3)),Helper.stringToListString(parameters.get(4)));
                    log.debug("Returned value id: "+ id);
                    return id;
                }
                catch (NoSuchElementException | NullPointerException | TypeNotPresentException e){
                    log.error(e);
                    return null;
                }
            case (Constants.REVIEW):
                try {
                    id = createReview(Long.valueOf(parameters.get(0)),Integer.parseInt(parameters.get(1)),parameters.get(2));
                    log.debug("Returned value id: "+ id);
                    return id;
                }
                catch (NoSuchElementException | NullPointerException | TypeNotPresentException e){
                    log.error(e);
                    return null;
                }
            case (Constants.QUESTION):
                try {
                    id = createQuestion(parameters.get(0),Long.valueOf(parameters.get(1)),Long.valueOf(parameters.get(2)));
                    log.debug("Returned value id: "+ id);
                    return id;
                }
                catch (NoSuchElementException | NullPointerException | TypeNotPresentException e){
                    log.error(e);
                    return null;
                }
            case (Constants.ANSWER):
                try {
                    id = createAnswer(parameters.get(0),Long.valueOf(parameters.get(1)));
                    log.debug("Returned value id: "+ id);
                    return id;
                }
                catch (NoSuchElementException | NullPointerException | TypeNotPresentException e){
                    log.error(e);
                    return null;
                }
            default:
                log.error("Bad argument!");
                return null;
        }
    }


    public Status updateEntity(String classname, List<String> parameters) {
        Status status;
        switch (classname.trim()) {
            case (Constants.TEACHER):
                try {
                    status = updateTeacher(Long.valueOf(parameters.get(0)),parameters.get(1),parameters.get(2),Integer.parseInt(parameters.get(3)),
                            parameters.get(4),parameters.get(5),parameters.get(6), Integer.parseInt(parameters.get(7)));
                    log.debug("Returned status: "+ status);
                    return status;
                }
                catch (NoSuchElementException | NullPointerException | TypeNotPresentException e){
                    log.error(e);
                    return Status.FAIL;
                }
            case (Constants.STUDENT):
                try {
                    status = updateStudent(Long.valueOf(parameters.get(0)),parameters.get(1),parameters.get(2),Integer.parseInt(parameters.get(3)),
                            parameters.get(4),parameters.get(5),parameters.get(6));
                    log.debug("Returned status: "+ status);
                    return status;
                }
                catch (NoSuchElementException | NullPointerException | TypeNotPresentException e){
                    log.error(e);
                    return Status.FAIL;
                }
            case (Constants.COURSE):
                try {
                    status = updateCourse(Long.valueOf(parameters.get(0)),parameters.get(1),parameters.get(2));
                    log.debug("Returned status: "+ status);
                    return status;
                }
                catch (NoSuchElementException | NullPointerException | TypeNotPresentException e){
                    log.error(e);
                    return Status.FAIL;
                }
            case (Constants.SECTION):
                try {
                    status = updateSection(Long.valueOf(parameters.get(0)),parameters.get(1), parameters.get(2),Helper.stringToListString(parameters.get(3)),Helper.stringToListString(parameters.get(4)));
                    log.debug("Returned status: "+ status);
                    return status;
                }
                catch (NoSuchElementException | NullPointerException | TypeNotPresentException e){
                    log.error(e);
                    return Status.FAIL;
                }
            case (Constants.REVIEW):
                try {
                    status = updateReview(Long.valueOf(parameters.get(0)),Integer.parseInt(parameters.get(1)),parameters.get(2));
                    log.debug("Returned status: "+ status);
                    return status;
                }
                catch (NoSuchElementException | NullPointerException | TypeNotPresentException e){
                    log.error(e);
                    return Status.FAIL;
                }
            case (Constants.QUESTION):
                try {
                    status = updateQuestion(Long.valueOf(parameters.get(0)),parameters.get(2));
                    log.debug("Returned status: "+ status);
                    return status;
                }
                catch (NoSuchElementException | NullPointerException | TypeNotPresentException e){
                    log.error(e);
                    return Status.FAIL;
                }
            case (Constants.ANSWER):
                try {
                    status = updateAnswer(Long.valueOf(parameters.get(0)),parameters.get(1));
                    log.debug("Returned status: "+ status);
                    return status;
                }
                catch (NoSuchElementException | NullPointerException | TypeNotPresentException e){
                    log.error(e);
                    return Status.FAIL;
                }
            default:
                log.error("Bad argument!");
                return Status.FAIL;
        }
    }



    public Long createStudent(String fname, String lname, int age,
                              String email, String country, String preferences) {
        Student stud = new Student();
        if (!checkName(fname) && !checkName(lname)) {
            log.error(Constants.BAD_NAME);
            return null;
        }
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

    public Status updateStudent(Long id,String fname, String lname, int age,
                              String email, String country, String preferences) {
        if (!checkName(fname) && !checkName(lname)) {
            log.error(Constants.BAD_NAME);
            return Status.FAIL;
        }
        Student student;
        try {
            student = getByID(Student.class, id).get();
        }
        catch(NoSuchElementException e){
            log.error("Problem with Student Id!");
            return Status.FAIL;
        }
        student.setFirstName(fname);
        student.setSecondName(lname);
        student.setAge(age);
        student.setEmail(email);
        student.setCountry(country);
        student.setPreferences(preferences);
        log.debug("Updating bean:" + student);
        this.update(student);
        return Status.SUCCESSFUL;
    }

    public Long createTeacher(String fname, String lname, int age,
                              String email, String country, String competence,
                              int experience) {
        if (!checkName(fname) && !checkName(lname)) {
            log.error(Constants.BAD_NAME);
            return null;
        }
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

    public Status updateTeacher(Long id,String fname, String lname, int age,
                              String email, String country, String competence,
                              int experience) {
        if (!checkName(fname) && !checkName(lname)) {
            log.error(Constants.BAD_NAME);
            return Status.FAIL;
        }
        Teacher teacher;
        try {
            teacher = getByID(Teacher.class, id).get();
        }
        catch(NoSuchElementException e){
            log.error("Problem with teacher Id!");
            return Status.FAIL;
        }
        teacher.setFirstName(fname);
        teacher.setSecondName(lname);
        teacher.setAge(age);
        teacher.setEmail(email);
        teacher.setCountry(country);
        teacher.setCompetence(competence);
        teacher.setExperience(experience);
        this.update(teacher);
        log.debug("Updating teacher with id: " + teacher);
        return Status.SUCCESSFUL;
    }

    public Long createReview(Long courseId,int rating, String comment) {
        if (rating < 0 || rating > 5) {
            log.error("Rating must be in [0,5]");
            return null;
        }
        Course course;
        try {
            course = getByID(Course.class, courseId).get();
        } catch (NoSuchElementException e) {
            log.error("Course with this id not found!");
            return null;
        }
        Review review = new Review();
        review.setRating(rating);
        review.setComment(comment);
        review.setCourse(course);
        log.debug("Initializing review: " + review.toString());
        Long reviewId = save(review);
        log.debug("Saving review with id : " +reviewId);
        return reviewId;
    }

    public Status updateReview(Long reviewId,int rating, String comment) {
        if (rating < 0 || rating > 5) {
            log.error("Rating must be in [0,5]");
            return Status.FAIL;
        }
       Review review;
        try {
            review = getByID(Review.class, reviewId).get();
        } catch (NoSuchElementException e) {
            log.error("Review with this id not found!");
            return Status.FAIL;
        }
        review.setRating(rating);
        review.setComment(comment);
        update(review);
        log.debug("Updating  review with id : " +review);
        return Status.SUCCESSFUL;
    }


    public Long createSection(String name, String description, Long courseId,
                              List<String> materials, List<String> videos) {
        if (!checkName(name)) {
            log.error(Constants.BAD_NAME);
            return null;
        }
        Course course;
        try {
            course = getByID(Course.class, courseId).get();
        }
        catch(NoSuchElementException e){
            log.error("Problem with courseId!");
            return null;
        }
        Section section = new Section();
        section.setName(name);
        section.setDescription(description);
        section.setCourse(course);
        section.setMaterials(materials);
        section.setVideos(videos);
        log.debug("Initializing bean:" + section);
        Long id = this.save(section);
        log.debug("Saving teacher with id: " + id);
        return id;
    }

    public Status updateSection(Long id,String name, String description,
                              List<String> materials, List<String> videos) {
        if (!checkName(name)) {
            log.error(Constants.BAD_NAME);
            return Status.FAIL;
        }
        Section section;
        try {
            section = getByID(Section.class, id).get();
        }
        catch(NoSuchElementException e){
            log.error("Problem with id!");
            return Status.FAIL;
        }
        section.setName(name);
        section.setDescription(description);
        section.setMaterials(materials);
        section.setVideos(videos);
        update(section);
        log.debug("Updating  review with id : " +section);
        return Status.FAIL;
    }


    public Long createCourse(String courseName, Long ownerId, String courseDescription) {
        if (!checkName(courseName)) {
            log.error(Constants.BAD_NAME);
            return null;
        }
        Teacher owner;
        try {
            owner = getByID(Teacher.class, ownerId).get();

        }
        catch(NoSuchElementException e){
            log.error("Problem with ownerId!");
            return null;
        }
        Course course = new Course();
        course.setName(courseName);
        course.setOwner(owner);
        course.setDescription(courseDescription);
        log.debug("Initializing object: " + course);
        Long id = this.save(course);
        log.debug("Saving object with id: " + id);
        return id;
    }

    public Status updateCourse(Long id,String courseName,  String courseDescription) {
        if (!checkName(courseName)) {
            log.error(Constants.BAD_NAME);
            return Status.FAIL;
        }
        Course course;
        try {
            course = getByID(Course.class, id).get();
        }
        catch(NoSuchElementException e){
            log.error("Problem with Id!");
            return Status.FAIL;
        }
        course.setName(courseName);
        course.setDescription(courseDescription);
        this.update(course);
        log.debug("Updating object: " + course);
        return Status.SUCCESSFUL;
    }

    public Long createQuestion(String question, Long courseId, Long studentId) {
        if (!checkName(question)) {
            log.error("Empty question is not allowed!");
            return null;
        }
        Course course;
        try {
            course = getByID(Course.class, courseId).get();
        }
        catch(NoSuchElementException e){
            log.error("Problem with Course Id!");
            return null;
        }
        Student student;
        try {
            student = getByID(Student.class, studentId).get();
        }
        catch(NoSuchElementException e){
            log.error("Problem with Student Id!");
            return null;
        }
        Question questionObj = new Question();
        questionObj.setQuestion(question);
        questionObj.setCourse(course);
        questionObj.setStudent(student);
        log.debug("Initializing object: " + questionObj);
        Long id = this.save(questionObj);
        log.debug("Saving object with id: " + id);
        return id;
    }

    public Status updateQuestion(Long id,String question) {
        if (!checkName(question)) {
            log.error("Empty question is not allowed!");
            return Status.FAIL;
        }
        Question questionObj;
        try {
            questionObj = getByID(Question.class, id).get();
        }
        catch(NoSuchElementException e){
            log.error("Problem with Question Id!");
            return Status.FAIL;
        }

        questionObj.setQuestion(question);
        this.update(questionObj);
        log.debug("Updating object: " + questionObj);
        return Status.SUCCESSFUL;
    }

    public Long createAnswer(String answer, Long questionId) {
        if (!checkName(answer)) {
            log.error("Empty answer is not allowed!");
            return null;
        }
        Question question;
        try {
            question = getByID(Question.class, questionId).get();
        }
        catch(NoSuchElementException e){
            log.error("Problem with Question Id!");
            return null;
        }
        Answer answerObj = new Answer();
        answerObj.setQuestion(question);
        answerObj.setAnswer(answer);
        log.debug("Initializing object: " + answerObj.toString());
        Long id = this.save(answerObj);
        log.debug("Saving object with id: " + id);
        return id;
    }

    public Status updateAnswer(Long id,String answer) {
        if (!checkName(answer)) {
            log.error("Empty answer is not allowed!");
            return Status.FAIL;
        }
        Answer answerObj;
        try {
            answerObj = getByID(Answer.class, id).get();
        }
        catch(NoSuchElementException e){
            log.error("Problem with Answer Id!");
            return Status.FAIL;
        }
        answerObj.setAnswer(answer);
        this.update(answerObj);
        log.debug("Updating object : " + answerObj);
        return Status.SUCCESSFUL;
    }


    public Status deleteEntity(Long id,String classname){
        log.debug("On deleteSCourse method");
        try {
            session = this.getSession();
            Transaction transaction = session.beginTransaction();
            switch (classname.trim()){
                case(Constants.TEACHER):
                    Teacher teacher = getByID(Teacher.class,id).get();
                    session.delete(teacher);
                break;
                case(Constants.SECTION):
                    Section section = getByID(Section.class,id).get();
                    session.delete(section);
                break;
                case(Constants.STUDENT):
                    Student student = getByID(Student.class,id).get();
                    session.delete(student);
                break;
                case(Constants.COURSE):
                    Course course = getByID(Course.class,id).get();
                    session.delete(course);
                break;
                case(Constants.ANSWER):
                    Answer answer = getByID(Answer.class,id).get();
                    session.delete(answer);
                break;
                case(Constants.REVIEW):
                    Review review = getByID(Review.class,id).get();
                    session.delete(review);
                break;
                default:
                    return Status.FAIL;
            }
            transaction.commit();
            log.debug(Status.SUCCESSFUL);
            return Status.SUCCESSFUL;
        } catch (IOException | NoSuchElementException e) {
            log.error(e);
            return Status.FAIL;

        } finally {
            if (session != null) session.close();
        }
    }

    public boolean checkName(String name) {
        if (name.trim().isEmpty()) {
            log.error(Constants.BAD_NAME);
            return false;
        }
        if (name.length() < 2) {
            log.error(Constants.BAD_NAME_LENGTH);
            return false;
        }
        return true;
    }

    public Session getSession() throws IOException {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        return factory.openSession();
    }


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

    public <T> Long save(T bean) {
        try {
            session = this.getSession();
            Transaction transaction = session.beginTransaction();
            Long id = (Long) session.save(bean);
            transaction.commit();
            return id;
        } catch (IOException e) {
            log.error(e);
            return null;
        } finally {
            if (session != null) session.close();
        }
    }



    public Connection initConnectionH2() throws SQLException, IOException {
        String url = ConfigurationUtil.getConfigurationEntry("URL");
        String user = ConfigurationUtil.getConfigurationEntry("USER");
        String password = ConfigurationUtil.getConfigurationEntry("PASSWORD");
        String driver = ConfigurationUtil.getConfigurationEntry("DRIVER");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            log.error(e);
            log.fatal(Constants.DRIVER_ERROR + driver);
            System.exit(1);
        }
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public String gettingSummaryInformationHQL(){
        try {
            Connection conn = this.initConnectionH2();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(Constants.NATIVE_SQL);
            StringBuilder records  = new StringBuilder();
            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()) {
                int numColumns = rsmd.getColumnCount();
                for (int i=1; i<=numColumns; i++) {
                    String column_name = rsmd.getColumnName(i);
                    records.append(column_name);
                    records.append(" ");
                    records.append(rs.getObject(column_name));
                    records.append(" \n");
                }
            }
            log.debug(records);
            conn.close();
            return " ";
        }
        catch (SQLException | IOException e){
            log.error(e);
            return null;
        }

    }

    public Connection initConnection() throws SQLException, IOException {
        String url = ConfigurationUtil.getConfigurationEntry("URL_POSTGRES");
        String user = ConfigurationUtil.getConfigurationEntry("USER_POSTGRES");
        String password = ConfigurationUtil.getConfigurationEntry("USER_PASSWORD");
        String driver = ConfigurationUtil.getConfigurationEntry("DRIVER_POSTGRES");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            log.error(e);
            log.fatal(Constants.DRIVER_ERROR + driver);
            System.exit(1);
        }
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public String gettingSummaryInformationNative(){
        try {
            Connection conn = this.initConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(Constants.NATIVE_SQL);
            StringBuilder records  = new StringBuilder();
            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()) {
                int numColumns = rsmd.getColumnCount();
                for (int i=1; i<=numColumns; i++) {
                    String column_name = rsmd.getColumnName(i);
                    records.append(column_name);
                    records.append(" ");
                    records.append(rs.getObject(column_name));
                    records.append(" \n");
                }
            }
            log.debug(records);
            conn.close();
            return " ";
        }
        catch (SQLException | IOException e){
            log.error(e);
            return null;
        }
    }

   public void executeTimeNative(){
       log.debug("START");
       long startTime = System.currentTimeMillis();
       gettingSummaryInformationNative();
       long endTime = System.currentTimeMillis();
       log.debug("END");
       log.debug("Time: "+ String.valueOf(endTime-startTime) +" ms");
   }

    public void executeTimeCriteria(){
        log.debug("START");
        long startTime = System.currentTimeMillis();
        gettingSummaryInformationCriteria();
        long endTime = System.currentTimeMillis();
        log.debug("END");
        log.debug("Time: "+ String.valueOf(endTime-startTime) +" ms");
    }

    public void executeTimeHQL(){
        log.debug("START");
        long startTime = System.currentTimeMillis();
        gettingSummaryInformationHQL();
        long endTime = System.currentTimeMillis();
        log.debug("END");
        log.debug("Time: "+ String.valueOf(endTime-startTime) +" ms");
    }

    public String gettingSummaryInformationCriteria(){
        try{
            Session session = this.getSession();
            Transaction transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Course.class);
            criteria.setProjection(Projections.count("id"));
            List totalCount = criteria.list();
            transaction.commit();
            log.debug("Result: "+totalCount.get(0).toString());
            return totalCount.toString();

        } catch (IOException e) {
            log.error(e);
            return " ";
        }
    }
}

