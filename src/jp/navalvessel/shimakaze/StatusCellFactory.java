/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.navalvessel.shimakaze;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import twitter4j.Status;

/**
 *
 * @author eguchi
 */
public class StatusCellFactory implements Callback<ListView<Status>, ListCell<Status>> {

    @Override
    public ListCell<Status> call(ListView<Status> p) {
        StatusCellBase cell = new StatusCellBase();
        
        return cell;
    }
    
}
