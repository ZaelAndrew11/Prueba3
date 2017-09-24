package cl.aguzman.prueba3.data;

public class Tale {
    String author, tale, title, image, key, uid;
    int score;
    Boolean publish;

    public Tale() {
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getTale() {
        return tale;
    }
    public void setTale(String tale) {
        this.tale = tale;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public Boolean getPublish() {
        return publish;
    }
    public void setPublish(Boolean publish) {
        this.publish = publish;
    }
}
