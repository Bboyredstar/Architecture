package ru.sfedu.my_pckg.api;

import ru.sfedu.my_pckg.beans.Review;
import ru.sfedu.my_pckg.beans.Section;
import ru.sfedu.my_pckg.enums.Status;

import java.util.List;

/**
 * The interface Abstract data provider.
 */
public interface AbstractDataProvider {
    /**
     * Метод создает entity bean Course
     *
     * @param id          the id
     * @param courseName        the name
     * @param courseDescription the description
     * @param courseOwner     the owner id
     * @param students    the students
     * @return the status
     *
     * Если метод выполнился успешно:
     * Status.SUCCESSFUL;
     * Если метод выполнился неудачно:
     * Status.FAIL;
     */
    Status createCourse(long id, String courseName, String courseDescription, Long courseOwner, List<Long> students);

    /**
     * Метод удаляет Course и все связанные объекты из Data Source
     *
     * @param courseId the course id
     * @return the status
     *
     * Если метод выполнился успешно:
     * Status. SUCCESSFUL;
     * Если метод выполнился неудачно:
     * Status.FAIL;
     */
    Status deleteCourse(long courseId);


    /**
     * Метод выводит информацию о курсе
     *
     * @param courseId   the  course id
     * @param extendMethod the extend method
     * @return the string
     *
     * Если метод выполнился успешно:
     * String course;
     * Если метод выполнился неудачно:
     * null
     */
    String viewCourse(long courseId, String extendMethod);

    /**
     * Метод выводит среднюю оценку курса, на основе оценок, которые поставили студенты.
     * Метод вызывается, если в метод View course передается строка extendMethod,
     *  значение которой равно полю Enum ExtendMethods.RATING.
     *
     * @param courseId the course id
     * @return the course rating
     *
     * Если метод выполнился успешно:
     * Double avgCourseRating;
     * Если метод выполнился неудачно:
     * Double -1.0;
     */
    double getCourseRating(long courseId);


    /**
     * Метод возвращает комментарии к курсу.
     * Метод вызывается, если в метод View course передается строка extendMethod,
     * значение которой равно полю Enum ExtendMethods.COMMENTS.
     * Входной параметр courseId  поступает из вешнего метода viewCourse.
     *
     * @param courseId the course id
     * @return the course comments
     *
     * Если метод выполнился успешно:
     * List<String> comments
     * Если метод выполнился неудачно:
     * Null;
     */
    List<String> getCourseComments(long courseId);

    /**
     * Метод изменяет поля entity bean Course
     *
     * @param courseId           the course id
     * @param courseName         the course name
     * @param courseDescription  the course description
     * @param sectionId          the section id
     * @param sectionName        the section name
     * @param sectionDescription the section description
     * @param sectionMaterials   the section materials
     * @param sectionVideos      the section videos
     * @param extendMethod       the extend method
     * @return the status
     *
     * Если метод выполнился успешно:
     * Status.SUCCESSFUL;
     * Если метод выполнился неудачно:
     * Status.FAIL;
     */
    Status updateCourse(long courseId, String courseName,
                        String courseDescription,
                        long sectionId, String sectionName, String sectionDescription,
                        List<String> sectionMaterials, List<String> sectionVideos,
                        String extendMethod);

    /**
     * Метод создает entity bean Section
     * Метод вызывается, если в метод updateCourse строка extendMethod,
     * значение которой равно полю Enum ExtendMethods.CREATE.
     * Входные параметры courseId, name, description, materials, videos
     * поступают из вешнего метода updateCourse.
     *
     * @param sectionId          the id
     * @param sectionName        the name
     * @param sectionDescription the description
     * @param course      the course
     * @param sectionVideos      the videos
     * @param sectionMaterials   the materials
     * @return the status
     *
     * Если метод выполнился успешно:
     * Status.SUCCESSFUL;
     * Если метод выполнился неудачно:
     * Status.FAIL;
     */
    Status createSection(long sectionId, String sectionName, String sectionDescription,
                         long course, List<String> sectionVideos, List<String> sectionMaterials
    );

