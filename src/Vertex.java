import java.util.*;
public class Vertex {
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
        String result = "{Vertex = " + state + "}\n";
        
        for(Action a : actions){
            for(Vector v : a.destinations){
                
            }
        }
        
        return result;
    }
}
