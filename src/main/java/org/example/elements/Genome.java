package org.example.elements;

import org.example.settings.variants.MutationVariant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class Genome {
    private static Random generator = new Random();
    private int genomeLength;
    public List<Integer> genomes = new ArrayList<>();
    private int currentIndex;
    private MutationVariant mutationVariant;

    public Genome(int genomeLength, MutationVariant mutationVariant) {
        this.genomeLength = genomeLength;
        this.mutationVariant = mutationVariant;
    }

    public void createRandomGenome(){
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
        int randomNumSize = generator.nextInt(genomeLength);
        if(mutationVariant == MutationVariant.RANDOM){
            for (int i = 0; i < randomNumSize; i++) {
                int randomIndex = generator.nextInt(genomeLength);
                genomes.set(randomIndex, generator.nextInt(8));
            }
        }
        else{
            for (int i = 0; i < randomNumSize; i++) {
                int randomIndex = generator.nextInt(genomeLength);
                int oldValue = genomes.get(randomIndex);
                int plusMinusOne = generator.nextBoolean() ? 1 : -1;
                int newValue = (oldValue+plusMinusOne)%8;
                if(abs(newValue-oldValue) < 2 && newValue >= 0) {
                    genomes.set(randomIndex, newValue);
                }
            }
        }
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
