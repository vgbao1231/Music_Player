package org.music_player.web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(name = "username",unique = true)
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "role")
    private String role;
    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Playlist> playlists;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private OTP otp;
}
