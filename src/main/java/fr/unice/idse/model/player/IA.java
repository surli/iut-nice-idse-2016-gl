package fr.unice.idse.model.player;

import fr.unice.idse.model.Board;

public abstract class IA extends Player {

    public IA(String name, String token /* , int difficulty*/){
        super(name, token);
        /*
        switch (difficulty) {
            case 1 :
                new IAEasy(name, token);
                break;
            case 2 :
                new IAMedium(name, token);
                break;
            case 3 :
                new IAHard(name, token);
                break;
        }

        */
    }

    public void reflexion(Board board) {
        System.out.println("C'est à l'IA de jouer");
    }

}
