package br.inf.ufg.integracao.dao;

import java.util.List;

import br.inf.ufg.integracao.model.Notificacao;

/**
 * Contrato de persistência para a entidade <code>Notificacao</code>. 
 */
public interface NotificacaoDAO {

	/**
	 * Faz a inserção ou atualização da notificação na base de dados.
	 * @param notificacao
	 * @return o id objeto persistido.
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	Long save(Notificacao notificacao);
	
	/**
	 * @return Lista com todas as notificações cadastradas no banco de dados.
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	List<Notificacao> getAll();
	
	/**
	 * Exclui o registro da notificação
	 * @param usuario
	 * @return true
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	Boolean remove(Notificacao notificacao);
	
	/**
	 * @param id filtro da pesquisa.
	 * @return Notificacao com filtro no id, caso nao exista retorna <code>null</code>.
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	Notificacao findById(Long id);
}
