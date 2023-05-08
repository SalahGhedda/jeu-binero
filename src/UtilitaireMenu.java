import javax.swing.JOptionPane;

/**
 * Classe qui contient les SP pour gérer les boutons d'options
 * de menu.
 * 
 * S'il y a ajout de bouton, il faut modifier cette classe et y ajouter
 * le comprtement désiré.
 * 
 * @author Pierre Bélisle (copyright 2016)
 * @version H2016
 * @revision H2021
 *
 */
public class UtilitaireMenu {

	
	/**
	 * Vérifie quelle option de menu a été choisie et appelle le sous-programme
	 * correspondant
	 * 
	 * @param grille La grille de jeu en mémoire
	 * @param gui L'interface graphique qui a été cliqué
	 */
	public static void gererMenu(int[][] grille, GrilleGui gui, String reponse){

		/*
		 * Stratégie : Agit simplement comme distributeur de tâche selon
		 * l'option du menu choisi par l'utlisateur du gui.
		 *                  
		 * On a créé un sous-programme pour chaque situation
		 * même si c'était possible de réutiliser en peu de lignes de code 
		 * (voir: montrer_solution)
		 *                  
		 * Doit être modifié si on ajoute des options de menu dans 
		 * le tableau-constante nommé TAB_OPTIONS
		 */
		

		// La réponse est un String, on favorise l'utilisation de equals à ==.
		if(reponse.equals(Constantes.TAB_OPTIONS[Constantes.MONTRER_SOLUTION])) {
			
			montrerSolution(grille);
		}

		else if (reponse
				  .equals(Constantes.TAB_OPTIONS[Constantes.VERIFIER_SOLUTION])) {
			
			verifierSolution(grille, gui);
		}
		
		else if (reponse
				  .equals(Constantes
						  .TAB_OPTIONS[Constantes.REINITIALISER_SOLUTION])) {
			
			
			reinitialiserGrilleGui(grille,gui);

		}
		
	}

	/**
	 * Remet le gui dans son état initial.
	 * 
	 * @param grille
	 * @param gui
	 */
	private static void reinitialiserGrilleGui(int[][] grille, GrilleGui gui) {
		
		
		UtilitaireGrilleGui.remettreGrilleDepart(grille, gui);
		
	}

	/**
	 * Montre la solution dans une autre fenêtre.
	 * 
	 * @param grille La grille du jeu à afficher entièrement
	 */
	private static void montrerSolution(int[][] grille){

		/*
		 * Stratégie : On crée un autre gui qu'on remplit totalement àl'aide
		 * de la grille, par la procédure remplirGuiDepart.
		 *                  
		 */

		// On crée une nouvelle grille sans menu.
		GrilleGui gui = new GrilleGui(Constantes.COULEUR_TEXTE,
                                      Constantes.COULEUR_FOND, 
                                      null,
                                      GrilleGui.DISPOSE);
		
		gui.setGrille(grille);
		
		// On la remplit avec la solution.
		UtilitaireGrilleGui.remplirGuiDepart(grille, gui, Constantes.TOUT);
		
		
	}

	/**
	 * Montre le nombre de différences qu'il y a entre le gui et la grille.
	 * 
     * @param grille La grille de jeu en mémoire.
	 * @param gui L'interface graphique qui a été cliqué.
	 */
	private static void verifierSolution(int[][] grille, GrilleGui gui){

		/*
		 * Stratégie : Comparer la grille avec les cases du gui.  Aussitôt
		 * qu'une case diffère c'est une difféerence, on en affiche le nombre  
		 * sans dire lesquelles.
		 */


		int nbDiff = 0;
		
		for(int i = 0; i < grille.length; i++) {

			for(int j = 0; j < grille[0].length; j++){
			
				// Conversion de la valeur de la case en entier.
				int valeur = UtilitaireGrilleGui
						          .valeurGuiConvertitEnInt(gui.getValeur(i, j));
				
				if(grille[i][j] != valeur && valeur != Constantes.VIDE) {
					
					nbDiff++;
				}
					
			}
			
		}

		    JOptionPane.showMessageDialog(null, 
		    		                     "Il y a " + nbDiff + " différence(s)");
		
	}

}
