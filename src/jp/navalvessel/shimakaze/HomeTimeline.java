/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.navalvessel.shimakaze;

import java.util.LinkedList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import twitter4j.Status;
import twitter4j.UserStreamAdapter;

/**
 *
 * @author eguchi
 */
public class HomeTimeline extends UserStreamAdapter {

    private int maxStatusNum = 100;
    private ObservableList<Status> statusList = FXCollections.observableList(new LinkedList<Status>());

    public ObservableList<Status> statusListProperty() {
        return statusList;
    }

    @Override
    public void onStatus(final Status status) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                if (statusList.size() >= maxStatusNum) {
                    statusList.remove(maxStatusNum - 1);
                }

                statusList.add(0, status);
            }
        });

    }
}
