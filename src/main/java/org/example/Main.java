package org.example;

import javafx.application.Application;
import org.example.elements.Animal;
import org.example.elements.Genome;
import org.example.gui.App;
import org.example.maps.Globe;
import org.example.settings.variants.BehaviourVariant;
import org.example.settings.variants.MutationVariant;
import org.example.settings.variants.PlantsGrowthVariant;
import org.example.utils.AnimalComparator;
import org.example.utils.MapDirection;
import org.example.utils.Vector2D;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Application.launch(App.class, args);


        // pseudo tests genome
//        Animal animal1 = new Animal(new Vector2D(1, 1), 8, 60, 10, 10, 5, MutationVariant.CORRECTION, BehaviourVariant.PREDESTINATION);
//        Genome genome1 = animal1.getGenome();
//        System.out.println(genome1.getGenomes());
//
//        System.out.println(animal1.getDirection());
//        System.out.println(animal1.getPosition());
//
//        animal1.move();
//
//        System.out.println(animal1.getDirection());
//        System.out.println(animal1.getPosition());

//        ConcurrentHashMap<Vector2D, LinkedList<Animal>> animals = new ConcurrentHashMap<>();
//        Vector2D pos = new Vector2D(2,2);
//
//        Animal animal2 = new Animal(0, new Vector2D(1, 1), 8, 20, 10, 10, 5, MutationVariant.CORRECTION, BehaviourVariant.PREDESTINATION, new Globe(10, 10, 10, 10, PlantsGrowthVariant.GRASSY_EQUATORS));
//        animals.computeIfAbsent(pos, k -> new LinkedList<>());
//        animals.get(pos).add(animal2);


//        Genome genome2 = animal2.getGenome();
//
//        Animal child1 = animal1.reproduce(animal2);
//        Genome genomeChild1 = child1.getGenome();
//
//        System.out.println(genome1.getGenomes());
//        System.out.println(genome2.getGenomes());
//        System.out.println(genomeChild1.getGenomes());
    }
}
