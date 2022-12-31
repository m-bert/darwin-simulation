package org.example.utils;

import org.example.elements.Animal;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal o1, Animal o2) {
        if (o1.getEnergy() > o2.getEnergy()) {
            return -1;
        } else if (o1.getEnergy() < o2.getEnergy()) {
            return 1;
        }

        if (o1.getLifeDays() > o2.getLifeDays()) {
            return -1;
        } else if (o1.getLifeDays() < o2.getLifeDays()) {
            return 1;
        }

        if (o1.getChildrenNum() > o2.getChildrenNum()) {
            return -1;
        } else if (o1.getChildrenNum() < o2.getChildrenNum()) {
            return 1;
        }

        return 0;
    }
}
