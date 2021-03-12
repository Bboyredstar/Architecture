package ru.sfedu.my_pckg.utils.helpers;

import ru.sfedu.my_pckg.beans.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Helper.
 */
public class Helper {
    /**
     * Create id long.
     *
     * @return the long
     */
    public static long createId() {
        return Math.round(System.currentTimeMillis() / (Math.random() * 100000));
    }

    /**
     * List to string string.
     *
     * @param list the list
     * @return the string
     */
    public static String ListToString(List<Long> list) {
        if (list == null) {
            return "null";
        }
        return list.stream().map(Objects::toString).collect(Collectors.joining(" ,"));
    }

    public static <T> String ListObjectToString(List<T> list) {
        if (list == null) {
            return "null";
        }
        return list.stream().map(T::toString).collect(Collectors.joining(" ,"));
    }

    public static String ListStringToString(List<String> list) {
        return list.stream().map(Objects::toString).collect(Collectors.joining(" ,"));
    }

    public static List<Long> stringToList(String ids){
        try {
            return Stream.of(ids.split(","))
                    .map(String::trim)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }
    public static Set<String> stringToSet(String string){
        try {
            return Stream.of(string.split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet());
        }
        catch (Exception e){
            return null;
        }
    }

    public static Map<String,String> stringToMap(String string){
        try {
            return Stream.of(string.split(","))
                    .map(s -> s.split(":"))
                    .collect(Collectors.toMap(e -> e[0], e ->(e[1])));
        }
        catch (Exception e){
            return null;
        }
    }

    public static List<String> stringToListString(String urls){
        try {
            if (urls.trim().toLowerCase().equals("null")) {
                return new ArrayList<>();
            }
            return Stream.of(urls.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }


    /**
     * Generate first name string.
     *
     * @return the string
     */
    public static String generateUserFName() {
        List<String> fnames = Arrays.asList("John", "Peter", "Jacob", "Stan", "Andrew","Scott","Tyler","Jim");
        int fnamePos = (int) (Math.random() * fnames.size());
        return fnames.get(fnamePos);
    }

    /**
     * Generate user last name string.
     *
     * @return the string
     */
    public static String generateUserLName() {
        List<String> lnames = Arrays.asList("Beach", "Morgan", "Tump", "Trant", "Doom","Richards","Jaxon","Nixon","Granter");
        int lnamePos = (int) (Math.random() * lnames.size());
        return lnames.get(lnamePos);
    }

    /**
     * Random number int.
     *
     * @param rightInterval the right interval
     * @return the int
     */
    public static int randomNumber(int leftInterval,int rightInterval) {
        return (int) (Math.random() * (rightInterval-leftInterval)+leftInterval);
    }

    /**
     * Generate random string.
     *
     * @param length the length of string
     * @return the string
     */
    public static String generateRandomString(int length) {
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";

        // combine all strings
        String alphaNumeric = upperAlphabet + lowerAlphabet;

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();


        for (int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphaNumeric.length());

            // get character specified by index
            // from the string
            char randomChar = alphaNumeric.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }
        return sb.toString();
    }

    /**
     * Random ur ls array list.
     *
     * @param count the count
     * @return the array list
     */
    public static ArrayList<String> randomURLs(int count) {
        List<String> protocol = Arrays.asList("http://", "https://");
        List<String> body = Arrays.asList("www.udemy", "www.youtube", "www.edyoda", "www.youtube");
        List<String> domain = Arrays.asList(".ru", ".com", ".io", ".su");
        ArrayList<String> urls = new ArrayList<String>();
        randomNumber(0,protocol.size());
        for (int i = 0; i < count; i++) {
            urls.add(protocol.get(randomNumber(0,protocol.size())) +
                    body.get(randomNumber(0,body.size())) +
                    domain.get(randomNumber(0,domain.size())) +
                    "?video=" + generateRandomString(10));
        }
        return urls;
    }

    public static String randomEmail() {
        List<String> mailService = Arrays.asList("@gmail", "@mail", "@icloud", "@yandex");
        List<String> domain = Arrays.asList(".ru", ".com", ".io", ".su");
        String email = generateRandomString(5)+mailService.get(randomNumber(0,mailService.size()))+domain.get(randomNumber(0,domain.size()));
        return email;
    }

    public static String randomCountry() {
        List<String> country = Arrays.asList("Russia", "USA", "Poland", "Germany");
        return country.get(randomNumber(0,country.size()));
    }

    public static String randomPreferences() {
        List<String> pref = Arrays.asList("Machine Learning", "Open CV", "Frontend", "Backend", "Fullstack");
        return pref.get(randomNumber(0,pref.size()));
    }


    public static Student createStudent(long id, String fname, String lname, int age,
                                        String email, String country, String preferences){
        Student stud = new Student();
        stud.setId(id);
        stud.setFirstName(fname);
        stud.setSecondName(lname);
        stud.setAge(age);
        stud.setEmail(email);
        stud.setCountry(country);
        stud.setPreferences(preferences);
        return stud;
    }

    public static Teacher createTeacher(long id,String fname,String lname, int age,
                                        String email,String country,String competence,
                                        int experience){
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setFirstName(fname);
        teacher.setSecondName(lname);
        teacher.setAge(age);
        teacher.setEmail(email);
        teacher.setCountry(country);
        teacher.setCompetence(competence);
        teacher.setExperience(experience);
        return teacher;
    }

    public static Course createCourse(long id, String name, String description, long ownerId,List<Long> students){
        Course course = new Course();
        course.setId(id);
        course.setName(name);
        course.setDescription(description);
        course.setOwner(ownerId);
        course.setStudents(students);
        return course;
    }

    public static Section createSection(long id, String name, String description, long course,
                                        List<String> materials, List<String> videos){
        Section section = new Section();
        section.setId(id);
        section.setCourse(course);
        section.setName(name);
        section.setDescription(description);
        section.setMaterials(materials);
        section.setVideos(videos);
        return section;
    }

    public static Question createQuestion(long id, String question){
        Question questionObj = new Question();
        questionObj.setId(id);
        questionObj.setQuestion(question);
        return questionObj;
    }

    public static Answer createAnswer(long id, long question, String answer ){
        Answer answerObj = new Answer();
        answerObj.setId(id);
        answerObj.setQuestion(question);
        answerObj.setAnswer(answer);
        return answerObj;
    }

    public static Review createReview(long id,int rating, String comment){
        Review review = new Review();
        review.setId(id);
        review.setRating(rating);
        review.setComment(comment);
        return review;
    }

    public static CourseActivity createCourseActivity(long id, long course, long student, List<Long> questions,long review){
        CourseActivity courseActivity = new CourseActivity();
        courseActivity.setId(id);
        courseActivity.setCourse(course);
        courseActivity.setStudent(student);
        courseActivity.setQuestions(questions);
        courseActivity.setReview(review);
        return courseActivity;
    }


}
