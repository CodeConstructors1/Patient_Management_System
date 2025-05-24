# Patient_Management_System
 Patient Management System (Java + MySQL + Swing)
 A simple desktop application built using Java, Swing (GUI), and MySQL to perform basic CRUD operations on patient data. This project follows a layered architecture using the DAO (Data Access Object) pattern.

 Technologies Used:Java SE,Swing (Java GUI),MySQL,JDBC (Java Database Connectivity).
 
 Key Features
 Add new patients to the database

View all patient records

Update patient details by ID

Delete patient records by ID

Search patients by ID or name

Scrollable GUI for patient list display

 Classes Overview

 MySQL Database Setup
 

 CREATE TABLE patients (
 
    id INT AUTO_INCREMENT PRIMARY KEY,
    
    name VARCHAR(100) NOT NULL,
     
    age INT NOT NULL,
    
    gender VARCHAR(10),
    
    disease VARCHAR(255)
    
    );

   How to Run

   1. Clone the repository:
   
   2. Set up the database and update your DB credentials in DatabaseConnection.java:
   
   String url = "jdbc:mysql://localhost:3306/HospitalDB";
   
  String user = "root";// Username

  String password = "your_password";
  

   3. Compile and run PatientManagementGUI.java using your preferred IDE .

    





 

    









