-- Student -->20905896T

INSERT INTO Student (dni , name, email, degree, password)
VALUES (1234, 'Roberto ElRueda', 'rodando@gmail.com', 'Medicina', '123');

INSERT INTO Student (dni , name, email, degree, password)
VALUES (5678, 'Paco Matao', 'seAMataoPaco@gmail.com', 'Ingenieria', '123');

INSERT INTO Student (dni , name, email, degree, password, banned)
VALUES (5678, 'Paco 1', 'seAMataoPaco1@gmail.com', 'Ingenieria', '123', TRUE);

INSERT INTO Student (dni , name, email, degree, password, banned)
VALUES (5678, 'Paco 2', 'seAMataoPaco2@gmail.com', 'Ingenieria', '123', TRUE);

INSERT INTO Student (dni , name, email, degree, isskp, password)
VALUES (91011, 'Manolo Hey', 'heyMan@gmail.com', 'Medicina', TRUE, '123');

INSERT INTO Student (dni , name, email, degree, isskp, password)
VALUES (91041, 'Manolo No Hey', 'heyNoMan@gmail.com', 'Medicina', TRUE, '123');

-- Student -->

-- Skill -->

INSERT INTO Skill (name, description, level)
VALUES ('Matematicas','Pues una clase normal la verdad, unas mates poraqui', 'beginner');

INSERT INTO Skill (name, description, level)
VALUES ('Matematicas','Pues una clase normal la verdad, unas mates poraqui', 'beginner');

INSERT INTO Skill (name, description, level)
VALUES ('Aleman','Pues una clase normal la verdad, unos pocos de der Abend poraqui', 'beginner');

INSERT INTO Skill (name, description, level)
VALUES ('Fuegos','Pues una clase normal la verdad, de fuego', 'beginner');

INSERT INTO Skill (name, description, level)
VALUES ('Fuegos','Pues una clase normal la verdad, de fuego', 'expert');

-- Skill -->


-- Offer -->

INSERT INTO Offer VALUES ('Ayuda en Matematicas 2' , '1234', 1 , 'necesito ayuda urgente', TO_DATE('04/03/2022', 'DD/MM/YYYY'), TO_DATE('14/03/2022', 'DD/MM/YYYY'));

-- Offer -->

--- Request -->
INSERT INTO Request VALUES ('Ayuda en Matematicas 2' , '5678', 1, 'doy ayuda', TO_DATE('04/03/2022', 'DD/MM/YYYY'), TO_DATE('14/03/2022', 'DD/MM/YYYY'));

-- Collaboration -->

INSERT INTO Collaboration VALUES ('1234','5678', 1 ,'Mi casa','notStarted',0,0,TO_DATE('04/03/2022','DD/MM/YYYY'),TO_DATE('07/03/2022','DD/MM/YYYY'));

-- Collaboration -->
