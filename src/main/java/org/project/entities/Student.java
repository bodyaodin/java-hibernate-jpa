package org.project.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "phone", unique = true, nullable = false)
    private String phone;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "report_card", nullable = false)
    private int reportCard;
    @Column(name = "course", nullable = false)
    private int course;
    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private StudentGroup studentGroup;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "student")
    private List<Session> sessions;

    public void addSession(Session session) {
        this.sessions.add(session);
        session.setStudent(this);
    }

    public void removeSession(Session session) {
        this.sessions.remove(session);
        session.setStudent(null);
    }

    @Override
    public String toString() {
        return String.format("ID %d: name - %s %s, email - %s, phone - %s, birth date - %tD, " +
                        "report card - %d, course - %d, entry date - %tD",
                id, firstName, lastName, email, phone, birthDate, reportCard, course, entryDate);
    }
}
