package br.com.edr.cinema.telas;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import br.com.edr.cinema.dao.IngressoDAO;
import br.com.edr.cinema.entidades.Ingresso;
import br.com.edr.cinema.util.ConnectionFactory;
import br.com.edr.cinema.util.JButtonCustom;

public class FrmPrincipal extends JFrame implements ActionListener {

	private Container pane;
	private IngressoDAO ingressoDAO;

	public FrmPrincipal(boolean login) {

		pane = getContentPane();
		pane.setLayout(new GridLayout(8, 12));

		criaLayoutCadeiras();
		super.setSize(800, 600);
		super.setLocationRelativeTo(null);
		super.setContentPane(pane);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setTitle("Cinema em CASA");
		setEnabled(false);

		FrmLogin frmLogin = new FrmLogin(this, true);
	}

	public void criaLayoutCadeiras() {

		ingressoDAO = new IngressoDAO(new ConnectionFactory().getConnection());

		List<Ingresso> ingressos = ingressoDAO.getIngressos();

		JButton[][] buttons = 
			{
				{ new JButtonCustom("H", "H", false), new JButtonCustom("0", "H", this), new JButtonCustom("1", "H", this), new JButtonCustom("2", "H", this), new JButtonCustom("3", "H", this), new JButtonCustom("4", "H", this), new JButtonCustom("5", "H", this), new JButtonCustom("6", "H", this), new JButtonCustom("7", "H", this), new JButtonCustom("8", "H", this), new JButtonCustom("9", "H", this), new JButtonCustom("10", "H", this), new JButtonCustom("11", "H", this)},
	            { new JButtonCustom("G", "G", false), new JButtonCustom("0", "G", this), new JButtonCustom("1", "G", this), new JButtonCustom("2", "G", this), new JButtonCustom("3", "G", this), new JButtonCustom("4", "G", this), new JButtonCustom("5", "G", this), new JButtonCustom("6", "G", this), new JButtonCustom("7", "G", this), new JButtonCustom("8", "G", this), new JButtonCustom("9", "G", this), new JButtonCustom("10", "G", this), new JButtonCustom("11", "G", this)},
	            { new JButtonCustom("F", "F", false), new JButtonCustom("",  "", false), new JButtonCustom("1", "F", this), new JButtonCustom("2", "F", this), new JButtonCustom("3", "F", this), new JButtonCustom("4", "F", this), new JButtonCustom("5", "F", this), new JButtonCustom("6", "F", this), new JButtonCustom("7", "F", this), new JButtonCustom("8", "F", this), new JButtonCustom("9", "F", this), new JButtonCustom("10", "F", this), new JButtonCustom("", "", false)},
	            { new JButtonCustom("E", "E", false), new JButtonCustom("",  "", false), new JButtonCustom("1", "E", this), new JButtonCustom("2", "E", this), new JButtonCustom("3", "E", this), new JButtonCustom("4", "E", this), new JButtonCustom("5", "E", this), new JButtonCustom("6", "E", this), new JButtonCustom("7", "E", this), new JButtonCustom("8", "E", this), new JButtonCustom("9", "E", this), new JButtonCustom("10", "E", this), new JButtonCustom("", "", false)},
	            { new JButtonCustom("D", "D", false), new JButtonCustom("",  "", false), new JButtonCustom("1", "D", this), new JButtonCustom("2", "D", this), new JButtonCustom("3", "D", this), new JButtonCustom("4", "D", this), new JButtonCustom("5", "D", this), new JButtonCustom("6", "D", this), new JButtonCustom("7", "D", this), new JButtonCustom("8", "D", this), new JButtonCustom("9", "D", this), new JButtonCustom("10", "D", this), new JButtonCustom("", "", false)},
	            { new JButtonCustom("C", "C", false), new JButtonCustom("",  "", false), new JButtonCustom("1", "C", this), new JButtonCustom("2", "C", this), new JButtonCustom("3", "C", this), new JButtonCustom("4", "C", this), new JButtonCustom("5", "C", this), new JButtonCustom("6", "C", this), new JButtonCustom("7", "C", this), new JButtonCustom("8", "C", this), new JButtonCustom("9", "C", this), new JButtonCustom("10", "C", this), new JButtonCustom("", "", false)},
	            { new JButtonCustom("B", "B", false), new JButtonCustom("",  "", false), new JButtonCustom("1", "B", this), new JButtonCustom("2", "B", this), new JButtonCustom("3", "B", this), new JButtonCustom("4", "B", this), new JButtonCustom("5", "B", this), new JButtonCustom("6", "B", this), new JButtonCustom("7", "B", this), new JButtonCustom("8", "B", this), new JButtonCustom("9", "B", this), new JButtonCustom("10", "B", this), new JButtonCustom("", "", false)},
	            { new JButtonCustom("A", "A", false), new JButtonCustom("",  "", false), new JButtonCustom("1", "A", this), new JButtonCustom("2", "A", this), new JButtonCustom("3", "A", this), new JButtonCustom("4", "A", this), new JButtonCustom("5", "A", this), new JButtonCustom("6", "A", this), new JButtonCustom("7", "A", this), new JButtonCustom("8", "A", this), new JButtonCustom("9", "A", this), new JButtonCustom("10", "A", this), new JButtonCustom("", "", false)}
	        };
		JButton btn;
		for (int i = 0; i < buttons.length; i++) {
			pane.add(buttons[i][0]);
			for (int j = 1; j < buttons[i].length; j++) {
				
				btn = buttons[i][j];
				Ingresso searchIngresso = searchIngresso(btn.getName(), btn.getText());
				
				if(searchIngresso != null && searchIngresso.isFlagVendida())
					btn.setBackground(Color.red);	
				else
					btn.setBackground(Color.green);
				
				pane.add(btn);
			}
		}
	}
	
	private Ingresso searchIngresso(String fileira, String coluna){
		
		ingressoDAO = new IngressoDAO(new ConnectionFactory().getConnection());
		List<Ingresso> ingressos = ingressoDAO.getIngressos();
		
		for (Ingresso ingresso : ingressos) {
			if(fileira.equalsIgnoreCase(ingresso.getFileira()) && coluna.equalsIgnoreCase(String.valueOf(ingresso.getColuna())))
				return ingresso;
		}
		
		return null;		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new FrmVenda(((JButton) e.getSource()), this);
	}

}
