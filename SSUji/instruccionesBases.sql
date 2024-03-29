DROP TABLE Collaboration;
DROP TABLE Offer;
DROP TABLE Request;
DROP TABLE Student;
DROP TABLE Skill;



CREATE TABLE Student (
    dni     VARCHAR(10),
    name    VARCHAR(50),
    email   VARCHAR(50),
    degree  VARCHAR(50),
    balance FLOAT DEFAULT 0,
    isSKP   BOOLEAN DEFAULT FALSE,
    banned  BOOLEAN DEFAULT FALSE,
    password VARCHAR(50) NOT NULL,

    CONSTRAINT cp_student PRIMARY KEY (dni),
    CONSTRAINT calt_student UNIQUE (email)
);

CREATE TABLE Skill (
    id			SERIAL,
    name        VARCHAR(50),
    description VARCHAR(100),
    level       VARCHAR(50),
    active      BOOLEAN DEFAULT TRUE,

    CONSTRAINT cp_skill PRIMARY KEY (id),

    CONSTRAINT calt_skill UNIQUE (name, level),
    CONSTRAINT ri_skill_level CHECK (level in ('beginner', 'intermediate', 'expert'))
);

CREATE TABLE Offer (
    id          SERIAL,
    name        VARCHAR(50),
    dniOffer    VARCHAR(10),
    skillId     SERIAL,
    description VARCHAR(100),
    startDate   DATE,
    endDate     DATE,
    CONSTRAINT cp_offer PRIMARY KEY (id),
    CONSTRAINT ca_offer_student FOREIGN KEY (dniOffer) REFERENCES Student(dni) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ca_offer_skill FOREIGN KEY (skillId) REFERENCES Skill(id) ON DELETE RESTRICT ON UPDATE CASCADE

);

CREATE TABLE Request (
    id          SERIAL,
    name        VARCHAR(50),
    dniRequest  VARCHAR(10),
    skillId     SERIAL,
    description VARCHAR(100),
    startDate   DATE,
    endDate     DATE,
    CONSTRAINT cp_request PRIMARY KEY (id),
    CONSTRAINT ca_request_student FOREIGN KEY (dniRequest) REFERENCES Student(dni) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ca_request_skill FOREIGN KEY (skillId) REFERENCES Skill(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Collaboration (
    id          SERIAL,
    idRequest   SERIAL,
    idOffer     SERIAL,
    skillId     SERIAL,
    place       VARCHAR(100),
    state       VARCHAR(50) DEFAULT 'notStarted',
    score       INTEGER,
    hours       FLOAT,
    startDate   DATE,
    endDate     DATE,
    CONSTRAINT cp_collaboration PRIMARY KEY (id),

    CONSTRAINT ca_collaboration_offer FOREIGN KEY (idOffer) REFERENCES Offer(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ca_collaboration_request FOREIGN KEY (idRequest) REFERENCES Request(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ca_collaboration_skill FOREIGN KEY (skillId) REFERENCES Skill(id) ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT ri_collaboration_score CHECK (score BETWEEN 0 AND 5),
    CONSTRAINT ri_collaboration_state CHECK (state in ('notStarted', 'inProgress', 'finished'))
    -- No sea el mismo estudante
    --  CONSTRAINT ri_collaboration_sameSkill CHECK (offer.skillId == request.skillId), -- Mismas skills
    -- Las fechas dentro del mismo rango
);

