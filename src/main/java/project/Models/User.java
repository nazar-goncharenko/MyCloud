package project.Models;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;


    String login;
    String password;

    @ManyToMany
    private  Set <Track> likedLists;

    public User() {
    }
//TODO ADD THIS
    /*
    List<Track> LikedTracks;
    List<Playlist> LikedPlaylist;
    List<Album> LikedAlbum;
     */

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Set<Track> getLikedLists() {
        return likedLists;
    }

    public void setLikedLists(Set<Track> likedLists) {
        this.likedLists = likedLists;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addLikedTrack(Track track)
    {
        this.likedLists.add(track);
    }

    public void deleteLikedTrack(Track track)
    {
        this.likedLists.remove(track);
    }
    public void deleteAllLike()
    {
        this.likedLists.clear();
    }

}
