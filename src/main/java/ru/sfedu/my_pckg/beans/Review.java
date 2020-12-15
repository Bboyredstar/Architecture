package ru.sfedu.my_pckg.beans;

import com.opencsv.bean.CsvBindByName;


import java.io.Serializable;
import java.util.Objects;

/**
 * Class Review
 */
public class Review implements Serializable {

  //
  // Fields
  //
  @CsvBindByName
  private long id;
  @CsvBindByName
  private int rating;
  @CsvBindByName
  private String comment;
  
  //
  // Constructors
  //
  public Review () { };
  
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
  public void setId (long id) { this.id = id; }

  /**
   * Get the value of id
   * @return the value of id
   */
  public long getId () {
    return id;
  }

  /**
   * Set the value of rating
   * @param rating the new value of rating
   */
  public void setRating (int rating) {
    this.rating = rating;
  }

  /**
   * Get the value of rating
   * @return the value of rating
   */
  public int getRating () {
    return rating;
  }

  /**
   * Set the value of comment
   * @param comment the new value of comment
   */
  public void setComment (String comment) { this.comment = comment; }

  /**
   * Get the value of comment
   * @return the value of comment
   */
  public String getComment () {
    return comment;
  }

  //
  // Other methods
  //
  @Override
  public String toString(){
    return " Review : { "+
            "\nid: " + getId() +
            "\nrating: " + getRating() +
            "\ncomment: " + getComment() +
            "\n}";
  }
  @Override
  public int hashCode(){
    return Objects.hash(getId(),getRating(),getComment());
  }

  @Override
  public boolean equals(Object Obj){
    if (this == Obj) return true;
    if (Obj == null || getClass() != Obj.getClass()) return false;
    Review review = (Review) Obj;
    if (getId() != review.getId()) return false;
    if (!getComment().equals(review.getComment())) return false;
    if (getRating() != review.getRating()) return false;
    return true;
  }

}
