import java.util.*;
public class State implements Comparable<State>{
    public String name;
    public boolean isFinal;
    private List<Action> actions;
    
    public State(String state){
        this.name = state;
        actions = new ArrayList<>();
    }
    
    public void addAction(Action a){
        actions.add(a);
    }
    
    public Set<State> getChildren(){
        if(isFinal) return new TreeSet<>();
        
        Set<State> list = new TreeSet<>();
        for(Action action : actions)
            list.addAll(action.getChildren());
        
        return list;
    }
    
    public State transition(){
        int randomIndex = Util.getRandom().nextInt(actions.size());
        Action randomAction = actions.get(randomIndex);
        
        return randomAction.destination();
    }
    
    public String toString(){
        String result = "{Vertex=" + name + "}\n";
        
        for(Action a : actions){
            result += "\t\t{Action=" + a.name + ", Reward=" + a.reward + "}";
            
            int count = 0;
            result += "\t\t->";
            for(State v : a.destinations){
                if(count >= 1)
                    result += ", ";
                result += v.name;
                count++;
            }
            result += "\n";
        }
        
        return result;
    }
    
    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof State)) return false;

        State other = (State)o;
        return name.equals(other.name);
    }

    @Override
    public int compareTo(State other) {
        if(equals(other))
            return 0;
        
        return name.compareTo(other.name);
            
        
    }
}
