/**
 * Classe utilitaire qui permet de choisir un fichier sur disque qui contient
 * les valeurs binaires du jeu binéro.
 * 
 * Le contenu du fichier doit avoir cet allure
 * 
 * 1010
 * 0101
 * 1100
 * 0011
 * 
 * Il ne peut y avoir que GrilleGui.MAX_LIGNES au maximum
 * et le nombre de colonnes est GrilleGui.MAX_COLONNES qui ne sont pas 
 * nécessairement égales mais doivent être pairs.
 * 
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UtilitaireFichier {

	/**
	 * Méthode privée pour �viter la r�p�tition de code
	 * 
	 * @return null si le nom n'est pas valide ou annuler.
	 */
	private static File obtenirFic(String nomFiltre, String filtre){

		//Cr�ation du s�lectionneur de fichier
		JFileChooser fc = new JFileChooser(".");

		File fic = null;

		//On filtre seulement les fichiers avec l'extension .txt
		FileNameExtensionFilter filter = 
				new FileNameExtensionFilter(nomFiltre, filtre);

		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);


		//On obtient le nom du fichier � ouvrir
		int nb = fc.showOpenDialog(null);

		//Seulement si le fichier est choisi
		if(nb == JFileChooser.APPROVE_OPTION)
			fic = fc.getSelectedFile();

		return fic;
	}

	/**
	 * Le nombre de lignes et de colonnes sera d�termin� par ce qui est lu
	 * dans le fichier choisi qui doit �tre valide.
	 * 
	 * Le contenu du fichier choisi ne doit pas d�passer
	 * GrilleGui.MAX_LIGNES et GrilleGui.MAX_COLONNES
	 * 
	 * Aucune validation n'est effectu�e ici.
	 * 
	 * @param nbLignes
	 * @param nbColonnes
	 * @return
	 */
	public static int[][] obtenirGrille(){

		/*
		 * Strat�gie : Pas tr�s efficace, on cr�e la grille la plus grande possible et on va 
		 *                  la rapetisser si ce qui est lu en plus petit.
		 *                  
		 *                  Toutes les cha�nes lues doivent avoir la m�me taille.
		 */
		int[][] grilleMax = new int[GrilleGui.MAX_LIGNES]
				[GrilleGui.MAX_COLONNES];

		//Grille à retourner
		int[][] grille = null;



		//Contiendra le nombre de lignes lues � la fin.
		int ligne = 0;

		//Contiendra le nombre de colonnes lues � la fin.
		int col = 0;

		//Fichier à ouvrir
		File fic;
		
		do{

			//Fichier à ouvrir
			fic = obtenirFic("*", "txt");

			if(fic == null){


				JOptionPane
				.showMessageDialog(null, 
						"Il faut sélectionner un fichier valide");


			} 

			else {

				try {

					// On parcourt le fichier texte en lisant une string (ligne )
					// à la fois
					Scanner fichier = new Scanner(fic);

					ligne = 0;

					while(fichier.hasNextLine()){

						String str = fichier.nextLine();

						//On prend un caract�re de la String et on la change en entier
						for(col = 0; col < str.length(); col++) {

							grilleMax[ligne][col] = Integer
									.valueOf(String.valueOf(str.charAt(col)));
						}

						ligne++;

					}

					//On crée une grille de la taille exacte
					grille = new int[ligne][col];

					//On copie le grand tableau dans le petit
					UtilitaireTableau.copierTableau2D(grilleMax, grille, ligne, col);

					fichier.close();


				} catch (FileNotFoundException e) {				
					e.printStackTrace();
				}

			  }

			}while(fic == null);

		return grille;
	}
}