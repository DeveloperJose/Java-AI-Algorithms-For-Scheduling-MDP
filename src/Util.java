import java.util.*;
public class Util {
    private static final int SEED = 300; 
    
    private static Random rand;
    public static Random getRandom(){
        if(rand == null)
            rand = new Random(SEED);
        
        return rand;
    }
}
