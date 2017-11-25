/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;
import view.ChatBox;
import view.GroupChatBox;

/**
 *
 * @author Bui
 */
public class ListenServer implements Runnable{
    //private Socket clientSocket = null;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private ArrayList<ChatBox> chatBox = null;
    private ArrayList<GroupChatBox> groupChatBox = null;
    
    public ListenServer(ObjectInputStream ois, ObjectOutputStream oos, ArrayList<ChatBox> chatBox, ArrayList<GroupChatBox> groupChatBox){
        //this.clientSocket = clientSocket;
        //System.out.println("socket thread:" + this.clientSocket);
        this.oos = oos;
        this.ois = ois;
        this.chatBox = chatBox;
        this.groupChatBox = groupChatBox;
    }

    @Override
    public void run() {
        System.out.println("run thread");
        try {
            Message msg= null;
            while(true){
                if((msg = (Message) ois.readObject()) != null )
                {
                    if(msg.getAction().equals("sendMessage")){
                        System.out.println("read from server:" + msg.getContent());
                        for(ChatBox chatBox: chatBox){
                            if(chatBox.getConversationID().getConversation_id() == msg.getConversation_id()){
                                chatBox.returnMessage(msg);
                            }
                        }
                    }
                    else if(msg.getAction().equals("sendGroupMessage")){
                        System.out.println("read from server:" + msg.getContent());
                        for(GroupChatBox chatBox: groupChatBox){
                            if(chatBox.getConversationID().getConversation_id() == msg.getConversation_id()){
                                chatBox.returnMessage(msg);
                            }
                        }
                    }
                    
                    
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ListenServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ListenServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
