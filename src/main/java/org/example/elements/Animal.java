package org.example.elements;

import org.example.maps.IMap;
import org.example.settings.variants.BehaviourVariant;
import org.example.settings.variants.MutationVariant;
import org.example.utils.IPositionChangeObserver;
import org.example.utils.MapDirection;
import org.example.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Animal extends AbstractMapElement{
    private final int id;
    private MutationVariant mutationVariant;
    private BehaviourVariant behaviourVariant;
    private Random random = new Random();
    private int lifeDays;
    private int initialEnergy;
    private int reproduceEnergy;
    private int stuffedEnergy;
    private int moveEnergy;
    private int childrenNum;
    private int genomeLength;
    private Genome genome;
    private boolean isAlive;
    private MapDirection direction;
    private final ArrayList<IPositionChangeObserver> observers; //TODO: can be one observer?

    private final IMap map;

    public Animal(int id, Vector2D position, int genomeLength, int initialEnergy, int stuffedEnergy, int reproduceEnergy, int moveEnergy, MutationVariant mutationVariant, BehaviourVariant behaviourVariant, IMap map) {
        // initial position for animal
        super(position);
        this.id = id;
        this.direction = MapDirection.randomDirection();
        this.observers = new ArrayList<>();
        // when animal is dead - set false
        this.isAlive = true;
        this.lifeDays = 0;
        this.childrenNum = 0;
        this.genomeLength = genomeLength;
        this.behaviourVariant = behaviourVariant;
        // get genomeLength from settings
        this.genome = new Genome(genomeLength, mutationVariant);
        // only first generation of animal have init energy from settings
        // juveniles will have init energy as reproduction energy from parents
        this.initialEnergy = initialEnergy;
        // ready to reproduce energy
        this.stuffedEnergy = stuffedEnergy;
        // lost energy during reproduction
        this.reproduceEnergy = reproduceEnergy;
        this.moveEnergy = moveEnergy;

        this.map = map;

        genome.createRandomGenome();
    }

    private void positionChanged(Vector2D oldPosition, Vector2D newPosition) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }

    public void rotateAnimal(int angleNumber){
        // angleNumber from 0 to 7 (map directions)
        for(int i=0; i < angleNumber; i++){
            direction = direction.next();
            System.out.println(direction);
        }
    }

    public void move(){
        initialEnergy -= moveEnergy;
        increaseAge();
        if(initialEnergy <= 0){
            // usunac niezywe zwierzeta w klasie map
            isAlive = false;

            for(IPositionChangeObserver observer : observers){
                observer.notifyDeath(this);
            }
        }
        else {
            if (behaviourVariant == BehaviourVariant.PREDESTINATION) {
                int currentGenome = genome.next();
                rotateAnimal(currentGenome);
                Vector2D previousPosition = position;
                position = position.add(direction.toUnitVector());
                positionChanged(previousPosition, position);
            }
            else {
                double probability = this.random.nextDouble();
                if (probability <= 0.2) {
                    int randomNumSize = random.nextInt(genomeLength);
                    int currentGenome = genome.next();
                    for (int i = 0; i < randomNumSize; i++) {
                        currentGenome = genome.next();
                    }
                    rotateAnimal(currentGenome);
                } else {
                    int currentGenome = genome.next();
                    rotateAnimal(currentGenome);
                }

                Vector2D previousPosition = position;
                position = position.add(direction.toUnitVector());
                positionChanged(previousPosition, position);
            }
        }
    }

    public Animal reproduce(Animal other){
        if(this.getEnergy() < this.stuffedEnergy ||
           other.getEnergy() < other.stuffedEnergy){
            return null;
        }

        this.increaseChildrenNum();
        other.increaseChildrenNum();

        // initial child energy from parents reproduction
        int childEnergy = this.reproduceEnergy + other.reproduceEnergy;
        this.energyReproduceChange();
        other.energyReproduceChange();

        Animal child = new Animal(map.getCurrentId(), position, genomeLength, childEnergy, stuffedEnergy, reproduceEnergy, moveEnergy, mutationVariant, behaviourVariant, map);
        Genome newGenome = genomeDivide(other);
        newGenome.mutation();
        child.setGenome(newGenome);

        return child;
    }

    public Genome genomeDivide(Animal other){
        int totalEnergy = this.getEnergy() + other.getEnergy();
        int thisGenomePartLen = (int) (this.genomeLength*((float) this.getEnergy()/totalEnergy));
        int otherGenomePartLen = genomeLength - thisGenomePartLen;

        Genome childGenome = new Genome(genomeLength, mutationVariant);

        // 0 - left part of stronger Animal
        // 1 - right part of stronger Animal
        Random rand = new Random();
        int side = rand.nextInt() % 2;
        List<Integer> newGenome = new ArrayList<>();

        if(side == 0 && this.getEnergy() > other.getEnergy()){
            List<Integer> firstPart = this.genome.getLeftGenomePart(thisGenomePartLen);
            List<Integer> secondPart = other.genome.getRightGenomePart(thisGenomePartLen);
            newGenome.addAll(firstPart);
            newGenome.addAll(secondPart);
        }
        else if(side == 1 && this.getEnergy() > other.getEnergy()){
            List<Integer> firstPart = other.genome.getLeftGenomePart(otherGenomePartLen);
            List<Integer> secondPart = this.genome.getRightGenomePart(otherGenomePartLen);
            newGenome.addAll(firstPart);
            newGenome.addAll(secondPart);
        } else if (side == 0 && this.getEnergy() <= other.getEnergy()) {
            List<Integer> firstPart = other.genome.getLeftGenomePart(thisGenomePartLen);
            List<Integer> secondPart = this.genome.getRightGenomePart(thisGenomePartLen);
            newGenome.addAll(firstPart);
            newGenome.addAll(secondPart);
        } else {
            List<Integer> firstPart = this.genome.getLeftGenomePart(otherGenomePartLen);
            List<Integer> secondPart = other.genome.getRightGenomePart(otherGenomePartLen);
            newGenome.addAll(firstPart);
            newGenome.addAll(secondPart);
        }

        childGenome.setGenomes(newGenome);
        return childGenome;
    }

    public void setGenome(Genome genome) {
        this.genome = genome;
    }

    public void energyReproduceChange(){
        initialEnergy -= reproduceEnergy;
        if(initialEnergy <= 0){
            isAlive = false;
        }
    }

    public void eat(int eatEnergy){
        initialEnergy += eatEnergy;
    }

    public void increaseAge(){
        lifeDays += 1;
    }

    public String toString() {
        return direction.toString();
    }

    public MapDirection getDirection() {
        return direction;
    }

    public void addObserver(IPositionChangeObserver newObserver) {
        this.observers.add(newObserver);
    }

    public void removeObserver(IPositionChangeObserver observerToRemove) {
        this.observers.remove(observerToRemove);
    }

    @Override
    public String getPath() {
        return switch (getDirection()) {
            case NORTH -> "src/main/resources/gfx/up.png";
            case NORTHEAST -> "src/main/resources/gfx/rightup.png";
            case EAST -> "src/main/resources/gfx/right.png";
            case SOUTHEAST -> "src/main/resources/gfx/rightdown.png";
            case SOUTH -> "src/main/resources/gfx/down.png";
            case SOUTHWEST -> "src/main/resources/gfx/leftdown.png";
            case WEST -> "src/main/resources/gfx/left.png";
            case NORTHWEST -> "src/main/resources/gfx/leftup.png";
        };
    }

    public int getEnergy(){
        return initialEnergy;
    }

    public Genome getGenome() {
        return genome;
    }

    public int getLifeDays() {
        return lifeDays;
    }

    public int getInitialEnergy() {
        return initialEnergy;
    }

    public int getChildrenNum() {
        return childrenNum;
    }

    public void increaseChildrenNum(){
        ++childrenNum;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }

        if(obj.getClass() != getClass()){
            return false;
        }

        return id == ((Animal) obj).getId();
    }
}
