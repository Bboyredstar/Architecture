package ru.sfedu.my_pckg.lab5.manyToMany.api;

import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.lab5.manyToMany.model.CourseActivity;

import java.util.List;
import java.util.Optional;

/**
 * The interface Hibernate provider.
 */
public interface IHibernateProvider {
    /**
     * Create course long.
     *
     * @param courseName        the course name
     * @param courseDescription the course description
     * @param courseActivity    the course activity
     * @return the long
     */
    public Long createCourse(String courseName, String courseDescription, List<CourseActivity> courseActivity );

    /**
     * Update course status.
     *
     * @param id               the id
     * @param newName          the new name
     * @param newDescription   the new description
     * @param courseActivities the course activities
     * @return the status
     */
    public Status updateCourse(Long id, String newName, String newDescription, List<CourseActivity> courseActivities);

    /**
     * Delete course status.
     *
     * @param Id the id
     * @return the status
     */
    public Status deleteCourse(Long Id);

    /**
     * Update.
     *
     * @param <T>  the type parameter
     * @param bean the bean
     */
    public <T> void update(T bean);

    /**
     * Gets by id.
     *
     * @param <T>  the type parameter
     * @param bean the bean
     * @param id   the id
     * @return the by id
     */
    public <T> Optional<T> getByID(Class<T> bean, long id);

    /**
     * Save long.
     *
     * @param <T>  the type parameter
     * @param bean the bean
     * @return the long
     */
    public <T>Long save(T bean);
    public String gettingSummaryInformationNative();
    public String gettingSummaryInformationCriteria();
}
