package ru.sfedu.my_pckg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.my_pckg.lab1.HibernateMetadataProvider;
import ru.sfedu.my_pckg.lab1.IMetaDataProvider;
import ru.sfedu.my_pckg.lab2.api.HibernateEntityProvider;
import ru.sfedu.my_pckg.lab2.api.ITestEntityDataProvider;
import ru.sfedu.my_pckg.lab2.model.EmbeddedEntity;

import ru.sfedu.my_pckg.lab5.api.HibernateProvider;
import ru.sfedu.my_pckg.lab5.api.IHibernateProvider;
import ru.sfedu.my_pckg.utils.helpers.Helper;

import java.util.*;

public class ProjectAppClient {

    private static Logger log = LogManager.getLogger(ProjectAppClient.class);

//    protected static AbstractDataProvider resolveDataProvider(List<String> arguments) {
//        if (arguments.size() == 0) return null;
//        try{
//            switch (arguments.get(0)) {
//                case Constants.CSV_COMMAND:
//                    try {
//                        return new DataProviderCsv();
//                    } catch (IOException e) {
//                        log.error(e);
//                        System.exit(1);
//                    }
//                case Constants.XML_COMMAND:
//                    try{
//                        return new DataProviderXML();
//                    } catch (IOException e) {
//                        log.error(e);
//                        System.exit(1);
//                    }
//                case Constants.JDBC_COMMAND:
//                    try{
//                        return new DataProviderJDBC();
//                    } catch (IOException e) {
//                        log.error(e);
//                        System.exit(1);
//                    }
//            }
//        }
//        catch (IllegalArgumentException e){
//            log.error(e);
//            log.error(Constants.BAD_ARGS_FORMAT);
//            return null;
//        }
//        return null;
//    }

//    public static String resolveAPIResult(AbstractDataProvider provider,List<String> arguments){
//        try {
//            String action = arguments.get(1);
//
//            if (Status.FAIL == provider.dataInitialization()){
//                log.error(Constants.PROBLEM_INIT_DATA);
//                System.exit(-1);
//            }
//            switch(action.trim().toUpperCase()){
//                case Constants.CREATE_COURSE:{
//                    long id = Helper.createId();
//                    String name = arguments.get(2);
//                    String description = arguments.get(3);
//                    Long ownerId = Long.parseLong(arguments.get(4));
//                    List<Long> students =Helper.stringToList(arguments.get(5));
//                    return provider.createCourse(id,name,description,ownerId,students).toString();
//                }
//                case Constants.UPDATE_COURSE:{
//                    long id = Long.parseLong(arguments.get(2));
//                    String courseName = arguments.get(3);
//                    String courseDescription = arguments.get(4);
//                    long sectionId = Long.parseLong(arguments.get(5));
//                    String sectionName = arguments.get(6);
//                    String sectionDescription = arguments.get(7);
//                    List<String> videos = Helper.stringToListString(arguments.get(8));
//                    List<String> materials = Helper.stringToListString(arguments.get(9));
//                    String extendMethod = arguments.get(10);
//                    return provider.updateCourse(id,courseName,courseDescription,sectionId,sectionName,sectionDescription,materials,videos,extendMethod).toString();
//                }
//                case Constants.DELETE_COURSE:{
//                    long id = Long.parseLong(arguments.get(2));
//                    return provider.deleteCourse(id).toString();
//                }
//                case Constants.VIEW_COURSE:{
//                    long id = Long.parseLong(arguments.get(2));
//                    String extendMethod = arguments.get(3);
//                    return provider.viewCourse(id,extendMethod);
//                }
//                case Constants.CHOOSE_COURSE: {
//                    long course = Long.parseLong(arguments.get(2));
//                    long student = Long.parseLong(arguments.get(3));
//                    String extendMethod = arguments.get(4);
//                    return provider.chooseCourse(course,student,extendMethod);
//                }
//                case Constants.GET_STUDENTS_COURSES: {
//                    long studentId = Long.parseLong(arguments.get(2));
//                    long courseId = Long.parseLong(arguments.get(3));
//                    int rating = Integer.parseInt(arguments.get(4));
//                    String comment = arguments.get(5);
//                    String question = arguments.get(6);
//                    String extendMethod = arguments.get(7);
//                    boolean needQuestion = Boolean.parseBoolean(arguments.get(8));
//                    return provider.getStudentsCourses(studentId,courseId,rating,comment,question,extendMethod,needQuestion);
//                }
//                case Constants.CHECK_STUDENTS_QUESTIONS: {
//                    long courseId = Long.parseLong(arguments.get(2));
//                    long questionId = Long.parseLong(arguments.get(3));
//                    String answer =  arguments.get(4);
//                    return provider.checkStudentsQuestions(courseId,questionId,answer);
//                }
//                default:
//                    return Constants.ERROR_METHOD_SIGNATURE;
//            }
//        }
//        catch(Exception e){
//         log.error(e);
//         log.error(Constants.BAD_ARGS_FORMAT);
//         log.error(Constants.ERROR_METHOD_SIGNATURE);
//         System.exit(1);
//         return Constants.ERROR_METHOD_SIGNATURE;
//        }
//    }

