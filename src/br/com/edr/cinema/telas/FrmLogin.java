package br.com.edr.cinema.telas;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.edr.cinema.util.ConnectionFactory;

public class FrmLogin extends JFrame implements ActionListener, WindowListener {

	private Button btnLogin;
	private JTextField txtNome;
	private JPasswordField txtPass;
	private JFrame telaPrincipal;
	private boolean sucesso;
	private FrmLogin telaLogin;

	public FrmLogin(JFrame telaPrincipal, boolean closeOperation) {
		this.telaPrincipal = (JFrame)telaPrincipal;

		telaLogin = new FrmLogin();
		telaLogin.setTitle("Tela de Login");

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(60, 40, 60, 25);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(60, 70, 60, 25);

		txtNome = new JTextField();
		txtNome.setBounds(110, 40, 160, 25);

		txtPass = new JPasswordField();
		txtPass.setBounds(110, 70, 160, 25);

		btnLogin = new Button("Login");
		btnLogin.setBounds(210, 100, 60, 25);

		btnLogin.addActionListener(this);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.add(lblNome);
		panel.add(lblSenha);
		panel.add(txtNome);
		panel.add(txtPass);
		panel.add(btnLogin);

		telaLogin.addWindowListener(this);
		telaLogin.add(panel);
		telaLogin.setSize(400, 200);
		if(closeOperation)
			telaLogin.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		telaLogin.setVisible(true);
		telaLogin.setAlwaysOnTop(true);
		telaLogin.setLocationRelativeTo(null);
	}

	public FrmLogin() {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		Connection conn = new ConnectionFactory().getConnection();
		String login = "";
		String senha = "";

		try {
			java.sql.PreparedStatement ps;
			ps = conn.prepareStatement("select * from usuarios where login = ? and password = ? ");
			ps.setString(1, txtNome.getText().trim());
			ps.setString(2, String.valueOf(txtPass.getPassword()));
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				login = rs.getString("login");
				senha = rs.getString("password");
			}
			rs.close();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}

		telaLogin.setAlwaysOnTop(false);
		
		if (arg0.getSource() == btnLogin) {
			if (!login.isEmpty() && !senha.isEmpty() && txtNome.getText().trim().equals(login) && String.valueOf(txtPass.getPassword()).equals(senha)) {
				sucesso = true;
				JOptionPane.showMessageDialog(null, "Login realizado com sucesso!");
				telaPrincipal.setEnabled(true);
				telaLogin.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			} else
				JOptionPane.showMessageDialog(null, "Usuário ou Senha inválido!");
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		if (!sucesso) {
			telaPrincipal.setVisible(false);
			telaPrincipal.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			System.exit(0);
			dispose();
		}

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

}
