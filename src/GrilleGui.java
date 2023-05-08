import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Grille de jeu rectangulaire d'au maximum MAX_LIGNES par MAX_COLONNES
 * qui permet d'obtenir s'il y  eu un clic, la position du clic et modifier le 
 * contenu de la case (couleur et texte).
 * 
 * Il est possible aussi d'ajouter des boutons de menu.  Dans ce cas la méthode, 
 * estBoutonMenu retourne vrai et getTexteMenu retourne le texte contenu dans 
 * le bouton.  Ces boutons sont créés en bas de la fenêtre à partir d'un tableau
 * de String fourni lors de l'appel à new GrilleGui (mettre null si non désiré).
 * 
 * Utile pour des TP1 en inf111 (jeux tels Sudoku, Binero, 421, dessin, ...).
 * 
 * 
 * ***Le code a été conçu pour entrer dans un seul et même fichier.  Le tout
 * pour faciliter l'intégration de cette classe dans le travail des élèves. 
 * Une meilleure conception sera enseignée d'ici la fin de session.  Les seules
 * méthodes utiles pour ce travail sont :
 * 
 * - GrilleGui(int nbLignes, int nbColonnes, 
			   Color couleurTexte, Color couleurFond,
			   String[] tabMenus,
			   int modeFermeture)
 * - getPosition()
 * - optionMenuEstCliquee()
 * - getOptionMenuClique()
 * - getValeur(int ligne, int colonne)
 * - setValeur(int ligne, int colonne, String texte)
 * - getNbLignes()
 * - getNbColonnes() 
 * - setCouleurFond(int ligne, int colonne, Color couleurFond)
 * - setCouleurTexte(int ligne, int colonne, Color couleurTexte)
 * - getCouleurTexte(int ligne, int colonne)
 * - getCouleurFond(int ligne, int colonne)
 * - caseEstCliquee()
 * - desactiverCase(int ligne, int colonne)
 * - pause(int delai)
 * - setGrille(int[][] grille)
 * 
 * @author Pierre Bélisle (copyright 2016)
 * @version H2016
 * @revision Pierre Bélisle H2021
 */
public class GrilleGui  implements Runnable{

	/*
	 * STRATÉGIE : On met des boutons dans un panneau mais on les retient aussi 
	 * dans une grille (tableau2D).  Une classe interne MonJButton hérite de 
	 * JButton auquelle on ajoute des attributs pour retenir la position
	 * du bouton dans la grille.  Tout cela pour éviter la recherche du 
	 * bouton lors d'un clic (deux boucles en moins).
	 *                        
	 * Un attribut booléen permet de retenir si un bouton a été cliqué. 
	 * L'attribut est remis à faux après une lecture de la position par son 
	 * accesseur (getEstClique).
	 */

	// Limite (pour voir le texte comme il faut).
	public static final int MAX_LIGNES = 20;
	public static final int MAX_COLONNES = 20;
	
	//Dimension du texte des boutons.
	public static final int TAILLE_CAR = 40;
	
	// Deux modes de fermeture du gui.  On quitte le programme ou on 
	// dispose juste la fenêtre.
	 public static final int QUITTE = JFrame.EXIT_ON_CLOSE;
	 public static final int DISPOSE = JFrame.DISPOSE_ON_CLOSE;

	// On compose dans un cadre.
	private JFrame cadre = new JFrame();

	// La grille qui sera affichée qui
	// retient aussi sa position(classe interne décrite à la fin).
	private MonJButton [][] gui;

	// La position en ligne,colonne du dernier clic (y,x).
	private Coordonnee dernierClic;

	// Mis à vrai lors d'un clic et à faux dans getPosition.
	private boolean estClique;
	
	// Retenir la taille de la grille.
	private int nbLignes;
	private int nbColonnes;

	// Les couleurs des cases.
	private Color couleurTexte;
	private Color couleurFond;

	// La taille de l'écran.
	private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

	// Retenir le tableau des options de menus.
	private String [] tabMenus;
	
	// Pour les options de meus du panneau du bas.
	private boolean estBoutonMenu;
	
	// Contiendra le texte bouton cliqué s'il y a eu un clic sur un des boutons 
	// du menu et il est mis à null après getOptionMenu.
	private String optionClique;
	
