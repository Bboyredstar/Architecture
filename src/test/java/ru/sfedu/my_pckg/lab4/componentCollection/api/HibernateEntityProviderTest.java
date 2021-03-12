package ru.sfedu.my_pckg.lab4.componentCollection.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab4.componentCollection.model.Course;
import ru.sfedu.my_pckg.lab4.componentCollection.model.Section;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class HibernateEntityProviderTest {

    public ITestEntityDataProvider provider = new HibernateEntityProvider();
    public static Section section = new Section();

    @Test
    void createCourse() {
        section.setName("New section");
        section.setDescription("New description");
        Long id  = provider.createCourse("New course","", Collections.singletonList(section));
        assertNotNull(id);
        assertNotNull(provider.getByID(Course.class,id).get());
    }

    @Test
    void createCourseFail(){
        Long id  = provider.createCourse("","", Collections.singletonList(section));
        assertNull(id);
    }

    @Test
    void deleteCourse() {
        Long id  = provider.createCourse("New course","", Collections.emptyList());
        assertEquals(Status.SUCCESSFUL,provider.deleteCourse(id));
    }

    @Test
    void deleteCourseFail() {
        assertEquals(Status.FAIL,provider.deleteCourse(-1L));
    }

    @Test
    void updateCourse() {
        Section sectionNew = new Section();
        sectionNew.setName("New section");
        sectionNew.setDescription("New description");
        Long id = provider.createCourse("New course","",Collections.singletonList(sectionNew));
        assertEquals(Status.SUCCESSFUL,provider.updateCourse(id,"New Name","",Collections.singletonList(sectionNew)));
    }

    @Test
    void updateCourseFail(){
        assertEquals(Status.FAIL,provider.updateCourse(-1L,"","",Collections.emptyList()));
    }
}