import java.util.*;
public class Action {
    private DistributedRandomNumberGenerator distRand;
    public String name;
    public double reward;
    public List<Tuple> destinations;
    
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
        
        for(Tuple t : destinations){
            State state = t.nextState;
            double probability = t.probability;
            result += (stateProbability * probability) * (reward + (gamma * state.value));
        }
        
        return result;
    }
    
    public double getMaxQ(){
        double maxQ = Integer.MIN_VALUE;
        
        for(Tuple t : destinations){            
            if(t.nextState.getMaxQ() + t.Q > maxQ){
                maxQ = t.nextState.getMaxQ() + t.Q;
            }
        }
        return maxQ;
    }
    
    public void addDestination(State destination, double probability){
        int nextIndex = destinations.size();
        destinations.add(new Tuple(destination, probability, reward));
        
        distRand.addNumber(nextIndex, probability);
    }
    
    public Set<State> getChildren(){
        Set<State> list = new TreeSet<>();
        for(Tuple t : destinations){
            State dest = t.nextState;
            list.add(dest);
            list.addAll(dest.getChildren());
        }
        return list;
    }
    
    public State getNextRandomState(){
        int randomIndex = distRand.getDistributedRandomNumber();
        State randomDestination = destinations.get(randomIndex).nextState;
        return randomDestination;
    }
    
    public State getBestState(){
        double highestValue = Integer.MIN_VALUE;
        State bestState = null;
        for(Tuple t : destinations){
            State state = t.nextState;
            if(state.value > highestValue){
                highestValue = state.value;
                bestState = state;
            }
        }
        return bestState;
    }
    
    public State getBestStateQ(){
        double highestValue = Integer.MIN_VALUE;
        State bestState = null;
        for(Tuple t : destinations){
            if(t.nextState.getMaxQ() + t.Q > highestValue){
                highestValue = t.Q;
                bestState = t.nextState;
            }
        }
        return bestState;
    }
}
