
public class Tuple {
    State nextState;
    double Q;
    double probability;
    
    public Tuple(State nextState, double probability, double Q){
        this.nextState = nextState;
        this.probability = probability;
        this.Q = Q;
    }
}
