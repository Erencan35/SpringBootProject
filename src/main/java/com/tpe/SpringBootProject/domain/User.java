package com.tpe.SpringBootProject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_user")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false)
    private String firstName;

    @Column(length = 32, nullable = false)
    private String lastName;

    @Column(length = 32, nullable = false, unique = true)
    private String username;

    @Column(length = 128, nullable = false)
    private String password;

    /* Encoding Methods
        MD5     --> 32 character lenght
        SHA-1   --> 40  character lenght
        SHA-256 --> 64  character lenght
        SHA-512 --> 128 character lenght
     */

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "t_user_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRole> roles = new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Student student;
}
