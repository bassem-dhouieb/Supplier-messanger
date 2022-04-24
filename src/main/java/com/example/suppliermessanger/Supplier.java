package com.example.suppliermessanger;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;

public class Supplier {

    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    public Supplier(Socket socket,VBox vBox) {
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            receiveMessageFromServer(vBox);
        } catch (IOException e){
            System.out.println("Error creating Supplier");
            e.printStackTrace();
            closeEverything(socket,bufferedReader,bufferedWriter);

        }
    }

    public  void sendMessageToServer(String messageToServer) {
        try {
            bufferedWriter.write(messageToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println(messageToServer);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error sending message to server");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveMessageFromServer(VBox vBox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String messageFromServer = bufferedReader.readLine();
                        SupplierController.addLabel(messageFromServer, vBox);
                        System.out.println(messageFromServer);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error receiving message from the server");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }


                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
