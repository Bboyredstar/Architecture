package ru.sfedu.my_pckg.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.my_pckg.Constants;
import ru.sfedu.my_pckg.beans.*;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.utils.ConfigurationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataProviderJDBC {
    private final String URL = ConfigurationUtil.getConfigurationEntry("URL");
    private final String DEFAULT_URL = "jdbc:h2:./src/main/resources/db/test";
    private final String USER = ConfigurationUtil.getConfigurationEntry("USER");
    private final String DEFAULT_USER = "user";
    private final String PASSWORD = ConfigurationUtil.getConfigurationEntry("PASSWORD");
    private final String DEFAULT_PASSWORD = "12345";
    private final String DRIVER = ConfigurationUtil.getConfigurationEntry("DRIVER");
    private final String DEFAULT_DRIVER = "org.h2.Driver";
    public static Logger log = LogManager.getLogger(DataProviderJDBC.class);


    public DataProviderJDBC() throws IOException {}
    public void initDataSource() {
        try {
            Connection connection = initConnection();
            String createDb = new String(Files.readAllBytes(Paths.get(Constants.INIT)));
            Statement st = connection.createStatement();
            st.executeUpdate(createDb);
            st.close();
            connection.close();
        } catch (SQLException | IOException e) {
            log.fatal(Constants.CONNECTION_ERROR);
            log.fatal(e);
            System.exit(1);
        }
    }

    public Connection initConnection() throws SQLException {
        String url = (URL == null) ? DEFAULT_URL : URL;
        String user = (USER == null) ? DEFAULT_USER : USER;
        String password = (PASSWORD == null) ? DEFAULT_PASSWORD : PASSWORD;
        String driver = (DRIVER == null) ? DEFAULT_DRIVER : DRIVER;
        log.debug(driver);
        log.debug(url);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            log.error(e);
            log.fatal(Constants.DRIVER_ERROR + driver);
            System.exit(1);
        }
        log.debug(Constants.INITIALIZING_CONNECTION);
        Connection connection = DriverManager.getConnection(url, user, password);
        log.debug(Constants.SUCCESSFUL_CONNECTION);
        return connection;
    }

    public <T> List<T> getRecords(Class<T> cl) {
        try {
            Connection connection = initConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(Constants.SELECT_ALL + cl.getSimpleName().toUpperCase());
            List records  = new ArrayList<>();
            while (rs.next()) {
                records.add(dataFromResultSet(rs, cl.getSimpleName()));
            }
            connection.close();
            statement.close();
            return records;
        } catch (Exception e) {
            log.fatal(Constants.GETTING_ERROR);
            log.fatal(e);
        }
        return Collections.emptyList();
    }

    public Object dataFromResultSet(ResultSet rs, String cl) throws SQLException {
        switch(cl){
            case Constants.TEACHER:
                Teacher teacher = new Teacher();
                teacher.setId(rs.getLong(1));
                teacher.setFirstName(rs.getString(2));
                teacher.setSecondName(rs.getString(3));
                teacher.setEmail(rs.getString(4));
                teacher.setAge(rs.getInt(5));
                teacher.setCountry(rs.getString(6));
                teacher.setCompetence(rs.getString(7));
                teacher.setExperience(rs.getInt(8));
                return teacher;
            case Constants.STUDENT:
                Student student = new Student();
                student.setId(rs.getLong(1));
                student.setFirstName(rs.getString(2));
                student.setSecondName(rs.getString(3));
                student.setEmail(rs.getString(4));
                student.setAge(rs.getInt(5));
                student.setCountry(rs.getString(6));
                student.setPreferences(rs.getString(7));
                return student;
            case Constants.COURSE:
                Course course = new Course();
                course.setId(rs.getLong(1));
                course.setOwner(rs.getLong(2));
                course.setName(rs.getString(3));
                course.setDescription(rs.getString(4));
                course.setStudents((List<Long>) rs.getArray(5));
                return course;
            case Constants.SECTION:
                Section section = new Section();
                section.setId(rs.getLong(1));
                section.setCourse(rs.getLong(2));
                section.setName(rs.getString(3));
                section.setDescription(rs.getString(4));
                section.setMaterials((List<String>)rs.getArray(5));
                section.setVideos((List<String>)rs.getArray(6));
                return section;
            case Constants.COURSE_ACTIVITY:
                CourseActivity courseActivity = new CourseActivity();
                courseActivity.setId(rs.getLong(1));
                courseActivity.setCourse(rs.getLong(2));
                courseActivity.setStudent(rs.getLong(3));
                courseActivity.setQuestions((List<Long>)rs.getArray(4));
                courseActivity.setReview(rs.getLong(5));
                return courseActivity;
            case Constants.REVIEW:
                Review review = new Review();
                review.setId(rs.getLong(1));
                review.setRating(rs.getInt(2));
                review.setComment(rs.getString(3));
                return review;
            case Constants.QUESTION:
                Question question = new Question();
                question.setId(rs.getLong(1));
                question.setQuestion(rs.getString(2));
                return question;
            case Constants.ANSWER:
                Answer answer = new Answer();
                answer.setId(rs.getLong(1));
                answer.setQuestion(rs.getLong(2));
                answer.setAnswer(rs.getString(3));
                return answer;
        }
        return null;
    }

    public Status insertTeacher(List<Teacher> teachers) {
        try{
            Connection connection = initConnection();
            PreparedStatement pst = connection.prepareStatement(Constants.TEACHER_INSERT);
            for (Teacher teacher:teachers) {
                pst.setLong(1,teacher.getId());
                pst.setString(2,teacher.getFirstName());
                pst.setString(3,teacher.getSecondName());
                pst.setString(4,teacher.getEmail());
                pst.setInt(5,teacher.getAge());
                pst.setString(6,teacher.getCountry());
                pst.setString(7,teacher.getCompetence());
                pst.setInt(8,teacher.getExperience());
                pst.execute();
                log.debug(teacher);
                log.info(Constants.CREATING_SUCCESS);
                return Status.SUCCESSFUL;
            }
        } catch (SQLException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
        return Status.FAIL;
    }

    public Status insertStudent(List<Student> students) {
        try{
            Connection connection = initConnection();
            PreparedStatement pst = connection.prepareStatement(Constants.STUDENT_INSERT);
            for (Student student:students) {
                pst.setLong(1,student.getId());
                pst.setString(2,student.getFirstName());
                pst.setString(3,student.getSecondName());
                pst.setString(4,student.getEmail());
                pst.setInt(5,student.getAge());
                pst.setString(6,student.getCountry());
                pst.setString(7,student.getPreferences());
                pst.execute();
                log.debug(student);
                log.info(Constants.CREATING_SUCCESS);
                return Status.SUCCESSFUL;
            }
        } catch (SQLException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
        return Status.FAIL;
    }

    public boolean isExistRecord(Class cl,long id,Connection connection) {
        try {
            Statement st = connection.createStatement();
            return st.execute(Constants.SELECT_ALL + cl.getSimpleName().toUpperCase() + Constants.WHERE_ID + id);
        } catch (SQLException e) {
            log.error(e);
            return false;
        }
    }

//    public Status insertCourse(List<Course> courses) {
//        try{
//            Connection connection = initConnection();
//            PreparedStatement pst = connection.prepareStatement(Constants.STUDENT_INSERT);
//            for (Course course:courses) {
//                pst.setLong(1,student.getId());
//                pst.setString(2,student.getFirstName());
//                pst.setString(3,student.getSecondName());
//                pst.setString(4,student.getEmail());
//                pst.setInt(5,student.getAge());
//                pst.setString(6,student.getCountry());
//                pst.setString(7,student.getPreferences());
//                pst.execute();
//                log.debug(student);
//                log.info(Constants.CREATING_SUCCESS);
//                return Status.SUCCESSFUL;
//            }
//        } catch (SQLException e) {
//            log.error(e);
//            log.error(Constants.CREATING_ERROR);
//            return Status.FAIL;
//        }
//        return Status.FAIL;
//    }


    public Status deleteRecords(Class cl){
        try{
            Connection connection = initConnection();
            Statement st = connection.createStatement();
            st.execute(Constants.DElETE+cl.getSimpleName().toUpperCase());
            return Status.SUCCESSFUL;
        } catch (SQLException e) {
            log.error(e);
            log.error(Constants.DELETING_ERROR);
            return Status.FAIL;
        }
    }

    public Status deleteRecord(Class cl, long id){
        try{
            Connection connection = initConnection();
            Statement st = connection.createStatement();
            st.execute(Constants.DElETE+cl.getSimpleName().toUpperCase() + Constants.WHERE_ID+id);
            return Status.SUCCESSFUL;
        } catch (SQLException e) {
            log.error(e);
            log.error(Constants.DELETING_ERROR);
            return Status.FAIL;
        }
    }




}

