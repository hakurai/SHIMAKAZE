/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.navalvessel.shimakaze;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;

/**
 *
 * @author eguchi
 */
public class Sample implements Initializable {

    @FXML
    private TextArea inputTweetArea;
    @FXML
    private Button tweetButton;
    @FXML
    private ListView<Status> statusList;
    private TwitterServiceAgent twitterServiceAgent;
    private Executor tweetExecutor = Executors.newSingleThreadExecutor();

    @FXML
    private void handleTweetAction(ActionEvent event) {

        final String text = inputTweetArea.getText();
        tweetExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    twitterServiceAgent.updateStatus(text);
                } catch (TwitterException ex) {
                }
            }
        });

        inputTweetArea.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Configuration conf = TwitterServiceAgent.createConfiguration(new File("user.xml"));
            twitterServiceAgent = TwitterServiceAgent.createServiceAgent(conf);

            HomeTimeline homeTimeline = new HomeTimeline();
            twitterServiceAgent.addHomeTimelineListener(homeTimeline);

            statusList.setItems(homeTimeline.statusListProperty());
            statusList.setCellFactory(new StatusCellFactory());

        } catch (IOException ex) {
        }
    }
}
