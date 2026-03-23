import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainController {
    @FXML Pane rootPane;
    @FXML Pane mainPane;
    @FXML SplitPane addMaterialPane;
    @FXML Pane learnPane;
    @FXML VBox contextMenu;

//  =============Buttons==========

    @FXML Button startButton;
    @FXML Button addMaterialButton;
    @FXML Button backButton1;
    @FXML Button backButton;
    @FXML Button contextMenuButton;

//  =============Values==========



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

        backButton.setOnAction(e -> {
            mainPane.setVisible(true);
            addMaterialPane.setVisible(false);
        });

        contextMenuButton.setOnAction(e ->{
            contextMenuButton.setDisable(true);
            sideBarAnim();
        });


        }
    //      ============Anim====================
    private void sideBarAnim() {
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


