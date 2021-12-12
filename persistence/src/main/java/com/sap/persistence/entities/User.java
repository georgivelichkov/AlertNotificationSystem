package com.sap.persistence.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "user-uuid")
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    private final String id;

    @Column(length = 50)
    private final String username;

    @Column(length = 100)
    private final String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authority")
    private final List<String> authorities;

    public User() {
        this(null, null , null, null);
    }

    public User( String username, String password, List<String> authorities) {
        this(null, username, password, authorities);
    }

    public User(String id, String username, String password, List<String> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public String getId() {
        return id;
    }
}
