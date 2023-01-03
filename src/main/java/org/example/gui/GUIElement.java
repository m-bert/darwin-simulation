package org.example.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.elements.Animal;
import org.example.elements.Grass;
import org.example.elements.IMapElement;
import org.example.simulation.ISimulationController;

public class GUIElement extends VBox {
    private IMapElement element;
    private final int CELL_SIZE;

    public GUIElement(IMapElement element, ISimulationController controller, int cellSize) {
        this.element = element;
        CELL_SIZE = cellSize;

        setWidth(CELL_SIZE);
        setHeight(CELL_SIZE);

        if (element instanceof Animal) {
            final double ratio = (double) ((Animal) element).getEnergy() / ((Animal) element).getStartEnergy();

            setBackground(new Background(new BackgroundFill(getAnimalColor(ratio), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (element instanceof Grass) {
            setBackground(new Background(new BackgroundFill(Color.valueOf("#75d613"), CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            setBackground(new Background(new BackgroundFill(Color.valueOf("#346602"), CornerRadii.EMPTY, Insets.EMPTY)));
        }

        setOnMouseClicked(e -> {
            if (element instanceof Animal) {
                controller.setTrackedAnimal((Animal) element);
            }
        });
    }

    private Color getAnimalColor(double energyRatio) {
        if (energyRatio <= 0) {
            return Color.BLACK;
        } else if (energyRatio <= 0.25d) {
            return Color.valueOf("#f5c484");
        } else if (energyRatio <= 0.5) {
            return Color.valueOf("#f5b056");
        } else if (energyRatio <= 0.75) {
            return Color.valueOf("#f79c25");
        } else {
            return Color.valueOf("#bd6d06");
        }
    }
}
