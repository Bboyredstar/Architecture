package ru.sfedu.my_pckg.lab5.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab5.model.Course;
import ru.sfedu.my_pckg.lab5.model.Student;
import ru.sfedu.my_pckg.lab5.model.Teacher;
import ru.sfedu.my_pckg.utils.helpers.Helper;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class HibernateProviderTest {
    static HibernateProvider provider = new HibernateProvider();

    @Test
    void createEntity(){
        Long id = provider.createEntity("Teacher",Arrays.asList("Name","Surname","10","email","country","preferences","10"));
        assertNotNull(id);
    }

    @Test
    void createCourseTEst(){
        Long id = provider.createEntity("Course",Arrays.asList("Name","123","Description"));
        assertNotNull(id);
    }

    @Test
    void createEntityFail(){
        Long id = provider.createEntity("",Arrays.asList("Name","Surname","10","email","country","preferences","10"));
        assertNull(id);
    }

    @Test
    void getById() {
        provider.getByID(Teacher.class, 123);
    }
    @Test
    void createStudent() {
        Long id = provider.createStudent(Helper.generateUserFName(),Helper.generateUserLName(),Helper.randomNumber(25,30),Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences());
        assertNotNull(id);
    }

    @Test
    void createTeacher() {
        Long id = provider.createTeacher(Helper.generateUserFName(),Helper.generateUserLName(),Helper.randomNumber(25,30),Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences(),Helper.randomNumber(3,10));
        assertNotNull(id);
    }

    @Test
    void createSection() {
        Long teacherId = provider.createTeacher(Helper.generateUserFName(),Helper.generateUserLName(),Helper.randomNumber(25,30),Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences(),Helper.randomNumber(3,10));
        Teacher owner = new Teacher();
        owner = provider.getByID(Teacher.class,teacherId).get();
        Long courseId = provider.createCourse(Helper.generateRandomString(10),teacherId,Helper.generateRandomString(30));
        Long id = provider.createSection(Helper.generateRandomString(20),Helper.generateRandomString(30),courseId, Arrays.asList("test","test","test"), Arrays.asList("test","test","test"));
        assertNotNull(id);
    }

    @Test
    void createCourse() {
        Long id = provider.createTeacher(Helper.generateUserFName(),Helper.generateUserLName(),Helper.randomNumber(25,30),Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences(),Helper.randomNumber(3,10));
        Teacher owner = new Teacher();
        owner = provider.getByID(Teacher.class,id).get();
        Long courseId = provider.createCourse(Helper.generateRandomString(10),id,Helper.generateRandomString(30));
    }

    @Test
    void createReview(){
        Long id = provider.createTeacher(Helper.generateUserFName(),Helper.generateUserLName(),Helper.randomNumber(25,30),Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences(),Helper.randomNumber(3,10));
        Teacher owner = new Teacher();
        owner = provider.getByID(Teacher.class,id).get();
        Long courseId = provider.createCourse(Helper.generateRandomString(10),id,Helper.generateRandomString(30));
        Long review = provider.createReview(courseId,5,"Test comment");
        assertNotNull(review);
    }


    @Test
    void addingStudents() {
        Long id = provider.createTeacher(Helper.generateUserFName(),Helper.generateUserLName(),Helper.randomNumber(25,30),Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences(),Helper.randomNumber(3,10));
        Teacher owner = new Teacher();
        owner = provider.getByID(Teacher.class,id).get();
        Long courseId = provider.createCourse(Helper.generateRandomString(10),id,Helper.generateRandomString(30));
        Course course = provider.getByID(Course.class,courseId).get();
        Long student = provider.createStudent(Helper.generateUserFName(),Helper.generateUserLName(),Helper.randomNumber(25,30),Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences());
        Student student1 = provider.getByID(Student.class,student).get();
        course.setStudents(Collections.singletonList(student1));
        provider.update(course);
    }

    @Test
    void deleteEntity(){
        Long id = provider.createTeacher(Helper.generateUserFName(),Helper.generateUserLName(),Helper.randomNumber(25,30),Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences(),Helper.randomNumber(3,10));

        assertEquals(Status.SUCCESSFUL,provider.deleteEntity(id,"Teacher"));
    }

    @Test
    void deleteEntityFail(){
         assertEquals(Status.FAIL,provider.deleteEntity(-1L,"Teacher"));
    }

    @Test
    void updateEntity(){
        Long id = provider.createTeacher(Helper.generateUserFName(),Helper.generateUserLName(),Helper.randomNumber(25,30),Helper.randomEmail(),Helper.randomCountry(),Helper.randomPreferences(),Helper.randomNumber(3,10));
        assertEquals(Status.SUCCESSFUL,provider.updateEntity("Teacher",Arrays.asList(String.valueOf(id),"New Name Upd","","10","mail","country","pref","3")));
    }

    @Test
    void updateEntityFail(){
        assertEquals(Status.FAIL,provider.updateEntity("",Arrays.asList("1","New Name Upd","","10","mail","country","pref","3")));
    }

    @Test
    void getSummaryNative(){
       assertNotNull(provider.gettingSummaryInformationNative());
    }

    @Test
    void getSummaryCriteria(){
        assertNotNull(provider.gettingSummaryInformationCriteria());
    }

    @Test
    void getSummaryH2(){
        assertNotNull(provider.gettingSummaryInformationHQL());
    }

    @Test
    void getTimeNative(){
        provider.executeTimeNative();
    }

    @Test
    void getTimeHQL(){
        provider.executeTimeHQL();
    }


    @Test
    void getTimeCriteria(){
        provider.executeTimeCriteria();
    }
}