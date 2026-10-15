# Student Management System

### Version: 0.9.0 (Beta 1)

## What Is It About?

The Student Management System is a desktop application that allows users to manage student records in a table. Records can include student IDs, names, nicknames (if applicable), home addresses, phone numbers, email addresses, and enrolled classes.

## Features

1. Add students to the system.
2. Delete students from the system.
3. Load student data from a JSON file.
4. Save student data to a JSON file.
5. Automatically load previously saved student data when the application starts.

## How to Run the Application

Currently, you can run the application using Maven:

```bash
mvn javafx:run
```

Alternatively, you can package the application for Windows using `jpackage`. A more convenient executable distribution is planned for a future release.

## Recommended Java Version

- Java 21

## Dependencies

- **JavaFX (OpenJFX):** Provides the graphical user interface.
- **Jackson:** Handles JSON serialization and deserialization.
- **Maven:** Builds the project and manages dependencies.

See `pom.xml` for dependency versions and configuration.

## Known Limitations

1. Student records may need additional fields. More student information is planned for Beta 2.
2. The application does not yet include an About section with information about the application, its version, and its developer.
3. A ready-to-download Windows executable is not yet available.
4. JSON is currently the only supported data format.

## Planned Features for Beta 2

1. Light and dark themes.
2. Additional student information fields.
3. An About section containing information about the application, its version, and its developer.
4. Support for additional data formats, including CSV and YAML.

## Dependencies and Third-Party Licenses

This project uses third-party libraries, including JavaFX and Jackson. Their respective licensing terms apply independently of the license covering this project's original code.

See [THIRD-PARTY-LICENSES.md](THIRD-PARTY-LICENSES.md) for licensing information.

## License

This project's original code is licensed under the Apache License 2.0. You may use, modify, and distribute that code in accordance with the terms of the license.

See [LICENSE](LICENSE) for the full license text.

Third-party dependencies, including JavaFX and Jackson, remain subject to their respective licenses. See [THIRD-PARTY-LICENSES.md](THIRD-PARTY-LICENSES.md) for details.
