package com.instagram.instagram_api.controller;

import com.instagram.instagram_api.exceptions.PostException;
import com.instagram.instagram_api.exceptions.UserException;
import com.instagram.instagram_api.modal.Post;
import com.instagram.instagram_api.modal.User;
import com.instagram.instagram_api.response.MessageResponse;
import com.instagram.instagram_api.service.PostService;
import com.instagram.instagram_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.instagram.instagram_api.service.PostServiceImplementation.logger;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create")
    public ResponseEntity<Post> createPostHandler(
            @RequestParam("caption") String caption,
            @RequestParam("location") String location,
            @RequestParam("image") String image,
            @RequestHeader("Authorization") String token) throws UserException {

        // Validate the user token
        User user = userService.findUserProfile(token);
        if (user == null) {
            throw new UserException("Invalid token.");
        }

        Integer userId = user.getId();  // Extract the userId from the User object

        try {
            // Create the post using the userId
            Post createdPost = postService.createPost(caption, location, image, userId);

            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating post: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Post>> findPostByUserIdHandler(@PathVariable Integer userId) throws UserException {

        List<Post> posts = postService.findPostByUserId(userId);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @GetMapping("/following/{userId}")
    public ResponseEntity<List<Post>> findPostByUserIdsHandler(@PathVariable List<Integer> userId) throws PostException, UserException {

        // Fetch posts of the users that the given user is following
        List<Post> posts = postService.findAllPostByUserIds(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws PostException {

        Post post = postService.findPostById(postId);
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }


    @PutMapping("/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {

        User user = userService.findUserProfile(token);
        Post likedPost = postService.likePost(postId, user.getId());

        return new ResponseEntity<Post>(likedPost, HttpStatus.OK);
    }


    @PutMapping("/unlike/{postId}")
    public ResponseEntity<Post> unLikePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {

        User user = userService.findUserProfile(token);
        Post likedPost = postService.unLikePost(postId, user.getId());

        return new ResponseEntity<Post>(likedPost, HttpStatus.OK);
    }

    @DeleteMapping("delete/{postId}")
    public ResponseEntity<MessageResponse> deletePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {

        User user = userService.findUserProfile(token);
        String message = postService.deletePost(postId, user.getId());

        MessageResponse messageResponse = new MessageResponse(message);

        return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.ACCEPTED);
    }

    @PutMapping("savePost/{postId}")
    public ResponseEntity<?> savedPostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);
        String message = postService.savedPost(postId, user.getId());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(message));
    }

    @PutMapping("unsave_post/{postId}")
    public ResponseEntity<MessageResponse> unSavedPostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);
        String message = postService.unSavedPost(postId, user.getId());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(message));
    }
}
