package com.kyozho.frontend;

import com.kyozho.backend.Student;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.io.File;
import java.util.ArrayList;

import com.kyozho.backend.JsonProcessor;

/**
 * The system that manages students via a table. <br>
 * This system can:
 * <ol>
 *  <li>Add a student.</li>
 *  <li>Delete a student.</li>
 *  <li>Search for a student via I.D.</li>
 *  <li>Load a student dataset via a JSON file.</li>
 *  <li>Save the student dataset to a JSON in the folder strcuture and quit the program.</li>
 * </ol>
 * When the application opens up, the application will process data in the <code>data</code> folder and 
 * put it in the table.
 */
public class StudentManageSystem {
    enum OptionValues {
        ADD_STUDENT, DELETE_STUDENT, SEARCH, LOAD_FROM_FILE, SAVE_AND_QUIT;
    }

    private final String[] OPTIONS = {
        "Add Student", "Delete Student", "Search for Student", 
        "Load from file", "Save and Quit"
    };

    private Button[] buttons = {
		new Button(OPTIONS[OptionValues.ADD_STUDENT.ordinal()]), 
        new Button(OPTIONS[OptionValues.DELETE_STUDENT.ordinal()]), 
		new Button(OPTIONS[OptionValues.SEARCH.ordinal()]), 
        new Button(OPTIONS[OptionValues.LOAD_FROM_FILE.ordinal()]),
		new Button(OPTIONS[OptionValues.SAVE_AND_QUIT.ordinal()])
    };

    private File filePath;

    private Stage stage;

    private TableView<Student> studentsTableView;

    private StackPane stackPane;

    private final ToolBar toolBar;
    private final BorderPane borderPane;

    private ArrayList<Student> students;

    private boolean savingFirstTime;

    private JsonProcessor jsonProcessor;

    private Alert errAlert;
    private Alert infoAlert;

    /**
     * Sets up the necessary components for the student management system to use.
     * @param stackPane The root of the application.
     * @param stage The stage/window of the application.
     */
    public StudentManageSystem(StackPane stackPane, Stage stage) {
        this.stage = stage;

        students = new ArrayList<>();

        jsonProcessor = new JsonProcessor();

        savingFirstTime = true;

        this.stackPane = stackPane;

        studentsTableView = new TableView<Student>();

        studentsTableView.setEditable(true);

        toolBar = new ToolBar();

        borderPane = new BorderPane();
        
        errAlert = new Alert(AlertType.ERROR);
        infoAlert = new Alert(AlertType.INFORMATION);

        configureToolBar();

        loadLastSavedData();

        addStudentBtnFunctionality();
        addDeleteBtnFunctionality();
        addSearchFunctionality();
        addLoadFromFileFunctionality();
        addQuitBtnFunctionality();

        /* Stylizing the application */
        stackPane.getStylesheets().add(
            getClass()
            .getResource("/com/kyozho/css/main-screen.css")
            .toExternalForm()
        );

        stackPane.getStyleClass().add("stack-pane");
    }
    
    private void configureToolBar() {
        for (Button button : buttons) {
            toolBar.getItems().addAll(button);
        }

        borderPane.setTop(toolBar);

        stackPane.getChildren().add(borderPane);
    }

    /**
     * Creates the columns (if not already created) and adds a default row to the table. <br>
     * After adding a row, it saves the table to the <code>Student</code> <code>ArrayList</code>
     */
    private void addStudentBtnFunctionality() {
        buttons[OptionValues.ADD_STUDENT.ordinal()].setOnAction(event -> {
            if (studentsTableView.getColumns().isEmpty()) {
                createColumns();
            }

            createDefaultStudents();

            save();
        });
    }

    /**
     * Opens a dialog window and allows a user to search for a student via a student's I.D.
     */
    private void addSearchFunctionality() {
        buttons[OptionValues.SEARCH.ordinal()].setOnAction(event -> {
			final String DIALOG_TITLE = "Searching Student Information";
            final int[] DIALOG_DIMENSIONS = {300, 300};

			Dialog<Void> dialog = new Dialog<>();

			dialog.setTitle(DIALOG_TITLE);
            dialog.setHeight(DIALOG_DIMENSIONS[0]);
            dialog.setWidth(DIALOG_DIMENSIONS[1]);

			dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

			dialog.getDialogPane().setContent(addStudentInfoForm());
            
            dialog.getDialogPane().getStylesheets().add(getClass()
                    .getResource("/com/kyozho/css/search-dialog.css")
                    .toExternalForm()
            );

			dialog.show();
        });
    }

