/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import controller.dao.DAOConversation;
import controller.dao.DAOFriendship;
import controller.dao.DAOMessage;
import controller.dao.DAOUser;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import model.Conversation;
import model.Friendship;
import model.Group;
import model.Message;
import model.User;
import view.ServerView;

/**
 *
 * @author Bui
 */
public class clientThread extends Thread {

    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;
    private int maxClientsCount;
    private Connection con;
    private ServerView view;
    private User user;
    //private ArrayList<clientThread> threadConversations = new ArrayList<>();
    private int conversation;
    private String action;
    //private UserController userController = null;
    //private DAOUser daoUser = null;

    public clientThread(Socket clientSocket, clientThread[] threads, Connection con, ServerView view
    /*User user, DAOUser daoUser*/) {
        System.out.println("clientThread");
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
        this.con = con;
        this.view = view;
        //this.conversation = new Conversation();
        //this.user = user;
        //this.userController = userController;
        //this.daoUser = daoUser;
    }

    public void run() {
        //System.out.println("run");
        int maxClientsCount = this.maxClientsCount;
        clientThread[] threads = this.threads;

        try {
            //clientSocket = new Socket();
            //clientSocket = myServer.accept();
            //System.out.println("run1");
            ois = new ObjectInputStream(clientSocket.getInputStream());
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            //this.oos.reset();
            //System.out.println("run o");
            while (true) {
                Object o = ois.readObject();
                System.out.println("Object:" + o);
                if (o instanceof Conversation) {
                    Conversation conversation = (Conversation) o;
                    DAOConversation dao = new DAOConversation(con);
                    ConversationController conversationController = new ConversationController(dao);
                    if (conversation.getAction().equals("getGroupList")) {
                        oos.writeObject(conversationController.returnAvailableGroupList(conversation));
                        oos.flush();
                    }
                    if (conversation.getAction().equals("openFriendConversation")) {
                        System.out.println("conversation id:" + conversationController.openFriendConversation(conversation).getId());
                        //this.conversation = conversationController.openFriendConversation(conversation).getId();
                        oos.writeObject(conversationController.openFriendConversation(conversation));
                        oos.flush();
                    }
                    if (conversation.getAction().equals("createGroupConversation")) {
                        oos.writeObject(conversationController.returnGroupConversation(conversation));
                        oos.flush();
                    }
                    if (conversation.getAction().equals("openGroupConversation")) {
                        oos.writeObject(conversationController.openGroupConversation(conversation));
                        oos.flush();
                    }
                } else if (o instanceof Friendship) {
                    Friendship friendship = (Friendship) o;
                    DAOFriendship dao = new DAOFriendship(con);
                    FriendshipController friendshipController = new FriendshipController(dao);
                    if (friendship.getAction().equals("getFriendshipState")) {
                        oos.writeObject(friendshipController.returnFriendshipState(friendship));
                        oos.flush();
                    }
                    if (friendship.getAction().equals("addFriend")) {
                        oos.writeObject(friendshipController.returnAddFriendRespond(friendship));
                        oos.flush();
                    }
                    if (friendship.getAction().equals("acceptFriendRequest")) {
                        oos.writeObject(friendshipController.returnAcceptFriendRequestRespond(friendship));
                        oos.flush();
                    }
                } else if (o instanceof Group) {
                    Group group = (Group) o;

                } else if (o instanceof Message) {
                    System.out.println("message");
                    Message message = (Message) o;
                    System.out.println("message content:" + message.getContent());
                    System.out.println("message action:" + message.getAction());
                    DAOMessage dao = new DAOMessage(con);
                    MessageController messageController = new MessageController(dao);
                    if (message.getAction().equals("sendMessage")) {
                        this.action = message.getAction();
                        System.out.println("conversation id in message:" + message.getConversation_id());
                        //Conversation threads
                        int conversationId = message.getConversation_id();
                        this.conversation = conversationId;
                        //System.out.println("conversationId:" + conversationId);
                        int check = 0;
                        for (int i = 0; i < threads.length; i++) {

                            if (threads[i] != null) {
                                System.out.println("threads:" + i + " : " + threads[i] + "conversationid:" + threads[i].conversation);
                                try {
                                    check++;
                                    if (check < 2 && message.getUser_id() != 0) {

                                        messageController.returnSentMessage(message);
                                    }
                                    //System.out.println("index:" + i);

                                    if (threads[i].conversation == conversationId && message.getUser_id() != 0) {
                                        System.out.println("save thread " + i);
                                        //Message msg = new Message();
                                        //msg.setContent("asdasd");
                                        threads[i].oos.writeObject(message);
                                        threads[i].oos.flush();
                                        System.out.println("write to client");
                                    }
                                } catch (Exception e) {
                                    System.out.println("exception:" + e);
                                }
                            }

                            /*if(threads[i].conversation == conversationId && threads[i].action.equals("sendMessage")){
                                System.out.println("message sent");
                                threads[i].oos.writeObject(messageController.returnSentMessage(message));
                                threads[i].oos.flush();
                            }*/
                        }
                        System.out.println("end loop");
                    } else if (message.getAction().equals("getHistoryMessages")) {
                        System.out.println("get history");
                        System.out.println("get histroy message content:" + message.getContent());
                        oos.writeObject(messageController.returnHistoryMessages(message));
                        oos.flush();
                    }
                    if (message.getAction().equals("sendGroupMessage")) {
                        int conversationId = message.getConversation_id();
                        this.conversation = conversationId;
                        int check = 0;
                        for (int i = 0; i < threads.length; i++) {

                            if (threads[i] != null) {
                                System.out.println("threads:" + i + " : " + threads[i] + "conversationid:" + threads[i].conversation);
                                try {
                                    check++;
                                    if (check < 2 && message.getUser_id() != 0) {

                                        messageController.returnSentMessage(message);
                                    }
                                    //System.out.println("index:" + i);

                                    if (threads[i].conversation == message.getConversation_id() && message.getUser_id() != 0) {
                                        System.out.println("save thread " + i);
                                        //Message msg = new Message();
                                        //msg.setContent("asdasd");
                                        threads[i].oos.writeObject(message);
                                        threads[i].oos.flush();
                                        System.out.println("write to client");
                                    }
                                } catch (Exception e) {
                                    System.out.println("exception:" + e);
                                }
                            }

                            /*if(threads[i].conversation == conversationId && threads[i].action.equals("sendMessage")){
                                System.out.println("message sent");
                                threads[i].oos.writeObject(messageController.returnSentMessage(message));
                                threads[i].oos.flush();
                            }*/
                        }
                    }
                    if (message.getAction().equals("getGroupHistoryMessages")) {
                        oos.writeObject(messageController.returnHistoryMessages(message));
                        oos.flush();
                    }
                } else if (o instanceof User) {
                    User user = (User) o;
                    DAOUser dao = new DAOUser(con);
                    UserController userController = new UserController(dao);
                    if (user.getAction().equals("login")) {
                        oos.writeObject(userController.checkUserLogin(user));
                        oos.flush();
                    }
                    if (user.getAction().equals("signUp")) {
                        oos.writeObject(userController.signUpUser(user));
                        oos.flush();
                    }
                    if (user.getAction().equals("changeInfo")) {
                        oos.writeObject(userController.changeUserInfo(user));
                        oos.flush();
                    }
                    if (user.getAction().equals("changeAvatar")) {
                        oos.writeObject(userController.changeUserAvatar(user));
                        oos.flush();
                    }
                    if (user.getAction().equals("changeStatus")) {
                        oos.writeObject(userController.changeUserStatus(user));
                        oos.flush();
                    }
                    if (user.getAction().equals("changePassword")) {
                        oos.writeObject(userController.changeUserPassword(user));
                        oos.flush();
                    }
                    if (user.getAction().equals("changeDescription")) {
                        oos.writeObject(userController.changeUserDescription(user));
                        oos.flush();
                    }
                    if (user.getAction().equals("getFriendList")) {
                        oos.writeObject(userController.returnAvailableFriendList(user));
                        oos.flush();
                    }
                    if (user.getAction().equals("getSuggestedNewFriends")) {
                        oos.writeObject(userController.returnSuggestedUsers(user));
                        oos.flush();
                    }
                    if (user.getAction().equals("getSuggestedParticipantFriends")) {
                        oos.writeObject(userController.returnSuggestedParticipants(user));
                        oos.flush();
                    }
                }
            }

        } catch (Exception e) {
            view.showMessage(e.toString());
            e.printStackTrace();
        }
    }
}
