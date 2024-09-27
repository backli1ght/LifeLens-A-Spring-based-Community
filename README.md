
# LifeLens Community Platform

This project is a community platform developed using Spring Boot. 
It primarily implements features such as user registration, login, posting, and commenting.

## Project Overview

Lifelens is a community platform where users can register, log in, create posts, and comment on others' posts. 
The goal of the project is to provide users with an interactive community platform.

## Features

- User registration and login
- Create posts
- Comment on posts

## Tech Stack

- **Backend**: Spring Boot
- **Database**: MySQL
- **Frontend**: Thymeleaf, HTML, CSS, JavaScript
- **Others**: Maven, Git, Docker

## Installation and Running

### Prerequisites

- JDK 1.8 or above
- Maven 3.6 or above
- MySQL 5.7 or above

### Steps

1. **Clone the repository locally:**

   ```bash
   git clone git@github.com:backli1ght/LifeLens-A-Spring-based-Community.git
   ```

2. **Configure the database:**
   - Create a new MySQL database.
   - Import the provided SQL scripts from the `/resources` directory to set up the required tables and data.
   - Update the database configuration in the `application.properties` file located in the `/resources` directory with your local database details.

3. **Build the project using Maven:**

   Navigate to the root directory of the project and run:

   ```bash
   mvn clean install
   ```

4. **Run the project:**

   Navigate to the `src/main/java/com/nowcoder/community` directory and run the `CommunityApplication.java` file. 
   This will start the Spring Boot application.

   Or, run the following command from the root directory:

   ```bash
   mvn spring-boot:run
   ```

5. **Access the application:**

   Open your browser and go to:

   ```
   http://localhost:8080
   ```

   This will open the homepage of the community platform where you can register, log in, create posts, and interact with other users.
