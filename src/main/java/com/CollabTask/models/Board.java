package com.CollabTask.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToMany
    @JoinTable(
        name = "board_user",
        joinColumns = @JoinColumn(name = "board_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties("boards")
    private List<AppUser> users;

    @OneToMany(mappedBy = "board")
    @JsonIgnoreProperties("board")
    private List<BoardList> lists;

    public Board() {
        this.users = new ArrayList<>();
        this.lists = new ArrayList<>();
    }

    public Board(Long id, String title, String description, List<AppUser> users, List<BoardList> lists) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.users = users;
        this.lists = lists;
    }
    
    public Board(String title, String description, List<AppUser> users, List<BoardList> lists) {
        this.title = title;
        this.description = description;
        this.users = users;
        this.lists = lists;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AppUser> getUsers() {
        return users;
    }

    public void setUsers(List<AppUser> users) {
        this.users = users;
    }

    public List<BoardList> getLists() {
        return lists;
    }

    public void setLists(List<BoardList> lists) {
        this.lists = lists;
    }
}
