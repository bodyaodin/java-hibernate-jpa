package org.project.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Override
    public String toString() {
        return String.format("ID %d: name - %s", id, subjectName);
    }
}
