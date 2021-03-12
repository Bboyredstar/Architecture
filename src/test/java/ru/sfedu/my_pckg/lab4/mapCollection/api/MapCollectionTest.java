package ru.sfedu.my_pckg.lab4.mapCollection.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab4.mapCollection.model.Section;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapCollectionTest {

    public ITestEntityDataProvider provider = new HibernateEntityProvider();
    public static Map<String,String> materials = new HashMap<String,String>();
    public static Map<String,String> videos = new HashMap<String,String>();
    public static Map<String,String> materialsUpdated = new HashMap<String,String>();
    public static Map<String,String> videosUpdated = new HashMap<String,String>();

    @Test
    void updateSection() {
        materials.put("material_key","material_1");
        videos.put("video_key","video_1");
        materialsUpdated.put("key","material_upd_2");
        videosUpdated.put("key","video_upd_1");
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
        materials.put("material_key","material_1");
        videos.put("video_key","video_1");
        Long id = provider.createSection("HashMap","Description test",materials,videos);
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