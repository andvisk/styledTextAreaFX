package styledtextareafx;

import javafx.scene.shape.Path;

import java.util.List;

public class PathIndex {

    private double nearestPathX;
    private Path nearestPath;
    private int nearestIndex;
    private TextExtended text;

    public PathIndex(TextExtended text, double x) {
        this.text = text;
        double xToReturn = -1;
        List<Path> paths = text.getPaths();
        nearestIndex = getNearestPathIndex(text, x);
        if (nearestIndex == paths.size()) {
            Path path = paths.get(paths.size() - 1);
            nearestPathX = path.getBoundsInParent().getMinX() + path.getBoundsInLocal().getWidth();
        } else {
            nearestPathX = paths.get(nearestIndex).getBoundsInParent().getMinX();
        }
    }

    //getting last path
    public PathIndex(TextExtended text) {
        this.text = text;
        double xToReturn = -1;
        List<Path> paths = text.getPaths();
        nearestIndex = text.getText().length();
        Path path = paths.get(paths.size() - 1);
        nearestPathX = path.getBoundsInParent().getMinX() + path.getBoundsInLocal().getWidth();
    }

    private int getNearestPathIndex(TextExtended text, double x) {
        double closestX = -1;
        List<Path> paths = text.getPaths();
        int index = -1;
        int indexToReturn = -1;
        for (Path path : paths) {
            ++index;
            double deltaLeft = Math.abs(path.getBoundsInParent().getMinX() - x);
            double deltaRight = Math.abs(path.getBoundsInParent().getMinX() + path.getBoundsInLocal().getWidth() - x);
            if (closestX < 0 || closestX > deltaLeft) {
                closestX = deltaLeft;
                indexToReturn = index;
            }
            if (closestX > deltaRight) {
                closestX = deltaRight;
                indexToReturn = index + 1;
            }
        }
        return indexToReturn;
    }

    public double getNearestPathX() {
        return nearestPathX;
    }

    public Path getNearestPath() {
        return nearestPath;
    }

    public int getNearestIndex() {
        return nearestIndex;
    }

    public TextExtended getText() {
        return text;
    }
}
