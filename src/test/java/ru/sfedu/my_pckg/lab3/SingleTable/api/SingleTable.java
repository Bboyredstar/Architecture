package ru.sfedu.my_pckg.lab3.SingleTable.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.my_pckg.MappedSuperclass;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab3.SingleTable.api.HibernateEntityProvider;
import ru.sfedu.my_pckg.lab3.SingleTable.api.ITestEntityDataProvider;

import static org.junit.jupiter.api.Assertions.*;

class SingleTable {
    public static Logger log = LogManager.getLogger(SingleTable.class);
    ITestEntityDataProvider provider = new HibernateEntityProvider();

    @Test
    public void createStudentSuccess() {
        Long id = provider.createStudent("Lu", "Mollo", 20, "sasd@mail.com", "America", "test");
        assertNotNull(id);
    }

    @Test
    public void createTeacherSuccess() {
        Long id = provider.createTeacher("Geno", "Floyd", 32, "nnnmail.com", "Russia", "test", 12);
        assertNotNull(id);
    }

    @Test
    public void deleteTeacherSuccess() {
        Long id = provider.createTeacher("Georg", "Floyd", 32, "nnnmail.com", "Russia", "test", 12);
        assertEquals(Status.SUCCESSFUL, provider.deleteTeacher(id));
    }

    @Test
    public void deleteStudentSuccess() {
        Long id = provider.createStudent("Luca", "Mollo", 20, "sasd@mail.com", "America", "test");
        assertEquals(Status.SUCCESSFUL, provider.deleteStudent(id));
    }

    @Test
    public void updateTeacherSuccess() {
        Long id = provider.createTeacher("Geno", "Floyd", 32, "nnnmail.com", "Russia", "test", 12);
        assertEquals(Status.SUCCESSFUL, provider.updateTeacher(id, "Georg", "Floyd", 23, "nnnmail.com", "Russia", "test", 12));
    }

    @Test
    public void updateStudentSuccess() {
        Long id = provider.createStudent("Lucas", "Mollo", 21, "sasd@mail.com", "America", "test");
        assertEquals(Status.SUCCESSFUL, provider.updateStudent(id, "Lucas", "Mollo", 21, "sasd@mail.com", "America", "test"));
    }
}