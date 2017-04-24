import java.util.*;
public class Util {
    private static final int SEED = 300;
    public static boolean useSeed = false;
    
    private static Random rand;
    public static Random getRandom(){
        if(!useSeed)
            return new Random();
    
        if(rand == null)
            rand = new Random(SEED);
        
        return rand;
    }
}
