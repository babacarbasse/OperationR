/**
 * @author Babacar Basse
 * @date Samedi 4/08/2018
 * @Decription class TestCalcul pour les tests de calcul
 */

package test;

import application.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 *
 * @author basse
 */
public class TestCalcul extends ApplicationTest{
        
    @Override
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainScene.fxml"));
        loader.setController(new Controller());
        Parent mainNode = loader.load();
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
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
        TimeUnit.SECONDS.sleep(5);
        clickOn("#submitButton");
        TimeUnit.SECONDS.sleep(10);
    }
    

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
    
    
    public void clearInput(String inputName) {
        TextField textField = (TextField) GuiTest.find(inputName);
        textField.clear();
    }
}
