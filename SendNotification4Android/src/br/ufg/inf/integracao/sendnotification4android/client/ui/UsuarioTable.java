package br.ufg.inf.integracao.sendnotification4android.client.ui;

import br.ufg.inf.integracao.sendnotification4android.client.model.Usuario;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * Especialização de <code>FlexTable</code>, adaptado para representar uma lista de <code>Mercadoria</code>.
 * 
 * <p>As colunas:</p>
 * <ul>
 *   <li>ID;</li>
 *   <li>Nome;</li>
 *   <li>Descrição;</li>
 *   <li>Quantidade;</li>
 *   <li>Preço;</li>
 * </ul>
 * 
 * @author YaW Tecnologia
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
		setText(0, 2, "Descrição");
		setText(0, 3, "Quantidade");
		setText(0, 4, "Preço");
		setCellPadding(6);
		
		getRowFormatter().addStyleName(0, "listHeader");
		addStyleName("list");
		getCellFormatter().addStyleName(0, 1, "listTextColumn");
		getCellFormatter().addStyleName(0, 2, "listTextColumn");
		getCellFormatter().addStyleName(0, 3, "listNumericColumn");
		getCellFormatter().addStyleName(0, 4, "listNumericColumn");
		
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
	 * Preenche a tabela com as mercadorias informadas. Caso exista alguma linha na tabela, será removida.
	 * @param mercadorias
	 */
	public void fillTable(Usuario[] mercadorias) {
		source = new UsuarioDataSource(mercadorias);
		updateAll();
	}
	
	/**
	 * Recarrega todas as linhas na tabela.
	 */
	private void updateAll() {
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
		setText(row, 2, m.getDescricao());
		setText(row, 3, m.getQuantidade().toString());
		setText(row, 4, Usuario.convertPrecoToString(m.getPreco()));
		
		getCellFormatter().addStyleName(row, 3, "listNumericColumn");
		getCellFormatter().addStyleName(row, 4, "listNumericColumn");
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
	 * @return <code>Mercadoria</code> selecionada pelo usuário na tabela.
	 */
	public Usuario getMercadoriaSelected() {
		if (source == null || selectedRow == 0)
			return null;
		
		return source.getRow(selectedRow-1);
	}
	
	/**
	 * Adiciona uma <code>Mercadoria</code> na tabela.
	 * @param m
	 */
	public void add(Usuario m) {
		source.add(m);
		updateAll();
	}
	
	/**
	 * Remove um <code>Mercadoria</code> da tabela.
	 * @param m
	 */
	public void remove(Usuario m) {
		source.remove(m);
		updateAll();
	}
}
