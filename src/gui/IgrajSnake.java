package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Klasa za pokretanje igre sa GUI
 * 
 * @author Almir Handabaka
 *
 */
public class IgrajSnake {
	public static void main(String[] args) {
		
		JFrame jf = new JFrame("Snake");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MojPanel mojPanel = new MojPanel(20);
		jf.add(mojPanel);
	
		jf.pack();
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}
}
