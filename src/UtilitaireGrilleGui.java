import java.awt.*;
import java.util.Random;
import java.lang.String;

/**
 * Contient les differentes méthodes permettant de jouer au jeu Binéro et permettant aussi
 * d'effectuer les changements dû aux differentes interactions faites avec le jeu et l'utilisateur
 *
 * @author Salah Eddine Ghedda
 * @author Kevin Chan
 * @author Anthony Niculescu-Cornejo
 * @version H2021
 */

public class UtilitaireGrilleGui {

	static Random rand = new Random();

	/**
	 * La méthode remplirGuiDepart affiche un certain nombre de cases selon le nombre
	 * de pourcentage qu'elle recoit en parametre. Les cases à afficher sont aléatoires
	 *
	 * @param grille, description
	 * @param gui, description
	 * @param nbPourcentage
	 * @return rien
	 */

	public static void remplirGuiDepart(int[][] grille, GrilleGui gui, double nbPourcentage) {

		//On trouve le nombre de cases du jeu
		int nbCases = gui.getNbColonnes() * gui.getNbLignes();

		//On calcule le nombre de cases qui seront affichés
		double nbCasesAffichees = nbCases * nbPourcentage;

		//indice de ligne et colonne d'une case donnée
		int ligne; int colonne;


		for(int k = 0; k < nbCasesAffichees; k++)
		{

			//On genere aleatoirement les indices d'une case
			ligne = rand.nextInt(grille.length);
			colonne = rand.nextInt(grille[0].length);

			//Verifier si la case a deja été generé aleatoirement
			if(gui.caseEstDesactive(ligne, colonne))
			{
				//On revient au debut de la boucle pour generer d'autres indices aleatoirement
				k -= 1;
				continue;
			}

			//On affiche la case selon la grille du fichier et on desactive la case
			gui.setValeur(ligne,colonne, String.valueOf(grille[ligne][colonne]));
			gui.desactiverCase(ligne, colonne);
		}
	}

	/**
	 * La méthode getNbValeursValide compte le nombre de valeurs valides en comparant la valeur rentrée sur le jeu
	 * et la valeur de la grille du solutionnaire.Elle retroune le nombre de valeurs valides
	 *
	 * @param grille
	 * @param gui
	 * @return nombre de cases valides rentrées dans le jeu
	 */

	public static int getNbValeursValide(int[][] grille, GrilleGui gui) {

		//Initialiser le compteur de nombre de valeurs valides à 0
		int nbValeursValides = 0;

		//Parcourir toutes les cases du jeu
		for(int i = 0; i < gui.getNbLignes(); i++)
		{
			for(int j = 0; j < gui.getNbColonnes(); j++)
			{
				//verifier si la valeur de la case correspond à la valeur de la solution
				if(casePasVide(gui, i, j) && valeurGuiConvertitEnInt(gui.getValeur(i, j)) == grille[i][j])
				{
					nbValeursValides += 1; //incrémenter le nombre de valeur valides
				}
			}
		}

		return nbValeursValides;
	}

	/**
	 * La méthode obtenirErreurs compte le nombre d'erreurs en comparant la valeur rentrée sur le jeu
	 * et la valeur de la grille du solutionnaire.Elle retroune le nombre de valeurs valides
	 *
	 * @param gui
	 * @return nombre d'erreurs présent dans le jeu
	 */

	public static int obtenirErreurs(GrilleGui gui) {

		//Initialiser le compteur de nombre d'erreurs à 0
		int nbErreurs = 0;

		//parcourir toutes les cases du jeu
		for(int i = 0; i < gui.getNbLignes(); i++)
		{
			for(int j = 0; j < gui.getNbColonnes(); j++)
			{
				//Verifier si la case est a une couleur indiquant la presence d'une erreur
				if(gui.getCouleurFond(i, j) != Constantes.COULEUR_FOND || gui.getCouleurTexte(i, j) != Constantes.COULEUR_TEXTE)
				{
					nbErreurs++; //incrémenter le nombre d'erreurs
				}
			}
		}

		return nbErreurs;
	}