	// Retenir le mode de fermeture désiré.
	private int modeFermeture;
	
	
	/**
	 * Créé une grille de 1 par 1.  Le gui doit ensuite être initialisé avec
	 * une grille par initialiserGrille.
	 * 
	 * Le tableau de menu est optionnel et doit être mis à null si non 
	 * désiré. S'il est non null, chaque case devient un bouton au bas
	 * de la grille.
	 * 
	 * @param nbLignes L'axe des Y
	 * @param nbColonnes L'axe des X
	 * @param couleurTexte Couleur du texte des cases au départ.
	 * @param couleurFond  Couleur du fond des cases au départ.
	 * @param tabMenus Les options du menu du bas
	 * @param modeFermeture GrilleGui.QUITTE  ou GrilleGui.DISPOSE 
	 * (DISPOSE ferme la grille maine ne termine pas le programme)
	 * @see this.initialiserGrille
	 */
	public GrilleGui(Color couleurTexte, Color couleurFond,
			         String[] tabMenus,
			         int modeFermeture){
		
	

		// On met la grille au plus petit possible en attendant l'initialisation
		// de la grille (cvoir initialiserGrille).
		initAttributs( 1,  1, 
				       couleurTexte,  couleurFond,
				       tabMenus,
				       modeFermeture);
		
		//On affiche le cadre dans un thread
		SwingUtilities.invokeLater(this);

	}
	
	/*
	 * On assigne les paramètres à leur attribut respectif.
	 */
	private void initAttributs(int nbLignes, int nbColonnes, 
			                   Color couleurTexte, Color couleurFond,
			                   String[] tabMenus,
			                   int modeFermeture) {
		
		// On retient la taille et les couleurs de la grille.
		this.nbLignes = (nbLignes>MAX_LIGNES)?MAX_LIGNES:nbLignes;

		this.nbColonnes = (nbColonnes>MAX_COLONNES)?MAX_COLONNES:nbColonnes;

		this.couleurFond = couleurFond;
		this.couleurTexte = couleurTexte;

		//On retient les options du menu et le mode de fermeture
		this.tabMenus = tabMenus;

		this.modeFermeture = modeFermeture;

		// On crée le tableau 2D pour retenir les boutons (vide au départ).
		gui = new MonJButton[nbLignes][nbColonnes];
		
		// Rien de cliqué à date.
		estClique = false;
		
		// Si l'utilisateur a cliqué sur un bouton du menu.
		estBoutonMenu = false;
		
	}

	/**
	 * Accesseur de la position du dernier clic.  Ne tient pas compte s'il y a
	 * eu un clic ou non.
	 * 
	 * Le vérificateur de clic est remis  false.
	 * 
	 * @return La position du dernier clic (en Coordonnee)
	 */
	public Coordonnee getPosition(){

		estClique = false;
		
		return dernierClic;		
	}

	/**
	 * Retourne si vrai si un des boutons de menu a été cliqué.
	 * 
	 * @return Si un des boutons de menu a été cliqué.
	 */
	public boolean optionMenuEstCliquee(){
		
		return estBoutonMenu;
	}
	
	/**
	 * Retourne la dernière option cliqué et null autrement.
	 * @return Le texte dans le bouton cliqué s'il y a lieu.
	 */
	public String getOptionMenuClique(){
		
		if(estBoutonMenu)
		    estBoutonMenu = false;
		else
			optionClique = null;
		
		return optionClique;
	}
	
	/**
	 * Retourne la valeur contenue dans une case.
	 * 
	 * @param ligne, colonne La position de la case désirée
	 * @return la valeur contenue dans une case.
	 */
	public String getValeur(int ligne, int colonne){

		return gui[ligne][colonne].getText();
	}
	
	/**
	 * Permet de modifier la valeur d'une case de la grille.
	 * 
	 * @param ligne, colonne La position de la case désirée
	 * @param valeur La nouvelle valeur
	 */
	public void setValeur(int ligne, int colonne, String valeur){

		/*
		 * Comme c'est un Thread,  il se peut que la grille ne soit pas encore 
		 * créé alors on attend.
		 */
		if(ligne != 1) {
			
			// Le temps n'a pas été mis en constante puisqu'elle est 
			// utilisée seulement ici.
			pause(100);
		
		}
		
		gui[ligne][colonne].setText(valeur);
		
	}

