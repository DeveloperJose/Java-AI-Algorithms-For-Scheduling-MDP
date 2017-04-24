public class MainProgram {
    public static void main(String[] args){          
        MDP mdp = MDP.createStudentLife();
        //System.out.print(mdp);
        mdp.step();
    }
}
