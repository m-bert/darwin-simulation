package org.example.maps;

import org.example.settings.variants.PlantsGrowthVariant;
import org.example.utils.Vector2D;

public class Globe extends AbstractMap {
    public Globe(int WIDTH, int HEIGHT, int plantsGrowth, int initialPlantsNum, int plantsEnergy, PlantsGrowthVariant plantsGrowthVariant) {
        super(WIDTH, HEIGHT, plantsGrowth, initialPlantsNum, plantsEnergy, plantsGrowthVariant);
    }

    @Override
    public Vector2D teleportAnimal(Vector2D position) {
        if (position.y >= HEIGHT || position.y < 0) {
            return null;
        }

        if (position.x >= WIDTH) {
            return new Vector2D(0, position.y);
        }

        if (position.x < 0) {
            return new Vector2D(WIDTH - 1, position.y);
        }

        return null;
    }
}
