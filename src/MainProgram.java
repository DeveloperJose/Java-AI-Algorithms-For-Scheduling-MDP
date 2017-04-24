public class MainProgram {
    public static void main(String[] args){          
        MDP mdp = MDP.createStudentLife();
        long startTime = System.nanoTime();
        
        /*for(int i = 1; i <= 50; i++){
            System.out.println("===== Episode " + i);
            mdp.firstVisitMonteCarlo(0.1);
        }
        System.out.println("===== First Visit MonteCarlo Results");
        mdp.printStateValues();
        */
        
        mdp.valueIteration();
        mdp.printStateValues();
        mdp.run();
        
        System.out.println("===== Program Statistics");
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.printf("Program took %.3f ms", duration / 1000000f);
    }
}