	/**
	 * La méthode grilleEstPleine vérifie si la grille du jeu est pleine. Elle retourne true or false dépendament
	 *si la grille est pleine ou non
	 *
	 * @param gui
	 * @return boolean
	 */

	public static boolean grilleEstPleine(GrilleGui gui) {

		//parcourir toutes les cases du jeu
		for(int i = 0; i < gui.getNbLignes(); i++)
		{
			for(int j = 0; j < gui.getNbColonnes(); j++)
			{
				//verifier si la case n'est pas vide
				if(!casePasVide(gui, i , j))
				{
					return false; //retourner faux si la case est vide
				}
			}
		}
		return true;
	}

	/**
	 * La méthode reinitialiserCouleur remet les couleurs de départ du texte et des cases
	 * gris pour le texte et blanc pour les cases
	 *
	 * @param gui
	 * @return rien
	 */

	public static void reinitialiserCouleur(GrilleGui gui)
	{
		//parcourir toutes les cases du jeu
		for(int i = 0; i < gui.getNbLignes(); i++)
		{
			for(int j = 0; j < gui.getNbColonnes(); j++)
			{
				//On remet les couleurs du début du jeu
				gui.setCouleurFond(i, j, Constantes.COULEUR_FOND);
				gui.setCouleurTexte(i, j, Constantes.COULEUR_TEXTE);
			}
		}
	}

	/**
	 * La méthode afficherChangementGrilleGui affiche les changement d'une case lors d'un clic sur celle-ci.
	 * La valeur affichée sur la case change apres chaque clic en passant de vide à 0 puis 1, ensuite on recommence
	 * ce cycle
	 *
	 * @param gui
	 * @return rien
	 */

	public static void afficherChangementGrilleGui(GrilleGui gui)
	{
		//ligne et colonne de la case cliquée
		int ligneDeLaCase;
		int colonneDeLaCase;

		int valeurConvertie;

		//verifier si une case a été cliquée
		if(gui.caseEstCliquee())
		{
			//On obtient la ligne et colonne de la case
			ligneDeLaCase = gui.getPosition().ligne;
			colonneDeLaCase = gui.getPosition().colonne;

			//Convertir la valeur en string de la case en un entier
			valeurConvertie = valeurGuiConvertitEnInt(gui.getValeur(ligneDeLaCase, colonneDeLaCase));


			//On change la valeur de la case du jeu selon la valeur actuelle

			if(valeurConvertie == Constantes.VALEUR1)
			{
				//Changer 0 en 1
				gui.setValeur(ligneDeLaCase, colonneDeLaCase, String.valueOf(Constantes.VALEUR2));
			}
			else if(valeurConvertie == Constantes.VALEUR2)
			{
				//Changer 1 en case vide
				gui.setValeur(ligneDeLaCase, colonneDeLaCase, Constantes.VIDE_GUI);
			}
			else
			{
				//Changer case vide en 0
				gui.setValeur(ligneDeLaCase, colonneDeLaCase, String.valueOf(Constantes.VALEUR1));
			}
		}
	}

	/**
	 * La méthode ajusterGui changes les couleurs des cases et/ou du text pour les cases contenant
	 * potentiellement une erreur
	 *
	 * @param gui
	 * @return rien
	 */

	public static void ajusterGui(GrilleGui gui)
	{
		//parcourir les lignes du jeu
		for(int i = 0; i < gui.getNbLignes(); i++)
		{
			//verifier s'il y a autant de 0 et de 1 sur la meme ligne
			//changer la couleur de la ligne au cas contraire
			if(!AutantUnEtZeroLigne(gui, i))
			{
				for(int j = 0; j < gui.getNbColonnes(); j++)
				{
					gui.setCouleurFond(i, j, Constantes.COULEUR_VALEUR_TROP);
				}
			}
		}

		////parcourir les colonnes du jeu
		for(int j = 0; j < gui.getNbColonnes(); j++)
		{
			//verifier s'il y a autant de 0 et de 1 sur la meme colonne
			//changer la couleur de la colonne au cas contraire
			if(!AutantUnEtZeroColonne(gui, j))
			{
				for(int i = 0; i < gui.getNbLignes(); i++)
				{
					gui.setCouleurFond(i, j, Constantes.COULEUR_VALEUR_TROP);
				}
			}
		}
		//on change la couleur des lignes ou des colonnes identiques
		colorierMemesLignes(gui);
		colorierMemesColonnes(gui);

		//On change la couleur des valeurs consecutives plus que deux fois
		colorierValeursConsecutivesLignes(gui);
		colorierValeursConsecutivesColonnes(gui);

	}

