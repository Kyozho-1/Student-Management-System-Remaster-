/**
 * Chase Parlett
 * Last Edited 2026/09/27
 * Version: 0.9.0 (Beta 1)
 */

package com.kyozho.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class Main extends Application {
    private Scene scene;
    private StackPane stackPane;

    /** (non-Javadoc)
     * The start of the application.
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage stage) {
        stackPane = new StackPane();

        scene = new Scene(stackPane, 500, 500);

        new StudentManageSystem(stackPane, stage);
        
        stage.setMaximized(true);
        stage.setTitle("Student Management System");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}