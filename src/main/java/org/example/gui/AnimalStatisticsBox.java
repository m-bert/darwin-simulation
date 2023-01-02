package org.example.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import org.example.utils.statistics.AnimalStatistics;

import java.util.HashMap;
import java.util.Map;

public class AnimalStatisticsBox extends GridPane {
    private AnimalStatistics statistics;
    private final Map<String, String> statisticFields;

    public AnimalStatisticsBox() {
        final String emptyValue = "-----";

        statisticFields = new HashMap<>();
        statisticFields.put("Genome", emptyValue);
        statisticFields.put("Active_genome_part", emptyValue);
        statisticFields.put("Energy", emptyValue);
        statisticFields.put("Eaten_plants", emptyValue);
        statisticFields.put("Children_amount", emptyValue);
        statisticFields.put("Lifetime", emptyValue);
        statisticFields.put("Day_of_death", emptyValue);

        getColumnConstraints().add(new ColumnConstraints());
        getRowConstraints().add(new RowConstraints());

        setPadding(new Insets(10));
    }

    public void updateStatistics(){
        if(statistics == null){
            return;
        }

        statisticFields.put("Genome", String.valueOf(statistics.getGenome()));
        statisticFields.put("Active_genome_part", String.valueOf(statistics.getActiveGenome()));
        statisticFields.put("Energy", String.valueOf(statistics.getCurrentEnergy()));
        statisticFields.put("Eaten_plants", String.valueOf(statistics.getGrassEatenNum()));
        statisticFields.put("Children_amount", String.valueOf(statistics.getChildrenNum()));
        statisticFields.put("Lifetime", String.valueOf(statistics.getAge()));
        statisticFields.put("Day_of_death", String.valueOf(statistics.getDeathDay()));

        updateStatisticsGrid();
    }
    private void updateStatisticsGrid() {
        getChildren().clear();

        int i = 0;
        for(Map.Entry<String, String> statistic : statisticFields.entrySet()){
            add(new Label(statistic.getKey()), 0, i);
            add(new Label(statistic.getValue()), 1, i);

            ++i;
        }
    }

    public void setStatistics(AnimalStatistics statistics) {
        this.statistics = statistics;
    }
}
