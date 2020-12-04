package ru.sfedu.my_pckg.beans;


import java.util.*;


/**
 * Class CourseActivity
 */
public class CourseActivity {

  //
  // Fields
  //

  private long id;
  private long courseId;
  private ArrayList <Long> questionsId;
  private long review;
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

}
