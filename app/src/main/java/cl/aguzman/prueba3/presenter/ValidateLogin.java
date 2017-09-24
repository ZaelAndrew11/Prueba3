package cl.aguzman.prueba3.presenter;

import cl.aguzman.prueba3.data.CurrentUser;

public class ValidateLogin {
    private ValidateLoginCallback callback;

    public ValidateLogin(ValidateLoginCallback callback) {
        this.callback = callback;
    }

    public void loginValidate(){
        if (new CurrentUser().getCurrentUser() != null){
            callback.logged();
        }else{
            callback.signUp();
        }
    }
}
