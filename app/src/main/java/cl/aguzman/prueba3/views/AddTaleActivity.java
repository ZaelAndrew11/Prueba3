package cl.aguzman.prueba3.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import cl.aguzman.prueba3.R;
import cl.aguzman.prueba3.data.CurrentUser;
import cl.aguzman.prueba3.data.Preloading;
import cl.aguzman.prueba3.data.Tale;
import cl.aguzman.prueba3.presenter.NewTaleUpload;
import cl.aguzman.prueba3.presenter.NewTaleUploadCallback;
import cl.aguzman.prueba3.views.main.MainActivity;

public class AddTaleActivity extends AppCompatActivity implements NewTaleUploadCallback {
    private Preloading preloading = new Preloading();
    private static final int PICK_IMAGE = 0;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = db.getReference();
    FirebaseUser name = new CurrentUser().getCurrentUser();
    String uid = new CurrentUser().getCurrentUid();
    String urlPickPath;
    String urlImageDownload;
    EditText title;
    EditText tale;
    ImageView imagePreview;
    Button selectImageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tale);

        getSupportActionBar().setTitle(R.string.action_bar_new_tale);
        title = (EditText) findViewById(R.id.titleTalesEt);
        tale = (EditText) findViewById(R.id.descriptionTaleEt);
        imagePreview = (ImageView) findViewById(R.id.imagePreviewIv);
        selectImageBtn = (Button) findViewById(R.id.selectImageBtn);
        Button saveTale = (Button) findViewById(R.id.saveTaleBtn);

        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(intent.EXTRA_LOCAL_ONLY, false);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        saveTale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NewTaleUpload(AddTaleActivity.this).validateUpload(title.getText().toString(), tale.getText().toString(), urlPickPath);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri urlPickImage = data.getData();
            urlPickPath = data.getData().toString();
            imagePreview.setVisibility(View.VISIBLE);
            Picasso.with(this).load(urlPickImage).into(imagePreview);
        }
    }

    public void uploadImage(String path) {
        Long tsLong = System.currentTimeMillis() / 1000;
        final String ts = tsLong.toString();
        String url = "gs://prueba3-2fa91.appspot.com/tales/" + uid + "/talePhoto-" + ts + ".jpg";
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(url);

        storageReference.putFile(Uri.parse(path)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") String url = taskSnapshot.getDownloadUrl().toString();
                url = url.split("&token=")[0];
                urlImageDownload = url;
                String key = dbRef.child("tales").child(uid.toString()).push().getKey();
                Map<String, Object> map = new HashMap<String, Object>();

                Tale newTale = new Tale();
                Tale minTale = new  Tale();

                minTale.setTitle(String.valueOf(title.getText()));
                minTale.setImage(urlImageDownload);
                minTale.setAuthor(name.getDisplayName());
                minTale.setKey(key);
                minTale.setPublish(true);
                minTale.setUid(uid);

                newTale.setAuthor(name.getDisplayName());
                newTale.setTale(String.valueOf(tale.getText()));
                newTale.setTitle(String.valueOf(title.getText()));
                newTale.setImage(urlImageDownload);
                newTale.setKey(key);
                newTale.setScore(0);
                newTale.setPublish(true);

                map.put("allTales/"+key, minTale);
                map.put("tales/"+uid.toString()+"/"+key, newTale);
                dbRef.updateChildren(map);

                preloading.progressDialog.dismiss();
                Intent intent = new Intent(AddTaleActivity.this, MainActivity.class);
                startActivity(intent);
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
    public void success() {
        uploadImage(urlPickPath);
    }

    @Override
    public void error() {
        Toast.makeText(this, "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void preloading() {
        preloading.preloadingShow(this, getString(R.string.preload_message_new));
    }
}
