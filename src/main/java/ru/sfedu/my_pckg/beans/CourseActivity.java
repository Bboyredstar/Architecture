package ru.sfedu.my_pckg.beans;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.my_pckg.utils.csvConverters.IdTransformer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Class CourseActivity
 */
public class CourseActivity implements Serializable{

  //
  // Fields
  //
  @CsvBindByName
  private long id;
  @CsvBindByName
  private long courseId;
  @CsvCustomBindByName(column = "questions", converter = IdTransformer.class)
  private ArrayList<Long> questionsId;
  @CsvBindByName
  private long review;
  @CsvBindByName
  private long student;

  //
  // Constructors
  //
  public CourseActivity () { };

  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of id
   * @param id the new value of id
   */
  public void setId (long id) {
      this.id = id;
  }

  /**
   * Get the value of id
   * @return the value of id
   */
  public long getId () {
    return id;
  }

  /**
   * Set the value of course
   * @param courseId the new value of course
   */
  public void setCourse (long courseId) {
    this.courseId = courseId;
  }

  /**
   * Get the value of course
   * @return the value of course
   */
  public long getCourse () {
    return courseId;
  }

  /**
   * Set the value of question
   * @param questions the new value of question
   */
  public void setQuestion (ArrayList <Long> questions) {
    this.questionsId = questions;
  }

  /**
   * Get the value of question
   * @return the value of question
   */
  public ArrayList <Long> getQuestion () {
    return questionsId;
  }

  /**
   * Set the value of review
   * @param review the new value of review
   */
  public void setReview ( long review) {
    this.review = review;
  }

  /**
   * Get the value of review
   * @return the value of review
   */
  public long getReview () {
    return review;
  }

  /**
   * Set the value of student
   * @param student the new value of student
   */
  public void setStudent (long student) {
    this.student = student;
  }

  /**
   * Get the value of student
   * @return the value of student
   */
  public long getStudent () {
    return student;
  }

  //
  // Other methods
  //
  @Override
  public String toString(){
    return " CourseActivity : { "+
            "\nid: " + getId() +
            "\ncourseId: " + getCourse() +
            "\nquestions: " + questionsId.stream().map(Objects::toString).collect(Collectors.joining(" ,"))+
            "\nreview: " + getReview() +
            "\nstudent: " + getStudent() +
            "\n}";
  }
  @Override
  public int hashCode(){
    return Objects.hash(getId(),getCourse(),
            questionsId.stream().map(Objects::toString).collect(Collectors.joining(" ,")),
            getReview(),getStudent());
  }

  @Override
  public boolean equals(Object Obj){
    if (this == Obj) return true;
    if (Obj == null || getClass() != Obj.getClass()) return false;
    CourseActivity course = (CourseActivity) Obj;
    if (getId() != course.getId()) return false;
    if (getCourse() != course.getCourse()) return false;
    if (!getQuestion().equals(course.getQuestion())) return false;
    if (getReview() != course.getReview()) return false;
    return getStudent() == course.getStudent();
  }
}
