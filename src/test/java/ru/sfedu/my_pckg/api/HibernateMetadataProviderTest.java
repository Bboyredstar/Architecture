package ru.sfedu.my_pckg.api;


import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HibernateMetadataProviderTest {
    HibernateMetadataProvider instance = new HibernateMetadataProvider();

    @Test
    void getAllSchemas() {
        List result = instance.getAllSchemas();
        assertNotNull(result);
    }

    @Test
    void getCatalogName() {
        String result = instance.getCatalogName();
        assertNotNull(result);
        assertEquals("ProjectApp",result);
    }

    @Test
    void getCurrentUser() {
        String result = instance.getCurrentUser();
        assertNotNull(result);
    }

    @Test
    void getTablesName(){
        List result = instance.getTablesName();
        assertNotNull(result);
    }

    @Test
    void getTablesSize() {
        List result = instance.getTablesSize();
        assertNotNull(result);
    }
}