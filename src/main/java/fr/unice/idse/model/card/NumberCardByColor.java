package fr.unice.idse.model.card;

/**
 * Classe permettant de stocker le nombre de cartes de la même couleur présente dans une partie de UNO
 */
public class NumberCardByColor implements Comparable<NumberCardByColor> {
    private Color color;
    private int number;

    // --- Constructeur

    public NumberCardByColor(Color color,int number) {
        this.color=color;
        this.number=number;
    }

    // --- Getters
    public Color getColor() { return color; }
    public int getNumber() { return number; }

    // --- Setters
    public void setColor(Color color) { this.color = color; }
    public void setValue(int number) { this.number = number; }


    //TODO -- compareTo --
    @Override
    public int compareTo(NumberCardByColor obj) {

        return Integer.compare(number, obj.getNumber());
    }
}
