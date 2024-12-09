package com.instagram.instagram_api.controller;

import com.instagram.instagram_api.exceptions.UserException;
import com.instagram.instagram_api.modal.User;
import com.instagram.instagram_api.response.MessageResponse;
import com.instagram.instagram_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/id/{id}")
    public ResponseEntity<User> findUserByIdHandler(@PathVariable Integer id) throws UserException {

        User user = userService.findUserById(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> findUserByUsernameHandler(@PathVariable String username) throws UserException {

        User user = userService.findUserByUsername(username);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PutMapping("/follow/{followUserId}")
    public ResponseEntity<MessageResponse> followUserHandler(@PathVariable Integer followUserId) {
        return null;
    }


    @PutMapping("/unfollow/{userId}")
    public ResponseEntity<MessageResponse> unFollowUserHandler(@PathVariable Integer userId) {
        return null;
    }


    @PutMapping("/req")
    public ResponseEntity<MessageResponse> findUserProfileHandler(@RequestHeader("Authorization") String token) {

        return null;
    }


    @GetMapping("/m/{userIds}")
    public ResponseEntity<List<User>> findUserByUserIdsHandler(@PathVariable List<Integer> userIds) throws UserException {
        System.out.println("This is USER ID: "+userIds);
        List<User> users = userService.findUserByIds(userIds);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }


    //    api/users/search?q="query"
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserHandler(@RequestParam("q") String query) throws UserException {

        List<User> users = userService.searchUser(query);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }


    public ResponseEntity<User> updateUserHandler(@RequestHeader("Authorization") String token, @RequestBody User user) throws UserException {

//        User updateduser = userService.updateUserDetails(user, user);
        return null;
    }
}
