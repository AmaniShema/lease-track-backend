# LeaseTrack - Mid-Term Project

Student Name: Shema Amani
Student ID: 26961
Course Code & Name: INSY 8322 Web Technology and Internet
Institution: Adventist University of Central Africa (AUCA)

---

## 1. Project Overview
LeaseTrack is a backend system designed for landlords to manage rental units, tenants, and lease agreements digitally. Built using Spring Boot, Spring Data JPA, and PostgreSQL, it handles complex relational mappings, custom derived queries, JPQL queries with joins, business rule validations, and rent payment tracking.

---

## 2. Prerequisites & Running the Application
1. Java JDK: Version 17 or higher.
2. PostgreSQL: Create a database named `lease_track` (or let Hibernate auto-create via `spring.jpa.hibernate.ddl-auto=update`).
3. Configuration (`application.properties`):
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/lease_track
   spring.datasource.username=postgres
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
