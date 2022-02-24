package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Klasa za implementaciju statusne trake koja prikazuje rezultat i poruke
 * 
 * @author Almir Handabaka
 *
 */


public class StatusnaTraka extends JPanel{
	/**
	 * JLabel za prikaz rezultat
	 */
	public JLabel rezultat;
	/**
	 * JLabel za prikaz informacije
	 */
	public JLabel informacije;
	
	
	/**
	 * Konstruktor bez parametara, namješta statusnu traku
	 * 
	 */
	public StatusnaTraka() {
		setLayout(new GridLayout(1,2));
		
		rezultat = new JLabel("Rezultat: 0", SwingConstants.CENTER);
		rezultat.setPreferredSize(new Dimension(20, 40));
		rezultat.setFont(new Font("Serif", Font.PLAIN, 19));
		
		informacije = new JLabel("Hrana: ", SwingConstants.CENTER);
		informacije.setPreferredSize(new Dimension(20, 40));
		informacije.setFont(new Font("Serif", Font.PLAIN, 19));
		
		add(rezultat);
		add(informacije);
	}
	
	/*
	 *  Updatuje rezultat na statusnoj traci
	 */
	
	public void setRezultat(int rez) {
		rezultat.setText("Rezultat: " + rez);
	}
	
	/*
	 *  Updatuje informacije na statusnoj traci
	 */
	
	public void setInformacije(String info) {
		informacije.setText(info);
	}
}
