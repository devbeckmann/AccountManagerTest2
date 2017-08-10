package cortado.com.accountmanagertest;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
	private static final String TAG = "MainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button createAccount = (Button) findViewById(R.id.b_create_account);
		Button getAuthToken = (Button) findViewById(R.id.b_get_auth_token);
		
		createAccount.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new CreateAccountTask().execute();
			}
		});
		
		getAuthToken.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new GetAuthToken().execute();
			}
		});
	}
	
	private void handleResultCreateAccount(Bundle bundle) {
		if (bundle != null) {
			Toast.makeText(this, "Account with name " + bundle.get(AccountManager.KEY_ACCOUNT_NAME) + " successfully created !", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "Could not create account. Is DISALLOW_MODIFY_ACCOUNT set ?", Toast.LENGTH_LONG).show();
		}
	}
	
	private void handleResultGetAccountToken(Bundle bundle) {
		if (bundle != null) {
			Toast.makeText(this, "Token for Account " + bundle.getString(AccountManager.KEY_ACCOUNT_NAME) + " received. Token value: " + bundle.getString(AccountManager.KEY_AUTHTOKEN), Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "Could not get auth token. Account already created ?", Toast.LENGTH_LONG).show();
		}
	}
	
	private class CreateAccountTask extends AsyncTask<Void, Void, Bundle> {
		@Override
		protected Bundle doInBackground(Void... params)
		{
			Log.d(TAG, "create account");
			
			AccountManager am = AccountManager.get(getApplicationContext());
			AccountManagerFuture<Bundle> future = am.addAccount(CustomAccountAuthenticator.ACCOUNT_TYPE, CustomAccountAuthenticator.TOKEN_TYPE, null, null, null, null, null);
			
			try
			{
				Bundle bundle = future.getResult();
				return bundle;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
						
			return null;
		}
		
		@Override
		protected void onPostExecute(Bundle bundle)
		{
			handleResultCreateAccount(bundle);
		}
	}
	
	private class GetAuthToken extends AsyncTask<Void, Void, Bundle> {
		@Override
		protected Bundle doInBackground(Void... params)
		{
			Log.d(TAG, "get auth token");
			
			AccountManager am = AccountManager.get(getApplicationContext());
			
			Account[] accounts = am.getAccountsByType(CustomAccountAuthenticator.ACCOUNT_TYPE);
			
			if (accounts.length == 0) {
				return null;
			}
			
			AccountManagerFuture<Bundle> future = am.getAuthToken(accounts[0], CustomAccountAuthenticator.TOKEN_TYPE, null, false, null, null);
			
			try
			{
				Bundle bundle = future.getResult();
				String name = bundle.getString(AccountManager.KEY_ACCOUNT_NAME);
				String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
				
				Log.d(TAG, "name: " + name + " token: " + token);
				
				return bundle;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Bundle bundle)
		{
			handleResultGetAccountToken(bundle);
		}
	}
}
