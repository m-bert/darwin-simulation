package org.example.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import org.example.utils.statistics.MapStatistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapStatisticsBox extends GridPane {
    private final MapStatistics statistics;
    private final Map<String, String> statisticFields;

    public MapStatisticsBox(MapStatistics statistics) {
        this.statistics = statistics;
        final String emptyValue = "-----";

        statisticFields = new HashMap<>();
        statisticFields.put("Animals", emptyValue);
        statisticFields.put("Plants", emptyValue);
        statisticFields.put("Free_places", emptyValue);
        statisticFields.put("Top_genomes", emptyValue);
        statisticFields.put("Average_energy", emptyValue);
        statisticFields.put("Average_lifetime", emptyValue);

        getColumnConstraints().add(new ColumnConstraints());
        getRowConstraints().add(new RowConstraints());

        setPadding(new Insets(10));

        updateStatisticsGrid();
    }

    public void updateStatistics() {
        statisticFields.put("Animals", String.valueOf(statistics.getAnimalsAmount()));
        statisticFields.put("Plants", String.valueOf(statistics.getPlantsAmount()));
        statisticFields.put("Free_places", String.valueOf(statistics.getFreeCellsAmount()));
        statisticFields.put("Average_energy", String.valueOf(statistics.getAverageEnergy()));
        statisticFields.put("Average_lifetime", String.valueOf(statistics.getAverageDeadAnimalsLifeLength()));

        ArrayList<String> topGenomes = statistics.getTopGenomes();
        StringBuilder topGenomesSTr = new StringBuilder();

        for (String topGenome : topGenomes) {
            topGenomesSTr.append(topGenome).append(" ");
        }

        statisticFields.put("Top_genomes", topGenomesSTr.toString());

        updateStatisticsGrid();
    }

    private void updateStatisticsGrid() {
        getChildren().clear();

        int i = 0;
        for (Map.Entry<String, String> statistic : statisticFields.entrySet()) {
            add(new Label(statistic.getKey()), 0, i);
            add(new Label(statistic.getValue()), 1, i);

            ++i;
        }
    }
}
