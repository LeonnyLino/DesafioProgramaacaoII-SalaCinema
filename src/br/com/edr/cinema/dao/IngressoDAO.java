package br.com.edr.cinema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.edr.cinema.entidades.Ingresso;
import br.com.edr.cinema.interfaces.IIngressoIpl;
import br.com.edr.cinema.util.ConnectionFactory;

public class IngressoDAO implements IIngressoIpl {

	private Connection conn;

	public IngressoDAO(Connection conn) {
		this.conn = conn;
		// Connection conn = new ConnectionFactory().getConnection();
	}

	@Override
	public boolean insertOrUpdate(Ingresso ingresso) {
		boolean execute;

		String sql = "";
		PreparedStatement ps;

		try {

			if(conn.isClosed())
				conn = new ConnectionFactory().getConnection();
				
			Ingresso old = buscarIngresso(ingresso.getFileira(), ingresso.getColuna());
			if (old != null) {
				if(conn.isClosed())
					conn = new ConnectionFactory().getConnection();
				return update(ingresso, old.getId());
			} else {
				if(conn.isClosed())
					conn = new ConnectionFactory().getConnection();

				if (ingresso.isEstudante()) {
					sql = "INSERT INTO INGRESSOS (fileira, coluna, estudante, numeroCarteira, flag) VALUES (? , ?, ?, ?, ?);";
					ps = conn.prepareStatement(sql);
					ps.setString(1, ingresso.getFileira());
					ps.setInt(2, ingresso.getColuna());
					ps.setBoolean(3, ingresso.isEstudante());
					ps.setLong(4, ingresso.getNumeroCarteira());
					ps.setBoolean(5, ingresso.isFlagVendida());
				} else {
					sql = "INSERT INTO INGRESSOS (fileira, coluna, estudante, flag) VALUES (?, ?, ?, ?);";
					ps = conn.prepareStatement(sql);
					ps.setString(1, ingresso.getFileira());
					ps.setInt(2, ingresso.getColuna());
					ps.setBoolean(3, ingresso.isEstudante());
					ps.setBoolean(4, ingresso.isFlagVendida());
				}

				execute = ps.execute();
				ps.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return execute;
	}

	@Override
	public boolean insert(Ingresso ingresso) {
		boolean execute;

		String sql = "";
		PreparedStatement ps;

		try {
			if (ingresso.isEstudante()) {
				sql = "INSERT INTO INGRESSOS (fileira, coluna, estudante, numeroCarteira, flag) VALUES (? , ?, ?, ?, ?);";
				ps = conn.prepareStatement(sql);
				ps.setString(1, ingresso.getFileira());
				ps.setInt(2, ingresso.getColuna());
				ps.setBoolean(3, ingresso.isEstudante());
				ps.setLong(4, ingresso.getNumeroCarteira());
				ps.setBoolean(5, ingresso.isFlagVendida());
			} else {
				sql = "INSERT INTO INGRESSOS (fileira, coluna, estudante, flag) VALUES (?, ?, ?, ?);";
				ps = conn.prepareStatement(sql);
				ps.setString(1, ingresso.getFileira());
				ps.setInt(2, ingresso.getColuna());
				ps.setBoolean(3, ingresso.isEstudante());
				ps.setBoolean(4, ingresso.isFlagVendida());
			}

			execute = ps.execute();
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
		return execute;
	}

	@Override
	public boolean update(Ingresso ingresso, long id) {
		boolean execute;
		String sql = "";
		PreparedStatement ps;

		try {
			if (ingresso.isEstudante()) {
				sql = "UPDATE INGRESSOS SET fileira = ?, coluna = ?, estudante = ?, numeroCarteira = ?, flag = ? WHERE idIngressos = ?;";
				ps = conn.prepareStatement(sql);
				ps.setString(1, ingresso.getFileira());
				ps.setInt(2, ingresso.getColuna());
				ps.setBoolean(3, ingresso.isEstudante());
				ps.setLong(4, ingresso.getNumeroCarteira());
				ps.setBoolean(5, ingresso.isFlagVendida());
				ps.setLong(6, id);
			} else {
				sql = "UPDATE INGRESSOS SET fileira = ?, coluna = ?, estudante = ?, flag = ? WHERE idIngressos = ?;";
				ps = conn.prepareStatement(sql);
				ps.setString(1, ingresso.getFileira());
				ps.setInt(2, ingresso.getColuna());
				ps.setBoolean(3, ingresso.isEstudante());
				ps.setBoolean(4, ingresso.isFlagVendida());
				ps.setLong(5, id);
			}

			execute = ps.execute();
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
		return execute;
	}

	@Override
	public boolean delete(long id) {
		boolean execute;
		String sql = "DELETE FROM INGRESSOS WHERE idIngressos = ?;";

		try {

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, id);

			execute = ps.execute();
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
		return execute;
	}

	@Override
	public List<Ingresso> getIngressos() {
		ArrayList<Ingresso> lista = new ArrayList<Ingresso>();
		String sql = "SELECT * FROM INGRESSOS ORDER BY fileira desc, coluna asc";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Ingresso i = new Ingresso();

				i.setFileira(rs.getString("fileira"));
				i.setColuna(rs.getInt("coluna"));
				i.setEstudante(rs.getBoolean("estudante"));
				i.setNumeroCarteira(rs.getLong("numeroCarteira"));
				i.setFlagVendida(rs.getBoolean("flag"));

				lista.add(i);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return lista;

	}

	@Override
	public boolean checaCadeiraDisponivel(String fileira, long coluna) {
		boolean disponivel = true;
		String sql = "SELECT * FROM INGRESSOS WHERE fileira = ? AND coluna = ?";

		try {

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, fileira);
			ps.setLong(2, coluna);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				disponivel = false;
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
		return disponivel;
	}

	@Override
	public Ingresso buscarIngresso(String fileira, long coluna) {
		Ingresso ingresso = null;
		List<Ingresso> listIngresso = null;
		String sql = "SELECT * FROM INGRESSOS WHERE fileira = ? AND coluna = ?";

		try {

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, fileira);
			ps.setLong(2, coluna);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				listIngresso = new ArrayList<>();
				ingresso = new Ingresso();
				ingresso.setId(rs.getLong("idIngressos"));
				ingresso.setFileira(rs.getString("fileira"));
				ingresso.setColuna(rs.getInt("coluna"));
				ingresso.setNumeroCarteira(rs.getLong("numeroCarteira"));
				ingresso.setEstudante(rs.getBoolean("estudante"));
				ingresso.setFlagVendida(rs.getBoolean("flag"));
				listIngresso.add(ingresso);
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
		return listIngresso != null ? listIngresso.get(0) : null;
	}

}
