package org.example.gui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import org.example.elements.Animal;
import org.example.elements.Grass;
import org.example.elements.IMapElement;
import org.example.gui.GUIElement;
import org.example.gui.MapStatisticsBox;
import org.example.maps.IMap;
import org.example.settings.SimulationSettings;
import org.example.simulation.ISimulationController;
import org.example.utils.statistics.MapStatistics;
import org.example.utils.Vector2D;

import java.io.IOException;
import java.util.LinkedList;

public class SimulationController extends VBox implements ISimulationController {

    private final SimulationSettings settings;
    private final MapStatisticsBox mapStatisticsBox;
    private final MapStatistics mapStatistics;
    private final IMap map;
    private final int WIDTH, HEIGHT, CELL_SIZE;
    private final GUIElement[][] board;
    private final GridPane grid;

    public SimulationController(SimulationSettings settings, IMap map) {
        this.settings = settings;
        this.map = map;
        this.mapStatistics = map.getStatistics();

        WIDTH = settings.getMapWidth();
        HEIGHT = settings.getMapHeight();
        CELL_SIZE = 20;

        // Load FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/simulation.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        // Set up board array
        board = new GUIElement[settings.getMapHeight()][settings.getMapWidth()];
        for (int i = 0; i < HEIGHT; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                board[i][j] = new GUIElement(null, CELL_SIZE);
            }
        }

        // Initialize grid
        grid = new GridPane();
        grid.setGridLinesVisible(true);

        for (int i = 0; i < WIDTH; ++i) {
            grid.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        }

        for (int i = 0; i < HEIGHT; ++i) {
            grid.getRowConstraints().add(new RowConstraints(CELL_SIZE));
        }

        drawGrid();

        getChildren().add(grid);

        // Add statistics
        mapStatisticsBox = new MapStatisticsBox(mapStatistics);
        getChildren().add(mapStatisticsBox);
    }

    public void drawGrid() {
        grid.getChildren().clear();

        for (int y = 0; y < HEIGHT; ++y) {
            for (int x = 0; x < WIDTH; ++x) {
                grid.add(board[y][x], x, y);
            }
        }
    }

    public void updateGrid() {
        for (int y = 0; y < HEIGHT; ++y) {
            for (int x = 0; x < WIDTH; ++x) {
                Vector2D currentPosition = new Vector2D(x, HEIGHT - 1 - y);

                IMapElement element = null;

                LinkedList<Animal> animalsAt = map.getAnimals().get(currentPosition);
                Grass grassAt = map.getGrass().get(currentPosition);

                if (animalsAt != null) {
                    element = animalsAt.get(0);
                } else if (grassAt != null) {
                    element = grassAt;
                }


                board[y][x] = new GUIElement(element, CELL_SIZE);
            }
        }

        drawGrid();
    }

    @Override
    public void update() {
        updateGrid();
        mapStatistics.updateStatistics();
        mapStatisticsBox.updateStatistics();
    }
}
