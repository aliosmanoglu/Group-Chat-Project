/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.multichatproject;

import java.util.ArrayList;

/**
 *
 * @author Ali Haydar
 */
class User {
    
    public String username;
    public String password;
    public ArrayList<Project> projectList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.projectList = new ArrayList<>();
    }
    
    
    
    
}
