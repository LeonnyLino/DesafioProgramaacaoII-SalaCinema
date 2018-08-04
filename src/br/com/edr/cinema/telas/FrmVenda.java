package br.com.edr.cinema.telas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.edr.cinema.dao.IngressoDAO;
import br.com.edr.cinema.entidades.Ingresso;
import br.com.edr.cinema.util.ConnectionFactory;

public class FrmVenda extends JFrame implements ActionListener, KeyListener {

//	public static void main(String[] args) {
//		new FrmVenda(new JButton(), null);
//	}

	private JButton btnListar;
	private JButton btnSalvar;

	private JLabel lblFilme;
	private JLabel lblSessao;
	private JLabel lblTitle;
	private JLabel lblFileira;
	private JLabel lblColuna;
	private JLabel lblCarteiraAluno;
	private JLabel lblStatus;
	private JLabel lblIngressos;

	private JTextField txtFileira;
	private JTextField txtColuna;
	private JTextField txtCarteiraAluno;

	private JCheckBox chbEstudante;
	private JCheckBox chbFlagAtivado;

	private JComboBox<String> cbIngressos;
	// Vector<String> vectorIngressos;
	private JPanel panel;

	private ArrayList<Ingresso> listaIngressos;
	private IngressoDAO ingressoDAO;
	private List<Ingresso> ingressos;

	private JButton btnCadeira;
	private JTextField txtFilme;
	private JTextField txtSessao;
	private JButton btnDeletar;
	private DefaultComboBoxModel dm;
	private FrmPrincipal frmPrincipal;

	public FrmVenda(JButton btnCadeira, FrmPrincipal frmPrincipal) {
		this.frmPrincipal = frmPrincipal;
		this.btnCadeira = btnCadeira;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 400);
		setVisible(true);

		inicializarObjetos();

		panel = new JPanel();
		panel.setLayout(null);
		setPanel();

		super.setContentPane(panel);
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		super.setTitle("Cadastro de Vendas");
		super.setLocationRelativeTo(null);
		super.setVisible(true);

		// addWindowListener(new WindowAdapter()
		// {
		// @Override
		// public void windowClosing(WindowEvent e)
		// {
		//// frmPrincipal.criaLayoutCadeiras();
		// new FrmPrincipal(false);
		// e.getWindow().dispose();
		// }
		// });

		btnSalvar.addActionListener(this);
		btnDeletar.addActionListener(this);
		btnListar.addActionListener(this);
		chbEstudante.addActionListener(this);
		chbFlagAtivado.addActionListener(this);
		txtCarteiraAluno.addKeyListener(this);
		// txtSessao.addKeyListener(this);

