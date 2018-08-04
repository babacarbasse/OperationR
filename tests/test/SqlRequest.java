/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Babacar Basse
 * @date Samedi 4/08/2018
 * @Decription class SqlRequest qui va contenir l'ensemble de nos requests vers la base de donnée testRail
 */
public class SqlRequest {
 
    private ConnexionDB accessDB;
    private PreparedStatement st = null;
    private ResultSet rs = null;
    public SqlRequest() {
        this.accessDB = new ConnexionDB();
    }
    
    /**
     * @description cette méthode permettra d'ajouter une section à notre projet testRail
     * 
     * @param sectionName nom de la section
     * @param idSuite id de la suite en rapport avec le projet
     * @param order ordre affichage de la section
     * @param description description de la section
     * @return id de la section à ajouter ou à récupérer
     */
    public int addSection(String sectionName, int idSuite, int order, String description) {
        int idSection = this.checkSectionExist(sectionName, idSuite);
        if (idSection != -1) {
            return idSection;
        }
        try {
           String sql = "INSERT INTO sections(suite_id, name, display_order, is_copy, depth, description) values (?, ?, ?, ?, ?, ?)";
           st = this.accessDB.getConn().prepareStatement(sql);
           st.setInt(1, idSuite);
           st.setString(2, sectionName);
           st.setInt(3, order);
           st.setInt(4, 0);
           st.setInt(5, 0);
           st.setString(6, description);
           st.executeUpdate();
           return this.checkSectionExist(sectionName, idSuite);
        } catch(SQLException e) {
            System.out.println("!!! " + e.toString());
            return -1;
        }
    }
     
    /**
     * @description cette méthode permettra de vérifier si une section existe déjà ou pas avant de l'ajouter
     * @param sectionName nom de la section 
     * @param suitesId id de la suite en rapport avec le projet
     * @return id de la section si ca existe sinon -1
     */
    public int checkSectionExist(String sectionName, int suitesId) {
        try {
            String sql = "SELECT id from sections WHERE name=? AND suite_id = ?";
            st = this.accessDB.getConn().prepareStatement(sql);
            st.setString(1, sectionName);
            st.setInt(2, suitesId);
            this.rs = st.executeQuery();
            if (this.rs.next()) {
                return rs.getInt("id");
            }
            return -1;
         } catch(SQLException e) {
            System.out.println("!!! " + e.toString());
            return -1;
        }
    }
     
    /**
     * @description cette méthode va permettre d'ajouter un case dans notre section
     * @param title titre cas de test
     * @param typeId id du type de test
     * @param sectionId section du cas de test 
     * @param userId id du user effectuant le test
     * @param suiteId id de la suite en relation avec le projet
     * @param orderDisplay ordre d'affichage du cas de test
     * @param templateId type du cas de test (text ou step)
     * @return id du cas de test ajouté ou trouvé
     */
    public int addSectionCase(String title, int typeId, int sectionId, int userId, int suiteId, int orderDisplay, int templateId, String custom_steps_separated) {
        int idCase = this.checkCaseExit(title, sectionId);
        if (idCase != -1) {
            return idCase;
        }
        try {
           Timestamp timestamp = new Timestamp(System.currentTimeMillis());
           String sql = "INSERT INTO cases"
                   + "(title, section_id, display_order, is_copy, priority_id, type_id, user_id, suite_id, updated_by, template_id,"
                   + " updated_on, created_on, custom_steps_separated"
                   + ")"
                   + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
           st = this.accessDB.getConn().prepareStatement(sql);
           st.setString(1, title);
           st.setInt(2, sectionId);
           st.setInt(3, orderDisplay);
           st.setInt(4, 0);
           st.setInt(5, 2);
           st.setInt(6, typeId);
           st.setInt(7, userId);
           st.setInt(8, suiteId);
           st.setInt(9, userId);
           st.setInt(10, templateId);
           st.setInt(11, (int) timestamp.getTime());
           st.setInt(12, (int) timestamp.getTime());
           st.setString(13, custom_steps_separated);
           st.executeUpdate();
           return this.checkSectionExist(title, sectionId);
        } catch(SQLException e) {
            System.out.println("!!! " + e.toString());
            return -1;
        }
    }
    
    /**
     * @description cette méthode permettra de vérifier si un cas de test existe deja dans une section donnée
     * @param title titre du cas
     * @param sectionId section du cas
     * @return id du test case si ca existe sinon -1
     */
    public int checkCaseExit(String title, int sectionId) {
        try {
            String sql = "SELECT id from cases WHERE title=? AND section_id = ?";
            st = this.accessDB.getConn().prepareStatement(sql);
            st.setString(1, title);
            st.setInt(2, sectionId);
            this.rs = st.executeQuery();
            if (this.rs.next()) {
                return rs.getInt("id");
            }
            return -1;
         } catch(SQLException e) {
            System.out.println("!!! " + e.toString());
            return -1;
        }
    }
    
    
    /**
     * @description cette méthode nous permet d'ajouter un testRun dans la bdd testRail
     * @param suite_id suite id relative au projet
     * @param user_id id user effectuant le test
     * @param project_id id du projet
     * @param is_completed  valeur (0 ou 1) pour dire si c'est complet ou pas
     * @param include_all  valeur (0 ou 1) pour dire si c'est tous les cas son inclus ou pas
     * @param name nom du testRun
     * @param description description du testRun
     * @param is_plan  valeur (0 ou 1) pour dire si c'est planifié ou pas
     * @param is_editable valeur (0 ou 1) pour dire si c'est editable ou pas
     * @param updated_by id User qui effectue l'update/ajout
     * @return id du testRun ajouter sinon -1
     */
    public int ajoutTestRun(int suite_id, int user_id, int project_id, int is_completed, int include_all, String name,
            String description, int is_plan, int is_editable, int updated_by
        ) {
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String sql = "INSERT INTO runs ("
                    + "suite_id, user_id, project_id, is_completed, include_all, name, description,"
                    + "is_plan, is_editable, updated_by, created_on, updated_on"
                    + ")"
                    + "values(?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?)";
            st = this.accessDB.getConn().prepareStatement(sql);
            st.setInt(1, suite_id);
            st.setInt(2, user_id);
            st.setInt(3, project_id);
            st.setInt(4, is_completed);
            st.setInt(5, include_all);
            st.setString(6, name);
            st.setString(7, description);
            st.setInt(8, is_plan);
            st.setInt(9, is_editable);
            st.setInt(10, updated_by);
            st.setInt(11, (int) timestamp.getTime());
            st.setInt(12, (int) timestamp.getTime());
            st.executeUpdate();
            return this.getTestRunId(name, project_id);
        } catch(SQLException e) {
           System.out.println("!!! " + e.toString());
           return -1; 
        }
    }
    
    /**
     * @description cette méthode nous retourne un id d'un test run
     * @param name du test run
     * @param project_id id du projet
     * @return id du testrun si existe sinon -1
     */
    public int getTestRunId(String name, int project_id) {
        try {
            String sql = "SELECT id from runs WHERE name=? AND project_id = ?";
            st = this.accessDB.getConn().prepareStatement(sql);
            st.setString(1, name);
            st.setInt(2, project_id);
            this.rs = st.executeQuery();
            if (this.rs.next()) {
                return rs.getInt("id");
            }
            return -1;
         } catch(SQLException e) {
            System.out.println("!!! " + e.toString());
            return -1;
        }
    }
}
