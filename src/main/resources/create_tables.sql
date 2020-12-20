CREATE TABLE IF NOT EXISTS TEACHER (
    id BIGINT primary key,
    firstName VARCHAR(50) NOT NULL,
    secondName VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    country VARCHAR(80) ,
    competence VARCHAR(255) NOT NULL,
    experience INT NOT NULL,
);

CREATE TABLE IF NOT EXISTS STUDENT (
    id BIGINT PRIMARY KEY,
    fistName VARCHAR(50) NOT NULL,
    secondName VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    country VARCHAR(80),
    preferences VARCHAR(255) NOT NULL,
);


CREATE TABLE IF NOT EXISTS COURSE (
    id BIGINT PRIMARY KEY,
    owner BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(255) NOT NULL,
    students ARRAY,
    FOREIGN KEY (owner) REFERENCES TEACHER(id),
);

CREATE TABLE IF NOT EXISTS SECTION (
    id BIGINT PRIMARY KEY,
    course BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(255),
    materials ARRAY,
    videos ARRAY,
    FOREIGN KEY (course) REFERENCES COURSE(id),
);

CREATE TABLE IF NOT EXISTS COURSEACTIVITY (
    id BIGINT PRIMARY KEY,
    course BIGINT NOT NULL,
    student BIGINT NOT NULL,
    questions ARRAY,
    review INT,
    FOREIGN KEY (course) REFERENCES COURSE(id),
    FOREIGN KEY (student) REFERENCES STUDENT(id),
);

CREATE TABLE IF NOT EXISTS REVIEW (
    id BIGINT PRIMARY KEY,
    rating INT NOT NULL,
    comment VARCHAR(255),
);

CREATE TABLE IF NOT EXISTS QUESTION (
    id BIGINT PRIMARY KEY,
    question VARCHAR(255) NOT NULL,
);

CREATE TABLE IF NOT EXISTS ANSWER (
    id BIGINT PRIMARY KEY,
    question BIGINT NOT NULL,
    answer VARCHAR(255) NOT NULL,
    FOREIGN KEY (question) REFERENCES QUESTION(id),
);