		cbIngressos.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (ingressos != null && ingressos.size() > 0) {
						for (Ingresso ingresso : ingressos) {
							if (ingresso.toString().equals(e.getItem())) {
								txtFileira.setText(ingresso.getFileira());
								txtColuna.setText(String.valueOf(ingresso.getColuna()));
								txtCarteiraAluno.setText(
										ingresso.isEstudante() ? String.valueOf(ingresso.getNumeroCarteira()) : "");
								chbEstudante.setSelected(ingresso.isEstudante());
								chbFlagAtivado.setSelected(ingresso.isFlagVendida());
							}
						}
					}
				}
			}
		});

	}

	private void inicializarObjetos() {
		btnDeletar = new JButton("DELETAR");
		btnDeletar.setBounds(610, 70, 110, 25);

		btnSalvar = new JButton("SALVAR");
		btnSalvar.setBounds(610, 280, 110, 25);

		btnListar = new JButton("LISTAR");
		btnListar.setBounds(320, 280, 80, 25);

		lblTitle = new JLabel("Venda Ingressos");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
		lblTitle.setBounds(270, 10, 200, 40);

		lblFilme = new JLabel("Nome do Filme:");
		lblFilme.setFont(new Font("Arial", Font.PLAIN, 16));
		lblFilme.setBounds(30, 70, 120, 25);

		txtFilme = new JTextField();
		txtFilme.setFont(new Font("Arial", Font.PLAIN, 16));
		txtFilme.setBounds(160, 70, 200, 25);

		lblSessao = new JLabel("Sessão:");
		lblSessao.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSessao.setBounds(30, 110, 80, 25);

		txtSessao = new JTextField();
		txtSessao.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSessao.setBounds(160, 110, 200, 25);

		lblFileira = new JLabel("Fileira:");
		lblFileira.setFont(new Font("Arial", Font.PLAIN, 16));
		lblFileira.setBounds(30, 150, 50, 25);

		txtFileira = new JTextField();
		txtFileira.setFont(new Font("Arial", Font.PLAIN, 16));
		txtFileira.setBounds(160, 150, 100, 25);
		txtFileira.setText(btnCadeira.getName());
		txtFileira.setEnabled(false);

		lblColuna = new JLabel("Coluna:");
		lblColuna.setFont(new Font("Arial", Font.PLAIN, 16));
		lblColuna.setBounds(30, 190, 90, 25);
				
		txtColuna = new JTextField();
		txtColuna.setFont(new Font("Arial", Font.PLAIN, 16));
		txtColuna.setBounds(160, 190, 100, 25);
		txtColuna.setText(btnCadeira.getText());
		txtColuna.setEnabled(false);

		chbEstudante = new JCheckBox(" Estudante");
		chbEstudante.setFont(new Font("Arial", Font.PLAIN, 16));
		chbEstudante.setBounds(420, 150, 100, 40);
		chbEstudante.setSelected(false);

		lblCarteiraAluno = new JLabel("Número Carteira:");
		lblCarteiraAluno.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCarteiraAluno.setBounds(420, 190, 120, 25);

		txtCarteiraAluno = new JTextField();
		txtCarteiraAluno.setFont(new Font("Arial", Font.PLAIN, 16));
		txtCarteiraAluno.setBounds(560, 190, 150, 25);
		txtCarteiraAluno.setEnabled(false);

		lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Arial", Font.PLAIN, 16));
		lblStatus.setBounds(30, 230, 90, 25);

		chbFlagAtivado = new JCheckBox(" Ativado/Desativado");
		chbFlagAtivado.setFont(new Font("Arial", Font.PLAIN, 16));
		chbFlagAtivado.setBounds(160, 230, 180, 25);
		chbFlagAtivado.setSelected(true);

		lblIngressos = new JLabel("Ingressos:");
		lblIngressos.setFont(new Font("Arial", Font.PLAIN, 16));
		lblIngressos.setBounds(30, 280, 80, 25);

		cbIngressos = new JComboBox();
		cbIngressos.setFont(new Font("Arial", Font.PLAIN, 16));
		cbIngressos.setBounds(160, 280, 140, 25);
		cbIngressos.setEnabled(false);

		preencherCampos();

	}

	private void preencherCampos() {
		Ingresso ingresso = getIngresso(btnCadeira.getName(),
				!btnCadeira.getText().equalsIgnoreCase("") ? Integer.parseInt(btnCadeira.getText()) : 0);

		if (ingresso != null) {
			txtFileira.setText(ingresso.getFileira());
			txtColuna.setText(String.valueOf(ingresso.getColuna()));
			chbFlagAtivado.setSelected(ingresso.isFlagVendida());
			chbEstudante.setSelected(ingresso.isEstudante());
			txtCarteiraAluno.setText(String.valueOf(ingresso.getNumeroCarteira()));
			txtCarteiraAluno.setEnabled(ingresso.isEstudante());			
		}
	}

	private Ingresso getIngresso(String fileira, int coluna) {
		ingressoDAO = new IngressoDAO(new ConnectionFactory().getConnection());
		return ingressoDAO.buscarIngresso(fileira, coluna);
	}

	private void setPanel() {
		panel.add(btnListar);
		panel.add(btnSalvar);
		panel.add(btnDeletar);
		panel.add(lblTitle);
		panel.add(lblFilme);
		panel.add(lblSessao);
		panel.add(lblFileira);
		panel.add(lblColuna);
		panel.add(lblCarteiraAluno);
		panel.add(lblStatus);
		panel.add(lblIngressos);
		panel.add(cbIngressos);

		panel.add(txtFilme);
		panel.add(txtSessao);
		panel.add(txtFileira);
		panel.add(txtColuna);
		panel.add(txtCarteiraAluno);
		panel.add(chbFlagAtivado);

		panel.add(chbEstudante);
	}

	private void salvar() {

		if (!verificaCamposObrigatorio(true))
			return;

		// ingressoDAO = new IngressoDAO(new
		// ConnectionFactory().getConnection());
		// if (ingressoDAO.checaCadeiraDisponivel(txtFileira.getText(),
		// Integer.parseInt(txtColuna.getText())) ||
		// chbFlagAtivado.isSelected()) {
		Ingresso ingresso = new Ingresso();
		ingresso.setFileira(txtFileira.getText());
		ingresso.setColuna(Integer.parseInt(txtColuna.getText()));

		if (!txtCarteiraAluno.getText().trim().equalsIgnoreCase(""))
			ingresso.setNumeroCarteira(Integer.parseInt(txtCarteiraAluno.getText()));

		ingresso.setEstudante(chbEstudante.isSelected());
		ingresso.setFlagVendida(chbFlagAtivado.isSelected());

		ingressoDAO = new IngressoDAO(new ConnectionFactory().getConnection());
		ingressoDAO.insertOrUpdate(ingresso);
		
		if(!chbFlagAtivado.isSelected()){
			deletar();
			JOptionPane.showMessageDialog(null, "Desativado com sucesso!");
		}else{
			btnCadeira.setBackground(Color.RED);
			JOptionPane.showMessageDialog(null, "Venda efetuada com sucesso!");
		}
		
		limparCampos();		
		
		this.frmPrincipal.invalidate();
		this.frmPrincipal.validate();
		this.frmPrincipal.repaint();
		// } else
		// JOptionPane.showMessageDialog(null, "Essa cadeira não está
		// disponível!");

	}

	private void deletar() {

		if (!verificaCamposObrigatorio(false))
			return;

		ingressoDAO = new IngressoDAO(new ConnectionFactory().getConnection());

		Ingresso ingresso = ingressoDAO.buscarIngresso(txtFileira.getText(), Integer.parseInt(txtColuna.getText()));

		if (ingresso != null) {
			ingressoDAO = new IngressoDAO(new ConnectionFactory().getConnection());
			ingressoDAO.delete(ingresso.getId());
			limparCampos();
			btnCadeira.setBackground(Color.green);
		} else
			JOptionPane.showMessageDialog(null, "Essa cadeira não está salva!");

	}

	private void limparCampos() {
		txtFileira.setText("");
		txtColuna.setText("");
		txtCarteiraAluno.setText("");
		dm = new DefaultComboBoxModel();
		cbIngressos.setModel(dm);
		cbIngressos.setSelectedIndex(-1);
		chbEstudante.setSelected(false);
		chbFlagAtivado.setSelected(true);
	}

	private boolean verificaCamposObrigatorio(boolean salvar) {

		if (salvar && chbFlagAtivado.isSelected()) {
			if (txtFilme.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "O Campo Nome do Filme não poder ser vazio!");
				return false;
			}

			if (txtSessao.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "O Campo Sessão não poder ser vazio!");
				return false;
			}
		}

		if (txtFileira.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "O Campo Fileira não poder ser vazio!");
			return false;
		}

		if (txtColuna.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "O Campo Coluna não poder ser vazio!");
			return false;
		}

		if (chbEstudante.isSelected() && txtCarteiraAluno.getText().trim().length() < 3) {
			JOptionPane.showMessageDialog(null, "O Campo Número Carteira não poder ser menor 03 dígitos!");
			return false;
		}

		return true;
	}

	private void listar() {

		limparCampos();

		ingressoDAO = new IngressoDAO(new ConnectionFactory().getConnection());
		ingressos = ingressoDAO.getIngressos();

		if (ingressos != null && ingressos.size() > 0) {

			// List<String> vendidos = ingressos
			// .stream().map(i -> i.getFileira() + " - " +
			// ingressos.stream().filter(x -> x == i)
			// .map(i2 -> i2.getColuna()).collect(Collectors.toList()).get(0))
			// .collect(Collectors.toList());

			List<String> vendidos = ingressos.stream().map(i -> i.toString()).collect(Collectors.toList());

			for (String str : vendidos) {
				dm.addElement(str);
			}
			cbIngressos.setModel(dm);
			cbIngressos.setEnabled(true);
		} else {
			JOptionPane.showMessageDialog(null, "A lista está vazia!");
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSalvar) {
			salvar();
		}

		if (e.getSource() == btnDeletar) {
			deletar();
		}

		if (e.getSource() == btnListar) {
			listar();
		}

		if (e.getSource() == chbEstudante) {
			if (chbEstudante.isSelected()) {
				txtCarteiraAluno.setEnabled(true);
				txtCarteiraAluno.setText("0");
			} else {
				txtCarteiraAluno.setEnabled(false);
				txtCarteiraAluno.setText("0");
			}
		}
		
		if (e.getSource() == chbFlagAtivado) {
			if (!chbFlagAtivado.isSelected()) {
				JOptionPane.showMessageDialog(null, "Confirme o usuário e senha!", "Permissão!", JOptionPane.QUESTION_MESSAGE);				
				FrmLogin frmLogin = new FrmLogin(this, false);
			}
		}		

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {

		if (!Character.isDigit(e.getKeyChar()))
			e.consume();

		if (chbEstudante.isSelected() && txtCarteiraAluno.hasFocus()) {
			if (txtCarteiraAluno.getText().length() >= 10)
				e.consume();
		}

	}

}