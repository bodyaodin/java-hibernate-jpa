package org.project.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "faculties")
public class Faculty {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "faculty_name", nullable = false)
    private String facultyName;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "faculty")
    private List<Chair> chairs;

    public void addChair(Chair chair) {
        this.chairs.add(chair);
        chair.setFaculty(this);
    }

    public void removeChair(Chair chair) {
        this.chairs.remove(chair);
        chair.setFaculty(null);
    }

    @Override
    public String toString() {
        return String.format("ID %d: name - %s", id, facultyName);
    }
}
