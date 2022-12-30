package org.example;

import javafx.application.Application;
import org.example.elements.Animal;
import org.example.elements.Genome;
import org.example.gui.App;
import org.example.utils.MapDirection;
import org.example.utils.Vector2D;

public class Main {
    public static void main(String[] args) {
        Application.launch(App.class, args);

        // pseudo tests genome
//        Animal animal1 = new Animal(new Vector2D(1, 1), 8, 60, 10, 10, 5);
//        Animal animal2 = new Animal(new Vector2D(1, 1), 8, 20, 10, 10, 5);
//        Animal child1 = animal1.reproduce(animal2);
//
//        System.out.println(animal1.genome.getGenomes());
//        System.out.println(animal2.genome.getGenomes());
//        System.out.println(child1.genome.getGenomes());
    }
}
