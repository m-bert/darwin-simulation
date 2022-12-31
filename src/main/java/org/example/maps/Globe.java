package org.example.maps;

import org.example.settings.variants.PlantsGrowthVariant;

public class Globe extends AbstractMap {
    public Globe(int WIDTH, int HEIGHT, int plantsGrowth, int initialPlantsNum, int plantsEnergy, PlantsGrowthVariant plantsGrowthVariant) {
        super(WIDTH, HEIGHT, plantsGrowth, initialPlantsNum, plantsEnergy, plantsGrowthVariant);
    }
}
