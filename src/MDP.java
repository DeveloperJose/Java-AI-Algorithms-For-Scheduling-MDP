import java.util.*;
public class MDP {
    public State startingState;
    public State currentState;
    
    public MDP(State startingState){
        this.startingState = startingState;
        this.currentState = startingState;
    }
    
    public void step(){
        currentState = currentState.transition();
        System.out.println("Now in state: " + currentState.name);
    }
    
    public static MDP createStudentLife(){
        // 5th layer (final)
        State finalState = new State("11am class begins");
        finalState.isFinal = true;
        
        // 4th layer
        State tu10a = new State("TU10a");
        tu10a.addAction(new Action("any", -1, finalState));
        
        State ru10a = new State("RU10a");
        ru10a.addAction(new Action("any", 0, finalState));
        
        State rd10a = new State("RD10a");
        rd10a.addAction(new Action("any", 4, finalState));
        
        State td10a = new State("TD10a");
        td10a.addAction(new Action("any", 3, finalState));
        
        // 3rd layer
        State ru8a = new State("RU8a");
        ru8a.addAction(new Action("P", 2, tu10a));
        ru8a.addAction(new Action("R", 0, ru10a));
        ru8a.addAction(new Action("S", -1, rd10a));
        
        State rd8a = new State("RD8a");
        rd8a.addAction(new Action("R", 0, rd10a));
        rd8a.addAction(new Action("P", 2, td10a));
        
        // 2nd layer
        State tu10p = new State("TU10p");
        tu10p.addAction(new Action("P", 2, ru10a));
        tu10p.addAction(new Action("R", 0, ru8a));
        
        State ru10p = new State("RU10p");
        ru10p.addAction(new Action("R", 0, ru8a));
        
        Action actionP = new Action("P", 2);
        actionP.addDestination(ru8a, 0.5);
        actionP.addDestination(ru10a, 0.5);
        ru10p.addAction(actionP);
        
        ru10p.addAction(new Action("S", -1, rd8a));
        
        State rd10p = new State("RD10p");
        rd10p.addAction(new Action("R", 0, rd8a));
        
        Action actionP2 = new Action("P", 2);
        actionP2.addDestination(rd8a, 0.5);
        actionP2.addDestination(rd10a, 0.5);
        rd10p.addAction(actionP2);
        
        // First Layer
        State ru8p = new State("RU8p");
        ru8p.addAction(new Action("P", 2, tu10p));
        ru8p.addAction(new Action("R", 0, ru10p));
        ru8p.addAction(new Action("S", -1, rd10p));
        
        MDP temp = new MDP(ru8p);
        return temp;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        Set<State> states = startingState.getChildren();
        sb.append("{MDP, Size=" + states.size() + "}\n");
        
        for(State v : states){
            sb.append("\t" + v);
        }
        
        
        return sb.toString();
    }
    
}
