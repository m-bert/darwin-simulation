package org.example.maps;

import org.example.settings.variants.PlantsGrowthVariant;
import org.example.utils.Vector2D;

import java.util.concurrent.ThreadLocalRandom;

public class HellPortal extends AbstractMap {
    public HellPortal(int WIDTH, int HEIGHT, int plantsGrowth, int initialPlantsNum, int plantsEnergy, PlantsGrowthVariant plantsGrowthVariant) {
        super(WIDTH, HEIGHT, plantsGrowth, initialPlantsNum, plantsEnergy, plantsGrowthVariant);
    }

    @Override
    public Vector2D teleportAnimal(Vector2D position) {
        int x = ThreadLocalRandom.current().nextInt(0, WIDTH);
        int y = ThreadLocalRandom.current().nextInt(0, HEIGHT);

        return new Vector2D(x, y);
    }
}
