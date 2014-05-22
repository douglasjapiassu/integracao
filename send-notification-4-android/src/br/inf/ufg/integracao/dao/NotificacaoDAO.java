package br.inf.ufg.integracao.dao;

import java.util.List;

import br.inf.ufg.integracao.model.Notificacao;

/**
 * Contrato de persistência para a entidade <code>Usuario</code>. 
 */
public interface NotificacaoDAO {

	/**
	 * Faz a inserção ou atualização do usuario na base de dados.
	 * @param usuario
	 * @return o id objeto persistido.
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	Long save(Notificacao notificacao);
	
	/**
	 * @return Lista com todos os usuarios cadastrados no banco de dados.
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	List<Notificacao> getAll();
	
	/**
	 * Exclui o registro do usuario na base de dados 
	 * @param usuario
	 * @return true
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	Boolean remove(Notificacao usuario);
	
	/**
	 * @param id filtro da pesquisa.
	 * @return Usuario com filtro no id, caso nao exista retorna <code>null</code>.
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	Notificacao findById(Long id);
}
