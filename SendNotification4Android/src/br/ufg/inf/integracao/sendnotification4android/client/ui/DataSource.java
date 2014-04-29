package br.ufg.inf.integracao.sendnotification4android.client.ui;

import java.io.Serializable;

/**
 * Define uma estrutura para armazenar e fornecer dados para um componente GUI, normalmente uma <code>Table</code>.
 * 
 * @author YaW Tecnologia
 *
 * @param <T> Tipo da informação mantida no <code>DataSource</code>.
 */
public interface DataSource<T extends Serializable> {

	/**
	 * @return quantidade de linhas do <code>datasource</code>.
	 */
	int getRowCount();
	
	/**
	 * @param index indice do elemento
	 * @return o objeto armazenado de acordo com o indice.
	 */
	T getRow(int index);
	
	/**
	 * Adiciona um novo elemento ao <code>datasource</code>.
	 * @param t
	 */
	void add(T t);
	
	/**
	 * Remove o elemento, caso ele exista, no <code>datasource</code>.
	 * @param t
	 */
	void remove(T t);
	
}
