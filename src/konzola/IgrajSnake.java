package konzola;

import java.util.Scanner;

public class IgrajSnake {
public void zapocniIgru() {
		
		String noviSmjer;
		Scanner ulaz = new Scanner(System.in);
		while(kraj != true) {
			ispisiStanjeMatrice();
			System.out.print("Unesi potez (u, d, l, r): ");
			noviSmjer = ulaz.next();
			
			smjer = noviSmjer;
			
			pomjeriZmiju();
			osvjeziMatricuStanja();
		}
		ulaz.close();
		System.out.println("Kraj igre!");
	}
}
