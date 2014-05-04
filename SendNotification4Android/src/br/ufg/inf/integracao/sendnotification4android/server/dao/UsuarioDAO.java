package br.ufg.inf.integracao.sendnotification4android.server.dao;

import java.util.List;

import br.ufg.inf.integracao.sendnotification4android.server.model.UsuarioEntity;

/**
 * Contrato de persistência para a entidade <code>Mercadoria</code>. 
 * 
 * <p>Define as operações basicas de cadastro (CRUD), seguindo o design pattern <code>Data Access Object</code>.</p>
 * 
 * @author YaW Tecnologia
 */
public interface UsuarioDAO {

	/**
	 * Faz a inserção ou atualização da mercadoria na base de dados.
	 * @param me
	 * @return referência atualizada do objeto.
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	UsuarioEntity save(UsuarioEntity ue);
	
	/**
	 * Exclui o registro da mercadoria na base de dados 
	 * @param id chave da pesquisa.
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	void remove(Long id);
	
	/**
	 * @return Lista com todas as mercadorias cadastradas no banco de dados.
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	List<UsuarioEntity> getAll();
	
	/**
	 * @param id chave da pesquisa.
	 * @return Mercadoria com filtro no id, caso nao exista retorna <code>null</code>.
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	UsuarioEntity findById(Long id);
	
}