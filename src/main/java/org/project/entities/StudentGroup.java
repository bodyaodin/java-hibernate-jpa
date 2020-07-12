package org.project.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "stud_groups")
public class StudentGroup {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chair_id", nullable = false)
    private Chair chair;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "studentGroup")
    private List<Student> students;

    public void addStudent(Student student) {
        this.students.add(student);
        student.setStudentGroup(this);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
        student.setStudentGroup(null);
    }

    @Override
    public String toString() {
        return String.format("ID %d: name - %s", id, groupName);
    }
}
