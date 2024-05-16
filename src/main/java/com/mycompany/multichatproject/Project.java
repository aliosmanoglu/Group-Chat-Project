/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.multichatproject;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.DefaultListModel;

/**
 *
 * @author Ali Haydar
 */
public class Project {
    
    public ArrayList<User> userList;
    public DefaultListModel messagesList;
    public String projectKey;
    public String name;
    public DefaultListModel userListModel;

    public Project(String name) {
        this.projectKey = randomKey();
        this.name = name;
        this.userList = new ArrayList();
        this.messagesList = new DefaultListModel();
        this.userListModel = new DefaultListModel();
    }
    
    public String randomKey() {
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000); // 1000 ile 9999 arasında bir sayı üretir
        while(Data.projectKeys.contains(randomNumber)) {
            randomNumber = 1000 + random.nextInt(9000);
        }
        return String.valueOf(randomNumber);
    }
    
    
    
    
    
    
    
}
