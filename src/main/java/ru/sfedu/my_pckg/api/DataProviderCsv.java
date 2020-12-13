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
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * The type Data provider csv.
 */
public class DataProviderCsv {
    /**
     * The constant log.
     */
    public static Logger log = LogManager.getLogger(DataProviderCsv.class);
    private final String FILE_EXTENSION = ConfigurationUtil.getConfigurationEntry("CSV_FILE_EXTENSION");
    private final String PATH = ConfigurationUtil.getConfigurationEntry("PATH_TO_CSV");
    private final String DEFAULT_PATH = "./src/main/resources/data/csv/";
    private final String DEFAULT_EXTENSION = ".csv";

    /**
     * Instantiates a new Data provider csv.
     *
     * @throws IOException the io exception
     */
    public DataProviderCsv() throws IOException {
    }

    /**
     * Create path string.
     *
     * @param Class the abstract
     * @return the string
     * @throws IOException the io exception
     */
    public String createPath(String Class) throws IOException {
        String currentPath = (PATH == null) ? DEFAULT_PATH : PATH;
        String currentExtension = (FILE_EXTENSION == null) ? DEFAULT_EXTENSION : FILE_EXTENSION;
        return currentPath + Class.toLowerCase() + currentExtension;
    }

    /**
     * Ds init.
     *
     * @param filePath the file path
     * @throws IOException the io exception
     */
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

    /**
     * Data insert boolean.
     *
     * @param <T>        the type parameter
     * @param listRecord the list record
     * @return the boolean
     * @throws IOException                    the io exception
     * @throws CsvDataTypeMismatchException   the csv data type mismatch exception
     * @throws CsvRequiredFieldEmptyException the csv required field empty exception
     */
    public <T> boolean dataInsert(@NotNull List<T> listRecord, String classname) {
        try {
            String path = createPath(classname);
            log.debug(path);
            DSIinit(path);
            FileWriter fileWriter = new FileWriter(path, false);
            CSVWriter writer = new CSVWriter(fileWriter);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(listRecord);
            writer.close();
            log.debug(Constants.CREATING_SUCCESS);
            return true;
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException | IOException e) {
            log.error(e);
            log.error(Constants.CREATING_ERROR);
            return false;
        }

    }