    public static void lab1Result(List<String> args){
        String action = args.get(1);
        IMetaDataProvider provider = new HibernateMetadataProvider();
        switch (action.trim().toLowerCase()){
            case("getcurrentuser"):
                System.out.print(provider.getCurrentUser());
                break;
            case("gettablessize"):
                System.out.print(provider.getTablesSize().toString());
                break;
            case("gettablesname"):
                System.out.print(provider.getTablesName().toString());
                break;
            case("getcatalogname"):
                System.out.print(provider.getCatalogName());
                break;
            case("getallschemas"):
                System.out.print(provider.getAllSchemas());
                break;
            default:
                log.error("Bad args");
                System.exit(1);
        }

    }

    public static void lab2Result(List<String> args){
        String action = args.get(1);
        ITestEntityDataProvider provider = new HibernateEntityProvider();
        switch (action.trim().toLowerCase()){
            case("createbean"):
                EmbeddedEntity embeddedEntity = new EmbeddedEntity();
                embeddedEntity.setName(args.get(5));
                embeddedEntity.setDescription(args.get(6));
                embeddedEntity.setSessions(Helper.stringToListString(args.get(7)));
                provider.createBean(args.get(2), args.get(3),new Date(),Boolean.valueOf(args.get(4)), embeddedEntity);
                break;
            case("updatebean"):
                EmbeddedEntity embedded = new EmbeddedEntity();
                embedded.setName(args.get(5));
                embedded.setDescription(args.get(6));
                embedded.setSessions(Helper.stringToListString(args.get(7)));
                provider.updateBean(Long.valueOf(args.get(2)), args.get(3),args.get(4),new Date(),Boolean.valueOf(args.get(4)), embedded);
                break;
            case("delete"):
                provider.delete(Long.valueOf(args.get(2)));
                break;
            default:
                log.error("Bad args");
                System.exit(1);
        }
    }



