package org.project.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "chairs")
public class Chair {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "chair_name", nullable = false)
    private String chairName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "chair")
    private List<StudentGroup> studentGroups;

    public void addStudentGroup(StudentGroup studentGroup) {
        this.studentGroups.add(studentGroup);
        studentGroup.setChair(this);
    }

    public void removeStudentGroup(StudentGroup studentGroup) {
        this.studentGroups.remove(studentGroup);
        studentGroup.setChair(null);
    }

    @Override
    public String toString() {
        return String.format("ID %d: name - %s", id, chairName);
    }
}
