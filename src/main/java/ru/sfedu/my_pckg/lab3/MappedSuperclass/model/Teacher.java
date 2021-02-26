package ru.sfedu.my_pckg.lab3.MappedSuperclass.model;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.my_pckg.enums.UserType;

import java.util.Objects;

/**
 * The type Teacher.
 */
@Root
public class Teacher extends User {
    @CsvBindByName
    @Element(name="competence")
    private String competence;
    @Element(name="experience")
    @CsvBindByName
    private int experience;

    public Teacher () {
        super.setType(UserType.TEACHER);
    };
    /**
     * Gets competence.
     *
     * @return the competence
     */
    public String getCompetence() {
        return competence;
    }

    /**
     * Sets competence.
     *
     * @param competence the competence
     */
    public void setCompetence(String competence) {
        this.competence = competence;
    }

    /**
     * Gets experience.
     *
     * @return the experience
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Sets experience.
     *
     * @param experience the experience
     */
    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String toString(){
        return " \nTeacher : { "+
                "\nid: " + getId() +
                "\nfirstName: " + getFirstName() +
                "\nsecondName: " + getSecondName() +
                "\nemail: " + getEmail() +
                "\nage: " + getAge()+
                "\ncountry: " + getCountry()+
                "\ncompetence: " + getCompetence() +
                "\nexperience: " + getExperience() +
                "\n}";
    }
    @Override
    public int hashCode(){
        return Objects.hash(getId(),
                getFirstName(),getSecondName(),
                getEmail(),getAge(),getCountry(),
                getCompetence(),getExperience());
    }

    @Override
    public boolean equals(Object Obj){
        if (this == Obj) return true;
        if (Obj == null || getClass() != Obj.getClass()) return false;
        Teacher newTeacher = (Teacher) Obj;
        if (getId() != newTeacher.getId()) return false;
        if (!getFirstName().equals(newTeacher.getFirstName())) return false;
        if (!getSecondName().equals(newTeacher.getSecondName())) return false;
        if (!getEmail().equals(newTeacher.getEmail())) return false;
        if (getAge()!= newTeacher.getAge()) return false;
        if (!getCountry().equals(newTeacher.getCountry())) return false;
        if (!getCompetence().equals(newTeacher.getCompetence())) return false;
        return getExperience() == newTeacher.getExperience();
    }


}
