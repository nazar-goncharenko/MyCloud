package project.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import project.Models.Track;
import project.Models.User;
import project.Repositories.TrackRepo;
import project.Repositories.UsersRepo;

@Service
public class UserService {

    @Autowired
    private TrackRepo trackRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Value("${upload.path}")
    private String uploadpath;


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
}
