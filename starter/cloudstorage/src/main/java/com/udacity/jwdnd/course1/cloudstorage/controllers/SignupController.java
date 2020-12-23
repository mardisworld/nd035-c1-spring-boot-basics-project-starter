package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.model.SignupForm;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView(@ModelAttribute("signupForm") SignupForm signupForm ) { return "signup"; }

    @PostMapping
    public String signupUser(@ModelAttribute("signupForm") SignupForm signupForm, Model model){
        String signupError = null;
        if (!userService.isUserNameAvailable(signupForm.getUserName())) {
            signupError = "The username already exists.";
        }
        if (signupError == null) {
            int rowsAdded = userService.createUser(signupForm);
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }
        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
            System.out.println("Sign up successful");
        } else {
            model.addAttribute("signupError", signupError);
            System.out.println("Sign up failed, please try again.");
        }
        System.out.println("Thank you for signing up!.");
        return "signup";
    }
}