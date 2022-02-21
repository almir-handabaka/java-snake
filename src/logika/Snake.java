package logika;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;


public class Snake {
	public final static int pravac_dolje = 0;
	public final static int pravac_lijevo = 1;
	public final static int pravac_desno = 2;
	public final static int pravac_gore = 3;
	
	public static int skreni_desno = 0;
	public static int skreni_lijevo = 1;
	
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
	
	private int bodovi; // totalni bodovi
	private int trenutni_bodovi_hrane; // koliko hrana na polju nosi bodova
	private int povecati_tijelo; // tijelo zmije povecavamo za neki broj
	boolean obrnuti_kontrole;
	int trajanje_hrane;
	
	public static Hashtable<String, Integer> hrana;
	
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
		kraj = false;
		bodovi = 0;
		povecati_tijelo = 0;
		obrnuti_kontrole = false;
		trajanje_hrane = 0;
		hrana = new Hashtable<String, Integer>();
		
		
		hrana.put("tip_1", 1); // obicna
		hrana.put("tip_2", 3); // povecaj vise od 1 
		hrana.put("tip_3", -1); // smanji za 1
		hrana.put("tip_4", 0); // obrni kontrole
		
		generisiPrepreke();
		
		for(int i = 0 + 1;i<n;i++) {
			matricaStanja[n/2][i] = oznaka_prazno_polje;
		}
		
		tijeloZmije = new LinkedList<Pozicija>();
		for(int i = pocetna_duzina_zmije;i>=0;i--) {
			tijeloZmije.add(new Pozicija(n/2, i));
			matricaStanja[n/2][i] = oznaka_tijelo_zmije;
		}
		
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
			//System.out.println("Broj prepreka" + broj_prepreka);
			broj_prepreka--;
			duzina_prepreke = 1 + rn.nextInt(max_broj_prepreka);
			
			int smjer_prepreke = 0 + rn.nextInt(3);
			//System.out.println("Smjer prepreke " + smjer_prepreke);
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
				//System.out.println("Koordinate "+ pocetak_x + " " +pocetak_y);
			}
		}
		
		// cistimo pocetni pravac zmije
		
		
		
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
		
		// boolean daLiJeHrana
		if(daLiJeHrana(i, j)) {
			pojediHranu();
			generisiJabuku();
		}
		else if(trajanje_hrane == 0) {
			generisiJabuku();
		}
		// boolean daLiJePrepreka
		else if(daLiJePrepreka(i,j)) {
			kraj = true;
			return;
		}
		
		//System.out.println("Desno i lijevo " + skreni_desno + " " + skreni_lijevo);
		
		
		if(povecati_tijelo != 0) {
			if(povecati_tijelo > 0) {
				povecati_tijelo--;
			}
			else if(povecati_tijelo < 0) {
				ukloniSaKrajaZmije();
				ukloniSaKrajaZmije();
				povecati_tijelo++;
			}
		}
		else {
			ukloniSaKrajaZmije();
		}
		
		trajanje_hrane--;
		dodajNaPocetakZmije(i,j);
		osvjeziMatricuStanja();
	}
	
	public void pojediHranu() {
		bodovi += Math.abs(max_broj_prepreka);
		// obrnuti_kontrole = true;
		if(trenutni_bodovi_hrane == hrana.get("tip_4")) {
			if(obrnuti_kontrole == true) {
				obrnuti_kontrole = false;
			}
			else {
				obrnuti_kontrole = true;
			}
		}
		else {
			povecati_tijelo = trenutni_bodovi_hrane;
		}
		
	}
	
	private boolean daLiJeHrana(int i,int j) {
		return (jabuka.getI() == i && jabuka.getJ() == j);
	}
	
	boolean daLiJePrepreka(int i, int j) {
		return (matricaStanja[i][j] == oznaka_tijelo_zmije || matricaStanja[i][j] == oznaka_prepreka);
	}
	
	private void ukloniSaKrajaZmije() {
		tijeloZmije.removeLast();
	}
	
	private void dodajNaPocetakZmije(int i, int j) {
		tijeloZmije.addFirst(new Pozicija(i,j));
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
	
	
	/*	generisemo hranu, imamo 4 vrste hrane, obicnu koja poveca zmiju za 1
	 * 	-	da zmiju poveæa više od jednog èlanka
		-	da smanji zmiju
		-	da zamijeni lijevo – desno kontrole (na odreðeni 
	 */
	private void generisiJabuku() {
		Random rn = new Random();
		
		int random = rn.nextInt(100);
		
		trajanje_hrane = 25 + rn.nextInt(20);
		System.out.println("Trajanje hrane je " + trajanje_hrane + " pomjeranja");
		
		if(random <= 5) {
			// da zamijeni lijevo - desno kontrole (na odreðeni period ili za 
			//stalno)
			System.out.println("Generisana hrana obrtanja kontrola");
			trenutni_bodovi_hrane = hrana.get("tip_4");
		}
		else if(random <= 15) {
			System.out.println("Generisana hrana smanji zmiju");
			// da smanji zmiju
			trenutni_bodovi_hrane = hrana.get("tip_3");
		}
		else if(random <= 25) {
			System.out.println("Generisana hrana povecaj za 3");
			// da zmiju poveæa više od jednog èlanka
			trenutni_bodovi_hrane = hrana.get("tip_2");
		}
		else {
			System.out.println("Generisana obicna hrana");
			
			// obicna hrana
			trenutni_bodovi_hrane = hrana.get("tip_1");
		}
		
		
		int i, j;
		while(true) {
			
			i = 0 + rn.nextInt(n);
			j = 0 + rn.nextInt(n);
			
			if(matricaStanja[i][j] == oznaka_prazno_polje) {
				jabuka = new Pozicija(i,j);
				return;
			}
			
		}
		
	}
	
	/*
	 * U sluèaju da korisnik pokuša da se vrati u pravcu iz kojeg je došao (udario bi u 
	 * tijelo zmije).
	 */
	
	public void setSmjer(int smjer_skretanja) {
		if(obrnuti_kontrole == true) {
			if(smjer_skretanja == skreni_desno) {
				smjer_skretanja = skreni_lijevo;
			}
			else {
				smjer_skretanja = skreni_desno;
			}
		}
		
		
		int smjerovi[] = {pravac_gore, pravac_desno, pravac_dolje, pravac_lijevo};
		
		int tmp_index = 0;
		for(int i = 0;i<smjerovi.length;i++) {
			if(this.smjer == smjerovi[i]) {
				tmp_index = i;
				break;
			}
		}
		
		if(smjer_skretanja == skreni_desno) {
			if(tmp_index == smjerovi.length - 1) {
				tmp_index = 0;
			}
			else {
				tmp_index++;
			}
		}
		
		else if(smjer_skretanja == skreni_lijevo) {
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
	
	public int getSkreniDesno() {
		return skreni_desno;
	}
	
	public int getSkreniLijevo() {
		return skreni_lijevo;
	}
	
	public int getTrenutneBodove() {
		return trenutni_bodovi_hrane;
	}
}
