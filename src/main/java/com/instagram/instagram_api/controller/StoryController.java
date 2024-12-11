package com.instagram.instagram_api.controller;

import com.instagram.instagram_api.exceptions.StoryException;
import com.instagram.instagram_api.exceptions.UserException;
import com.instagram.instagram_api.modal.Story;
import com.instagram.instagram_api.modal.User;
import com.instagram.instagram_api.service.StoryService;
import com.instagram.instagram_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private StoryService storyService;


    @PostMapping("/create")
    public ResponseEntity<Story> createStoryHandler(@RequestBody Story story, @RequestHeader("Authorization") String token) throws UserException {

        User user = userService.findUserProfile(token);
        Story createdStory = storyService.createStory(story, user.getId());

        return new ResponseEntity<Story>(createdStory, HttpStatus.OK);
    }

@GetMapping("id/{userId}")
    public ResponseEntity<List<Story>> findAllStoryByUserIdHandler(@PathVariable Integer userId) throws UserException, StoryException {

        User user = userService.findUserById(userId);
        List<Story> stories = storyService.findStoryByUserId(user.getId());
        return new ResponseEntity<List<Story>>(stories, HttpStatus.OK);
    }
}
