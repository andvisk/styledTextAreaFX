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

    private int stageWidth = 450;
    private int stageHeight = 300;

    protected void init(Stage stage, StackPane rootElement) {

        this.stage = stage;

        rootElement.setPrefSize(stageWidth, stageHeight);

        Scene scene = new Scene(rootElement, stageWidth, stageHeight);

        StyledTextAreaFX textArea = new StyledTextAreaFX(rootElement);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());

        Paragraph paragraph = new Paragraph();

        paragraph.addTexts(new TextExtended("Pirmas tekstas", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addTexts(new TextExtended(" ", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addTexts(new TextExtended("Antras tekstas", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addTexts(new TextExtended(" ", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addTexts(new TextExtended("Trecias tekstas", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addTexts(new TextExtended(" ", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph.addTexts(new TextExtended("Ketvirtas tekstas", "Verdana", 18, Color.CHOCOLATE, textArea));

        Paragraph paragraph2 = new Paragraph();
        paragraph2.addTexts(new TextExtended("Penktas tekstas", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph2.addTexts(new TextExtended(" ", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph2.addTexts(new TextExtended("Sestas tekstas", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph2.addTexts(new TextExtended(" ", "Verdana", 18, Color.CHOCOLATE, textArea));
        paragraph2.addTexts(new TextExtended("Septintas tekstas", "Verdana", 18, Color.CHOCOLATE, textArea));

        textArea.addParagraphs(paragraph, paragraph2);
    }
}