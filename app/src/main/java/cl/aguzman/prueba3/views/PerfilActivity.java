package cl.aguzman.prueba3.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import cl.aguzman.prueba3.R;
import cl.aguzman.prueba3.data.CurrentUser;
import cl.aguzman.prueba3.data.Preloading;
import cl.aguzman.prueba3.views.login.LoginActivity;

public class PerfilActivity extends AppCompatActivity {

    private Preloading preloading = new Preloading();
    FirebaseUser currentUser = new CurrentUser().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        getSupportActionBar().setTitle(currentUser.getDisplayName());

        CircularImageView userPicture = (CircularImageView) findViewById(R.id.avatarIv);
        TextView userName = (TextView) findViewById(R.id.userNameTv);
        TextView userEmail = (TextView) findViewById(R.id.userEmailTv);
        Button logout = (Button) findViewById(R.id.logoutUser);

        userName.setText(currentUser.getDisplayName());
        userEmail.setText(currentUser.getEmail());
        Uri userPhoto = currentUser.getPhotoUrl();
        if(userPhoto != null){
            Picasso.with(this).load(userPhoto).into(userPicture);
        }else{
            userPicture.setBackground(ContextCompat.getDrawable(this, R.mipmap.ic_account_circle_black_24dp));
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preloading.preloadingShow(PerfilActivity.this, getString(R.string.exit));
                AuthUI.getInstance().signOut(PerfilActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(PerfilActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        finishAffinity();
                        preloading.progressDialog.dismiss();
                    }
                });
            }
        });
    }
}
