/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.navalvessel.shimakaze;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.apache.log4j.Logger;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 *
 * @author eguchi
 */
public class MainFrameController implements Initializable {

    private static final Logger LOG = Logger.getLogger(MainFrameController.class);
    @FXML
    private TextArea inputTweetArea;
    @FXML
    private Button tweetButton;
    @FXML
    private ListView<Status> statusList;
    private HomeTimeline homeTimeline;
    private Executor tweetExecutor = Executors.newSingleThreadExecutor();

    public static Parent createView() throws IOException {
        return FXMLLoader.load(MainFrameController.class.getResource("MainFrame.fxml"));
    }

    @FXML
    private void handleTweetAction(ActionEvent event) {

        final String text = inputTweetArea.getText();
        tweetExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        homeTimeline.tweet(text);
                    } catch (IOException ex) {
                        LOG.error("", ex);
                    }
                } catch (TwitterException ex) {
                }
            }
        });

        inputTweetArea.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            homeTimeline = HomeTimeline.create();
        } catch (IOException ex) {
            LOG.error("", ex);
        }

        statusList.setItems(homeTimeline.statusListProperty());
        statusList.setCellFactory(new StatusCellFactory());
    }
}
