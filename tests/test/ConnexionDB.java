/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

//ajout de java sql pour la base de donnée testRail
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * @author Babacar Basse
 * @date Samedi 4/08/2018
 * @Decription class ConnexionDB pour la connexion à la base de donnée testRail
 */
public class ConnexionDB {
 
    private Connection conn = null;
    public ConnexionDB() {
        try {
            conn =
               DriverManager.getConnection("jdbc:mysql://localhost/testeRail?" +
                    "user=root&password=eagledev");
            System.out.println("Connexion ok");
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    
    
    public Connection getConn() {
        return this.conn;
    }
    
}
