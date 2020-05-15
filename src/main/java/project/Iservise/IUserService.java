package project.Iservise;

import project.models.Track;

public interface IUserService {

    void playlistAdd(Long ID, String Name);

    void playlistRemove(Long ID, String Name);

    boolean register(String login,String password);

    Iterable<Track> getPlaylist(String Name);
}
