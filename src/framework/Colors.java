package framework;

import java.awt.Color;

public enum Colors {
    GREEN(50, 205, 50), /* USES: Circle, line */
    LIGHTGREEN(255, 59, 48), /* USES: Borders, */
    DARKGREEN(75, 217, 100), /* USES: Dot inside circles */
	NAVYGREEN(35, 140, 35); /* USES: connection cricle */
	

    private final int r;
    private final int g;
    private final int b;
    private final String rgb;

    /**
     * Gets the value
     * @param r gets the red from the color
     * @param g gets the green from the color
     * @param b gets the blue from the color
     */
    private Colors(final int r,final int g,final int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.rgb = r + ", " + g + ", " + b;
    }

    /**
     * Gets the color.
     * @return the color
     */
    public Color getColor(){
        return new Color(r,g,b);
    }    
}