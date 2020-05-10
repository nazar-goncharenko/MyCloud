package project.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.Models.Track;
import project.Models.User;
import project.Repositories.TrackRepo;
import project.Repositories.UsersRepo;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Controller
public class Pages_Controller {

    @Autowired
    private TrackRepo trackRepo;

    @Autowired
    private UsersRepo usersRepo;


    @GetMapping("/")
    public String gotomain(Map<String,Object> model)
    {
        Iterable<Track> tracks = trackRepo.findTop20ByOrderByRatingDesc();
        model.put("tracks", tracks);
        return "main";
    }

    @GetMapping("/register")
    public String register()
    {
        return "/register";
    }


    @PostMapping("/register")
    public String addUser(@RequestParam String login, @RequestParam String password)
    {
        if ( usersRepo.findByLoginIs(login) != null && password != null)
        {
            return "/register";
        }
        usersRepo.save(new User(login,password));
        return "redirect:/";
    }


    @GetMapping("/addtrack")
    public String addtrack()
    {
        return "addtrack";
    }



    @GetMapping("/playlists")
    public String playlists(Map<String,Object> model)
    {
        User curUser;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getName() != "anonymousUser" && auth.getName() != null)
        {
            String Name = auth.getName();
            System.out.println("Name: " + Name);
            curUser = usersRepo.findByLoginIs(Name);
            if ( !curUser.getLikedLists().isEmpty())
            {
                System.out.println("NOT EMPTY");
                Set<Track> likedTracks = curUser.getLikedLists();
                model.put("likedTracks", likedTracks);
            }
            return "/playlists";
        }
        else
        {
            return "/login";
        }



    }
}
