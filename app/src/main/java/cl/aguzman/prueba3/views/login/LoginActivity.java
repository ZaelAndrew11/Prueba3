package cl.aguzman.prueba3.views.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;

import java.util.Arrays;

import cl.aguzman.prueba3.R;
import cl.aguzman.prueba3.presenter.ValidateLogin;
import cl.aguzman.prueba3.presenter.ValidateLoginCallback;
import cl.aguzman.prueba3.views.main.MainActivity;

public class LoginActivity extends AppCompatActivity implements ValidateLoginCallback{
    private static final int RC_SIGN_IN = 3008;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new ValidateLogin(this).loginValidate();
    }

    public void signUp() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setProviders(Arrays.asList(
                                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()/*,
                                new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()*/)
                        ).setIsSmartLockEnabled(false)
                        .setTheme(R.style.LoginTheme)
                        .setLogo(R.mipmap.logo)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RC_SIGN_IN == requestCode){
            if(ResultCodes.OK == resultCode){
                logged();
            }
        }
    }

    public void logged(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
