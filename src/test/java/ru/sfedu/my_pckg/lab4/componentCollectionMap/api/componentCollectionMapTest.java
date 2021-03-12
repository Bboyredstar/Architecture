package ru.sfedu.my_pckg.lab4.componentCollectionMap.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab4.componentCollectionMap.model.Course;
import ru.sfedu.my_pckg.lab4.componentCollectionMap.model.Section;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class componentCollectionMapTest {
    public ITestEntityDataProvider provider = new HibernateEntityProvider();
    public static Section section = new Section();

    @Test
    void createCourse() {
        section.setName("New section");
        section.setDescription("New description");
        Map<String ,Section> sections = new HashMap<String,Section>();
        sections.put("section_key",section);
        Long id  = provider.createCourse("New course","",sections );
        assertNotNull(id);
        assertNotNull(provider.getByID(Course.class,id).get());
    }

    @Test
    void createCourseFail(){
        Long id  = provider.createCourse("","", Collections.emptyMap());
        assertNull(id);
    }

    @Test
    void deleteCourse() {
        Long id  = provider.createCourse("New course","", Collections.emptyMap());
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
        Map<String ,Section> sections = new HashMap<String,Section>();
        sections.put("section_key", sectionNew);
        Long id = provider.createCourse("New course","",Collections.emptyMap());
        assertEquals(Status.SUCCESSFUL,provider.updateCourse(id,"New Name","",sections));
    }

    @Test
    void updateCourseFail(){
        assertEquals(Status.FAIL,provider.updateCourse(-1L,"","",Collections.emptyMap()));
    }
}