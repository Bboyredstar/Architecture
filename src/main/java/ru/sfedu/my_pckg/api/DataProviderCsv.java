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


/**
 * The type Data provider csv.
 */
public class DataProviderCsv {
    /**
     * The constant log.
     */
    public static Logger log =  LogManager.getLogger(DataProviderCsv .class);
    private final String FILE_EXTENSION = ConfigurationUtil.getConfigurationEntry("CSV_FILE_EXTENSION");
    private final String PATH = ConfigurationUtil.getConfigurationEntry("PATH_TO_CSV");
    private final String DEFAULT_PATH = "./src/main/resources/data/csv/";
    private final String DEFAULT_EXTENSION = ".csv";
    private final String STUDENT = "Student";
    private final String TEACHER = "Teacher";
    private final String SECTION = "Section";
    private final String COURSE = "Course";
    /**
     * Instantiates a new Data provider csv.
     *
     * @throws IOException the io exception
     */
    public DataProviderCsv() throws IOException {}

    /**
     * Create path string.
     *
     * @param Class the abstract
     * @return the string
     * @throws IOException the io exception
     */
    public String createPath(String Class) throws IOException {
        String currentPath = (PATH == null)? DEFAULT_PATH: PATH;
        String currentExtension = (FILE_EXTENSION == null)? DEFAULT_EXTENSION: FILE_EXTENSION;
        log.debug(Constants.CURRENT_PATH + currentPath);
        log.debug(Constants.CURRENT_EXTENSION + currentExtension);
        return currentPath + Class.toLowerCase() + currentExtension;
    }

    /**
     * Ds init.
     *
     * @param filePath the file path
     * @throws IOException the io exception
     */
    public void DSIinit(String filePath) throws IOException {
        String currentPath = (PATH == null)? DEFAULT_PATH: PATH;
        File file = new File(filePath);
        if (!file.exists()) {
            log.warn(Constants.FILE_NOT_EXIST+filePath);
            Path dirPath = Paths.get(currentPath);
            Files.createDirectories(dirPath);
            file.createNewFile();
            log.warn(Constants.FILE_CREATED+filePath);
        }
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
    public <T> boolean dataInsert (@NotNull List <T> listRecord) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try {
            String path = createPath(listRecord.get(0).getClass().getSimpleName());
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
        }
        catch(IndexOutOfBoundsException e) {
            log.error(Constants.CREATING_ERROR);
            return false;
        }
    }

    public  Boolean hasDuplicates(@NotNull List<Long> newRecords, @NotNull List<Long> oldRecords) {
        return oldRecords
                .stream()
                .anyMatch(newRecords::contains);
    }

