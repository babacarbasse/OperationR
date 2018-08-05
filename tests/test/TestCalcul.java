package test;

import application.*;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
//import javaFx Test
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import static org.hamcrest.CoreMatchers.is;
import org.loadui.testfx.GuiTest;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;


/**
 * @author Babacar Basse
 * @date Samedi 4/08/2018
 * @Decription class TestCalcul pour les tests de calcul
 */
public class TestCalcul extends ApplicationTest{
    private int done = 0;
    //Attribut pour faire les requests sql dans la base de donnée de testRail
    private SqlRequest requestSql;
    
    private final String sectionName = "Calcul";
    private ProjetTestRail projet;
    private int idSection;
    private int idCase;
    private String caseName = "Test calcul";
    private int idTestRun = -1;
    private String descriptionTestRun = "Test run pour les tests de calcul";
    private String nameTestRun = "Test run: " + new java.util.Date();
    private final String custom_steps_separated = "["
            + "{\"content\":\"test calcul simple\",\"expected\":\"affichage d'un résultat quelconque\"},"
            + "{\"content\":\"Test calcul correct avec l'équation -x^2 + 3x - 2\","
            + "\"expected\":\"Résultat à afficher: Les solutions sont x1: 2.0 x2: 1.0 S = {2.0 , 1.0}\"},"
            + "{\"content\":\"Test calcul en donnant des lettres au lieu de valeur numérique\","
            + "\"expected\":\"Rien ne doit s'afficher dans la zone de résultat\"}"
            + "]";
    @Override
    public void start (Stage stage) throws Exception {
        if (this.done == 0) {
            this.initTestRailBD();
        }
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainScene.fxml"));
        loader.setController(new Controller());
        Parent mainNode = loader.load();
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }
    
    public void initTestRailBD() {
        this.done = 1;
        this.projet = new ProjetTestRail("OperationR test");
        this.requestSql = new SqlRequest();
        this.idSection = this.requestSql.addSection(sectionName, this.projet.suitesId, 3, "Section de test des calculs du programme.");
        if (this.idSection != -1) {
            this.idCase = this.requestSql.addSectionCase(caseName, 7,this.idSection, 1, this.projet.suitesId, 1, 2, custom_steps_separated);
        } else {
            this.idCase = this.requestSql.checkCaseExit(caseName, idSection);
        }
        if (this.requestSql.checkTestRunExit(this.projet.id, descriptionTestRun) == -1) {
            if (this.idTestRun == -1) {
                this.idTestRun = this.requestSql.addRun(this.projet.suitesId, 1, this.projet.id, 0, 0, nameTestRun, descriptionTestRun, 0, 1, 1, 1, this.projet.id);
            }
            this.requestSql.addTest(this.idTestRun, this.idCase, 1, 0, 0, this.idCase, 1);
        }
    }
    
    public TestCalcul() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    /**
     * @description testCase calculSimple pour vérifier l'affichage d'un quelconque résultat
     * @throws Exception 
     */
    @Test
    public void testCalculSimple() throws Exception {
        clearInput("#firstOp");
        clearInput("#secondOp");
        clearInput("#thirdOp");
        clickOn("#firstOp");
        write("2");
        clickOn("#secondOp");
        write("1");
        clickOn("#thirdOp");
        write("0");
        // TimeUnit.SECONDS.sleep(5);
        clickOn("#submitButton");
        // TimeUnit.SECONDS.sleep(10);
    }
    

    /**
     * @description testCase testCalculSolutionCorrect, test d'un calcul afin de verifier la solution correct ou pas
     * @throws Exception 
     */
    @Test
    public void testCalculSolutionCorrect() throws Exception {
        TextArea txtResult = (TextArea) GuiTest.find("#resultField");
        clearInput("#firstOp");
        clearInput("#secondOp");
        clearInput("#thirdOp");
        clickOn("#firstOp");
        write("-1");
        clickOn("#secondOp");
        write("3");
        clickOn("#thirdOp");
        write("-2");
        TimeUnit.SECONDS.sleep(5);
        clickOn("#submitButton");
        TimeUnit.SECONDS.sleep(5);
        assertThat(txtResult.getText(), is("Les solutions sont x1: 2.0 x2: 1.0 S = {2.0 , 1.0}"));
    }
    
    @Test
    public void testCalculNonNombre() throws Exception {
     TextArea txtResult = (TextArea) GuiTest.find("#resultField");
        clearInput("#firstOp");
        clearInput("#secondOp");
        clearInput("#thirdOp");
        clickOn("#firstOp");
        write("-a");
        clickOn("#secondOp");
        write("b");
        clickOn("#thirdOp");
        write("-c");
        clickOn("#submitButton");
        assertThat(txtResult.getText(), is(""));   
    }
    
    
    public void clearInput(String inputName) {
        TextField textField = (TextField) GuiTest.find(inputName);
        textField.clear();
    }
}
