public class MainProgram {
    public static void main(String[] args){          
        MDP mdp = MDP.createStudentLife();
        long startTime = System.nanoTime();
        
        System.out.println("============================== Part I: Monte Carlo");
        for(int i = 1; i <= 50; i++){
            System.out.println("Episode " + i);
            mdp.firstVisitMonteCarlo(0.1);
            System.out.println();
        }
        System.out.println("***** First Visit MonteCarlo Results");
        mdp.printStateValues();
        
        System.out.println("============================== Part II: Value Iteration");
        mdp.resetValues();
        mdp.valueIteration();
        
        System.out.println("============================== Part III: Q-Learning");
        mdp.resetValues();
        mdp.qLearning();
        
        System.out.println("============================== Program Statistics");
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.printf("Program took %.3f ms", duration / 1000000f);
    }
}