    /**
     * Deletes a selected row from the table. <br>
     * If no row is selected, an error alert will be displayed. <br>
     * After deleting the row, the table will be saved.
     */
    private void addDeleteBtnFunctionality() {
        buttons[OptionValues.DELETE_STUDENT.ordinal()].setOnAction(event -> {
            Student selectedRow = studentsTableView.getSelectionModel().getSelectedItem();

            if (selectedRow == null) {
                showErrAlert("Please select a row to delete.");

                return;
            }

            studentsTableView.getItems().remove(selectedRow);

            save();
        });
    }

    /**
     * Opens the JavaFX's <code>FileChooser</code> and only filters JSON files. <br>
     * once a JSON file is chosen, it loads the contents of the JSON file into the table.
     * If there's any contents in the table, that content will be overwritten with the new content from the
     * JSON file.
     */
    private void addLoadFromFileFunctionality() {
        final String JSON_FILE_EXTENSION = "*.json";

        FileChooser jsonChooser = new FileChooser();

        jsonChooser.getExtensionFilters().add(new ExtensionFilter("JSON Files", JSON_FILE_EXTENSION));
        jsonChooser.setTitle("Selecting a JSON file");

        buttons[OptionValues.LOAD_FROM_FILE.ordinal()].setOnAction(event -> {
            filePath = jsonChooser.showOpenDialog(stage);
            
            try {
                if (studentsTableView.getColumns().isEmpty()) {
                    createColumns();
                }

                students = jsonProcessor.loadFromJson(filePath.getAbsolutePath());
                
                studentsTableView.getItems().clear();
                
                students.forEach(student -> studentsTableView.getItems().addAll(student));
            } catch (NullPointerException NPE) {
                return;
            }
        });
    }

    /**
     * Saves the contents of the table into the JSON in the <code>data</code> folder and quits the application.
     */
    private void addQuitBtnFunctionality() {
        buttons[OptionValues.SAVE_AND_QUIT.ordinal()].setOnAction(event -> {
            jsonProcessor.saveToJson(students);
            
            System.exit(0);
        });
    }

    /* Helper Methods */
    @SuppressWarnings("unchecked")
    /**
     * Creates the columns of the table.
     */
    private void createColumns() {
        final Label[] LABELS = {
            new Label("I.D."), new Label("Name"), 
            new Label("Nickname (Optional)"), new Label("Home Address"),
            new Label("Phone Number"), new Label("E-mail"), new Label("Classes")
        };

        TableColumn<Student, String> idColumn = new TableColumn<>(LABELS[0].getText());
        TableColumn<Student, String> nameColumn = new TableColumn<>(LABELS[1].getText());
        TableColumn<Student, String> nicknameColumn = new TableColumn<>(LABELS[2].getText());
        TableColumn<Student, String> addressColumn = new TableColumn<>(LABELS[3].getText());
        TableColumn<Student, String> phoneNumberColumn = new TableColumn<>(LABELS[4].getText());
        TableColumn<Student, String> emailColumn = new TableColumn<>(LABELS[5].getText());
        TableColumn<Student, String> classesColumn = new TableColumn<>(LABELS[6].getText());

        /* Setting CellValueFactories */
        idColumn.setCellValueFactory(
            cellData -> cellData.getValue().getIdProperty()
        );

        nameColumn.setCellValueFactory(
            cellData -> cellData.getValue().getNameProperty()
        );

        nicknameColumn.setCellValueFactory(
            cellData -> cellData.getValue().getNicknameProperty()
        );

        addressColumn.setCellValueFactory(
            cellData -> cellData.getValue().getHomeAddressProperty()
        );

        phoneNumberColumn.setCellValueFactory(
            cellData -> cellData.getValue().getPhoneNumberProperty()
        );

        emailColumn.setCellValueFactory(
            cellData -> cellData.getValue().getEmailProperty()
        );

        classesColumn.setCellValueFactory(
            cellData -> cellData.getValue().getClassesProperty()
        );

        /* Setting CellFactories */
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nicknameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        classesColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        /* Setting OnEditOnCommits and getting the columns. */
        idColumn.setEditable(false);

        nameColumn.setOnEditCommit(
            nameEvent -> {
                Student student = nameEvent.getRowValue();
                student.setName(nameEvent.getNewValue());
            });

        nicknameColumn.setOnEditCommit(
            nicknameEvent -> {
                Student student = nicknameEvent.getRowValue();
                student.setNickname(nicknameEvent.getNewValue());
            });

        addressColumn.setOnEditCommit(
            addressEvent -> {
                Student student = addressEvent.getRowValue();
                student.setHomeAddress(addressEvent.getNewValue());
            });

        phoneNumberColumn.setOnEditCommit(
            phoneNumberEvent -> {
                try {
                    Student student = phoneNumberEvent.getRowValue();
                    student.setPhoneNumber(phoneNumberEvent.getNewValue());
                } catch (IllegalArgumentException IAE) {
                    showErrAlert(IAE.getMessage());
                }
            });

        emailColumn.setOnEditCommit(
            emailEvent -> {
                Student student = emailEvent.getRowValue();
                student.setEmail(emailEvent.getNewValue());
            });

        classesColumn.setOnEditCommit(
            classesEvent -> {
                try {
                    Student student = classesEvent.getRowValue();
                    student.setClasses(classesEvent.getNewValue());
                } catch (IndexOutOfBoundsException IOOBE) {
                    showErrAlert(IOOBE.getMessage());
                }
            });
        
        studentsTableView.getColumns().addAll(idColumn, nameColumn, nicknameColumn, 
                                                addressColumn, phoneNumberColumn, emailColumn, 
                                                classesColumn);

        borderPane.setCenter(studentsTableView);

        studentsTableView.setColumnResizePolicy(
            TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS
        );
        
        for (TableColumn<Student, ?> column : studentsTableView.getColumns()) {
            column.setReorderable(false);
        }
    }

