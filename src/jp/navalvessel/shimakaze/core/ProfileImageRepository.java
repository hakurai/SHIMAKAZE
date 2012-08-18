package jp.navalvessel.shimakaze.core;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ProfileImageRepository {

    private static final ProfileImageRepository INSTANCE = new ProfileImageRepository();
    private final Map<String, Image> imageCache = new HashMap<String, Image>();

    private ProfileImageRepository() {
    }

    public static ProfileImageRepository getInstance() {
        return INSTANCE;
    }


    public Image getProfileImage( String url ){
        Image image = imageCache.get( url );
        if( image == null ){
            image = new Image(url, true );
            imageCache.put(url,image);
        }

        return image;
    }
}
