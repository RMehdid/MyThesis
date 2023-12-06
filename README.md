# MyThesis - IHM Project

## Overview
MyThesis is a desktop Java Swing application developed for the "Humane Machine Interactions" class. It facilitates thesis management for three types of users: Students, Professors, and Admins.

## Features
- **Students and Professors:**
    - Read theses
    - Search for theses

- **Admins:**
    - Create, Read, Update, and Delete theses
    - Create accounts for professors

## Getting Started

### Clone the Repository
Clone the project from the GitHub repository:

```bash
git clone https://github.com/RMehdid/MyThesis.git
```

### Database Initialization

1. Ensure you have MySQL installed on your machine.

2. Create a new MySQL database.

3. Import the database schema using the provided SQL file:
   ```bash
   mysql -u your-username -p your-database-name < database.sql
   ```

### Run the Application

1. Open the project in your preferred Java IDE (e.g., IntelliJ, Eclipse).

2. Locate the `Main.java` file in the project.

3. Run the `Main.java` file to start the application.

## Usage

- **Students and Professors:**
    - Log in with your credentials.
    - Browse and search for theses.

- **Admins:**
    - Log in with admin credentials.
    - Perform thesis management tasks.
    - Create accounts for professors.

## License
This project is licensed under the MIT License.