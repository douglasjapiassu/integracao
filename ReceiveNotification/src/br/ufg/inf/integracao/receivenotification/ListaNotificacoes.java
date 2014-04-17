package br.ufg.inf.integracao.receivenotification;


import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.integracao.receivenotification.model.Notificacao;
import br.ufg.inf.integracao.receivenotification.persistencia.DbAdapter;
import android.app.ListActivity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListaNotificacoes extends ListActivity {
    
	DbAdapter dbAdapter;
	Point point;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAdapter = new DbAdapter(this);
        List<String> listaNotificacoes = new ArrayList<String>();
        
        for (Notificacao notificacao : dbAdapter.getNotificacoes()) {
        	listaNotificacoes.add(notificacao.toString());
		}
        
        
        setListAdapter(new ArrayAdapter<String>(this, R.layout.listanotificacoes, listaNotificacoes));
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	// TODO Auto-generated method stub
    	super.onListItemClick(l, v, position, id);
    	CharSequence texto = ((TextView) v).getText();
    	Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }
}