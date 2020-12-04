package ru.sfedu.my_pckg.beans;

import com.sun.corba.se.spi.ior.ObjectKey;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Student.
 */
public class Student extends User implements Serializable {
    private String preferences;

    public Student () { };
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
    @Override
    public String toString(){
        return " Student : { "+
               "\nid: " + getId() +
                "\nfirstName: " + getFirstName() +
                "\nsecondName: " + getSecondName() +
                "\nemail: " + getEmail() +
                "\nage: " + getAge()+
                "\ncountry: " + getCountry()+
                "\npreferences " + getPreferences() +
                "\n}";
    }
    @Override
    public int hashCode(){
        return Objects.hash(getId(),getFirstName(),getSecondName(), getEmail(),getAge(),getCountry(),getPreferences());
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
        if (!getPreferences().equals(newStudent.getPreferences())) return false;
        return true;
    }
}
