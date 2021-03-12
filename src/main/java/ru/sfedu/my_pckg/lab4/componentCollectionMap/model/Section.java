
package ru.sfedu.my_pckg.lab4.componentCollectionMap.model;


import ru.sfedu.my_pckg.utils.helpers.Helper;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Embeddable
public class Section implements Serializable{

  private String name;
  private String description;
  
  //
  // Constructors
  //
  public Section() { };
  
  //
  // Methods
  //

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


  //
  // Other methods
  //


  @Override
  public String toString(){
    return " \nSection : { "+
            "\nname: " + getName() +
            "\ndescription: " + getDescription() +
            "\n}";
  }
  @Override
  public int hashCode(){
    return Objects.hash(getName(),getDescription());
  }

  @Override
  public boolean equals(Object Obj){
    if (this == Obj) return true;
    if (Obj == null || getClass() != Obj.getClass()) return false;
    Section section = (Section) Obj;
    if (!getName().equals(section.getName())) return false;
    if (!getDescription().equals(section.getDescription())) return false;
    return true;
  }
}
