# 📔 ElectronicDiary

**ElectronicDiary** is an electronic diary web application designed to record grades, comments, and information about students.  
The project is implemented in Java for the backend and uses HTML, CSS and JavaScript for the front-end.  

---

## 🧠 Project Overview

Functional:
- Administrator: creating a class, assigning a teacher, creating subjects, assigning teachers to a subject, deleting subjects from teachers, changing the password of users, assigning users to the role of a student
- Teacher: add grades, set attendance, add homework, view subject statistics and student grades
- Student: View grades, homework, report, schedule, attendance


---

## 🚀 Technology stack

| Component | Technology |
|-----------|------------|
| Backend  | Java, (Spring Boot) |
| Frontend | HTML, CSS, JavaScript |
| Database | MySQL / PostgreSQL |
| Build | Maven |

---

## 🛠 Requirements

Before launching, make sure that you have installed:

- JDK 11 or higher
- Maven
- Database (MySQL/PostgreSQL)

---

##  Installation

1. Clone the repository:
   git clone https://github.com/KKerya/ElectronicDiary.git
2. Set up the database. Create a database and import the create.sql SQL script:
3. Configure the database connection in application.properties:
  spring.datasource.url=jdbc:mysql://localhost:3306/electronic_diary

  spring.datasource.username=your_username
  
  spring.datasource.password=your_password
  
5. Build the project: mvn clean install
6. Launch the app: mvn spring-boot:run
