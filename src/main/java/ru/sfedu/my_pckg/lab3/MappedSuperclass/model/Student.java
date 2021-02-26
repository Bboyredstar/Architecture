package ru.sfedu.my_pckg.lab3.MappedSuperclass.model;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.my_pckg.enums.UserType;

import java.util.Objects;

/**
 * The type Student.
 */
@Root(name="Student")
public class Student extends User {

    @CsvBindByName
    @Element(name="preferences")
    private String preferences;

    public Student () {
        super.setType(UserType.STUDENT);
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
    @Override
    public String toString(){
        return "\nStudent : { "+
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
        return getPreferences().equals(newStudent.getPreferences());
    }
}
