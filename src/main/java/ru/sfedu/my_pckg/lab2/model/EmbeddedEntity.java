package ru.sfedu.my_pckg.lab2.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Embeddable
public class EmbeddedEntity implements Serializable {
    public EmbeddedEntity(){};

    @Column(name="EmbeddedName")
    private String name;
    @Column(name="EmbeddedDescription")
    private String description;
    @ElementCollection
    private List<String> sessions;

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

    public List<String> getSessions() {
        return sessions;
    }

    public void setSessions(List<String> sessions) {
        this.sessions = sessions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmbeddedEntity that = (EmbeddedEntity) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(sessions, that.sessions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, sessions);
    }

    @Override
    public String toString() {
        return "EmbeddedEntity{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sessions=" + sessions +
                '}';
    }
}
