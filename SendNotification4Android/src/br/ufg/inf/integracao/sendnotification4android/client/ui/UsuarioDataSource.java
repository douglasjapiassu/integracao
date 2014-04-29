package br.ufg.inf.integracao.sendnotification4android.client.ui;


import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.integracao.sendnotification4android.client.model.Usuario;

/**
 * Implementa um <code>datasource</code> para <code>Mercadoria</code>.
 * 
 * @author YaW Tecnologia
 */
public class UsuarioDataSource implements DataSource<Usuario> {	
	
    private List<Usuario> data = new ArrayList<Usuario>();
    
    public UsuarioDataSource(Usuario[] mercadorias) {
    	if (mercadorias != null) {
    		for (Usuario m: mercadorias) {
            	data.add(m);
            }
    	}
    }

    public int getRowCount() {
        return data.size();
    }

    public Usuario getRow(int i) {
        return data.get(i);
    }
    
    public void add(Usuario m) {
    	if (m == null) return;
    	
    	if (data.contains(m)) {
    		int i = data.indexOf(m);
    		data.set(i, m);
    	} else {
    		data.add(m);
    	}
    }
    
    public void remove(Usuario m) {
    	if (m == null || m.getId() == null) return;
    	
    	for (int i = 0; i < data.size(); i++) {
    		if (m.getId().equals(data.get(i).getId())) {
    			data.remove(i);
    			break;
    		}
    	}
    }
	
}
