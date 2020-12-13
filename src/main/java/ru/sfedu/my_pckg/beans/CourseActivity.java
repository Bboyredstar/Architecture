package ru.sfedu.my_pckg.beans;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.my_pckg.utils.csvConverters.IdTransformer;
import ru.sfedu.my_pckg.utils.helpers.Helper;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


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
  private long course;
  @CsvCustomBindByName(column = "questions", converter = IdTransformer.class)
  private List<Long> questions;
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
   * @param course the new value of course
   */
  public void setCourse (long course) {
    this.course = course;
  }

  /**
   * Get the value of course
   * @return the value of course
   */
  public long getCourse () {
    return course;
  }

  /**
   * Set the value of question
   * @param questions the new value of question
   */
  public void setQuestions (List <Long> questions) {
    this.questions = questions;
  }

  /**
   * Get the value of question
   * @return the value of question
   */
  public List <Long> getQuestions () {
    return questions;
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
            "\nquestions: " + Helper.ListToString(questions)+
            "\nreview: " + getReview() +
            "\nstudent: " + getStudent() +
            "\n}";
  }
  @Override
  public int hashCode(){
    return Objects.hash(getId(),getCourse(),
            Helper.ListToString(questions),
            getReview(),getStudent());
  }

  @Override
  public boolean equals(Object Obj){
    if (this == Obj) return true;
    if (Obj == null || getClass() != Obj.getClass()) return false;
    CourseActivity course = (CourseActivity) Obj;
    if (getId() != course.getId()) return false;
    if (getCourse() != course.getCourse()) return false;
    if (!Helper.ListToString(getQuestions()).equals(Helper.ListToString(course.getQuestions()))) return false;
    if (getReview() != course.getReview()) return false;
    return getStudent() == course.getStudent();
  }
}
