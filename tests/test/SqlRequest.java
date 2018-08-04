/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 * @author Babacar Basse
 * @date Samedi 4/08/2018
 * @Decription class SqlRequest qui va contenir l'ensemble de nos requests vers la base de donnée testRail
 */
public class SqlRequest {
 
    private ConnexionDB connexion;
    
    public SqlRequest() {
        this.connexion = new ConnexionDB();
    }
}
