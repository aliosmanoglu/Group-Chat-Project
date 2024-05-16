/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.multichatproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Ali Haydar
 */
class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> list = new ArrayList<>();

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    User user;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = bufferedReader.readLine();

            for (int i = 0; i < Data.userList.size(); i++) {
                if (Data.userList.get(i).username.equals(username)) {
                    this.user = Data.userList.get(i);
                }
            }

            list.add(this);

            broadcast("SERVER : " + username + " connected !!");

        } catch (IOException e) {
            e.printStackTrace();
            Close();
        }

    }

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
                    messageCode = msgFromClient.substring(index + 1, index2);
                    broadcastProject(msgFromClient, messageCode);
                }
                else if (messageCode.equals("PRIVATEMESSAGE")) {
                    messageCode = msgFromClient.substring(index + 1, index2);
                    broadcastPrivate(msgFromClient, messageCode);
                }else {
                    broadcast(msgFromClient);
                }

                
            } catch (IOException e) {
                Close();
                e.printStackTrace();
            }
        }

    }

    private void Close() {
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
            remove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void remove() {
        list.remove(this);
        broadcast("SERVER : " + username + " has left the chat");
    }

    private void broadcast(String msg) {
        for (ClientHandler clientHandler : list) {
            try {
                clientHandler.bufferedWriter.write(msg);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
                Close();
            }
        }

    }

    private void broadcastProject(String msg, String code) {
        //System.out.println(code);
        Project p = null;
        for (int i = 0; i < Data.projectList.size(); i++) {
            if (Data.projectList.get(i).projectKey.equals(code)) {
                p = Data.projectList.get(i);
                break;
            }
        }

        try {
            for (int j = 0; j < p.userList.size(); j++) {// USER 1 USER 2 USER 3
                for (int k = 0; k < list.size(); k++) {
                    if (p.userList.get(j) == list.get(k).user  && list.get(k).user != this.user) { // LİST : USER 1 USER 2 USER 3
                        list.get(k).bufferedWriter.write(msg);
                        list.get(k).bufferedWriter.newLine();
                        list.get(k).bufferedWriter.flush();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            Close();
            e.printStackTrace();
        }
    }
     private void broadcastPrivate(String msg, String code) {
        Project p = null;
        for (int i = 0; i < Data.projectList.size(); i++) {
            if (Data.projectList.get(i).projectKey.equals(code)) {
                p = Data.projectList.get(i);
            }
        }

        try {
            for (int j = 0; j < p.userList.size(); j++) {
                for (int k = 0; k < list.size(); k++) {
                    if (p.userList.get(j) == list.get(k).user && list.get(k).user != this.user) {
                        list.get(k).bufferedWriter.write(msg);
                        list.get(k).bufferedWriter.newLine();
                        list.get(k).bufferedWriter.flush();
                    }
                }
            }
        } catch (IOException e) {
            Close();
            e.printStackTrace();
        }
    }

}
