/**
 * 
 */
package application;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Cheikh
 * @date 25/05/2017
 * Classe Résolution : utiliser par la vue pour afficher l'historique
 */
public class Resolution {
	private SimpleStringProperty dte;
	private SimpleStringProperty equation;
	private SimpleStringProperty solution;
	
	Resolution(String d,String eq,String s){
		dte = new SimpleStringProperty(d);
		equation = new SimpleStringProperty(eq);
		solution = new SimpleStringProperty(s);
	}
	public String getDte() {
		return dte.get();
	}
	public String getEqu() {
		return equation.get();
	}
	public String getSolution() {
		return solution.get();
	}
	
	public void setDte(String d) {
		dte.set(d);
	}
	public void setEqu(String eq) {
		 equation.set(eq);
	}
	public void setSolution(String s) {
		solution.set(s);
	}
	
	
}
