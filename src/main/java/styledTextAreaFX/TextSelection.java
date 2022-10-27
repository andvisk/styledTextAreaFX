package styledTextAreaFX;

import java.util.ArrayList;
import java.util.List;

public class TextSelection {
    private List<Paragraph> allParagraphs;
    private List<Paragraph> selectedParagraphList;
    private List<TextExtended> selectedTextList;
    private PathIndex startSelection;
    private PathIndex endSelection;


    public TextSelection(List<Paragraph> allParagraphs, PathIndex nearestPathIndex1, PathIndex nearestPathIndex2) {

        this.allParagraphs = allParagraphs;
        Paragraph startParagraph = null;
        Paragraph endParagraph = null;

        //if first paragraph is above the second
        if (nearestPathIndex1.getText().getParagraph().getBoundsInParent().getMinY()
                < nearestPathIndex2.getText().getParagraph().getBoundsInParent().getMinY()) {
            startParagraph = nearestPathIndex1.getText().getParagraph();
            endParagraph = nearestPathIndex2.getText().getParagraph();
            startSelection = nearestPathIndex1;
            endSelection = nearestPathIndex2;
        } else {
            startParagraph = nearestPathIndex2.getText().getParagraph();
            endParagraph = nearestPathIndex1.getText().getParagraph();
            startSelection = nearestPathIndex2;
            endSelection = nearestPathIndex1;
        }

        //same paragraph
        if (startParagraph.getUuid().compareTo(endParagraph.getUuid()) == 0) {
            //same text
            if (nearestPathIndex1.getText().getUuid().compareTo(nearestPathIndex2.getText().getUuid())==0) {
                if(nearestPathIndex1.getNearestIndex() < nearestPathIndex2.getNearestIndex()){
                    startSelection = nearestPathIndex1;
                    endSelection = nearestPathIndex2;
                }else{
                    startSelection = nearestPathIndex2;
                    endSelection = nearestPathIndex1;
                }
            } else {
                //compare text indexes in paragraph
                if(nearestPathIndex1.getText().getIndexInParagraph() < nearestPathIndex2.getText().getIndexInParagraph()){
                    startSelection = nearestPathIndex1;
                    endSelection = nearestPathIndex2;
                }else{
                    startSelection = nearestPathIndex2;
                    endSelection = nearestPathIndex1;
                }
            }
        } // else we already set by paragraphs Y coord

        collectSelectedParagraphs(allParagraphs, startParagraph, endParagraph);
        collectSelectedTexts();
        deselectTexts(allParagraphs);
        selectTexts();
    }

    public static void deselectTexts(List<Paragraph> allParagraphs) {
        allParagraphs.stream().flatMap(p -> p.getListText().stream()).forEach(p -> {
            p.setSelectionStart(0);
            p.setSelectionEnd(0);
        });
    }

    private void selectTexts() {
        for (int i = 0; i < selectedTextList.size(); i++) {
            TextExtended text = selectedTextList.get(i);

            if (i == 0 || i == selectedTextList.size() - 1) {
                if (startSelection.getText().getUuid().compareTo(endSelection.getText().getUuid()) == 0) {
                    text.selectText(startSelection.getNearestIndex(), endSelection.getNearestIndex());
                } else {
                    if (i == 0) {
                        text.selectText(startSelection.getNearestIndex(), text.getText().length());
                    }
                    if (i == selectedTextList.size() - 1) {
                        text.selectText(0, endSelection.getNearestIndex());
                    }
                }
            } else {
                text.selectText(0, text.getText().length());
            }

        }
    }

    private void collectSelectedTexts() {
        selectedTextList = new ArrayList<>();
        boolean add = false;
        boolean foundEnd = false;
        for (int i = 0; i < selectedParagraphList.size(); i++) {
            List<TextExtended> textsFromPar = selectedParagraphList.get(i).getListText();
            for (TextExtended text : textsFromPar) {
                if (startSelection.getText().getUuid().compareTo(text.getUuid()) == 0) {
                    add = true;
                }
                if (add)
                    selectedTextList.add(text);
                if (endSelection.getText().getUuid().compareTo(text.getUuid()) == 0) {
                    foundEnd = true;
                    break;
                }
            }
            if (foundEnd)
                break;
        }
    }

    private void collectSelectedParagraphs(List<Paragraph> allParagraphs, Paragraph startParagraph, Paragraph endParagraph) {
        selectedParagraphList = new ArrayList<>();
        boolean add = false;
        for (int i = 0; i < allParagraphs.size(); i++) {
            if (allParagraphs.get(i).getUuid().compareTo(startParagraph.getUuid()) == 0) {
                add = true;
            }
            if (add)
                selectedParagraphList.add(allParagraphs.get(i));
            if (allParagraphs.get(i).getUuid().compareTo(endParagraph.getUuid()) == 0) {
                break;
            }
        }
    }

    public List<TextExtended> getSelectedTextList() {
        return selectedTextList;
    }
}
