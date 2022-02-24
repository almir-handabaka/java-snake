package logika;
import java.awt.Point;


/**
 * Implementacija klase Pozicija
 * 
 * @author Almir Handabaka
 *
 */
public class Pozicija {
	/**
	 * �uva red i kolonu elementa unutar matrice
	 */
	Point tacka;

	/**
	 * Konstruktor sa dva parametra koji namje�ta poziciju
	 * 
	 * @param x red matrice
	 * @param y kolonu matrice
	 */
	Pozicija(int x, int y){
		tacka = new Point(x,y);
	}
	
	/**
	 * Konstruktor koji ne prima parametrem, namje�ta red i kolonu na 0, 0
	 * 
	 */
	Pozicija(){
		Point tacka = new Point();
	}
	
	/**
	 * Vra�a red
	 * @return red
	 */
	public int getI() {
		return (int) tacka.getX();
	}
	
	/**
	 * Vra�a kolonu
	 * @return kolona
	 */
	public int getJ() {
		return (int) tacka.getY();
	}
	
	/**
	 * Metoda koja pomjera red, kolonu elementa u nekom smjeru
	 * @param n broj redova
	 * @param m broj kolona
	 * @param smjer smjer u kojem treba da se pomjeri element
	 */
	public void pomjeriKoordinate(int n, int m, int smjer) {
		int i = (int) tacka.getX();
		int j = (int) tacka.getY();
		
		if(smjer == Snake.PRAVAC_GORE) {
			if(i - 1 < 0) {
				i += n-1;
			}
			else {
				i = (i-1)%n;
			}
			
		}
		else if(smjer == Snake.PRAVAC_DOLJE) {
			i = (i+1)%n;
		}
		else if(smjer == Snake.PRAVAC_DESNO) {
			j = (j+1)%m;
		}
		else if(smjer == Snake.PRAVAC_LIJEVO) {
			if(j - 1 < 0) {
				j += m-1;
			}
			else {
				j = (j-1)%m;
			}
		}
		tacka.setLocation(i, j);
	}
	
}
