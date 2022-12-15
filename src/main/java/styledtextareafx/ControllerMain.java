package styledtextareafx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ControllerMain {

    private Logger log = LogManager.getLogger(this.getClass());

    private int sceneWidth = 650;
    private int sceneHeight = 350;

    protected void init(Stage stage, StackPane rootElement) {

        Scene scene = new Scene(rootElement, sceneWidth, sceneHeight);

        StyledTextAreaFX textArea = new StyledTextAreaFX(rootElement, stage);

        stage.setScene(scene);
        stage.show();

        stage.setMinWidth(sceneWidth);
        stage.setMinHeight(sceneHeight);

        addText(textArea);
    }

    //todo remove
    private void addText(StyledTextAreaFX textArea){
        Paragraph paragraph = new Paragraph();

        paragraph.addString("Pirmas tekstas1 Antras tekstas2 ", "Verdana", 40, Color.CHOCOLATE, textArea);
        paragraph.addString("Trecias tekstas3 ", "Verdana", 30, Color.DARKSLATEBLUE, textArea);
        paragraph.addString(" Ketvirtas tekstas4", "Verdana", 35, Color.DARKSLATEGRAY, textArea);

        Paragraph paragraph2 = new Paragraph();

        paragraph2.addString("Penktas tekstas5 ", "Verdana", 35, Color.DIMGRAY, textArea);
        paragraph2.addString("Sestas tekstas6 ", "Verdana", 40, Color.GOLDENROD, textArea);
        paragraph2.addString("Septintas tekstas7", "Verdana", 35, Color.MEDIUMSLATEBLUE, textArea);

        textArea.addParagraphs(paragraph, paragraph2);
    }


}
