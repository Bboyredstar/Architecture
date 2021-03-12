package ru.sfedu.my_pckg.lab4.listCollection.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab4.listCollection.model.Section;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListCollectionTest {

    public ITestEntityDataProvider provider = new HibernateEntityProvider();
    public List<String> materials = Arrays.asList("material1","material2","material3");
    public List<String> videos = Arrays.asList("video1","video2","video3");
    public List<String> materialsUpdated = Arrays.asList("material_1","material_2","material_3");
    public List<String> videosUpdated = Arrays.asList("video_1","video_2","video_3");

    @Test
    void updateSection() {
        Long id = provider.createSection("Test","Description test",materials,videos);
        assertEquals(Status.SUCCESSFUL,provider.updateSection(id,"NewTest","",materialsUpdated,videosUpdated));
    }

    @Test
    void updateSectionFail() {
        Long id = provider.createSection("Test","Description test",materials,videos);
        assertEquals(Status.FAIL,provider.updateSection(123L,"NewTest","",materialsUpdated,videosUpdated));
        assertEquals(Status.FAIL,provider.updateSection(id,"","",materialsUpdated,videosUpdated));
    }

    @Test
    void createSection() {
        Long id = provider.createSection("Test","Description test",materials,videos);
        assertNotNull(provider.getByID(Section.class,id).get());
    }

    @Test
    void createSectionFail() {
        Long id = provider.createSection("","Description test",materials,videos);
        assertNull(id);
    }

    @Test
    void deleteSection() {
        Long id = provider.createSection("Test","Description test",materials,videos);
        assertEquals(Status.SUCCESSFUL,provider.deleteSection(id));
    }

    @Test
    void deleteSectionFail(){
        assertEquals(Status.FAIL,provider.deleteSection(123L));
    }
}