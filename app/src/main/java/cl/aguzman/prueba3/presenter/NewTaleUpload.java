package cl.aguzman.prueba3.presenter;

public class NewTaleUpload {
    private NewTaleUploadCallback callback;
    private boolean firstTale = true;

    public NewTaleUpload(NewTaleUploadCallback callback) {
        this.callback = callback;
    }

    public void validateUpload(String title, String text, String photoUrl){
        int titleTale = title.trim().length();
        int textTale = text.trim().length();
        if(titleTale > 0 && textTale > 0 && photoUrl != null){
            if(firstTale){
                firstTale = false;
                callback.preloading();
            }
            callback.success();
        }else{
            callback.error();
        }
    }
}
