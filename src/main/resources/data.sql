INSERT INTO STUDENT (ID,EMAIL, FIRST_NAME, LAST_NAME, PATRONYMIC)
VALUES (1001, 'user@gmail.com', 'username', 'userlastname', 'patronomyc'),
       (2002, 'manager@email.ru', 'manager', 'manager', 'manager');

INSERT INTO COURSE (ID, TITLE)
VALUES (1000, 'course_1'),
       (2000, 'course_2');

INSERT INTO COURSES(STUDENT_ID, COURSE_ID)
VALUES (1001, 1000),
       (1001, 2000),
       (2002, 2000);

INSERT INTO MARKS(ID, MARK_VALUE, MARK_DATE, COURSE_ID, STUDENT_ID)
VALUES ( 101, 'A', '2021-01-01', 1000, 1001),
       ( 102, 'B','2021-01-02', 2000, 1001);