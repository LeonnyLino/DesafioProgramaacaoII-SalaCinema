package br.com.edr.cinema.interfaces;

import java.util.List;

import br.com.edr.cinema.entidades.Ingresso;

public interface IIngressoIpl {
	
	boolean insert(Ingresso ingresso);
	
	boolean insertOrUpdate(Ingresso ingresso);
	
	boolean update(Ingresso ingresso, long id);
	
	boolean delete(long id);
	
	List<Ingresso> getIngressos();
	
	boolean checaCadeiraDisponivel(String fileira, long coluna);
	
	Ingresso buscarIngresso(String fileira, long coluna);

}
