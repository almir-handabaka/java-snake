package gui;
import javax.swing.JFrame;
import javax.swing.JPanel;


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
		//jf.setSize(800, 800);
		jf.setContentPane(mojPanel);
		jf.pack();
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}
}
