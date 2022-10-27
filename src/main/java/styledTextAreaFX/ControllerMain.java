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

    private int sceneWidth = 450;
    private int sceneHeight = 350;

    protected void init(Stage stage, StackPane rootElement) {

        this.stage = stage;

        Scene scene = new Scene(rootElement, sceneWidth, sceneHeight);

        StyledTextAreaFX textArea = new StyledTextAreaFX(rootElement);

        stage.setScene(scene);
        stage.show();

        stage.setMinWidth(sceneWidth);
        stage.setMinHeight(sceneHeight);

        addText(textArea);
    }

    //todo remove
    private void addText(StyledTextAreaFX textArea){
        Paragraph paragraph = new Paragraph();

        paragraph.addWords(new Word("Pirmas", "Verdana", 40, Color.CHOCOLATE, textArea));
        paragraph.addWords(new Word(" ", "Verdana", 40, Color.CHOCOLATE, textArea));
        paragraph.addWords(new Word("tekstas", "Verdana", 40, Color.CHOCOLATE, textArea));
        paragraph.addWords(new Word(" ", "Verdana", 40, Color.CHOCOLATE, textArea));
        paragraph.addWords(new Word("Antras", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addWords(new Word(" ", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addWords(new Word("tekstas", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addWords(new Word(" ", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addWords(new Word("Trecias", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addWords(new Word(" ", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addWords(new Word("tekstas", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addWords(new Word(" ", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addWords(new Word("Ketvirtas", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addWords(new Word(" ", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addWords(new Word("tekstas", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addWords(new Word(" ", "Verdana", 18, Color.CHOCOLATE, textArea));

        Paragraph paragraph2 = new Paragraph();
        paragraph2.addWords(new Word("Penktas", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph2.addWords(new Word(" ", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph2.addWords(new Word("tekstas", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph2.addWords(new Word(" ", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph2.addWords(new Word("Sestas", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph2.addWords(new Word(" ", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph2.addWords(new Word("tekstas", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph2.addWords(new Word(" ", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph2.addWords(new Word("Septintas", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph2.addWords(new Word(" ", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph2.addWords(new Word("tekstas", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph2.addWords(new Word(" ", "Verdana", 18, Color.CHOCOLATE, textArea));

        textArea.addParagraphs(paragraph, paragraph2);
    }
}