import java.util.*;
public class MainProgram {
    public static void main(String[] args){          
        // 5th layer (final)
        Vertex finalState = new Vertex("11am class begins");
        
        // 4th layer
        Vertex tu10a = new Vertex("TU10a");
        tu10a.addAction(new Action("any", -1, finalState));
        
        Vertex ru10a = new Vertex("RU10a");
        ru10a.addAction(new Action("any", 0, finalState));
        
        Vertex rd10a = new Vertex("RD10a");
        rd10a.addAction(new Action("any", 4, finalState));
        
        Vertex td10a = new Vertex("TD10a");
        td10a.addAction(new Action("any", 3, finalState));
        
        // 3rd layer
        Vertex ru8a = new Vertex("RU8a");
        ru8a.addAction(new Action("P", 2, tu10a));
        ru8a.addAction(new Action("R", 0, ru10a));
        ru8a.addAction(new Action("S", -1, rd10a));
        
        Vertex rd8a = new Vertex("RD8a");
        rd8a.addAction(new Action("R", 0, rd10a));
        rd8a.addAction(new Action("P", 2, td10a));
        
        // 2nd layer
        Vertex tu10p = new Vertex("TU10p");
        tu10p.addAction(new Action("P", 2, ru10a));
        tu10p.addAction(new Action("R", 0, ru8a));
        
        Vertex ru10p = new Vertex("RU10p");
        ru10p.addAction(new Action("R", 0, ru8a));
        
        Action actionP = new Action("P", 2);
        actionP.addDestination(ru8a, 0.5);
        actionP.addDestination(ru10a, 0.5);
        ru10p.addAction(actionP);
        
        ru10p.addAction(new Action("S", -1, rd8a));
        
        Vertex rd10p = new Vertex("RD10p");
        rd10p.addAction(new Action("R", 0, rd8a));
        
        Action actionP2 = new Action("P", 2);
        actionP2.addDestination(rd8a, 0.5);
        actionP2.addDestination(rd10a, 0.5);
        rd10p.addAction(actionP2);
        
        // First Layer
        Vertex ru8p = new Vertex("RU8p");
        ru8p.addAction(new Action("P", 2, tu10p));
        ru8p.addAction(new Action("R", 0, ru10p));
        ru8p.addAction(new Action("S", -1, rd10p));
        
        System.out.print(ru8p.toStringActions());
    }
    
    
}
