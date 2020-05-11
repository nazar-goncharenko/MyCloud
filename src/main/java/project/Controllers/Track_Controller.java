package project.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import project.Models.Track;
import project.Models.User;
import project.Repositories.TrackRepo;
import project.Repositories.UsersRepo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
public class Track_Controller {

    @Autowired
    private TrackRepo trackRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Value("${upload.path}")
    private String uploadpath;

    private User curUser;

    @PostMapping("/adtrack")
    String addfile(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) throws IOException
    {
        System.out.println("HERE #1");
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
            file.transferTo(new File(uploadpath + "/" + resultFileName));

            Track newTrack = new Track(name, resultFileName);
            trackRepo.save(newTrack);

        }

        return"redirect:/";
    }


    @PostMapping("/like")
    public String rating_plus(@RequestParam("id") Long ID)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getName() != "anonymousUser" && auth.getName() != null)
        {
            Optional<Track> tr = trackRepo.findById(ID);
            Track new_track = tr.get();
            new_track.addRating();
            trackRepo.save(new_track);
            System.out.println(new_track.getRating());
            // -------^^^^rating^^^^-----------//


            String Name = auth.getName();
            curUser = usersRepo.findByLoginIs(Name);
            Track tr1 = trackRepo.findById(ID).get();
//            System.out.println(Name + " " + tr1.get().getName() + " " + curUser.getLikedLists().size());
            curUser.addLikedTrack(tr1);
            usersRepo.save(curUser);

            return "redirect:/";
        }
        else
        {
            return "/login";
        }

    }



    @PostMapping("/unlike")
    public String rating_mines(@RequestParam("id") Long ID)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getName() != "anonymousUser" && auth.getName() != null)
        {
            Optional<Track> tr = trackRepo.findById(ID);
            Track new_track = tr.get();
            new_track.remRating();
            trackRepo.save(new_track);
            System.out.println(new_track.getRating());
            // -------^^^^rating^^^^-----------//


            String Name = auth.getName();
            curUser = usersRepo.findByLoginIs(Name);
            Track tr1 = trackRepo.findById(ID).get();
            curUser.getLikedLists().remove(tr1);
//            System.out.println(Name + " " + tr1.get().getName() + " " + curUser.getLikedLists().size());
            usersRepo.save(curUser);

            return "redirect:/";
        }
        else
        {
            return "/login";
        }

    }
}
