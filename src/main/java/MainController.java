import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {
    @FXML TextField subjectName;
    @FXML Pane rootPane;
    @FXML Pane mainPane;
    @FXML SplitPane addMaterialPane;
    @FXML SplitPane selectionSplitPane;
    @FXML Pane learnPane;
    @FXML VBox contextMenu;
    @FXML VBox selectionSubjectVBox;
    @FXML VBox selectionWordsVBox;
    @FXML HBox hBoxEasy;
    @FXML HBox hBoxMedium;
    @FXML HBox hBoxHard;
    @FXML VBox wordVBox;

    SQLiteController sql = new SQLiteController();

//  =============Buttons==========

    @FXML Button startButton;
    @FXML Button addMaterialButton;
    @FXML Button addWordButton;
    @FXML Button saveSubjectButton;
    @FXML Button backButton1;
    @FXML Button backButton;
    @FXML Button contextMenuButton;
    @FXML Button contextMenuEasy;
    @FXML Button contextMenuMedium;
    @FXML Button contextMenuHard;
    @FXML Button selectionAcceptBtn;
    @FXML Button selectionClearBtn;

    public MainController() throws SQLException {
    }

//  =============Values==========
    Map<String, CheckBox> checkBoxes = new HashMap<>();

//  ========== Main Tab =========
    @FXML
    private void initialize(){
        startButton.setOnAction(e -> {
            ArrayList<String> subjectList;
            try {
                subjectList = sql.getSubjects();
            }catch (SQLException e2){
                throw new RuntimeException(e2);
            }

            mainPane.setVisible(false);
            selectionSplitPane.setVisible(true);
            if(subjectList.isEmpty()){
                System.out.println("Coś jest zjebane frajerze");           }

            for (String subject : subjectList){
                HBox hBox = new HBox(10);
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(0,0,20,0));
                Label label = new Label(subject);
                CheckBox checkBox = new CheckBox();
                checkBox.setSelected(false);
                checkBoxes.put(subject, checkBox);
                hBox.getChildren().addAll(label, checkBox);
                selectionSubjectVBox.getChildren().add(hBox);
            }
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

        saveSubjectButton.setOnAction(e -> {

            List<Word> wordList = getWords();

            if (wordList.isEmpty() || subjectName.getText().isEmpty()) {
                return;
            }
            System.out.println(subjectName.getText());
            for(Word word : wordList){
                System.out.println(word.getPronoun() + " " + word.getWord() + " " + word.getTranslation());
            }

            try {
                sql.addSubject(wordList, (String) subjectName.getText());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        addWordButton.setOnAction(e -> {
            HBox newWordHBox = new HBox(10);
            wordVBox.setMargin(newWordHBox, new Insets(5,0,5,5));


            TextField pronoun = new TextField();
            pronoun.setPromptText("Enter pronoun");

            TextField word = new TextField();
            word.setPromptText("Enter word");

            TextField translation = new TextField();
            translation.setPromptText("Enter translation");

            Button deleteSelfBtn = new Button("-");

            pronoun.setOnKeyPressed(e1 ->{
                if (e1.getCode() == KeyCode.ENTER){
                    word.requestFocus();
                }
                else if (e1.getCode() == KeyCode.BACK_SPACE && pronoun.getText().isEmpty()){
                    int index = Math.max(0, wordVBox.getChildren().indexOf(newWordHBox) - 1);
                    HBox targethbox = (HBox) wordVBox.getChildren().get(index);
                    targethbox.getChildren().get(2).requestFocus();
                    deleteSelfBtn.fire();
                }
            });
            word.setOnKeyPressed(e2 ->{
                if (e2.getCode() == KeyCode.ENTER){
                    translation.requestFocus();
                }
                else if (e2.getCode() == KeyCode.BACK_SPACE && word.getText().isEmpty()){
                    pronoun.requestFocus();
                }
            });
            translation.setOnKeyPressed(e2 ->{
                if (e2.getCode() == KeyCode.ENTER){
                    addWordButton.fire();
                }
                else if (e2.getCode() == KeyCode.BACK_SPACE && translation.getText().isEmpty()){
                    word.requestFocus();
                }
            });

            deleteSelfBtn.setOnAction(e4 ->{
                wordVBox.getChildren().remove(newWordHBox);
            });

            newWordHBox.getChildren().addAll(pronoun, word, translation, deleteSelfBtn);
            wordVBox.getChildren().add(newWordHBox);
            pronoun.requestFocus();
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

    //      ============Functions====================
    private List<Word> getWords(){
        int index = 1;
        List<Word> words = new ArrayList<>();
        for(Node wordNode : wordVBox.getChildren()){
            HBox hBox = (HBox) wordNode;

            TextField pronounField = (TextField) hBox.getChildren().get(0);
            TextField wordField = (TextField) hBox.getChildren().get(1);
            TextField translationField = (TextField) hBox.getChildren().get(2);

            if(wordField.getText().isEmpty() || translationField.getText().isEmpty()) {
                System.out.println("Unable to get words from row " + index + " becouse it's blank");
                index++;
                continue;
            }

            String pronoun = pronounField.getText();
            String word = wordField.getText();
            String translation = translationField.getText();

            Word w = new Word(pronoun, word, translation);

            words.add(w);
            index++;
        };
        return words;
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


