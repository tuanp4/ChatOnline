/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Valdez
 */
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String avatar_path;
    private String description;
    private String frame_color;
    private String main_icon;
    private Timestamp created_at;

    private String action;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrame_color() {
        return frame_color;
    }

    public void setFrame_color(String frame_color) {
        this.frame_color = frame_color;
    }

    public String getMain_icon() {
        return main_icon;
    }

    public void setMain_icon(String main_icon) {
        this.main_icon = main_icon;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Conversation() {
    }

    private int mainUserId;
    private int friendId;
    private String mainUserAvatarPath;
    private String friendAvatarPath;
    private String friendDisplayName;

    public int getMainUserId() {
        return mainUserId;
    }

    public void setMainUserId(int mainUserId) {
        this.mainUserId = mainUserId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getMainUserAvatarPath() {
        return mainUserAvatarPath;
    }

    public void setMainUserAvatarPath(String mainUserAvatarPath) {
        this.mainUserAvatarPath = mainUserAvatarPath;
    }

    public String getFriendAvatarPath() {
        return friendAvatarPath;
    }

    public void setFriendAvatarPath(String friendAvatarPath) {
        this.friendAvatarPath = friendAvatarPath;
    }

    public String getFriendDisplayName() {
        return friendDisplayName;
    }

    public void setFriendDisplayName(String friendDisplayName) {
        this.friendDisplayName = friendDisplayName;
    }

}
