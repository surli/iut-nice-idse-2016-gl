package fr.unice.idse.model.player;

public class IAFactory extends IA {


	public IAFactory(String name, String token, int difficulty) {
		super(name, token, difficulty);
	}

	public IA setDifficultyIA(String name, String token, int difficulty){
	        IA ia = null;
	        switch (difficulty) {
	            case 1 :
	            	ia = new IAEasy(name, token, difficulty);
	                break;
	            case 2 :
	            	ia = new IAMedium(name, token , difficulty);
	                break;
	            case 3 :
	            	ia = new IAHard(name, token , difficulty);
	                break;
	        }
			return ia;	       
	 }

}
