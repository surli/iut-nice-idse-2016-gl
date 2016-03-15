package fr.unice.idse.model.player;

import fr.unice.idse.model.Board;

public class IA extends Player {

    public IA(String name, String token  , int difficulty){
        super(name, token);
        switch (difficulty) {
            case 1 :
                new IAEasy(name, token, difficulty);
                break;
            case 2 :
                new IAMedium(name, token , difficulty);
                break;
            case 3 :
                new IAHard(name, token , difficulty);
                break;
        }
    }
}
