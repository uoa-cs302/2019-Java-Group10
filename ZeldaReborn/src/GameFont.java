import java.awt.Font;

public class GameFont {

	//code-source : https://www.programcreek.com/java-api-examples/?class=java.awt.Font&method=createFont
	public static Font getFont(String file, int size) {
	    try {
	    	//add new font into game from file input
	        Font font = Font.createFont(Font.TRUETYPE_FONT, Home.class.getResourceAsStream(file));
	        font = font.deriveFont(Font.TRUETYPE_FONT, size);
	        return font;
	    } catch (Exception ex) {
	    	//if there is an error, display text with default font, so the game doesn't crash
	    	return new Font("Tahoma", Font.BOLD, size);
	    }
	}	
}