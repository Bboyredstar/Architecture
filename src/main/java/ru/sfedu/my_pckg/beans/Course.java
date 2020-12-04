
package ru.sfedu.my_pckg.beans;
import java.util.*;


/**
 * Class Course
 */
public class Course {

  //
  // Fields
  //

  private long id;
  private String name;
  private String description;
  private Teacher owner;

  
  //
  // Constructors
  //
  public Course () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of id
   * @param newVar the new value of id
   */
  public void setId (long newVar) {
    id = newVar;
  }

  /**
   * Get the value of id
   * @return the value of id
   */
  public long getId () {
    return id;
  }

  /**
   * Set the value of name
   * @param newVar the new value of name
   */
  public void setName (String newVar) {
    name = newVar;
  }

  /**
   * Get the value of name
   * @return the value of name
   */
  public String getName () {
    return name;
  }

  /**
   * Set the value of description
   * @param newVar the new value of description
   */
  public void setDescription (String newVar) {
    description = newVar;
  }

  /**
   * Get the value of description
   * @return the value of description
   */
  public String getDescription () {
    return description;
  }

  /**
   * Set the value of owner
   * @param newVar the new value of owner
   */
  public void setOwner (Teacher newVar) {
    this.owner = newVar;
  }

  /**
   * Get the value of owner
   * @return the value of owner
   */
  public Teacher getOwner () {
    return owner;
  }



  //
  // Other methods
  //

}
