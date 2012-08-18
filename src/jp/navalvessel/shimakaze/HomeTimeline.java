/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.navalvessel.shimakaze;

import java.io.IOException;
import java.util.LinkedList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.navalvessel.shimakaze.core.TwitterServiceAgent;
import org.apache.log4j.Logger;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.UserStreamAdapter;

/**
 *
 * @author eguchi
 */
public class HomeTimeline extends UserStreamAdapter {

    private static final Logger LOG = Logger.getLogger(HomeTimeline.class);
    private int maxStatusNum = 100;
    private ObservableList<Status> statusList = FXCollections.observableList(new LinkedList<Status>());

    private HomeTimeline() {
    }

    public static HomeTimeline create() throws IOException {
        final HomeTimeline instance = new HomeTimeline();
        final TwitterServiceAgent twitterServiceAgent = Context.getInstance().getTwitterServiceAgent();
        twitterServiceAgent.addHomeTimelineListener(instance);

        return instance;
    }

    public void tweet(String text) throws TwitterException, IOException {
        final TwitterServiceAgent twitterServiceAgent = Context.getInstance().getTwitterServiceAgent();
        twitterServiceAgent.updateStatus(text);
    }

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
                } else if (statusList.size() == 0) {
                    statusList.add(status);
                } else {
                    for (int i = 0; i < statusList.size(); i++) {
                        final Status s = statusList.get(i);
                        if (s.getId() < status.getId()) {
                            statusList.add(i, status);
                            break;
                        }
                    }
                }



            }
        });

    }
}
