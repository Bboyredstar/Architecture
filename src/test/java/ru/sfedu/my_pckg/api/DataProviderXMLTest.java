package ru.sfedu.my_pckg.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.my_pckg.BaseTest;
import ru.sfedu.my_pckg.beans.*;
import ru.sfedu.my_pckg.enums.Status;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ru.sfedu.my_pckg.utils.helpers.Helper.*;

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
    Course new_course = createCourse(1235, "Test", "Test course", 12L, studentsIds);
    Section section_1 = createSection(1234, "Test", "Test section", 1234, videos, materials);

    @BeforeEach
    public void initTestData() throws Exception {
        log.info("Initializing test data");
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.flushFile(Answer.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
        provider.<Teacher>dataInsert(Collections.singletonList(teacher_1));
        provider.<Student>dataInsert(Arrays.asList(student_1, student_2, student_3,student_4));
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
    }

    @Test
    public void createCourseSuccess() throws Exception{
        log.debug("On test createCourseSuccess");
        assertEquals(Status.SUCCESSFUL,provider.createCourse(1235, "Test", "Test course", 12L, studentsIds));
        assertEquals(new_course,provider.getCourseById(1235));
    }

    @Test
    public void createCourseFail() throws Exception {
        log.debug("On test createCourseFail");
        assertEquals(Status.FAIL,provider.createCourse(1235, "Test 2", "Test course 2", 12L, Collections.singletonList(10L)));
        assertEquals(Status.FAIL,provider.createCourse(1235, "", "Test course 2", 12L, studentsIds));
    }

    @Test
    public void createSectionSuccess() throws Exception {
        log.debug("On test createSectionSuccess");
        assertEquals(Status.SUCCESSFUL,provider.createSection(1234, "Test", "Test section", 1234, videos, materials));
        assertEquals(section_1,provider.getSectionById(1234));
    }

    @Test
    public void createSectionFail() throws  Exception {
        log.debug("On test createSectionFail");
        assertEquals(Status.FAIL,provider.createSection(1234,"Test","Test section",1235,videos,materials));
        assertEquals(Status.FAIL,provider.createSection(1234,"","Test section",1234,videos,materials));
        assertTrue(provider.getRecords(Section.class).isEmpty());
    }

    @Test
    public void deleteSectionSuccess() throws Exception {
        log.debug("On test deleteSectionSuccess");
        assertEquals(Status.SUCCESSFUL,provider.createSection(1234, "Test", "Test section", 1234, videos, materials));
        assertEquals(Status.SUCCESSFUL,provider.deleteSection(1234));
        assertEquals(Collections.emptyList(),provider.getRecords(Section.class));
    }

    @Test
    public void deleteSectionFail()  {
        log.debug("On test deleteSectionFail");
        assertEquals(Status.FAIL,provider.deleteSection(1234));
    }

    @Test
    public void updateSectionSuccess() throws Exception{
        provider.createSection(1234, "Test", "Test section", 1234, videos, materials);
        assertEquals(Status.SUCCESSFUL,provider.updateSection(1234,"New name","",videos_Upd,materials_Upd));
        assertEquals("New name",provider.getSectionById(1234).getName());
        assertEquals(videos_Upd,provider.getSectionById(1234).getVideos());
        assertEquals(materials_Upd,provider.getSectionById(1234).getMaterials());
    }

    @Test
    public void updateSectionFail(){
        log.debug("On test updateSectionFail");
        assertEquals(Status.FAIL,provider.updateSection(1234,"New name","",videos_Upd,materials_Upd));
    }

    @Test
    public void chooseCourseSuccess() throws Exception {
        log.debug("On test chooseCourseSuccess");
        provider.createCourse(1235,"New course","Description",12L,null);
        provider.createSection(1234, "Test", "Test section", 1234, videos, materials);
        provider.createSection(1235, "New Section", "New section", 1235, videos, materials);
        assertEquals(provider.chooseCourse(-1,1,""),provider.getRecords(Course.class).toString());
        assertEquals(Status.SUCCESSFUL.toString(),provider.chooseCourse(1235,15L,"JOIN"));
        assertEquals(15L,provider.getCourseById(1235).getStudents().get(0));
        assertEquals("[]",provider.chooseCourse(1234,1,"REVIEW"));
    }

    @Test
    public void chooseCourseFail() throws Exception {
        log.debug("On test chooseCourseFail");
        assertEquals(Status.FAIL.toString(),provider.chooseCourse(1235,15L,"JOIN"));
        assertNull(provider.chooseCourse(1234,12L,"Random String"));
        assertNull(provider.chooseCourse(1,1,"Review"));
    }


    @Test
    public void joinCourseSuccess() throws Exception {
        log.debug("On test joinCourseSuccess");
        assertEquals(Status.SUCCESSFUL,provider.joinCourse(1234,15L));
        assertTrue(provider.getCourseById(1234).getStudents().contains(15L));
    }

    @Test
    public void joinCourseFail() throws Exception {
        log.debug("On test joinCourseFail");
        assertEquals(Status.FAIL,provider.joinCourse(1234,16L));
        assertFalse(provider.getCourseById(1234).getStudents().contains(16L));
        assertEquals(Status.FAIL,provider.joinCourse(1235,15L));
    }


    @Test
    public void leaveAReviewAboutCourseSuccess() throws Exception {
        log.debug("On test leaveAReviewAboutCourseSuccess");
        assertEquals(Status.SUCCESSFUL,provider.leaveAReviewAboutCourse(1234,12L,10,"Test Comment"));
        assertEquals("Test Comment",provider.<Review>getRecords(Review.class).get(0).getComment());
        assertEquals(5,provider.<Review>getRecords(Review.class).get(0).getRating());
        assertEquals(provider.<Review>getRecords(Review.class).get(0).getId(),provider.<CourseActivity>getRecords(CourseActivity.class).get(0).getReview());
        assertEquals(provider.getRecords(Review.class).toString(),provider.chooseCourse(1234,1,"REVIEW"));
    }

    @Test
    public void leaveAReviewAboutCourseFail() throws Exception {
        log.debug("On test leaveAReviewAboutCourseFail");
        assertEquals(Status.FAIL,provider.leaveAReviewAboutCourse(1234,1L,10,"Test Comment"));
        assertEquals(Status.FAIL,provider.leaveAReviewAboutCourse(1233,12L,10,"Test Comment"));
        assertTrue(provider.getRecords(Review.class).isEmpty());
    }

    @Test
    public void deleteCourseSuccess() throws Exception {
        log.debug("On test deleteCourseSuccess");
        provider.createSection(1234, "Test", "Test section", 1234, videos, materials);
        provider.leaveAReviewAboutCourse(1234,12L,10,"Test Comment");
        assertEquals(Status.SUCCESSFUL,provider.deleteCourse(1234));
        assertTrue(provider.getRecords(Course.class).isEmpty());
        assertTrue(provider.getRecords(Section.class).isEmpty());
        assertTrue(provider.getRecords(CourseActivity.class).isEmpty());
    }

    @Test
    public void deleteCourseFail() throws Exception {
        log.debug("On test deleteCourseFail");
        assertEquals(Status.FAIL,provider.deleteCourse(123445));
        assertFalse(provider.getRecords(Course.class).isEmpty());
    }

    @Test
    public void unsubscribeFromACourseSuccess() throws Exception {
        log.debug("On test unsubscribeFromACourseSuccess");
        provider.joinCourse(1234,15L);
        provider.leaveAReviewAboutCourse(1234,15L,10,"Test Comment");
        assertEquals(Status.SUCCESSFUL,provider.unsubscribeFromACourse(1234,15L));
        assertTrue(provider.getRecords(CourseActivity.class).isEmpty());
        assertFalse(provider.getCourseById(1234L).getStudents().contains(15L));
    }

    @Test
    public void unsubscribeFromACourseFail() throws Exception {
        log.debug("On test unsubscribeFromACourseFail");
        assertEquals(Status.FAIL,provider.unsubscribeFromACourse(1234,15L));
    }


    @Test
    public void checkCourseReviewsSuccess() throws Exception {
        log.debug("On test checkCourseReviewsSuccess");
        provider.leaveAReviewAboutCourse(1234,12L,10,"Test Comment");
        provider.leaveAReviewAboutCourse(1234,13L,2,"Test Comment 2");
        assertEquals(provider.<Review>getRecords(Review.class),provider.checkCourseReviews(1234));
    }

    @Test
    public void checkCourseReviewsFail() {
        log.debug("On test checkCourseReviewsFail");
        assertNull(provider.checkCourseReviews(1235));
    }

    @Test
    public void getCourseRatingSuccess() throws Exception {
        log.debug("On test getCourseRatingSuccess");
        provider.leaveAReviewAboutCourse(1234,12L,5,"Test Comment");
        provider.leaveAReviewAboutCourse(1234,13L,2,"Test Comment");
        provider.leaveAReviewAboutCourse(1234,14L,3,"Test Comment");
        double avgRating  = provider.getCourseRating(1234);
        assertEquals(avgRating,3.3333333333333335,0.0000001);
    }

    @Test
    public void getCourseRatingFail() throws Exception {
        log.debug("On test getCourseRatingFail");
        double avgRating  = provider.getCourseRating(1235);
        assertEquals(avgRating,-1.0,0.0000001);
    }

    @Test
    public void getCourseCommentsSuccess() throws Exception {
        log.debug("On test getCourseCommentsSuccess");
        provider.leaveAReviewAboutCourse(1234,12L,5,"Test Comment 1");
        provider.leaveAReviewAboutCourse(1234,13L,2,"Test Comment 2");
        provider.leaveAReviewAboutCourse(1234,14L,3,"Test Comment 3");
        List<String> comments  = provider.getCourseComments(1234);
        List <String> assertComments = Arrays.asList("Test Comment 1","Test Comment 2","Test Comment 3");
        assertTrue(comments.containsAll(assertComments));
    }

    @Test
    public void getCourseCommentsFail() {
        log.debug("On test getCourseCommentsFail");
        assertNull(provider.getCourseComments(1235));
    }

    @Test
    public void askAQuestionSuccess() throws Exception {
        log.debug("On test askAQuestionSuccess");
        assertEquals(Status.SUCCESSFUL,provider.askAQuestion(1234,12L,"Test Question_1"));
        assertEquals(Status.SUCCESSFUL,provider.askAQuestion(1234,12L,"Test Question_2"));
        assertFalse(provider.<CourseActivity>getRecords(CourseActivity.class).get(0).getQuestions().isEmpty());
        assertFalse(provider.getRecords(Question.class).isEmpty());
        assertTrue(provider.<CourseActivity>getRecords(CourseActivity.class).get(0).getQuestions().contains(provider.<Question>getRecords(Question.class).get(0).getId()));
        assertTrue(provider.<CourseActivity>getRecords(CourseActivity.class).get(0).getQuestions().contains(provider.<Question>getRecords(Question.class).get(1).getId()));
    }


    @Test
    public void askAQuestionFail() throws Exception {
        log.debug("On test askAQuestionFail");
        assertEquals(Status.FAIL,provider.askAQuestion(1234,19L,"Test Question_1"));
        assertEquals(Status.FAIL,provider.askAQuestion(1234,13L,""));
        assertTrue(provider.getRecords(Question.class).isEmpty());
    }

    @Test
    public void checkQuestionsSuccess() throws Exception {
        log.debug("On test checkQuestionsSuccess");
        provider.askAQuestion(1234,12L,"Test Question_1");
        provider.askAQuestion(1234,13L,"Test Question_2");
        provider.askAQuestion(1234,14L,"Test Question_3");
        long questionId = provider.<Question>getRecords(Question.class).get(0).getId();
        String questions = provider.checkStudentsQuestions(1234,-1,"");
        String expected = provider.<Question>getRecords(Question.class).stream().map(Question::toString)
                .collect(Collectors.joining(" , "));
        assertEquals(expected,questions);
        assertEquals(Status.SUCCESSFUL.toString(),provider.checkStudentsQuestions(1234,questionId,"Test Answer"));
        assertFalse(provider.getRecords(Answer.class).isEmpty());
    }

    @Test
    public void checkQuestionsFail() throws Exception {
        log.debug("On test checkQuestionsFail");
        String questions = provider.checkStudentsQuestions(1234,1,"");
        assertEquals("",questions);
        assertEquals(Status.FAIL.toString(),provider.checkStudentsQuestions(1234,1,"Test answer"));
    }

    @Test
    public void answerQuestionSuccess() throws Exception {
        log.debug("On test answerQuestionSuccess");
        provider.askAQuestion(1234,12L,"Test Question_1");
        assertEquals(Status.SUCCESSFUL,provider.answerQuestion(provider.<Question>getRecords(Question.class).get(0).getId(),"Test Answer 1"));
        assertFalse(provider.getRecords(Answer.class).isEmpty());
    }

    @Test
    public void answerQuestionFail() throws Exception{
        log.debug("On test answerQuestionFail");
        provider.answerQuestion(1234,"Test answer");
        assertEquals(Status.FAIL,provider.answerQuestion(12L,"Answer Test 1"));
        assertTrue(provider.getRecords(Answer.class).isEmpty());
    }

    @Test
    public void getCourseMaterialsSectionSuccess() throws Exception {
        log.debug("On test getCourseMaterialsSectionSuccess");
        provider.createSection(1234, "Test", "Test section", 1234, videos, materials);
        provider.createSection(1235, "Test 2", "Test section 2", 1234, videos_Upd, materials_Upd);
        List<Section> courseSections = provider.getCourseMaterials(1234);
        assertTrue(courseSections.contains(provider.getSectionById(1234)));
        assertTrue(courseSections.contains(provider.getSectionById(1235)));
        assertEquals(2, courseSections.size());
    }

    @Test
    public void getCourseMaterialsSectionFail() {
        log.debug("On test getCourseMaterialsSectionFail");
        List<Section> courseSections = provider.getCourseMaterials(1235);
        assertNull(courseSections);
    }

    @Test
    public void checkQASuccess() throws Exception {
        log.debug("On test checkQASuccess");
        provider.askAQuestion(1234,12L,"Test Question_1");
        String allQA = provider.checkQA(1234,-1,"",false);
        assertEquals(provider.getRecords(Question.class).get(0).toString(),allQA);
        provider.answerQuestion(provider.<Question>getRecords(Question.class).get(0).getId(),"Test Answer");
        assertEquals(provider.getRecords(Question.class).get(0).toString()+provider.getRecords(Answer.class).get(0).toString(),provider.checkQA(1234,-1,"",false));
        assertEquals(Status.SUCCESSFUL.toString(),provider.checkQA(1234,12L,"New question student 12L",true));
    }

    @Test
    public void checkQAFail() throws Exception {
        log.debug("On test checkQAFail");
        assertNull(provider.checkQA(123,12,"",false));
        assertEquals(Status.FAIL.toString(),provider.checkQA(1234,15L,"Test",true));
    }

    @Test
    public void viewCourseSuccess() throws Exception {
        log.debug("On test viewCourseSuccess");
        assertEquals(provider.getCourseById(1234).toString(),provider.viewCourse(1234,""));
    }

    @Test
    public void viewCourseFail() {
        log.debug("On test viewCourseSuccess");
        assertNull(provider.viewCourse(1236, ""));
        assertNull(provider.viewCourse(1234, "Random string"));
    }

    @Test
    public void updateCourseSuccess() throws Exception {
        log.debug("On test updateCourseSuccess");
        assertEquals(Status.SUCCESSFUL,provider.updateCourse(1234,"New Name","New Description",-1,"","",null,null,""));
        assertEquals("New Name",provider.getCourseById(1234).getName());
        assertEquals("New Description",provider.getCourseById(1234).getDescription());
        assertEquals(Status.SUCCESSFUL,provider.updateCourse(1234,"","",1,"New Section","New Description",materials,videos,"create"));
        assertFalse(provider.getRecords(Section.class).isEmpty());
        long section = provider.<Section>getRecords(Section.class).get(0).getId();
        assertEquals(Status.SUCCESSFUL,provider.updateCourse(1234,"","",section,"New Section UPDATED","New Description UPDATED",materials,videos,"UPDATE"));
        assertEquals("New Section UPDATED",provider.getSectionById(section).getName());
        assertEquals("New Description UPDATED",provider.getSectionById(section).getDescription());
        assertEquals(Status.SUCCESSFUL,provider.updateCourse(1234,"","",section,"","",null,null,"delete"));
        assertTrue(provider.getRecords(Section.class).isEmpty());
    }

    @Test
    public void updateCourseFail() throws Exception {
        log.debug("On test updateCourseSuccess");
        assertEquals(Status.FAIL,provider.updateCourse(1235,"New Name","New Description",-1,"","",null,null,""));
        assertEquals(course_1,provider.getCourseById(1234));
        assertEquals(Status.FAIL,provider.updateCourse(1234,"","",12L,"","",null,null,"delete"));
        assertEquals(Status.FAIL,provider.updateCourse(1234,"","",12L,"","",null,null,"Update"));
        assertEquals(Status.FAIL,provider.updateCourse(1234,"","",12L,"","",null,null,"create"));
    }

    @Test
    public void getStudentsCoursesSuccess() throws Exception {
        log.debug("On test updateCourseSuccess");
        provider.createCourse(1235, "Test_2 ", "Test course_2", 12L, Arrays.asList(13L,14L));
        assertEquals(course_1.toString(),provider.getStudentsCourses(12L,1234,3,"","","",false));
        assertEquals(Status.SUCCESSFUL.toString(),provider.getStudentsCourses(12L,1234,5,"Nice course","","review",false));
        assertFalse(provider.getRecords(Review.class).isEmpty());
        assertEquals(provider.<CourseActivity>getRecords(CourseActivity.class).get(0).getReview(),provider.<Review>getRecords(Review.class).get(0).getId());
        assertEquals(12L,provider.<CourseActivity>getRecords(CourseActivity.class).get(0).getStudent());
        assertEquals("",provider.getStudentsCourses(12L,1234,0,"","","QA",false));
        assertEquals(Status.SUCCESSFUL.toString(),provider.getStudentsCourses(12L,1234,0,"","Test Question","QA",true));
        assertFalse(provider.<Question>getRecords(Question.class).isEmpty());
        assertEquals(provider.<Question>getRecords(Question.class).get(0).getId(),provider.<CourseActivity>getRecords(CourseActivity.class).get(0).getQuestions().get(0));
        assertEquals(Status.SUCCESSFUL.toString(),provider.getStudentsCourses(12L,1234,0,"","","UNSUBSCRIBE",false));
        assertFalse(provider.getCourseById(1234).getStudents().contains(12L));
        assertTrue(provider.getRecords(CourseActivity.class).isEmpty());
    }

    @Test
    public void getStudentsCoursesFail() throws Exception {
        log.debug("On test updateCourseSuccess");
        provider.createCourse(1234, "Test", "Test course", 12L, studentsIds);
        provider.createCourse(1235, "Test_2 ", "Test course_2", 12L, Arrays.asList(13L, 14L));
        assertNull(provider.getStudentsCourses(16L, 1234, 3, "", "", "", false));
        assertEquals("", provider.getStudentsCourses(15L, 1234, 3, "", "", "", false));
        assertEquals(Status.FAIL.toString(), provider.getStudentsCourses(12L, 1234, 0, "", "", "QA", true));
        assertTrue(provider.getRecords(Question.class).isEmpty());
        assertEquals(Status.FAIL.toString(),provider.getStudentsCourses(12L, 1235, 3, "Error comment", "", "Review", false));
        assertTrue(provider.getRecords(Review.class).isEmpty());
        assertEquals(-1L,provider.<CourseActivity>getRecords(CourseActivity.class).get(0).getReview());
        assertNull(provider.<CourseActivity>getRecords(CourseActivity.class).get(0).getQuestions());
    }

    @AfterEach
    public void flushData() throws IOException {
        provider.flushFile(Student.class.getSimpleName());
        provider.flushFile(Teacher.class.getSimpleName());
        provider.flushFile(Course.class.getSimpleName());
        provider.flushFile(Section.class.getSimpleName());
        provider.flushFile(CourseActivity.class.getSimpleName());
        provider.flushFile(Question.class.getSimpleName());
        provider.flushFile(Answer.class.getSimpleName());
        provider.flushFile(Review.class.getSimpleName());
    }

}

