package ru.sfedu.my_pckg.lab4.listCollection.api;


import ru.sfedu.my_pckg.enums.Status;

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
    public <T> Optional<T> getByID(Class<T> bean, long id);

    /**
     * Save bean in DS.
     *
     * @param <T>  the type parameter
     * @param bean the bean
     * @return the id
     */
    public <T> Long save(T bean);

    /**
     * Update bean.
     *
     * @param <T>  the type parameter
     * @param bean the TestBean
     */
    public <T> void update(T bean);

    /**
     * Delete section from DS.
     *
     * @param Id the id
     * @return the status
     */
    public Status deleteSection(Long Id);


    /**
     * Update section status.
     *
     * @param id          the id
     * @param name        the name
     * @param description the description
     * @param videos      the videos
     * @param materials   the materials
     * @return the status
     */
    public Status updateSection(long id, String name,
                                String description, List<String> videos, List<String> materials);

    /**
     * Create section long.
     *
     * @param name        the name
     * @param description the description
     * @param videos      the videos
     * @param materials   the materials
     * @return the long
     */
    public Long createSection(String name, String description,
                              List<String> videos, List<String> materials);

}