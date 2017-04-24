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
    
    public double getValue(double stateProbability, double gamma){
        double result = 0;
        
        for(int i = 0; i < destinations.size(); i++){
            State state = destinations.get(i);
            double probability = distRand.distribution.get(i);
            result += (stateProbability * probability) * (reward + (gamma * state.value));
        }
        
        return result;
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
    
    public State getNextRandomState(){
        int randomIndex = distRand.getDistributedRandomNumber();
        State randomDestination = destinations.get(randomIndex);
        return randomDestination;
    }
    
    public State getBestState(){
        double highestValue = Integer.MIN_VALUE;
        State bestState = null;
        for(State state : destinations){
            if(state.value > highestValue){
                highestValue = state.value;
                bestState = state;
            }
        }
        return bestState;
    }
}
