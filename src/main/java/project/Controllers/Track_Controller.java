package project.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class Track_Controller {

    @Autowired
    private TrackRepo trackRepo;

    @Autowired
    private UsersRepo usersRepo;

    private User curUser;

    @PostMapping("/adtrack")
    String addfile(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) throws IOException
    {

        if(!file.isEmpty())
        {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File("src\\main\\resources\\static\\sounds\\" + name + ".mp3")));
            stream.write(bytes);
            stream.close();
            Track track = new Track(name,"\\sounds\\" + name + ".mp3");
            trackRepo.save(track);

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
            Optional<Track> tr1 = trackRepo.findById(ID);
//            System.out.println(Name + " " + tr1.get().getName() + " " + curUser.getLikedLists().size());
            curUser.addLikedTrack(tr1.get());
            usersRepo.save(curUser);

            return "redirect:/playlists";
        }
        else
        {
            return "/login";
        }

    }
}
