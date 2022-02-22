package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JPanel;

import logika.Snake;



/**
 * Klasa za implementaciju GUI-a za igru Snake
 * 
 * @author Almir Handabaka
 *
 */

public class MojPanel extends JPanel{
	private Snake snake;
	//private JPanel prikazTabele;
	private JButton tabelaDugmadi[][];
	
	/**
	 * Konstruktor sa jednim parametrom koji namje�ta polje za igricu
	 * 
	 * @param n veli�ina polja za igru - generi�e polje veli�ine n x n
	 * 
	 */
	public MojPanel(int n){
		snake = new Snake(n);
		setLayout(new GridLayout(n,n));
		tabelaDugmadi = new JButton[n][n];
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<n;j++) {
				tabelaDugmadi[i][j] = new JButton();
				add(tabelaDugmadi[i][j]);
				tabelaDugmadi[i][j].addKeyListener(new MojKeyListener());
				tabelaDugmadi[i][j].setBackground(getBoja(snake.getVrijednostStanja(i, j)));
				
				tabelaDugmadi[i][j].setPreferredSize(new Dimension(30, 30));
			}
		}
		
		MojTimerTask mtt = new MojTimerTask();
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(mtt, 0, 200);
		
	}
	
	/**
	 * Vrati boju ovisno od elementa polja
	 */
	private Color getBoja(int boja) {
		if (boja == 1) {
			return Color.GREEN;
		}
		else if (boja == 2) {
			if(snake.hrana.get("tip_1") == snake.getTrenutneBodove()) {
				return Color.PINK;
			}
			else if(snake.hrana.get("tip_2") == snake.getTrenutneBodove()) {
				return Color.CYAN;
			}
			else if(snake.hrana.get("tip_3") == snake.getTrenutneBodove()) {
				return Color.LIGHT_GRAY;
			}
			else if(snake.hrana.get("tip_4") == snake.getTrenutneBodove()) {
				return Color.RED;
			}
			
		}
		else if(boja == 3) {
			return Color.YELLOW;
		}
		return Color.BLACK;
	}
	
	/**
	 * Osvje�i trenutno stanje igre, promjeni boju polja ovisno od stanja
	 * 
	 */
	public void osvjeziStanjeTabele() {
		for(int i = 0;i<tabelaDugmadi.length;i++) {
			for(int j = 0;j<tabelaDugmadi[0].length;j++) {
				//System.out.print(snake.getVrijednostStanja(i, j));
				tabelaDugmadi[i][j].setBackground(getBoja(snake.getVrijednostStanja(i, j)));
				
			}
			//System.out.println();
		}
		//System.out.println("-----------------");
	}
	
	/**
	 * Implementacija KeyListenera za komande
	 * 
	 */
	class MojKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
			
		}
		// desno 39, lijevo 37, dolje 40, up 38
		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			//System.out.println("keyPressed: " + arg0.getExtendedKeyCode());
			if (arg0.getExtendedKeyCode() == 39) {
				snake.setSmjer(snake.getSkreniDesno());
				//System.out.println("desno");
			}
			else if (arg0.getExtendedKeyCode() == 37) {
				snake.setSmjer(snake.getSkreniLijevo());
				//System.out.println("lijevo");
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	/**
	 * Implementacija TimerTaska koji vrti igru
	 * 
	 */
	class MojTimerTask extends TimerTask {

		@Override
		public void run() {
			snake.pomjeriZmiju();
			osvjeziStanjeTabele();
			if (snake.krajIgre()) {
				cancel();
				System.out.println("KRAJ IGRE");
			}
		}
		
		
	}
}
