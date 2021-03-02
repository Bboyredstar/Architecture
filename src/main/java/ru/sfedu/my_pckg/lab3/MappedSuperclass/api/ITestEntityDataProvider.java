package ru.sfedu.my_pckg.lab3.MappedSuperclass.api;


import ru.sfedu.my_pckg.enums.Status;


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
     * Delete Student from DS.
     *
     * @param Id the id
     */
    public Status deleteStudent(Long Id);

    /**
     * Delete Teacher from DS.
     *
     * @param Id the id
     */
    public Status deleteTeacher(Long Id);

    /**
     * Create student long.
     *
     * @param fname       the fname
     * @param lname       the lname
     * @param age         the age
     * @param email       the email
     * @param country     the country
     * @param preferences the preferences
     * @return the long
     */
    public Long createStudent(String fname, String lname, int age,
                              String email, String country, String preferences);

    /**
     * Create teacher long.
     *
     * @param fname      the fname
     * @param lname      the lname
     * @param age        the age
     * @param email      the email
     * @param country    the country
     * @param competence the competence
     * @param experience the experience
     * @return the long
     */
    public Long createTeacher(String fname,String lname, int age,
                              String email,String country,String competence,
                              int experience);

    /**
     * Update teacher status.
     *
     * @param id         the id
     * @param fname      the fname
     * @param lname      the lname
     * @param age        the age
     * @param email      the email
     * @param country    the country
     * @param competence the competence
     * @param experience the experience
     * @return the status
     */
    public Status updateTeacher(Long id,String fname,String lname, int age,
                                String email,String country,String competence,
                                int experience);

    /**
     * Update student status.
     *
     * @param id          the id
     * @param fname       the fname
     * @param lname       the lname
     * @param age         the age
     * @param email       the email
     * @param country     the country
     * @param preferences the preferences
     * @return the status
     */
    public Status updateStudent(Long id,String fname, String lname, int age,
                                String email, String country, String preferences);
}
