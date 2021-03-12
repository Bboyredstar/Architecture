package ru.sfedu.my_pckg.lab4.componentCollectionMap.model;



import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity(name="course_map")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private String description;
    @ElementCollection
    @CollectionTable(name="course_sections_map")
    @AttributeOverride(name="name",column = @Column(name="section_name",nullable = false))
    @MapKeyColumn(name="section_key")
    private Map<String,Section> sections = new HashMap();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String,Section> getSections() {
        return sections;
    }

    public void setSections(Map<String,Section>  sections) {
        this.sections = sections;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sections=" + sections +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Objects.equals(name, course.name) && Objects.equals(description, course.description) && Objects.equals(sections, course.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, sections);
    }
}
