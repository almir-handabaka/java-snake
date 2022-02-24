package konzola;

import java.util.Scanner;
import logika.Snake;

/**
 * Klasa za pokretanje Snake igre unutar konzole
 * 
 * @author Almir Handabaka
 *
 */


public class IgrajSnake {
	
	/**
	 * Uèita poteze od igraèa i pokrene igru i ispisuje stanje polja
	 * @param args string args
	 */
	
	public static void main(String[] args) {
		System.out.println("Igrica snake, potezi su:\n" +
				"\t4 - lijevo\n" +
				"\t6 - desno\n" +
				"\t5 - ništa\n");
		Snake snake = new Snake(20);
		System.out.println(pripremiTabeluStanja(snake.getTrenutnoStanje()));
		while (!snake.krajIgre()) {
			int potez = ucitajPotez();
			if(potez != 5) {
				snake.setSmjer(potez);
			}
			snake.pomjeriZmiju();
			System.out.println(pripremiTabeluStanja(snake.getTrenutnoStanje()));
			
		}
		System.out.println("Kraj igre!");
	}
	
	
	/**
	 * Uèita potez od igraèa
	 */
	private static int ucitajPotez() {
		Scanner sc = new Scanner(System.in);
		int potez = 0;
		try {
			potez = sc.nextInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sc.close();
		
		if(potez == 4) {
			return Snake.SKRENI_LIJEVO;
		}
		else if(potez == 6){
			return Snake.SKRENI_DESNO;
		}
		
		return 5;
	}
	
	
	/**
	 * Priprema trenutnog stanja u polju za igru za ispis igraèu
	 */
	private static String pripremiTabeluStanja(int[][] matrica_stanja) {
		String stanje = "";
		
		
		for (int i = 0; i < matrica_stanja.length; i++) {
			//System.out.println(Arrays.toString(vratiTrenutnoStanje[i]));
			for (int j = 0; j < matrica_stanja[i].length; j++) {
				if(matrica_stanja[i][j] == Snake.OZNAKA_PRAZNO_POLJE) {
					stanje += ". ";
				}
				else if(matrica_stanja[i][j] == Snake.OZNAKA_TIJELO_ZMIJE) {
					stanje += "- ";
				}
				else if(matrica_stanja[i][j] == Snake.OZNAKA_JABUKA) {
					stanje += "+ ";
				}
				else if(matrica_stanja[i][j] == Snake.OZNAKA_PREPREKA) {
					stanje += "! ";
				}
			}
			stanje += "\n";
		}
		return stanje;
	}
	
}
