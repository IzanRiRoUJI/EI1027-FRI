CREATE TABLE Student {
    dni     VARCHAR(10),
    name    VARCHAR(50),
    email   VARCHAR(50),
    degree  VARCHAR(50),
    balance FLOAT,
    isSKP   BOOLEAN,
    CONSTRAINT cp_student PRIMARY KEY (dni),
    CONSTRAINT calt_student UNIQUE (email)
}

CREATE TABLE Offer {
    name        VARCHAR(50),
    dniOffer    VARCHAR(10),
    skillName   VARCHAR(50),
    skillLevel  VARCHAR(50),
    description VARCHAR(100),
    startDate   DATE,
    endDate     DATE,
    CONSTRAINT cp_offer PRIMARY KEY (dniOffer, skillName, skillLevel),
    CONSTRAINT ca_offer_student FOREIGN KEY (dniOffer) REFERENCES Student(dni) ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT ca_offer_skill FOREIGN KEY (skillName) REFERENCES Skill(name) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ca_offer_skill_level FOREIGN KEY (skillLevel) REFERENCES Skill(level) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ri_offer_endDate CHECK (endDate >= startDate)
}

CREATE TABLE Request {
    name        VARCHAR(50),
    dniRequest  VARCHAR(10),
    skillName   VARCHAR(50),
    skillLevel  VARCHAR(50),
    description VARCHAR(100),
    startDate   DATE,
    endDate     DATE,
    CONSTRAINT cp_request PRIMARY KEY (dniRequest, skillName, skillLevel),
    CONSTRAINT ca_request_student FOREIGN KEY (dnirequest) REFERENCES Student(dni) ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT ca_request_skill_skill FOREIGN KEY (skillName) REFERENCES Skill(name) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ca_request_skill_level FOREIGN KEY (skillLevel) REFERENCES Skill(skill) ON DELETE RESTRICT ON UPDATE CASCADE
    CONSTRAINT ri_request_endDate CHECK (endDate >= startDate)

}

CREATE TABLE Collaboration {
    dniOffer    VARCHAR(10),
    dniRequest  VARCHAR(10),
    skillName   VARCHAR(50),
    skillLevel  VARCHAR(50),
    place       VARCHAR(100),
    state       VARCHAR(50),
    score       INTEGER,
    hours       FLOAT,
    startDate   DATE,
    endDate     DATE,
    CONSTRAINT cp_collaboration PRIMARY KEY (dniOffer, dniRequest, skillName, skillLevel, state),
    CONSTRAINT ca_collaboration_offer FOREIGN KEY (dniOffer) REFERENCES Offer(dniOffer) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ca_collaboration_request FOREIGN KEY (dnirequest) REFERENCES Request(dniRequest) ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT ca_collaboration_skill_skill FOREIGN KEY (skillName) REFERENCES Skill(skill) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ca_collaboration_skill_level FOREIGN KEY (skillLevel) REFERENCES Skill(level) ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT ri_collaboration_score CHECK (score BETWEEN 0 AND 5),
    CONSTRAINT ri_collaboration_state CHECK (state in ('notStarted', 'inProgress', 'finished')),
    CONSTRAINT ri_collaboration_endDate CHECK (endDate >= startDate)
}

CREATE TABLE Skill {
    name        VARCHAR(50),
    description VARCHAR(100),
    level       VARCHAR(50),
    active      BOOLEAN,
    CONSTRAINT cp_skill PRIMARY KEY (name, level),
    CONSTRAINT ri_collaboration_level CHECK (level in ('beginner', 'intermediate', 'expert'))
}