    public  <T> List<T> getRecords(@NotNull Class classname) throws IOException,RuntimeException {
       try {
           String path =  createPath(classname.getSimpleName());
           DSIinit(path);
           FileReader fileReader = new FileReader(path);
           CSVReader csvReader = new CSVReader(fileReader);
           CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                   .withType(classname)
                   .withIgnoreLeadingWhiteSpace(true)
                   .build();
           List<T> list = csvToBean.parse();
            if ( list.size() == 0) {
                log.warn(Constants.LIST_EMPTY + classname.getSimpleName());
            }
            log.debug(Constants.LIST_RETURNED);
           return list;
       }
       catch (RuntimeException e){
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
        String path = createPath(STUDENT);
        log.debug(Constants.CURRENT_PATH + path );
        FileReader fileReader = new FileReader(path);
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<Student> csvToBean = new CsvToBeanBuilder<Student>(csvReader)
                   .withType(Student.class)
                   .withIgnoreLeadingWhiteSpace(true)
                   .build();
        List<Student> students = csvToBean.parse();
        try {
            log.debug(Constants.GETTING_BY_ID_SUCCESS + id);
            return students.stream().filter(el -> el.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
             log.error(e);
             log.error(Constants.GETTING_BY_ID_FAIL + id);
             return null;
           }
       }


    public Section getSectionById(long id) throws IOException{
        String path = createPath(SECTION);
        log.debug("Path to csv file: "+path );
        FileReader fileReader = new FileReader(path);
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<Section> csvToBean = new CsvToBeanBuilder<Section>(csvReader)
                .withType(Section.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<Section> sections = csvToBean.parse();
        try {
            log.debug(Constants.GETTING_BY_ID_SUCCESS+id);
            return sections.stream().filter(el -> el.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            log.error(e);
            log.error(Constants.GETTING_BY_ID_FAIL + id);
            return null;
        }
    }

    public List<Student> getStudentRecords() throws IOException {
         String path = createPath(STUDENT);
         log.debug(Constants.CURRENT_PATH+path );
         FileReader fileReader = new FileReader(path);
         CSVReader csvReader = new CSVReader(fileReader);
         CsvToBean<Student> csvToBean = new CsvToBeanBuilder<Student>(csvReader)
                 .withType(Student.class)
                 .withIgnoreLeadingWhiteSpace(true)
                 .build();
         List<Student> students = csvToBean.parse();
         if (students.size()>0) {
             log.debug(Constants.LIST_RETURNED);
             return students;
         }
         else {
             log.warn(Constants.LIST_EMPTY);
             return null;
         }
      }

    public Course getCourseById(long id) throws IOException{
        String path = createPath(COURSE);
        log.debug(Constants.CURRENT_PATH+path );
        FileReader fileReader = new FileReader(path);
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<Course> csvToBean = new CsvToBeanBuilder<Course>(csvReader)
                .withType(Course.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<Course> courses = csvToBean.parse();
        try {
            log.debug(Constants.GETTING_BY_ID_SUCCESS+id);
            return courses.stream().filter(el -> el.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            log.error(e);
            log.error(Constants.GETTING_BY_ID_FAIL + id);
            return null;
        }
    }



    // UseCase Teacher Role


    public void createCourse(long id,@NotNull String name,String description,@NotNull Long ownerId, List<Long>students){
        try {
            Course course = new Course();
            List<Course> courses = new ArrayList<>();
            List <Course> allCourse = this.<Course>getRecords(Course.class);


            if(!isExistUser(ownerId,UserType.TEACHER)){
                log.error(Constants.USER_NOT_EXIST + id);
                log.error(Constants.CREATING_ERROR);
                return;
            }
            if(!checkStudentsId(students)){
                log.error(Constants.IDS_ERROR);
                log.error(Constants.CREATING_ERROR);
                return;
            }
            course.setId(id);
            course.setName(name);
            course.setDescription(description);
            course.setOwner(ownerId);
            course.setStudents(students);
            allCourse.add(course);
            if (dataInsert(allCourse)){
                log.info(Constants.CREATING_SUCCESS);
            }
            else{
                log.error(Constants.CREATING_ERROR);
            }
        }
        catch (IllegalArgumentException | IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e){
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
    public  Teacher getTeacherById(long id) throws IOException {
        String path = createPath(TEACHER);
        DSIinit(path);
        log.debug(Constants.CURRENT_PATH+path );
        FileReader fileReader = new FileReader(path);
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<Teacher> csvToBean = new CsvToBeanBuilder<Teacher>(csvReader)
                .withType(Teacher.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<Teacher> teachers = csvToBean.parse();
        try {
            log.debug(Constants.GETTING_BY_ID_SUCCESS+id);
            return teachers.stream().filter(el -> el.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            log.error(e);
            log.error(Constants.GETTING_BY_ID_FAIL);
            return null;
        }
    }

    public boolean isExistUser(long id, UserType type) throws IOException {
        switch (type){
            case TEACHER:{
                User user = getTeacherById(id);
                boolean isTeacher=false;
                if (user!=null){
                    isTeacher = user.getType()== UserType.TEACHER;
                }
                return isTeacher;
            }
            case STUDENT:{
                User user = getStudentById(id);
                boolean isStudent=false;
                if (user!=null){
                    isStudent = user.getType()== UserType.STUDENT;
                }
                return isStudent;
            }
            default: return false;
        }
    }


    public  boolean checkStudentsId(List<Long> students ){
        if(students!=null && students.size()>0){
            boolean isExist  = students.stream().map(el-> {
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

    public void viewCourse(long id,String extendMethod) throws IOException {
        Course course = getCourseById(id);
        if (course!=null){
            log.info(course.toString());
        }
        else{
            log.info(Constants.GETTING_BY_ID_FAIL);
            return;
        }
        switch(ExtendMethods.valueOf(extendMethod.toUpperCase())){
            case RATING:
                log.debug("rating");
                return;
            case COMMENTS:
                log.debug("rating");
                return;
            default:
                log.info("Unknown method was given!");
                return;
        }

    }
}
