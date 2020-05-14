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
import project.Service.TrackService;
import project.Service.UserService;

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

    @Autowired
    private TrackService trackService;

    @Autowired
    private UserService userService;

    private User curUser;

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
