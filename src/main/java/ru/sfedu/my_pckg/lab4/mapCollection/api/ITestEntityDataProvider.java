package ru.sfedu.my_pckg.lab4.mapCollection.api;


import ru.sfedu.my_pckg.enums.Status;

import java.util.Map;
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
    public <T> Optional<T> getByID(Class<T> bean,long id);

    /**
     * Save bean in DS.
     *
     * @param bean the bean
     * @return the id
     */
    public<T> Long save(T bean);

    /**
     * Update bean.
     *
     * @param bean the TestBean
     */
    public <T >void update(T bean);

    /**
     * Delete section status.
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
     * @param materials   the materials
     * @param videos      the videos
     * @return the status
     */
    public Status updateSection(Long id,String name, String description,
                                Map<String,String> materials, Map<String,String> videos);

    /**
     * Create section long.
     *
     * @param name        the name
     * @param description the description
     * @param materials   the materials
     * @param videos      the videos
     * @return the long
     */
    public Long createSection(String name, String description,
                              Map<String,String> materials, Map<String,String> videos);
}
