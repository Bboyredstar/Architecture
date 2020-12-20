package ru.sfedu.my_pckg.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.my_pckg.BaseTest;
import ru.sfedu.my_pckg.Constants;
import ru.sfedu.my_pckg.beans.*;
import ru.sfedu.my_pckg.enums.Status;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DataProviderXMLTest extends BaseTest {
    public static Logger log = LogManager.getLogger(DataProviderXMLTest.class);
    public DataProviderXML provider = new DataProviderXML();
    Serializer serializer = new Persister();
    DataProviderXMLTest() throws IOException {}
    Teacher teacher_1 = createTeacher(12L, "John", "Beach", 40,
            "asdasd@mail.ru", "Romania", "Machine Learning",
            10);
    Student student_1 = createStudent(12L, "John", "Beach", 40,
            "asdasd@mail.ru", "Romania", "Machine Learning");
    Student student_2 = createStudent(13L, "John", "Beach", 40,
            "asdasd@mail.ru", "Romania", "Machine Learning");
    Student student_3 = createStudent(14L, "John", "Beach", 40,
            "asdasd@mail.ru", "Romania", "Machine Learning");
    Student student_4 = createStudent(15L, "John", "Beach", 40,
            "asdasd@mail.ru", "Romania", "Machine Learning");
    List<String> videos = Arrays.asList("www.test.com", "www.test2.com");
    List<String> materials = Arrays.asList("www.test.com", "www.test2.com");
    List<String> videos_Upd= Arrays.asList("www.test_1.com", "www.test_2.com");
    List<String> materials_Upd = Arrays.asList("www.test_1.com", "www.test_2.com");
    List<Long> studentsIds = Arrays.asList(12L, 13L, 14L);
    Course course_1 = createCourse(1234, "Test", "Test course", 12L, studentsIds);
    Section section_1 = createSection(1234, "Test", "Test section", 1234, videos, materials);
    Section section_update = createSection(1234,"New Name", "New description",1234,videos_Upd,materials_Upd);

    @Test
    public void testInsertStudentSuccess() throws Exception {
        log.debug("On test TeacherInsertSuccess");
        provider.flushFile(Student.class.getSimpleName());
        assertEquals(Status.SUCCESSFUL,provider.dataInsert(Collections.singletonList(student_1)));
        assertEquals(student_1,provider.getStudentById(12L));
    }

    @Test
    public void testInsertStudentFail() throws Exception {
        log.debug("On test testInsertStudentFail");
        provider.flushFile(Student.class.getSimpleName());
        assertEquals(Status.FAIL,provider.dataInsert(Collections.emptyList()));
        assertEquals(Collections.emptyList(),provider.getRecords(Student.class));
    }


    @Test
    public void testInsertTeacherSuccess() throws Exception {
        log.debug("On test TeacherInsertSuccess");
        provider.flushFile(Teacher.class.getSimpleName());
        assertEquals(Status.SUCCESSFUL,provider.dataInsert(Collections.singletonList(teacher_1)));
        assertEquals(teacher_1,provider.getTeacherById(12L));
    }

    @Test
    public void testInsertTeacherFail() throws Exception {
        log.debug("On test testInsertTeacherFail");
        provider.flushFile(Student.class.getSimpleName());
        assertEquals(Status.FAIL,provider.dataInsert(Collections.emptyList()));
        assertEquals(Collections.emptyList(),provider.getRecords(Student.class));
        provider.getRecords(Review.class);
    }

    @Test
    public void createCourseSuccess() throws Exception {
        provider.flushFile(Constants.STUDENT);
        provider.flushFile(Constants.TEACHER);
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3));
        assertEquals(Status.SUCCESSFUL,provider.createCourse(1234, "Test", "Test course", 12L, studentsIds));
        assertEquals(course_1,provider.<Course>getCourseById(1234));
    }

    @Test
    public void createCourseFail() throws Exception {
        log.debug("On test createCourseFail");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.<Teacher>dataInsert(Collections.singletonList(teacher_1));
        provider.<Student>dataInsert(Arrays.asList(student_1, student_2, student_3));
        assertEquals(Status.FAIL,provider.createCourse(1235, "Test 2", "Test course 2", 12L, Collections.singletonList(10L)));
    }

    @Test
    public void createSectionSuccess() throws Exception {
        log.debug("On test createSectionSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        assertEquals(Status.SUCCESSFUL,provider.createSection(1234, "Test", "Test section", 1234, videos, materials));
        assertEquals(section_1,provider.getSectionById(1234));
    }

    @Test
    public void createSectionFail() throws Exception {
        log.debug("On test createSectionFail");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        assertEquals(Status.FAIL,provider.createSection(1234,"Test","Test section",-1,videos,materials));
        assertEquals(Collections.emptyList(),provider.getRecords(Section.class));
    }

    @Test
    public void deleteSectionSuccess() throws Exception {
        log.debug("On test deleteSectionSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.<Teacher>dataInsert(Collections.singletonList(teacher_1));
        provider.<Student>dataInsert(Arrays.asList(student_1, student_2, student_3));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        provider.createSection(1234,"Test","Test section",1234,videos,materials);
        assertEquals(Status.SUCCESSFUL,provider.deleteSection(1234));
        assertEquals(Collections.emptyList(),provider.getRecords(Section.class));
    }

    @Test
    public void deleteSectionFail() throws Exception {
        log.debug("On test deleteSectionSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.<Teacher>dataInsert(Collections.singletonList(teacher_1));
        provider.<Student>dataInsert(Arrays.asList(student_1, student_2, student_3));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        provider.createSection(1234,"Test","Test section",1234,videos,materials);
        assertEquals(Status.SUCCESSFUL,provider.deleteSection(1234));
        assertEquals(Collections.emptyList(),provider.getRecords(Section.class));
    }

    @Test
    public void updateSectionSuccess() throws Exception {
        log.debug("On test updateSectionSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.<Teacher>dataInsert(Collections.singletonList(teacher_1));
        provider.<Student>dataInsert(Arrays.asList(student_1, student_2, student_3));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        provider.createSection(1234,"Test","Test section",1234,videos,materials);
        assertEquals(Status.SUCCESSFUL,provider.<Section>updateSection(1234,"New Name", "New description",videos_Upd,materials_Upd));
        assertEquals(section_update,provider.<Section>getSectionById(1234));
    }

    @Test
    public void updateSectionFail() throws Exception {
        log.debug("On test updateSectionFail");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.<Teacher>dataInsert(Collections.singletonList(teacher_1));
        provider.<Student>dataInsert(Arrays.asList(student_1, student_2, student_3));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        provider.createSection(1234,"Test","Test section",1234,videos,materials);
        assertEquals(Status.FAIL,provider.<Section>updateSection(1235,"New Name", "New description",videos_Upd,materials_Upd));
        assertEquals(section_1,provider.<Section>getSectionById(1234));
    }

    @Test
    public void joinCourseSuccess() throws Exception {
        log.debug("On test joinCourseSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        assertEquals(Status.SUCCESSFUL,provider.joinCourse(1234,15L));
        assertEquals(15L,provider.getCourseById(1234).getStudents().get(3));
    }

    @Test
    public void joinCourseFail() throws Exception {
        log.debug("On test joinCourseFail");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        assertEquals(Status.FAIL,provider.joinCourse(1234,15L));
        assertEquals(studentsIds,provider.getCourseById(1234).getStudents());
    }

    @Test
    public void leaveAReviewAboutCourseSuccess() throws Exception {
        log.debug("On test leaveAReviewAboutCourseSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        assertEquals(Status.SUCCESSFUL,provider.leaveAReviewAboutCourse(1234,12L,10,"Test Comment"));
        assertEquals("Test Comment",provider.<Review>getRecords(Review.class).get(0).getComment());
        assertEquals(5,provider.<Review>getRecords(Review.class).get(0).getRating());
        assertEquals(provider.<Review>getRecords(Review.class).get(0).getId(),provider.<CourseActivity>getRecords(CourseActivity.class).get(0).getReview());
    }

    @Test
    public void leaveAReviewAboutCourseFail() throws Exception {
        log.debug("On test leaveAReviewAboutCourseFail");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        assertEquals(Status.FAIL,provider.leaveAReviewAboutCourse(1234,12L,10,"Test Comment"));
    }

    @Test
    public void deleteCourseSuccess() throws Exception {
        log.debug("On test deleteCourseSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        provider.createSection(1234, "Test", "Test section", 1234, videos, materials);
        provider.leaveAReviewAboutCourse(1234,12L,10,"Test Comment");
        assertEquals(Status.SUCCESSFUL,provider.deleteCourse(1234));
        assertEquals(Collections.emptyList(),provider.getRecords(Section.class));
        assertEquals(Collections.emptyList(),provider.getRecords(CourseActivity.class));
    }

    @Test
    public void deleteCourseFail() throws IOException {
        log.debug("On test deleteCourseFail");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        assertEquals(Status.FAIL,provider.deleteCourse(123445));
    }

    @Test
    public void unsubscribeFromACourseSuccess() throws IOException {
        log.debug("On test unsubscribeFromACourseSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        provider.joinCourse(1234,15L);
        provider.leaveAReviewAboutCourse(1234,15L,10,"Test Comment");
        assertEquals(Status.SUCCESSFUL,provider.unsubscribeFromACourse(1234,15L));
    }

    @Test
    public void unsubscribeFromACourseFail() throws IOException {
        log.debug("On test unsubscribeFromACourseFail");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        assertEquals(Status.FAIL,provider.unsubscribeFromACourse(1234,15L));
    }


    @Test
    public void checkCourseReviewsSuccess() throws Exception {
        log.debug("On test checkCourseReviewsSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        provider.leaveAReviewAboutCourse(1234,12L,10,"Test Comment");
        provider.leaveAReviewAboutCourse(1234,13L,2,"Test Comment 2");
        assertEquals(provider.<Review>getRecords(Review.class),provider.checkCourseReviews(1234));
    }

    @Test
    public void checkCourseReviewsFail() throws IOException {
        log.debug("On test checkCourseReviewsFail");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        assertNull(provider.checkCourseReviews(1234L));
    }

    @Test
    public void getCourseRatingSuccess() throws IOException {
        log.debug("On test getCourseRatingSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        provider.leaveAReviewAboutCourse(1234,12L,5,"Test Comment");
        provider.leaveAReviewAboutCourse(1234,13L,2,"Test Comment");
        provider.leaveAReviewAboutCourse(1234,14L,3,"Test Comment");
        Double avgRating  = provider.getCourseRating(1234);
        assertEquals(avgRating,3.3333333333333335);
    }

    @Test
    public void getCourseRatingFail() throws IOException {
        log.debug("On test getCourseRatingFail");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        Double avgRating  = provider.getCourseRating(1235);
        assertEquals(avgRating,-1.0);
    }

    @Test
    public void getCourseCommentsSuccess() throws IOException {
        log.debug("On test getCourseCommentsSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        provider.leaveAReviewAboutCourse(1234,12L,5,"Test Comment");
        provider.leaveAReviewAboutCourse(1234,13L,2,"Test Comment");
        provider.leaveAReviewAboutCourse(1234,14L,3,"Test Comment");
        List<String> comments  = provider.getCourseComments(1234);
        List <String> assertComments = Arrays.asList("Test Comment","Test Comment","Test Comment");
        assertEquals(comments,assertComments);
    }

    @Test
    public void getCourseCommentsFail() throws IOException {
        log.debug("On test getCourseCommentsFail");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        assertNull(provider.getCourseComments(1234));
    }

    @Test
    public void askAQuestionSuccess() throws IOException {
        log.debug("On test askAQuestionSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        assertEquals(Status.SUCCESSFUL,provider.askAQuestion(1234,12L,"Test Question_1"));
        assertEquals(Status.SUCCESSFUL,provider.askAQuestion(1234,13L,"Test Question_2"));
        assertEquals(Status.SUCCESSFUL,provider.askAQuestion(1234,14L,"Test Question_3"));
    }

    @Test
    public void askAQuestionFail() throws IOException {
        log.debug("On test askAQuestionFail");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        assertEquals(Status.FAIL,provider.askAQuestion(1234,12L,"Test Question_1"));
        assertEquals(Status.FAIL,provider.askAQuestion(1234,13L,"Test Question_2"));
        assertEquals(Status.FAIL,provider.askAQuestion(1234,14L,"Test Question_3"));
    }

    @Test
    public void checkQuestionsSuccess() throws Exception {
        log.debug("On test checkQuestionsSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        provider.askAQuestion(1234,12L,"Test Question_1");
        provider.askAQuestion(1234,13L,"Test Question_2");
        provider.askAQuestion(1234,14L,"Test Question_3");
        List <Question> questions = provider.checkQuestions(1234,-1,"");
        assertEquals(provider.<Question>getRecords(Question.class),questions);
    }

    @Test
    public void checkQuestionsFail() throws IOException {
        log.debug("On test checkQuestionsFail");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        List <Question> questions = provider.checkQuestions(1234,1,"");
        assertNull(questions);
    }

    @Test
    public void answerQuestionSuccess() throws Exception {
        log.debug("On test answerQuestionSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.flushFile(Answer.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        provider.askAQuestion(1234,12L,"Test Question_1");
        assertEquals(Status.SUCCESSFUL,provider.answerQuestion(provider.<Question>getRecords(Question.class).get(0).getId(),"Test Answer 1"));
    }

    @Test
    public void answerQuestionFail() throws IOException{
        log.debug("On test answerQuestionFail");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.flushFile(Answer.class.getSimpleName());
        provider.answerQuestion(1234,"Test answer");
        assertEquals(Status.FAIL,provider.answerQuestion(12L,"Answer Test 1"));
    }

    @Test
    public void getCourseMaterialsSectionSuccess() throws IOException {
        log.debug("On test getCourseMaterialsSectionSuccess");
        DataProviderCsv provider = new DataProviderCsv();
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        provider.createSection(1234, "Test", "Test section", 1234, videos, materials);
        provider.createSection(1235, "Test 2", "Test section 2", 1234, videos_Upd, materials_Upd);
        List<Section> courseSections = provider.getCourseMaterials(1234);
        assertEquals("Test",courseSections.get(0).getName());
        assertEquals("Test 2",courseSections.get(1).getName());
        assertEquals(1234,courseSections.get(0).getId());
        assertEquals(1235,courseSections.get(1).getId());

    }

    @Test
    public void getCourseMaterialsSectionFail() throws IOException {
        log.debug("On test getCourseMaterialsSectionFail");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        List<Section> courseSections = provider.getCourseMaterials(1234);
        assertNull(courseSections);
    }

    @Test
    public void checkQASuccess() throws Exception {
        log.debug("On test checkQASuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.flushFile(Answer.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        provider.askAQuestion(1234,12L,"Test Question_1");
        Question question = provider.<Question>getRecords(Question.class).get(0);
        provider.answerQuestion(question.getId(),"Test answer");
        List<String> allQA = provider.checkQA(1234,12,"New question_2",false);
        Answer answer = provider.<Answer>getRecords(Answer.class).get(0);
        assertEquals(allQA.get(0),question.toString());
        assertEquals(allQA.get(1),answer.toString());
    }

    @Test
    public void checkQAFail() throws IOException {
        log.debug("On test checkQAFail");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.flushFile(Answer.class.getSimpleName());
        assertNull(provider.checkQA(123,12,"",false));
    }

    @Test
    public void viewCourseSuccess() throws Exception {
        log.debug("On test viewCourseSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.flushFile(Answer.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3, student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        assertEquals(provider.getCourseById(1234).toString(),provider.viewCourse(1234,"").toString());
    }

    @Test
    public void viewCourseFail() throws IOException {
        log.debug("On test viewCourseSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.flushFile(Answer.class.getSimpleName());
        assertNull(provider.viewCourse(1234, ""));
    }

    @Test
    public void updateCourseSuccess() throws Exception {
        log.debug("On test updateCourseSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.flushFile(Answer.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        assertEquals(Status.SUCCESSFUL,provider.updateCourse(1234,"New Name","New Description",null,-1,"","",null,null,""));
    }

    @Test
    public void updateCourseFail() throws IOException {
        log.debug("On test updateCourseSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.flushFile(Answer.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        assertEquals(Status.FAIL,provider.updateCourse(1235,"New Name","New Description",null,-1,"","",null,null,""));
    }

    @Test
    public void getStudentsCoursesSuccess() throws Exception {
        log.debug("On test updateCourseSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.flushFile(Answer.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        provider.createCourse(1235, "Test_2 ", "Test course_2", 12L, Arrays.asList(13L,14L));
        assertEquals(provider.getRecords(Course.class).get(0),provider.getStudentsCourses(12L,1234,3,"","","",false).get(0));
    }

    @Test
    public void getStudentsCoursesFail() throws IOException {
        log.debug("On test updateCourseSuccess");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.flushFile(Answer.class.getSimpleName());
        provider.dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.dataInsert(Collections.singletonList(teacher_1));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        provider.createCourse(1235, "Test_2 ", "Test course_2", 12L, Arrays.asList(13L,14L));
        assertEquals(Collections.emptyList(),provider.getStudentsCourses(15L,1234,3,"","","",false));
    }


}