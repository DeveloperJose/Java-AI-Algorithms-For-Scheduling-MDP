import java.util.*;
public class Action {
    private DistributedRandomNumberGenerator distRand;
    public String name;
    public double reward;
    
    public Action(String name, double reward){
        this.name = name;
        this.reward = reward;
        destinations = new ArrayList<>();
        distRand = new DistributedRandomNumberGenerator();
    }
    
    public Action(String name, double reward, Vertex destination){
        this(name,  reward);
        addDestination(destination, 1.0);
    }
    
    public List<Vertex> destinations;
    public void addDestination(Vertex destination, double probability){
        int nextIndex = destinations.size();
        destinations.add(destination);
        distRand.addNumber(nextIndex, probability);
    }
    
    public Vertex destination(){
        int randomIndex = distRand.getDistributedRandomNumber();
        Vertex randomDestination = destinations.get(randomIndex);
        return randomDestination;
    }
}
