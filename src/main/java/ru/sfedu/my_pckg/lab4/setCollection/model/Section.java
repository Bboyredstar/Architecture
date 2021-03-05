
package ru.sfedu.my_pckg.lab4.setCollection.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.my_pckg.utils.csvConverters.UrlTransformer;
import ru.sfedu.my_pckg.utils.helpers.Helper;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class Section
 */
@Root
public class Section implements Serializable{

  //
  // Fields
  //
  @CsvBindByName
  @Attribute(name="id")
  private long id;
  @CsvBindByName
  @Element(name="course")
  private long course;
  @CsvBindByName
  @Element(name="name")
  private String name;
  @CsvBindByName
  @Element(name="description")
  private String description;
  @ElementList(inline = true, entry = "material")
  @CsvCustomBindByName(column = "materials", converter = UrlTransformer.class)
  private List<String> materials;
  @ElementList(inline = true, entry = "video")
  @CsvCustomBindByName(column = "videos", converter = UrlTransformer.class)
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
   * @param  Id the new value of course
   */
  public void setCourse (long Id) {
    this.course = Id;
  }

  /**
   * Get the value of course
   * @return the value of course
   */
  public long getCourse () {
    return course;
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


  @Override
  public String toString(){
    return " \nSection : { "+
            "\nid: " + getId() +
            "\ncourseId: " + getCourse() +
            "\nname: " + getName() +
            "\ndescription: " + getDescription() +
            "\nvideos: " + getVideos().stream().map(Objects::toString).collect(Collectors.joining(" ,"))+
            "\nmaterials: " + getMaterials().stream().map(Objects::toString).collect(Collectors.joining(" ,")) +

            "\n}";
  }
  @Override
  public int hashCode(){
    return Objects.hash(getId(),getCourse(),
            getCourse(),getName(),getDescription(),
            getVideos().stream().map(Objects::toString).collect(Collectors.joining(" ,")),
            getMaterials().stream().map(Objects::toString).collect(Collectors.joining(" ,")));
  }

  @Override
  public boolean equals(Object Obj){
    if (this == Obj) return true;
    if (Obj == null || getClass() != Obj.getClass()) return false;
    Section section = (Section) Obj;
    if (getId() != section.getId()) return false;
    if (getCourse() != section.getCourse()) return false;
    if (!getName().equals(section.getName())) return false;
    if (!getDescription().equals(section.getDescription())) return false;
    if (!Helper.ListStringToString(getMaterials()).equals(Helper.ListStringToString(section.getMaterials()))) return false;
    if (!Helper.ListStringToString(getVideos()).equals(Helper.ListStringToString(section.getVideos()))) return false;
    return true;
  }
}
