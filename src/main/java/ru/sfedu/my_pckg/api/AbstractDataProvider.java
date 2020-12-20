package ru.sfedu.my_pckg.api;

import ru.sfedu.my_pckg.beans.Course;
import ru.sfedu.my_pckg.beans.Question;
import ru.sfedu.my_pckg.beans.Review;
import ru.sfedu.my_pckg.beans.Section;
import ru.sfedu.my_pckg.enums.Status;

import java.util.List;

public interface AbstractDataProvider {
    Status createCourse(long id, String name, String description, Long ownerId, List<Long> students);
    Status createSection(long id, String name, String description,
                              long course, List<String> videos, List<String> materials);
    Status deleteSection(long id);
    Status deleteCourse(long id);
    String viewCourse(long id, String extendMethod);
    double getCourseRating(long courseId);
    List<String> getCourseComments(long courseId);
    Status updateCourse(long courseId, String courseName,
                        String courseDescription, List<Long> students,
                        long sectionId, String sectionName, String sectionDescription,
                        List<String> sectionMaterials, List<String> sectionVideos,
                        String extendMethod);
    List<Question> checkQuestions(long courseId, long questionId, String answer);
    Status answerQuestion(long questionId,String answer);
    String chooseCourse(long courseId, long studentId, String extendMethod);
    Status joinCourse(long courseId, long studentId);
    List<Review> checkCourseReviews(long courseId);
    List<Course> getStudentsCourses(long studentId,long courseId,int rating,String comment,String question,String ExtendMethod, boolean needQuestion );
    List<Section> getCourseMaterials(long courseId);
    Status unsubscribeFromACourse(long courseId,long studentId);
    Status leaveAReviewAboutCourse(long courseId,long studentId,int rating,String comment);
    List<String> checkQA(long courseId,long studentId, String question, boolean needQuestion );
    Status askAQuestion(long courseId,long studentId, String question);
}