    public static void lab3Result(List<String> args){
        switch (args.get(1).trim().toLowerCase()){
            case("jt"):
                ru.sfedu.my_pckg.lab3.JoinedTable.api.ITestEntityDataProvider provider = new ru.sfedu.my_pckg.lab3.JoinedTable.api.HibernateEntityProvider();
                switch (args.get(2).trim().toLowerCase()){
                    case ("createstudent"):
                        provider.createStudent(args.get(3),args.get(4),Integer.parseInt(args.get(5)),args.get(6),args.get(7),args.get(8));
                        break;
                    case ("createacher"):
                        provider.createTeacher(args.get(3),args.get(4),Integer.parseInt(args.get(5)),args.get(6),args.get(7),args.get(8),Integer.parseInt(args.get(9)));
                        break;
                    case ("updatestudent"):
                        provider.updateStudent(Long.valueOf(args.get(3)),args.get(4),args.get(5),Integer.parseInt(args.get(6)),args.get(7),args.get(8),args.get(9));
                        break;
                    case ("updateteacher"):
                        provider.updateTeacher(Long.valueOf(args.get(3)),args.get(4),args.get(5),Integer.parseInt(args.get(6)),args.get(7),args.get(8),args.get(9),Integer.parseInt(args.get(10)));
                        break;
                    case ("deleteteacher"):
                        provider.deleteTeacher(Long.valueOf(args.get(3)));
                        break;
                    case ("deletestudent"):
                        provider.deleteStudent(Long.valueOf(args.get(3)));
                        break;
                    default:
                        log.error("Args error!");
                        System.exit(-1);
                        break;
                }
                break;
            case("st"):
                ru.sfedu.my_pckg.lab3.SingleTable.api.ITestEntityDataProvider provider1 = new ru.sfedu.my_pckg.lab3.SingleTable.api.HibernateEntityProvider();
                switch (args.get(2).trim().toLowerCase()){
                    case ("createstudent"):
                        provider1.createStudent(args.get(3),args.get(4),Integer.parseInt(args.get(5)),args.get(6),args.get(7),args.get(8));
                        break;
                    case ("createacher"):
                        provider1.createTeacher(args.get(3),args.get(4),Integer.parseInt(args.get(5)),args.get(6),args.get(7),args.get(8),Integer.parseInt(args.get(9)));
                        break;
                    case ("updatestudent"):
                        provider1.updateStudent(Long.valueOf(args.get(3)),args.get(4),args.get(5),Integer.parseInt(args.get(6)),args.get(7),args.get(8),args.get(9));
                        break;
                    case ("updateteacher"):
                        provider1.updateTeacher(Long.valueOf(args.get(3)),args.get(4),args.get(5),Integer.parseInt(args.get(6)),args.get(7),args.get(8),args.get(9),Integer.parseInt(args.get(10)));
                        break;
                    case ("deleteteacher"):
                        provider1.deleteTeacher(Long.valueOf(args.get(3)));
                        break;
                    case ("deletestudent"):
                        provider1.deleteStudent(Long.valueOf(args.get(3)));
                        break;
                    default:
                        log.error("Args error!");
                        System.exit(-1);
                        break;
                }
                break;
            case("ms"):
                ru.sfedu.my_pckg.lab3.MappedSuperclass.api.ITestEntityDataProvider provider2 = new ru.sfedu.my_pckg.lab3.MappedSuperclass.api.HibernateEntityProvider();
                switch (args.get(2).trim().toLowerCase()){
                    case ("createstudent"):
                        provider2.createStudent(args.get(3),args.get(4),Integer.parseInt(args.get(5)),args.get(6),args.get(7),args.get(8));
                        break;
                    case ("createacher"):
                        provider2.createTeacher(args.get(3),args.get(4),Integer.parseInt(args.get(5)),args.get(6),args.get(7),args.get(8),Integer.parseInt(args.get(9)));
                        break;
                    case ("updatestudent"):
                        provider2.updateStudent(Long.valueOf(args.get(3)),args.get(4),args.get(5),Integer.parseInt(args.get(6)),args.get(7),args.get(8),args.get(9));
                        break;
                    case ("updateteacher"):
                        provider2.updateTeacher(Long.valueOf(args.get(3)),args.get(4),args.get(5),Integer.parseInt(args.get(6)),args.get(7),args.get(8),args.get(9),Integer.parseInt(args.get(10)));
                        break;
                    case ("deleteteacher"):
                        provider2.deleteTeacher(Long.valueOf(args.get(3)));
                        break;
                    case ("deletestudent"):
                        provider2.deleteStudent(Long.valueOf(args.get(3)));
                        break;
                    default:
                        log.error("Args error!");
                        System.exit(-1);
                        break;
                }
                break;
            case("tpc"):
                ru.sfedu.my_pckg.lab3.TablePerClass.api.ITestEntityDataProvider provider3 = new ru.sfedu.my_pckg.lab3.TablePerClass.api.HibernateEntityProvider();
                switch (args.get(2).trim().toLowerCase()){
                    case ("createstudent"):
                        provider3.createStudent(args.get(3),args.get(4),Integer.parseInt(args.get(5)),args.get(6),args.get(7),args.get(8));
                        break;
                    case ("createacher"):
                        provider3.createTeacher(args.get(3),args.get(4),Integer.parseInt(args.get(5)),args.get(6),args.get(7),args.get(8),Integer.parseInt(args.get(9)));
                        break;
                    case ("updatestudent"):
                        provider3.updateStudent(Long.valueOf(args.get(3)),args.get(4),args.get(5),Integer.parseInt(args.get(6)),args.get(7),args.get(8),args.get(9));
                        break;
                    case ("updateteacher"):
                        provider3.updateTeacher(Long.valueOf(args.get(3)),args.get(4),args.get(5),Integer.parseInt(args.get(6)),args.get(7),args.get(8),args.get(9),Integer.parseInt(args.get(10)));
                        break;
                    case ("deleteteacher"):
                        provider3.deleteTeacher(Long.valueOf(args.get(3)));
                        break;
                    case ("deletestudent"):
                        provider3.deleteStudent(Long.valueOf(args.get(3)));
                        break;
                    default:
                        log.error("Args error!");
                        System.exit(-1);
                        break;
                }
                break;
            default:
                log.error("Args error!");
                System.exit(-1);
        }
    }

