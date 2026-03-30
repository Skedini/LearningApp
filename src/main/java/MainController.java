import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.awt.event.ActionListener;

public class MainController {
    @FXML Pane rootPane;
    @FXML Pane mainPane;
    @FXML SplitPane addMaterialPane;
    @FXML Pane learnPane;
    @FXML VBox contextMenu;
    @FXML HBox hBoxEasy;
    @FXML HBox hBoxMedium;
    @FXML HBox hBoxHard;

//  =============Buttons==========

    @FXML Button startButton;
    @FXML Button addMaterialButton;
    @FXML Button backButton1;
    @FXML Button backButton;
    @FXML Button contextMenuButton;
    @FXML Button contextMenuEasy;
    @FXML Button contextMenuMedium;
    @FXML Button contextMenuHard;

//  =============Values==========


//  ========== Main Tab =========
    @FXML
    private void initialize(){
        startButton.setOnAction(e -> {
            mainPane.setVisible(false);
            learnPane.setVisible(true);
        });

        addMaterialButton.setOnAction(e -> {
            mainPane.setVisible(false);
            addMaterialPane.setVisible(true);
        });

        backButton1.setOnAction(e -> {
            mainPane.setVisible(true);
            learnPane.setVisible(false);
        });

//  ================= Add Material Pane ===============

        backButton.setOnAction(e -> {
            mainPane.setVisible(true);
            addMaterialPane.setVisible(false);
        });



//  ================= Learning Pane ==================

        contextMenuButton.setOnAction(e ->{
            sideBarAnim();
        });

        contextMenuEasy.setOnAction(e ->{
            hBoxEasy.setVisible(true);
            hBoxMedium.setVisible(false);
            hBoxHard.setVisible(false);
            hBoxEasy.setDisable(false);
            hBoxMedium.setDisable(true);
            hBoxHard.setDisable(true);
        });

        contextMenuMedium.setOnAction(e ->{
            hBoxEasy.setVisible(false);
            hBoxMedium.setVisible(true);
            hBoxHard.setVisible(false);
            hBoxEasy.setDisable(true);
            hBoxMedium.setDisable(false);
            hBoxHard.setDisable(true);
        });

        contextMenuHard.setOnAction(e ->{
            hBoxEasy.setVisible(false);
            hBoxMedium.setVisible(false);
            hBoxHard.setVisible(true);
            hBoxEasy.setDisable(true);
            hBoxMedium.setDisable(true);
            hBoxHard.setDisable(false);
        });

        hBoxMedium.setOnKeyPressed(e ->{
           if (e.getCode() == KeyCode.ENTER){
               backButton1.fire();
           }
        });

        hBoxHard.setOnKeyPressed(e ->{
            if (e.getCode() == KeyCode.ENTER){
                backButton1.fire();
            }
        });

        }
    //      ============Anim====================
    private void sideBarAnim() {
        contextMenuButton.setDisable(true);
        TranslateTransition menuTransition = new TranslateTransition(Duration.millis(200), contextMenu);
        TranslateTransition btnMove = new TranslateTransition(Duration.millis(200), contextMenuButton);
        RotateTransition btnRotation = new RotateTransition(Duration.millis(200), contextMenuButton);

        btnRotation.setAxis(new Point3D(0, 0, 1));

        if(!contextMenu.isVisible()){
            
            contextMenuButton.setText("X");
            contextMenu.setVisible(true);
            contextMenu.setLayoutX(600);
            menuTransition.setByX(-130);
            btnMove.setByX(-130);

            btnRotation.setFromAngle(360);
            btnRotation.setToAngle(0);

            menuTransition.setOnFinished(e->{
                contextMenuButton.setDisable(false);
            });
        }
        else {
            contextMenuButton.setText("☰");
            menuTransition.setByX(130);
            btnMove.setByX(130);

            btnRotation.setFromAngle(0);
            btnRotation.setToAngle(360);

            menuTransition.setOnFinished(e->{
                contextMenuButton.setDisable(false);
                contextMenu.setVisible(false);
            });
        }

        ParallelTransition btnAnim = new ParallelTransition(btnMove, btnRotation);

        btnAnim.play();
        menuTransition.play();
    }

    }


