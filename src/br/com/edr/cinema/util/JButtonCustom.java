package br.com.edr.cinema.util;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.naming.Context;
import javax.swing.JButton;

public class JButtonCustom extends JButton {
	
	Context ctx;
	
	public JButtonCustom(String texto, String nome, boolean enable) {
		this.setEnabled(enable);
		this.setText(texto); //coluna
		this.setName(nome); //fileira
	}
	
	public JButtonCustom(String texto, String nome, ActionListener listener) {
		this.setText(texto); //coluna
		this.setName(nome); //fileira
		this.addActionListener(listener);
		this.setBackground(Color.green);
	}
	
}
