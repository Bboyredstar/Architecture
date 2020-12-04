
package ru.sfedu.my_pckg.beans;

import java.util.List;

/**
 * Class Section
 */
public class Section extends Course {

  //
  // Fields
  //

  private long id;
  private long courseId;
  private String name;
  private String description;
  private List<String> materials;
  private List<String> videos;
  
  //
  // Constructors
  //
  public Section () { };
  
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
   * Set the value of materials
   * @param materials the new value of materials
   */
  public void setMaterials (List<String> materials) {
    this.materials = materials;
  }

  /**
   * Get the value of materials
   * @return the value of materials
   */
  public List<String> getMaterials () {
    return materials;
  }

  /**
   * Set the value of videos
   * @param videos the new value of videos
   */
  public void setVideos (List<String> videos) {
    this.videos = videos;
  }

  /**
   * Get the value of videos
   * @return the value of videos
   */
  public List<String> getVideos () {
    return videos;
  }

  //
  // Other methods
  //

}
