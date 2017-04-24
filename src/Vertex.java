import java.util.*;
public class Vertex implements Comparable<Vertex>{
    public String state;
    public List<Action> actions;
    
    public Vertex(String state){
        this.state = state;
        actions = new ArrayList<>();
    }
    
    public void addAction(Action a){
        actions.add(a);
    }
    
    public Vertex transition(){
        int randomIndex = Util.getRandom().nextInt(actions.size());
        Action randomAction = actions.get(randomIndex);
        
        return randomAction.destination();
    }
    
    public String toString(){
        String result = "{Vertex=" + state + "}\n";
        
        for(Action a : actions){
            result += "\t{Action=" + a.name + ", Reward=" + a.reward + "}";
            
            int count = 0;
            result += "\t->";
            for(Vertex v : a.destinations){
                if(count >= 1)
                    result += ", ";
                result += v.state;
                count++;
            }
            result += "\n";
        }
        
        return result;
    }
    
    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof Vertex)) return false;

        Vertex other = (Vertex)o;
        return state.equals(other.state);
    }

    @Override
    public int compareTo(Vertex other) {
        if(equals(other))
            return 0;
        
        return state.compareTo(other.state);
            
        
    }
}
