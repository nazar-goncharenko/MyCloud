package project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.Iservise.ITrackService;
import project.Iservise.IUserService;
import project.models.Track;
import project.models.User;
import project.repositories.TrackRepo;
import project.repositories.UsersRepo;

import java.util.Map;
import java.util.Set;

@Controller
public class Pages_Controller {

    @Autowired
    private ITrackService trackService;

    @Autowired
    private IUserService userService;


    @GetMapping("/")
    public String gotomain(Map<String,Object> model)
    {

        User curUser;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Iterable<Track> tracks = trackService.getTop20();

        if(auth.getName() != "anonymousUser" && auth.getName() != null)
        {
            trackService.configToUser(tracks,auth.getName());
        }

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
        if ( userService.register(login,password))
        {
            return "/register";
        }

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
            Iterable<Track> likedLists = userService.getPlaylist(auth.getName());
            model.put("likedTracks", likedLists);

            return "/playlists";
        }
        else
        {
            return "/login";
        }



    }
}
