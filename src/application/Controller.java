package application;

import java.io.IOException;
import java.lang.Math.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Date;
import java.util.regex.*;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import java.sql.ResultSet;

/**
 * @author Cheikh
 * @date 25/05/2017
 *  Classe Controller qui se charge de la logique metier de l'application
 *  
 */
public class Controller {
	
	Date date ;
	
	private Model model;
	private int a,b,c;
	private String regex = "^-?\\d{1,}$"; //Regex permettant de vérifier si l'user à entrer un entier
	private Pattern motif ;              //le regex est compilté et utilisé via un objet de type Pattern
	private String defaultLng = "fr"; //langage par défaut
	//TextField
	@FXML
	private TextField firstOp; 
	@FXML
	private TextField secondOp;
	@FXML
	private TextField thirdOp;
	@FXML
	private TextArea resultField;
	
	//Control Button
	@FXML
	private Button submitButton;
	@FXML
	private Button translateButton;
	@FXML
	private Button helpButton;
	@FXML
	private Button lngButton;
	@FXML
	private Button clearButton;
	
	//MenuItem
	@FXML
	private MenuItem historique;
	@FXML
	private MenuItem help;
	
	//Label
	@FXML private Label inputLabel;
	@FXML private Label resultLabel;
	
	
	
	@FXML private AnchorPane main;
	
	Controller(){
		date = new Date();
		motif = Pattern.compile(regex);
		model = Model.getInstance();
	}
	 /**
	  * Event Handler du click sur le boutton Resoudre
	 * @throws SQLException 
	  */
	@FXML
	public void handleSubmitButton() throws SQLException {
	    Window owner = submitButton.getScene().getWindow();
		Matcher fOp = motif.matcher(firstOp.getText());
		Matcher sndOp = motif.matcher(secondOp.getText());
		Matcher thdOp = motif.matcher(thirdOp.getText());
		
		if(fOp.find() && sndOp.find() && thdOp.find()){
			try {
				a = Integer.parseInt(firstOp.getText());
				 if(a==0) {
					 AlertModal.showAlert(Alert.AlertType.ERROR, owner, "Form Error!","la valeur de a ne doit pas être nulle");
				 }
				b = Integer.parseInt(secondOp.getText());
				c = Integer.parseInt(thirdOp.getText());
			}catch (Exception e){
				System.out.println(e.getMessage());
		        AlertModal.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",e.getMessage());
		        return;
			}
			resolution(a,b,c);
		}else {
			 AlertModal.showAlert(Alert.AlertType.ERROR, owner, "Form Error!","Veuiller saisir des nombres ou vérifier que tous les champs sont remplis");
			 return;
		}
	}
	 /**
	  * Event Handler du click sur le boutton FR/ENG
	  * Se charge de changer la langue 
	  */
	
	@FXML
	public void handleTranslateButton(ActionEvent event){
		
		if(lngButton.getText().equals("FR")) {
			inputLabel.setText("Enter the value of a,b and c :");
			resultLabel.setText("Result");
			submitButton.setText("Resolve");
			lngButton.setText("ENG");
			clearButton.setText("Clear");
		}else{
			lngButton.setText("FR");
			inputLabel.setText("Entrer les valeurs de a,b et c");
			resultLabel.setText("Résultat");
			submitButton.setText("Résoudre");
			clearButton.setText("Effacer");
		}
		   
	}
	
	/**
	  * Event Handler du click sur le boutton History
	  * Ouvre une nouvelle fenêtre et lui associe un controller
	 * @throws SQLException 
	  */
	@FXML
	public void handleHistoryClick(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TableView.fxml"));
		loader.setController(new HistoryControler());
		Stage primaryStage = new Stage();
		Parent root = loader.load();
		primaryStage.setTitle("Application de resolution équation 2nd degré dans R");
		primaryStage.setScene(new Scene(root,840,600));
		primaryStage.show();
	}
	/**
	 * Ouvre une fenêtre d'aide lorsqu'on click sur le boutton help
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void handleHelpClick(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HelpView.fxml"));
		loader.setController(new WebViewControler());
		Stage primaryStage = new Stage();
		Parent root = loader.load();
		primaryStage.setTitle("Help");
		primaryStage.setScene(new Scene(root,840,600));
		primaryStage.show();
	}
	/**
	 * Efface les différents
	 * @param event
	 */
	@FXML
	public void handleClearButton(ActionEvent event) {
		firstOp.setText(" ");; 
		secondOp.setText("0");;
		thirdOp.setText("0");;
		resultField.setText("");;
	}
	
	/**
	 * Méthode qui se charge de résoudre une équation du 2nd degré
	 * @param a | b | c : int
	 * ********/
	
	
	public void resolution(int a,int b, int c){
		double delta,x1,x2,x0;
		
		delta =b*b - 4*a*c;
		
		if(delta<0){
			 if(lngButton.getText().equals("ENG")){
				 
				 resultField.setText("There is no solutions in R");
				 return;
			 }	 
			  resultField.setText("Le système n'admet pas de solutions dans R");
			  model.insertStatement(a+"x²"+"+("+b+")"+"x"+"+("+c+")","Le système n'admet pas de solutions dans R", new Date());
			 return;
		}
		
		if(delta==0) {
			x0 = (-b)/(double)(2*a);
			 if(lngButton.getText().equals("ENG")) {
				 resultField.setText("Double solution x0 :"+x0+"S = {"+x0+"}");
				 return;
			 }		
			 resultField.setText("Le système admet une solution double x0 :"+x0+" S = {"+x0+"}");
			 model.insertStatement(a+"x²"+"+("+b+")"+"x"+"+("+c+")","Le système admet une solution double x0 :"+x0+" S = {"+x0+"}", new Date());
			return;
		}
		
		if(delta>0) {
			 x1 = (-b - Math.sqrt(delta)) / (2*a) ;
		     x2 = (-b + Math.sqrt(delta)) / (2*a); 
		     if(lngButton.getText().equals("ENG")) {
		    	 resultField.setText("Solutions are x1: "+x1+" x2: "+x2+" S = {"+x1+" , "+x2+"}");
				 return;
			 }
		     resultField.setText("Les solutions sont x1: "+x1+" x2: "+x2+" S = {"+x1+" , "+x2+"}");
		     model.insertStatement(a+"x²"+"+("+b+")"+"x"+"+("+c+")","Les solutions sont x1: "+x1+" x2: "+x2+" S = {"+x1+" , "+x2+"}", new Date());
		     return;
		     
		}
	}
	
	
	
}
