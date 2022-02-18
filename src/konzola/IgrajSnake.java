package konzola;

import java.util.Arrays;
import java.util.Scanner;
import logika.Snake;

/**
 * @author Almir Handabaka
 *
 */


public class IgrajSnake {
	public static void main(String[] args) {
		System.out.println("Igrica snake, potezi su:\n" +
				"\t4 - lijevo\n" +
				"\t6 - desno\n" +
				"\t8 - gore\n" +
				"\t2 - dole\t");
		Snake snake = new Snake(20);
		System.out.println(pripremiTabeluStanja(snake.getTrenutnoStanje(), snake));
		while (!snake.krajIgre()) {
			int potez = ucitajPotez(snake);
			snake.setSmjer(potez);
			snake.pomjeriZmiju();
			System.out.println(pripremiTabeluStanja(snake.getTrenutnoStanje(), snake));
			
		}
		System.out.println("Kraj igre!");
	}
	
	private static int ucitajPotez(Snake snake) {
		Scanner sc = new Scanner(System.in);
		int potez = snake.getTrenutniSmjer();
		try {
			potez = sc.nextInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(potez == 4) {
			System.out.println("lijevo");
			return snake.pravac_lijevo;
		}
		else if(potez == 6){
			System.out.println("desno");
			return snake.pravac_desno;
		}
		else if(potez == 8){
			System.out.println("gore");
			return snake.pravac_dolje;
		}
		else if(potez == 2){
			System.out.println("dole");
			return snake.pravac_gore;
		}
		
		return potez;
	}

	private static String pripremiTabeluStanja(int[][] vratiTrenutnoStanje, Snake snake) {
		String stanje = "";
		
		for (int i = vratiTrenutnoStanje.length - 1; i >= 0; i--) {
			System.out.println(Arrays.toString(vratiTrenutnoStanje[i]));
			for (int j = 0; j < vratiTrenutnoStanje[i].length; j++) {
				if(vratiTrenutnoStanje[i][j] == snake.oznaka_prazno_polje) {
					stanje += "-";
				}
				else if(vratiTrenutnoStanje[i][j] == snake.oznaka_tijelo_zmije) {
					stanje += "+";
				}
				else if(vratiTrenutnoStanje[i][j] == snake.oznaka_jabuka) {
					stanje += "0";
				}
			}
			stanje += "\n";
		}
		return "";
	}
	
}
