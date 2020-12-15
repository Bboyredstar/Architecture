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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


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
        provider.createCourse(1234,"TestFail","Test course",12L,students_1);
        provider.createCourse(1235,"TestFail 2","Test course 2",12L,students_2);
        assertNull(provider.getCourseById(-1));
        assertNull(provider.getCourseById(123444));
    }

    @Test
    public void createSectionSuccess() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        log.info("On test createSectionSuccess");
        Long id = Helper.createId();
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
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_2 = createStudent(13L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        studentsId.add(student_1.getId());
        students.add(student_1);
        students.add(student_2);
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
        provider.joinCourse(1234,13L);
        List<Long> ids = course_1.getStudents();
        ids.add(13L);
        List<Course> getCourses = provider.<Course>getRecords(Course.class);
        assertEquals(getCourses.get(0).getStudents(),course_1.getStudents());
    }

    @Test
    public void joinCourseFail() throws IOException {
        log.info("On test joinCourseFail");
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
    public void leaveAReviewAboutCourseSuccess() throws IOException {
        log.info("On test leaveAReviewAboutCourseSuccess");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
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
        provider.leaveAReviewAboutCourse(1234,12L,10,"Test Comment");
        List <Review> reviews = provider.<Review>getRecords(Review.class);
        List <CourseActivity> activities = provider.<CourseActivity>getRecords(CourseActivity.class);
        assertEquals(reviews.get(0).getRating(),5);
        assertEquals(reviews.get(0).getComment(),"Test Comment");
        assertEquals(activities.get(0).getReview(),reviews.get(0).getId());
    }

    @Test
    public void leaveAReviewAboutCourseFail() throws IOException {
        log.info("On test leaveAReviewAboutCourseFail");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_2 = createStudent(13L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        studentsId.add(student_1.getId());
        students.add(student_1);
        students.add(student_2);
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
        provider.leaveAReviewAboutCourse(1232,13L,10,"Test Comment");
        List <Review> reviews = provider.<Review>getRecords(Review.class);
        List <CourseActivity> activities = provider.<CourseActivity>getRecords(CourseActivity.class);
        assertTrue(reviews.isEmpty());
        assertTrue(activities.isEmpty());
    }

    @Test
    public void deleteCourseSuccess() throws IOException {
        log.info("On test deleteCourseSuccess");
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
        provider.deleteCourse(1234);
        List<CourseActivity> empty = new ArrayList<>();
        List<Section> empty_2 = new ArrayList<>();
        assertNull(provider.getCourseById(1234));
        assertEquals(provider.<CourseActivity>getRecords(CourseActivity.class)
                        .stream().filter(el->el.getCourse()==1234)
                        .collect(Collectors.toList()),empty);
        assertEquals(provider.<Section>getRecords(Section.class).
                        stream().filter(el->el.getCourse()==1234)
                        .collect(Collectors.toList()), empty_2);
    }

    @Test
    public void deleteCourseFail() throws IOException {
        log.info("On test deleteCourseFail");
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
        provider.deleteCourse(123445);
        assertEquals(provider.<Course>getRecords(Course.class),courses);
    }

    @Test
    public void unsubscribeFromACourseSuccess() throws IOException {
        log.info("On test unsubscribeFromACourseSuccess");
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
        List<Course> courses = new ArrayList<>();
        courses.add(course_1);
        provider.<Course>dataInsert(courses,Constants.COURSE);
        provider.unsubscribeFromACourse(1234,12L);
        Course unsubCourse = provider.<Course>getRecords(Course.class).get(0) ;;
        assertNull(unsubCourse.getStudents());
    }

    @Test
    public void unsubscribeFromACourseFail() throws IOException {
        log.info("On test unsubscribeFromACourseSuccess");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_2 = createStudent(13L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        studentsId.add(student_1.getId());
        students.add(student_1);
        students.add(student_2);
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
        provider.unsubscribeFromACourse(1234,13L);
        assertEquals(provider.getCourseById(1234).getStudents(),studentsId);
    }

    @Test
    public void checkCourseReviewsSuccess() throws IOException {
        log.info("On test checkCourseReviewsSuccess");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
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
        provider.leaveAReviewAboutCourse(1234,12L,10,"Test Comment");
        assertEquals(provider.<Review>getRecords(Review.class),provider.checkCourseReviews(1234));
    }

    @Test
    public void checkCourseReviewsFail() throws IOException {
        log.info("On test checkCourseReviewsFail");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        assertNull(provider.checkCourseReviews(1234L));
    }

    @Test
    public void getCourseRatingSuccess() throws IOException {
        log.info("On test getCourseRatingSuccess");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_2 = createStudent(13L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_3 = createStudent(14L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        studentsId.add(student_1.getId());
        studentsId.add(student_2.getId());
        studentsId.add(student_3.getId());
        students.add(student_1);
        students.add(student_2);
        students.add(student_3);
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
        provider.leaveAReviewAboutCourse(1234,12L,5,"Test Comment");
        provider.leaveAReviewAboutCourse(1234,13L,2,"Test Comment");
        provider.leaveAReviewAboutCourse(1234,14L,3,"Test Comment");
        Double avgRating  = provider.getCourseRating(1234);
        assertEquals(avgRating,3.3333333333333335);
    }

    @Test
    public void getCourseRatingFail() throws IOException {
        log.info("On test getCourseRatingFail");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        Double avgRating  = provider.getCourseRating(1235);
        assertEquals(avgRating,-1.0);
    }

    @Test
    public void getCourseCommentsSuccess() throws IOException {
        log.info("On test getCourseCommentsSuccess");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_2 = createStudent(13L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_3 = createStudent(14L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        studentsId.add(student_1.getId());
        studentsId.add(student_2.getId());
        studentsId.add(student_3.getId());
        students.add(student_1);
        students.add(student_2);
        students.add(student_3);
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
        provider.leaveAReviewAboutCourse(1234,12L,5,"Test Comment");
        provider.leaveAReviewAboutCourse(1234,13L,2,"Test Comment");
        provider.leaveAReviewAboutCourse(1234,14L,3,"Test Comment");
        List<String> comments  = provider.getCourseComments(1234);
        List <String> assertComments = Arrays.asList("Test Comment","Test Comment","Test Comment");
        assertEquals(comments,assertComments);
    }

    @Test
    public void getCourseCommentsFail() throws IOException {
        log.info("On test getCourseCommentsFail");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        assertNull(provider.getCourseComments(1234));
    }

    @Test
    public void askAQuestionSuccess() throws IOException {
        log.info("On test askAQuestionSuccess");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_2 = createStudent(13L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_3 = createStudent(14L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        studentsId.add(student_1.getId());
        studentsId.add(student_2.getId());
        studentsId.add(student_3.getId());
        students.add(student_1);
        students.add(student_2);
        students.add(student_3);
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
        provider.askAQuestion(1234,12L,"Test Question_1");
        provider.askAQuestion(1234,13L,"Test Question_2");
        provider.askAQuestion(1234,14L,"Test Question_3");
        List <Question> questions = provider.<Question>getRecords(Question.class);
        List <CourseActivity> activities = provider.<CourseActivity>getRecords(CourseActivity.class);
        assertEquals(questions.get(0).getQuestion(),"Test Question_1");
        assertEquals(questions.get(1).getQuestion(),"Test Question_2");
        assertEquals(questions.get(2).getQuestion(),"Test Question_3");
        assertEquals(activities.get(0).getQuestions(),Arrays.asList(questions.get(0).getId()));
        assertEquals(activities.get(1).getQuestions(),Arrays.asList(questions.get(1).getId()));
        assertEquals(activities.get(2).getQuestions(),Arrays.asList(questions.get(2).getId()));
    }

    @Test
    public void askAQuestionFail() throws IOException {
        log.info("On test askAQuestionFail");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.askAQuestion(1234,12L,"Test Question_1");
        List <Question> questions = provider.<Question>getRecords(Question.class);
        List <CourseActivity> activities = provider.<CourseActivity>getRecords(CourseActivity.class);
        assertEquals(questions.size(),0);
        assertEquals(activities.size(),0);
    }

    @Test
    public void checkQuestionsSuccess() throws IOException {
        log.info("On test checkQuestionsSuccess");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        Student student_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_2 = createStudent(13L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student student_3 = createStudent(14L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        List<Student> students = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        studentsId.add(student_1.getId());
        studentsId.add(student_2.getId());
        studentsId.add(student_3.getId());
        students.add(student_1);
        students.add(student_2);
        students.add(student_3);
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
        provider.askAQuestion(1234,12L,"Test Question_1");
        provider.askAQuestion(1234,13L,"Test Question_2");
        provider.askAQuestion(1234,14L,"Test Question_3");
        List <Question> questions = provider.checkQuestions(1234,-1,"");
       assertEquals(provider.<Question>getRecords(Question.class),questions);
    }

    @Test
    public void checkQuestionsFail() throws IOException {
        log.info("On test checkQuestionsFail");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        List <Question> questions = provider.checkQuestions(1234,1,"");
        assertNull(questions);
    }

    @Test
    public void answerQuestionSuccess() throws IOException {
        log.info("On test answerQuestionSuccess");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.flushFile(Answer.class.getSimpleName());
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
        provider.askAQuestion(1234,12L,"Test Question_1");
        Question question = provider.<Question>getRecords(Question.class).get(0);
        provider.answerQuestion(question.getId(),"Test answer");
        assertEquals(provider.<Answer>getRecords(Answer.class).get(0).getQuestion(),question.getId());
    }

    @Test
    public void answerQuestionFail() throws IOException{
        log.info("On test answerQuestionFail");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.flushFile(Answer.class.getSimpleName());
        provider.answerQuestion(1234,"Test answer");
        assertEquals(provider.<Answer>getRecords(Answer.class).size(),0);
    }

    @Test
    public void getCourseMaterialsSectionSuccess() throws IOException {
        log.info("On test getCourseMaterialsSectionSuccess");
        Long id = Helper.createId();
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
        provider.createSection(1233,"Test","Test section",1234,videos,materials);
        provider.createSection(1234,"Test","Test section",1234,videos,materials);
        Section section_1 = createSection(1233,"Test","Test section",1234,videos,materials);
        Section section_2 = createSection(1234,"Test","Test section",1234,videos,materials);
        List<Section> courseSections = provider.getCourseMaterials(1234);
        assertEquals(courseSections,Arrays.asList(section_1,section_2));
    }

    @Test
    public void getCourseMaterialsSectionFail() throws IOException {
        log.info("On test getCourseMaterialsSectionFail");
        Long id = Helper.createId();
        List<String> videos  = Arrays.asList("www.test.com","www.test2.com");
        List<String> materials = Arrays.asList("www.test.com","www.test2.com");;
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        List<Section> courseSections = provider.getCourseMaterials(1234);
        assertNull(courseSections);
    }

    @Test
    public void checkQASuccess() throws IOException {
        log.info("On test checkQASuccess");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.flushFile(Answer.class.getSimpleName());
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
        provider.askAQuestion(1234,12L,"Test Question_1");
        Question question = provider.<Question>getRecords(Question.class).get(0);
        provider.answerQuestion(question.getId(),"Test answer");
        List<String> allQA = provider.checkQA(1234,12,"",true);
        Answer answer = provider.<Answer>getRecords(Answer.class).get(0);
        assertEquals(allQA.get(0),question.toString());
        assertEquals(allQA.get(1),answer.toString());
    }
    @Test
    public void checkQAFail() throws IOException {
        log.info("On test checkQAFail");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.flushFile(Answer.class.getSimpleName());
        assertNull(provider.checkQA(123,12,"",false));
    }
}
