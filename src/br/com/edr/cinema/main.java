package br.com.edr.cinema;

import javax.swing.JFrame;

import br.com.edr.cinema.telas.FrmPrincipal;

public class main {

	public static void main(String[] args) {
		
		FrmPrincipal frmPrincipal = new FrmPrincipal(true);
		frmPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frmPrincipal.pack();
	    frmPrincipal.setVisible(true);

	}

}
