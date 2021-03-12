package ru.sfedu.my_pckg.lab5.model;

import ru.sfedu.my_pckg.utils.helpers.Helper;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Objects;

/**
 * The type Student.
 */
@Entity(name="STUDENT_LAB5")
public class Student extends User {
    private String preferences;
        @ManyToMany (mappedBy = "students")
    private List<Course> courses;

    public Student () {

    };
    /**
     * Gets preferences.
     *
     * @return the preferences
     */
    public String getPreferences() {
        return preferences;
    }

    /**
     * Sets preferences.
     *
     * @param preferences the preferences
     */
    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString(){
        return "\nStudent : { "+
               "\nid: " + getId() +
                "\nfirstName: " + getFirstName() +
                "\nsecondName: " + getSecondName() +
                "\nemail: " + getEmail() +
                "\nage: " + getAge()+
                "\ncountry: " + getCountry()+
                "\npreferences: " + getPreferences() +
                "\ncourses: " + Helper.ListObjectToString(courses) +
                "\n}";
    }
    @Override
    public int hashCode(){
        return Objects.hash(getId(),getFirstName(),getSecondName(), getEmail(),getAge(),getCountry(),getPreferences(),getCourses());
    }

    @Override
    public boolean equals(Object Obj){
        if (this == Obj) return true;
        if (Obj == null || getClass() != Obj.getClass()) return false;
        Student newStudent = (Student) Obj;
        if (getId() != newStudent.getId()) return false;
        if (!getFirstName().equals(newStudent.getFirstName())) return false;
        if (!getSecondName().equals(newStudent.getSecondName())) return false;
        if (!getEmail().equals(newStudent.getEmail())) return false;
        if (getAge()!= newStudent.getAge()) return false;
        if (!getCountry().equals(newStudent.getCountry())) return false;
        return getPreferences().equals(newStudent.getPreferences());
    }
}