    public static void lab4Result(List<String> args){
        switch (args.get(1).trim().toLowerCase()){
            case("sc"):
                ru.sfedu.my_pckg.lab4.setCollection.api.ITestEntityDataProvider provider = new ru.sfedu.my_pckg.lab4.setCollection.api.HibernateEntityProvider();
                switch (args.get(2).trim().toLowerCase()){
                    case ("createsection"):
                        provider.createSection(args.get(3),args.get(4),Helper.stringToSet(args.get(5)),Helper.stringToSet(args.get(6)));
                        break;
                    case ("updatesection"):
                        provider.updateSection(Long.parseLong(args.get(3)),args.get(4),args.get(5),Helper.stringToSet(args.get(6)),Helper.stringToSet(args.get(7)));
                        break;
                    case ("deletesection"):
                        provider.deleteSection(Long.valueOf(args.get(3)));
                        break;
                    default:
                        log.error("Args error!");
                        System.exit(-1);
                        break;
                }
                break;
            case("mc"):
                ru.sfedu.my_pckg.lab4.mapCollection.api.ITestEntityDataProvider provider1 = new ru.sfedu.my_pckg.lab4.mapCollection.api.HibernateEntityProvider();
                switch (args.get(2).trim().toLowerCase()){
                    case ("createsection"):
                        provider1.createSection(args.get(3),args.get(4),Helper.stringToMap(args.get(5)),Helper.stringToMap(args.get(6)));
                        break;
                    case ("updatesection"):
                        provider1.updateSection(Long.parseLong(args.get(3)),args.get(4),args.get(5),Helper.stringToMap(args.get(6)),Helper.stringToMap(args.get(7)));
                        break;
                    case ("deletesection"):
                        provider1.deleteSection(Long.valueOf(args.get(3)));
                        break;
                    default:
                        log.error("Args error!");
                        System.exit(-1);
                        break;
                }
                break;
            case("lc"):
               ru.sfedu.my_pckg.lab4.listCollection.api.ITestEntityDataProvider provider2 = new ru.sfedu.my_pckg.lab4.listCollection.api.HibernateEntityProvider();
                switch (args.get(2).trim().toLowerCase()){
                    case ("createsection"):
                        provider2.createSection(args.get(3),args.get(4),Helper.stringToListString(args.get(5)),Helper.stringToListString(args.get(6)));
                        break;
                    case ("updatesection"):
                        provider2.updateSection(Long.parseLong(args.get(3)),args.get(4),args.get(5),Helper.stringToListString(args.get(6)),Helper.stringToListString(args.get(7)));
                        break;
                    case ("deletesection"):
                        provider2.deleteSection(Long.valueOf(args.get(3)));
                        break;
                    default:
                        log.error("Args error!");
                        System.exit(-1);
                        break;
                }
                break;
            default:
                log.error("Args error!");
                System.exit(-1);
        }
    }

    public static void lab5Result(List<String> args){
        IHibernateProvider provider = new HibernateProvider();
        log.debug(args);
        switch (args.get(1).trim().toLowerCase()){
            case("create"):
                log.info("::::::::::: " +args.get(2));
                log.debug(":::::::::: "+args.get(3));
                provider.createEntity(args.get(2),Helper.stringToListString(args.get(3)));
                break;
            case ("update"):
               provider.updateEntity(args.get(2),Helper.stringToListString(args.get(3)));
                break;
            case ("delete"):
                provider.deleteEntity(Long.valueOf(args.get(2)), args.get(3));
                break;
            case ("summary"):
                provider.gettingSummaryInformationCriteria();
                break;
        }
    }


    public static void main(String[] args) {

        List<String> arguments = Arrays.asList(args);
        log.debug(arguments);
        switch (arguments.get(0)){
            case("lab1"):
                lab1Result(arguments);
                break;
            case("lab2"):
                lab2Result(arguments);
                break;
            case("lab3"):
                lab3Result(arguments);
                break;
            case("lab4"):
                lab4Result(arguments);
                break;
            case("lab5"):
                lab5Result(arguments);
                break;
            default:
                log.error("Args error!");
                System.exit(-1);
        }



//        AbstractDataProvider dataProvider = resolveDataProvider(arguments);
//        if (dataProvider == null){
//            log.error(Constants.BAD_ARGS_FORMAT);
//            System.exit(1);
//        }
//        String result = resolveAPIResult(dataProvider,arguments);
//        System.out.print(result);
    }
}
