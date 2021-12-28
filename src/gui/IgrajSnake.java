package gui;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class IgrajSnake {
	public static void main(String[] args) {
		JFrame jf = new JFrame("Snake");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MojPanel mojPanel = new MojPanel(20);
		jf.setSize(1000, 1000);
		jf.setContentPane(mojPanel);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}
}