    /**
     * Метод меняет поля entity bean Section
     * Метод вызывается, если в метод updateCourse строка extendMethod,
     * начение которой равно полю Enum ExtendMethods.UPDATE.
     * Входные параметры name, description, materials, videos
     * поступают из вешнего метода updateCourse.
     *
     * @param sectionId          the id
     * @param sectionName        the name
     * @param sectionDescription the description
     * @param sectionVideos      the videos
     * @param sectionMaterials   the materials
     * @return the status
     *
     * Если метод выполнился успешно:
     * Status.SUCCESSFUL;
     * Если метод выполнился неудачно:
     * Status.FAIL;
     */
    Status updateSection(long sectionId, String sectionName,
                         String sectionDescription,  List<String> sectionVideos, List<String> sectionMaterials);

    /**
     * Метод удаляет entity bean Section из Data Source
     * Метод вызывается, если в метод update course строка extendMethod, значение которой равно полю Enum ExtendMethods.DELETE.
     * Входной параметр sectionId поступает из вешнего метода update course.
     *
     * @param sectionId the id
     * @return the status
     */
    Status deleteSection(long sectionId);

    /**
     * Метод выводит список вопросов к курсу.
     *
     * @param courseId   the course id
     * @param questionId the question id
     * @param answer     the answer
     * @return the string
     *
     * Если метод выполнился успешно:
     * String questions;
     * Если метод выполнился неудачно:
     * null;
     */
    String checkStudentsQuestions(long courseId, long questionId, String answer);

    /**
     * Метод создает entity bean Answer
     * Метод вызывается, если в метод checkStudentsQuestions передается (questionId!=-1,answer!=””)
     * Входные параметры questionId, answer поступают из вешнего метода update course.
     *
     * @param questionId the question id
     * @param answer     the answer
     * @return the status
     *
     * Если метод выполнился успешно:
     * Status.SUCCESSFUL;
     * Если метод выполнился неудачно:
     * Status.FAIL;
     */

    Status answerQuestion(long questionId,String answer);

    /**
     * Метод выводит список курсов, после этого студент может
     * записаться на курс или посмотреть оценки и отзывы этого курса.
     *
     * @param courseId     the course id
     * @param studentId    the student id
     * @param extendMethod the extend method
     * @return the string
     *
     * Если метод выполнился успешно:
     * String courses;
     * Если метод выполнился неудачно:
     * Null;
     */
    String chooseCourse(long courseId, long studentId, String extendMethod);

    /**
     * Метод  записывает id студента в поле students выбранного курса
     * Метод вызывается, если в метод chooseCourse передается строка extendMethod,
     * значение которой равно ExtendMethods.JOIN.
     * Входные параметры я courseId, studentId поступают из вешнего метода chooseCourse.
     *
     * @param courseId  the course id
     * @param studentId the student id
     * @return the status
     *
     * Если метод выполнился успешно:
     * Status.SUCCESSFUL;
     * Если метод выполнился неудачно:
     * Status.FAIL;
     */
    Status joinCourse(long courseId, long studentId);

    /**
     * Метод выводит оценки и комментарии студентов по курсу.
     * Метод вызывается, если в метод chooseCourse передается строка extendMethod,
     * значение которой равно ExtendMethods.REVIEW.
     * Входной параметр courseId поступает из вешнего метода chooseCourse.
     *
     * @param courseId the course id
     * @return the list
     *
     * Если метод выполнился успешно:
     * List <Review>.
     * Если метод выполнился неудачно:
     * Null;
     */
    List<Review> checkCourseReviews(long courseId);

