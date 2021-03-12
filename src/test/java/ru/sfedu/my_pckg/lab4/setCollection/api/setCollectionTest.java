package ru.sfedu.my_pckg.lab4.setCollection.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab4.setCollection.model.Section;


import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class setCollectionTest {

    public Set<String> materials = Stream.of("material1", "material2", "material3")
            .collect(Collectors.toSet());
    public Set<String> videos = Stream.of("video1","video2","video3")
            .collect(Collectors.toSet());
    public Set<String> materialsUpdated = Stream.of("material_1","material_2","material_3")
            .collect(Collectors.toSet());
    public Set<String> videosUpdated = Stream.of("video_1","video_2","video_3")
            .collect(Collectors.toSet());
    public ITestEntityDataProvider provider = new HibernateEntityProvider();
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
