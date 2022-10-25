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

        TextExtended text1 = new TextExtended("Pirmas tekstas", textArea);
        text1.setFont(Font.font ("Verdana", 40));
        text1.setFill(Color.CHOCOLATE);;

        paragraph.addTexts(text1);
        paragraph.addTexts(new TextExtended("Antras tekstas", textArea));
        paragraph.addTexts(new TextExtended("Trecias tekstas", textArea));
        paragraph.addTexts(new TextExtended("Ketvirtas tekstas", textArea));

        Paragraph paragraph2 = new Paragraph();
        paragraph2.addTexts(new TextExtended("Penktas tekstas", textArea));
        paragraph2.addTexts(new TextExtended("Sestas tekstas", textArea));
        paragraph2.addTexts(new TextExtended("Septintas tekstas", textArea));

        textArea.addParagraphs(paragraph, paragraph2);
    }
}