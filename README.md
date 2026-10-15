# Student Management System
### Version: 0.9.0 (Beta 1)

## What Is It About?
+ This system allows you to add students to a table. This allows you to keep track of students' IDs, names, nicknames (if applicable), home addresses, phone numbers, E-mails, and classes that they are enrolled in.

## Features
1. Adding a student to the system
2. Deleting a student from the system.
3. Load a student dataset via a JSON file.
4. Save the student dataset to a JSON in the folder strcuture and quit the application.
5. Automatically loading last saved data from previous use into the table.

## How to Run The Application
+ For now, in order to run the application, please use `mvn javafx:run` or generate an executable via Windows via `jpackage`. This problem should be fixed by the next update.

## Recommened Version of Java to Run This Application
+ Java 21

## Dependencies Needed to Run This Application
+ JavaFX
+ Maven

## Known Limitations
1. There could be more columns concerning more information about a student. Beta 2 should have more columns containing such information.
2. There's no "About" button talking about the developer, the version, etc.
3. There's no .exe present for easy download. This will be present in the release version.
4. JSON is the only format accepted for data handling.

## Features to Be Present in Beta 2
1. A light and dark mode.
2. More columns about information of students.
3. An "About" button talking about some information about the application and the developer.
4. More data formats will be accepted (CSV, YAML).

## Dependencies and Third-Party Licenses

This project uses the following libraries:

- **JavaFX (OpenJFX):** Provides the graphical user interface.
- **Jackson:** Handles JSON serialization and deserialization.

See [THIRD-PARTY-LICENSES.md](THIRD-PARTY-LICENSES.md) for licensing information.

The project uses Maven to manage its dependencies. See `pom.xml` for dependency versions and configuration.
