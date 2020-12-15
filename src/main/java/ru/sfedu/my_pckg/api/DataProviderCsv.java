package ru.sfedu.my_pckg.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ru.sfedu.my_pckg.Constants;
import ru.sfedu.my_pckg.beans.*;
import ru.sfedu.my_pckg.enums.ExtendMethods;
import ru.sfedu.my_pckg.enums.UserType;
import ru.sfedu.my_pckg.utils.ConfigurationUtil;
import ru.sfedu.my_pckg.utils.helpers.Helper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DataProviderCsv {

    public static Logger log = LogManager.getLogger(DataProviderCsv.class);
    private final String FILE_EXTENSION = ConfigurationUtil.getConfigurationEntry("CSV_FILE_EXTENSION");
    private final String PATH = ConfigurationUtil.getConfigurationEntry("PATH_TO_CSV");
    private final String DEFAULT_PATH = "./src/main/resources/data/csv/";
    private final String DEFAULT_EXTENSION = ".csv";


    public DataProviderCsv() throws IOException {}

    //Generics methods

    public <T> boolean dataInsert(@NotNull List<T> listRecord, String classname) {
        try {
            String path = createPath(classname);
            DSIinit(path);
            FileWriter fileWriter = new FileWriter(path, false);
            CSVWriter writer = new CSVWriter(fileWriter);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(listRecord);
            writer.close();
            return true;
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException | IOException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return false;
        }

    }

    public <T> List<T> getRecords(@NotNull Class classname) throws IOException, RuntimeException {
        try {
            String path = createPath(classname.getSimpleName());
            DSIinit(path);
            FileReader fileReader = new FileReader(path);
            CSVReader csvReader = new CSVReader(fileReader);
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                    .withType(classname)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<T> list = csvToBean.parse();
            if (list.size() == 0) {
                log.warn(Constants.LIST_EMPTY + classname.getSimpleName());
            }
            return list;
        } catch (RuntimeException e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }

    }

    public <T> List<T> mergeLists(List<T> oldLists, List<T> newList) {
        List<T> mergeList = new ArrayList<>();
        mergeList = (oldLists == null || oldLists.isEmpty()) ? newList : Stream
                .concat(oldLists.stream(), newList.stream())
                .collect(Collectors.toList());
        ;
        return mergeList;
    }

    //  CRUD
    public Student getStudentById(long id) throws IOException {
        String path = createPath(Constants.STUDENT);
        FileReader fileReader = new FileReader(path);
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<Student> csvToBean = new CsvToBeanBuilder<Student>(csvReader)
                .withType(Student.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<Student> students = csvToBean.parse();
        try {
            return students.stream().filter(el -> el.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            log.error(e);
            log.error(Constants.GETTING_BY_ID_FAIL + id);
            return null;
        }
    }

    public Section getSectionById(long id) throws IOException {
        String path = createPath(Constants.SECTION);
        FileReader fileReader = new FileReader(path);
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<Section> csvToBean = new CsvToBeanBuilder<Section>(csvReader)
                .withType(Section.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<Section> sections = csvToBean.parse();
        try {
            return sections.stream().filter(el -> el.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            log.error(e);
            log.error(Constants.GETTING_BY_ID_FAIL + id);
            return null;
        }
    }

    public Course getCourseById(long id) throws IOException {
        String path = createPath(Constants.COURSE);
        FileReader fileReader = new FileReader(path);
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<Course> csvToBean = new CsvToBeanBuilder<Course>(csvReader)
                .withType(Course.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<Course> courses = csvToBean.parse();
        try {
            return courses.stream().filter(el -> el.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            log.error(e);
            log.error(Constants.GETTING_BY_ID_FAIL + id);
            return null;
        }
    }

    public Teacher getTeacherById(long id) throws IOException {
        String path = createPath(Constants.TEACHER);
        DSIinit(path);
        log.debug(Constants.CURRENT_PATH + path);
        FileReader fileReader = new FileReader(path);
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<Teacher> csvToBean = new CsvToBeanBuilder<Teacher>(csvReader)
                .withType(Teacher.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<Teacher> teachers = csvToBean.parse();
        try {
            return teachers.stream().filter(el -> el.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            log.error(e);
            log.error(Constants.GETTING_BY_ID_FAIL);
            return null;
        }
    }

    public Review getReviewById(long id) throws IOException {
        String path = createPath(Constants.REVIEW);
        DSIinit(path);
        FileReader fileReader = new FileReader(path);
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<Review> csvToBean = new CsvToBeanBuilder<Review>(csvReader)
                .withType(Review.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<Review> reviews = csvToBean.parse();
        try {
            return reviews.stream().filter(el -> el.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            log.error(e);
            log.error(Constants.GETTING_BY_ID_FAIL);
            return null;
        }
    }

    public Question getQuestionById(long id) throws IOException {
        String path = createPath(Constants.QUESTION);
        DSIinit(path);
        log.debug(Constants.CURRENT_PATH + path);
        FileReader fileReader = new FileReader(path);
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<Question> csvToBean = new CsvToBeanBuilder<Question>(csvReader)
                .withType(Question.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<Question> questions = csvToBean.parse();
        try {
            return questions.stream().filter(el -> el.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            log.error(e);
            log.error(Constants.GETTING_BY_ID_FAIL);
            return null;
        }
    }

    public Answer getAnswerById(long id) throws IOException {
        String path = createPath(Constants.ANSWER);
        DSIinit(path);
        FileReader fileReader = new FileReader(path);
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<Answer> csvToBean = new CsvToBeanBuilder<Answer>(csvReader)
                .withType(Answer.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<Answer> questions = csvToBean.parse();
        try {
            return questions.stream().filter(el -> el.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            log.error(e);
            log.error(Constants.GETTING_BY_ID_FAIL);
            return null;
        }
    }

    public CourseActivity getCourseActivityById(long id) throws IOException {
        String path = createPath(Constants.COURSE_ACTIVITY);
        DSIinit(path);
        log.debug(Constants.CURRENT_PATH + path);
        FileReader fileReader = new FileReader(path);
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<CourseActivity> csvToBean = new CsvToBeanBuilder<CourseActivity>(csvReader)
                .withType(CourseActivity.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<CourseActivity> activities = csvToBean.parse();
        try {
            log.debug(Constants.GETTING_BY_ID_SUCCESS + id);
            return activities.stream().filter(el -> el.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            log.error(e);
            log.error(Constants.GETTING_BY_ID_FAIL);
            return null;
        }
    }



    public void createCourse(long id, @NotNull String name, String description, @NotNull Long ownerId, List<Long> students) {
        try {
            if (!isExistUser(ownerId, UserType.TEACHER)) {
                log.error(Constants.USER_NOT_EXIST + id);
                log.error(Constants.CREATING_ERROR);
                return;
            }
            if (!checkStudentsId(students)) {
                log.error(Constants.IDS_ERROR);
                log.error(Constants.CREATING_ERROR);
                return;
            }

            Course course = new Course();
            List<Course> courses = new ArrayList<>();
            List<Course> oldCourse = this.<Course>getRecords(Course.class);
            List<Long> oldCourseId = oldCourse.stream().map(Course::getId).collect(Collectors.toList());
            if (hasDuplicates(id, oldCourseId)) {
                log.error(Constants.EXIST_ERROR);
                log.error(Constants.CREATING_ERROR);
                return;
            }
            course.setId(id);
            course.setName(name);
            course.setDescription(description);
            course.setOwner(ownerId);
            course.setStudents(students);
            courses.add(course);
            List<Course> allCourse = mergeLists(oldCourse, courses);
            if (dataInsert(allCourse, Constants.COURSE)) {
                log.info(Constants.CREATING_SUCCESS + id);
            } else {
                log.error(Constants.CREATING_ERROR);
            }
        } catch (IllegalArgumentException | IOException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
        }
    }

    public void createSection(long id, @NotNull String name, String description,
                              long course, List<String> videos, List<String> materials) {
        try {
            Section section = new Section();
            List<Section> sections = new ArrayList<>();
            if (getCourseById(course) == null) {
                log.error(Constants.COURSE_NOT_EXIST + id);
                log.error(Constants.CREATING_ERROR);
                return;
            }
            section.setId(id);
            section.setName(name);
            section.setDescription(description);
            section.setCourse(course);
            section.setVideos(videos);
            section.setMaterials(materials);
            sections.add(section);
            List<Section> allSection = mergeLists(this.<Section>getRecords(Section.class), sections);
            if (dataInsert(allSection, Constants.SECTION)) {
                log.info(Constants.CREATING_SUCCESS + id);
            } else {
                log.error(Constants.CREATING_ERROR);
            }
        } catch (IllegalArgumentException | IOException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
        }
    }


    public void viewCourse(long id, String extendMethod) throws IOException {
        Course course = getCourseById(id);
        if (course != null) {
            log.info(course.toString());
        } else {
            log.info(Constants.GETTING_BY_ID_FAIL);
            return;
        }
        switch (ExtendMethods.valueOf(extendMethod.trim().toUpperCase())) {
            case RATING:
                log.debug("rating");
                return;
            case COMMENTS:
                log.debug("comments");
                return;
            default:
                log.info(Constants.EXTEND_ERROR);
                return;
        }
    }

    public void deleteSection(long id) {
        try {
            Section section = getSectionById(id);
            if (section == null) {
                log.error(Constants.GETTING_BY_ID_FAIL + id);
                log.error(Constants.DELETING_ERROR);
                return;
            }
            List<Section> sections = this.<Section>getRecords(Section.class);
            List<Section> newSections = sections.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
            if (dataInsert(newSections, Constants.SECTION)) {
                log.info(Constants.DELETING_SUCCESS + id);
                return;
            }
        } catch (IOException e) {
            log.error(e);
            log.error(Constants.DELETING_ERROR + id);
        }
    }

    public void deleteCourseActivity(long id){
        try {
            CourseActivity activity = getCourseActivityById(id);
            if (activity == null) {
                log.error(Constants.GETTING_BY_ID_FAIL + id);
                log.error(Constants.DELETING_ERROR);
                return;
            }
            List<CourseActivity> activities = this.<CourseActivity>getRecords(CourseActivity.class);
            List<CourseActivity> newActivities = activities.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
            if (dataInsert(newActivities, Constants.COURSE_ACTIVITY)) {
                log.info(Constants.DELETING_SUCCESS + id);
                return;
            }
        } catch (IOException e) {
            log.error(e);
            log.error(Constants.DELETING_ERROR + id);
        }
    }

    public void updateSection(long id, String name,
                              String description, long course, List<String> videos, List<String> materials) {
        try {
            Section section = getSectionById(id);
            if (section == null) {
                log.error(Constants.GETTING_BY_ID_FAIL + id);
                log.error(Constants.UPDATING_ERROR);
                return;
            }
            List<Section> sections = new ArrayList<>();
            if (!name.trim().equals("")) {
                section.setName(name);
            }
            if (!description.trim().equals("")) {
                section.setDescription(description);
            }
            if (course != -1) {
                section.setCourse(course);
            }
            if (!(videos == null)) {
                section.setVideos(videos);
            }
            if (!(materials == null)) {
                section.setMaterials(materials);
            }
            sections.add(section);
            deleteSection(id);
            List<Section> allSection = mergeLists(this.<Section>getRecords(Section.class), sections);
            if (dataInsert(allSection, Constants.SECTION)) {
                log.info(Constants.UPDATING_SUCCESS + id);
                return;
            }
            log.info(Constants.UPDATING_ERROR + id);
        } catch (IllegalArgumentException | IOException e) {
            log.error(e);
            log.error(Constants.UPDATING_ERROR);
        }
    }

    public void updateCourse(long courseId, String courseName,
                             String courseDescription, List<Long> students,
                             long sectionId, String sectionName, String sectionDescription,
                             List<String> sectionMaterials, List<String> sectionVideos,
                             String extendMethod){
        try{
            Course course = getCourseById(courseId);
            if (course==null){
                log.error(Constants.UPDATING_ERROR);
                return;
            }
            if (!checkStudentsId(students)) {
                log.error(Constants.IDS_ERROR);
                log.error(Constants.UPDATING_ERROR);
                return;
            }
            if (!courseName.trim().equals("")){
                course.setName(courseName);
            }
            if (!courseDescription.trim().equals("")){
                course.setDescription(courseDescription);
            }
            if (students!=null){
                course.setStudents(students);
            }
            deleteCourse(courseId);
            List <Course> courses = new ArrayList<>();
            courses.add(course);
            if(!this.<Course>dataInsert(courses,Constants.COURSE)){
                log.error(Constants.UPDATING_ERROR);
                return;
            }
            log.info(Constants.UPDATING_SUCCESS);
            switch(ExtendMethods.valueOf(extendMethod.trim().toUpperCase())){
                case CREATE:
                    long id = Helper.createId();
                    createSection(id,sectionName,sectionDescription,
                                courseId,sectionMaterials,sectionVideos);
                    break;
                case UPDATE:
                    updateSection(sectionId,sectionName,sectionDescription,
                    courseId,sectionVideos,sectionMaterials);
                    break;
                case DELETE:
                    deleteSection(sectionId);
                    break;
            }

        } catch (IOException e) {
            log.error(e);
            log.error(Constants.UPDATING_ERROR);
        }

    }


    public void joinCourse(long courseId, long studentId){
       try {
           Course course = getCourseById(courseId);
           if (course==null || getStudentById(studentId)==null){
               log.error(Constants.JOINING_ERROR);
               return;
           }
           if(findActivity(courseId,studentId)){
               log.error(Constants.JOINING_ERROR);
               log.error(Constants.USER_ALREADY_JOINED);
               return;
           }
           if(!appendStudent(course,studentId)){
               log.error(Constants.JOINING_ERROR);
               log.error(Constants.USER_ALREADY_JOINED);
               return;
           }

           log.info(Constants.JOINING_SUCCESS);
       } catch (IOException e) {
           e.printStackTrace();
           log.error(Constants.JOINING_ERROR);
       }
    }

    public void leaveAReviewAboutCourse(long courseId,long studentId,int rating,String comment){
        try{
            if(getCourseById(courseId)==null||getStudentById(studentId)==null){
                log.debug(Constants.GETTING_BY_ID_FAIL);
                log.error(Constants.CREATING_ERROR);
                return;
            }

            List<CourseActivity> activities = this.<CourseActivity>getRecords(CourseActivity.class)
                                                .stream()
                                                .filter(el->el.getCourse()==courseId)
                                                .filter(el->el.getStudent()==studentId)
                                                .collect(Collectors.toList());

            CourseActivity activity = (activities.isEmpty())?createCourseActivity(courseId,studentId):activities.get(0);
            if(activity==null){
                log.error(Constants.USER_NOT_JOIN);
                return;
            }

            if (activity.getReview() != -1){
                log.error(Constants.REVIEW_ALREADY_EXIST + studentId);
            }
            long reviewId = createReview(rating,comment);

            if (reviewId == -1){
                log.error(Constants.CREATING_ERROR);
                return;
            }

            if(updateCourseActivity(activity.getId(),reviewId,null)){
                  log.info(Constants.CREATING_SUCCESS + reviewId);
            }

        } catch (IOException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
        }
    }

    public List<Review> checkCourseReviews(long courseId){
        List<Review> reviews = new ArrayList<>();
        try{
            if(getCourseById(courseId)==null){
                log.error(Constants.COURSE_NOT_EXIST);
                return null;
            }
            List<CourseActivity> courseActivities = this.<CourseActivity>getRecords(CourseActivity.class)
                                                        .stream().filter(el->el.getCourse()==courseId).collect(Collectors.toList());
            if(courseActivities.isEmpty()){
                log.warn(Constants.LIST_EMPTY);
                return reviews;
            }
            List<Long> reviewsId = courseActivities.stream().map(CourseActivity::getReview).collect(Collectors.toList());
            List<Review> allReviews = this.<Review>getRecords(Review.class);
            if (allReviews==null){
                log.warn(Constants.LIST_EMPTY);
                return reviews;
            }
            reviews = allReviews.stream().filter(el->reviewsId.contains(el.getId())).collect(Collectors.toList());
            return reviews;

        } catch (IOException e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
        }
        return reviews;
    }

    public List<Course> chooseCourse(long courseId, long studentId, String extendMethod) {
            List<Course> courses = new ArrayList<>();
        try {
            courses = this.<Course>getRecords(Course.class);
            if(extendMethod.trim().equals("")){
                return courses;
            }
            switch (ExtendMethods.valueOf(extendMethod.trim().toUpperCase())) {
                case JOIN:
                    joinCourse(courseId,studentId);
                    break;
                case REVIEW:
                    List <Review> reviews = checkCourseReviews(courseId);
                    log.info(reviews.stream().map(Review::toString));
                    break;
                default:
                    log.warn(Constants.EXTEND_ERROR);
                    break;
            }
        } catch (IOException e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    return courses;
    }

    public void deleteCourse(long id){
        try {
            if (getCourseById(id) == null) {
                log.error(Constants.GETTING_BY_ID_FAIL);
                return;
            }
            List<Course> courses = this.<Course>getRecords(Course.class).stream().filter(el -> el.getId() != id).collect(Collectors.toList());
            if (!courses.isEmpty()) {
                if(!this.<Course>dataInsert(courses, Constants.COURSE)){
                    log.error(Constants.DELETING_ERROR);
                    return;
                };
            }
            List<Section> sections = this.<Section>getRecords(Section.class).stream().filter(el -> el.getCourse() != id).collect(Collectors.toList());
            if (!sections.isEmpty()) {
                if(!this.<Section>dataInsert(sections, Constants.SECTION)){
                    log.error(Constants.DELETING_ERROR);
                    return;
                };
            }
            List<CourseActivity> activities = this.<CourseActivity>getRecords(CourseActivity.class).stream().filter(el -> el.getCourse() != id).collect(Collectors.toList());
            if(!this.<CourseActivity>dataInsert(activities, Constants.COURSE_ACTIVITY)){
                log.error(Constants.DELETING_ERROR);
                return;
            };

        } catch (IOException e) {
            log.error(e);
            log.error(Constants.DELETING_ERROR);
        }
    }

    public void unsubscribeFromACourse(long courseId,long studentId){
        try{
            Course course = getCourseById(courseId);
            List<Course> courses = new ArrayList<>();
            if(course == null || getStudentById(studentId) == null){
                log.error(Constants.IDS_ERROR);
                return;
            }
            if(!deleteStudentFromCourse(course,studentId)){
                log.error(Constants.UNSUBSCRIBE_ERROR);
                return;
            }
            List<CourseActivity> courseActivities = this.<CourseActivity>getRecords(CourseActivity.class);
            if(!this.<CourseActivity>dataInsert(courseActivities.stream()
                                        .filter(el->el.getStudent()!=studentId && el.getCourse()!=courseId)
                                        .collect(Collectors.toList()),Constants.COURSE_ACTIVITY)){
                log.error(Constants.UNSUBSCRIBE_ERROR);
                return;
            }
            log.info(Constants.UNSUBSCRIBE_SUCCES);

        } catch (IOException e) {
            log.error(e);
            log.error(Constants.UNSUBSCRIBE_ERROR);
        }
    }

    public double getCourseRating(long courseId) {
        try{
        if (getCourseById(courseId)==null){
            log.error(Constants.GETTING_BY_ID_FAIL);
            return -1 ;
        }
        List<Review> reviews = checkCourseReviews(courseId);
        List<Integer> ratings = reviews.stream().map(Review::getRating).collect(Collectors.toList());
        double avgCourseRating = ratings.stream().mapToDouble(el->el).sum()/ratings.size();
        return avgCourseRating;
        } catch (IOException e) {
            log.error(e);
            return -1;
        }
    }

    public List<String> getCourseComments(long courseId) {
        try{
            if (getCourseById(courseId)==null){
                log.error(Constants.GETTING_BY_ID_FAIL);
                return null ;
            }
            List<Review> reviews = checkCourseReviews(courseId);
            List<String> comments = reviews.stream().map(Review::getComment).collect(Collectors.toList());
            return comments;
        } catch (IOException e) {
            log.error(e);
            return null;
        }
    }

    public List<Question> checkQuestions(long courseId,long questionId,String answer){
        List<Question> questions = new ArrayList<>();
        try{
            if (getCourseById(courseId)==null){
                log.error(Constants.COURSE_NOT_EXIST);
                return null;
            }
            List<CourseActivity> activities = this.<CourseActivity>getRecords(CourseActivity.class)
                                        .stream()
                                        .filter(el->el.getCourse()==courseId)
                                        .collect(Collectors.toList());
            if (activities.isEmpty()){
                log.warn(Constants.LIST_EMPTY);
                return questions;
            }

            List<Long> questionsIds = new ArrayList<>();
            activities.forEach(e-> questionsIds.addAll(e.getQuestions()));
            List<Question> allQuestions = questionsIds.stream().map(el-> {
                try {
                    return getQuestionById(el);
                } catch (IOException e) {
                    return null;
                }
            }).collect(Collectors.toList());

            if(questionId!=-1&&!answer.trim().equals("")){
                answerQuestion(questionId,answer);
            }

            return allQuestions;
        } catch (IOException e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    public List<Section> getCourseMaterials(long courseId){
        try{
            if(getCourseById(courseId)==null){
                log.error(Constants.GETTING_ERROR);
                return null;
            }
            List<Section> sections = this.<Section>getRecords(Section.class).stream()
                                        .filter(el->el.getCourse()==courseId)
                                        .collect(Collectors.toList());
            return sections;
        } catch (IOException e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    public List<String> checkQA(long courseId,long studentId, String question, boolean needQuestion ){
        try{
            if (getCourseById(courseId)==null){
                log.error(Constants.GETTING_BY_ID_FAIL+courseId);
                log.error(Constants.GETTING_ERROR);
                return null;
            }
            List<Question> questions = checkQuestions(courseId,-1,"");
            List<Long> questionsIds = questions.stream().map(Question::getId).collect(Collectors.toList());
            List<Answer> answers = this.<Answer>getRecords(Answer.class).stream()
                                                                        .filter(el->questionsIds.contains(el.getQuestion()))
                                                                        .collect(Collectors.toList());
            List <String> allQuestionsAnswers = new ArrayList<>();
            allQuestionsAnswers.addAll(questions.stream().map(Question::toString).collect(Collectors.toList()));
            allQuestionsAnswers.addAll(answers.stream().map(Answer::toString).collect(Collectors.toList()));
            return allQuestionsAnswers;
        } catch (IOException e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }
    }

    public void askAQuestion(long courseId,long studentId, String question){
        try{
            if(getCourseById(courseId)==null||getStudentById(studentId)==null){
                log.error(Constants.CREATING_ERROR);
                return;
            }
            List<CourseActivity> activities = this.<CourseActivity>getRecords(CourseActivity.class)
                    .stream()
                    .filter(el->el.getCourse()==courseId)
                    .filter(el->el.getStudent()==studentId)
                    .collect(Collectors.toList());

            CourseActivity activity = (activities.isEmpty())?createCourseActivity(courseId,studentId):activities.get(0);

            if(activity==null){
                log.error(Constants.USER_NOT_JOIN);
                return;
            }
            Question questionObj = createQuestion(question);
            if(questionObj==null){
                log.error(Constants.CREATING_ERROR);
                return;
            }
            List<Long> questionsIds = activity.getQuestions();
            questionsIds.add(questionObj.getId());
            if(updateCourseActivity(activity.getId(),-1,questionsIds)){
                log.info(Constants.CREATING_SUCCESS + questionObj.getId());
            }

        } catch (IOException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
        }
    }

    public void answerQuestion(long questionId,String answer){
        try{
            if(getQuestionById(questionId)==null){
                log.error(Constants.GETTING_BY_ID_FAIL+questionId);
                log.error(Constants.CREATING_ERROR);
                return;
            }
            List<Answer> answers = this.<Answer>getRecords(Answer.class);
            List<Answer> empty = new ArrayList<>();
            Answer answerObj = new Answer();
            answerObj.setId(Helper.createId());
            answerObj.setQuestion(questionId);
            answerObj.setAnswer(answer);
            empty.add(answerObj);
            if(!dataInsert(mergeLists(answers,empty),Constants.ANSWER)){
                log.info(Constants.CREATING_ERROR);
                return;
            }
            log.info(Constants.CREATING_SUCCESS);
        } catch (IOException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);

        }
    }

//Helper function not from api

    public String createPath(String Class) throws IOException {
        String currentPath = (PATH == null) ? DEFAULT_PATH : PATH;
        String currentExtension = (FILE_EXTENSION == null) ? DEFAULT_EXTENSION : FILE_EXTENSION;
        return currentPath + Class.toLowerCase() + currentExtension;
    }

    public void DSIinit(String filePath) throws IOException {
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
        FileWriter file = new FileWriter(createPath(Class));
        file.flush();
    }

    public boolean checkStudentsId(List<Long> students) {
        if (students != null && students.size() > 0) {
            return students.stream().allMatch(el -> {
                try {
                    return isExistUser(el, UserType.STUDENT);
                } catch (IOException e) {
                    return false;
                }
            });
        }
        return false;
    }

    public boolean checkQuestionsId(List<Long> questions) {
        if (questions != null && questions.size() > 0) {
            boolean isExist = questions.stream().map(el ->
            {
                try {
                    return getQuestionById(el);
                } catch (IOException e) {
                    return false;
                }
            }).allMatch(Objects::nonNull);
            return isExist;
        }
        return false;
    }

    public Boolean hasDuplicates(@NotNull Long newId, @NotNull List<Long> oldRecords) {
        return oldRecords
                .stream()
                .anyMatch(el -> el.equals(newId));
    }


    public boolean isExistUser(long id, UserType type) throws IOException {
        switch (type) {
            case TEACHER: {
                User user = getTeacherById(id);
                boolean isTeacher = false;
                if (user != null) {
                    isTeacher = user.getType() == UserType.TEACHER;
                }
                return isTeacher;
            }
            case STUDENT: {
                User user = getStudentById(id);
                boolean isStudent = false;
                if (user != null) {
                    isStudent = user.getType() == UserType.STUDENT;
                }
                return isStudent;
            }
            default:
                return false;
        }
    }

    public boolean appendStudent(Course course,long id){
        try{
            List <Long> ids = course.getStudents();
            if (ids.contains(id)){
                return false;
            }
            ids.add(id);
            course.setStudents(ids);
            List<Course> courses = this.<Course>getRecords(Course.class).stream()
                    .filter(el->el.getId()!=course.getId())
                    .collect(Collectors.toList());
            courses.add(course);
            return this.<Course>dataInsert(courses,Constants.COURSE);
        } catch (IOException e) {
            log.error(e);
            return false;
        }
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
            return this.<Course>dataInsert(courses,Constants.COURSE);
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    public boolean findActivity(long courseId,long studentId){
        try{
            List<CourseActivity> activities = this.<CourseActivity>getRecords(CourseActivity.class);
            if(activities.stream().anyMatch(el -> el.getStudent() == studentId && el.getCourse() == courseId)){
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public long createReview(int rating,String comment)  {
        try {
            List<Review> reviews = new ArrayList<>();
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
            reviews.add(review);
            List<Review> allReviews = mergeLists(this.<Review>getRecords(Review.class), reviews);
            if (this.<Review>dataInsert(allReviews, Constants.REVIEW)) {
                log.debug(Constants.CREATING_SUCCESS);
                return id;
            }
        } catch (IOException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return -1;
        }

        return -1;
    }

    public boolean updateCourseActivity(long id, long reviewId, List<Long> questions){
        try {
            CourseActivity activity = getCourseActivityById(id);
            if (activity == null) {
                log.error(Constants.GETTING_BY_ID_FAIL + id);
                log.error(Constants.UPDATING_ERROR);
                return false;
            }
            List<CourseActivity> activities = new ArrayList<>();

            if(reviewId!=-1){
                if (getReviewById(reviewId)==null){
                    log.error(Constants.GETTING_BY_ID_FAIL + id);
                    log.error(Constants.UPDATING_ERROR);
                    return false;
                }
                activity.setReview(reviewId);
            }

            if(questions!=null) {
                if (!checkQuestionsId(questions)) {
                    log.error(Constants.IDS_ERROR);
                    log.error(Constants.UPDATING_ERROR);
                    return false;
                }
                activity.setQuestions(questions);
            }

            activities.add(activity);
            deleteCourseActivity(id);
            List<CourseActivity> allActivities = mergeLists(this.<CourseActivity>getRecords(CourseActivity.class), activities);
            if (dataInsert(allActivities, Constants.COURSE_ACTIVITY)) {
                log.info(Constants.UPDATING_SUCCESS + id);
                return true;
            }
            log.info(Constants.UPDATING_ERROR + id);
        } catch (IllegalArgumentException | IOException e) {
            log.error(e);
            log.error(Constants.UPDATING_ERROR);
            return false;
        }
        return false;
    }

    public boolean checkUserAssignToCourse(long courseId,long studentId){
        try{
            Course course = getCourseById(courseId);
            return course.getStudents().contains(studentId);
        } catch (IOException e) {
            return false;
        }
    }

    public CourseActivity createCourseActivity(long courseId,long studentId) {
        if (!checkUserAssignToCourse(courseId,studentId)){
            return null;
        }
        try{
        List<Long> empty = new ArrayList<>();
        List<CourseActivity> activities = this.<CourseActivity>getRecords(CourseActivity.class);
        CourseActivity courseActivity = new CourseActivity();
        courseActivity.setId(Helper.createId());
        courseActivity.setStudent(studentId);
        courseActivity.setCourse(courseId);
        courseActivity.setReview(-1);
        courseActivity.setQuestions(empty);
        activities.add(courseActivity);
        if(this.<CourseActivity>dataInsert(activities,Constants.COURSE_ACTIVITY)){
            return courseActivity;
        }
        } catch (IOException e) {
            return null;
        }
        return null;

    }

    public Question createQuestion(String questionString) {
        Question question = new Question();
        try {
            List<Question> questions = this.<Question>getRecords(Question.class);
            question.setId(Helper.createId());
            question.setQuestion(questionString);
            questions.add(question);
        if (this.<Question>dataInsert(questions, Constants.QUESTION)) {
            return question;
        }
        } catch (IOException e) {
            return null;
        }
        return null;
    }
}


