package org.example.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.controllers.SettingsController;

public class App extends Application {
    private Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        SettingsController settingsController = new SettingsController();
        Scene settingsScene = new Scene(settingsController);
        window.setScene(settingsScene);
        window.show();
    }
}