	/**
	 * La méthode remettreGrilleDepart change toutes les cases du jeu en des cases vides sauf les cases qui ont
	 * été affichées au début du jeu
	 *
	 * @param gui
	 * @param grille
	 * @return rien
	 */

	public static void remettreGrilleDepart(int[][] grille, GrilleGui gui)
	{
		//Parcourir toutes les cases du jeu
		for(int i = 0; i < gui.getNbLignes(); i++)
		{
			for(int j = 0; j < gui.getNbColonnes(); j++)
			{
				//verifier si la case n'est pas désactivé
				//Une case est désactivé car elle a été affiché au debut du jeu
				if(!gui.caseEstDesactive(i, j))
				{
					//vider la case
					gui.setValeur(i, j, Constantes.VIDE_GUI);
				}
				//on remet les couleurs du debut de jeu
				gui.setCouleurTexte(i, j, Constantes.COULEUR_TEXTE);
				gui.setCouleurFond(i, j, Constantes.COULEUR_FOND);
			}
		}
	}

	/**
	 * La méthode valeurGuiConvertitEnInt change toutes les cases du jeu en des cases vides sauf les cases qui ont
	 * été affichées au début du jeu
	 *
	 * @param valeur
	 * @return valeur entière d'une case du jeu
	 */

	public static int valeurGuiConvertitEnInt(String valeur)
	{
		//On convertit les valeurs de String à un entier

		if(valeur.equalsIgnoreCase("1"))
		{
			return Constantes.VALEUR2;
		}
		else if(valeur.equalsIgnoreCase("0"))
		{
			return Constantes.VALEUR1;
		}
		else
		{
			return Constantes.VIDE;
		}
	}

	/**
	 * La méthode casePasVide vérifie si une case est vide ou pas
	 *
	 * @param gui
	 * @param ligneDeLaCase
	 * @param colonneDeLaCase
	 * @return true si la case est pas vide, return false si la case est vide
	 */

	public static boolean casePasVide(GrilleGui gui, int ligneDeLaCase, int colonneDeLaCase)
	{
		//retourner vrai si la case n'est pas vide, et faux dans le cas contraire
		return valeurGuiConvertitEnInt(gui.getValeur(ligneDeLaCase, colonneDeLaCase)) != Constantes.VIDE;
	}

	/**
	 * La méthode AutantUnEtZeroColonne vérifie s'il y a autant de 1 et de 0 sur une meme colonne
	 *
	 * @param gui
	 * @param colonne
	 * @return true s'il y a autant de de 1 et 0 sur la colonne, sinon retourne false
	 */

	public static boolean AutantUnEtZeroColonne(GrilleGui gui, int colonne)
	{
		//Iniatialiser le nombre de 0 et 1 sur la meme colonne
		int compteurRep1 = 0;
		int compteurRep0 = 0;

		int valeurConvertie;

		//parcourir toutes les lignes du jeu
		for(int i = 0; i < gui.getNbLignes(); i++)
		{
			//On obtient l'entier de la valeur de la case
			valeurConvertie = valeurGuiConvertitEnInt(gui.getValeur(i, colonne));

			//On incrémente les compteurs de repitions selon la valeur présente dans la case

			if(valeurConvertie == 0)
			{
				compteurRep0++;
			}
			else if(valeurConvertie == 1)
			{
				compteurRep1++;
			}
			//Si une case de la colonne est vide on retourne vrai
			//Le nombre de 0 et 1 n'est pas important si toute la colonne n'est pas remplie
			else if(!casePasVide(gui, i, colonne))
			{
				return true;
			}
		}
		//retourner vrai s'il y a autant de 1 et de 0 sur la colonne
		return compteurRep0 == compteurRep1;
	}

