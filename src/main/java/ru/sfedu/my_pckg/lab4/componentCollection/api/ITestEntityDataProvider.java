package ru.sfedu.my_pckg.lab4.componentCollection.api;


import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab4.componentCollection.model.Section;

import java.util.List;
import java.util.Optional;

/**
 * The interface Test entity data provider.
 */
public interface ITestEntityDataProvider {
    /**
     * Gets bean by id.
     *
     * @param <T>  the type parameter
     * @param bean the bean
     * @param id   the id
     * @return the by id
     */
    public <T> Optional<T> getByID(Class<T> bean,long id);

    /**
     * Save bean in DS.
     *
     * @param <T>  the type parameter
     * @param bean the bean
     * @return the id
     */
    public<T> Long save(T bean);

    /**
     * Update bean.
     *
     * @param <T>  the type parameter
     * @param bean the TestBean
     */
    public <T >void update(T bean);

    /**
     * Create course long.
     *
     * @param courseName        the course name
     * @param courseDescription the course description
     * @param sections          the sections
     * @return the long
     */
    public Long createCourse(String courseName,String courseDescription,List<Section> sections);

    /**
     * Delete course status.
     *
     * @param Id the id
     * @return the status
     */
    public Status deleteCourse(Long Id);

    /**
     * Instantiates a new Update course.
     *
     * @param courseName        the course name
     * @param courseDescription the course description
     * @param sections          the sections
     * @return the status
     */
    public Status updateCourse(Long id,String courseName,String courseDescription,List<Section> sections );
}
