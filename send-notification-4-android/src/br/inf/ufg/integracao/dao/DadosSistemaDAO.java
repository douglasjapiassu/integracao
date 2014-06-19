package br.inf.ufg.integracao.dao;

import java.util.List;

import br.inf.ufg.integracao.model.DadosSistema;

/**
 * Contrato de persistência para a entidade <code>Usuario</code>. 
 */
public interface DadosSistemaDAO {

	/**
	 * Insere os Dados do Sistema
	 * @param dadosSistema
	 * @return o id objeto persistido.
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	Long save(DadosSistema dadosSistema);
	
	/**
	 * @return Lista com todos os dados cadastrados no banco de dados.
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	List<DadosSistema> getAll();
	
	/**
	 * Exclui o registro do usuario na base de dados 
	 * @param usuario
	 * @return true
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	Boolean remove(DadosSistema dadosSistema);
	
	/**
	 * @param id filtro da pesquisa.
	 * @return Usuario com filtro no id, caso nao exista retorna <code>null</code>.
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	DadosSistema findById(Long id);
}