    /**
     * Inserts a row into the table with default default put into the row.
     */
    private void createDefaultStudents() {
        studentsTableView.getItems().addAll(
					new Student(createId(), "[Enter Name]", "[Enter Nickname]", 
								"[Enter Home Address]", "123-456-7890", 
								"[Enter E-mail]", "[Enter 1-6 Classes (Comma seperated)]")
                            );
    }

    /**
     * Creates the Student Information Form for the dialog window to contain. <br>
     * This allows the user to look a student in the table via the student's I.D. <br>
     * If the student cannot be found, then an info. alert will be displayed.
     * @return A <code>Node</code> containing the Student Information Form.
     */
    private Node addStudentInfoForm() {
        GridPane gridPane = new GridPane();
		TextField idTextField = new TextField();
		Button submitBtn = new Button("Submit");

        final Label ID_LABEL = new Label("I.D.: ");

        final Text ERROR_TEXT = new Text("Error! Please enter a student I.D.");

        gridPane.add(ID_LABEL, 0, 0);

        gridPane.add(idTextField, 1, 0);

        gridPane.add(submitBtn, 0, 2);
        
		submitBtn.setOnAction(event -> {
			String enteredId = idTextField.getText();
            
			for (Student student : students) {
				if (enteredId.equals(student.getIdProperty().getValue())) {
                    showInfoAlert(student.toString());
                    
                    return;
                }
			}

            /* This will run if the student isn't found. */
            showInfoAlert("The student with the I.D. (" + enteredId + ") was not able to be found.");
		});

        ID_LABEL.getStyleClass().add("id-label");
        ERROR_TEXT.getStyleClass().add("error-text");

        return gridPane;
    }

    /**
     * Shows an error alert.
     * @param message The message to be shown.
     */
    private void showErrAlert(String message) {
        errAlert.setContentText(message);

        errAlert.show();
    }

    /**
     * Shows an info. alert.
     * @param message The message to be shown.
     */
    private void showInfoAlert(String message) {
        infoAlert.setContentText(message);

        infoAlert.show();
    }

    /**
     * Gets the data within each row of the table and inserts it into the 
     * <code>Student</code> <code>ArrayList</code>. <br>
     * If the user is saving for the first time, the <code>Student</code> <code>ArrayList</code>
     * will not be cleared. Otherwise, the <code>ArrayList</code> will be cleared to avoid duplicate data.
     */
    private void save() {
        ObservableList<Student> rows = studentsTableView.getItems();

        if (savingFirstTime) {
            rows.forEach(student -> students.add(student));

            savingFirstTime = false;
        } else {
            /* This prevents duplicate data in the ArrayList. */
            students.clear();

            rows.forEach(student -> students.add(student));
        }
    }

    /**
     * Creates and formats a student's I.D.
     * @return A <code>String</code> of the student's I.D.
     */
    private String createId() {
        return String.format("%05d", students.size());
    }

    /**
     * Loads the <code>students.json</code> from the <code>data</code> folder into the table. <br>
     * Data saved in the <code>data</code> 
     * folder is saved data that was in the table from the user's last use.
     */
    private void loadLastSavedData() {
        try {
            if (studentsTableView.getColumns().isEmpty()) {
                createColumns();
            }

            students = jsonProcessor.loadLastSavedData();

            studentsTableView.getItems().clear();

            students.forEach(student -> studentsTableView.getItems().addAll(student));
        } catch (NullPointerException NPE) {
            return;
        }
    }
}