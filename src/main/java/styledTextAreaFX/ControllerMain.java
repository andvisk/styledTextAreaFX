package styledTextAreaFX;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
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

        Scene scene = new Scene(rootElement, stageWidth, stageHeight);

        StyledTextAreaFX textArea = new StyledTextAreaFX();

        TextExtended text1 = new TextExtended("Pirmas paragrafas");
        text1.setFont(Font.font ("Verdana", 12));
        text1.setFill(Color.CHOCOLATE);;


        textArea.add(text1);
        textArea.add(new TextExtended("Antras paragrafas"));
        textArea.add(new TextExtended("Trecias paragrafas"));
        textArea.add(new TextExtended("Ketvirtas paragrafas"));

        textArea.getChildren().add(new Caret().getLine());

        rootElement.getChildren().add(textArea);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());


    }

    private double computeTextWidth(Font font, String text, double wrappingWidth) {
        Text helper = new Text();
        helper.setFont(font);
        helper.setText(text);
        // Note that the wrapping width needs to be set to zero before
        // getting the text's real preferred width.
        helper.setWrappingWidth(0);
        helper.setLineSpacing(0);
        double w = Math.min(helper.prefWidth(-1), wrappingWidth);
        helper.setWrappingWidth((int) Math.ceil(w));
        double textWidth = Math.ceil(helper.getLayoutBounds().getWidth());
        return textWidth;
    }
}