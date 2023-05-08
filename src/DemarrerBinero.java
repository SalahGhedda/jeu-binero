import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
Le binero est un cousin éloigné du Sudoku, un savant mélange de jeu de chiffres 
et de jeu de logique. Arriverez-vous à replacer tous les 0 et les 1 dans la 
grille ? 

La règle du jeu est simple !
Il suffit en effet de remplir la grille avec des 0 et des 1.

Les contraintes sont les suivantes :

Il ne peut y avoir plus de deux 0 ou deux 1 consécutifs
Il y a le même nombre de 0 et de 1 sur chaque ligne et chaque colonne
Les lignes ou colonnes identiques sont interdites.

source : http://www.sudoku-land.com/binero/binero.php


 *@author  Pierre Bélisle
 *@version Copyright (2016)
 *@revision Copyright (2021)
 *
 */

public class DemarrerBinero {

	// Nécessaire pour la saisi du clic au clavier.
	private static final int DELAI = 100;

	/*
	 * Stratégie globale : On utilise les SP des différents modules pour 
	 * obtenir une grille solution que l'on se sert pour remplir  la grilleGui. 
	 * 
	 * C'est ici qu'on gère la boucle principale qui se termine si l'utilisateur
	 * quitte ou s'il réussit.
	 * 
	 * De plus, on obtient s'il y a eu un clique et selon, on modifie le contenu 
	 * de la case de la grille en conséquence ainsi que les couleurs selon les 
	 * règles décrite dans l'énoncé OU c'est une option de menu, alors on 
	 * délàgue au module prévu.
	 */
	public static void main(String[] args) {


		// Pour les problèmes de couleur sur Mac.
		ajusterCouleurAuMac();
		
		//Création de l'interface graphique qui permet de jouer
		GrilleGui gui = new GrilleGui(Constantes.COULEUR_TEXTE, 
				                      Constantes.COULEUR_FOND, 
				                      Constantes.TAB_OPTIONS,
				                      GrilleGui.QUITTE);
		

		
		// Pour retenir la taille de la grille (après son obtention).	
		int taille;
		
		// Tant que l'utilisateur n'appuie pas sur le X de la fenêtre.
		while(true) {
		

			// Sera mis à true si l'utilisateur réussit.
			boolean quitter = false;

			// Obtenir une grille solution valide.
			int[][] grille = UtilitaireFichier.obtenirGrille();		
				

			//Création de l'interface graphique qui permet de jouer
			gui.setGrille(grille);

			// Doit être obtenue après Avoir réinitialiser le gui.
			taille = gui.getNbLignes() * gui.getNbColonnes();

			//Remplit le GUI avec quelques une des cases de la solution 
			//Pour changer le niveau, simplement à changer le pourcentage 
			//fourni au départ (COnstantes.NB_POURCENTAGE).

			//NOTE : Pourrait être demandé à l'utilisateur éventuellement 
			UtilitaireGrilleGui.remplirGuiDepart(grille, 
					                             gui, 
					                             Constantes.NB_POURCENTAGE);

			
			
			// Boucle infinie qui se termine si l'utilisateur gagne ou 
			// s'il quitte en cliquant sur X
			while(!quitter){

				// IMPORTANT pour donner le nom que le clic soit saisi 
				// par le gui.
				gui.pause(DELAI);


				// OS'il y a eu un clic.
				if(gui.caseEstCliquee()){

					// Change la couleur du gui sur des erreurs.
					gererClic(gui);
					
				
					// S'il a fini, bravo et on quitte.
					if(UtilitaireGrilleGui
							.getNbValeursValide(grille, gui) == taille){


						JOptionPane.showMessageDialog(null, 
								"Bravo pour la persévérance, " + 
								"la partie est terminée!");
						
						
						quitter = true;					
					}				

					// Il est possible de résoudre le Binéro mais que nos 
					// solutions soient différentes.  OAlord toutes les cases 
					// sont blanches et remplies mais pas identiwues à la 
					// solution. (trouver une autre solution).
					else if (UtilitaireGrilleGui.obtenirErreurs(gui) == 0 &&
							UtilitaireGrilleGui.grilleEstPleine(gui)) {
						
						
						JOptionPane.showMessageDialog(null, 
								"Bravo, votre solution est valide " + 
								"mais ne correspond pas à la nôtre!" +
								"\nVous pouvez remettre la grille dans son " +
								"état initial ou continuer");

					}
				}

				// L'utilisateur a cliqué sur un des boutons d'options
				else if(gui.optionMenuEstCliquee()) {
					
					String reponse = gui.getOptionMenuClique();
				

					if(reponse.equals(Constantes.TAB_OPTIONS[Constantes.NOUVELLE_PARTIE])) {
						
						quitter = true;
					}
					
					else {
						UtilitaireMenu.gererMenu(grille, gui, reponse);
					}
				}

			}
			
		}
	}

	
	/*
	 * Change la couleur du gui sur des erreurs.
	 */
	private static void gererClic(GrilleGui gui) {
		
		// À chaque tour on réinitialise les couleurs.
		UtilitaireGrilleGui.reinitialiserCouleur(gui);

		// Affiche du changement des valeurs lors d'un clic
		UtilitaireGrilleGui.afficherChangementGrilleGui(gui);

		// On ajuste le gui en conséquence.
		UtilitaireGrilleGui.ajusterGui(gui);

	}

	/*
	 * S'assure d'utiliser le bon look and feel pour que les couleurs 
	 * fonctionnent sur un Mac.
	 */
	private static void ajusterCouleurAuMac() {
		
		try {
			
			UIManager
			.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			
		} catch (Exception e) {  
			
			e.printStackTrace();
		}
	}
}