package com.kyozho.backend;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains methods for processing JSON data.
 */
public class JsonProcesser {
    private ObjectMapper objMapper;

    private final String DATA_PATH = "data/students.json";

    /**
     * Sets up the <code>ObjectMapper</code> needed to be able to process JSON data.
     */
    public JsonProcesser() {
        objMapper = new ObjectMapper();
    }

    /**
     * Saves the saved data from the table to <code>students.json</code> in the <code>data</code> folder.
     * @param students A <code>Student</code> <code>ArrayList</code> containing the students from the table.
     */
    public void saveToJson(ArrayList<Student> students) {
        try {
            objMapper.writerWithDefaultPrettyPrinter().writeValue(new File(DATA_PATH), students);
        } catch (IOException IOE) {
            IOE.printStackTrace();
        }
    }

    /**
     * Opens the JavaFX's <code>FileChooser</code> and only filters JSON files. <br>
     * Once the user selects a JSON file, the student management system will add that data from the file
     * into the table.
     * @param path The path of where the JSON file is.
     * @return If the user doesn't select "cancel", the <code>ArrayList</code> will contain the students.
     * Otherwise, an empty <code>ArrayList</code> will be returned.
     */
    public ArrayList<Student> loadFromJson(String path) {
        try {
            List<Student> loadedStudents = Arrays.asList(
                objMapper.readValue(Paths.get(path).toFile(), Student[].class)
            );

            return new ArrayList<Student>(loadedStudents);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<Student>();
    }
    
    /**
     * Loads the JSON data from the <code>data</code> folder into the table. <br>
     * Data saved in the <code>data</code> 
     * folder is saved data that was in the table from the user's last use.
     * @return If there's a JSON file in the <code>data</code> folder, then there will be in the students
     * in the <code>ArrayList</code>. Otherwise, an empty <code>ArrayList</code> will be returned.
     */
    public ArrayList<Student> loadLastSavedData() {
        try {
            List<Student> lastSavedStudents = Arrays.asList(
                objMapper.readValue(Paths.get(DATA_PATH).toFile(), Student[].class)
            );

            return new ArrayList<Student>(lastSavedStudents);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<Student>();
    }
}
