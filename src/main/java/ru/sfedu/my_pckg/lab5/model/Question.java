package ru.sfedu.my_pckg.lab5.model;

import javax.persistence.*;
import java.util.Objects;
@Entity(name="question_lab5")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String question;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="COURSE_LAB5_ID")
    private Course course;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_LAB5")
    private Student student;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return Objects.equals(id, question1.id) && Objects.equals(question, question1.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question,course,student);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id= " + id +
                ", question= " + question + '\'' +
                ", course = " + course.toString() + '\'' +
                ", student = " + student.toString() + '\'' +
                '}';
    }
}

