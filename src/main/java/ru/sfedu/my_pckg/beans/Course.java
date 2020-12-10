
package ru.sfedu.my_pckg.beans;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.my_pckg.utils.helpers.Helper;
import ru.sfedu.my_pckg.utils.csvConverters.IdTransformer;

import java.io.Serializable;
import java.util.*;


/**
 * Class Course
 */
public class Course implements Serializable{

  //
  // Fields
  //
  @CsvBindByName
  private long id;
  @CsvBindByName
  private String name;
  @CsvBindByName
  private String description;
  @CsvBindByName
  private long owner;
  @CsvCustomBindByName(column = "students", converter = IdTransformer.class)
  private List<Long> students;

  
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
   * Set the value of name
   * @param name the new value of name
   */
  public void setName (String name) {
    this.name = name;
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
   * @param description the new value of description
   */
  public void setDescription (String description) {
    this.description = description;
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
   * @param owner the new value of owner
   */
  public void setOwner (long owner) {
    this.owner = owner;
  }

  /**
   * Get the value of owner
   * @return the value of owner
   */
  public long getOwner () {
    return owner;
  }

  public List<Long> getStudents() {
    return students;
  }

  public void setStudents(List<Long> students) {
    this.students = students;
  }

  //
  // Other methods
  //
  @Override
  public String toString(){
    return " Course : { "+
            "\nid: " + getId() +
            "\nname: " + getName() +
            "\ndescription: " + getDescription() +
            "\nowner: " + getOwner() +
            "\nstudents: " + Helper.ListToString(getStudents()) +
            "\n}";
  }
  @Override
  public int hashCode(){
    return Objects.hash(getId(),getName(),getDescription(),getOwner(),
            Helper.ListToString(getStudents()));
  }

  @Override
  public boolean equals(Object Obj){
    if (this == Obj) return true;
    if (Obj == null || getClass() != Obj.getClass()) return false;
    Course course = (Course) Obj;
    if (getId() != course.getId()) return false;
    if (!getName().equals(course.getName())) return false;
    if (getOwner() != course.getOwner()) return false;
    if (!Helper.ListToString(getStudents()).equals(Helper.ListToString(course.getStudents()))) return false;
    return getDescription().equals(course.getDescription());
  }

}
