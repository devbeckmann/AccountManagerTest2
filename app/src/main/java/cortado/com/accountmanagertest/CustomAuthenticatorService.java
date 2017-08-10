package cortado.com.accountmanagertest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by daniel on 10.08.17.
 */

public class CustomAuthenticatorService extends Service
{
	@Nullable
	@Override
	public IBinder onBind(Intent intent)
	{
		CustomAccountAuthenticator authenticator = new CustomAccountAuthenticator(this);
		return authenticator.getIBinder();
	}
}
