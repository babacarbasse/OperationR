/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author basse
 * @description class ProjetTestRail qui va contenir les infos de notre projet sur testRail
 */
public final class ProjetTestRail {
 
    public String name;
    public int id;
    public int suitesId;
    
    private ConnexionDB conn;
    private PreparedStatement st = null;
    private ResultSet rs = null;
    public ProjetTestRail(String projeString) {
        this.name = projeString;
        this.conn = new ConnexionDB();
        this.getProjetProperties();
    }
    
    /**
     * @description récupération des informations de notre projet testRail sur la BDD
     */
    public void getProjetProperties() {
        try {
            String sql = "SELECT * from projects WHERE name=?";
            st = this.conn.getConn().prepareStatement(sql);
            st.setString(1, this.name);
            rs = st.executeQuery();
            if (rs.next()) {
                this.id = rs.getInt("id");
                this.suitesId = rs.getInt("master_id");
            }
        } catch(SQLException e) {
            System.out.println("!!! Error " + e.toString());   
        }
    }
}