	/**
	 * Accesseur du nombre de lignes
	 * 
	 * @return Le nombre de lignes de la grille
	 */
	public int getNbLignes() {
		return nbLignes;
	}

	/**
	 * Accesseur du nombre de colonnes
	 * 
	 * @return Le nombre de colonnes de la grille
	 */
	public int getNbColonnes() {
		return nbColonnes;
	}
	
	/**
	 * Accesseur de la couleur de fond de la case désirée.
	 * 
	 * @param ligne
	 * @param colonne
	 * @return La couleur de texte de la case déeirée.
	 */
	public Color getCouleurTexte(int ligne, int colonne) {
		
		return gui[ligne][colonne].getForeground();
	}
	
	/**
	 * 
	 * Accesseur de la couleur du texte de la case désirée.
	 * 
	 * @param ligne
	 * @param colonne
	 * @return La couleur du texte de la case.
	 */
	public Color getCouleurFond(int ligne, int colonne) {
		
		return gui[ligne][colonne].getBackground();
	}
	/**
	 * Permet de changer la couleur de fond d'une case.
	 * 
	 * @param ligne, colonne La position de la case
	 * @param couleur La nouvelle couleur
	 */
	public void setCouleurFond(int ligne, int colonne, Color couleurFond){
		
	        gui[ligne][colonne].setBackground(couleurFond);
	}

	/**
	 * Permet de changer la couleur de texte d'une case.
	 * 
	 * @param ligne, colonne La position de la case
	 * @param couleur La nouvelle couleur
	 */
	public void setCouleurTexte(int ligne, int colonne, Color couleurTexte){
		gui[ligne][colonne].setForeground(couleurTexte);
	}
	
	/**
	 * Retourne si un des boutons a été cliqué depuis le dernier appel à 
	 * l'accesseur de position.
	 * 
	 * @return Si un des boutons a été sélectionné.
	 */
	public boolean caseEstCliquee(){
		
		return estClique;
	}
	
	/**
	 * Reoyurne si la case i,j est désactivée ou non.
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean caseEstDesactive(int i, int j) {
		
		
		// TODO Auto-generated method stub
		return !gui[i][j].isEnabled();
	}
		

	/**
	 * Le clic de cette case n'a plus d'effet.
	 * 
	 * @param ligne
	 * @param colonne
	 */
	public void desactiverCase(int ligne, int colonne){
		
		gui[ligne][colonne].setEnabled(false);
	}

	@Override
	public void run() {
		
		
		if(cadre != null){
			cadre.dispose();
			cadre = null;
		}
			
		cadre = new JFrame();
		
		// plein écran
		cadre.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// On quitte sur X.
		cadre.setDefaultCloseOperation(modeFermeture);

		// Obtention de la référence sur le contentPane (évite pls appels)
		JPanel panneauPrincipal = (JPanel) cadre.getContentPane();

		// Une disposition en grille pour celui du haut.
		JPanel panneauHaut = new JPanel();
				
		// On ajoute les boutons vides.
		ajouterBoutons(panneauHaut);
		
		if(tabMenus != null){
	
			// Le panneau du haut est plus peit lorsqu'il y a des options au menu.
			ajouterPanneauHaut(panneauHaut, 
					          panneauPrincipal, BorderLayout.PAGE_START);
			
			// Les boutons de menu s'il y en a sont ajoutés.
			ajouterPanneauBas(panneauPrincipal, BorderLayout.PAGE_END);
		}

		else {
			
			// Le panneau du haut est plein écran , géré par le LayoutManager.
			panneauPrincipal.add(panneauHaut);
		}

		cadre.setVisible(true);		
	}
	


	/*
	 *   Permet de dimensionner les 3 tailles du panneau de la même grandeur.
	 */
	private void dimensionnerPanneau(JPanel panneau, Dimension db) {
		
		panneau.setMinimumSize(db);
		panneau.setMaximumSize(db);
		panneau.setPreferredSize(db);

	}
	
