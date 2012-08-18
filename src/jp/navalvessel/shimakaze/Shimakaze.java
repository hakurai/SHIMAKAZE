/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.navalvessel.shimakaze;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author eguchi
 */
public class Shimakaze extends Application {

    public static void main(String[] args) {
        Application.launch(Shimakaze.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final File config = new File("user.xml");
        if (!config.exists()) {
            final TwitterAuthController authController = new TwitterAuthController();
            authController.authlization();
        } else {
            Parent root = MainFrameController.createView();

            stage.setScene(new Scene(root));
            stage.setTitle("SHIMAKAZE");
            stage.show();
        }

    }
    

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }
}
