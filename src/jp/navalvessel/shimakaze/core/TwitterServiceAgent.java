package jp.navalvessel.shimakaze.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author eguchi
 */
public class TwitterServiceAgent implements UserStreamListener {

    private static final Logger LOG = Logger.getLogger(TwitterServiceAgent.class);
    public static final String CONSUMER_KEY = "nn40h8NFnZEZC8i8i3p7qQ";
    public static final String CONSUMER_SECRET = "NmYNbCpgEYn96TchIN7b0aaI7WLICyuUFZgZTjPNk4";
    private Configuration conf;
    private String myScreenName;
    private Twitter rest;
    private TwitterStream homeTimelineStream;
    private List<TwitterStream> filterStreamList = new ArrayList<TwitterStream>();

    private TwitterServiceAgent(Configuration conf) {
        this.conf = conf;
        rest = new TwitterFactory(conf).getInstance();
    }

    public static TwitterServiceAgent createServiceAgent(Configuration conf) {
        return new TwitterServiceAgent(conf);
    }

    public static Configuration createConfiguration(File propFile) throws IOException {
        Properties prop = new Properties();

        FileInputStream stream = null;
        try {
            stream = new FileInputStream(propFile);
            prop.loadFromXML(stream);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignored) {
                }
            }
        }

        String accessToken = prop.getProperty("accessToken");
        String accessTokenSecret = prop.getProperty("accessTokenSecret");

        if (accessToken == null || accessTokenSecret == null) {
            throw new IllegalStateException("no accessToken or accessTokenSecret");
        }

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setUserStreamRepliesAllEnabled(false)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        return builder.build();
    }

    public static Configuration createConfigurationFile(AccessToken token) throws TwitterException {
        String accessToken = token.getToken();
        String accessTokenSecret = token.getTokenSecret();

        Properties prop = new Properties();
        prop.setProperty("accessToken", accessToken);
        prop.setProperty("accessTokenSecret", accessTokenSecret);

        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream("user.xml");
            prop.storeToXML(stream, "Shimakaze user properties");
        } catch (IOException e) {
            LOG.error("", e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    LOG.error("", e);
                }
            }
        }

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        return builder.build();
    }

//    public ResponseList<Status> getHomeTimeline( Paging paging ) throws TwitterException{
//
//        return rest.getHomeTimeline( paging );
//    }
//
//    public ResponseList<Status> getMentions( Paging paging ) throws TwitterException{
//
//        return rest.getMentions( paging );
//    }
//
//    public ResponseList<DirectMessage> getDirectMessage( Paging paging ) throws TwitterException{
//
//        return rest.getDirectMessages( paging );
//    }
    public void addHomeTimelineListener(StatusListener l) {
        getHomeTimelineStream().addListener(l);
    }

    public TwitterStream getHomeTimelineStream() {
        if (homeTimelineStream == null) {
            homeTimelineStream = new TwitterStreamFactory(conf).getInstance();
            homeTimelineStream.addListener(this);
            homeTimelineStream.user();
        }

        return homeTimelineStream;
    }

    public String getMyScreenName() {
        if (rest == null) {
            return "";
        }

        if (myScreenName == null) {
            try {
                myScreenName = rest.getScreenName();
            } catch (TwitterException e) {
                return "";
            }
        }

        return myScreenName;


    }

    public long getMyID() throws TwitterException {
        if (rest == null) {
            throw new IllegalStateException("");
        }
        return rest.getId();
    }

    public User showUser(String screenName) throws TwitterException {
        if (rest == null) {
            throw new IllegalStateException("");
        }
        return rest.showUser(screenName);
    }

    public Status retweetStatus(long l) throws TwitterException {
        if (rest == null) {
            throw new IllegalStateException("");
        }
        return rest.retweetStatus(l);
    }

    public Status createFavorite(long l) throws TwitterException {
        if (rest == null) {
            throw new IllegalStateException("");
        }
        return rest.createFavorite(l);
    }

    public void updateStatus(String text) throws TwitterException {
        rest.updateStatus(text);
    }

    @Override
    public void onDeletionNotice(long l, long l1) {
    }

    @Override
    public void onFriendList(long[] longs) {
    }

    @Override
    public void onFavorite(User user, User user1, Status status) {
    }

    @Override
    public void onUnfavorite(User user, User user1, Status status) {
    }

    @Override
    public void onFollow(User user, User user1) {
    }

    @Override
    public void onRetweet(User user, User user1, Status status) {
    }

    @Override
    public void onDirectMessage(DirectMessage dm) {
    }

    @Override
    public void onUserListMemberAddition(User user, User user1, UserList ul) {
    }

    @Override
    public void onUserListMemberDeletion(User user, User user1, UserList ul) {
    }

    @Override
    public void onUserListSubscription(User user, User user1, UserList ul) {
    }

    @Override
    public void onUserListUnsubscription(User user, User user1, UserList ul) {
    }

    @Override
    public void onUserListCreation(User user, UserList ul) {
    }

    @Override
    public void onUserListUpdate(User user, UserList ul) {
    }

    @Override
    public void onUserListDeletion(User user, UserList ul) {
    }

    @Override
    public void onUserProfileUpdate(User user) {
    }

    @Override
    public void onBlock(User user, User user1) {
    }

    @Override
    public void onUnblock(User user, User user1) {
    }

    @Override
    public void onStatus(Status status) {
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice sdn) {
    }

    @Override
    public void onTrackLimitationNotice(int i) {
    }

    @Override
    public void onScrubGeo(long l, long l1) {
    }

    @Override
    public void onException(Exception excptn) {
    }
}
