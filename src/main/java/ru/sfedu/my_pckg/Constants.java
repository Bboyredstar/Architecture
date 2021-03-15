package ru.sfedu.my_pckg;

public class Constants {
  /*TYPES*/
  public static final String STUDENT = "Student";
  public static final String TEACHER = "Teacher";
  public static final String SECTION = "Section";
  public static final String COURSE = "Course";
  public static final String COURSE_ACTIVITY = "CourseActivity";
  public static final String REVIEW = "Review";
  public static final String QUESTION = "Question";
  public static final String ANSWER = "Answer";

  /*INFO MESSAGES*/
  public static final String CREATING_ERROR = "Creating process error! ";
  public static final String CREATING_SUCCESS = "Creating process success! ";
  public static final String UPDATING_ERROR = "Updating process error! ";
  public static final String UPDATING_SUCCESS = "Updating process success! ";
  public static final String DELETING_ERROR = "Deleting process error! ";
  public static final String DELETING_SUCCESS = "Deleting process success! ";
  public static final String GETTING_BY_ID_FAIL = "Record not found! id: ";
  public static final String CURRENT_PATH = "Current path to csv files: ";
  public static final String FILE_NOT_EXIST = "File is not exist! ";
  public static final String FILE_CREATED = "File was created! ";
  public static final String LIST_EMPTY = "List is empty! ";
  public static final String GETTING_ERROR = "Getting list error! ";
  public static final String IDS_ERROR = "Problem with id! One or more id doesn't exist! ";
  public static final String EXTEND_ERROR = "Unknown method was given! ";
  public static final String JOINING_SUCCESS = "Student was successful joined to the course! ";
  public static final String JOINING_ERROR = "Joining error! ";
  public static final String UNSUBSCRIBE_SUCCES = "Student was unsubscribe from the course! ";
  public static final String UNSUBSCRIBE_ERROR = "Unsubscribing error! ";
  public static final String USER_NOT_JOIN = "Student not signed up for the course! ";
  public static final String REVIEW_ALREADY_EXIST = "Student already leaved a review about course! ";
  public static final String USER_ALREADY_JOINED = "Student already join this course! ";
  public static final String BAD_ARGS_FORMAT = "Bad args format! Args must be like: [DataProvider] [Method] {args}";
  public static final String DRIVER_ERROR = "DB driver error! ";
  public static final String CONNECTION_ERROR = "Connection error! ";
  public static final String HAS_DUPLICATE = "List of ids has repeat id! ";
  public static final String BAD_NAME = "Name cannot be empty!";
  public static final String BAD_QUESTION = "Question cannot be empty!";
  public static final String BAD_ANSWER = "Answer cannot be empty!";
  public static final String BAD_NAME_LENGTH = "Name must contain minimum 2 symbols ";
  public static final String ERROR_METHOD_SIGNATURE = "Bad method arguments! ";
  public static final String PROBLEM_INIT_DATA = "Problem with initialization data!";
  //Commands

  public static final String CSV_COMMAND = "csv";
  public static final String XML_COMMAND = "xml";
  public static final String JDBC_COMMAND = "jdbc";
  public static final String INIT = "./src/main/resources/create_tables.sql";
  public static final String CREATE_COURSE = "CREATECOURSE";
  public static final String UPDATE_COURSE = "UPDATECOURSE";
  public static final String DELETE_COURSE = "DELETECOURSE";
  public static final String VIEW_COURSE = "VIEWCOURSE";
  public static final String CHOOSE_COURSE = "CHOOSECOURSE";
  public static final String GET_STUDENTS_COURSES = "GETSTUDENTSCOURSES";
  public static final String CHECK_STUDENTS_QUESTIONS = "CHECKSTUDENTSQUESTIONS";


  //SQL
  public static final String SELECT_ALL = "SELECT * FROM ";
  public static final String TEACHER_INSERT = "INSERT INTO TEACHER(id,firstName,secondName,email,age,country,competence,experience) VALUES (?, ?, ?, ?, ? ,? ,? ,?)";
  public static final String STUDENT_INSERT = "INSERT INTO STUDENT(id,firstName,secondName,email,age,country,preferences) VALUES (?, ?, ?, ? ,? ,? ,?)";
  public static final String COURSE_INSERT = "INSERT INTO COURSE(id,owner,name,description,students) VALUES (?, ?, ?, ? ,? )";
  public static final String COURSE_ACTIVITY_INSERT = "INSERT INTO COURSEACTIVITY(id,course,student,questions,review) VALUES (?, ?, ?, ? ,? )";
  public static final String SECTION_INSERT = "INSERT INTO SECTION(id,course,name,description,materials,videos) VALUES (?, ?, ?, ? ,? ,? )";
  public static final String REVIEW_INSERT = "INSERT INTO REVIEW(id,rating,comment) VALUES (?, ?, ?)";
  public static final String QUESTION_INSERT = "INSERT INTO QUESTION(id,question) VALUES (?, ?)";
  public static final String ANSWER_INSERT = "INSERT INTO ANSWER(id,question,answer) VALUES (?, ?, ?)";
  public static final String SECTION_UPDATE = "UPDATE SECTION SET name = ?, description = ?, videos = ?, materials = ? WHERE id = ";
  public static final String COURSE_UPDATE = "UPDATE COURSE SET name = ?, description = ? WHERE id = ";
  public static final String JOIN_UPDATE = "UPDATE COURSE SET students = ? WHERE id = ";
  public static final String REVIEW_UPDATE = "UPDATE COURSEACTIVITY SET review = ? WHERE id = ?";
  public static final String QUESTIONS_UPDATE = "UPDATE COURSEACTIVITY SET questions = ? WHERE id = ?";
  public static final String DELETE_COURSE_ACTIVITY = "DELETE FROM COURSEACTIVITY WHERE course = ? AND student = ?";
  public static final String DElETE = "DELETE FROM ";
  public static final String SQL_ALL_SCHEMAS = "SELECT schema_name FROM information_schema.schemata";
  public static final String SQL_CATALOG_NAME = "SELECT current_catalog";
  public static final String SQL_CURRENT_USER = "SELECT current_user";
  public static final String SQL_SIZE_TABLES ="Select pg_size_pretty(pg_database_size(datname)) from pg_database";
  public static final String SQL_TABLES_NAME = "SELECT datname FROM pg_database";
  public static final String WHERE_ID = " WHERE id =  ";
  public static final String SELECT_IDS = " SELECT id FROM ";
  public static final String BIGINT = "BIGINT";
  public static final String VARCHAR = "VARCHAR";
  public static final String H2 = "H2";
  public static final String POSTGRES = "POSTGRESQL";
  public static final String NATIVE_SQL = "SELECT count(*) FROM COURSE_LAB5;";

}
