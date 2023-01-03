package org.example.simulation;

import org.example.elements.Animal;

public interface ISimulationController {
    void update();

    void setTrackedAnimal(Animal animal);

    void setEngine(ISimulationEngine engine);
}
