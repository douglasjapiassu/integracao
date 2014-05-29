package br.ufg.inf.integracao.receivenotification;

import br.ufg.inf.integracao.receivenotification.util.Consulta;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private Consulta mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mNome;
	private String mEmail;

	// UI references.
	private EditText mNomeView;
	private EditText mEmailView;
	private View mRegistrarFormView;
	private View mStatusRegistroView;
	private TextView mRegistroStatusMensagemView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		// Set up the login form.
		mNome = getIntent().getStringExtra(EXTRA_EMAIL);
		mNomeView = (EditText) findViewById(R.id.email);
		mNomeView.setText(mNome);

		mEmailView = (EditText) findViewById(R.id.password);
		mEmailView
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mNomeView.setError(null);
		mEmailView.setError(null);

		// Store values at the time of the login attempt.
		mNome = mNomeView.getText().toString();
		mEmail = mEmailView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.erro_campo_obrigatorio));
			focusView = mEmailView;
			cancel = true;
		} else if (mEmail.length() < 4) {
			mEmailView.setError(getString(R.string.erro_campo_obrigatorio));
			focusView = mEmailView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mNome)) {
			mNomeView.setError(getString(R.string.erro_campo_obrigatorio));
			focusView = mNomeView;
			cancel = true;
		} else if (!mNome.contains("@")) {
			mNomeView.setError(getString(R.string.error_invalid_email));
			focusView = mNomeView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mRegistroStatusMensagemView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new Consulta();
			mAuthTask.execute((String) null);
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

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}

			for (String credential : DUMMY_CREDENTIALS) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(mNome)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(mEmail);
				}
			}

			// TODO: register the new account here.
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				finish();
			} else {
				mEmailView
						.setError(getString(R.string.error_incorrect_password));
				mEmailView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
