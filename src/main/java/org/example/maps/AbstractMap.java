package org.example.maps;

import org.example.elements.AbstractMapElement;
import org.example.elements.Animal;
import org.example.elements.Grass;
import org.example.settings.variants.PlantsGrowthVariant;
import org.example.utils.AnimalComparator;
import org.example.utils.IAnimalObserver;
import org.example.utils.MapStatistics;
import org.example.utils.Vector2D;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class AbstractMap implements IMap, IAnimalObserver {
    protected final int WIDTH;
    protected final int HEIGHT;

    protected final MapStatistics statistics;

    // Plants
    protected final ConcurrentHashMap<Vector2D, Grass> grass;
    protected final int plantsGrowth;
    protected final int initialPlantsNum;
    protected final PlantsGrowthVariant plantsGrowthVariant;
    protected final int plantsEnergy;

    // Animals
    protected final ConcurrentHashMap<Vector2D, LinkedList<Animal>> animals;
    protected final ArrayList<Animal> deadAnimals;
    protected final ArrayList<Animal> deadAnimalsHistory;
    protected int animalsNum;
    protected int currentId; // Id for animals incremented when new animals is placed on map

    // Equator
    protected Vector2D equatorOrigin = null;
    protected int EQUATOR_WIDTH = -1;
    protected int EQUATOR_HEIGHT = -1;

    public AbstractMap(int WIDTH, int HEIGHT, int plantsGrowth, int initialPlantsNum, int plantsEnergy, PlantsGrowthVariant plantsGrowthVariant) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.plantsGrowth = plantsGrowth;
        this.initialPlantsNum = initialPlantsNum;
        this.plantsEnergy = plantsEnergy;
        this.plantsGrowthVariant = plantsGrowthVariant;

        statistics = new MapStatistics(this);
        grass = new ConcurrentHashMap<>();
        animals = new ConcurrentHashMap<>();
        deadAnimals = new ArrayList<>();
        deadAnimalsHistory = new ArrayList<>();
        animalsNum = 0;
        currentId = 0;

        switch (plantsGrowthVariant) {
            case TOXIC_CORPSES -> {
            }

            case GRASSY_EQUATORS -> {
                int area = WIDTH * HEIGHT;
                int equatorArea = (int) (0.2 * area);
                int equatorHeight = equatorArea / WIDTH;
                int middleHeight = HEIGHT / 2;
                int equatorOriginY = middleHeight - (equatorHeight / 2);

                equatorOrigin = new Vector2D(0, equatorOriginY);
                EQUATOR_HEIGHT = equatorHeight;
                EQUATOR_WIDTH = WIDTH;
            }
        }
    }

    @Override
    public void positionChanged(Animal animal, Vector2D oldPosition, Vector2D newPosition) {
        animals.get(oldPosition).remove(animal);

        if(animals.get(oldPosition).size() == 0){
            animals.remove(oldPosition);
        }

        animals.computeIfAbsent(newPosition, k -> new LinkedList<>());
        animals.get(newPosition).add(animal);

        animals.get(newPosition).sort(new AnimalComparator());
    }

    @Override
    public void notifyDeath(Animal animal) {
        deadAnimals.add(animal);
        deadAnimalsHistory.add(animal);
    }

    @Override
    public boolean isInsideBoundaries(Vector2D position) {
        return position.x >= 0 && position.x < WIDTH && position.y >= 0 && position.y < HEIGHT;
    }

    @Override
    public Vector2D teleportAnimal(Vector2D position) {
        return null;
    }

    @Override
    public boolean place(AbstractMapElement mapElement) {
        Vector2D position = mapElement.getPosition();

        if (mapElement instanceof Animal) {
            animals.computeIfAbsent(position, k -> new LinkedList<>());
            animals.get(position).add((Animal) mapElement);

            ++animalsNum;
            ++currentId;
        } else {
            if (containsGrassAt(position)) {
                return false;
            }

            grass.put(position, (Grass) mapElement);
        }

        return true;
    }

    @Override
    public boolean containsGrassAt(Vector2D position) {
        return grass.get(position) != null;
    }

    @Override
    public LinkedList<AbstractMapElement> objectsAt(Vector2D position) {
        LinkedList<AbstractMapElement> objects = new LinkedList<>(animals.get(position));
        objects.add(grass.get(position));

        return objects.stream().filter(Objects::nonNull).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void removeDeadAnimals() {
        for (Animal animal : deadAnimals) {
            Vector2D position = animal.getPosition();
            animals.get(position).remove(animal);

            if(animals.get(position).size() == 0){
                animals.remove(position);
            }
        }

        animalsNum -= deadAnimals.size();
        deadAnimals.clear();
    }

    @Override
    public void eating() {
        for (Vector2D key : grass.keySet()) {
            if (animals.get(key) == null) {
                continue;
            }

            LinkedList<Animal> animalsAt = animals.get(key);
            animalsAt.get(0).eat(plantsEnergy);

            grass.remove(key);
        }
    }

    @Override
    public void reproduce() {
        for (Vector2D key : animals.keySet()) {
            if (animals.get(key).size() < 2) {
                continue;
            }

            Animal parent1 = animals.get(key).get(0);
            Animal parent2 = animals.get(key).get(1);

            Animal child = parent1.reproduce(parent2);

            if (child != null) {
                place(child);
            }
        }
    }

    private Vector2D findGrassPosition(boolean onEquator) {
        final int occupiedFields = grass.size();
        final int maxAmount = WIDTH * HEIGHT;

        if (occupiedFields == maxAmount) {
            return null;
        }

        int x, y;
        int trials = 0;

        do {
            ++trials;

            x = ThreadLocalRandom.current().nextInt(0, WIDTH);

            if (onEquator) {
                y = ThreadLocalRandom.current().nextInt(equatorOrigin.y, equatorOrigin.y + EQUATOR_HEIGHT);
            } else {
                if (new Random().nextDouble() <= 0.5) {
                    y = ThreadLocalRandom.current().nextInt(0, equatorOrigin.y);
                } else {
                    y = ThreadLocalRandom.current().nextInt(equatorOrigin.y + EQUATOR_HEIGHT + 1, HEIGHT);
                }
            }

            if (trials == maxAmount) {
                return null;
            }

        } while (containsGrassAt(new Vector2D(x, y)));

        return new Vector2D(x, y);
    }

    @Override
    public void plantSeeds() {
        switch (plantsGrowthVariant) {
            case TOXIC_CORPSES -> plantCorpses();
            case GRASSY_EQUATORS -> plantEquator();
        }
    }

    @Override
    public void plantEquator() {
        for (int i = 0; i < plantsGrowth; ++i) {
            double probability = new Random().nextDouble();
            Vector2D position;

            if (probability <= 0.2) {
                position = findGrassPosition(false);
            } else {
                position = findGrassPosition(true);
            }

            if (position == null) {
                // Cannot plant more seeds
                break;
            }

            grass.put(position, new Grass(position));
        }
    }

    @Override
    public void plantCorpses() {
        //TODO: Implement later
    }

    @Override
    public void updateStatistics() {
        statistics.updateStatistics();
    }

    @Override
    public MapStatistics getStatistics() {
        return statistics;
    }

    @Override
    public int getCurrentId() {
        //TODO: Think about incrementation moment
        return currentId;
    }

    @Override
    public int getAnimalsNum() {
        return animalsNum;
    }

    @Override
    public ConcurrentHashMap<Vector2D, LinkedList<Animal>> getAnimals() {
        return animals;
    }

    @Override
    public ArrayList<Animal> getDeadAnimalsHistory() {
        return deadAnimalsHistory;
    }

    @Override
    public ConcurrentHashMap<Vector2D, Grass> getGrass() {
        return grass;
    }

    @Override
    public int getSize() {
        return WIDTH * HEIGHT;
    }
}
