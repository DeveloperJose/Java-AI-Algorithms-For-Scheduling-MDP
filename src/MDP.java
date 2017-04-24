import java.util.*;
public class MDP {
    public State startingState;
    public State currentState;
    
    public MDP(State startingState){
        this.startingState = startingState;
        this.currentState = startingState;
    }
    
    public void resetValues(){
        for (State state : startingState.getChildren())
            state.value = 0;
    }
    
    public State getState(String name){
        Set<State> allStates = new TreeSet<>();
        allStates.add(startingState);
        allStates.addAll(startingState.getChildren());
        for(State state : allStates)
            if(state.name.equals(name))
                return state;
        return null;
    }
    
    public void qLearning(){
        double gamma = 0.99; // Discount Factor
        double alpha = 0.01; // Learning rate
        
        Set<State> allStates = new TreeSet<>();
        allStates.add(startingState);
        allStates.addAll(startingState.getChildren());
        boolean isRunning = true;
        int iterations = 0;
                
        currentState = startingState;
        while(isRunning){
            Action actionToTake = currentState.getNextBestActionQ();
            State nextState = actionToTake.getBestStateQ();
            iterations++;
            
            //System.out.println(getState("RD10p").getMaxQ());
            
            for (int i = 0; i < currentState.actions.size(); i++){
                Action action = currentState.actions.get(i);
            
                for(Tuple t : action.destinations){
                    double newQValue = t.Q + (alpha * (action.reward + gamma * nextState.getMaxQ() - t.Q));
                    double difference = Math.abs(t.Q - newQValue);
                    //System.out.println(difference);
                    
                    t.Q = newQValue;
                    //if(difference < 0.001)
                        //isRunning = false;
                    
                    if(iterations > 1000)
                        isRunning = false;
                    
                    //Action futureAction = nextState.getNextBestActionQ();
                    
                    //if(futureAction == null)
                      //  continue;
                    
                    //for(Tuple futureTuple : futureAction.destinations){
                        //double futureQ = futureTuple.Q;
                        //System.out.printf("(Action %s) Old Q = %.3f, New Q = %.3f, Immediate Reward = %.3f, Next State(%s) Q = %.2f \n", action.name, t.Q, newQValue, action.reward, nextState.name, futureQ);
                    //}
                }
            }
            
            currentState = nextState;
            if(currentState.isFinal){
                currentState = startingState;
                alpha *= 0.99;
            }  
        }
        
        System.out.printf("Episodes %s\n", iterations);
        System.out.println("Final Q values");
        
        
        for(State state : allStates){
            System.out.println("State: " + state.name);
            for(Action action : state.actions){
                for(Tuple t : action.destinations){
                    System.out.printf("\t(Action = %s -> %s, Q Value = %.3f)\n", t.nextState.name, action.name, t.Q);
                }
            }
            System.out.println();
        }
        
        currentState = startingState;
        double totalReward = 0;
        System.out.printf("Best Policy {%s ->", currentState.name);
        
        while(!currentState.isFinal){
            Action nextAction = currentState.getNextBestActionQ();
            State nextState = nextAction.getBestStateQ();
            
            System.out.printf("%s -> ", nextState.name);

            totalReward += nextAction.reward;
            currentState = nextState;
        }
        System.out.printf("}\nTotal reward: %s\n", totalReward);
    }
    
    public void valueIteration(){
        double gamma = 0.99; // Discount Factor
        Set<State> allStates = new TreeSet<>();
        allStates.add(startingState);
        allStates.addAll(startingState.getChildren());
        
        boolean isRunning = true;
        int iterations = 0;
        
        while(isRunning){
            // Update all the states
            for(State state : allStates){
                if(state.isFinal)
                    continue;
                iterations++;
                //State state = startingState.actions.get(1).destinations.get(0);
                //System.out.println("State: " + state.name);
                List<Double> values = new ArrayList<>();
                double prob = 1f / state.actions.size();
                for(Action action : state.actions)
                    values.add(action.getValue(prob, gamma));
                
                
                double newValue = Collections.max(values);
                double difference = Math.abs(newValue - state.value);
                state.value = newValue;

                if(difference < 0.001)
                    isRunning = false;
                
            }
        }

        System.out.println("Iterations: " + iterations);
        
        // Final values for each state
        printStateValues();
        
        // Print out the best policy
        double totalReward = 0;
        System.out.printf("Best Policy {%s ->", currentState.name);
        
        while(!currentState.isFinal){
            Action nextAction = currentState.getNextBestAction();
            State nextState = nextAction.getBestState();
            
            System.out.printf("%s -> ", nextState.name);

            totalReward += nextAction.reward;
            currentState = nextState;
        }
        System.out.printf("}\nTotal reward: %s\n", totalReward);
        currentState = startingState;
    }
    
    public void firstVisitMonteCarlo(double alpha){
        Set<State> visitedStates = new TreeSet<>();
        double totalReward = 0;
        double totalVisits = 0;
        
        while(!currentState.isFinal){
            Action actionToTake = currentState.getNextAction();
            State nextState = actionToTake.getNextRandomState();
            double reward = actionToTake.reward;    
           
            // Only update on first visit :)
            if(!visitedStates.contains(currentState)){
                currentState.value = currentState.value + alpha * (reward - currentState.value);
                visitedStates.add(currentState);
            }
            
            currentState = nextState;
            totalReward += reward;
            totalVisits++;
            
            System.out.printf("[%s,%s,%.3f]->", actionToTake.name, currentState.name, reward);
        }
        System.out.println();
        
        currentState = startingState;
        System.out.printf("Total reward: %.2f\n", totalReward);
        
        double averageReward = totalReward / totalVisits;
        System.out.printf("Average reward: %.2f\n", averageReward);
    }
    
    public void printStateValues(){
        System.out.println("State Values");
        for(State state : startingState.getChildren())
            System.out.printf("(State=%s, Value=%.3f)\n", state.name, state.value);
        System.out.println();
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
