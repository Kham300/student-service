mvn clean install

swagger - http://localhost/8080/api/swagger-ui/

- get all students:

`curl -X GET "http://localhost:8080/api/profile/all" -H "accept: application/json"`

- register new user:

`curl -X POST "http://localhost:8080/api/profile/register" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"email\": \"string@new\", \"firstName\": \"new\", \"lastName\": \"new\", \"patronymic\": \"new\"}"`

- get all courses:

`curl -X GET "http://localhost:8080/api/course/all" -H "accept: application/json"`

- show all Student courses:

`curl -X GET "http://localhost:8080/api/course/all/user/1001" -H "accept: application/json"`

- create new course:

`curl -X POST "http://localhost:8080/api/course" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"title\": \"string\"}"`

- Attempt to course as a student:

`curl -X PUT "http://localhost:8080/api/course/2001/add/1001" -H "accept: application/json"`

- Grade student:

`curl -X POST "http://localhost:8080/api/mark" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"courseId\": 2000, \"markDate\": \"2021-02-20\", \"studentId\": 2002, \"value\": \"A\"}"`
  
- Get average student mark:

`curl -X GET "http://localhost:8080/api/mark/average/1001" -H "accept: application/json"`