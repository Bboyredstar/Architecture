package ru.sfedu.my_pckg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.my_pckg.api.AbstractDataProvider;
import ru.sfedu.my_pckg.api.DataProviderCsv;
import ru.sfedu.my_pckg.api.DataProviderJDBC;
import ru.sfedu.my_pckg.api.DataProviderXML;
import ru.sfedu.my_pckg.enums.Status;
import ru.sfedu.my_pckg.utils.helpers.Helper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ProjectAppClient {

    private static Logger log = LogManager.getLogger(ProjectAppClient.class);

    protected static AbstractDataProvider resolveDataProvider(List<String> arguments) {
        if (arguments.size() == 0) return null;
        try{
            switch (arguments.get(0)) {
                case Constants.CSV_COMMAND:
                    try {
                        return new DataProviderCsv();
                    } catch (IOException e) {
                        log.error(e);
                        System.exit(1);
                    }
                case Constants.XML_COMMAND:
                    try{
                        return new DataProviderXML();
                    } catch (IOException e) {
                        log.error(e);
                        System.exit(1);
                    }
                case Constants.JDBC_COMMAND:
                    try{
                        return new DataProviderJDBC();
                    } catch (IOException e) {
                        log.error(e);
                        System.exit(1);
                    }
            }
        }
        catch (IllegalArgumentException e){
            log.error(e);
            log.error(Constants.BAD_ARGS_FORMAT);
            return null;
        }
        return null;
    }

    public static String resolveAPIResult(AbstractDataProvider provider,List<String> arguments){
        try {
            String action = arguments.get(1);

            if (Status.FAIL == provider.dataInitialization()){
                log.error(Constants.PROBLEM_INIT_DATA);
                System.exit(-1);
            }
            switch(action.trim().toUpperCase()){
                case Constants.CREATE_COURSE:{
                    long id = Helper.createId();
                    String name = arguments.get(2);
                    String description = arguments.get(3);
                    Long ownerId = Long.parseLong(arguments.get(4));
                    List<Long> students =Helper.stringToList(arguments.get(5));
                    return provider.createCourse(id,name,description,ownerId,students).toString();
                }
                case Constants.UPDATE_COURSE:{
                    long id = Long.parseLong(arguments.get(2));
                    String courseName = arguments.get(3);
                    String courseDescription = arguments.get(4);
                    long sectionId = Long.parseLong(arguments.get(5));
                    String sectionName = arguments.get(6);
                    String sectionDescription = arguments.get(7);
                    List<String> videos = Helper.stringToListString(arguments.get(8));
                    List<String> materials = Helper.stringToListString(arguments.get(9));
                    String extendMethod = arguments.get(10);
                    return provider.updateCourse(id,courseName,courseDescription,sectionId,sectionName,sectionDescription,materials,videos,extendMethod).toString();
                }
                case Constants.DELETE_COURSE:{
                    long id = Long.parseLong(arguments.get(2));
                    return provider.deleteCourse(id).toString();
                }
                case Constants.VIEW_COURSE:{
                    long id = Long.parseLong(arguments.get(2));
                    String extendMethod = arguments.get(3);
                    return provider.viewCourse(id,extendMethod);
                }
                case Constants.CHOOSE_COURSE: {
                    long course = Long.parseLong(arguments.get(2));
                    long student = Long.parseLong(arguments.get(3));
                    String extendMethod = arguments.get(4);
                    return provider.chooseCourse(course,student,extendMethod);
                }
                case Constants.GET_STUDENTS_COURSES: {
                    long studentId = Long.parseLong(arguments.get(2));
                    long courseId = Long.parseLong(arguments.get(3));
                    int rating = Integer.parseInt(arguments.get(4));
                    String comment = arguments.get(5);
                    String question = arguments.get(6);
                    String extendMethod = arguments.get(7);
                    boolean needQuestion = Boolean.parseBoolean(arguments.get(8));
                    return provider.getStudentsCourses(studentId,courseId,rating,comment,question,extendMethod,needQuestion);
                }
                case Constants.CHECK_STUDENTS_QUESTIONS: {
                    long courseId = Long.parseLong(arguments.get(2));
                    long questionId = Long.parseLong(arguments.get(3));
                    String answer =  arguments.get(4);
                    return provider.checkStudentsQuestions(courseId,questionId,answer);
                }
                default:
                    return Constants.ERROR_METHOD_SIGNATURE;
            }
        }
        catch(Exception e){
         log.error(e);
         log.error(Constants.BAD_ARGS_FORMAT);
         log.error(Constants.ERROR_METHOD_SIGNATURE);
         System.exit(1);
         return Constants.ERROR_METHOD_SIGNATURE;
        }
    }

    public static void main(String[] args) {

        List<String> arguments = Arrays.asList(args);
        log.debug(arguments.toString());

        AbstractDataProvider dataProvider = resolveDataProvider(arguments);
        if (dataProvider == null){
            log.error(Constants.BAD_ARGS_FORMAT);
            System.exit(1);
        }
        String result = resolveAPIResult(dataProvider,arguments);
        System.out.print(result);
    }
}
