package br.ufg.inf.integracao.receivenotification.views;


import java.util.Date;
import java.util.List;

import br.ufg.inf.integracao.receivenotification.R;
import br.ufg.inf.integracao.receivenotification.model.Notificacao;
import br.ufg.inf.integracao.receivenotification.persistencia.DBAdapter;
import br.ufg.inf.integracao.receivenotification.util.UtilGCM;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPrincipal extends Activity {
    
	Button btnSalvar, btnCancelar, btnNovoCadastro, btnRegistrar, btnHistorico, btnVoltar;
	EditText txtNome, txtEndereco, txtTelefone;
	TextView tvNotificacoes;
	DBAdapter dbAdapter;
	private boolean gcmAtivo;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAdapter = new DBAdapter(this);
        loadViewPrincipal();
    }
    
    public void loadViewPrincipal() {
    	setContentView(R.layout.activity_view_principal);
        final Context context = this;
    	
    	btnHistorico = (Button) findViewById(R.id.btnHistorico);
    	btnHistorico.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				//carregarNotificacoesSalvas(context);
				Intent intent = new Intent(context, ViewHistoricoNotificacoes.class);
				startActivity(intent);
			}});
        
    	
        btnNovoCadastro = (Button) findViewById(R.id.btnNovo);
        btnNovoCadastro.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				loadViewCadastro();
			}});
        
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		registrarGCM();
        	}});
    }
    
    private void registrarGCM() {
    	if (UtilGCM.isRegistrado(getApplicationContext())) {
            UtilGCM.desativa(getApplicationContext());
            gcmAtivo = false;
            Toast.makeText(getApplicationContext(), "GCM desativado!", Toast.LENGTH_LONG).show();
        } else {
            UtilGCM.registrar(getApplicationContext());
            gcmAtivo = true;
            Toast.makeText(getApplicationContext(), "GCM ativado!", Toast.LENGTH_LONG).show();
        }
    }
    
    public void loadViewCadastro() {
    	setContentView(R.layout.cadastro);
    	
    	//configurando o botao cancelar
        btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				loadViewPrincipal();
			}});
        
        //configurando o formuario de cadastro
        txtNome = (EditText)findViewById(R.id.txtNome);
        txtEndereco = (EditText)findViewById(R.id.txtEndereco);
        txtTelefone = (EditText)findViewById(R.id.txtTelefone);
        
        //configurando o botao salvar
        btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				salvarNotificacao();
			}});
    }
    
    public void salvarNotificacao() {
    	DBAdapter dbAdapter = new DBAdapter(this);
    	
    	dbAdapter.salvarNotificacao(txtTelefone.getText().toString(), new Date());
		loadViewPrincipal();
    }
    
    public void carregarNotificacoesSalvas(Context c) {
    	setContentView(R.layout.historico);
    	
    	btnVoltar = (Button)findViewById(R.id.btnVoltar);
    	btnVoltar.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				loadViewPrincipal();
			}});
        
    	List<Notificacao> listaNotificacoes = dbAdapter.getNotificacoes();
    	
    	for (Notificacao notificacao : listaNotificacoes) {
    		imprimirNotificacao(notificacao);
		}
    }
    
    public void imprimirNotificacao(Notificacao notificacao) {
    	tvNotificacoes = (TextView) findViewById(R.id.listaNotificacoes);
    	
    	if(tvNotificacoes.getText().toString().equalsIgnoreCase("Nenhuma notifica��o cadastrada."))
    		tvNotificacoes.setText("");
    	
    	tvNotificacoes.setText(tvNotificacoes.getText() + "\r\n" + 
    			"Identificador: "+ notificacao.getIdentificador() + "\n" + 
    			"Mensagem: "+ notificacao.getMensagem() + "\n"+ 
    			"Data: "+notificacao.getData_recebimento() +
    			"-------------------------");
    }
}