	/**
	 * La méthode AutantUnEtZeroLigne vérifie s'il y a autant de 1 et de 0 sur une meme ligne
	 *
	 * @param gui
	 * @param ligne
	 * @return true s'il y a autant de de 1 et 0 sur la ligne, sinon retourne false
	 */

	public static boolean AutantUnEtZeroLigne(GrilleGui gui, int ligne)
	{
		//Iniatialiser le nombre de 0 et 1 sur la meme ligne
		int compteurRep1 = 0;
		int compteurRep0 = 0;

		int valeurConvertie;

		//parcourir toutes les colonnes du jeu
		for(int j = 0; j < gui.getNbColonnes(); j++)
		{
			//On obtient l'entier de la valeur de la case
			valeurConvertie = valeurGuiConvertitEnInt(gui.getValeur(ligne, j));

			//On incrémente les compteurs de repitions selon la valeur présente dans la case

			if(valeurConvertie == Constantes.VALEUR1)
			{
				compteurRep0++;
			}
			else if(valeurConvertie == Constantes.VALEUR2)
			{
				compteurRep1++;
			}
			//Si une case de la ligne est vide on retourne vrai
			//Le nombre de 0 et 1 n'est pas important si toute la ligne n'est pas remplie
			else if(!casePasVide(gui, ligne, j))
			{
				return true;
			}
		}
		//retourner vrai s'il y a autant de 1 et de 0 sur la ligne
		return compteurRep0 == compteurRep1;
	}

	/**
	 * La méthode colorierMemesLignes change la couleur des cases des lignes qui sont identiques
	 *
	 * @param gui
	 * @return rien
	 */

