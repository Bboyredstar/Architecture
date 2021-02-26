package ru.sfedu.my_pckg.api;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.my_pckg.lab1.HibernateMetadataProvider;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HibernateMetadataProviderTest {
    public static Logger log = LogManager.getLogger(HibernateMetadataProviderTest.class);
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