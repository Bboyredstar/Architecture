package ru.sfedu.my_pckg.lab5.model;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name="COURSE_LAB5")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="TEACHER_LAB5_ID",nullable = false)
    private Teacher owner;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "COURSE_REVIEW_LAB5",
            joinColumns =
                    { @JoinColumn(name = "COURSE_LAB5_ID" )},
            inverseJoinColumns =
                    { @JoinColumn(name = "REVIEW_LAB5_ID")})
    private Review courseReview;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "STUDENTS_COURSES_LAB5",
            joinColumns = {@JoinColumn(name="COURSE_ID")},
            inverseJoinColumns = {@JoinColumn(name="STUDENT_ID")}
    )
    private List<Student> students;


    public Teacher getOwner() {
        return owner;
    }

    public void setOwner(Teacher owner) {
        this.owner = owner;
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

    public Review getReview() {
        return courseReview;
    }

    public void setReview(Review review) {
        this.courseReview = review;
    }

    public Review getCourseReview() {
        return courseReview;
    }

    public void setCourseReview(Review courseReview) {
        this.courseReview = courseReview;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        String rev;
        try{
            rev = this.getCourseReview().toString();
        }
        catch (NullPointerException e){
            rev = "";
        }
        return "Course{" +
                "id=" + id +
                ", name=" + name + '\'' +
                ", description=" + description + '\'' +
                ", owner=" + owner.toString() + '\'' +
                ", review=" + rev + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
       Course course = (Course) o;
        return Objects.equals(id, course.getId()) && Objects.equals(name, course.getName()) && Objects.equals(owner, course.getOwner())&& Objects.equals(description, course.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, owner,courseReview);
    }
}
