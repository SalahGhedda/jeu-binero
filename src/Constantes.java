import java.awt.Color;

/**
 * Contient les déclarations de constantes globales pour le projet de Binéro
 * (voir énoncé fourni).
 * 
 * @author Pierre Bélisle (copyright 2016)
 * @version H2016
 * @révision H2021
 */
public class Constantes {
	
	// Constantes initiales pour les couleurs du jeu (valeur arbitraires)
	public static final Color COULEUR_FOND = Color.WHITE;
	public static final Color COULEUR_TEXTE = Color.BLACK;

	public static final Color COULEUR_LIGNE_IDENTIQUE = Color.RED;
	public static final Color COULEUR_COLONNE_IDENTIQUE = Color.GREEN;

	public static final Color COULEUR_VALEUR_TROP = Color.YELLOW;
	
	public static final Color COULEUR_TROP_CONSECUTIF = Color.CYAN;
	
	// Le nombre d'indices (valeurs) au départ à donner en %
	//(faible = 50%, moyen = 25%, fort = 10%).
	public static final double NB_POURCENTAGE = .25;
	public static final double TOUT = 1;

	// Les valeurs possibles d'une case dans la grille solution.
	public static final int VIDE = -1;
	public static final int VALEUR1 = 0;
	public static final int VALEUR2 = 1;
	
	// Vide une case dans le gui.
	public static final String VIDE_GUI = " ";

	// Nécessaire à l'affichage des options du menu.
	public static final String[] TAB_OPTIONS = 
		                              {"Solution",
		                               "Vérifier"
		                               ,"Réinitialiser",
		                               "Nouvelle partie"};

	// Position dans le tableau des options du menu.
	public static final int MONTRER_SOLUTION = 0;
	public static final int VERIFIER_SOLUTION = 1;
	public static final int REINITIALISER_SOLUTION = 2;
	public static final int NOUVELLE_PARTIE = 3;

}