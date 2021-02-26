package ru.sfedu.my_pckg.lab2.api;


import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab2.model.EmbeddedEntity;
import ru.sfedu.my_pckg.lab2.model.TestBean;

import java.util.Date;
import java.util.Optional;

/**
 * The interface Test entity data provider.
 */
public interface ITestEntityDataProvider {
    /**
     * Gets bean by id.
     *
     * @param bean the bean
     * @param id   the id
     * @return the by id
     */
    public Optional<TestBean> getByID(Class<TestBean> bean,long id);

    /**
     * Save bean in DS.
     *
     * @param bean the bean
     * @return the id
     */
    public Long save(TestBean bean);

    /**
     * Update bean.
     *
     * @param bean the TestBean
     */
    public void update(TestBean bean);

    /**
     * Delete bean from DS.
     *
     * @param id the id
     */
    public Status delete(Long id);

    /**
     * Create bean .
     *
     * @param beanName        the bean name
     * @param beanDescription the bean description
     * @param date            the date
     * @param check           the check
     * @param embeddedEntity  the embedded entity
     * @return the id
     */
    public Long createBean(String beanName, String beanDescription, Date date, Boolean check, EmbeddedEntity embeddedEntity);

    /**
     * Update bean.
     *
     * @param id              the id
     * @param beanName        the bean name
     * @param beanDescription the bean description
     * @param date            the date
     * @param check           the check
     * @param embeddedEntity  the embedded entity
     * @return Status
     */
    public Status updateBean(Long id,String beanName, String beanDescription, Date date, Boolean check, EmbeddedEntity embeddedEntity);
}
