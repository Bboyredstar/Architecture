package ru.sfedu.my_pckg.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.my_pckg.BaseTest;
import ru.sfedu.my_pckg.beans.Course;
import ru.sfedu.my_pckg.beans.Section;
import ru.sfedu.my_pckg.beans.Student;
import ru.sfedu.my_pckg.beans.Teacher;
import ru.sfedu.my_pckg.enums.Status;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;


class DataProviderJDBCTest extends BaseTest {
    public static Logger log = LogManager.getLogger(ru.sfedu.my_pckg.api.DataProviderJDBCTest.class);
    public DataProviderJDBC provider = new DataProviderJDBC();
    Teacher teacher_1 = createTeacher(12L, "John", "Beach", 40,
            "asdasd@mail.ru", "Romania", "Machine Learning",
            10);
    Student student_1 = createStudent(12L, "John", "Beach", 40,
            "asdasd@mail.ru", "Romania", "Machine Learning");
    Student student_2 = createStudent(13L, "John", "Beach", 40,
            "asdasd@mail.ru", "Romania", "Machine Learning");
    Student student_3 = createStudent(14L, "John", "Beach", 40,
            "asdasd@mail.ru", "Romania", "Machine Learning");
    Student student_4 = createStudent(15L, "John", "Beach", 40,
            "asdasd@mail.ru", "Romania", "Machine Learning");
    List<String> videos = Arrays.asList("www.test.com", "www.test2.com");
    List<String> materials = Arrays.asList("www.test.com", "www.test2.com");
    List<String> videos_Upd= Arrays.asList("www.test_1.com", "www.test_2.com");
    List<String> materials_Upd = Arrays.asList("www.test_1.com", "www.test_2.com");
    List<Long> studentsIds = Arrays.asList(12L, 13L, 14L);
    Course course_1 = createCourse(1234, "Test", "Test course", 12L, studentsIds);
    Section section_1 = createSection(1234, "Test", "Test section", 1234, videos, materials);
    Section section_update = createSection(1234,"New Name", "New description",1234,videos_Upd,materials_Upd);

    DataProviderJDBCTest() throws IOException {}

    @Test
    public void testInsertTeacherSuccess() throws Exception {
        log.debug("On test TeacherInsertSuccess");
        provider.deleteRecords(Teacher.class);
        assertEquals(Status.SUCCESSFUL,provider.insertTeacher(Collections.singletonList(teacher_1)));
        assertEquals(teacher_1,provider.<Teacher>getRecords(Teacher.class).get(0));
    }

    @Test
    public void testInsertStudentSuccess() throws Exception {
        log.debug("On test testInsertStudentSuccess");
        provider.deleteRecords(Student.class);
        assertEquals(Status.SUCCESSFUL,provider.insertStudent(Collections.singletonList(student_1)));
        assertEquals(student_1,provider.<Student>getRecords(Student.class).get(0));
    }
}