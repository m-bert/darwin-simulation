package org.example.elements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Genome {
    private static Random generator = new Random();
    private int genomeLength;
    public List<Integer> genomes = new ArrayList<>();
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

    public List<Integer> getLeftGenomePart(int index){
        return genomes.subList(0, index);
    }

    public List<Integer> getRightGenomePart(int index){
        return genomes.subList(index, genomeLength);
    }

    public List<Integer> getGenomes() {
        return genomes;
    }

    public void setGenomes(List<Integer> genomes) {
        this.genomes = genomes;
    }
}
