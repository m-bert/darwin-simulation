package org.example.simulation;

import org.example.elements.Animal;
import org.example.maps.IMap;
import org.example.settings.SimulationSettings;
import org.example.utils.Vector2D;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class SimulationEngine implements Runnable {
    private final SimulationSettings settings;
    private final IMap map;

    public SimulationEngine(SimulationSettings settings, IMap map) {
        this.settings = settings;
        this.map = map;

        placeAnimals();
    }

    void placeAnimals(){
        for(int i=0; i<settings.getInitialAnimals(); ++i){
            int x = ThreadLocalRandom.current().nextInt(0, settings.getMapWidth());
            int y = ThreadLocalRandom.current().nextInt(0, settings.getMapHeight());
            Vector2D position = new Vector2D(x, y);

            Animal animal = new Animal(
                    map.getCurrentId(), position, settings.getGenomeLength(),settings.getInitialEnergy(), settings.getStuffedEnergy(),
                    settings.getReproduceEnergy(), settings.getMoveEnergy(), settings.getMutationVariant(), settings.getBehaviourVariant(),
                    map
            );

            map.place(animal);
        }
    }

    void moveAnimals(){
        ConcurrentHashMap<Vector2D, LinkedList<Animal>> animals = map.getAnimals();

        for(Vector2D key : animals.keySet()){
            LinkedList<Animal> animalsAtKey = animals.get(key);

            for(Animal animal : animalsAtKey){
                animal.move();
            }
        }
    }



    @Override
    public void run() {
        while(map.getAnimalsNum() > 0){
            map.removeDeadAnimals();
            moveAnimals();
            map.eating();
            map.reproduce();
            map.plantSeeds();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
