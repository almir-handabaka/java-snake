package logika;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;



/**
 * Klasa za implementaciju logike za igru Snake
 * 
 * @author Almir Handabaka
 *
 */

public class Snake {
	
	/**
	 * Smjerovi u kojima se zmija kreæe
	 */
	
	public final static int pravac_dolje = 0;
	public final static int pravac_lijevo = 1;
	public final static int pravac_desno = 2;
	public final static int pravac_gore = 3;
	
	/**
	 * Komande koje menjaju smjer
	 */
	public final static int skreni_desno = 0;
	public final static int skreni_lijevo = 1;
	
	/**
	 * Oznaka za prazno polje
	 */
	public final static int oznaka_prazno_polje = 0;
	/**
	 * Oznaka za tijelo zmije
	 */
	public final static int oznaka_tijelo_zmije = 1;
	/**
	 * Oznaka za hranu
	 */
	public final static int oznaka_jabuka = 2;
	/**
	 * Oznaka za prepreku
	 */
	public final static int oznaka_prepreka = 3;
	
	/**
	 * Poèetna dužina zmije
	 */
	public final static int pocetna_duzina_zmije = 5;
	/**
	 * Maximalni broj prepreka koje se mogu random generisati
	 */
	public final static int max_broj_prepreka = 5;
	
	/**
	 * Matrica stanja polja za igru
	 */
	private int matricaStanja[][];
	/**
	 * Pozicije tijela zmije unutar matrice stanja
	 */
	private LinkedList<Pozicija> tijeloZmije;
	/**
	 * Dimenzija matrice
	 */
	private int n;
	/**
	 * Trenutni smjer kretanja zmije
	 */
	private int smjer;
	private boolean kraj; // true ako je igra zavrsena
	/**
	 * Pozicija hrane unutar matrice stanja
	 */
	private Pozicija jabuka;
	
	private int bodovi; // totalni bodovi
	private int trenutni_bodovi_hrane; // koliko hrana na polju nosi bodova
	private int povecati_tijelo; // tijelo zmije povecavamo za neki broj
	boolean obrnuti_kontrole; // true za zamjenu desno - lijevo
	int trajanje_hrane; // hrana nasumicno nestane nakon isteka trajanja hrane
	
	/**
	 * Razlièiti tipovi hrane nose razlièit broj bodova
	 */
	public static Hashtable<String, Integer> hrana;
	
	
	/**
	 * Konstruktor sa jednim parametrom koji namješta polje za igricu
	 * 
	 * @param n velièina polja za igru - generiše polje velièine n x n
	 * 
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
		
	}
	
	/**
	 * Generise prepreke na polju igrice
	 * 
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
	
	/**
	 * Pomjera zmiju za jedno polje naprijed
	 * 
	 */
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
	
	/**
	 * Jedenje hrane, dodavanje bodova i izvršavanje efekta hrane (smanji tijelo, poveæaj tijelo za 3 polja ...)
	 * 
	 */
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
	
	/**
	 * Da li se u matrici na poziciji i,j nalazi hrana
	 * 
	 */
	private boolean daLiJeHrana(int i,int j) {
		return (jabuka.getI() == i && jabuka.getJ() == j);
	}
	
	/**
	 * Da li se u matrici na poziciji i,j nalazi prepreka
	 * 
	 */
	boolean daLiJePrepreka(int i, int j) {
		return (matricaStanja[i][j] == oznaka_tijelo_zmije || matricaStanja[i][j] == oznaka_prepreka);
	}
	
	/**
	 * Uklanjamo rep zmije
	 * 
	 */
	private void ukloniSaKrajaZmije() {
		tijeloZmije.removeLast();
	}
	
	/**
	 * Dodajemo novu glavu zmije
	 * 
	 */
	private void dodajNaPocetakZmije(int i, int j) {
		tijeloZmije.addFirst(new Pozicija(i,j));
	}
	
	/**
	 * Updatujemo poziciju tijela zmije i poziciju hrane u matrici stanja
	 * 
	 */
	
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
	
	
	/**
	 * Generišemo hranu
	 * 
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
	
	/**
	 * Seter smjera kretanja zmije
	 * 
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
			System.out.println("skreni desno");
			if(tmp_index == smjerovi.length - 1) {
				tmp_index = 0;
			}
			else {
				tmp_index++;
			}
		}
		
		else if(smjer_skretanja == skreni_lijevo) {
			System.out.println("skreni lijevo");
			if(tmp_index == 0) {
				tmp_index = smjerovi.length - 1;
			}
			else {
				tmp_index--;
			}
		}
		
		this.smjer = smjerovi[tmp_index];
	}
	
	
	/**
	 * Seter smjera kretanja zmije
	 * 
	 * @param i red
	 * @param j kolona
	 * 
	 * @return vrijednost stanja na poziciji i,j unutar matrice stanja
	 */
	public int getVrijednostStanja(int i, int j) {
		return matricaStanja[i][j];
	}
	
	/**
	 * Vraæa matricu stanja
	 * 
	 * @return matrica stanja
	 */
	public int[][] getTrenutnoStanje() {
		return matricaStanja;
	}

	/**
	 * Vraæa trenutni smjer zmije
	 *  
	 * @return smjer zmije
	 */
	public int getTrenutniSmjer() {
		return smjer;
	}
	
	/**
	 * Vraæa da li je igra gotova
	 * 
	 * @return kraj
	 */
	public boolean krajIgre() {
		return kraj;
	}
	
	/**
	 * Vraæa varijablu skreni_desno
	 * 
	 * @return skreni_desno
	 */
	public int getSkreniDesno() {
		return skreni_desno;
	}
	
	/**
	 * Vraæa varijablu skreni_lijevo
	 * 
	 * @return skreni_lijevo
	 */
	public int getSkreniLijevo() {
		return skreni_lijevo;
	}
	
	/**
	 * Vraæa koliko trenutna hrana nosi bodova
	 * 
	 * @return trenutni_bodovi_hrane
	 */
	public int getTrenutneBodove() {
		return trenutni_bodovi_hrane;
	}
}
