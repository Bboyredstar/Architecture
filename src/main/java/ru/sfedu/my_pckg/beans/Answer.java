package ru.sfedu.my_pckg.beans;

import com.opencsv.bean.CsvBindByName;


import java.io.Serializable;
import java.util.*;


/**
 * Class Answer
 **/ class Answer implements Serializable {

  //
  // Fields
  //
  @CsvBindByName
  private long id;
  @CsvBindByName
  private String answer;
  @CsvBindByName
  private long questionId;
  
  //
  // Constructors
  //
  public Answer () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of id
   * @param Id the new value of id
   */
  public void setId (long Id) {
    this.id = Id;
  }

  /**
   * Get the value of id
   * @return the value of id
   */
  public long getId () {
    return id;
  }

  /**
   * Set the value of answer
   * @param answer the new value of answer
   */
  public void setAnswer (String answer) {
    this.answer = answer;
  }

  /**
   * Get the value of answer
   * @return the value of answer
   */
  public String getAnswer () {
    return answer;
  }

  /**
   * Set the value of question
   * @param questionId the new value of question
   */
  public void setQuestion (long questionId) {
    this.questionId = questionId;
  }

  /**
   * Get the value of question
   * @return the value of question
   */
  public long getQuestion () {
    return questionId;
  }

  //
  // Other methods
  //

  @Override
  public String toString(){
    return " Answer : { "+
            "\nid: " + getId() +
            "\nanswer: " + getAnswer() +
            "\nquestion id: " + questionId +
            "\n}";
  }
  @Override
  public int hashCode(){
    return Objects.hash(getId(),getQuestion(),getQuestion());
  }

  @Override
  public boolean equals(Object Obj){
    if (this == Obj) return true;
    if (Obj == null || getClass() != Obj.getClass()) return false;
    Answer answer = (Answer) Obj;
    if (getId() != answer.getId()) return false;
    if (!getAnswer().equals(answer.getAnswer())) return false;
    return getQuestion() == answer.getQuestion();
  }

}
