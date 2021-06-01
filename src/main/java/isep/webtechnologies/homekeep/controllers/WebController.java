package isep.webtechnologies.homekeep.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import isep.webtechnologies.homekeep.models.user.User;

@Controller
public class WebController {
    @RequestMapping("/profile")
    public String profile(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user",currentUser);
        return "/profile";
    }

    @RequestMapping("/registration")
    public String registration() {
        return "/registration";
    }
}
