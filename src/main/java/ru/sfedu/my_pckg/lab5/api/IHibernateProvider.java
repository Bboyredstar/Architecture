package ru.sfedu.my_pckg.lab5.api;

import ru.sfedu.my_pckg.enums.Status;

import java.util.List;
import java.util.Optional;

/**
 * The interface Hibernate provider.
 */
public interface IHibernateProvider {

    /**
     * Create entity long.
     *
     * @param classname  the classname
     * @param parameters the parameters
     * @return the long
     */
    public Long createEntity(String classname, List<String> parameters);

    /**
     * Delete entity status.
     *
     * @param id        the id
     * @param classname the classname
     * @return the status
     */
    public Status deleteEntity(Long id, String classname);

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
    public <T> Long save(T bean);

    /**
     * Update entity status.
     *
     * @param classname  the classname
     * @param parameters the parameters
     * @return the status
     */
    public Status updateEntity(String classname, List<String> parameters);
}