	/*
	 * Crée, dimensionne et ajoute le panneau du bas au panneau principal.
	 * 
	 * De plus, les boutons de menu y sont ajoutés.
	 */
	private  void ajouterPanneauBas(JPanel panneauPrincipal, String position) {
		
		JPanel panneauBas = new JPanel();
		
		Dimension db = new Dimension (d.width, (int)(d.height*.1));
			
		dimensionnerPanneau(panneauBas, db);
		
		ajouterMenu(panneauBas);
		
		panneauPrincipal.add(panneauBas, position);
		
	}
	
	
	
	JButton point;
	
	

	/*
	 * Crée, dimensionne et ajoute le panneau du haut au panneau principal.
	 */
	private  void ajouterPanneauHaut(JPanel panneauHaut,
			                         JPanel panneauPrincipal, 
			                         String position) {

		Dimension dh = new Dimension (d.width, (int)(d.height*.8));
		
		dimensionnerPanneau(panneauHaut, dh);
	
		panneauPrincipal.add(panneauHaut, position);

	}



	
	/*
	 * Ajoute des boutons de menu (S'il y en a) au panneau 
	 * 
	 * Si on est ici, on est certain qu'il y a des options de menu.
	 */
	private void ajouterMenu(JPanel panneau){

		JButton b;

		for(int i = 0; i < tabMenus.length; i++){

			b =new JButton(tabMenus[i]);

			// La dimension d'un bouton dépend de la taille de l'écran, on 
			// centre la grille			
			b.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {

					estClique = false;
					optionClique = ((JButton)e.getSource()).getText();
					estBoutonMenu = true;
				}	
			});


			panneau.add(b);
		}

	}
	/*
	 * Ajoute les boutons dans la grille et dans le panneau
	 * 
	 * Principalement pour la lisibilité du code.
	 */
	private void ajouterBoutons(JPanel panneau){
		
		panneau.setLayout(new GridLayout(nbLignes, nbColonnes));

		for(int i = 0; i < nbLignes;i++)
			
			for(int j = 0; j <nbColonnes;j++){

				gui[i][j] = 
						   new MonJButton(i,j, " ",  couleurTexte, couleurFond);
				
				panneau.add(gui[i][j]);
			}	
	}
	
	/*
	 * Permet de pauser l'application pour intercepter les clics.
	 */
	public void pause(int delai) {
		
		try {
			Thread.sleep(delai);
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Initialise et affiche le gui avec la grille reçue.
	 * 
	 * @param grille La grille à afficher.
	 */
	public void setGrille(int[][] grille){
		
		// lorsqu'on réinitialise le gui.
        cadre.dispose();

		// Permet la réinitialisation si nécessaire. 
		initAttributs( grille.length,  grille[0].length, 
				       couleurTexte,  couleurFond,
				       tabMenus,
			      	   modeFermeture);
		run();
	}


	/**
	 * Classe interne qui ajoute à un JButton la position (x,y) où il se trouve 
	 * dans la grille.
	 * 
	 * Cela évite de chercher cette position lors d'un clic.
	 */
	private class MonJButton extends JButton{

		// Juste pour enlever le warning.
		private static final long serialVersionUID = 1L;
		
		//Coordonnée ligne colonne du bouton dans le gui.
		private int ligne;
		private int colonne;

		/**
		 * Constructeur avec la position du bouotn et sa valeur.
		 * @param y La position en ligne
		 * @param x La position en colonne
		 * @param valeur La valeur � afficher
		 */
		private MonJButton(int ligne, int colonne, 
				           String valeur, 
				           Color couleurTexte, 
				           Color couleurFond){

			// On passe le texte à la classe parent.
			super(valeur);

			this.ligne = ligne;
			this.colonne = colonne;

			setForeground(couleurTexte);
			setBackground(couleurFond);
			setFont(new Font("sans serif", Font.BOLD, TAILLE_CAR));

			// L'écouteur des cases.		
			addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {

					//On obtient la référence du bouton cliqué
					MonJButton b = (MonJButton) e.getSource();

					// On retient la position du clic
					dernierClic =  new Coordonnee();
					dernierClic.ligne = b.ligne;
					dernierClic.colonne = b.colonne;
					
					estClique = true;		
					estBoutonMenu = false;
				}	
			});

		}
	}
}