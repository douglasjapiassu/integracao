package br.ufg.inf.integracao.sendnotification4android.server.model;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import br.ufg.inf.integracao.sendnotification4android.client.model.Usuario;

/**
 * Classe de modelo que representa uma mercadoria. A mercadoria é um objeto persistido, por isso utilizamos o nome entidade.
 * 
 * <p>As funcionalidades desse sistema demonstração são concentradas no cadastro (CRUD) de mercadorias.</p>
 * <p>
 * Nessa classe são definidas operações para assumir os dados de uma <code>Mercadoria</code> em um uma <code>MercadoriaEntity</code>.
 * Também é possível recuperar os dados de uma lista de <code>MercadoriaEntity</code> e criar um array de <code>Mercadoria</code> com esses dados.
 * </p>
 * 
 * <p>Essa entidade é mapeada com anotações da especificação JDO (<i>Java Data Objects</i>), o mecanismo de persistência utilizado na aplicação.</p>
 * 
 * @author YaW Tecnologia
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class MercadoriaEntity {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String nome;
	
	@Persistent
	private String descricao;
	
	@Persistent
	private Integer quantidade;
	
	@Persistent
	private Double preco;
	
	public MercadoriaEntity() {}
	
	public MercadoriaEntity(Usuario m) {
		if (m != null) {
			this.id = m.getId();
			this.nome = m.getNome();
			this.descricao = m.getDescricao();
			this.quantidade = m.getQuantidade();
			this.preco = m.getPreco();
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}
	
	public Double getPreco() {
		return preco;
	}
	
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	protected Usuario toMercadoria() {
		Usuario m = new Usuario();
		m.setId(this.id);
		m.setNome(this.nome);
		m.setDescricao(this.descricao);
		m.setPreco(this.preco);
		m.setQuantidade(this.quantidade);
		return m;
	}
	
	public static Usuario[] toMercadoriaArray(List<MercadoriaEntity> mercadorias) {
		Usuario[] data = new Usuario[mercadorias.size()];
		for (int i = 0; i < mercadorias.size(); i++) {
			MercadoriaEntity m = mercadorias.get(i);
			if (m != null) {
				data[i] = m.toMercadoria();
			}
		}
		return data;
	}
	
}
