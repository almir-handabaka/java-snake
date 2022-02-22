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
	 * U�ita poteze od igra�a i pokrene igru i ispisuje stanje polja
	 * @param args string args
	 */
	
	public static void main(String[] args) {
		System.out.println("Igrica snake, potezi su:\n" +
				"\t4 - lijevo\n" +
				"\t6 - desno\n" +
				"\t5 - ni�ta\n");
		Snake snake = new Snake(20);
		System.out.println(pripremiTabeluStanja(snake.getTrenutnoStanje(), snake));
		while (!snake.krajIgre()) {
			int potez = ucitajPotez(snake);
			if(potez != 5) {
				snake.setSmjer(potez);
			}
			snake.pomjeriZmiju();
			System.out.println(pripremiTabeluStanja(snake.getTrenutnoStanje(), snake));
			
		}
		System.out.println("Kraj igre!");
	}
	
	
	/**
	 * U�ita potez od igra�a
	 */
	private static int ucitajPotez(Snake snake) {
		Scanner sc = new Scanner(System.in);
		int potez = snake.getTrenutniSmjer();
		try {
			potez = sc.nextInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(potez == 4) {
			return snake.skreni_lijevo;
		}
		else if(potez == 6){
			return snake.skreni_desno;
		}
		sc.close();
		return 5;
	}
	
	
	/**
	 * Priprema trenutnog stanja u polju za igru za ispis igra�u
	 */
	private static String pripremiTabeluStanja(int[][] matrica_stanja, Snake snake) {
		String stanje = "";
		
		
		for (int i = 0; i < matrica_stanja.length; i++) {
			//System.out.println(Arrays.toString(vratiTrenutnoStanje[i]));
			for (int j = 0; j < matrica_stanja[i].length; j++) {
				if(matrica_stanja[i][j] == snake.oznaka_prazno_polje) {
					stanje += ". ";
				}
				else if(matrica_stanja[i][j] == snake.oznaka_tijelo_zmije) {
					stanje += "- ";
				}
				else if(matrica_stanja[i][j] == snake.oznaka_jabuka) {
					stanje += "+ ";
				}
				else if(matrica_stanja[i][j] == snake.oznaka_prepreka) {
					stanje += "! ";
				}
			}
			stanje += "\n";
		}
		return stanje;
	}
	
}