    public Boolean hasDuplicates(@NotNull Long newId, @NotNull List<Long> oldRecords) {
        return oldRecords
                .stream()
                .anyMatch(el -> el.equals(newId));
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
            log.debug(Constants.LIST_RETURNED);
            return list;
        } catch (RuntimeException e) {
            log.error(e);
            log.error(Constants.GETTING_ERROR);
            return null;
        }

    }

    /**
     * Gets student by id.
     *
     * @param id the id
     * @return the student by id
     * @throws IOException the io exception
     */
    public Student getStudentById(long id) throws IOException {
        String path = createPath(Constants.STUDENT);
        log.debug(Constants.CURRENT_PATH + path);
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
        log.debug("Path to csv file: " + path);
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
        log.debug(Constants.CURRENT_PATH + path);
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


    // UseCase Teacher Role
    public <T> List<T> mergeLists(List<T> oldLists, List<T> newList) {
        List<T> mergeList = new ArrayList<>();
        mergeList = (oldLists == null || oldLists.isEmpty()) ? newList : Stream
                .concat(oldLists.stream(), newList.stream())
                .collect(Collectors.toList());
        ;
        return mergeList;
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

    /**
     * Gets teacher by id.
     *
     * @param id the id
     * @return the teacher by id
     * @throws IOException the io exception
     */
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
            log.debug(Constants.GETTING_BY_ID_SUCCESS + id);
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
        log.debug(Constants.CURRENT_PATH + path);
        FileReader fileReader = new FileReader(path);
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<Review> csvToBean = new CsvToBeanBuilder<Review>(csvReader)
                .withType(Review.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<Review> reviews = csvToBean.parse();
        try {
            log.debug(Constants.GETTING_BY_ID_SUCCESS + id);
            return reviews.stream().filter(el -> el.getId() == id).findFirst().get();
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


    public boolean checkStudentsId(List<Long> students) {
        if (students != null && students.size() > 0) {
            boolean isExist = students.stream().map(el -> {
                try {
                    return isExistUser(el, UserType.STUDENT);
                } catch (IOException e) {
                    return false;
                }
            }).allMatch(el -> el);
            return isExist;
        }
        return false;
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

    //Section
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

    public void deleteCourseActivity()

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

    public void joinCourse(long courseId, long studentId){
       try {
           if (getCourseById(courseId)==null || getStudentById(studentId)==null){
               log.error(Constants.JOINING_ERROR);
               return;
           }
           CourseActivity courseActivity = new CourseActivity();
           courseActivity.setId(Helper.createId());
           courseActivity.setStudent(studentId);
           courseActivity.setCourse(courseId);
           courseActivity.setReview(-1);
           List<CourseActivity> courseActivities = new ArrayList<>();
           courseActivities.add(courseActivity);
           List<CourseActivity> allCourseActivity = mergeLists(this.<CourseActivity>getRecords(CourseActivity.class), courseActivities);
           if (dataInsert(allCourseActivity, Constants.COURSE_ACTIVITY)) {
               log.info(Constants.UPDATING_SUCCESS  );
               return;
           }
           log.info(Constants.JOINING_SUCCESS);
       } catch (IOException e) {
           e.printStackTrace();
           log.error(Constants.JOINING_ERROR);
       }
    }

    public boolean createReview(int rating,String comment)  {
       try {
           List<Review> reviews = new ArrayList<>();
           Review review = new Review();
           review.setId(Helper.createId());
           if (rating > 5) {
               review.setRating(5);
           }
           if (rating <= 0) {
               review.setRating(0);
           }
           review.setComment(comment);
           reviews.add(review);
           List<Review> allReviews = mergeLists(this.<Review>getRecords(Review.class), reviews);
           if (this.<Review>dataInsert(allReviews, Constants.REVIEW)) {
               log.debug(Constants.CREATING_SUCCESS);
               return true;
           }
       } catch (IOException e) {
           log.error(e);
           log.error(Constants.CREATING_ERROR);
           return false;
       }

        return false;
    }

    public void updateCourseActivity(long id, long reviewId, List<Long> questions){
        try {
             CourseActivity activity = getCourseActivityById(id);
            if (activity == null) {
                log.error(Constants.GETTING_BY_ID_FAIL + id);
                log.error(Constants.UPDATING_ERROR);
                return;
            }
            List<CourseActivity> activities = new ArrayList<>();

            if (getReviewById(reviewId)==null){
                log.error(Constants.GETTING_BY_ID_FAIL + id);
                log.error(Constants.UPDATING_ERROR);
                return;
            }
            activity.setReview(reviewId);

            if (questions!=null && !questions.isEmpty()){
                activity.setQuestions(questions);
            }
            activities.add(activity);
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
    }

    public void leaveAReviewAboutCourse(long courseId,long studentId,int rating,String comment){
        try{
            if(getCourseById(courseId)==null||getStudentById(studentId)==null){
                log.debug(Constants.GETTING_BY_ID_FAIL);
                log.error(Constants.CREATING_ERROR);
                return;
            }
            List<CourseActivity> activities = this.<CourseActivity>getRecords(CourseActivity.class)
                                                .stream().filter(el->el.getCourse()==courseId&&el.getStudent()==studentId)
                                                .collect(Collectors.toList());
            if (activities.isEmpty()){
                log.error(Constants.USER_NOT_JOIN + studentId);
                return;
            }

            CourseActivity activity = activities.get(0);

            if (activity.getReview() != -1){
                log.error(Constants.REVIEW_ALREADY_EXIST + studentId);
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
                return reviews;
            }
            List<Long> reviewsId = courseActivities.stream().map(CourseActivity::getId).collect(Collectors.toList());
            List<Review> allReviews = this.<Review>getRecords(Review.class);
            if (allReviews==null){
                return reviews;
            }
            reviews = allReviews.stream().filter(el->reviewsId.contains(el.getId())).collect(Collectors.toList());
            log.debug(reviews.size());
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
                    log.info("REVIEW");
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

}
