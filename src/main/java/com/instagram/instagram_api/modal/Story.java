package com.instagram.instagram_api.modal;

import com.instagram.instagram_api.dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Stories")
@AllArgsConstructor
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "user_id")),
            @AttributeOverride(name = "email", column = @Column(name = "user_email"))
    })
    private UserDto user;

    @NotNull
    private String image;
    private String caption;
    private LocalDateTime timestamp;

    public Story() {

    }
}