	public static void colorierMemesLignes(GrilleGui gui)
	{
		int nbMemeValeur;

		//deux boucles qui vont permettre de comparer deux lignes
		for (int ligneActuelle = 0; ligneActuelle < gui.getNbLignes() - 1; ligneActuelle++) {

			for(int ligneDeComparaison = 1; ligneDeComparaison < gui.getNbLignes(); ligneDeComparaison++) {

				//on remet le nombre de meme valeur à 0 à chque fois qu'on passe à une nouvelle ligne de comparaison
				nbMemeValeur = 0;

				//on compare les cases des deux lignes
				for(int j = 0; j < gui.getNbColonnes(); j++) {

					//verifier si les deux cases qu'on compare sont identiques
					if((gui.getValeur(ligneActuelle, j)).equalsIgnoreCase(gui.getValeur(ligneDeComparaison, j)) && casePasVide(gui, ligneActuelle, j)) {

						nbMemeValeur++; //incrémenter le nombre des memes valeurs consécutives

						//verifier si toutes les cases des deux deux lignes sont identiques
						//on s'assure qu'on ne compare pas la meme ligne
						if(nbMemeValeur == gui.getNbColonnes() && ligneActuelle != ligneDeComparaison) {

							//on parcourt les valeur consécutives
							for(int caseIdentique = 0; caseIdentique < gui.getNbColonnes(); caseIdentique++) {

								//on parcourt les cases des lignes identiques
								gui.setCouleurFond(ligneActuelle, caseIdentique, Color.RED);
								gui.setCouleurFond(ligneDeComparaison, caseIdentique, Color.RED);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * La méthode colorierMemesColonnes change la couleur des cases des colonnes qui sont identiques
	 *
	 * @param gui
	 * @return rien
	 */

	public static void colorierMemesColonnes(GrilleGui gui) {

		int nbMemeValeur;

		//deux boucles qui vont permettre de comparer deux colonnes
		for (int colonneActuelle = 0; colonneActuelle < gui.getNbColonnes() - 1; colonneActuelle++) {

			for(int colonneDeComparaison = 1; colonneDeComparaison < gui.getNbColonnes(); colonneDeComparaison++) {

				//on remet le nombre de meme valeur à 0 à chque fois qu'on passe à une nouvelle colonnes de comparaison
				nbMemeValeur = 0;

				//on compare les cases des deux colonnes
				for(int i = 0; i < gui.getNbLignes(); i++) {

					//verifier si les deux cases qu'on compare sont identiques
					if((gui.getValeur(i, colonneActuelle)).equalsIgnoreCase(gui.getValeur(i, colonneDeComparaison)) && casePasVide(gui, i, colonneActuelle)) {

						nbMemeValeur++; //incrémenter le nombre des memes valeurs consécutives

						//verifier si toutes les cases des deux deux colonnes sont identiques
						//on s'assure qu'on ne compare pas la meme colonne
						if(nbMemeValeur == gui.getNbLignes() && colonneActuelle != colonneDeComparaison) {

							//on parcourt les cases des colonnes identiques
							for(int caseIdentique = 0; caseIdentique < gui.getNbLignes(); caseIdentique++) {

								//changer la couleur des colonnes identiques
								gui.setCouleurFond(caseIdentique, colonneActuelle, Color.GREEN);
								gui.setCouleurFond(caseIdentique, colonneDeComparaison, Color.GREEN);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * La méthode colorierValeursConsecutivesLignes change la couleur du texte des valeurs qui sont consécutives
	 * plus que deux fois sur la ligne
	 *
	 * @param gui
	 * @return rien
	 */

	public static void colorierValeursConsecutivesLignes(GrilleGui gui)
	{
		int nbConsecutives;

		//on parcourt les lignes de la grille du jeu
		for(int ligne = 0; ligne < gui.getNbLignes(); ligne++)
		{
			//on utilise deux boucles pour comparer une case actuelle aux cases qui suivent sur la meme ligne
			for(int caseActuelle = 0; caseActuelle < gui.getNbColonnes(); caseActuelle++)
			{
				nbConsecutives = 0;

				for(int caseComparaison = caseActuelle + 1; caseComparaison < gui.getNbColonnes(); caseComparaison++)
				{
					//verifier s'il y a des valeurs consecutives à la case actuelle
					if(gui.getValeur(ligne, caseActuelle).equalsIgnoreCase(gui.getValeur(ligne, caseComparaison)) &&  caseActuelle != caseComparaison )
					{
						nbConsecutives++; //incrémenter le nombre de valeurs consécutives

						//verifier si au moins de 2 valeurs sont consécutives
						if(nbConsecutives >= 2)
							for(int m = 0; m <= nbConsecutives; m++)
							{
								//changer la couleur du texte de la valeur
								gui.setCouleurTexte(ligne, caseActuelle + m, Color.CYAN);
							}
					}
					else
					{
						break; //si on a moins de 2 valeurs consécutives on sort de la boucle de comparaison
					}
				}
			}
		}
	}

	/**
	 * La méthode colorierValeursConsecutivesColonnes change la couleur du texte des valeurs qui sont consécutives
	 * plus que deux fois sur la colonne
	 *
	 * @param gui
	 * @return rien
	 */

	public static void colorierValeursConsecutivesColonnes(GrilleGui gui)
	{
		int nbConsecutives;

		//on parcourt les lignes de la grille du jeu
		for(int j = 0; j < gui.getNbColonnes(); j++)
		{
			//on utilise deux boucles pour comparer une case actuelle aux cases qui suivent sur la meme ligne
			for(int i = 0; i < gui.getNbLignes(); i++)
			{
				nbConsecutives = 0;

				for(int k = i + 1; k < gui.getNbLignes(); k++)
				{
					//verifier s'il y a des valeurs consecutives à la case actuelle
					if(gui.getValeur(i, j).equalsIgnoreCase(gui.getValeur(k, j)) &&  i != k )
					{
						nbConsecutives++; //incrémenter le nombre de valeurs consécutives

						//verifier si au moins de 2 valeurs sont consécutives
						if(nbConsecutives >= 2)
							for(int m = 0; m <= nbConsecutives; m++)
							{
								//changer la couleur du texte de la valeur
								gui.setCouleurTexte(i + m, j, Color.CYAN);
							}
					}
					else
					{
						break; //si on a moins de 2 valeurs consécutives on sort de la boucle de comparaison
					}
				}
			}
		}
	}

}
