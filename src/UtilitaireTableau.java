/**
 * Utilitaire qui contient des SP pour gérer des tableaux
 * (comme java.util.Arrays)
 * 
 * @author Pierre Bélisle (copyright 2016)
 * @version H2016
 * @revision H2021
 *
 */
public class UtilitaireTableau {
	
	/**
	 * 
	 * Retourne le nombre d'occurrences d'une valeur dans un vecteur ligne.
	 * 
	 * @param tab Le tableau à considérer
	 * @param occurrence La valeur cherchée
	 * @return Si la valeur est dans le tableau.
	 */
	
	/**
	 * 
	 */
	public static int nbOccurrenceLigne(int[] tab, int occurrence){
		
		/*
		 * Stratégie : On compare chaque valeur du tableau avec le paramèetre
		 * occurrence et s'ils sont égales, on ajoute 1 au compteur qui sera
		 * retournée.
		 */
		
		int nb = 0;
		
		for(int i = 0; i < tab.length;i++) {
			
			    // Ajoute 1 si la valeur du tableau == occurrence
				nb += booleenAentier(tab[i], occurrence);
		}
		
		return nb;
	}
	
	/*
	 *  Si la valeur == occurence, on retourne 1 et sinon on retourne 0.
	 *  
	 *  (revient à convertir l'évaluation booléenne en entier).
	 */
	private static int booleenAentier(int valeur, int occurence) {
		
	
		return (valeur == occurence)?1:0;
	}

	/**
	 * Permet de copier un tableau 2D dans un autres de la case [0,0] à
	 * la case [nbLignes,nbColonnes]
	 * 
	 * @param grilleSrc Grille qui sera copié dans grilleDest
	 * @param grilleDest Grille qui reçoit les valeurs provenant de grilleSrc
	 * @param nbLignes Dernière ligne
	 * @param nbColonnes Dernière colonne
	 */
	public static void copierTableau2D(int[][]grilleSrc, 
			                           int[][]grilleDest,
			                           int nbLignes,
			                           int nbColonnes){
		
		/*
		 *Stratégie : On parcourt simplement les deux dimensions du premier 
		 *tableau pour le copier dans le second, case par case. 
		 *
		 * 
		 */
		for(int i = 0; i < nbLignes; i++) {
				
			//***NOTE: On aurait pou utiliser java.util.Arrays.clone ici
			// mais cela ne fonctionne pas pour les tableaux 2D.
			for(int j = 0; j < nbColonnes; j++) {
				
				grilleDest[i][j] = grilleSrc[i][j];
				
			}
		}
	}
}
