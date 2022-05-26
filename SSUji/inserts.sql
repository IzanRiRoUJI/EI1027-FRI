-- Student --
INSERT INTO Student (dni , name, email, degree, password)
VALUES ('0000', 'Roberto ElRueda', 'al0000@uji.es', 'Medicina', '123');

INSERT INTO Student (dni , name, email, degree, password)
VALUES ('0001', 'Patrocinia Cipriano', 'al0001@uji.es', 'Ingeniería', '123');

INSERT INTO Student (dni , name, email, degree, password)
VALUES ('0002', 'Pía Anastasio', 'al0002@uji.es', 'Derecho', '123');

INSERT INTO Student (dni , name, email, degree, password)
VALUES ('0003', 'Juan Pablo Íñigo', 'al0003@uji.es', 'Economía', '123');

INSERT INTO Student (dni , name, email, degree, password)
VALUES ('0004', 'Heraclio Prudencia', 'al0004@uji.es', 'Periodismo', '123');

INSERT INTO Student (dni , name, email, degree, password)
VALUES ('0005', 'Bernabé Aarón', 'al0005@uji.es', 'Química', '123');

    -- SKP --

INSERT INTO Student (dni , name, email, degree, isskp, password)
VALUES ('0006', 'SKP1', 'skp01@uji.es', 'Medicina', TRUE, '123');

INSERT INTO Student (dni , name, email, degree, isskp, password)
VALUES ('0007', 'SKP2', 'skp02@gmail.com', 'Química', TRUE, '123');

-- Student --

-- Skill --

INSERT INTO Skill (name, description, level) -- 1 --
VALUES ('Mathematics','Mathematics for beginners', 'beginner');

INSERT INTO Skill (name, description, level) -- 2 --
VALUES ('Programming','Programming for beginners', 'beginner');

INSERT INTO Skill (name, description, level) -- 3 --
VALUES ('Programming','Programming for intermediates', 'intermediate');

INSERT INTO Skill (name, description, level)  -- 4 --
VALUES ('German','German for beginners', 'beginner');

INSERT INTO Skill (name, description, level)  -- 5 --
VALUES ('German','German for intermediates', 'intermediate');

INSERT INTO Skill (name, description, level)  -- 6 --
VALUES ('German','German for experts', 'expert');

INSERT INTO Skill (name, description, level)  -- 7 --
VALUES ('Physics','Physics for beginners', 'beginner');

INSERT INTO Skill (name, description, level)  -- 8 --
VALUES ('Physics','Physics for intermediates', 'intermediate');

INSERT INTO Skill (name, description, level)  -- 9 --
VALUES ('Algebra','Algebra for beginners', 'beginner');

INSERT INTO Skill (name, description, level)  -- 10 --
VALUES ('Algebra','Algebra for intermediates', 'intermediate');

INSERT INTO Skill (name, description, level)  -- 11 --
VALUES ('Algebra','Algebra for experts', 'expert');


-- Offer --

    --- Mathematics --
INSERT INTO Offer (name, dniOffer, skillId, description, startDate, endDate)
VALUES ('help with anything math related' , '0001', 1 , 'i have a final coming soon and i need help as soon as possible', TO_DATE('01/05/2022', 'DD/MM/YYYY'), TO_DATE('01/07/2022', 'DD/MM/YYYY'));

    --- German --
INSERT INTO Offer (name, dniOffer, skillId, description, startDate, endDate)
VALUES ('I can help anyone with German' , '0005', 4 , 'German is my second language so you can trust me :)', TO_DATE('01/05/2022', 'DD/MM/YYYY'), TO_DATE('01/07/2022', 'DD/MM/YYYY'));
INSERT INTO Offer (name, dniOffer, skillId, description, startDate, endDate)
VALUES ('I can help anyone with German' , '0005', 5 , 'German is my second language so you can trust me :)', TO_DATE('01/05/2022', 'DD/MM/YYYY'), TO_DATE('01/07/2022', 'DD/MM/YYYY'));
INSERT INTO Offer (name, dniOffer, skillId, description, startDate, endDate)
VALUES ('I can help anyone with German' , '0005', 6 , 'German is my second language so you can trust me :)', TO_DATE('01/05/2022', 'DD/MM/YYYY'), TO_DATE('01/07/2022', 'DD/MM/YYYY'));

    --- Algebra --
INSERT INTO Offer (name, dniOffer, skillId, description, startDate, endDate)
VALUES ('algebra in economics' , '0003', 9 , 'mainly macroeconomics', TO_DATE('01/05/2022', 'DD/MM/YYYY'), TO_DATE('01/07/2022', 'DD/MM/YYYY'));

    --- Programming --
INSERT INTO Offer (name, dniOffer, skillId, description, startDate, endDate)
VALUES ('programming help' , '0001', 3 , 'I have intermediate knowledge', TO_DATE('01/05/2022', 'DD/MM/YYYY'), TO_DATE('01/07/2022', 'DD/MM/YYYY'));
INSERT INTO Offer (name, dniOffer, skillId, description, startDate, endDate)
VALUES ('basic programming' , '0000', 2 , 'I did a course in high school so I can help with the basics', TO_DATE('01/05/2022', 'DD/MM/YYYY'), TO_DATE('01/07/2022', 'DD/MM/YYYY'));

--- Request --

    --- Mathematics --
INSERT INTO Request (name, dniRequest, skillId, description, startDate, endDate)
VALUES ('need help with matrix transpose' , '0005', 1 , 'I dont understand much about matrix transpose.', TO_DATE('10/05/2022', 'DD/MM/YYYY'), TO_DATE('7/07/2022', 'DD/MM/YYYY'));
INSERT INTO Request (name, dniRequest, skillId, description, startDate, endDate)
VALUES ('Help with Mathematics II' , '0000', 1, 'I dont understand the new maths topic', TO_DATE('04/03/2022', 'DD/MM/YYYY'), TO_DATE('14/07/2022', 'DD/MM/YYYY'));

    --- German --
INSERT INTO Request (name, dniRequest, skillId, description, startDate, endDate) -- 3 --
VALUES ('need urgent help' , '0004', 4, 'I need to understand German for an interview', TO_DATE('04/03/2022', 'DD/MM/YYYY'), TO_DATE('14/07/2022', 'DD/MM/YYYY'));

    --- Programming --
INSERT INTO Request (name, dniRequest, skillId, description, startDate, endDate) -- 4 --
VALUES ('programming please' , '0004', 2, 'I need help understanding a program', TO_DATE('04/03/2022', 'DD/MM/YYYY'), TO_DATE('14/07/2022', 'DD/MM/YYYY'));
