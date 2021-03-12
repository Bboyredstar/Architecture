
package ru.sfedu.my_pckg.lab4.componentCollection.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;


@Embeddable
public class Section {

  @Column(nullable = false)
  private String name;
  @Column(nullable = true)
  private String description;


  public Section() { };

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
