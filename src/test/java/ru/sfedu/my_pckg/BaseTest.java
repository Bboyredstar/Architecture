package ru.sfedu.my_pckg;

import ru.sfedu.my_pckg.beans.Course;
import ru.sfedu.my_pckg.beans.Section;
import ru.sfedu.my_pckg.beans.Student;
import ru.sfedu.my_pckg.beans.Teacher;


import java.util.ArrayList;
import java.util.List;

public class BaseTest {
  public static Student createStudent(long id,String fname,String lname, int age,
                                      String email,String country,String preferences){
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

  public static Section createSection(long id,long Id,String name, String description,
                                      ArrayList<String> materials,ArrayList<String> videos){
      Section section = new Section();
      section.setId(id);
      section.setCourse(Id);
      section.setName(name);
      section.setDescription(description);
      section.setMaterials(materials);
      section.setVideos(videos);
      return section;
  }





}
