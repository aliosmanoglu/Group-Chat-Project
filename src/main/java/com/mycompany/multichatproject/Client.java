/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.multichatproject;

import static com.mycompany.multichatproject.ClientHandler.list;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.DefaultListModel;

/**
 *
 * @author Ali Haydar
 */
public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    //private String username;
    User user;

    public DefaultListModel messageList = new DefaultListModel();

    public Client(Socket socket, User user) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.user = user;

        } catch (IOException e) {
            e.printStackTrace();
            Close();
        }

    }

    /* public void sendMessage() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            
            Scanner s = new Scanner(System.in);
            while(socket.isConnected()){
                String msg = s.nextLine();
                bufferedWriter.write(username + " : " + msg);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                
            }
            
        } catch (IOException e) {
            Close();
            e.printStackTrace();
        }
    }*/
    public void sendMessage(String msg) {
        try {

            if (msg.equals(user.username)) {
                bufferedWriter.write(user.username);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } else {
                bufferedWriter.write(msg);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        } catch (IOException e) {
            Close();
            e.printStackTrace();
        }
    }

    public void Close() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Listen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromClient;

                while (socket.isConnected()) {
                    try {

                        msgFromClient = bufferedReader.readLine();

                        int index = msgFromClient.indexOf(" ");
                        int index2 = msgFromClient.indexOf(" ", index + 1);

                        String messageCode = msgFromClient.substring(0, index);

                        if (messageCode.equals("PROJECTMESSAGE")) {
                            messageCode = msgFromClient.substring(index + 1, index2); //PROJE KODU
                            String newMsg = msgFromClient.substring(index2);
                            for (Project project : Data.projectList) {
                                if(project.projectKey.equals(messageCode)){
                                    project.messagesList.addElement(newMsg);
                                }
                            }
                            
                        }else {
                             messageList.addElement(msgFromClient);
                        }
                        /*if (messageCode.equals("PRIVATEMESSAGE")) {
                            messageCode = msgFromClient.substring(index + 1, index2);
                            
                        }*/

                       


                    } catch (IOException e) {
                        Close();
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /*public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = s.nextLine();
        try {
            Socket socket = new Socket("localhost",5000);
            Client client = new Client(socket, username );
            client.Listen();
            client.sendMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }*/
}
