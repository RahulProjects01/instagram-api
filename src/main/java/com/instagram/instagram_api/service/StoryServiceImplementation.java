package com.instagram.instagram_api.service;

import com.instagram.instagram_api.dto.UserDto;
import com.instagram.instagram_api.exceptions.StoryException;
import com.instagram.instagram_api.exceptions.UserException;
import com.instagram.instagram_api.modal.Story;
import com.instagram.instagram_api.modal.User;
import com.instagram.instagram_api.repository.StoryRepository;
import com.instagram.instagram_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryServiceImplementation implements StoryService{

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Story createStory(Story story, Integer userId) throws UserException {

        User user = userService.findUserById(userId);
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getUsername());
        userDto.setUsername(user.getUsername());

        story.setUser(userDto);
        story.setTimestamp(LocalDateTime.now());

        user.getStories().add(story);
        return storyRepository.save(story);
    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException {

        User user = userService.findUserById(userId);
        List<Story> stories = user.getStories();

        if (stories.isEmpty()) {
            throw new StoryException("this user doesn't have any story");
        }
        return stories;
    }
}
