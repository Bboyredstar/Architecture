package ru.sfedu.my_pckg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab3.MappedSuperclass.api.HibernateEntityProvider;
import ru.sfedu.my_pckg.lab3.MappedSuperclass.api.ITestEntityDataProvider;
import ru.sfedu.my_pckg.lab3.MappedSuperclass.model.Student;
import ru.sfedu.my_pckg.lab3.MappedSuperclass.model.Teacher;
import ru.sfedu.my_pckg.lab3.MappedSuperclass.model.User;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

public class BaseTest {
    public static Logger log = LogManager.getLogger(BaseTest.class);
    ITestEntityDataProvider provider = new HibernateEntityProvider();
    public BaseTest(){}

    @Test
    public void createStudentSuccess(){
        Long id = provider.createStudent("Lu","Mollo",20,"sasd@mail.com","America","test");
        assertNotNull(id);
    }

    @Test
    public void createTeacherSuccess(){
        Long id = provider.createTeacher("Geno","Floyd",32,"nnnmail.com","Russia","test",12);
        assertNotNull(id);
    }

    @Test
    public void deleteTeacherSuccess(){
        Long id = provider.createTeacher("Georg","Floyd",32,"nnnmail.com","Russia","test",12);
        assertEquals(Status.SUCCESSFUL,provider.deleteTeacher(id));
    }

    @Test
    public void deleteStudentSuccess(){
        Long id = provider.createStudent("Luca","Mollo",20,"sasd@mail.com","America","test");
        assertEquals(Status.SUCCESSFUL,provider.deleteStudent(id));
    }

    @Test
    public void updateTeacherSuccess(){
       Long id = provider.createTeacher("Nick","Floyd",24,"nnnmail.com","Russia","test",12);
       assertEquals(Status.SUCCESSFUL, provider.updateTeacher(id,"Georgio","Floyd",23,"nnnmail.com","Russia","test",12));
    }

    @Test
    public void updateStudentSuccess(){
        Long id = provider.createStudent("Lucas","Mollo",21,"sasd@mail.com","America","test");
        assertEquals(Status.SUCCESSFUL,provider.updateStudent(id,"Lucas","Mollo",21,"sasd@mail.com","America","test"));
    }
}
