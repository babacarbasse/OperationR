/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Babacar Basse
 * @date Samedi 4/08/2018
 * @Decription class SqlRequest qui va contenir l'ensemble de nos requests vers la base de donnée testRail
 */
public class SqlRequest {
 
    private ConnexionDB accessDB;
    private PreparedStatement st = null;
    private ResultSet rs = null;
    private ProjetTestRail projet;
    public SqlRequest(String projetName) {
        this.accessDB = new ConnexionDB();
        this.projet = new ProjetTestRail(projetName);
    }
    
     public Boolean addSection(String sectionName) {
        if (this.checkSectionExist(sectionName)) {
            System.out.println("already exits");
            return true;
        }
        return true;
        /*
        try {
            String sql = "INSERT INTO Agence(nomAg, adresseAg) values (?,?)";
            st = this.accessDB.getConn().prepareStatement(sql);
            st.setString(1, a.getNomAg());
            st.setString(2, a.getAdresseAg());
            st.executeUpdate();
            return true;
        } catch(SQLException e) {
            System.out.println("!!! " + e.toString());
            return false;
        }*/
    }
     
     public Boolean checkSectionExist(String sectionName) {
        try {
             String sql = "SELECT count(*) from sections WHERE name=? AND suite_id = ?";
             st = this.accessDB.getConn().prepareStatement(sql);
             st.setString(1, sectionName);
             st.setInt(2, this.projet.suitesId);
             this.rs = st.executeQuery();
             if (this.rs.getRow() != 0) {
                 return true;
             }
             return false;
         } catch(SQLException e) {
            System.out.println("!!! " + e.toString());
            return false;
        }
    }
    
}
