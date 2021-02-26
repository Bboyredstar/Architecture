package ru.sfedu.my_pckg.lab2.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Test")
public class TestBean implements Serializable {
    public TestBean(){}
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Embedded
    private EmbeddedEntity testEntity;


    @Column
    private boolean checking;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = new java.util.Date((date.getTime()/1000)*1000);
    }

    public boolean getChecking() {
        return checking;
    }

    public void setChecking(boolean check) {
        this.checking = check;
    }

    public EmbeddedEntity getTestEntity() {
        return testEntity;
    }

    public void setTestEntity(EmbeddedEntity testEntity) {
        this.testEntity = testEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestBean testBean = (TestBean) o;
        return checking == testBean.checking && Objects.equals(id, testBean.id) && Objects.equals(name, testBean.name) && Objects.equals(description, testBean.description) && (date.compareTo(testBean.date)) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, date, testEntity, checking);
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", testEntity=" + testEntity.toString() +
                ", checking=" + checking +
                '}';
    }
}
