package constantes;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Font;

public interface Constantes {
    Font FIELD_FONT = new Font(Font.DIALOG, Font.PLAIN, 15);
    Border BORDER = BorderFactory.createLineBorder(Color.BLACK);

    Color BG_COLOR = new Color(26, 202, 206);
    Color WHITE = new Color(255, 255, 255);
    Color BLACK = new Color(0, 0, 0);
    Color ERROR = new Color(255, 0, 0);

    String BIBLI_MAIL = "bibliBoy@gmail.com";
    String BIBLI_MDP = "I@mBibliB0y";

    int AUTEUR_SIZE = 255;
    int TITRE_SIZE = 145;

    int BASIC_MESSAGE = 0;
    int NO_SELECTION = -1;
    int MAX_RES_REACHED = 1;
    int ALREADY_RESERVED = 2;
    int SUCCESS = 3;

    int MAX_RES = 5;
    int MAX_BOOK = 15;

}
