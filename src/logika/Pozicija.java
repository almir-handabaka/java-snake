package logika;
import java.awt.Point;

public class Pozicija {
	Point tacka;
	
	private int i;
	private int j;
	
	Pozicija(int x, int y){
		tacka = new Point(x,y);
		this.i = x;
		this.j = y;
	}
	
	Pozicija(){
		Point tacka = new Point();
	}
	
	public int getI() {
		return (int) tacka.getX();
		//return i;
		
	}
	
	public int getJ() {
		return (int) tacka.getY();
		//return j;
	}
	
	public void setI(int x) {
		this.i = x;
	}
	
	public void setJ(int y) {
		this.j = y;
	}
	
	
	
	
	public void pomjeriKoordinate(int n, int m, int smjer) {
		//System.out.println(smjer);
		int i = (int) tacka.getX();
		int j = (int) tacka.getY();
		
		if(smjer == Snake.pravac_gore) {
			if(i - 1 < 0) {
				i += n-1;
			}
			else {
				i = (i-1)%n;
			}
			
			
		}
		else if(smjer == Snake.pravac_dolje) {
			//i++;
			System.out.println("dolje " + (i+1)%n);
			i = (i+1)%n;
			
			
		}
		else if(smjer == Snake.pravac_desno) {
			//j++;
			j = (j+1)%m;
			
			
		}
		else if(smjer == Snake.pravac_lijevo) {
			//j--;
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
