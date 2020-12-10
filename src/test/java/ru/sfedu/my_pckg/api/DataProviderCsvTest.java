package ru.sfedu.my_pckg.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.my_pckg.BaseTest;
import ru.sfedu.my_pckg.beans.Course;
import ru.sfedu.my_pckg.beans.Section;
import ru.sfedu.my_pckg.beans.Student;
import ru.sfedu.my_pckg.beans.Teacher;
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
        provider.<Teacher>dataInsert(teachers);
        assertEquals(teacher_1,provider.getTeacherById(12L));
    }

    @Test
    public void testInsertTeacherFail() throws Exception{
        log.info("On test TeacherInsertSuccess");
        List<Teacher> teachers = new ArrayList<>();
        DataProviderCsv provider = new DataProviderCsv();
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
        provider.<Teacher>dataInsert(teachers);
        assertNull(provider.getTeacherById(4));
    }

    @Test
    public void testInsertStudentSuccess() throws Exception{
        log.info("On test testInsertStudentSuccess");
        List<Student> teachers = new ArrayList<>();
        DataProviderCsv provider = new DataProviderCsv();
        Student teacher_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student teacher_2 = createStudent(13L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student teacher_3 = createStudent(14L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        teachers.add(teacher_1);
        teachers.add(teacher_2);
        teachers.add(teacher_3);
        provider.<Student>dataInsert(teachers);
        assertEquals(teacher_1,provider.getStudentById(12L));
    }

    @Test
    public void testInsertStudentFail() throws Exception{
        log.info("On test testInsertStudentSuccess");
        List<Student> teachers = new ArrayList<>();
        DataProviderCsv provider = new DataProviderCsv();
        Student teacher_1 = createStudent(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student teacher_2 = createStudent(13L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        Student teacher_3 = createStudent(14L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning");
        teachers.add(teacher_1);
        teachers.add(teacher_2);
        teachers.add(teacher_3);
        provider.<Student>dataInsert(teachers);
        assertNull(provider.getTeacherById(4));
    }

    @Test
    public void getTeacherByIdSuccess() throws Exception{
        log.info("On test getTeacherByIdSuccess");
        List<Teacher> teachers = new ArrayList<>();
        DataProviderCsv provider = new DataProviderCsv();
        Teacher teacher_1 = createTeacher(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        teachers.add(teacher_1);
        provider.<Teacher>dataInsert(teachers);
        assertEquals(teacher_1,provider.getTeacherById(12L));
    }
    @Test
    public void getTeacherByIdFail() throws Exception{
        log.info("On test getTeacherByIdFail");
        List<Teacher> teachers = new ArrayList<>();
        DataProviderCsv provider = new DataProviderCsv();
        Teacher teacher_1 = createTeacher(12L,"John","Beach",40,
                "asdasd@mail.ru","Romania","Machine Learning",
                10);
        teachers.add(teacher_1);
        provider.<Teacher>dataInsert(teachers);
        assertNull(provider.getTeacherById(-2));
    }

    @Test
    public void createCourseSuccess() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        log.info("On test createCourseSuccess");
        Long id = Helper.createId();
        List<Course> courses = new ArrayList<>();
        List<Long> students_1 = Arrays.asList(12L);
        List<Long> students_2 = Arrays.asList(12L,13L,14L);
        DataProviderCsv provider = new DataProviderCsv();
        provider.createCourse(id,"Test","Test course",12L,students_1);
        provider.createCourse(id+1,"Test 2","Test course 2",12L,students_2);
        Course course_1 = createCourse(id,"Test","Test course",12L,students_1);
        Course course_2 = createCourse(id+1,"Test 2","Test course 2",12L,students_2);
        assertEquals(course_1,provider.getCourseById(id));
        assertEquals(course_2,provider.getCourseById(id+1));

    }

    @Test
    public void createCourseFail() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        log.info("On test createCourseFail");
        List<Long> students_1 = null;
        List<Long> students_2 = Arrays.asList(100L,-123L,14L);
        DataProviderCsv provider = new DataProviderCsv();
        long id = Helper.createId();
        provider.createCourse(id,"TestFail","Test course",12L,students_1);
        provider.createCourse(id+1,"TestFail 2","Test course 2",12L,students_2);
        assertNull(provider.getCourseById(id));
        assertNull(provider.getCourseById(id+1));
    }


    @Test
    public void createSectionFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        log.info("On test createSectionFail");
        List<Section> sections = new ArrayList<>();
        DataProviderCsv provider = new DataProviderCsv();
        long id = Helper.createId();
        Section section_1 = createSection(id,Helper.createId(),
                Helper.generateRandomString(5),Helper.generateRandomString(20),
                Helper.randomURLs(10),Helper.randomURLs(10));
        sections.add(section_1);
        provider.<Section>dataInsert(sections);
        assertEquals(section_1,provider.getSectionById(id));
    }

}
