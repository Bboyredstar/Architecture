package ru.sfedu.my_pckg.api;


import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HibernateMetadataProviderTest {


    @Test
    void getAllSchemas() {
        HibernateMetadataProvider instance = new HibernateMetadataProvider();
        List result = instance.getAllSchemas();
        assertNotNull(result);
    }
}