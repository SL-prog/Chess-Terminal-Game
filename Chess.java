/*Chess Terminal Game by S.L-prog 
 * (C) 2016
 * */

public class Chess {
		
	public static int[][] generateground() { //methode de generation du terrain
		int[][] ground = new int[8][8]; //on cree la matrice terrain qui va contenir les ID des pieces
										// de base la matrice contient des 0
				
		//placement de tous les pions
		for(int lign = 1; lign < 7; lign = lign + 5) {
			for(int column = 0; column < 8; column++) {
				ground[lign][column] = 1 + (10*(lign/6));
			}
		}	
		
		//placement de toutes les autres pieces (sauf rois)
		for(int piece = 2; piece <= 5; piece++) {
			for(int column = piece - 2; column < 10-piece; column++) { 
				for(int lign = 0; lign < 8; lign = lign + 7) {
					ground[lign][column] = piece + ((10*lign)/7);				
				}
			}
		}
		
		//placement des rois
		ground[0][4] = 6;	
		ground[7][3] = 16;	
	
		return ground; //renvoi le terrain initial genere avec toutes les pieces placees
	}
	
	public static void display(int[][] ground, int round) { //methode d'affichage des reperes (lettre, numeros), des bordures du terrain, des pieces et autres
		
		System.out.println("-----------------------------------------\n               Round " + round + "\n");
		
		String RESETCOLOR = "\u001B[0m"; //voir explication dans la methode convertText
		
		System.out.print("    "); //on met des espaces pour decaler la premiere lettre
		for(int letter = 65; letter < 73; letter++) { //afficher les lettres ABC.. (en haut) ce sont des codes ASCII (google : table ASCII)
			System.out.print("  " + (char)letter + " "); //on decale chaque lettre avec des espaces et on converti le code ascii en lettre
		}
		System.out.print("\n"); //on saute une ligne pour passer a l'affichage des bordures du terrain
		
		
		int number = 1; //on initialise le premier numero a afficher (sur la gauche)
		for(int boardery = 1; boardery < 18; boardery++) { //variable qui va permettre de placer les bordures sur la longueur (faire un schema pour comprendre pourquoi on va de 1 a 17)
			
			if ((boardery) % 2 == 0) { //si la longueur de la bordure est un multiple de 2,
				System.out.print(" " + number + "  "); //alors on affiche un numero a gauche des bordures (avec un decalage - espace)
				number = number +1; //et on rajoute +1 au numero
			}
			else { //sinon on met juste des espaces sans numeros
				System.out.print("    ");	
			}
			
			for(int boarderx = 1; boarderx < 18; boarderx++) { //variable qui va permettre de placer les bordures sur la hauteur

				if (((boarderx + 1) % 2 == 0) && (boardery % 2 == 1)) {  //si la longueur actuelle + 1 est paire, et de meme pour la hauteur
					System.out.print(RESETCOLOR + "+"); //on reinitialise la couleur du texte et on affiche un " + " (symbole pour les coins des cases)
				}
				if (((boarderx) % 2 == 0) && ((boardery) % 2 == 0)) {
					System.out.print(" " + convertText(ground[(boardery/2)-1][(boarderx/2)-1]) + " "); //ici on affiche le caractere renvoye par la methode convertText suite a l'ID qu'on lui a fourni (l'ID correspond a une case de la matrice terrain)
				}
				if (((boarderx + 1) % 2 == 0) && ((boardery + 1) % 2 == 0) && ((boarderx) != 17)) { //different de 17 pour eviter d'afficher un trait en plus apres les cases
					System.out.print(RESETCOLOR + "---");
				}
				if (((boarderx + 1) % 2 == 0) && ((boardery) % 2 == 0)) {
					System.out.print(RESETCOLOR + "|");
				}
			}
			System.out.print("\n"); //on saute une ligne
		}
		
		System.out.println("\n- Play, Human.");
	
	}
	
	public static String convertText(int pieceID) { //methode pour attribuer un caractere selon l'ID de la piece
		
								
		String REDCOLOR = "\u001B[31m"; //AINSI CODE --> c'est un code a mettre avant un texte, pour en changer sa couleur
		String YELLOWCOLOR = "\u001B[33m"; //AINSI CODE

		//structure switch :
		switch (pieceID) { //on analyse la valeur de pieceID
			
			default:
			//	System.out.println("Error : ID = " + pieceID);
				return "Error"; //on initialise le caractere a afficher par " erreur "
								//pour savoir si jamais un ID n'est pas reconnu (dans les if apres)
			case 00: //si l'ID = 0
				return " "; // on affiche rien (1 espace)
				
			case 01:
				return YELLOWCOLOR + "P"; //pion equipe 1 - jaune
				
			case 02:
				return YELLOWCOLOR + "R";
				
			case 03:
				return YELLOWCOLOR + "N";
				
			case 04:
				return YELLOWCOLOR + "B";
				
			case 05:
				return YELLOWCOLOR + "Q";
				
			case 06:
				return YELLOWCOLOR + "K";				


			case 11:
				return REDCOLOR + "P"; //pion equipe 2 - ROUGE
				
			case 12:
				return REDCOLOR + "R";
				
			case 13:
				return REDCOLOR + "N";
				
			case 14:
				return REDCOLOR + "B";
				
			case 15:
				return REDCOLOR + "Q";
				
			case 16:
				return REDCOLOR + "K";				
								
		 }

	}
	
	public static int[][] Moove(int[][] ground) {
		String postomoove = Lire.S();
		int [] tester = Verification(postomoove, ground);
		while (tester[0] == 0) {
			System.out.println("- You can't do this. Try again.");
			postomoove = Lire.S();
			tester = Verification(postomoove, ground);			
		}
		
		System.out.println("- Good.");
		
		ground[tester[3]][tester[4]] = ground[tester[1]][tester[2]];		
		ground[tester[1]][tester[2]] = 0;							

		return ground;
	}
	
	public static int[] Verification(String postomoove, int[][] ground) { //methode qui verifie le format de la commande
		int[] results = new int[5];
		
		if (postomoove.length() == 5) {
			if ("/".compareTo(postomoove.substring(2,3)) == 0) {
				for(int letter1 = 65; letter1 < 73; letter1++) {
					if (postomoove.matches((char)letter1+"(.*)")) {
						for(int letter2 = 65; letter2 < 73; letter2++) {
							if (postomoove.matches("(.*)"+ "/" + (char)letter2+"(.*)")) {
								for(int number1 = 49; number1 < 57; number1++) {
									if (postomoove.matches("(.*)"+(char)number1 + "/" +"(.*)")) {
										for(int number2 = 49; number2 < 57; number2++) {
											if (postomoove.matches("(.*)"+(char)number2)) {
												if (ground[number1-49][letter1-65] != 0) {
													results[0] = 1;
													results[1] = number1-49;
													results[2] = letter1-65;
													results[3] = number2-49;
													results[4] = letter2-65;										
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return results;
		

		//others verif.
	}
	
	public static void main (String args[]) {

		int[][] ground = generateground(); //generer le terrain au lancement du jeu
		int round = 1;	
		String postomoove;
		
		while (1==1) { //boucle principale

			display(ground, round); //on affiche le terrain avec les pieces		
					
			ground = Moove(ground);
			

			round++;
		} //fin boucle principale

	}
}

