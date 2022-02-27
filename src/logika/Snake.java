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
	
	public final static int PRAVAC_DOLJE = 0;
	public final static int PRAVAC_LIJEVO = 1;
	public final static int PRAVAC_DESNO = 2;
	public final static int PRAVAC_GORE = 3;
	
	/**
	 * Komande koje menjaju smjer
	 */
	public final static int SKRENI_DESNO = 0;
	public final static int SKRENI_LIJEVO = 1;
	
	/**
	 * Oznaka za prazno polje
	 */
	public final static int OZNAKA_PRAZNO_POLJE = 0;
	/**
	 * Oznaka za tijelo zmije
	 */
	public final static int OZNAKA_TIJELO_ZMIJE = 1;
	/**
	 * Oznaka za hranu
	 */
	public final static int OZNAKA_JABUKA = 2;
	/**
	 * Oznaka za prepreku
	 */
	public final static int OZNAKA_PREPREKA = 3;
	
	/**
	 * Poèetna dužina zmije
	 */
	public final static int POCETNA_DUZINA_ZMIJE = 5;
	/**
	 * Maksimalni broj prepreka koje se mogu random generisati
	 */
	public final static int MAX_BROJ_PREPREKA = 5;
	
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
	/**
	 * Trenutni status igre, true ako je igra završena, false u suprotnom
	 */
	private boolean kraj; // true ako je igra zavrsena
	/**
	 * Pozicija hrane unutar matrice stanja
	 */
	private Pozicija jabuka;
	/**
	 * Trenutni bodovi jedne igre
	 */
	private int bodovi; // totalni bodovi
	/**
	 * Koliko hrana koja je na polju nosi bodova
	 */
	private int trenutni_bodovi_hrane; // koliko hrana na polju nosi bodova
	/**
	 * Varijabla koja se koristi za poveæanje ili skraæivanje tijela zmije.
	 * Ako je vrijednost razlièita od nule, tijelo zmije skraæujemo ili poveæavamo
	 * dok vrijednost varijable ne bude 0
	 */
	private int povecati_tijelo; // tijelo zmije povecavamo za neki broj
	/**
	 * Postavlja se na true kada se pojede specijalna hrana koja obræe kontrole
	 */
	private boolean obrnuti_kontrole; // true za zamjenu desno - lijevo
	/**
	 * Hrana se regeneriše nakon odreðenog vremena tj. nakon što zmija preðe
	 * neki nasumièni broj polja
	 */
	private int trajanje_hrane; // hrana nasumicno nestane nakon isteka trajanja hrane
	
	/**
	 * Razlièiti tipovi hrane nose razlièit broj bodova i koje maju razlièit efekat
	 * 
	 */
	public Hashtable<String, Integer> hrana;
	
	
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
				matricaStanja[i][j] = OZNAKA_PRAZNO_POLJE;
			}
		}
		

		smjer = PRAVAC_DESNO;
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
			matricaStanja[n/2][i] = OZNAKA_PRAZNO_POLJE;
		}
		
		tijeloZmije = new LinkedList<Pozicija>();
		for(int i = POCETNA_DUZINA_ZMIJE - 1;i>=0;i--) {
			tijeloZmije.add(new Pozicija(n/2, i));
			matricaStanja[n/2][i] = OZNAKA_TIJELO_ZMIJE;
		}
		
		generisiJabuku();
		matricaStanja[jabuka.getI()][jabuka.getJ()] = OZNAKA_JABUKA;
		
	}
	
	/**
	 * Generise prepreke na polju igrice. Broj prepreka se generiše nasumièno do
	 * nekog MAX_BROJ_PREPREKA koje mu zadamo, dužina svake prepreke je isto 
	 * nasumièna kao i da li æe biti postavljena horizontalno ili vertikalno
	 * 
	 */
	private void generisiPrepreke() {
		
		Random rn = new Random();
		int broj_prepreka = 1 + rn.nextInt(MAX_BROJ_PREPREKA);
		int duzina_prepreke;
		while(broj_prepreka != 0) {
			
			broj_prepreka--;
			duzina_prepreke = 1 + rn.nextInt(MAX_BROJ_PREPREKA);
			
			int smjer_prepreke = 0 + rn.nextInt(3);

			int pocetak_x = 0 + rn.nextInt(this.n);
			int pocetak_y = 0 + rn.nextInt(this.n);
			matricaStanja[pocetak_x][pocetak_y] = OZNAKA_PREPREKA;
			while(duzina_prepreke > 0) {
				Pozicija tmp_poz = new Pozicija(pocetak_x,pocetak_y);
				tmp_poz.pomjeriKoordinate(n, n, smjer_prepreke);
				pocetak_x = tmp_poz.getI();
				pocetak_y = tmp_poz.getJ();
				matricaStanja[pocetak_x][pocetak_y] = OZNAKA_PREPREKA;
				duzina_prepreke--;
			}
		}	
		
		
	}
	
	/**
	 * Pomjera zmiju za jedno polje naprijed. Takoðe provjerava da li je
	 * naredno polje u pitanju hrana ili prepreka.
	 * 
	 */
	public void pomjeriZmiju() {

		int i = tijeloZmije.getFirst().getI();
		int j = tijeloZmije.getFirst().getJ();
		
		if(smjer == PRAVAC_GORE) {
			if(i - 1 < 0) {
				i += n-1;
			}
			else {
				i = (i-1)%n;
			}
		}
		else if(smjer == PRAVAC_DOLJE) {
			i = (i+1)%n;
		}
		else if(smjer == PRAVAC_DESNO) {
			j = (j+1)%n;
		}
		else if(smjer == PRAVAC_LIJEVO) {
			if(j - 1 < 0) {
				j += n-1;
			}
			else {
				j = (j-1)%n;
			}
		}
		

		if(daLiJeHrana(i, j)) {
			pojediHranu();
			generisiJabuku();
		}
		else if(trajanje_hrane == 0) {
			generisiJabuku();
		}
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
		bodovi += Math.abs(trenutni_bodovi_hrane);
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
	 * @param i red
	 * @param j kolona
	 * 
	 * @return boolean 
	 */
	private boolean daLiJeHrana(int i,int j) {
		return (jabuka.getI() == i && jabuka.getJ() == j);
	}
	
	/**
	 * Da li se u matrici na poziciji i,j nalazi prepreka
	 * 
	 * @param i red
	 * @param j kolona
	 * 
	 * @return boolean 
	 */
	boolean daLiJePrepreka(int i, int j) {
		return (matricaStanja[i][j] == OZNAKA_TIJELO_ZMIJE || matricaStanja[i][j] == OZNAKA_PREPREKA);
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
				if(matricaStanja[i][j] == OZNAKA_PREPREKA) {
					matricaStanja[i][j] = OZNAKA_PREPREKA;
				}else {
					matricaStanja[i][j] = OZNAKA_PRAZNO_POLJE;
				}
				
			}
		}
		
		for(int i = 0;i<tijeloZmije.size();i++) {
			matricaStanja[tijeloZmije.get(i).getI()][tijeloZmije.get(i).getJ()] = OZNAKA_TIJELO_ZMIJE;
		}
		matricaStanja[jabuka.getI()][jabuka.getJ()] = OZNAKA_JABUKA;
		
	}
	
	
	/**
	 * Generišemo hranu. Ona može biti jedna od 4 tipa hrane. Pojavljuje se na
	 * nasumiènom mjestu i nasumièno nestaje. Neke hrane æe se èešæe pojavljivati
	 * od nekih drugih, npr. obièna hrana se pojavljuje najèešæe dok hrana koja
	 * obræe kontrole lijevo - desno najmanje
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
			
			if(matricaStanja[i][j] == OZNAKA_PRAZNO_POLJE) {
				jabuka = new Pozicija(i,j);
				return;
			}
			
		}
		
	}
	
	/**
	 * Seter smjera kretanja zmije
	 * 
	 * @param smjer_skretanja novi smjer kretanja zmije
	 * 
	 */
	
	public void setSmjer(int smjer_skretanja) {
		
		if(obrnuti_kontrole == true) {
			if(smjer_skretanja == SKRENI_DESNO) {
				smjer_skretanja = SKRENI_LIJEVO;
			}
			else {
				smjer_skretanja = SKRENI_DESNO;
			}
		}
		
		
		int smjerovi[] = {PRAVAC_GORE, PRAVAC_DESNO, PRAVAC_DOLJE, PRAVAC_LIJEVO};
		
		int tmp_index = 0;
		for(int i = 0;i<smjerovi.length;i++) {
			if(this.smjer == smjerovi[i]) {
				tmp_index = i;
				break;
			}
		}
		
		if(smjer_skretanja == SKRENI_DESNO) {
			if(tmp_index == smjerovi.length - 1) {
				tmp_index = 0;
			}
			else {
				tmp_index++;
			}
		}
		
		else if(smjer_skretanja == SKRENI_LIJEVO) {
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
	 * Vraæa cijelu matricu stanja
	 * 
	 * @return int[][] matrica stanja
	 */
	public int[][] getTrenutnoStanje() {
		return matricaStanja;
	}

	/**
	 * Vraæa trenutni smjer zmije
	 *  
	 * @return int smjer zmije
	 */
	public int getTrenutniSmjer() {
		return smjer;
	}
	
	/**
	 * Vraæa da li je igra gotova
	 * 
	 * @return boolean kraj
	 */
	public boolean krajIgre() {
		return kraj;
	}
	
	
	/**
	 * Vraæa koliko trenutna hrana nosi bodova
	 * 
	 * @return int trenutni_bodovi_hrane
	 */
	public int getTrenutneBodove() {
		return trenutni_bodovi_hrane;
	}
	
	/**
	 * Vraæa broj bodova u trenutnoj igri
	 * 
	 * @return int bodovi
	 */
	public int getBodove() {
		return bodovi;
	}
	
	
}
