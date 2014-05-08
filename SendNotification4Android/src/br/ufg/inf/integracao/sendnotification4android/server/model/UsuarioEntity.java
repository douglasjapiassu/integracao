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
 * <p>Essa entidade é mapeada com anotações da especificação JDO (<i>Java Data Objects</i>), o mecanismo de persistência utilizado na aplicação.</p>
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class UsuarioEntity {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String nome;
	
	@Persistent
	private String email;
	
	@Persistent
	private Integer quantidade;
	
	@Persistent
	private Double preco;
	
	public UsuarioEntity() {}
	
	public UsuarioEntity(Usuario m) {
		if (m != null) {
			this.id = m.getId();
			this.nome = m.getNome();
			this.email = m.getEmail();
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String descricao) {
		this.email = descricao;
	}

	public Long getId() {
		return id;
	}
	
	protected Usuario toUsuario() {
		Usuario usuario = new Usuario();
		
		usuario.setId(this.id);
		usuario.setNome(this.nome);
		usuario.setEmail(this.email);
		
		return usuario;
	}
	
	public static Usuario[] toMercadoriaArray(List<UsuarioEntity> usuarios) {
		Usuario[] data = new Usuario[usuarios.size()];
		for (int i = 0; i < usuarios.size(); i++) {
			UsuarioEntity m = usuarios.get(i);
			if (m != null) {
				data[i] = m.toUsuario();
			}
		}
		return data;
	}
	
}
