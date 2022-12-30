package org.example.elements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Genome {
    private static Random generator = new Random();
    private int genomeLength;
    public ArrayList<Integer> genomes = new ArrayList<>();
    private int currentIndex;

    public Genome(int genomeLength) {
        this.genomeLength = genomeLength;
    }

    public void randomGenome(){
        for(int i = 0; i < genomeLength; i++){
            genomes.add(generator.nextInt(8));
            currentIndex = 0;
        }
    }

    public int next(){
        int currentGenome = genomes.get(currentIndex%genomes.size());
        currentIndex++;
        return currentGenome;
    }


    public void mutation(){

    }

}