    /**
     * Метод выводит список курсов студента
     *
     * @param studentId    the student id
     * @param courseId     the course id
     * @param rating       the rating
     * @param comment      the comment
     * @param question     the question
     * @param ExtendMethod the extend method
     * @param needQuestion the need question
     * @return the students courses
     *
     * Если метод выполнился успешно:
     * String courses;
     * Если метод выполнился неудачно:
     * Null;
     */
    String getStudentsCourses(long studentId,long courseId,int rating,String comment,String question,String ExtendMethod, boolean needQuestion );

    /**
     * Метод выводит список разделов курса.
     * Метод вызывается, если в метод getStudentsCourses
     * передается строка extendMethod, значение которой равно ExtendMethods.MATERIAL.
     * Входной параметр courseId поступает из вешнего метода getStudentsCourses.
     *
     * @param courseId the course id
     * @return the course materials
     *
     * Если метод выполнился успешно:
     * List<Section> sections;
     * Если метод выполнился неудачно:
     * Null;
     */
    List<Section> getCourseMaterials(long courseId);

    /**
     * Метод удаляет id студента из поля students Course,
     * а также удаляет CourseActivity студента .
     * Метод вызывается, если в метод  get students courses
     * передается строка extendMethod, значение которой равно ExtendMethods.UNSUBSCRIBE.
     * Входные параметры courseId,studentId поступают из вешнего метода getStudentsCourses.
     *
     * @param courseId  the course id
     * @param studentId the student id
     * @return the status
     *
     * Если метод выполнился успешно:
     * Status.SUCCESSFUL;
     * Если метод выполнился неудачно:
     * Status.FAIL;
     */
    Status unsubscribeFromACourse(long courseId,long studentId);

    /**
     * Метод создает entity bean Review и добавляет его id в CourseActivity.
     * Метод вызывается, если в метод getStudentsCourses
     * передается строка extendMethod, значение которой равно ExtendMethods.REVIEW.
     * Входные параметры courseId, studentId, rating, comment
     * поступают из вешнего метода getStudentsCourses.
     *
     * @param courseId  the course id
     * @param studentId the student id
     * @param rating    the rating
     * @param comment   the comment
     * @return the status
     *
     * Если метод выполнился успешно:
     * Status.SUCCESSFUL;
     * Если метод выполнился неудачно:
     * Status.FAIL;
     */
    Status leaveAReviewAboutCourse(long courseId,long studentId,int rating,String comment);

    /**
     * Метод выводит список вопросов и ответов по указанному курсу
     * Метод вызывается, если в метод getStudentsCourses
     * передается строка extendMethod, значение которой равно ExtendMethods.QA.
     * Входные параметры courseId, needQuestion поступают из вешнего метода getStudentsCourses.
     *
     * @param courseId     the course id
     * @param studentId    the student id
     * @param question     the question
     * @param needQuestion the need question
     * @return the string
     *
     * Если метод выполнился успешно:
     * String QA.
     * Сообщение с предложением задать вопрос
     * Если метод выполнился неудачно:
     * Null;
     */
    String checkQA(long courseId,long studentId, String question, boolean needQuestion );

    /**
     * Метод создает entity bean Question и
     * добавляет его id в поле questions CourseActivity.
     * Метод выполняется, если в метод checkQA передается флаг needQuestions со значением True.
     * Входные параметры  courseId, studentId, question поступают из вешнего метода checkQA.
     *
     * @param courseId  the course id
     * @param studentId the student id
     * @param question  the question
     * @return the status
     *
     * Если метод выполнился успешно:
     * Status.SUCCESSFUL;
     * Если метод выполнился неудачно:
     * Status.FAIL;
     */
    Status askAQuestion(long courseId,long studentId, String question);

    /**
     * Инициализирует корневые сущности проекта в разных DS.
     *
     * @return the status
     *
     *  Если метод выполнился успешно:
     *  Status.SUCCESSFUL;
     *  Если метод выполнился неудачно:
     *  Status.FAIL;
     */
    Status dataInitialization();
}



