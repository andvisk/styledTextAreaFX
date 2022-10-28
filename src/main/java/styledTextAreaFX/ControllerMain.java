package styledTextAreaFX;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ControllerMain {

    private Logger log = LogManager.getLogger(this.getClass());

    private Stage stage;

    private int sceneWidth = 650;
    private int sceneHeight = 350;

    protected void init(Stage stage, StackPane rootElement) {

        this.stage = stage;

        Scene scene = new Scene(rootElement, sceneWidth, sceneHeight);

        StyledTextAreaFX textArea = new StyledTextAreaFX(rootElement, stage.onCloseRequestProperty());

        stage.setScene(scene);
        stage.show();

        stage.setMinWidth(sceneWidth);
        stage.setMinHeight(sceneHeight);

        addText(textArea);
    }

    //todo remove
    private void addText(StyledTextAreaFX textArea){
        Paragraph paragraph = new Paragraph();

        paragraph.addString("Pirmas tekstas Antras tekstas ", "Verdana", 40, Color.CHOCOLATE, textArea);
        paragraph.addString("Trecias tekstas ", "Verdana", 30, Color.DARKSLATEBLUE, textArea);
        paragraph.addString(" Ketvirtas tekstas", "Verdana", 35, Color.DARKSLATEGRAY, textArea);

        Paragraph paragraph2 = new Paragraph();

        paragraph2.addString("Penktas tekstas ", "Verdana", 35, Color.DIMGRAY, textArea);
        paragraph2.addString("Sestas tekstas ", "Verdana", 40, Color.GOLDENROD, textArea);
        paragraph2.addString("Septintas tekstas", "Verdana", 35, Color.MEDIUMSLATEBLUE, textArea);

        textArea.addParagraphs(paragraph, paragraph2);
    }


}