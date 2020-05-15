package project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import project.Iservise.ITrackService;
import project.Iservise.IUserService;
import project.models.User;
import project.repositories.TrackRepo;
import project.repositories.UsersRepo;
import project.service.TrackService;
import project.service.UserService;

import java.io.IOException;

@Controller
public class Track_Controller {

    @Autowired
    private ITrackService trackService;

    @Autowired
    private IUserService userService;


    @PostMapping("/addtrack")
    String addfile(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) throws IOException
    {
        trackService.addTrack(file,name);
        return"redirect:/";
    }


    @PostMapping("/like")
    public String rating_plus(@RequestParam("id") Long ID)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getName() != "anonymousUser" && auth.getName() != null)
        {
            trackService.like(ID);

            userService.playlistAdd(ID,auth.getName());

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
            trackService.unlike(ID);

            userService.playlistRemove(ID,auth.getName());

            return "redirect:/";
        }
        else
        {
            return "/login";
        }

    }
}
