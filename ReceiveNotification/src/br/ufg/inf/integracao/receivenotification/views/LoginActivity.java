package br.ufg.inf.integracao.receivenotification.views;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import br.ufg.inf.integracao.receivenotification.R;
import br.ufg.inf.integracao.receivenotification.ReceiveNotification;
import br.ufg.inf.integracao.receivenotification.persistencia.DBAdapter;
import br.ufg.inf.integracao.receivenotification.util.UtilGCM;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {

	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private LoginTask mLoginTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mName;

	// UI references.
	private EditText mEmailView;
	private EditText mNameView;
	private View mRegistrarFormView;
	private View mStatusRegistroView;
	private TextView mRegistroStatusMensagemView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mNameView = (EditText) findViewById(R.id.name);
		mNameView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mRegistrarFormView = findViewById(R.id.login_form);
		mStatusRegistroView = findViewById(R.id.login_status);
		mRegistroStatusMensagemView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mLoginTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mNameView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mName = mNameView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mName)) {
			mNameView.setError(getString(R.string.erro_campo_obrigatorio));
			focusView = mNameView;
			cancel = true;
		} else if (mName.length() < 4) {
			mNameView.setError(getString(R.string.erro_nome_invalido));
			focusView = mNameView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.erro_campo_obrigatorio));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			focusView.requestFocus();
		} else {
			mRegistroStatusMensagemView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mLoginTask = new LoginTask();
			mLoginTask.execute(mName, mEmail);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mStatusRegistroView.setVisibility(View.VISIBLE);
			mStatusRegistroView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mStatusRegistroView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mRegistrarFormView.setVisibility(View.VISIBLE);
			mRegistrarFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mRegistrarFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mStatusRegistroView.setVisibility(show ? View.VISIBLE : View.GONE);
			mRegistrarFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	public class LoginTask extends AsyncTask<String, Void, Void> {
		
		private final String URL = "http://1.send-notification-4-android.appspot.com/sendID";
		private HttpClient httpClient; 
		private HttpResponse httpResponse;
	 
	    @Override
	    protected Void doInBackground(String... params) {
	    	DBAdapter dbAdapter = new DBAdapter(getApplicationContext());
	    	dbAdapter.salvarUsuario(params[0], params[1]);
	    	registrar();
	        
	        return null;
	    }
	    
	    private void registrar() {
	    	String senderID = "";
	    	try {
	    		 
	            httpClient = new DefaultHttpClient();
	           
	            HttpGet requisicao = new HttpGet();
	            requisicao.setHeader("Content-Type",
	                    "text/plain; charset=utf-8");
	            requisicao.setURI(new URI(URL));
	            
	            httpResponse = httpClient.execute(requisicao);
	            
	            BufferedReader br = new BufferedReader(new InputStreamReader(
	                    httpResponse.getEntity().getContent()));
	            StringBuffer sb = new StringBuffer("");

	            while ((senderID = br.readLine()) != null) {
	                sb.append(senderID);
	            }

	            br.close();
	            
	            senderID = sb.toString();
	            
	            if (senderID == "0") {
	            	showProgress(false);
	            	mRegistroStatusMensagemView.setText("Erro ao receber a configuração do servidor. Verifique com o administrador.");
	            } else {
	            	UtilGCM.registrar(ReceiveNotification.getAppContext(), senderID);
	            }

	        } catch (Exception e) {
	            System.out.println(e);
	            System.out.println(e.getMessage());
	        }
	    }
	    
		@Override
		protected void onPostExecute(Void result) {
			mLoginTask = null;
			showProgress(false);
			finish();
		}
		
		@Override
		protected void onCancelled() {
			mLoginTask = null;
			showProgress(false);
		}
	}
}
