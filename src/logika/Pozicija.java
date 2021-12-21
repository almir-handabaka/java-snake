package logika;

public class Pozicija {
	
	private int i;
	private int j;
	
	Pozicija(int x, int y){
		this.i = x;
		this.j = y;
	}
	
	Pozicija(){}
	
	public int getI() {
		return i;
	}
	
	public int getJ() {
		return j;
	}
	
	public void setI(int x) {
		this.i = x;
	}
	
	public void setJ(int y) {
		this.j = y;
	}
}
