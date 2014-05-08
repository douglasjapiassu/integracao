package br.ufg.inf.integracao.sendnotification4android.client.ui;

import br.ufg.inf.integracao.sendnotification4android.client.model.Usuario;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * Lista de Usu�rios
 * 
 * <p>As colunas:</p>
 * <ul>
 *   <li>ID;</li>
 *   <li>Nome;</li>
 *   <li>Email;</li>
 * </ul>
 * 
 * @author douglasjapiassu
 */
public class UsuarioTable extends FlexTable {
	
	/**
	 * <code>DataSource</code> para manter os dados da tabela.
	 */
    private DataSource<Usuario> source;
    
    /**
     * Indice da linha selecionada.
     */
    private int selectedRow;

	public UsuarioTable() {
		setText(0, 0, "ID");
		setText(0, 1, "Nome");
		setText(0, 2, "Email");
		setCellPadding(6);
		
		getRowFormatter().addStyleName(0, "listHeader");
		addStyleName("list");
		getCellFormatter().addStyleName(0, 1, "listTextColumn");
		getCellFormatter().addStyleName(0, 2, "listTextColumn");
		
		addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Cell td = getCellForEvent(event);
				if (td == null) return;
		        
		        boolean sameRow = selectedRow == td.getRowIndex();
		        changeRow(selectedRow, false);
		        selectedRow = td.getRowIndex();
		        changeRow(selectedRow, !sameRow);
		       	if (sameRow) selectedRow = 0;
			}
		});
	}	
	
	/**
	 * Preenche a tabela com os usuários cadastrados.
	 * @param mercadorias
	 */
	public void fillTable(Usuario[] mercadorias) {
		source = new UsuarioDataSource(mercadorias);
		atualizarTabela();
	}
	
	/**
	 * Recarrega todas as linhas na tabela.
	 */
	private void atualizarTabela() {
		for (int i = this.getRowCount()-1; i > 0; i--){
            this.removeRow(1);
        }
		
		int rows = source.getRowCount();
        for(int i = 0 ; i < rows; i++ ){
            Usuario m = source.getRow(i);
            insertRow(i+1, m);
        }
        this.selectedRow = 0;
	}

	/**
	 * Insere celulas com as informações de uma <code>Mercadoria</code> na tabela.
	 * @param row
	 * @param m
	 */
	private void insertRow(final int row, Usuario m) {
		setText(row, 0, m.getId().toString());
		setText(row, 1, m.getNome());
		setText(row, 2, m.getEmail());
	}
	
	/**
	 * Marca a seleção da linha na tabela.
	 * @param row
	 * @param selected
	 */
	private void changeRow(int row, boolean selected) {
		if (row > 0) {
			Element tr = getRowFormatter().getElement(row);
			if (tr != null)
				DOM.setStyleAttribute(tr, "backgroundColor", selected ? "#ffce00" : "#ffffff");
		}
	}
	
	/**
	 * @return Usuario selecionado na tabela.
	 */
	public Usuario getUsuarioSelecionado() {
		if (source == null || selectedRow == 0)
			return null;
		
		return source.getRow(selectedRow-1);
	}
	
	public void adicionar(Usuario usuario) {
		source.add(usuario);
		atualizarTabela();
	}
	
	public void remover(Usuario usuario) {
		source.remove(usuario);
		atualizarTabela();
	}
}
