package fr.unice.idse.model.player;

public class IAFactory {

	public static IA setIA(String name, int difficulty){
	        IA ia = null;
	        switch (difficulty) {
	            case 1 :
	            	ia = new IAEasy(name, difficulty);
	                break;
	            case 2 :
	            	ia = new IAMedium(name, difficulty);
					break;
	            case 3 :
	            	ia = new IAHard(name, difficulty);
					break;
	        }
			return ia;	       
	 }
}
