package com.instagram.instagram_api.modal;

import com.instagram.instagram_api.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "user_id")),
            @AttributeOverride(name = "email", column = @Column(name = "user_email")),
    })
    private UserDto user;

    private String content;

    @Embedded
    @ElementCollection
    private Set<UserDto> likesByUsers = new HashSet<UserDto>();

    private LocalDateTime createdAt;

}
