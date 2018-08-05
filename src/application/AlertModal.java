/**
 * 
 */
package application;

import javafx.scene.control.Alert;
import javafx.stage.Window;

/**
 * @author Cheikh
 * @date 25/05/2017
 * Modal pour afficher des informations
 */
public class AlertModal {
	
    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
