package br.ufg.inf.integracao.receivenotification;


import java.util.List;

import br.ufg.inf.integracao.receivenotification.model.Notificacao;
import br.ufg.inf.integracao.receivenotification.persistencia.DbAdapter;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaNotificacoes extends ListActivity {
    
	DbAdapter dbAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAdapter = new DbAdapter(this);
        List<Notificacao> listaNotificacoes = dbAdapter.getNotificacoes();
        setListAdapter(new ArrayAdapter<Notificacao>(this, R.layout.listanotificacoes, listaNotificacoes));
    }
}