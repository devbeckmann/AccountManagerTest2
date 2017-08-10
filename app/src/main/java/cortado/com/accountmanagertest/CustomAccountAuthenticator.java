package cortado.com.accountmanagertest;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by daniel on 10.08.17.
 */

public class CustomAccountAuthenticator extends AbstractAccountAuthenticator
{
	public static final String ACCOUNT_TYPE = "com.cortado.account.test";
	public static final String TOKEN_TYPE = "token.type.test";
	
	private Context mContext;
	
	public CustomAccountAuthenticator(Context context)
	{
		super(context);
		mContext = context;
	}
	
	@Override
	public Bundle editProperties(AccountAuthenticatorResponse response, String accountType)
	{
		return null;
	}
	
	@Override
	public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException
	{
		AccountManager manager = AccountManager.get(mContext);
		Account account = new Account("MyAccount", ACCOUNT_TYPE);
		
		manager.addAccountExplicitly(account, null, options);
		
		Bundle result = new Bundle();
		result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
		result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
		
		return result;
	}
	
	@Override
	public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException
	{
		return null;
	}
	
	@Override
	public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException
	{
		Bundle result = new Bundle();
		result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
		result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
		result.putString(AccountManager.KEY_AUTHTOKEN, "someverysafetoken");
		
		return result;
	}
	
	@Override
	public String getAuthTokenLabel(String authTokenType)
	{
		return null;
	}
	
	@Override
	public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException
	{
		return null;
	}
	
	@Override
	public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException
	{
		return null;
	}
}
