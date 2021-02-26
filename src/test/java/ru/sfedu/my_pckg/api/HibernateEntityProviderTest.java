package ru.sfedu.my_pckg.api;


import org.junit.jupiter.api.Test;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab2.model.EmbeddedEntity;
import ru.sfedu.my_pckg.lab2.model.TestBean;
import ru.sfedu.my_pckg.lab2.api.HibernateEntityProvider;
import ru.sfedu.my_pckg.lab2.api.ITestEntityDataProvider;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HibernateEntityProviderTest {
    public HibernateEntityProviderTest(){

    };

    @Test
    public void createBeanSuccess(){
        ITestEntityDataProvider provider = new HibernateEntityProvider();
        EmbeddedEntity embeddedEntity = new EmbeddedEntity();
        embeddedEntity.setName("Nested");
        embeddedEntity.setDescription("Nested Description");
        embeddedEntity.setSessions(Arrays.asList("First","Second"));
        Date date = new Date();
        Long id = provider.createBean("test","test bean description",date,true,embeddedEntity);
        assertNotNull(id);
        TestBean testBean = new TestBean();
        testBean = provider.getByID(TestBean.class,id).get();
        TestBean expected = new TestBean();
        expected.setId(id);
        expected.setId(id);
        expected.setName("test");
        expected.setDescription("test bean description");
        expected.setDate(date);
        expected.setChecking(true);
        expected.setTestEntity(embeddedEntity);
        assertEquals(expected,testBean);
    }

    @Test
    public void deleteTestSuccess(){
        ITestEntityDataProvider provider = new HibernateEntityProvider();
        Date date = new Date();
        TestBean bean = new TestBean();
        bean.setDate(date);
        bean.setDescription("Test bean");
        bean.setName("Test");
        Long id = provider.save(bean);
        assertEquals(Status.SUCCESSFUL,provider.delete(id));
        assertEquals(Optional.empty(),provider.getByID(TestBean.class,id));
    }

    @Test
    public void deleteTestFail(){
        ITestEntityDataProvider provider = new HibernateEntityProvider();
        assertEquals(Status.FAIL,provider.delete(-1L));
    }

    @Test
    public void updateSuccess(){
        ITestEntityDataProvider provider = new HibernateEntityProvider();
        EmbeddedEntity embeddedEntity = new EmbeddedEntity();
        embeddedEntity.setName("Nested");
        embeddedEntity.setDescription("Nested Description");
        embeddedEntity.setSessions(Arrays.asList("First","Second"));
        Date date = new Date();
        Long id = provider.createBean("test","test bean description",date,true,embeddedEntity);
        assertEquals(Status.SUCCESSFUL,provider.updateBean(id,"test","new bean description",date,true,embeddedEntity));
        assertEquals("new bean description",provider.getByID(TestBean.class,id).get().getDescription());
    }

    @Test
    public void updateFail(){
        ITestEntityDataProvider provider = new HibernateEntityProvider();
        Date date = new Date();
        assertEquals(Status.FAIL,provider.updateBean(-1L,"test","new bean description",date,true,null));
    }


}