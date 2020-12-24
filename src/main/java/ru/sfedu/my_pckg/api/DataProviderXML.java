package ru.sfedu.my_pckg.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.my_pckg.Constants;
import ru.sfedu.my_pckg.api.xml.WrapperXML;
import ru.sfedu.my_pckg.beans.*;
import ru.sfedu.my_pckg.enums.ExtendMethods;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.utils.ConfigurationUtil;
import ru.sfedu.my_pckg.utils.helpers.Helper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataProviderXML implements AbstractDataProvider {
    public static Logger log = LogManager.getLogger(DataProviderXML.class);
    private final String FILE_EXTENSION = ConfigurationUtil.getConfigurationEntry("XML_FILE_EXTENSION");
    private final String PATH = ConfigurationUtil.getConfigurationEntry("PATH_TO_XML");
    private final String DEFAULT_PATH = "./src/main/resources/data/csv/";
    private final String DEFAULT_EXTENSION = ".csv";

    public DataProviderXML() throws IOException {}

    public <T> Status dataInsert(List<T> listRecord) {
        try{
            String path = createPath(listRecord.get(0).getClass().getSimpleName());
            log.debug(path);
            DSInit(path);
            FileWriter writer = new FileWriter(path, false);
            Serializer serializer = new Persister();
            WrapperXML<T> xml = new WrapperXML<T>();
            xml.setList(listRecord);
            serializer.write(xml,writer);
            return Status.SUCCESSFUL;
        }
        catch(IndexOutOfBoundsException |IOException e) {
            log.error(e);
            return Status.FAIL;
        }
        catch (Exception e) {
            e.printStackTrace();
            return Status.FAIL;
        }
    }


    public Status dataInitialization(){
        try{
            Teacher teacher_1 = Helper.createTeacher(1111,Helper.generateUserFName(),Helper.generateUserLName(),
                    Helper.randomNumber(28,65), Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences(),Helper.randomNumber(2,10));
            Teacher teacher_2 = Helper.createTeacher(1222,Helper.generateUserFName(),Helper.generateUserLName(),
                    Helper.randomNumber(28,65), Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences(),Helper.randomNumber(2,10));
            Teacher teacher_3 = Helper.createTeacher(1333,Helper.generateUserFName(),Helper.generateUserLName(),
                    Helper.randomNumber(28,65), Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences(),Helper.randomNumber(2,10));
            if (getRecords(Teacher.class)==null||getRecords(Teacher.class).isEmpty()){
                dataInsert(Arrays.asList(teacher_1,teacher_2,teacher_3));
            }
            Student student_1 = Helper.createStudent(2112,Helper.generateUserFName(),Helper.generateUserLName(),
                    Helper.randomNumber(28,65), Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences());
            Student student_2 = Helper.createStudent(2113,Helper.generateUserFName(),Helper.generateUserLName(),
                    Helper.randomNumber(28,65), Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences());
            Student student_3 = Helper.createStudent(2114,Helper.generateUserFName(),Helper.generateUserLName(),
                    Helper.randomNumber(28,65), Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences());
            if (getRecords(Student.class)==null||getRecords(Student.class).isEmpty()){
                dataInsert(Arrays.asList(student_1,student_2,student_3));
            }
            if (getRecords(Course.class)==null || getRecords(Course.class).isEmpty()){
                createCourse(3111,"Backend Development","Back end development refers to the server side of an application and everything that communicates between the database and the browser. Back end Development refers to the server side of development where you are primarily focused on how the site works.",
                        1111L,Arrays.asList(2112L,2114L));
                createCourse(3112,"Frontend Development","Front-end web development, also known as client-side development is the practice of producing HTML, CSS and JavaScript for a website or Web Application so that a user can see and interact with them directly.",
                        1222L,Arrays.asList(2112L,2113L));
            }
            if (getRecords(Section.class)==null || getRecords(Section.class).isEmpty()) {
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

    public <T> List<T> getRecords(Class cl) throws Exception {
        String path = createPath(cl.getSimpleName());
        DSInit(path);
        FileReader fileReader = new FileReader(path);
        Serializer serializer = new Persister();
        try{
        WrapperXML <T> xml = serializer.read(WrapperXML.class, fileReader);
        if (xml.getList() == null) {
            xml.setList(new ArrayList<T>());
        }
        return xml.getList();
        }
        catch (Exception e){
            log.debug(cl.getSimpleName());
            log.error(e);
            return new ArrayList<T>();
        }
    }

    public <T> List<T> mergeLists(List<T> oldLists, List<T> newList) {
        List<T> mergeList;
        mergeList = (oldLists == null || oldLists.isEmpty()) ? newList : Stream
                .concat(oldLists.stream(), newList.stream())
                .collect(Collectors.toList());
        return mergeList;
    }

    //  CRUD
    public Student getStudentById(long id) throws Exception {
        List<Student> students = this.<Student>getRecords(Student.class);
        Student student = students.stream().filter(el->el.getId()==id).findFirst().get();
        return student;
    }

    public Section getSectionById(long id) throws Exception {
        List<Section> sections = this.<Section>getRecords(Section.class);
        Section section = sections.stream().filter(el->el.getId()==id).findFirst().get();
        return section;
    }

    public Teacher getTeacherById(long id) throws Exception {
        List<Teacher> teachers = this.<Teacher>getRecords(Teacher.class);
        Teacher teacher = teachers.stream().filter(el->el.getId()==id).findFirst().get();
        return teacher;
    }

    public Review getReviewById(long id) throws Exception  {
        List<Review> reviews = this.<Review>getRecords(Review.class);
        Review reiew = reviews.stream().filter(el->el.getId()==id).findFirst().get();
        return reiew;
    }

    public Question getQuestionById(long id) throws Exception  {
        List<Question> questions = this.<Question>getRecords(Question.class);
        Question question = questions.stream().filter(el->el.getId()==id).findFirst().get();
        return question;
    }

    public CourseActivity getCourseActivityById(long id) throws Exception  {
        List<CourseActivity> courseActivities = this.<CourseActivity>getRecords(CourseActivity.class);
        CourseActivity courseActivity = courseActivities.stream().filter(el->el.getId()==id).findFirst().get();
        return courseActivity;


    }

    public Answer getAnswerById(long id) throws Exception  {
        List<Answer> answers = this.<Answer>getRecords(Answer.class);
        Answer answer = answers.stream().filter(el->el.getId()==id).findFirst().get();
        return answer;
    }

    public Course getCourseById(long id) throws Exception {
        List<Course> courses = this.<Course>getRecords(Course.class);
        Course course = courses.stream().filter(el->el.getId()==id).findFirst().get();
        return course;
    }

    public Status createCourse(long id, String name, String description, Long ownerId, List<Long> students){
        try{
            isExist(ownerId, Constants.TEACHER);
            if (!checkName(name)){
                log.error(Constants.CREATING_ERROR);
                return Status.FAIL;
            }
            students = (students==null)?Collections.emptyList():students;
            if (!checkStudentsId(students)){
                log.error(Constants.IDS_ERROR);
                return Status.FAIL;
            }
            if(!checkDuplicate(students)){
                log.error(Constants.HAS_DUPLICATE);
                return Status.FAIL;
            }
            Course course = new Course();
            course.setId(id);
            course.setName(name);
            course.setDescription(description);
            course.setOwner(ownerId);
            course.setStudents(students);
            List<Course> allCourse = this.<Course>mergeLists(this.<Course>getRecords(Course.class), Collections.singletonList(course));
            this.<Course>dataInsert(allCourse);
            log.debug(course.toString());
            log.info(Constants.CREATING_SUCCESS);
            return Status.SUCCESSFUL;
        } catch (Exception e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
    }

    public Status createSection(long id, String name, String description,
                                long course, List<String> videos, List<String> materials){
        try {
            Section section = new Section();
            getCourseById(course);
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
            List<Section> allSection = mergeLists(this.<Section>getRecords(Section.class), Collections.singletonList(section));
            dataInsert(allSection);
            log.debug(section.toString());
            log.info(Constants.CREATING_SUCCESS + id);
            return Status.SUCCESSFUL;
        } catch (Exception e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
    }

    public Status deleteCourseActivity(long id){
        try {
            CourseActivity activity = getCourseActivityById(id);
            List<CourseActivity> activities = this.<CourseActivity>getRecords(CourseActivity.class);
            activities.removeIf(el->(el.getId()==id));
            if(activities.isEmpty()){
                flushFile(Constants.COURSE_ACTIVITY);
                log.info(Constants.DELETING_SUCCESS);
                return Status.SUCCESSFUL;
            }
            dataInsert(activities);
            log.info(Constants.DELETING_SUCCESS + id);
            return Status.SUCCESSFUL;
        } catch (Exception e) {
            log.error(e);
            log.error(Constants.DELETING_ERROR + id);
            return Status.FAIL;

        }
    }

    public Status deleteSection(long id) {
        try {
            isExist(id,Constants.SECTION);
            List<Section> sections = this.<Section>getRecords(Section.class);
            sections.removeIf(el->(el.getId()==id));
            if(sections.isEmpty()){
                flushFile(Constants.SECTION);
                log.info(Constants.DELETING_SUCCESS);
                return Status.SUCCESSFUL;
            }
            dataInsert(sections);
            log.info(Constants.DELETING_SUCCESS);
            return Status.SUCCESSFUL;
        } catch (Exception e) {
            log.error(e);
            log.error(Constants.DELETING_ERROR + id);
            return Status.FAIL;
        }
    }

    @Override
    public Status deleteCourse(long id) {
        try {
            isExist(id,Constants.COURSE);
            List<Course> courses = this.<Course>getRecords(Course.class);
            courses.removeIf(el->(el.getId()==id));
            log.debug(courses.toString());
            if (courses.isEmpty()){
                flushFile(Constants.COURSE);
            }
            else{
                log.debug(courses.toString());
                dataInsert(courses);
            }
            List<Section> sections = this.<Section>getRecords(Section.class);
            sections.removeIf(el->(el.getCourse()==id));
            if (sections.isEmpty()) {
                flushFile(Constants.SECTION);
            }
            else{
                log.debug(sections.toString());
                dataInsert(sections);
            }

            List<CourseActivity> activities = this.<CourseActivity>getRecords(CourseActivity.class);
            activities.removeIf(el->(el.getCourse()==id));
            if (activities.isEmpty()) {
                flushFile(Constants.COURSE_ACTIVITY);
            }
            else{
                log.debug(activities.toString());
                dataInsert(activities);
            }
            log.info(Constants.DELETING_SUCCESS);
            return Status.SUCCESSFUL;
        } catch (Exception e) {
            log.error(e);
            log.error(Constants.DELETING_ERROR + id);
            return Status.FAIL;
        }
    }

    @Override
    public String viewCourse(long id, String extendMethod) {
        try {
            String course = getCourseById(id).toString();
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
        catch(Exception e){
            log.error(e);
            log.error(Constants.GETTING_BY_ID_FAIL+id);
            return null;
        }
    }

    @Override
    public double getCourseRating(long courseId) {
        try{
            isExist(courseId,Constants.COURSE);
            List<Review> reviews = checkCourseReviews(courseId);
            List<Integer> ratings = reviews.stream().map(Review::getRating).collect(Collectors.toList());
            double avgCourseRating = ratings.stream().mapToDouble(el->el).sum()/ratings.size();
            return avgCourseRating;
        } catch (Exception e) {
            log.error(e);
            return -1.0;
        }
    }

    @Override
    public List<String> getCourseComments(long courseId) {
        try{
            isExist(courseId,Constants.COURSE);
            List<Review> reviews = checkCourseReviews(courseId);
            List<String> comments = reviews.stream().map(Review::getComment).collect(Collectors.toList());
            log.debug(comments.toString());
            return comments;
        } catch (Exception e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    @Override
    public Status updateCourse(long courseId, String courseName, String courseDescription,
                               long sectionId, String sectionName, String sectionDescription,
                               List<String> sectionMaterials, List<String> sectionVideos,
                               String extendMethod) {
        try{
            Course course = getCourseById(courseId);

            if (!courseName.trim().equals("")){
                course.setName(courseName);
            }
            if (!courseDescription.trim().equals("")){
                course.setDescription(courseDescription);
            }

            List <Course> oldCourses = this.<Course>getRecords(Course.class);
            oldCourses.removeIf(el->(el.getId()==courseId));
            dataInsert(mergeLists(oldCourses,Collections.singletonList(course)));
            if (extendMethod.trim().equals("")){
                log.debug(course.toString());
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
            log.debug(course);
            log.info(Constants.UPDATING_SUCCESS);
            return Status.SUCCESSFUL;

        } catch (Exception e) {
            log.error(e);
            log.error(Constants.UPDATING_ERROR);
            return Status.FAIL;
        }
    }

    public String checkQuestions(long courseId, long questionId, String answer) {
        try{
            isExist(courseId,Constants.COURSE);
            List<Long> questionsIds = getQuestions(courseId);
            List<Question> allQuestions = questionsIds.stream().map(el-> {
                try {
                    return getQuestionById(el);
                } catch (Exception e) {
                    return null;
                }
            }).collect(Collectors.toList());

            if(questionId!=-1&&!answer.trim().equals("")){
                return answerQuestion(questionId,answer).toString();
            }
            String questions = allQuestions.stream().map(Question::toString)
                    .collect(Collectors.joining(" , "));
            log.debug(questions);
            return questions;
        } catch (Exception e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    public Status answerQuestion(long questionId, String answer) {
        try{
            isExist(questionId,Constants.QUESTION);
            if(answer.trim().isEmpty()){
                log.error(Constants.BAD_ANSWER);
                return Status.FAIL;
            }
            List<Answer> answers = this.<Answer>getRecords(Answer.class);
            Answer answerObj = new Answer();
            answerObj.setId(Helper.createId());
            answerObj.setQuestion(questionId);
            answerObj.setAnswer(answer);
            dataInsert(mergeLists(answers,Collections.singletonList(answerObj)));
            log.info(Constants.CREATING_SUCCESS);
            log.debug(answerObj);
            return  Status.SUCCESSFUL;
        } catch (Exception e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
    }

    @Override
    public String chooseCourse(long courseId, long studentId, String extendMethod) {
        List<Course> courses;
        try {
            courses = this.<Course>getRecords(Course.class);
            if(courseId==-1 || studentId==-1 || extendMethod.trim().isEmpty()){
                return courses.toString();
            }
            switch (ExtendMethods.valueOf(extendMethod.trim().toUpperCase())) {
                case JOIN:
                    return joinCourse(courseId,studentId).toString();
                case REVIEW:
                    return checkCourseReviews(courseId).toString();
            }
            log.info(courses.toString());
            return courses.toString();
        } catch (Exception e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    public Status updateSection(long id, String name,
                                String description,  List<String> videos, List<String> materials) {
        try {
            Section section = getSectionById(id);

            if (!name.trim().equals("")) {
                section.setName(name);
            }

            if (!description.trim().equals("")) {
                section.setDescription(description);
            }

            if (!(videos == null)) {
                section.setVideos(videos);
            }
            if (!(materials == null)) {
                section.setMaterials(materials);
            }
            deleteSection(id);
            log.debug(section);
            List<Section> allSection = mergeLists(this.<Section>getRecords(Section.class), Collections.singletonList(section));
            dataInsert(allSection);
            log.info(Constants.UPDATING_SUCCESS + id);
            return Status.SUCCESSFUL;

        } catch (Exception e) {
            log.error(e);
            log.error(Constants.UPDATING_ERROR);
            return Status.FAIL;
        }
    }

    public Status joinCourse(long courseId, long studentId){
        try {
            isExist(courseId,Constants.COURSE);
            isExist(studentId,Constants.STUDENT);
            Course course = getCourseById(courseId);

            if(findActivity(courseId,studentId)){
                log.error(Constants.JOINING_ERROR);
                log.error(Constants.USER_ALREADY_JOINED);
                return Status.FAIL;
            }

            if(!appendStudent(course,studentId)){
                log.error(Constants.JOINING_ERROR);
                log.error(Constants.USER_ALREADY_JOINED);
                return Status.FAIL;
            }

            log.debug(course);
            log.info(Constants.JOINING_SUCCESS);
            return Status.SUCCESSFUL;
        } catch (Exception e) {
            log.error(e);
            log.error(Constants.JOINING_ERROR);
            return Status.FAIL;
        }
    }

    @Override
    public List<Review> checkCourseReviews(long courseId) {
        try{
            isExist(courseId,Constants.COURSE);
            List<CourseActivity> courseActivities = this.<CourseActivity>getRecords(CourseActivity.class)
                    .stream().filter(el->el.getCourse()==courseId).collect(Collectors.toList());
            if(courseActivities.isEmpty()){
                log.warn(Constants.LIST_EMPTY);
                return Collections.emptyList();
            }
            List<Long> reviewsId = courseActivities.stream().map(CourseActivity::getReview).collect(Collectors.toList());
            List<Review> reviews = this.<Review>getRecords(Review.class);
            reviews.stream().filter(el->reviewsId.contains(el.getId())).collect(Collectors.toList());
            log.debug(reviews);
            return reviews;

        } catch (Exception e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    @Override
    public String getStudentsCourses(long studentId, long courseId, int rating, String comment, String question, String ExtendMethod, boolean needQuestion) {
        try{
            isExist(studentId,Constants.STUDENT);
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
        catch (Exception e){
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    @Override
    public List<Section> getCourseMaterials(long courseId) {
        try{
            isExist(courseId,Constants.COURSE);
            List<Section> sections = this.<Section>getRecords(Section.class).stream()
                    .filter(el->el.getCourse()==courseId)
                    .collect(Collectors.toList());
            log.info(sections.toString());
            return sections;
        } catch (Exception e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    @Override
    public Status unsubscribeFromACourse(long courseId, long studentId) {
        try{
            Course course = getCourseById(courseId);
            isExist(studentId,Constants.STUDENT);
            if(!deleteStudentFromCourse(course,studentId)){
                log.error(Constants.UNSUBSCRIBE_ERROR);
                return Status.FAIL;
            }
            List<CourseActivity> courseActivities = this.<CourseActivity>getRecords(CourseActivity.class).stream()
                    .filter(el->el.getStudent()!=studentId)
                    .filter(el->el.getCourse()!=courseId)
                    .collect(Collectors.toList());

            if (courseActivities.isEmpty()){
                flushFile(Constants.COURSE_ACTIVITY);
            }
            else {
                dataInsert(courseActivities);
            }
            log.info(Constants.UNSUBSCRIBE_SUCCES);
            return Status.SUCCESSFUL;
        }
        catch (Exception e) {
            log.error(e);
            log.error(Constants.UNSUBSCRIBE_ERROR);
            return Status.FAIL;
        }
    }

    public Status leaveAReviewAboutCourse(long courseId,long studentId,int rating,String comment){
        try{
            isExist(courseId,Constants.COURSE);
            isExist(studentId,Constants.STUDENT);
            List<CourseActivity> activities = this.<CourseActivity>getRecords(CourseActivity.class)
                    .stream()
                    .filter(el->el.getCourse()==courseId)
                    .filter(el->el.getStudent()==studentId)
                    .collect(Collectors.toList());

            CourseActivity activity = (activities.isEmpty())?createCourseActivity(courseId,studentId):activities.get(0);

            if(activity==null){
                log.error(Constants.USER_NOT_JOIN);
                return Status.FAIL;
            }

            if (activity.getReview() != -1){
                log.error(Constants.REVIEW_ALREADY_EXIST + studentId);
                return Status.FAIL;
            }

            Review review = createReview(rating,comment);

            if (review == null){
                log.error(Constants.CREATING_ERROR);
                return Status.FAIL;
            }

            updateCourseActivity(activity.getId(),review.getId(),null);
            log.info(Constants.CREATING_SUCCESS + review);
            return Status.SUCCESSFUL;

        } catch (Exception e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
    }

    @Override
    public String checkQA(long courseId, long studentId, String question, boolean needQuestion) {
        try{
            isExist(courseId,Constants.COURSE);
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
        } catch (Exception e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    @Override
    public Status askAQuestion(long courseId, long studentId, String question) {
        try{
            isExist(courseId,Constants.COURSE);
            isExist(studentId,Constants.STUDENT);
            List<CourseActivity> activities = this.<CourseActivity>getRecords(CourseActivity.class)
                    .stream()
                    .filter(el->el.getCourse()==courseId)
                    .filter(el->el.getStudent()==studentId)
                    .collect(Collectors.toList());
            CourseActivity activity = (activities.isEmpty())?createCourseActivity(courseId,studentId):activities.get(0);
            if(activity==null){
                log.error(Constants.USER_NOT_JOIN);
                return Status.FAIL;
            }
            Question questionObj = createQuestion(question);

            if(questionObj==null){
                log.error(Constants.CREATING_ERROR);
                return Status.FAIL;
            }
            log.debug(questionObj);
            updateCourseActivity(activity.getId(),-1,mergeLists(activity.getQuestions(),Collections.singletonList(questionObj.getId())));
            log.info(Constants.CREATING_SUCCESS + questionObj.getId());
            return Status.SUCCESSFUL;

        } catch (Exception e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return Status.FAIL;
        }
    }

    public String createPath(String Class) throws IOException {
            String currentPath = (PATH == null) ? DEFAULT_PATH : PATH;
            String currentExtension = (FILE_EXTENSION == null) ? DEFAULT_EXTENSION : FILE_EXTENSION;
            return currentPath + Class.toLowerCase() + currentExtension;
        }

        public void DSInit(String filePath) throws IOException {
            String currentPath = (PATH == null) ? DEFAULT_PATH : PATH;
            File file = new File(filePath);
            if (!file.exists()) {
                log.warn(Constants.FILE_NOT_EXIST + filePath);
                Path dirPath = Paths.get(currentPath);
                Files.createDirectories(dirPath);
                file.createNewFile();
                log.warn(Constants.FILE_CREATED + filePath);
            }
        }

        public void flushFile(String Class) throws IOException {
            FileWriter writer = new FileWriter(createPath(Class));
            writer.flush();
        }

    public boolean checkStudentsId(List<Long> students) {
        if(students.isEmpty()){
            return true;
        }
        if (students.size() > 0) {
            return students.stream().allMatch(el -> {
                try {
                    return isExist(el, Constants.STUDENT);
                } catch (Exception e) {
                    return false;
                }
            });
        }
        return false;
    }

    public boolean isExist (long id,String classname) throws Exception {
        switch (classname) {
            case Constants.TEACHER: {
                return getTeacherById(id)!=null;
            }
            case Constants.STUDENT: {
                return getStudentById(id)!=null;
            }
            case Constants.QUESTION: {
                return getQuestionById(id)!=null;
            }
            case Constants.ANSWER: {
                return getAnswerById(id)!=null;
            }
            case Constants.COURSE:{
                return getCourseById(id)!=null;

            }
            case Constants.COURSE_ACTIVITY:{
                return getCourseActivityById(id)!=null;

            }
            case Constants.REVIEW:{
                return getReviewById(id)!=null;
            }
            case Constants.SECTION:{
                return getSectionById(id)!=null;

            }
            default:
                return false;
        }
    }

    public boolean appendStudent(Course course,long id) throws Exception {
        List<Long> ids = (course.getStudents()==null)?new ArrayList<>():course.getStudents();
        if (ids.contains(id)) {
            return false;
        }
        ids.add(id);
        course.setStudents(ids);
        List<Course> courses = this.<Course>getRecords(Course.class);
        courses.removeIf(el->(el.getId()==course.getId()));
        courses.add(course);
        return this.<Course>dataInsert(courses) == Status.SUCCESSFUL;
    }

    public boolean deleteStudentFromCourse(Course course,long id){
        try{
            List <Long> ids = course.getStudents();
            if (!ids.contains(id)){
                return false;
            }
            course.setStudents(ids.stream().filter(el->el!=id).collect(Collectors.toList()));
            List<Course> courses = this.<Course>getRecords(Course.class).stream()
                    .filter(el->el.getId()!=course.getId())
                    .collect(Collectors.toList());
            courses.add(course);
            return this.<Course>dataInsert(courses)==Status.SUCCESSFUL;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    public boolean findActivity(long courseId,long studentId) throws Exception {
        List<CourseActivity> activities = this.<CourseActivity>getRecords(CourseActivity.class);
        return activities.stream().anyMatch(el -> el.getStudent() == studentId && el.getCourse() == courseId);
    }

    public Review createReview(int rating,String comment)  {
        try {
            Review review = new Review();
            long id = Helper.createId();
            review.setId(id);
            if (rating > 5) {
                review.setRating(5);
            }
            if (rating <=0){
                review.setRating(0);
            }
            if (rating>0 && rating<=5){
                review.setRating(rating);
            }
            review.setComment(comment);
            List<Review> allReviews = mergeLists(this.getRecords(Review.class),Collections.singletonList(review));
            dataInsert(allReviews);
            log.debug(review);
            log.debug(Constants.CREATING_SUCCESS);
            return review;
        } catch (Exception e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return null;
        }
    }


    public Status updateCourseActivity(long id, long reviewId, List<Long> questions){
        try {
            CourseActivity activity = getCourseActivityById(id);

            if(reviewId!=-1){
                activity.setReview(reviewId);
            }

            if(questions!=null && checkDuplicate(questions)) {
                activity.setQuestions(questions);
            }

            deleteCourseActivity(id);
            List<CourseActivity> allActivities = mergeLists(this.<CourseActivity>getRecords(CourseActivity.class), Collections.singletonList(activity));
            dataInsert(allActivities);
            log.info(Constants.UPDATING_SUCCESS + id);
            log.debug(activity.toString());
            return Status.SUCCESSFUL;
        } catch (Exception e) {
            log.error(e);
            log.error(Constants.UPDATING_ERROR);
            return Status.FAIL;
        }
    }

    public boolean checkUserAssignToCourse(long courseId,long studentId){
        try{
            Course course = getCourseById(courseId);
            return course.getStudents().contains(studentId);
        } catch (Exception e) {
            return false;
        }
    }

    public CourseActivity createCourseActivity(long courseId,long studentId) {
        if (!checkUserAssignToCourse(courseId,studentId)){
            return null;
        }
        try{
            CourseActivity courseActivity = new CourseActivity();
            courseActivity.setId(Helper.createId());
            courseActivity.setStudent(studentId);
            courseActivity.setCourse(courseId);
            courseActivity.setReview(-1);
            courseActivity.setQuestions(Collections.emptyList());
            dataInsert(mergeLists(this.<CourseActivity>getRecords(CourseActivity.class),Collections.singletonList(courseActivity)));
            return courseActivity;
        } catch (Exception e) {
            return null;
        }
    }

    public Question createQuestion(String question) {
        Question questionObj = new Question();
        if(question.trim().isEmpty()){
            log.error(Constants.BAD_QUESTION);
            return null;
        }
        try {
            questionObj.setId(Helper.createId());
            questionObj.setQuestion(question);
            this.<Question>dataInsert(mergeLists(this.<Question>getRecords(Question.class),Collections.singletonList(questionObj)));
            return questionObj;
        } catch (Exception e) {
            return null;
        }
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

    public List<Long> getQuestions(long courseId) throws Exception {
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
