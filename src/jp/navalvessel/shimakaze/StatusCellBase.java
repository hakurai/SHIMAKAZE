/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.navalvessel.shimakaze;

import javafx.scene.control.ListCell;
import twitter4j.Status;

/**
 * @author eguchi
 */
public class StatusCellBase extends ListCell<Status> {

    public StatusCellBase() {
        setPrefWidth( 0 );
    }

    @Override
    protected void updateItem( Status t, boolean bln ) {
        super.updateItem( t, bln );
        if ( !bln ) {
            StatusCell statusCell = new StatusCell();

            statusCell.setProfileImage( t.getUser().getProfileImageURL().toExternalForm() );
            statusCell.setText( t.getText() );

            setGraphic( statusCell );

        }
    }
}
