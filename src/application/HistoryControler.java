/**
 * 
 */
package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * @author Cheikh
 * @date 25/05/2017
 * Controleur de la Vue History
 */
public class HistoryControler {
	private Stage stage;
	private Model model =Model.getInstance();
	FXMLLoader loader ;
			
	@FXML private TableView<Resolution> tableview;
	@FXML private TableColumn<Resolution, String> dte;
	@FXML private TableColumn<Resolution, String> equation;
	@FXML private TableColumn<Resolution, String> solution;
	
	private ObservableList<Resolution> tabData = FXCollections.observableArrayList();
	
	
	/**
	 * Methode appelée au démarrage de l'application
	 * @throws SQLException
	 * @throws IOException
	 */
	@FXML private void initialize() throws SQLException, IOException {
	 	
	 dte.setCellValueFactory(new PropertyValueFactory<Resolution,String>("Dte"));	
	 equation.setCellValueFactory(new PropertyValueFactory<Resolution,String>("Equ"));	
	 solution.setCellValueFactory(new PropertyValueFactory<Resolution,String>("Solution"));	
	 
	    ResultSet res; 
		res = model.getHistory();
		while(res.next()) {
			tabData.add(new Resolution(res.getString("dte"),res.getString("equation"),res.getString("solution"))) ;
			
		}
	//
		tableview.setItems(tabData);
		tabData.forEach(tab->{
			System.out.println(tab.getDte()+" "+tab.getSolution()+" "+tab.getEqu());
		});
		
	}
}
