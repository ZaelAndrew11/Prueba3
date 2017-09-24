package cl.aguzman.prueba3.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

import cl.aguzman.prueba3.R;
import cl.aguzman.prueba3.data.Nodes;
import cl.aguzman.prueba3.data.Tale;
import cl.aguzman.prueba3.presenter.Favorites;
import cl.aguzman.prueba3.presenter.FavoritesCallback;

public class ContentActivity extends AppCompatActivity implements FavoritesCallback {

    DatabaseReference databaseRefFav = new Nodes().favoritesRef();
    DatabaseReference databaseReference = new Nodes().tales();
    Button addfavBtn;
    String uid;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        final Favorites callback = new Favorites(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final CollapsingToolbarLayout taleTitle = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        final ImageView taleImage = (ImageView) findViewById(R.id.imageTaleContent);
        final TextView taleText = (TextView) findViewById(R.id.taleTextContent);
        final TextView taleAuthor = (TextView) findViewById(R.id.taleTextAuthor);
        addfavBtn = (Button) findViewById(R.id.addFavBtn);
        setSupportActionBar(toolbar);
        uid = getIntent().getStringExtra("uid");
        key = getIntent().getStringExtra("key");
        DatabaseReference dataContent = databaseReference.child(uid).child(key);
        dataContent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tale tale = dataSnapshot.getValue(Tale.class);
                Picasso.with(taleImage.getContext()).load(tale.getImage()).into(taleImage);
                taleTitle.setTitle(tale.getTitle());
                taleAuthor.setText(tale.getAuthor());
                taleText.setText(tale.getTale());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        verificationfav();

        //Añadir a favoritos
        addfavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textBtn = addfavBtn.getText().toString();
                callback.updatefav(key, textBtn);
            }
        });

    }

    //menu setting
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, PerfilActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
    //menu setting

    @Override
    public void addFavorites() {
        databaseRefFav.child(key).setValue(true);
        addfavBtn.setText("Remover de favoritos");
        addfavBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_favorite_white_24dp, 0);
    }

    @Override
    public void removeFavorites() {
        databaseRefFav.child(key).removeValue();
        addfavBtn.setText("Añadir a Favoritos");
        addfavBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_favorite_border_white_24dp, 0);
    }

    @Override
    public void verificationfav() {
        DatabaseReference dbtalesCheckFav = new Nodes().favoritesRef();
        dbtalesCheckFav.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> talefav = (Map<String, Object>) dataSnapshot.getValue();
                if (talefav == null || !talefav.containsKey(key)){
                    addfavBtn.setText("Añadir a Favoritos");
                    addfavBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_favorite_border_white_24dp, 0);
                } else {
                    addfavBtn.setText("Remover de favoritos");
                    addfavBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_favorite_white_24dp, 0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
