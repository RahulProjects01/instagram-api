package com.instagram.instagram_api.service;

import com.instagram.instagram_api.dto.UserDto;
import com.instagram.instagram_api.exceptions.UserException;
import com.instagram.instagram_api.modal.User;
import com.instagram.instagram_api.repository.UserRepository;
import com.instagram.instagram_api.security.JwtTokenClaims;
import com.instagram.instagram_api.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Override
    public User registerUser(User user) throws UserException {

        Optional<User> isEmailExist = userRepository.findByEmail(user.getEmail());

        if (isEmailExist.isPresent()) {
            throw new UserException("Email is Already Exist!");
        }

        Optional<User> isUsernameExist = userRepository.findByUsername(user.getUsername());

        if (isUsernameExist.isPresent()) {
            throw new UserException("Username is Already Taken...");
        }

        if (user.getEmail() == null || user.getPassword() == null || user.getUsername() == null || user.getName() == null) {
            throw new UserException("All fields are required");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());

        return userRepository.save(newUser);
    }

    @Override
    public User findUserById(Integer userId) throws UserException {

        Optional<User> opt = userRepository.findById(userId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new UserException("user not exist with id: " + userId);
    }

    @Override
    public User findUserProfile(String token) throws UserException {

        //  Bearer gdsfgdsgdsfgsdffsda
        token = token.substring(7);

        JwtTokenClaims jwtTokenClaims = jwtTokenProvider.getClaimsFromToken(token);
        String email = jwtTokenClaims.getUsername();

        Optional<User> opt = userRepository.findByEmail(email);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new UserException("invalid token..");
    }

    @Override
    public User findUserByUsername(String username) throws UserException {
        Optional<User> opt = userRepository.findByUsername(username);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new UserException("user not exist with username: " + username);
    }

    @Override
    public String followUser(Integer reqUserId, Integer followUserId) throws UserException {
        User requser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        UserDto follower = new UserDto();

        follower.setEmail(requser.getEmail());
        follower.setId(requser.getId());
        follower.setName(requser.getName());
        follower.setUserImage(requser.getImage());
        follower.setUsername(requser.getUsername());

        UserDto following = new UserDto();
        following.setEmail(followUser.getEmail());
        following.setId(followUser.getId());
        following.setName(followUser.getName());
        following.setUserImage(followUser.getImage());
        following.setUsername(followUser.getUsername());

        requser.getFollowing().add(following);
        followUser.getFollower().add(follower);

        userRepository.save(followUser);
        userRepository.save(requser);

        return "you are following " + followUser.getUsername();
    }

    @Override
    public String unFollowUser(Integer reqUserId, Integer followUserId) throws UserException {
        User requser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        UserDto follower = new UserDto();

        follower.setEmail(requser.getEmail());
        follower.setId(requser.getId());
        follower.setName(requser.getName());
        follower.setUserImage(requser.getImage());
        follower.setUsername(requser.getUsername());

        UserDto following = new UserDto();
        following.setEmail(followUser.getEmail());
        following.setId(followUser.getId());
        following.setName(followUser.getName());
        following.setUserImage(followUser.getImage());
        following.setUsername(followUser.getUsername());

        requser.getFollowing().remove(follower);
        followUser.getFollower().remove(follower);

        userRepository.save(followUser);
        userRepository.save(requser);

        return "you have unFollowed  " + followUser.getUsername();
    }

    @Override
    public List<User> findUserByIds(List<Integer> userIds) throws UserException {

        List<User> users = userRepository.findAllUsersByIds(userIds);
        return users;
    }

    @Override
    public List<User> searchUser(String query) throws UserException {

        List<User> users = userRepository.findByQuery(query);
        if (users.size() == 0) {
            throw new UserException("user not found");
        }
        return users;
    }

    @Override
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException {

        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getBio() != null) {
            existingUser.setBio(updatedUser.getBio());
        }
        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getMobile() != null) {
            existingUser.setMobile(updatedUser.getMobile());
        }
        if (updatedUser.getGender() != null) {
            existingUser.setGender(updatedUser.getGender());
        }
        if (updatedUser.getWebsite() != null) {
            existingUser.setWebsite(updatedUser.getWebsite());
        }
        if (updatedUser.getImage() != null) {
            existingUser.setImage(updatedUser.getImage());
        }
        if (updatedUser.getId().equals(existingUser.getId())) {
            return userRepository.save(existingUser);
        }

        throw new UserException("you cant update this user");
    }

    @Override
    public List<User> getTopUsers(int limit) throws UserException {
        // Correct Pageable setup: page 0, size 'limit'
        Pageable pageable = PageRequest.of(0, limit);  // Page number should be 0 for the first page
        List<User> topUsers = userRepository.findTopUsers(pageable);
        if (topUsers.isEmpty()) {
            throw new UserException("No top users found");
        }
        return topUsers;
    }


}
