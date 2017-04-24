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
    
    public Action(String name, double reward, State destination){
        this(name,  reward);
        addDestination(destination, 1.0);
    }
    
    public List<State> destinations;
    public void addDestination(State destination, double probability){
        int nextIndex = destinations.size();
        destinations.add(destination);
        distRand.addNumber(nextIndex, probability);
    }
    
    public Set<State> getChildren(){
        Set<State> list = new TreeSet<>();
        list.addAll(destinations);
        for(State dest : destinations)
            list.addAll(dest.getChildren());
        
        return list;
    }
    
    public State destination(){
        int randomIndex = distRand.getDistributedRandomNumber();
        State randomDestination = destinations.get(randomIndex);
        return randomDestination;
    }
}
