/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.navalvessel.shimakaze;

import java.io.File;
import java.io.IOException;
import jp.navalvessel.shimakaze.core.TwitterServiceAgent;
import twitter4j.conf.Configuration;

/**
 *
 * @author hakurai
 */
public class Context {

    private static final Context INSTANCE = new Context();
    private TwitterServiceAgent twitterServiceAgent;

    private Context() {
    }

    public static Context getInstance() {
        return INSTANCE;
    }

    public synchronized TwitterServiceAgent getTwitterServiceAgent() throws IOException {
        if (twitterServiceAgent == null) {
            Configuration conf = TwitterServiceAgent.createConfiguration(new File("user.xml"));
            twitterServiceAgent = TwitterServiceAgent.createServiceAgent(conf);
        }
        
        return twitterServiceAgent;
    }
}
