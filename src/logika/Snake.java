package logika;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;



public class Snake {
	public final static int pravac_dolje = 0;
	public final static int pravac_lijevo = 1;
	public final static int pravac_desno = 2;
	public final static int pravac_gore = 3;
	
	
	/*
	 * Oznake za vrijednosti polja, 0 za prazno polje, 1 ako se na polju nalazi zmija
	 * i 2 ako je u pitanju jabuka.
	 */
	
	public final static int oznaka_prazno_polje = 0;
	public final static int oznaka_tijelo_zmije = 1;
	public final static int oznaka_jabuka = 2;
	public final static int oznaka_prepreka = 3;
	
	public final static int pocetna_duzina_zmije = 5;
	public final static int max_broj_prepreka = 5;
	
	private int matricaStanja[][]; // stanje polja
	private LinkedList<Pozicija> tijeloZmije; // pozicija zmije unutar matrice stanja
	private int n; // dimenzija matrice nxn
	private int smjer;
	private boolean kraj;
	private Pozicija jabuka;
	//private Vector<Vector<Pozicija>> prepreke;
	// prepreke samo dodavati u matricuStanja
	
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
				matricaStanja[i][j] = oznaka_prazno_polje;
			}
		}
		
		/* U pocetku igre zmija uvijek krece sa iste pozicije i duzina iznosi 4 polja
		 *  x = kolona j   y = red i
		 */
		smjer = pravac_desno;
		
		tijeloZmije = new LinkedList<Pozicija>();
		for(int i = pocetna_duzina_zmije;i>=0;i--) {
			tijeloZmije.add(new Pozicija(n/2, i));
			matricaStanja[n/2][i] = oznaka_tijelo_zmije;
		}
		
		kraj = false;
		generisiPrepreke();
		generisiJabuku();
		matricaStanja[jabuka.getI()][jabuka.getJ()] = oznaka_jabuka;
		/*
		 * System.out.println("Igra generisana"); for(int i =
		 * 0;i<matricaStanja.length;i++) { for(int j = 0;j<matricaStanja[0].length;j++)
		 * { System.out.print(matricaStanja[i][j] + " ");
		 * 
		 * } System.out.println(); }
		 */
	}
	/*
	 * Nasumicno dodajemo prepreke u tabelu stanja.
	 */
	private void generisiPrepreke() {
		
		// pravac_gore, pravac_desno, pravac_dolje, pravac_lijevo
		Random rn = new Random();
		int broj_prepreka = 1 + rn.nextInt(max_broj_prepreka);
		int duzina_prepreke;
		while(broj_prepreka != 0) {
			System.out.println("Broj prepreka" + broj_prepreka);
			broj_prepreka--;
			duzina_prepreke = 1 + rn.nextInt(max_broj_prepreka);
			
			int smjer_prepreke = 0 + rn.nextInt(3);
			System.out.println("Smjer prepreke " + smjer_prepreke);
			int pocetak_x = 0 + rn.nextInt(this.n);
			int pocetak_y = 0 + rn.nextInt(this.n);
			matricaStanja[pocetak_x][pocetak_y] = oznaka_prepreka;
			while(duzina_prepreke > 0) {
				
				Pozicija tmp_poz = new Pozicija(pocetak_x,pocetak_y);
				tmp_poz.pomjeriKoordinate(n, n, smjer_prepreke);
				pocetak_x = tmp_poz.getI();
				pocetak_y = tmp_poz.getJ();
				matricaStanja[pocetak_x][pocetak_y] = oznaka_prepreka;
				duzina_prepreke--;
				System.out.println("Koordinate "+ pocetak_x + " " +pocetak_y);
			}
		}
		
		
	}
	
	
	
	// provjeravamo o kojem se smeru radi i pomjeramo zmiju
	// ako smo naisli na prepreku kraj igre
	// ako na jabuku prosirimo se 
	public void pomjeriZmiju() {
		//System.out.println(smjer);
		int i = tijeloZmije.getFirst().getI();
		int j = tijeloZmije.getFirst().getJ();
		
		if(smjer == pravac_gore) {
			if(i - 1 < 0) {
				i += n-1;
			}
			else {
				i = (i-1)%n;
			}
			
			
		}
		else if(smjer == pravac_dolje) {
			//i++;
			i = (i+1)%n;
			
		}
		else if(smjer == pravac_desno) {
			//j++;
			j = (j+1)%n;
			
			
		}
		else if(smjer == pravac_lijevo) {
			//j--;
			if(j - 1 < 0) {
				j += n-1;
			}
			else {
				j = (j-1)%n;
			}
			
		}
		
		tijeloZmije.addFirst(new Pozicija(i,j));
		if(jabuka.getI() == i && jabuka.getJ() == j) {
			generisiJabuku();
		}
		else if(matricaStanja[i][j] == oznaka_tijelo_zmije || matricaStanja[i][j] == oznaka_prepreka) {
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
				if(matricaStanja[i][j] == oznaka_prepreka) {
					matricaStanja[i][j] = oznaka_prepreka;
				}else {
					matricaStanja[i][j] = oznaka_prazno_polje;
				}
				
			}
		}
		
		for(int i = 0;i<tijeloZmije.size();i++) {
			matricaStanja[tijeloZmije.get(i).getI()][tijeloZmije.get(i).getJ()] = oznaka_tijelo_zmije;
		}
		matricaStanja[jabuka.getI()][jabuka.getJ()] = oznaka_jabuka;
		
	}
	
	// jedini uslov je da jabuka bude unutar granica
	// i da ne bude vec na tijelu zmije
	private void generisiJabuku() {
		boolean nova_jabuka_generisana;
		Random rn = new Random();
		int i, j;
		while(true) {
			nova_jabuka_generisana = true;
			i = 0 + rn.nextInt(n);
			j = 0 + rn.nextInt(n);
			for(int k = 0;k<tijeloZmije.size();k++) {
				if(tijeloZmije.get(k).getI() == i && tijeloZmije.get(k).getJ()  == j) {
					nova_jabuka_generisana = false;
				}
					
			}
			if(nova_jabuka_generisana) {
				jabuka = new Pozicija(i,j);
				return;
			}
		}
		
	}
	
	/*
	 * U sluèaju da korisnik pokuša da se vrati u pravcu iz kojeg je došao (udario bi u 
	 * tijelo zmije).
	 */
	
	public void setSmjer(int smjer) {
		
		int smjerovi[] = {pravac_gore, pravac_desno, pravac_dolje, pravac_lijevo};
		
		int tmp_index = 0;
		for(int i = 0;i<smjerovi.length;i++) {
			if(this.smjer == smjerovi[i]) {
				tmp_index = i;
				break;
			}
		}
		
		if(smjer == pravac_desno) {
			if(tmp_index == smjerovi.length - 1) {
				tmp_index = 0;
			}
			else {
				tmp_index++;
			}
		}
		
		else if(smjer == pravac_lijevo) {
			if(tmp_index == 0) {
				tmp_index = smjerovi.length - 1;
			}
			else {
				tmp_index--;
			}
		}
		
		this.smjer = smjerovi[tmp_index];
	}
	
	public int getVrijednostStanja(int i, int j) {
		return matricaStanja[i][j];
	}
	
	public int[][] getTrenutnoStanje() {
		return matricaStanja;
	}

	public int getTrenutniSmjer() {
		return smjer;
	}
	
	public boolean krajIgre() {
		return kraj;
	}
	
	
}
