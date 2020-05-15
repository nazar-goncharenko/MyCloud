package project.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.Iservise.ITrackService;
import project.models.Track;
import project.models.User;
import project.repositories.TrackRepo;
import project.repositories.UsersRepo;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class TrackService implements ITrackService {

    @Autowired
    private TrackRepo trackRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Value("${upload.path}")
    private String uploadpath;




    @Override
    public void addTrack(MultipartFile file,String name)
    {
        File uploadDir = new File(uploadpath);
        if ( !uploadDir.exists())
        {
            uploadDir.mkdir();
        }
        if(!file.isEmpty())
        {
            String uuid = UUID.randomUUID().toString();
            String resultFileName = uuid + "." + file.getOriginalFilename();

            System.out.println(resultFileName);
            try
            {
                file.transferTo(new File(uploadpath + "/" + resultFileName));
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            Track newTrack = new Track(name, resultFileName);
            trackRepo.save(newTrack);

        }
    }

    @Override
    public void like(Long ID)
    {
        Optional<Track> tr = trackRepo.findById(ID);
        Track new_track = tr.get();
        new_track.addRating();
        trackRepo.save(new_track);
    }

    @Override
    public void unlike(Long ID)
    {
        Optional<Track> tr = trackRepo.findById(ID);
        Track new_track = tr.get();
        new_track.remRating();
        trackRepo.save(new_track);
        System.out.println(new_track.getRating());
    }

    @Override
    public Iterable<Track> getTop20()
    {
        return trackRepo.findTop20ByOrderByRatingDesc();
    }

    @Override
    public void configToUser(Iterable <Track> tracks, String Name)
    {
        User curUser = usersRepo.findByLoginIs(Name);
        Set<Track> likedLists = curUser.getLikedLists();
        for (Track track: tracks) {
            if (likedLists.contains(track))
            {
                //System.out.println(track.isLikes());
                track.setLikes(true);
            }

        }


    }


}
