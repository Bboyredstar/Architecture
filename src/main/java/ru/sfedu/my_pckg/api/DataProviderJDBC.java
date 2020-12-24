package ru.sfedu.my_pckg.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.my_pckg.Constants;
import ru.sfedu.my_pckg.beans.*;
import ru.sfedu.my_pckg.enums.ExtendMethods;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.utils.ConfigurationUtil;
import ru.sfedu.my_pckg.utils.helpers.Helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataProviderJDBC implements AbstractDataProvider {
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

    public Status dataInitialization(){
        try{
            initDataSource();
            Teacher teacher_1 = Helper.createTeacher(1111,Helper.generateUserFName(),Helper.generateUserLName(),
                    Helper.randomNumber(28,65), Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences(),Helper.randomNumber(2,10));
            Teacher teacher_2 = Helper.createTeacher(1222,Helper.generateUserFName(),Helper.generateUserLName(),
                    Helper.randomNumber(28,65), Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences(),Helper.randomNumber(2,10));
            Teacher teacher_3 = Helper.createTeacher(1333,Helper.generateUserFName(),Helper.generateUserLName(),
                    Helper.randomNumber(28,65), Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences(),Helper.randomNumber(2,10));
            if (getRecords(Teacher.class)==null||getRecords(Teacher.class).isEmpty()){
                insertTeacher(Arrays.asList(teacher_1,teacher_2,teacher_3));
            }
            Student student_1 = Helper.createStudent(2112,Helper.generateUserFName(),Helper.generateUserLName(),
                    Helper.randomNumber(28,65), Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences());
            Student student_2 = Helper.createStudent(2113,Helper.generateUserFName(),Helper.generateUserLName(),
                    Helper.randomNumber(28,65), Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences());
            Student student_3 = Helper.createStudent(2114,Helper.generateUserFName(),Helper.generateUserLName(),
                    Helper.randomNumber(28,65), Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences());
            if (getRecords(Student.class)==null||getRecords(Student.class).isEmpty()){
                insertStudent(Arrays.asList(student_1,student_2,student_3));
            }
            if (getRecords(Course.class)==null||getRecords(Course.class).isEmpty()){
                createCourse(3111,"Backend Development","Back end development refers to the server side of an application and everything that communicates between the database and the browser. Back end Development refers to the server side of development where you are primarily focused on how the site works.",
                        1111L,Arrays.asList(2112L,2114L));
                createCourse(3112,"Frontend Development","Front-end web development, also known as client-side development is the practice of producing HTML, CSS and JavaScript for a website or Web Application so that a user can see and interact with them directly.",
                        1222L,Arrays.asList(2112L,2113L));
            }
            if (getRecords(Section.class)==null||getRecords(Section.class).isEmpty()) {
                createSection(4111, "Introduction", "Starting of course", 3111, Helper.randomURLs(3), Helper.randomURLs(4));
                createSection(4222, "Base Skills", "Learning of base skill that need to all developers", 3111, Helper.randomURLs(4), Helper.randomURLs(2));
                createSection(5111, "Introduction", "Small intro in web development", 3112, Helper.randomURLs(2), Helper.randomURLs(6));
                createSection(5222, "What is Frontend", "Basic conceptions of frontend", 3112, Helper.randomURLs(3), Helper.randomURLs(5));
            }
            return Status.SUCCESSFUL;
        }
        catch(Exception e){
            log.error(e);
            return Status.FAIL;
        }
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

    public Object getByID(Class cl, long id) throws SQLException, IndexOutOfBoundsException{
            Connection connection = initConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(Constants.SELECT_ALL + cl.getSimpleName().toUpperCase()+ Constants.WHERE_ID+id);
            List records  = new ArrayList<>();
            while (rs.next()) {
                records.add(dataFromResultSet(rs, cl.getSimpleName()));
            }
            connection.close();
            statement.close();
            return records.get(0);
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
                List<Long> students = new ArrayList<>();
                course.setId(rs.getLong(1));
                course.setOwner(rs.getLong(2));
                course.setName(rs.getString(3));
                course.setDescription(rs.getString(4));
                try{
                    Object[] idsArray = (Object[]) rs.getArray(5).getArray();
                    List<Long> idsList = new ArrayList<>();
                    for(Object obj : idsArray) {
                        idsList.add(((Long) obj));
                    }
                    course.setStudents(idsList);
                }
                catch (Exception e){
                    log.error(e);
                    course.setStudents(new ArrayList<>());
                }
                return course;
            case Constants.SECTION:
                Section section = new Section();
                section.setId(rs.getLong(1));
                section.setCourse(rs.getLong(2));
                section.setName(rs.getString(3));
                section.setDescription(rs.getString(4));
                try{
                    Object[] idsArray = (Object[]) rs.getArray(5).getArray();
                    List<String> materials = new ArrayList<>();
                    for(Object obj : idsArray) {
                        materials.add(((String) obj));
                    }
                    section.setMaterials(materials);
                }
                catch (Exception e){
                    section.setMaterials(new ArrayList<>());
                }
                try{
                    Object[] idsArray = (Object[]) rs.getArray(6).getArray();
                    List<String> videos = new ArrayList<>();
                    for(Object obj : idsArray) {
                        videos.add(((String) obj));
                    }
                    section.setVideos(videos);
                }
                catch (Exception e){
                    section.setVideos(new ArrayList<>());
                }
                return section;
            case Constants.COURSE_ACTIVITY:
                CourseActivity courseActivity = new CourseActivity();
                courseActivity.setId(rs.getLong(1));
                courseActivity.setCourse(rs.getLong(2));
                courseActivity.setStudent(rs.getLong(3));
                try{
                    Object[] idsArray = (Object[]) rs.getArray(4).getArray();
                    List<Long> questions = new ArrayList<>();
                    for(Object obj : idsArray) {
                        questions.add(((Long) obj));
                    }
                    log.debug(questions);
                    courseActivity.setQuestions(questions);
                }
                catch (Exception e){
                    log.error(e);
                    courseActivity.setQuestions(new ArrayList<>());
                }
                log.debug(rs.getLong(5));
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
            }
            connection.close();
            pst.close();
            log.info(Constants.CREATING_SUCCESS);
            return Status.SUCCESSFUL;
        } catch (SQLException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
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
            }
            connection.close();
            pst.close();
            log.info(Constants.CREATING_SUCCESS);
            return Status.SUCCESSFUL;
        } catch (SQLException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
    }


    public Status createCourse(long id, String name, String description, Long ownerId, List<Long> students){
        Course course = new Course();
        if (!checkName(name)){
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
        course.setId(id);
        course.setOwner(ownerId);
        course.setName(name);
        course.setDescription(description);
        course.setStudents(students);
        return insertCourse(Collections.singletonList(course));
    }

    public Status updateCourse(long courseId, String courseName,
                               String courseDescription,
                               long sectionId, String sectionName, String sectionDescription,
                               List<String> sectionMaterials, List<String> sectionVideos,
                               String extendMethod){
        try{
            Connection connection = initConnection();
            PreparedStatement pst = connection.prepareStatement(Constants.COURSE_UPDATE+courseId);
            Course course = (Course) getByID(Course.class,courseId);
            courseName = (courseName.trim().equals(""))?course.getName():courseName;
            courseDescription = (courseDescription.trim().equals(""))?course.getDescription():courseDescription;
            pst.setString(1,courseName);
            pst.setString(2,courseDescription);
            pst.execute();
            connection.close();
            pst.close();
            if (extendMethod.trim().equals("")){
                log.info(Constants.UPDATING_SUCCESS);
                return Status.SUCCESSFUL;
            }
            switch(ExtendMethods.valueOf(extendMethod.trim().toUpperCase())){
                case CREATE:
                    long id = Helper.createId();
                    return createSection(id,sectionName,sectionDescription,
                            courseId,sectionMaterials,sectionVideos);
                case UPDATE:
                    return updateSection(sectionId,sectionName,sectionDescription,sectionVideos,sectionMaterials);
                case DELETE:
                    return deleteSection(sectionId);
            }
            log.info(Constants.UPDATING_SUCCESS);
            return Status.SUCCESSFUL;
        } catch (SQLException | IndexOutOfBoundsException| NoSuchElementException e) {
           log.error(e);
           log.error(Constants.UPDATING_ERROR);
           return Status.FAIL;
        }
    }

    public Status joinCourse(long courseId, long studentId){
        try {
            Connection connection = initConnection();
            PreparedStatement pst = connection.prepareStatement(Constants.JOIN_UPDATE + courseId);
            Course course = (Course) getByID(Course.class,courseId);
            if(!isExistRecord(Student.class,studentId,connection)){
                log.error(Constants.USER_NOT_JOIN);
                return Status.FAIL;
            }
            List<Long> students = course.getStudents();
            students.add(studentId);
            if(!checkDuplicate(students)){
                log.info(Constants.USER_ALREADY_JOINED);
                return Status.FAIL;
            }
            pst.setArray(1,connection.createArrayOf(Constants.BIGINT,students.toArray()));
            pst.execute();
            log.debug(students);
            log.info(Constants.JOINING_SUCCESS);
            return Status.SUCCESSFUL;
        } catch (SQLException | IndexOutOfBoundsException e) {
            log.error(e);
            log.error(Constants.IDS_ERROR);
            log.error(Constants.JOINING_ERROR);
            return Status.FAIL;
        }
    }

    public Status leaveAReviewAboutCourse(long courseId,long studentId,int rating,String comment) {
        try {
            Connection connection = initConnection();
            Course course = (Course) getByID(Course.class, courseId);
            Student student = (Student) getByID(Student.class, studentId);
            List<CourseActivity> activities = this.<CourseActivity>getRecords(CourseActivity.class)
                    .stream()
                    .filter(el -> el.getCourse() == courseId)
                    .filter(el -> el.getStudent() == studentId)
                    .collect(Collectors.toList());
            CourseActivity activity = (activities.isEmpty()) ? createCourseActivity(courseId, studentId, course.getStudents()) : activities.get(0);
            if (activity == null) {
                log.error(Constants.USER_NOT_JOIN);
                return Status.FAIL;
            }
             if (activity.getReview() != -1){
                 log.error(Constants.REVIEW_ALREADY_EXIST);
                 return Status.FAIL;
             }
             Review review = createReview( rating,comment);
             if(review==null){
                 log.info(Constants.CREATING_ERROR);
                 return Status.FAIL;
             }
             log.debug(review);
             PreparedStatement pst = connection.prepareStatement(Constants.REVIEW_UPDATE);
             pst.setLong(1,review.getId());
             pst.setLong(2,activity.getId());
             pst.execute();
             connection.close();
             pst.close();
             log.info(Constants.CREATING_SUCCESS);
             return Status.SUCCESSFUL;

        } catch (SQLException | IndexOutOfBoundsException e) {
            log.error(e);
            log.error(Constants.IDS_ERROR);
            log.error(Constants.USER_NOT_JOIN);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
    }

    public Status insertCourse(List<Course> courses) {
        try{
            Connection connection = initConnection();
            PreparedStatement pst = connection.prepareStatement(Constants.COURSE_INSERT);
            for (Course course:courses) {
                List<Long> students = (course.getStudents()==null)?new ArrayList<>():course.getStudents();
                if(!isExistRecord(Teacher.class,course.getOwner(),connection)){
                    log.error(Constants.IDS_ERROR);
                    return Status.FAIL;
                }
                if(!checkIds(students,connection,Student.class)){
                    log.error(Constants.IDS_ERROR);
                    return Status.FAIL;
                }
                if(!checkDuplicate(students)){
                    log.error(Constants.HAS_DUPLICATE);
                    return Status.FAIL;
                }
                pst.setLong(1,course.getId());
                pst.setLong(2,course.getOwner());
                pst.setString(3,course.getName());
                pst.setString(4,course.getDescription());
                pst.setArray(5,connection.createArrayOf(Constants.BIGINT,students.toArray()));
                pst.execute();
                log.debug(course);
            }
            log.info(Constants.CREATING_SUCCESS);
            connection.close();
            pst.close();
            return Status.SUCCESSFUL;

        } catch (SQLException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
    }

    public List<Review> checkCourseReviews(long courseId){
        try{
            Connection connection = initConnection();
            if(! isExistRecord(Course.class,courseId,connection)){
                log.error(Constants.IDS_ERROR);
                return null;
            }
            List<CourseActivity> courseActivities = this.<CourseActivity>getRecords(CourseActivity.class)
                    .stream().filter(el->el.getCourse()==courseId).collect(Collectors.toList());
            if(courseActivities.isEmpty()){
                log.warn(Constants.LIST_EMPTY);
                return new ArrayList<>();
            }
            connection.close();
            List<Long> reviewsId = courseActivities.stream().map(CourseActivity::getReview).collect(Collectors.toList());
            List<Review> reviews = this.<Review>getRecords(Review.class);
            reviews.stream().filter(el->reviewsId.contains(el.getId())).collect(Collectors.toList());
            return reviews;
        } catch ( NoSuchElementException | SQLException e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    public Status createSection(long id, String name, String description,
                                long course, List<String> videos, List<String> materials){

        Section section = new Section();
        if (!checkName(name)){
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
        section.setId(id);
        section.setName(name);
        section.setDescription(description);
        section.setCourse(course);
        section.setVideos(videos);
        section.setMaterials(materials);
        return insertSection(Collections.singletonList(section));
    }

    public Status insertSection(List<Section> sections) {
        try{
            Connection connection = initConnection();
            PreparedStatement pst = connection.prepareStatement(Constants.SECTION_INSERT);
            for (Section section:sections) {
                if(!isExistRecord(Course.class,section.getCourse(),connection)){
                    log.error(Constants.IDS_ERROR);
                    return Status.FAIL;
                }
                pst.setLong(1,section.getId());
                pst.setLong(2,section.getCourse());
                pst.setString(3,section.getName());
                pst.setString(4,section.getDescription());
                pst.setArray(5,connection.createArrayOf(Constants.VARCHAR,section.getMaterials().toArray()));
                pst.setArray(6,connection.createArrayOf(Constants.VARCHAR,section.getVideos().toArray()));
                pst.execute();
                log.debug(section);
            }
            connection.close();
            pst.close();
            log.info(Constants.CREATING_SUCCESS);
            return Status.SUCCESSFUL;
        } catch (SQLException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
    }

    public String chooseCourse(long courseId, long studentId, String extendMethod) {
        List<Course> courses;
        try {
            courses = this.<Course>getRecords(Course.class);
            log.info(courses.toString());
            if(courseId==-1 || studentId==-1 || extendMethod.trim().isEmpty()){
                return courses.toString();
            }
            switch (ExtendMethods.valueOf(extendMethod.trim().toUpperCase())) {
                case JOIN:
                    return joinCourse(courseId,studentId).toString();
                case REVIEW:
                    return checkCourseReviews(courseId).toString();
            }
            return courses.toString();
        } catch ( NoSuchElementException e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    public Status deleteSection(long id){
        return deleteRecord(Section.class,id);
    }

    public Status deleteCourse(long id){
        return deleteRecord(Course.class,id);
    }


    public String viewCourse(long id, String extendMethod) {
        try {
            String course = getByID(Course.class,id).toString();
            log.info(course);
            if (extendMethod.trim().equals("")) {
                return course;
            }
            switch (ExtendMethods.valueOf(extendMethod.trim().toUpperCase())) {
                case RATING:
                    double avgRating = getCourseRating(id);
                    log.info(avgRating);
                    return Double.toString(avgRating);
                case COMMENTS:
                    List<String> comments = getCourseComments(id);
                    log.info(comments.toString());
                    return comments.toString();
                default:
                    log.info(Constants.EXTEND_ERROR);
                    break;
                }
            return course;
        }
        catch(NoSuchElementException | IndexOutOfBoundsException |SQLException e){
            log.error(e);
            log.error(Constants.GETTING_BY_ID_FAIL+id);
            return null;
        }
    }



    public Status updateSection(long id, String name,
                                String description,  List<String> videos, List<String> materials){
        try{
            Connection connection = initConnection();
            PreparedStatement pst = connection.prepareStatement(Constants.SECTION_UPDATE+id);
            Section section = (Section) getByID(Section.class,id);
            name = (name.trim().equals(""))?section.getName():name;
            description = (description.trim().equals(""))?section.getDescription():description;
            videos = (videos == null)? section.getVideos(): videos;
            materials = (materials == null)? section.getMaterials():materials;
            pst.setString(1,name);
            pst.setString(2,description);
            pst.setArray(3,connection.createArrayOf(Constants.VARCHAR,materials.toArray()));
            pst.setArray(4,connection.createArrayOf(Constants.VARCHAR,videos.toArray()));
            pst.execute();
            connection.close();
            pst.close();
            log.info(Constants.UPDATING_SUCCESS);
            return Status.SUCCESSFUL;
        } catch (SQLException | IndexOutOfBoundsException e) {
            log.error(e);
            log.error(Constants.UPDATING_ERROR);
        }
        return Status.SUCCESSFUL;
    }

    public Status unsubscribeFromACourse(long courseId,long studentId){
        try{

            Connection connection = initConnection();
            if(!isExistRecord(Student.class,studentId,connection)){
                log.error(Constants.IDS_ERROR);
                return Status.FAIL;
            }
            PreparedStatement pst = connection.prepareStatement(Constants.JOIN_UPDATE+courseId);
            Course course = (Course) getByID(Course.class,courseId);

            List<Long> students = course.getStudents();
            if(! students.removeIf(el-> el==studentId)){
                log.error(Constants.USER_NOT_JOIN);
                return Status.FAIL;
            }
            log.debug(students);
            pst.setArray(1,connection.createArrayOf(Constants.BIGINT,students.toArray()));
            pst.execute();
            pst = connection.prepareStatement(Constants.DELETE_COURSE_ACTIVITY);
            pst.setLong(1,courseId);
            pst.setLong(2,studentId);
            pst.execute();
            connection.close();
            pst.close();
            log.info(Constants.UNSUBSCRIBE_SUCCES);
            return Status.SUCCESSFUL;
        } catch (SQLException e) {
           log.error(e);
           log.error(Constants.UNSUBSCRIBE_ERROR);
           return Status.FAIL;
        }
    }

    public double getCourseRating(long courseId) {
        try{
            Connection connection = initConnection();
            if(!isExistRecord(Course.class,courseId,connection)){
                log.error(Constants.IDS_ERROR);
                return -1.0;
            }
            connection.close();
            List<Review> reviews = checkCourseReviews(courseId);
            List<Integer> ratings = reviews.stream().map(Review::getRating).collect(Collectors.toList());
            double avgCourseRating = ratings.stream().mapToDouble(el->el).sum()/ratings.size();
            return avgCourseRating;
        } catch (NoSuchElementException | SQLException e) {
            log.error(e);
            return -1.0;
        }
    }

    public List<String> getCourseComments(long courseId) {
        try{
            Connection connection = initConnection();
            if(!isExistRecord(Course.class,courseId,connection)){
                log.error(Constants.IDS_ERROR);
                return null;
            }
            List<Review> reviews = checkCourseReviews(courseId);
            List<String> comments = reviews.stream().map(Review::getComment).collect(Collectors.toList());
            log.debug(comments.toString());
            return comments;
        } catch ( NoSuchElementException | SQLException e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    public Status insertReview(List<Review> reviews) {
        try{
            Connection connection = initConnection();
            PreparedStatement pst = connection.prepareStatement(Constants.REVIEW_INSERT);
            for (Review review:reviews) {
                pst.setLong(1,review.getId());
                pst.setInt(2,review.getRating());
                pst.setString(3,review.getComment());
                pst.execute();
            }
            connection.close();
            pst.close();
            log.info(Constants.CREATING_SUCCESS);
            return Status.SUCCESSFUL;
        } catch (SQLException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
    }

    public Status askAQuestion(long courseId,long studentId, String question){
        try{
            Connection connection = initConnection();
            if(!isExistRecord(Course.class,courseId,connection)){
                log.error(Constants.IDS_ERROR);
                return Status.FAIL;
            }
            Course course = (Course) getByID(Course.class,courseId);
            if(!isExistRecord(Student.class,studentId,connection)){
                log.error(Constants.IDS_ERROR);
                return Status.FAIL;
            }
            List<CourseActivity> activities = this.<CourseActivity>getRecords(CourseActivity.class)
                    .stream()
                    .filter(el->el.getCourse()==courseId)
                    .filter(el->el.getStudent()==studentId)
                    .collect(Collectors.toList());
            CourseActivity activity = (activities.isEmpty()) ? createCourseActivity(courseId, studentId, course.getStudents()) : activities.get(0);
            if (activity == null) {
                log.error(Constants.USER_NOT_JOIN);
                return Status.FAIL;
            }
            Question questionObj = createQuestion(question);

            if(questionObj==null){
                log.error(Constants.CREATING_ERROR);
                return Status.FAIL;
            }
            log.debug(questionObj);
            List<Long> questions = (activity.getQuestions()==null)?new ArrayList<>():activity.getQuestions();
            questions.add(questionObj.getId());
            log.debug(questions);
            PreparedStatement pst = connection.prepareStatement(Constants.QUESTIONS_UPDATE);
            pst.setArray(1,connection.createArrayOf(Constants.BIGINT,questions.toArray()));
            pst.setLong(2,activity.getId());
            pst.execute();
            connection.close();
            pst.close();
            log.info(Constants.CREATING_SUCCESS);
            return Status.SUCCESSFUL;
        } catch (NoSuchElementException | SQLException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
    }

    public List<Section> getCourseMaterials(long courseId){
        try{
            Connection connection = initConnection();
            if(!isExistRecord(Course.class,courseId,connection)){
                log.error(Constants.IDS_ERROR);
                return null;
            }
            connection.close();
            List<Section> sections = this.<Section>getRecords(Section.class).stream()
                    .filter(el->el.getCourse()==courseId)
                    .collect(Collectors.toList());
            log.info(sections);
            return sections;
        } catch ( NoSuchElementException | SQLException e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    public Status answerQuestion(long questionId,String answer){
        try{
            Connection connection = initConnection();
            if(!isExistRecord(Question.class,questionId,connection)){
                log.error(Constants.IDS_ERROR);
                return Status.FAIL;
            }
            List<Answer> answers = this.<Answer>getRecords(Answer.class);
            Answer answerObj = new Answer();
            answerObj.setId(Helper.createId());
            answerObj.setQuestion(questionId);
            answerObj.setAnswer(answer);
            log.debug(answerObj);
            if(Status.SUCCESSFUL == insertAnswer(Collections.singletonList(answerObj))){
                log.info(Constants.CREATING_SUCCESS);
                return  Status.SUCCESSFUL;
            }
            return Status.FAIL;
        } catch (NoSuchElementException | SQLException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
    }

    public String checkQuestions(long courseId,long questionId,String answer){
        try{
            Connection connection = initConnection();
            if(!isExistRecord(Course.class,courseId,connection)){
                log.error(Constants.IDS_ERROR);
                return null;
            }
            connection.close();
            List<Long> questionsIds = getQuestions(courseId);
            List<Question> allQuestions = questionsIds.stream().map(el-> {
                try {
                    return (Question)getByID(Question.class,el);
                } catch (SQLException e) {
                    return null;
                }
            }).collect(Collectors.toList());
            if(questionId!=-1 || !answer.trim().equals("")){

                return answerQuestion(questionId,answer).toString();
            }
            String questions = allQuestions.stream().map(Question::toString)
                    .collect(Collectors.joining(" , "));
            log.debug(questions);
            return questions;
        } catch (NoSuchElementException | SQLException e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    public String checkQA(long courseId,long studentId, String question, boolean needQuestion ){
        try{
            Connection connection = initConnection();
            if(!isExistRecord(Course.class,courseId,connection)){
                log.error(Constants.IDS_ERROR);
                return null;
            }
            connection.close();
            List<Long> questions = getQuestions(courseId);
            List<Answer> answersObj = this.<Answer>getRecords(Answer.class).stream()
                    .filter(el->questions.contains(el.getQuestion()))
                    .collect(Collectors.toList());
            String answers = "";
            if (!answersObj.isEmpty()){
                answers = answersObj.stream().map(Answer::toString)
                        .collect(Collectors.joining(" , "));
            }
            String allQuestionsAnswers = checkQuestions(courseId,1,"") + answers;
            if(needQuestion){
                return askAQuestion(courseId,studentId,question).toString();
            }
            log.debug(allQuestionsAnswers);
            return allQuestionsAnswers;
        } catch (NoSuchElementException | SQLException e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    public String getStudentsCourses(long studentId,long courseId,int rating,String comment,String question,String ExtendMethod, boolean needQuestion ){
        try{
            Connection connection = initConnection();
            if(!isExistRecord(Student.class,studentId,connection)){
                log.error(Constants.IDS_ERROR);
                return null;
            }
            connection.close();
            List<Course> coursesObj = this.<Course>getRecords(Course.class).stream().filter(el->el.getStudents().contains(studentId)).collect(Collectors.toList());
            String courses = coursesObj.stream().map(Course::toString)
                    .collect(Collectors.joining(" , "));
            if(ExtendMethod.trim().equals("")){
                log.info(courses);
                return courses;
            }
            switch(ExtendMethods.valueOf(ExtendMethod.trim().toUpperCase())){
                case MATERIAl:
                    return getCourseMaterials(courseId).toString();
                case UNSUBSCRIBE:
                    return unsubscribeFromACourse(courseId,studentId).toString();
                case REVIEW:
                    return leaveAReviewAboutCourse(courseId,studentId,rating,comment).toString();
                case QA:
                    return checkQA(courseId,studentId,question,needQuestion);
            }
            log.info(courses);
            return courses;
        }
        catch (NoSuchElementException | IllegalArgumentException | SQLException e){
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    public Status insertQuestion(List<Question> questions) {
        try{
            Connection connection = initConnection();
            PreparedStatement pst = connection.prepareStatement(Constants.QUESTION_INSERT);
            for (Question question:questions) {
                pst.setLong(1,question.getId());
                pst.setString(2,question.getQuestion());
                pst.execute();
            }
            connection.close();
            pst.close();
            log.info(Constants.CREATING_SUCCESS);
            return Status.SUCCESSFUL;
        } catch (SQLException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
    }

    public Status insertAnswer(List<Answer> answers) {
        try{
            Connection connection = initConnection();
            PreparedStatement pst = connection.prepareStatement(Constants.ANSWER_INSERT);
            for (Answer answer:answers) {
                if (!isExistRecord(Question.class,answer.getQuestion(),connection)){
                    log.error(Constants.CREATING_ERROR);
                    return Status.FAIL;
                }
                pst.setLong(1,answer.getId());
                pst.setLong(2,answer.getQuestion());
                pst.setString(3,answer.getAnswer());
                pst.execute();
                log.debug(answer);
            }
            connection.close();
            pst.close();
            log.info(Constants.CREATING_SUCCESS);
            return Status.SUCCESSFUL;
        } catch (SQLException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
    }

    public Status insertCourseActivity(List<CourseActivity> courseActivities) {
        try{
            Connection connection = initConnection();
            PreparedStatement pst = connection.prepareStatement(Constants.COURSE_ACTIVITY_INSERT);
            for (CourseActivity courseActivity:courseActivities) {
                if (!isExistRecord(Course.class,courseActivity.getCourse(),connection)){
                    log.error(Constants.CREATING_ERROR);
                    return Status.FAIL;
                }
                if (!isExistRecord(Student.class,courseActivity.getStudent(),connection)){
                    log.error(Constants.CREATING_ERROR);
                    return Status.FAIL;
                }
                pst.setLong(1,courseActivity.getId());
                pst.setLong(2,courseActivity.getCourse());
                pst.setLong(3,courseActivity.getStudent());
                pst.setArray(4,connection.createArrayOf(Constants.BIGINT,courseActivity.getQuestions().toArray()));
                pst.setLong(5,courseActivity.getReview());
                pst.execute();
                log.debug(courseActivity);
            }
            connection.close();
            pst.close();
            log.info(Constants.CREATING_SUCCESS);
            return Status.SUCCESSFUL;
        } catch (SQLException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
    }

    public Status deleteRecords(Class cl){
        try{
            Connection connection = initConnection();
            Statement st = connection.createStatement();
            st.execute(Constants.DElETE+cl.getSimpleName().toUpperCase());
            connection.close();
            st.close();
            log.debug(Constants.DELETING_SUCCESS);
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
            if(!isExistRecord(cl,id,connection)){
                log.error(Constants.DELETING_ERROR);
                return Status.FAIL;
            }
            st.execute(Constants.DElETE+cl.getSimpleName().toUpperCase() + Constants.WHERE_ID+id);
            connection.close();
            st.close();
            log.info(Constants.DELETING_SUCCESS);
            return Status.SUCCESSFUL;
        } catch (SQLException e) {
            log.error(e);
            log.error(Constants.DELETING_ERROR);
            return Status.FAIL;
        }
    }

    public CourseActivity createCourseActivity(long courseId,long studentId,List<Long> students) {
        if (!students.contains(studentId)){
            return null;
        }
        CourseActivity courseActivity = new CourseActivity();
        courseActivity.setId(Helper.createId());
        courseActivity.setStudent(studentId);
        courseActivity.setCourse(courseId);
        courseActivity.setReview(-1);
        courseActivity.setQuestions(new ArrayList<>());
        if(Status.SUCCESSFUL == this.insertCourseActivity(Collections.singletonList(courseActivity))){
            return courseActivity;
        }
        return null;
    }

    public Question createQuestion(String question){
        if(question.trim().isEmpty()){
            return null;
        }
        Question questionObj = new Question();
        questionObj.setId(Helper.createId());
        questionObj.setQuestion(question);
        if(Status.SUCCESSFUL == this.insertQuestion(Collections.singletonList(questionObj))){
            return questionObj;
        }
        return null;
    }

    public <T> boolean checkIds(List<T> ids,Connection connection,Class cl) throws SQLException {
        if (ids.isEmpty()){
         return true;
        }
        if (ids.size() > 0) {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(Constants.SELECT_IDS+cl.getSimpleName().toUpperCase());
            List idsAll  = new ArrayList<>();
            while (rs.next()) {
                idsAll.add(rs.getLong(1));
            }
            st.close();

            return ids.stream().allMatch(idsAll::contains);
        }
        return false;
    }

    public boolean checkDuplicate(List<Long> ids){
        if(ids.isEmpty()){
            return true;
        }
        return ids.stream().collect(Collectors.groupingBy(Function.identity()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList()).isEmpty();
    }

    public Review createReview(int rating,String comment){
        Review review = new Review();
        long id = Helper.createId();
        review.setId(id);
        if(rating>10){
            review.setRating(5);
        }
        if(rating<=0){
            review.setRating(0);
        }
        if(rating>0 && rating<=5){
            review.setRating(rating);
        }
        review.setComment(comment);
        if(Status.SUCCESSFUL == this.insertReview(Collections.singletonList(review))){
            return review;
        }
        return null;
    }

    public boolean isExistRecord(Class cl,long id,Connection connection) {
        try {
            boolean flag = false;
            ResultSet row;
            Statement st = connection.createStatement();
            row = st.executeQuery(Constants.SELECT_ALL + cl.getSimpleName().toUpperCase() + Constants.WHERE_ID + id);
            flag = row.next();
            st.close();
            return flag;
        } catch (SQLException e) {
            log.error(e);
            return false;
        }
    }

    public boolean checkName(String name){
        if(name.trim().isEmpty()){
            log.error(Constants.BAD_NAME);
            return false;
        }
        if (name.length()<4){
            log.error(Constants.BAD_NAME);
            return false;
        }
        return true;
    }

    public List<Long> getQuestions(long courseId) {
        List<CourseActivity> activities = this.<CourseActivity>getRecords(CourseActivity.class)
                .stream()
                .filter(el->el.getCourse()==courseId)
                .collect(Collectors.toList());
        if (activities.isEmpty()){
            return Collections.emptyList();
        }
        List<Long> questionsIds = new ArrayList<>();
        try{
        activities.forEach(e-> questionsIds.addAll(e.getQuestions()));
        }
        catch (NullPointerException e){
            return Collections.emptyList();
        }
        return  questionsIds;
    }

}

