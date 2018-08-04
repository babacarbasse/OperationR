/**
 * 
 */
package application;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * @author Cheikh
 * @date 25/05/2017
 * Controleur de la vue WebView
 */
public class WebViewControler {

	@FXML private WebView webView;
	
	 	
	URL url = this.getClass().getResource("help.html");
	
	@FXML private void initialize() {
	    try {
	    	 WebEngine webEngine = webView.getEngine();
			 webEngine.load(url.toExternalForm());
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	
	
}
