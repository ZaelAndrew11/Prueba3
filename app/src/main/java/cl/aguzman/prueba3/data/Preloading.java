package cl.aguzman.prueba3.data;

import android.app.ProgressDialog;
import android.content.Context;

public class Preloading {

    public ProgressDialog progressDialog;

    public void preloadingShow(Context context, String message){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

}
