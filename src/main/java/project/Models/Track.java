package project.Models;


import javax.persistence.*;

@Entity
@Table(name = "tracks")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String name;

    String url;

    Long rating;

    boolean isLikes;



    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Track() {
    }

    public Track(String name, String url) {
        this.name = name;
        this.url = url;
        this.rating = 1l;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addRating()
    {
        this.rating++;
    }

    public void remRating(){ this.rating--;}

    public boolean isLikes() {
        return isLikes;
    }

    public void setLikes(boolean likes) {
        isLikes = likes;
    }
}
