package ru.sfedu.my_pckg.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.my_pckg.BaseTest;
import ru.sfedu.my_pckg.Constants;
import ru.sfedu.my_pckg.beans.*;
import ru.sfedu.my_pckg.utils.helpers.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class DataProviderCsvTest extends BaseTest{
    public static Logger log =  LogManager.getLogger(DataProviderCsvTest.class);

    @Test
    public void testInsertTeacherSuccess() throws Exception{
        log.info("On test TeacherInsertSuccess");
        List<Teacher> teachers = new ArrayList<>();
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Teacher.class.getSimpleName());
        Teacher teacher_1 = createTeacher(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        Teacher teacher_2 = createTeacher(13L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        Teacher teacher_3 = createTeacher(14L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        teachers.add(teacher_1);
        teachers.add(teacher_2);
        teachers.add(teacher_3);
        provider.<Teacher>dataInsert(teachers, Constants.TEACHER);
        assertEquals(teacher_1,provider.getTeacherById(12L));
        assertEquals(teacher_2,provider.getTeacherById(13L));
        assertEquals(teacher_3,provider.getTeacherById(14L));
    }

    @Test
    public void testInsertTeacherFail() throws Exception{
        log.info("On test testInsertTeacherFail");
        List<Teacher> teachers = new ArrayList<>();
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Teacher.class.getSimpleName());
        Teacher teacher_1 = createTeacher(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        Teacher teacher_2 = createTeacher(13L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        Teacher teacher_3 = createTeacher(14L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        teachers.add(teacher_1);
        teachers.add(teacher_2);
        teachers.add(teacher_3);
        provider.<Teacher>dataInsert(teachers,Constants.TEACHER);
        assertNull(provider.getTeacherById(4));
    }

    @Test
    public void testInsertStudentSuccess() throws Exception{
        log.info("On test testInsertStudentSuccess");
        List<Student> students = new ArrayList<>();
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_2 = createStudent(13L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_3 = createStudent(14L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        students.add(student_1);
        students.add(student_2);
        students.add(student_3);
        provider.<Student>dataInsert(students,Constants.STUDENT);
        assertEquals(student_1,provider.getStudentById(12L));
        assertEquals(student_2,provider.getStudentById(13L));
        assertEquals(student_3,provider.getStudentById(14L));
    }

    @Test
    public void testInsertStudentFail() throws Exception{
        log.info("On test testInsertStudentSuccess");
        List<Student> students = new ArrayList<>();
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_2 = createStudent(13L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_3 = createStudent(14L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        students.add(student_1);
        students.add(student_2);
        students.add(student_3);
        provider.<Student>dataInsert(students,Constants.STUDENT);
        assertNull(provider.getTeacherById(-1));
    }

    @Test
    public void getTeacherByIdSuccess() throws Exception{
        log.info("On test getTeacherByIdSuccess");
        List<Teacher> teachers = new ArrayList<>();
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Teacher.class.getSimpleName());
        Teacher teacher_1 = createTeacher(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        teachers.add(teacher_1);
        provider.<Teacher>dataInsert(teachers,Constants.TEACHER);
        assertEquals(teacher_1,provider.getTeacherById(12L));
    }
    @Test
    public void getTeacherByIdFail() throws Exception{
        log.info("On test getTeacherByIdFail");
        List<Teacher> teachers = new ArrayList<>();
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Teacher.class.getSimpleName());
        Teacher teacher_1 = createTeacher(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        teachers.add(teacher_1);
        provider.<Teacher>dataInsert(teachers,Constants.TEACHER);
        assertNull(provider.getTeacherById(-2));
    }

    @Test
    public void createCourseSuccess() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        log.info("On test createCourseSuccess");
        List<Course> courses = new ArrayList<>();

        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_2 = createStudent(13L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_3 = createStudent(14L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentId =  new ArrayList<>();
        List<Teacher> teachers = new ArrayList<>();
        Teacher teacher_1 = createTeacher(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        teachers.add(teacher_1);
        provider.<Teacher>dataInsert(teachers,Constants.TEACHER);
        students.add(student_1);
        students.add(student_2);
        students.add(student_3);
        studentId.add(student_1.getId());
        studentId.add(student_2.getId());
        studentId.add(student_3.getId());
        provider.<Student>dataInsert(students,Constants.STUDENT);
        provider.createCourse(1234,"Test","Test course",12L,studentId);
        provider.createCourse(1235,"Test 2","Test course 2",12L,studentId);
        Course course_1 = createCourse(1234,"Test","Test course",12L,studentId);
        Course course_2 = createCourse(1235,"Test 2","Test course 2",12L,studentId);
        assertEquals(course_1,provider.getCourseById(1234));
        assertEquals(course_2,provider.getCourseById(1235));

    }

    @Test
    public void createCourseFail() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        log.info("On test createCourseFail");
        List<Long> students_1 = null;
        List<Long> students_2 = Arrays.asList(100L,-123L,14L);
        DataProviderCsv provider = new DataProviderCsv();
        long id = Helper.createId();
        provider.createCourse(1234,"TestFail","Test course",12L,students_1);
        provider.createCourse(1235,"TestFail 2","Test course 2",12L,students_2);
        assertNull(provider.getCourseById(-1));
        assertNull(provider.getCourseById(123444));
    }

    @Test
    public void createSectionSuccess() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        log.info("On test createSectionSuccess");
        Long id = Helper.createId();
        List<Course> sections = new ArrayList<>();
        List<String> videos  = Arrays.asList("www.test.com","www.test2.com");
        List<String> materials = Arrays.asList("www.test.com","www.test2.com");;
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        studentsId.add(student_1.getId());
        students.add(student_1);
        provider.<Student>dataInsert(students,Constants.STUDENT);
        Teacher teacher_1 = createTeacher(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher_1);
        provider.<Teacher>dataInsert(teachers,Constants.TEACHER);
        Course course_1 = createCourse(1234,"Test","Test course",teacher_1.getId(),studentsId);
        List<Course> courses = new ArrayList<>();
        courses.add(course_1);
        provider.<Course>dataInsert(courses,Constants.COURSE);
        provider.createSection(1234,"Test","Test section",1234,videos,materials);
        Section section_1 = createSection(1234,"Test","Test section",1234,videos,materials);;
        assertEquals(section_1,provider.getSectionById(1234));
    }


    @Test
    public void createSectionFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        log.info("On test createSectionFail");
        Long id = Helper.createId();
        List<Course> sections = new ArrayList<>();
        List<String> videos  = Arrays.asList("www.test.com","www.test2.com");
        List<String> materials = Arrays.asList("www.test.com","www.test2.com");;
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        studentsId.add(student_1.getId());
        students.add(student_1);
        provider.<Student>dataInsert(students,Constants.STUDENT);
        Teacher teacher_1 = createTeacher(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher_1);
        provider.<Teacher>dataInsert(teachers,Constants.TEACHER);
        Course course_1 = createCourse(1234,"Test","Test course",teacher_1.getId(),studentsId);
        List<Course> courses = new ArrayList<>();
        courses.add(course_1);
        provider.<Course>dataInsert(courses,Constants.COURSE);
        provider.createSection(1234,"Test","Test section",-1,videos,materials);
        Section section_1 = createSection(1234,"Test","Test section",1234,videos,materials);
        assertNull(provider.getSectionById(1234));
    }
    @Test
    public void deleteSectionSuccess() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        log.info("On test deleteSectionSuccess");
        List<String> videos  = Arrays.asList("www.test.com","www.test2.com");
        List<String> materials = Arrays.asList("www.test.com","www.test2.com");;
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",20,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        studentsId.add(student_1.getId());
        students.add(student_1);
        provider.<Student>dataInsert(students,Constants.STUDENT);
        Teacher teacher_1 = createTeacher(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher_1);
        provider.<Teacher>dataInsert(teachers,Constants.TEACHER);
        Course course_1 = createCourse(1234,"Test","Test course",12L,studentsId);
        List<Course> courses = new ArrayList<>();
        courses.add(course_1);
        provider.<Course>dataInsert(courses,Constants.COURSE);
        provider.createSection(1235,"Test","Test section",1234,videos,materials);
        List<Section> test = new ArrayList<>();
        provider.deleteSection(1235);
        assertNull(provider.getSectionById(1235));
    }


    @Test
    public void deleteSectionFail() throws IOException {
        log.info("On test deleteSectionFail");
        List<String> videos  = Arrays.asList("www.test.com","www.test2.com");
        List<String> materials = Arrays.asList("www.test.com","www.test2.com");;
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        studentsId.add(student_1.getId());
        students.add(student_1);
        provider.<Student>dataInsert(students,Constants.STUDENT);
        Teacher teacher_1 = createTeacher(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher_1);
        provider.<Teacher>dataInsert(teachers,Constants.TEACHER);
        Course course_1 = createCourse(1234,"Test","Test course",teacher_1.getId(),studentsId);
        List<Course> courses = new ArrayList<>();
        courses.add(course_1);
        provider.<Course>dataInsert(courses,Constants.COURSE);
        provider.createSection(1234,"Test","Test section",1234,videos,materials);
        Section section_1 = createSection(1234,"Test","Test section",1234,videos,materials);
        List<Section> sections = new ArrayList<>();
        sections.add(section_1);
        List<Section> test = new ArrayList<>();
        provider.deleteSection(123439889);
        assertEquals(provider.<Section>getRecords(Section.class),sections);
    }

    @Test
    public void updateSectionSuccess() throws IOException{
        log.info("On test updateSection");
        Long id = Helper.createId();
        List<Course> sections = new ArrayList<>();
        List<String> videos  = Arrays.asList("www.test.com","www.test2.com");
        List<String> materials = Arrays.asList("www.test.com","www.test2.com");;
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        studentsId.add(student_1.getId());
        students.add(student_1);
        provider.<Student>dataInsert(students,Constants.STUDENT);
        Teacher teacher_1 = createTeacher(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher_1);
        provider.<Teacher>dataInsert(teachers,Constants.TEACHER);
        Course course_1 = createCourse(1234,"Test","Test course",teacher_1.getId(),studentsId);
        List<Course> courses = new ArrayList<>();
        courses.add(course_1);
        provider.<Course>dataInsert(courses,Constants.COURSE);
        provider.createSection(1234,"Test","Test section",1234,videos,materials);
        Section section_1 = createSection(1234,"New name","Test section",1234,videos,materials);
        provider.updateSection(1234,"New name","Test section",-1,videos,materials);
        assertEquals(section_1,provider.getSectionById(1234));
    }

    @Test
    public void updateSectionFail() throws IOException{
        log.info("On test updateSection");
        Long id = Helper.createId();
        List<Course> sections = new ArrayList<>();
        List<String> videos  = Arrays.asList("www.test.com","www.test2.com");
        List<String> materials = Arrays.asList("www.test.com","www.test2.com");;
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        studentsId.add(student_1.getId());
        students.add(student_1);
        provider.<Student>dataInsert(students,Constants.STUDENT);
        Teacher teacher_1 = createTeacher(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher_1);
        provider.<Teacher>dataInsert(teachers,Constants.TEACHER);
        Course course_1 = createCourse(1234,"Test","Test course",teacher_1.getId(),studentsId);
        List<Course> courses = new ArrayList<>();
        courses.add(course_1);
        provider.<Course>dataInsert(courses,Constants.COURSE);
        provider.createSection(1234,"Test","Test section",1234,videos,materials);
        Section section_1 = createSection(1234,"New name","Test section",1234,videos,materials);
        provider.updateSection(12345,"New name","Test section",-1,videos,materials);
        assertNull(provider.getSectionById(12345));
    }

    @Test
    public void chooseCourseSuccess() throws IOException {
        log.info("On test chooseCourseSuccess");
        List<Course> sections = new ArrayList<>();
        List<String> videos  = Arrays.asList("www.test.com","www.test2.com");
        List<String> materials = Arrays.asList("www.test.com","www.test2.com");;
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        studentsId.add(student_1.getId());
        students.add(student_1);
        provider.<Student>dataInsert(students,Constants.STUDENT);
        Teacher teacher_1 = createTeacher(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher_1);
        provider.<Teacher>dataInsert(teachers,Constants.TEACHER);
        Course course_1 = createCourse(1234,"Test","Test course",teacher_1.getId(),studentsId);
        Course course_2 = createCourse(1235,"Test_2","Test course_2",teacher_1.getId(),studentsId);
        List<Course> courses = new ArrayList<>();
        courses.add(course_1);
        courses.add(course_2);
        provider.<Course>dataInsert(courses,Constants.COURSE);
        assertEquals(provider.chooseCourse(1,1,""),courses);
    }
    @Test
    public void chooseCourseFail() throws IOException {
        log.info("On test chooseCourseFail");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        List<Course> test= new ArrayList<Course>();
        assertEquals(test,provider.chooseCourse(1,1,""));
    }

    @Test
    public void joinCourseSuccess() throws IOException {
        log.info("On test joinCourseSuccess");
        List<Course> sections = new ArrayList<>();
        List<String> videos  = Arrays.asList("www.test.com","www.test2.com");
        List<String> materials = Arrays.asList("www.test.com","www.test2.com");;
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        studentsId.add(student_1.getId());
        students.add(student_1);
        provider.<Student>dataInsert(students,Constants.STUDENT);
        Teacher teacher_1 = createTeacher(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher_1);
        provider.<Teacher>dataInsert(teachers,Constants.TEACHER);
        Course course_1 = createCourse(1234,"Test","Test course",teacher_1.getId(),studentsId);
        Course course_2 = createCourse(1235,"Test_2","Test course_2",teacher_1.getId(),studentsId);
        List<Course> courses = new ArrayList<>();
        courses.add(course_1);
        courses.add(course_2);
        provider.<Course>dataInsert(courses,Constants.COURSE);
        provider.joinCourse(1234,12L);
        List<CourseActivity> courseActivities = provider.<CourseActivity>getRecords(CourseActivity.class);
        assertEquals(courseActivities.get(0).getCourse(),1234);
        assertEquals(courseActivities.get(0).getStudent(),12L);
    }

    @Test
    public void joinCourseFail() throws IOException {
        log.info("On test joinCourseFail");
        List<Course> sections = new ArrayList<>();
        List<String> videos  = Arrays.asList("www.test.com","www.test2.com");
        List<String> materials = Arrays.asList("www.test.com","www.test2.com");;
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        studentsId.add(student_1.getId());
        students.add(student_1);
        provider.<Student>dataInsert(students,Constants.STUDENT);
        Teacher teacher_1 = createTeacher(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher_1);
        provider.<Teacher>dataInsert(teachers,Constants.TEACHER);
        Course course_1 = createCourse(1234,"Test","Test course",teacher_1.getId(),studentsId);
        Course course_2 = createCourse(1235,"Test_2","Test course_2",teacher_1.getId(),studentsId);
        List<Course> courses = new ArrayList<>();
        courses.add(course_1);
        courses.add(course_2);
        provider.<Course>dataInsert(courses,Constants.COURSE);
        provider.joinCourse(1232,12L);
        List<CourseActivity> courseActivities = provider.<CourseActivity>getRecords(CourseActivity.class);
        List<CourseActivity> empty = new ArrayList<>();
        assertEquals(empty,courseActivities);
    }


    @Test
    public void createReviewSuccess() throws IOException {
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Review.class.getSimpleName());
        provider.createReview(10,"");
        provider.createReview(-1,"Test");
        List<Review> reviews = provider.<Review>getRecords(Review.class);
        Review rev_1 = reviews.get(0);
        Review rev_2 = reviews.get(1);
        assertEquals(rev_1.getRating(),5);
        assertEquals(rev_1.getComment(),"");
        assertEquals(rev_2.getRating(),0);
        assertEquals(rev_2.getComment(),"Test");
    }

    @Test
    public void check(){

    }

}
