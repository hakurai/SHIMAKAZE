/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.navalvessel.shimakaze.core;

import org.apache.log4j.Logger;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 *
 * @author hakurai
 */
public class TwitterAuthAgent {

    private static final String CONSUMER_KEY = "nn40h8NFnZEZC8i8i3p7qQ";
    private static final String CONSUMER_SECRET = "NmYNbCpgEYn96TchIN7b0aaI7WLICyuUFZgZTjPNk4";
    private static Logger LOG = Logger.getLogger(TwitterAuthAgent.class);
    private Twitter twitter = new TwitterFactory().getInstance();

    public TwitterAuthAgent() {
        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
    }

    public RequestToken getOAuthRequestToken() throws TwitterException {
        RequestToken requestToken = twitter.getOAuthRequestToken();
        return requestToken;
    }

    

    public AccessToken enterPin(RequestToken requestToken, String pin) {
        AccessToken accessToken = null;

        try {
            if (pin.length() > 0) {
                accessToken = twitter.getOAuthAccessToken(requestToken, pin);
            } else {
                accessToken = twitter.getOAuthAccessToken();
            }
        } catch (TwitterException ex) {
            if (401 == ex.getStatusCode()) {
                LOG.fatal("", ex);
            } else {
                LOG.fatal("", ex);
            }
        }

        return accessToken;
    }
}
