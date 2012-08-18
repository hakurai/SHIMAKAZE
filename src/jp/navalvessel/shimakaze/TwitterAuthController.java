/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.navalvessel.shimakaze;

import jp.navalvessel.shimakaze.core.TwitterServiceAgent;
import jp.navalvessel.shimakaze.core.TwitterAuthAgent;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 *
 * @author hakurai
 */
public class TwitterAuthController {

    private static final Logger LOG = Logger.getLogger(TwitterAuthController.class);

    public void authlization() throws TwitterException {
        final TwitterAuthAgent twitterAuthAgent = new TwitterAuthAgent();
        final RequestToken token = twitterAuthAgent.getOAuthRequestToken();
        String url = token.getAuthenticationURL();

        BorderPane borderPane = new BorderPane();

        final WebView webView = new WebView();
        final Stage stage = new Stage(StageStyle.UTILITY);

        borderPane.setCenter(webView);
        Scene scene = new Scene(borderPane, 400, 640);
        stage.setScene(scene);
        stage.setTitle("認証");
        stage.show();
        final WebEngine engine = webView.getEngine();

        engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
                if (newState == State.SUCCEEDED) {
                    final Document document = engine.getDocument();
                    final Element oauth_pin = document.getElementById("oauth_pin");
                    if (oauth_pin != null) {
                        final NodeList code = oauth_pin.getElementsByTagName("code");
                        final String pin = code.item(0).getTextContent();
                        final AccessToken accessToken = twitterAuthAgent.enterPin(token, pin);
                        try {
                            TwitterServiceAgent.createConfigurationFile(accessToken);

                            stage.hide();

                            Parent root = MainFrameController.createView();

                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (IOException | TwitterException ex) {
                            LOG.fatal("", ex);
                        }


                    }

                }
            }
        });

        engine.load(url);

    }
}
