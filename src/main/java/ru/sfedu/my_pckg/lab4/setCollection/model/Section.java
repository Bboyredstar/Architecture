
package ru.sfedu.my_pckg.lab4.setCollection.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name="section_set")
public class Section implements Serializable{


  @Id
  @GeneratedValue(strategy= GenerationType.SEQUENCE)
  private long id;
  private String name;
  private String description;
  @ElementCollection
  @CollectionTable(name="materials_set")
  private Set<String> materials;
  @ElementCollection
  @CollectionTable(name="videos_set",joinColumns = @JoinColumn(name = "section_set_id"))
  private Set<String> videos;
  
  //
  // Constructors
  //
  public Section() { };
  
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
   * Set the value of materials
   * @param materials the new value of materials
   */
  public void setMaterials (Set<String> materials) {
    this.materials = materials;
  }

  /**
   * Get the value of materials
   * @return the value of materials
   */
  public Set<String> getMaterials () {
    return materials;
  }

  /**
   * Set the value of videos
   * @param videos the new value of videos
   */
  public void setVideos (Set<String> videos) {
    this.videos = videos;
  }

  /**
   * Get the value of videos
   * @return the value of videos
   */
  public Set<String> getVideos () {
    return videos;
  }

  //
  // Other methods
  //


  @Override
  public String toString(){
    return " \nSection : { "+
            "\nid: " + getId() +
            "\nname: " + getName() +
            "\ndescription: " + getDescription() +
            "\nvideos: " + getVideos().stream().map(Objects::toString).collect(Collectors.joining(" ,"))+
            "\nmaterials: " + getMaterials().stream().map(Objects::toString).collect(Collectors.joining(" ,")) +

            "\n}";
  }
  @Override
  public int hashCode(){
    return Objects.hash(getId()
           ,getName(),getDescription(),
            getVideos().stream().map(Objects::toString).collect(Collectors.joining(" ,")),
            getMaterials().stream().map(Objects::toString).collect(Collectors.joining(" ,")));
  }

  @Override
  public boolean equals(Object Obj){
    if (this == Obj) return true;
    if (Obj == null || getClass() != Obj.getClass()) return false;
    Section section = (Section) Obj;
    if (getId() != section.getId()) return false;
    if (!getName().equals(section.getName())) return false;
    if (!getDescription().equals(section.getDescription())) return false;
    if (getMaterials().equals(section.getMaterials())) return false;
    if (getVideos().equals(section.getVideos())) return false;
    return true;
  }
}
