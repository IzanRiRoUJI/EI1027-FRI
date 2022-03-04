<-- Student -->

INSERT INTO Student (dni , name, email, degree)
VALUES (1234, 'Roberto ElRueda', 'rodando@gmail.com', 'Medicina');

INSERT INTO Student (dni , name, email, degree)
VALUES (5678, 'Paco Matao', 'seAMataoPaco@gmail.com', 'Ingenieria');

INSERT INTO Student (dni , name, email, degree, isskp)
VALUES (91011, 'Manolo Hey', 'heyMan@gmail.com', 'Medicina', TRUE);

<-- Student -->

<-- Skill -->

INSERT INTO Skill (name, description, level)
VALUES ('Matematicas','Pues una clase normal la verdad, unas mates poraqui', 'Beginner');

INSERT INTO Skill (name, description, level)
VALUES ('Matematicas','Pues una clase normal la verdad, unas mates poraqui', 'beginner');

INSERT INTO Skill (name, description, level)
VALUES ('Aleman','Pues una clase normal la verdad, unos pocos de der Abend poraqui', 'beginner');

INSERT INTO Skill (name, description, level)
VALUES ('Fuegos','Pues una clase normal la verdad, de fuego', 'beginner');

<-- Skill -->


<-- Offer -->

INSERT INTO Offer VALUES ('Ayuda en Matematicas 2' , '1234', 'Matematicas', 'beginner', 'necesito ayuda urgente', TO_DATE('04/03/2022', 'DD/MM/YYYY'), TO_DATE('14/03/2022', 'DD/MM/YYYY'));

<-- Offer -->

<--- Request -->
INSERT INTO Request VALUES ('Ayuda en Matematicas 2' , '5678', 'Matematicas', 'beginner', 'doy ayuda', TO_DATE('04/03/2022', 'DD/MM/YYYY'), TO_DATE('14/03/2022', 'DD/MM/YYYY'));

<-- Collaboration -->

INSERT INTO Collaboration VALUES ('1234','5678','Matematicas','beginner','Mi casa','notStarted',0,0,TO_DATE('04/03/2022','DD/MM/YYYY'),TO_DATE('07/03/2022','DD/MM/YYYY'));

<-- Collaboration -->
