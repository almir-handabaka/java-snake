package konzola;

import java.util.Scanner;
import logika.Snake;

/**
 * @author Almir Handabaka
 *
 */


public class IgrajSnake {
	public static void main(String[] args) {
		System.out.println("Igrica tetris, potezi su:\n" +
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
			return snake.PRAVAC_LIJEVO;
		}
		else if(potez == 6){
			return snake.PRAVAC_DESNO;
		}
		else if(potez == 8){
			return snake.PRAVAC_DOLE;
		}
		else if(potez == 2){
			return snake.PRAVAC_GORE;
		}
		
		return potez;
	}

	private static String pripremiTabeluStanja(int[][] vratiTrenutnoStanje, Snake snake) {
		String stanje = "";
		for (int i = vratiTrenutnoStanje.length - 1; i >= 0; i--) {
			for (int j = 0; j < vratiTrenutnoStanje[i].length; j++) {
				if(vratiTrenutnoStanje[i][j] == snake.brojZapraznoPolje) {
					stanje += "-";
				}
				else if(vratiTrenutnoStanje[i][j] == snake.brojZatijeloZmije) {
					stanje += "+";
				}
				else if(vratiTrenutnoStanje[i][j] == snake.brojZajabuku) {
					stanje += "0";
				}
			}
			stanje += "\n";
		}
		return stanje;
	}
	
}
