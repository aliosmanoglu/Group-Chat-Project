/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.multichatproject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Ali Haydar
 */
public class Server {

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        
    }

    public void Start() {

        try {
            while (!serverSocket.isClosed()) {

                Socket socket = serverSocket.accept();
                System.out.println("A new client connected");
                
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread t1 = new Thread(clientHandler);
                t1.start();

            }
        } catch (IOException e) {
            Close();
            e.printStackTrace();
        }

    }

    public void Close() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
   /* public static void main(String[] args) {
        
      
        
        try {
            ServerSocket socket = new ServerSocket(5000);
            
            Server server = new Server(socket);
            server.Start();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
