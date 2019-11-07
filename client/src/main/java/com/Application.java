package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class Application extends AbstractJavaFxApplicationSupport {

    private Stage primaryStage;

    @Autowired
    private ControllersConfiguration.ViewHolder view;

    public Application() {
    }

    public static void main(String[] args) {
        launchApp(Application.class, args);
    }

    public void initRootLayout() {
        Scene scene = new Scene(view.getView());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("IT-client");

        initRootLayout();
    }
}
