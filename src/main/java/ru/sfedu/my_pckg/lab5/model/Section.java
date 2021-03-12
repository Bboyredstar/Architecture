package ru.sfedu.my_pckg.lab5.model;



import ru.sfedu.my_pckg.lab5.model.Course;
import ru.sfedu.my_pckg.utils.helpers.Helper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Entity(name="SECTION_LAB5")
public class Section implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="course_id",nullable = false)
    private Course course;
    @ElementCollection
    @OrderColumn
    @CollectionTable(name="MATERIALS_LAB5")
    private List<String> materials;
    @OrderColumn
    @CollectionTable(name="VIDEOS_LAB5")
    @ElementCollection
    private List<String> videos;



    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<String> getMaterials() {
        return materials;
    }

    public void setMaterials(List<String> materials) {
        this.materials = materials;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", materials='" + Helper.ListStringToString(materials) + '\'' +
                ", videos='" + Helper.ListStringToString(videos) + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return Objects.equals(id, section.id) && Objects.equals(name, section.name) && Objects.equals(description, section.description) && Objects.equals(course, section.course)  && Objects.equals(materials, section.getMaterials()) && Objects.equals(videos, section.getVideos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, course);
    }
}
