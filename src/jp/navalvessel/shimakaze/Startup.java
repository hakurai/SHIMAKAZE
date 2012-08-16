/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.navalvessel.shimakaze;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author eguchi
 */
public class Startup extends Application {

    public static void main(String[] args) {
        Application.launch(Startup.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final File config = new File("user.xml");
        if (!config.exists()) {
            final TwitterAuthController authController = new TwitterAuthController();
            authController.authlization();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));

            stage.setScene(new Scene(root));
            stage.show();
        }



    }
}
