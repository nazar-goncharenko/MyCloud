package project.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import project.Models.Track;
import project.Repositories.TrackRepo;
import project.Repositories.UsersRepo;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrackService {

    @Autowired
    private TrackRepo trackRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Value("${upload.path}")
    private String uploadpath;




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

    public void like(Long ID)
    {
        Optional<Track> tr = trackRepo.findById(ID);
        Track new_track = tr.get();
        new_track.addRating();
        trackRepo.save(new_track);
    }

    public void unlike(Long ID)
    {
        Optional<Track> tr = trackRepo.findById(ID);
        Track new_track = tr.get();
        new_track.remRating();
        trackRepo.save(new_track);
        System.out.println(new_track.getRating());
    }



}
