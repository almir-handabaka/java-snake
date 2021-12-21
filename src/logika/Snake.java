package logika;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Random;



public class Snake {
	/*
	 * Oznake za vrijednosti polja, 0 za prazno polje, 1 ako se na polju nalazi zmija
	 * i 2 ako je u pitanju jabuka.
	 */
	public final static int brojZapraznoPolje = 0;
	public final static int brojZatijeloZmije = 1;
	public final static int brojZajabuku = 2;
	
	private LinkedList<Pozicija> tijeloZmije; // pozicija zmije unutar matrice stanja
	private int matricaStanja[][]; // stanje polja
	private int n; // dimenzija matrice nxn
	private String smjer;
	private boolean kraj;
	private Pozicija jabuka;
	
	/*
	 * Konstruktor sa jednim parametrom za kreiranje igre.
	 * @param n - dimenzije polja
	 * Prazno polje je 0, tijelo zmije predstavlja broj 1, 
	 */
	public Snake(int n){
		this.n = n;
		matricaStanja = new int[n][n];
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<n;j++) {
				matricaStanja[i][j] = brojZapraznoPolje;
			}
		}
		
		/* U pocetku igre zmija uvijek krece sa iste pozicije i duzina iznosi 4 polja
		 *  x = kolona j   y = red i
		 */
		smjer = "r";
		
		tijeloZmije = new LinkedList<Pozicija>();
		for(int i = 5;i>=0;i--) {
			tijeloZmije.add(new Pozicija(n/2, i));
			matricaStanja[n/2][i] = brojZatijeloZmije;
		}
		
		kraj = false;
		generisiJabuku();
		matricaStanja[jabuka.getI()][jabuka.getJ()] = brojZajabuku;
		
	}
	
	public void ispisiStanjeMatrice() {
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<n;j++) {
				System.out.print(matricaStanja[i][j]+ " ");
			}
			System.out.println();
		}
	}
	
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
	
	
	// provjeravamo o kojem se smeru radi i pomjeramo zmiju
	// ako smo naisli na prepreku kraj igre
	// ako na jabuku prosirimo se 
	public void pomjeriZmiju() {
		int i = tijeloZmije.getFirst().getI();
		int j = tijeloZmije.getFirst().getJ();
		//System.out.println(i + " " + j);
		//System.out.println(smjer);
		if(smjer.contentEquals("u")) {
			i--;
			
		}
		else if(smjer.contentEquals("d")) {
			i++;
			
		}
		else if(smjer.contentEquals("r")) {
			j++;
			
		}
		else if(smjer.contentEquals("l")) {
			j--;
			
		}
		//System.out.println(i + " " + j);
		tijeloZmije.addFirst(new Pozicija(i,j));
		if(jabuka.getI() == i && jabuka.getJ() == j) {
			generisiJabuku();
		}
		else if(matricaStanja[i][j] == 1) {
			kraj = true;
		}
		else {
			tijeloZmije.removeLast();
		}
		
		osvjeziMatricuStanja();
	}
	
	public void osvjeziMatricuStanja() {
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<n;j++) {
				matricaStanja[i][j] = brojZapraznoPolje;
			}
		}
		
		for(int i = 0;i<tijeloZmije.size();i++) {
			matricaStanja[tijeloZmije.get(i).getI()][tijeloZmije.get(i).getJ()] = brojZatijeloZmije;
		}
		matricaStanja[jabuka.getI()][jabuka.getJ()] = brojZajabuku;
		
	}
	
	// jedini uslov je da jabuka bude unutar granica
	// i da ne bude vec na tijelu zmije
	private void generisiJabuku() {
		Random rn = new Random();
		int i, j;
		while(true) {
			i = 0 + rn.nextInt(n);
			j = 0 + rn.nextInt(n);
			for(int k = 0;k<tijeloZmije.size();k++) {
				if( tijeloZmije.get(k).getI() != i && tijeloZmije.get(k).getJ()  != j) {
					jabuka = new Pozicija(i,j);
					System.out.println(i +" " +j);
					return;
				}
					
			}
		}
		
	}
	
	public void setSmjer(String smjer) {
		this.smjer = smjer;
	}
	
	public int getVrijednostStanja(int i, int j) {
		return matricaStanja[i][j];
	}
	
	
}
