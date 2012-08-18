/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.navalvessel.shimakaze;

import jp.navalvessel.shimakaze.core.ProfileImageRepository;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * @author eguchi
 */
public class StatusCell extends AnchorPane {

    private Label profileImage;
    private Label textLabel;

    public StatusCell() {
        initialize();
    }

    public void initialize() {

        profileImage = LabelBuilder.create()
                .prefWidth( 48 )
                .prefHeight( 48 )
                .build();
        AnchorPane.setTopAnchor( profileImage, 1.0 );
        AnchorPane.setLeftAnchor( profileImage, 2.0 );

        textLabel = new Label();
        textLabel.setAlignment( Pos.TOP_LEFT );
        textLabel.setMinHeight( 50 );
        textLabel.setPrefWidth( 344 );
        textLabel.setWrapText( true );
        AnchorPane.setLeftAnchor( textLabel, 52.0 );
        AnchorPane.setRightAnchor( textLabel, 4.0 );
        AnchorPane.setTopAnchor( textLabel, 1.0 );


        getChildren().addAll( profileImage, textLabel );

        prefHeightProperty().bind( textLabel.heightProperty() );


    }

    public void setProfileImage( String url ) {
        Image image = ProfileImageRepository.getInstance().getProfileImage( url );
        ImageView imageView = new ImageView( image );
        imageView.setFitWidth( 48.0 );
        imageView.setFitHeight( 48.0 );
        imageView.setPreserveRatio( true );
        profileImage.setGraphic( imageView );
    }

    public void setText( String text ) {
        textLabel.setText( text );
    }
}
