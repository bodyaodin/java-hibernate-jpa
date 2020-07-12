CREATE TABLE faculty
(
        id INTEGER,
        faculty_name VARCHAR(250) NOT NULL,
        CONSTRAINT faculties_pkey PRIMARY KEY (id)
);

CREATE TABLE chair
(
        id INTEGER,
        chair_name VARCHAR(250) NOT NULL,
        faculty_id INTEGER NOT NULL,
        CONSTRAINT chairs_pkey PRIMARY KEY (id),
        CONSTRAINT chairs_faculty_id_fkey FOREIGN KEY (faculty_id)
                REFERENCES faculty (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE stud_groups
(
        id INTEGER,
        group_name VARCHAR(250) NOT NULL,
        chair_id INTEGER NOT NULL,
        CONSTRAINT stud_groups_pkey PRIMARY KEY (id),
        CONSTRAINT stud_groups_chair_id_fkey FOREIGN KEY (chair_id)
                REFERENCES chair (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE student
(
        id INTEGER,
        first_name VARCHAR(250) NOT NULL,
        last_name VARCHAR(250) NOT NULL,
        email VARCHAR(250) NOT NULL,
        phone VARCHAR(50) NOT NULL,
        birth_date DATE NOT NULL,
        report_card INTEGER NOT NULL,
        course INTEGER NOT NULL,
        entry_date DATE NOT NULL,
        group_id INTEGER NOT NULL,
        CONSTRAINT students_pkey PRIMARY KEY (id),
        CONSTRAINT unique_email UNIQUE (email),
        CONSTRAINT unique_phone UNIQUE (phone),
        CONSTRAINT positive_report_card CHECK (report_card > 0),
        CONSTRAINT positive_course CHECK (course > 0),
        CONSTRAINT students_group_id_fkey FOREIGN KEY (group_id)
                REFERENCES stud_groups (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE subject
(
        id INTEGER,
        subject_name VARCHAR(250) NOT NULL,
        CONSTRAINT subjects_pkey PRIMARY KEY (id)
);

CREATE TABLE session
(
        id INTEGER,
        exam_date DATE NOT NULL,
        semester INTEGER NOT NULL,
        student_id INTEGER NOT NULL,
        subject_id INTEGER NOT NULL,
        grade INTEGER NOT NULL,
        CONSTRAINT sessions_pkey PRIMARY KEY (id),
        CONSTRAINT positive_semester CHECK (semester > 0),
        CONSTRAINT sessions_student_id_fkey FOREIGN KEY (student_id)
                REFERENCES student (id) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT sessions_subject_id_fkey FOREIGN KEY (subject_id)
                REFERENCES subject (id) ON DELETE RESTRICT ON UPDATE CASCADE,
        CONSTRAINT positive_grade CHECK (grade > 0)
);