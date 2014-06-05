package br.ufg.inf.integracao.receivenotification.views;



import br.ufg.inf.integracao.receivenotification.LoginActivity;
import br.ufg.inf.integracao.receivenotification.R;
import br.ufg.inf.integracao.receivenotification.persistencia.DBAdapter;
import br.ufg.inf.integracao.receivenotification.util.UtilGCM;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ViewPrincipal extends Activity {
    
	Button btnRegistrar, btnHistorico;
	DBAdapter dbAdapter;
	
    private boolean isGcmAtivo() {
		return UtilGCM.isRegistrado(this);
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAdapter = new DBAdapter(this);
        loadViewPrincipal();
    }
    
    public void loadViewPrincipal() {
    	setContentView(R.layout.activity_view_principal);
        final Context context = this;
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        UtilGCM.desativa(getApplicationContext());
        if (isGcmAtivo())
        	btnRegistrar.setClickable(false);
    	
    	btnHistorico = (Button) findViewById(R.id.btnHistorico);
    	btnHistorico.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(context, ViewHistoricoNotificacoes.class);
				startActivity(intent);
			}});
        
        
        btnRegistrar.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		Intent intent = new Intent(context, LoginActivity.class);
				startActivity(intent);
        	}});
    }
}