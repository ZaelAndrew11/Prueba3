package cl.aguzman.prueba3.presenter;

public class Favorites {
    private FavoritesCallback favoritesCallback;

    public Favorites(FavoritesCallback favoritesCallback) {
        this.favoritesCallback = favoritesCallback;
    }

    public void updatefav(String key, String textBtn){
        if(textBtn == "Añadir a Favoritos"){
            favoritesCallback.addFavorites();
        }else {
            favoritesCallback.removeFavorites();
        }
    }


}
