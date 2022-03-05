CREATE TABLE Student (
    dni     VARCHAR(10),
    name    VARCHAR(50),
    email   VARCHAR(50),
    degree  VARCHAR(50),
    balance FLOAT DEFAULT 0,
    isSKP   BOOLEAN DEFAULT FALSE,

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
    name        VARCHAR(50),
    dniOffer    VARCHAR(10),
    skillId     SERIAL,
    description VARCHAR(100),
    startDate   DATE,
    endDate     DATE,
    CONSTRAINT cp_offer PRIMARY KEY (dniOffer, skillId),
    CONSTRAINT ca_offer_student FOREIGN KEY (dniOffer) REFERENCES Student(dni) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ca_offer_skill FOREIGN KEY (skillId) REFERENCES Skill(id) ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT ri_offer_endDate CHECK (endDate >= startDate)
);

CREATE TABLE Request (
    name        VARCHAR(50),
    dniRequest  VARCHAR(10),
    skillId     SERIAL,
    description VARCHAR(100),
    startDate   DATE,
    endDate     DATE,
    CONSTRAINT cp_request PRIMARY KEY (dniRequest, skillId),
    CONSTRAINT ca_request_student FOREIGN KEY (dniRequest) REFERENCES Student(dni) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ca_request_skill FOREIGN KEY (skillId) REFERENCES Skill(id) ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT ri_request_endDate CHECK (endDate >= startDate)
);

CREATE TABLE Collaboration (
    dniOffer    VARCHAR(10),
    dniRequest  VARCHAR(10),
    skillId     SERIAL,
    place       VARCHAR(100),
    state       VARCHAR(50) DEFAULT 'notStarted',
    score       INTEGER,
    hours       FLOAT,
    startDate   DATE,
    endDate     DATE,
    CONSTRAINT cp_collaboration PRIMARY KEY (dniOffer, dniRequest, skillId, state),

    CONSTRAINT ca_collaboration_offer FOREIGN KEY (dniOffer, skillId) REFERENCES Offer(dniOffer, skillId) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ca_collaboration_request FOREIGN KEY (dniRequest, skillId) REFERENCES Request(dniRequest, skillId) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ca_collaboration_skill FOREIGN KEY (skillId) REFERENCES Skill(id) ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT ri_collaboration_score CHECK (score BETWEEN 0 AND 5),
    CONSTRAINT ri_collaboration_state CHECK (state in ('notStarted', 'inProgress', 'finished')),
    CONSTRAINT ri_collaboration_endDate CHECK (endDate >= startDate)
    -- No sea el mismo estudante
    --  CONSTRAINT ri_collaboration_sameSkill CHECK (offer.skillId == request.skillId), -- Mismas skills
    -- Las fechas dentro del mismo rango
);

