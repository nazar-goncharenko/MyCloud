package project.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.Iservise.IUserService;
import project.models.Track;
import project.models.User;
import project.repositories.TrackRepo;
import project.repositories.UsersRepo;

@Service
public class UserService implements IUserService {

    @Autowired
    private TrackRepo trackRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Value("${upload.path}")
    private String uploadpath;


    public boolean register(String login,String password)
    {
        if ( usersRepo.findByLoginIs(login) != null && password != null)
        {
            return false;
        }
        usersRepo.save(new User(login,password));
        return true;
    }


    public void playlistAdd(Long ID, String Name)
    {
        User curUser = usersRepo.findByLoginIs(Name);
        Track track = trackRepo.findById(ID).get();
        curUser.addLikedTrack(track);
        usersRepo.save(curUser);
    }

    public void playlistRemove(Long ID, String Name)
    {
        User curUser = usersRepo.findByLoginIs(Name);
        Track track = trackRepo.findById(ID).get();
        curUser.getLikedLists().remove(track);
        usersRepo.save(curUser);
    }

    public Iterable<Track> getPlaylist(String Name)
    {
        User curUser = usersRepo.findByLoginIs(Name);
        Iterable<Track> likedLists = curUser.getLikedLists();
        for (Track track: likedLists)
        {
            track.setLikes(true);
        }
        return likedLists;
    }
}
