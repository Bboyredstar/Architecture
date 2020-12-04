package ru.sfedu.my_pckg.beans;

import java.util.*;


/**
 * Class Question
 */
public class Question {

  //
  // Fields
  //

  private long id;
  private String question;
  
  //
  // Constructors
  //
  public Question () { };
  
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
   * Set the value of question
   * @param question the new value of question
   */
  public void setQuestion (String question) {
    this.question = question;
  }

  /**
   * Get the value of question
   * @return the value of question
   */
  public String getQuestion () {
    return question;
  }

  //
  // Other methods
  //
  @Override
  public String toString(){
    return " Question : { "+
            "\nid: " + getId() +
            "\nquestion: " + getQuestion() +
            "\n}";
  }
  @Override
  public int hashCode(){
    return Objects.hash(getId(),getQuestion());
  }

  @Override
  public boolean equals(Object Obj){
    if (this == Obj) return true;
    if (Obj == null || getClass() != Obj.getClass()) return false;
    Question question = (Question) Obj;
    if (getId() != question.getId()) return false;
    if (!getQuestion().equals(question.getQuestion())) return false;
    return true;
  }
}
