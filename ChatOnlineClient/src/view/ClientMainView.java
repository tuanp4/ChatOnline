/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import model.*;

/**
 *
 * @author Valdez
 */
public class ClientMainView extends javax.swing.JFrame {

    private final String file_path = "file/icon/";
    private final String upload_domain = "http://uploads.chatonline.com";

    private UserController userController = new UserController(this);
    private ConversationController conversationController = null;
    private User user;
    private int status;
    private AccountInfoView myAccountInfoView;
    private ChangePasswordView myChangePasswordView;
    private CreateGroupView createGroupView;
    private boolean checkMyAccountInfoView = false;
    private boolean checkMyChangePasswordView = false;
    private boolean checkCreateGroupView = false;
    private DefaultListModel<User> friendList = new DefaultListModel<>();
    private DefaultListModel<Conversation> groupList = new DefaultListModel<>();
    private ArrayList<ChatBox> friendChatBoxList = new ArrayList<ChatBox>();
    private ArrayList<GroupChatBox> groupChatBoxList = new ArrayList<GroupChatBox>();
    private ArrayList<OtherUserInfoView> otherUserInfoViewsList = new ArrayList<OtherUserInfoView>();

    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;

    private Thread listenServer = null;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserID() {
        User model = new User();
        model.setId(user.getId());
        return model;
    }

    public void setCheckMyAccountInfoView(boolean checkMyAccountInfoView) {
        this.checkMyAccountInfoView = checkMyAccountInfoView;
    }

    public void setCheckMyChangePasswordView(boolean checkMyChangePasswordView) {
        this.checkMyChangePasswordView = checkMyChangePasswordView;
    }

    public void setCheckCreateGroupView(boolean checkCreateGroupView) {
        this.checkCreateGroupView = checkCreateGroupView;
    }

    public JLabel getLbl_Avatar() {
        return lbl_Avatar;
    }

    public UserController getUserController() {
        return userController;
    }

    public ConversationController getConversationController() {
        return conversationController;
    }

    /**
     * Creates new form JF_Main
     */
    public ClientMainView(User user, ObjectInputStream ois, ObjectOutputStream oos) {
        conversationController = new ConversationController(this, ois, oos);
        System.out.println("conversation controller");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }
        setUser(user);
        initComponents();
        status = 0;
        user.setStatus(status);
        userController.changeStatus();
        userController.getFriendList();
        conversationController.getGroupList();
        this.setSize(350, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
        this.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width - this.getWidth(), 0);
        displayAvatar(user);
        displayStatus(user);
        displayName(user);
        displayDescription(user);

        lbl_Avatar.requestFocusInWindow();
        this.ois = ois;
        this.oos = oos;

        new Thread(new ListenServer(ois, oos, friendChatBoxList, groupChatBoxList)).start();

        //.listenServer = new Thread(new ListenServer(ois,oos));
        //listenServer.start();
    }

    public void openUserInfoView(User user) {
        Boolean check = false;
        for (OtherUserInfoView ouiv : otherUserInfoViewsList) {
            if (ouiv.getUser().getId() == user.getId()) {
                ouiv.setVisible(true);
                check = true;
            }
        }
        if (check == false) {
            OtherUserInfoView chatBox = new OtherUserInfoView(user, this);

            otherUserInfoViewsList.add(chatBox);
            chatBox.setVisible(true);
        }
    }

    public void closeUserInfoView(User user) {
        for (OtherUserInfoView ouiv : otherUserInfoViewsList) {
            if (ouiv.getUser().getId() == user.getId()) {
                otherUserInfoViewsList.remove(ouiv);
            }
        }
    }

    public void openFriendChatBox(Conversation conversation) throws IOException {
        Boolean check = false;
        for (ChatBox cb : friendChatBoxList) {
            if (cb.getConversation().getId() == conversation.getId()) {
                cb.setVisible(true);
                cb.setOos(this.oos);
                cb.setOis(this.ois);
                check = true;
            }
        }
        if (check == false) {
            System.out.println("false conversation " + conversation.getId());
            ChatBox chatBox = new ChatBox(conversation, this, this.ois, this.oos);
            Message msg = new Message(conversation.getId(), "connected", "sendMessage");
            
            this.oos.writeObject(msg);
            this.oos.flush();

            friendChatBoxList.add(chatBox);
            chatBox.setVisible(true);
        }
    }

    public void openGroupChatBox(Conversation conversation) throws IOException {
        Boolean check = false;
        for (GroupChatBox gcb : groupChatBoxList) {
            if (gcb.getConversation().getId() == conversation.getId()) {
                gcb.setVisible(true);
                check = true;
            }
        }
        if (check == false) {
            GroupChatBox groupChatBox = new GroupChatBox(conversation, this,ois,oos);
            System.out.println("group chatbox conversationid:" + conversation.getId());
            Message msg = new Message(conversation.getId(), "connected", "sendGroupMessage");
            this.oos.writeObject(msg);
            System.out.println("convesationid in mesaage group:" + msg.getConversation_id());
            this.oos.flush();
            groupChatBoxList.add(groupChatBox);
            groupChatBox.setVisible(true);
        }
    }

    public void closeFriendChatBox(Conversation conversation) {
        for (ChatBox cb : friendChatBoxList) {
            if (cb.getConversation().getId() == conversation.getId()) {
                friendChatBoxList.remove(cb);
            }
        }
    }

    public void closeGroupChatBox(Conversation conversation) {
        for (GroupChatBox gcb : groupChatBoxList) {
            if (gcb.getConversation().getId() == conversation.getId()) {
                groupChatBoxList.remove(gcb);
            }
        }
    }

    public void returnFriendList(ArrayList<User> users) {
        friendList.removeAllElements();
        for (User user : users) {
            friendList.addElement(user);
        }
        JList<User> list = new JList<User>(friendList);
        displayFriendList(list);
    }

    public void displayFriendList(JList<User> friendList) {
        JListFriendList = friendList;
    }

    public void returnGroupList(ArrayList<Conversation> conversations) {
        groupList.removeAllElements();
        for (Conversation conversation : conversations) {
            groupList.addElement(conversation);
        }
        JList<Conversation> list = new JList<Conversation>(groupList);
        displayGroupList(list);
    }

    public void displayGroupList(JList<Conversation> groupList) {
        JListGroupList = groupList;
    }

    public void returnStatus(User user) {
        this.user = user;
        displayStatus(user);
    }

    public void returnDescription(User user) {
        this.user = user;
        displayDescription(user);
    }

    public User getUserChangedStatus() {
        User model = getUserID();
        model.setStatus(status);
        return model;
    }

    public User getUserChangedDescription() {
        User model = getUserID();
        if (!txt_Description.getText().equals(" Tell everyone what do you think... ?")) {
            model.setDescription(txt_Description.getText());
        }
        lbl_Avatar.requestFocusInWindow();
        return model;
    }

    public void openAccountInfoView() {
        checkMyAccountInfoView = true;
        myAccountInfoView = new AccountInfoView(user, this);
        myAccountInfoView.setLocationRelativeTo(this);
        myAccountInfoView.setVisible(true);
    }

    public void openChangePassWordView() {
        checkMyChangePasswordView = true;
        myChangePasswordView = new ChangePasswordView(user, this);
        myChangePasswordView.setLocationRelativeTo(this);
        myChangePasswordView.setVisible(true);
    }

    public void openCreateGroupView() {
        checkCreateGroupView = true;
        createGroupView = new CreateGroupView(user, this, this.ois, this.oos);
        createGroupView.setLocationRelativeTo(this);
        createGroupView.setVisible(true);
    }

    public void displayAvatar(User user) {
        try {
            BufferedImage avatar = ImageIO.read(new URL(upload_domain + "/default/default_avatar.png"));
            if (user.getAvatar_path() != null) {
                URL url = new URL(upload_domain + user.getAvatar_path());
                avatar = ImageIO.read(url);
            }
            Image avatar_rs = avatar.getScaledInstance(78, 78, Image.SCALE_SMOOTH);
            ImageIcon avatar_img = new ImageIcon(avatar_rs);
            lbl_Avatar.setText("");
            lbl_Avatar.setIcon(avatar_img);
        } catch (IOException ex) {
        }
    }

    public void displayStatus(User user) {
        try {
            String status_path = file_path;
            int temp = user.getStatus();
            switch (temp) {
                case 0:
                    status_path += "online-icon.png";
                    break;
                case 1:
                    status_path += "away-icon.png";
                    break;
                case 2:
                    status_path += "busy-icon.png";
                    break;
                case 3:
                    status_path += "invisible-icon.png";
                    break;
                case 4:
                    status_path += "offline-icon.png";
                    break;
            }
            BufferedImage status = ImageIO.read(new File(status_path));
            ImageIcon status_img = new ImageIcon(status);
            lbl_DisplayName.setIcon(status_img);
        } catch (IOException ex) {
        }
    }

    public void displayName(User user) {
        if (user.getDisplay_name() != null && !user.getDisplay_name().equals("")) {
            lbl_DisplayName.setText(user.getDisplay_name());
        } else {
            lbl_DisplayName.setText(user.getUsername());
        }
    }

    public void displayDescription(User user) {
        txt_Description.setForeground(Color.GRAY);
        if (user.getDescription() != null && !user.getDescription().equals("")) {
            txt_Description.setText(user.getDescription());
        } else {
            txt_Description.setText(" Tell everyone what do you think... ?");
        }
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JP_Main = new javax.swing.JPanel();
        JP_Info = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics grphcs) {
                super.paintComponent(grphcs);
                Graphics2D g2d = (Graphics2D) grphcs;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color color = new Color(120, 36, 111);
                GradientPaint gp = new GradientPaint(0, 0, color.darker(), 0, getHeight(), color.brighter());
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        lbl_Avatar = new javax.swing.JLabel();
        lbl_DisplayName = new javax.swing.JLabel();
        txt_Description = new javax.swing.JTextField();
        JP_ChatList = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics grphcs) {
                super.paintComponent(grphcs);
                Graphics2D g2d = (Graphics2D) grphcs;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color color = new Color(120, 36, 111);
                GradientPaint gp = new GradientPaint(0, 0, color.brighter(), 0, getHeight(), color.darker());
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        lbl_AddfriendIcon = new javax.swing.JLabel();
        cb_Addfriend = new javax.swing.JComboBox<>();
        lbl_FriendList = new javax.swing.JLabel();
        JP_FriendList = new javax.swing.JPanel(new BorderLayout());
        JScrollPaneFriendList = new javax.swing.JScrollPane();
        JListFriendList = new javax.swing.JList<>();
        lbl_GroupList = new javax.swing.JLabel();
        JP_GroupList = new javax.swing.JPanel();
        JScrollPaneGroupList = new javax.swing.JScrollPane();
        JListGroupList = new javax.swing.JList<>();
        JMenu = new javax.swing.JMenuBar();
        mn_MyProfile = new javax.swing.JMenu();
        mn_ChangeAccountInfo = new javax.swing.JMenuItem();
        mn_Status = new javax.swing.JMenu();
        mn_StatusOnline = new javax.swing.JMenuItem();
        mn_StatusAway = new javax.swing.JMenuItem();
        mn_StatusBusy = new javax.swing.JMenuItem();
        mn_StatusInvisble = new javax.swing.JMenuItem();
        mn_ChangePassword = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mn_SignOut = new javax.swing.JMenuItem();
        mn_Exit = new javax.swing.JMenuItem();
        mn_Conversation = new javax.swing.JMenu();
        mn_CreateGroup = new javax.swing.JMenuItem();
        mn_Help = new javax.swing.JMenu();
        mn_About = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mn_Version = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ChatOnline");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        JP_Main.setBackground((new java.awt.Color(120, 36, 111)).brighter());

        lbl_Avatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Avatar.setText("Avatar");
        lbl_Avatar.setToolTipText("");
        lbl_Avatar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.lightGray, java.awt.Color.lightGray));
        lbl_Avatar.setMaximumSize(new java.awt.Dimension(78, 78));
        lbl_Avatar.setMinimumSize(new java.awt.Dimension(78, 78));
        lbl_Avatar.setOpaque(true);
        lbl_Avatar.setPreferredSize(new java.awt.Dimension(78, 78));
        lbl_Avatar.setRequestFocusEnabled(false);

        lbl_DisplayName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_DisplayName.setForeground(new java.awt.Color(255, 255, 255));
        lbl_DisplayName.setText("Name");

        txt_Description.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        txt_Description.setForeground(java.awt.Color.gray);
        txt_Description.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_Description.setText(" Tell everyone what do you think... ?");
        txt_Description.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204)));
        txt_Description.setCaretColor(java.awt.Color.darkGray);
        txt_Description.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_DescriptionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_DescriptionFocusLost(evt);
            }
        });
        txt_Description.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_DescriptionKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout JP_InfoLayout = new javax.swing.GroupLayout(JP_Info);
        JP_Info.setLayout(JP_InfoLayout);
        JP_InfoLayout.setHorizontalGroup(
            JP_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_InfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Avatar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JP_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Description, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                    .addComponent(lbl_DisplayName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        JP_InfoLayout.setVerticalGroup(
            JP_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_InfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JP_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_Avatar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JP_InfoLayout.createSequentialGroup()
                        .addComponent(lbl_DisplayName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_Description, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        lbl_AddfriendIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_AddfriendIcon.setIcon(new javax.swing.ImageIcon(file_path + "add-contact-icon.png"));

        cb_Addfriend.setEditable(true);
        cb_Addfriend.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray));
        cb_Addfriend.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        JTextField text = (JTextField) cb_Addfriend.getEditor().getEditorComponent();
        text.setText(" Type friend's username here...");
        text.setFont(new java.awt.Font("Tahoma", 2, 14));
        text.setForeground(java.awt.Color.gray);
        text.addKeyListener(new ComboAddNewFriendKeyHandler(cb_Addfriend, this));
        text.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (text.getText().equals(" Type friend's username here...")) {
                    text.setText(" ");
                    text.setForeground(Color.DARK_GRAY);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                text.setText(" Type friend's username here...");
                text.setForeground(Color.GRAY);
            }
        });

        lbl_FriendList.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbl_FriendList.setForeground(new java.awt.Color(255, 255, 0));
        lbl_FriendList.setIcon(new javax.swing.ImageIcon(file_path + "down.png"));
        lbl_FriendList.setText("Friends List");

        JP_FriendList.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray));

        JListFriendList.setModel(friendList);
        JListFriendList.setCellRenderer(new UserRenderer());
        JListFriendList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JListFriendListMouseClicked(evt);
            }
        });
        JScrollPaneFriendList.setViewportView(JListFriendList);

        javax.swing.GroupLayout JP_FriendListLayout = new javax.swing.GroupLayout(JP_FriendList);
        JP_FriendList.setLayout(JP_FriendListLayout);
        JP_FriendListLayout.setHorizontalGroup(
            JP_FriendListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JScrollPaneFriendList, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
        );
        JP_FriendListLayout.setVerticalGroup(
            JP_FriendListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_FriendListLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(JScrollPaneFriendList, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lbl_GroupList.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbl_GroupList.setForeground(new java.awt.Color(255, 255, 0));
        lbl_GroupList.setIcon(new javax.swing.ImageIcon(file_path + "down.png"));
        lbl_GroupList.setText("Groups List");

        JP_GroupList.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray));

        JListGroupList.setModel(groupList);
        JListGroupList.setCellRenderer(new GroupRenderer());
        JListGroupList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JListGroupListMouseClicked(evt);
            }
        });
        JScrollPaneGroupList.setViewportView(JListGroupList);

        javax.swing.GroupLayout JP_GroupListLayout = new javax.swing.GroupLayout(JP_GroupList);
        JP_GroupList.setLayout(JP_GroupListLayout);
        JP_GroupListLayout.setHorizontalGroup(
            JP_GroupListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JScrollPaneGroupList)
        );
        JP_GroupListLayout.setVerticalGroup(
            JP_GroupListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JScrollPaneGroupList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout JP_ChatListLayout = new javax.swing.GroupLayout(JP_ChatList);
        JP_ChatList.setLayout(JP_ChatListLayout);
        JP_ChatListLayout.setHorizontalGroup(
            JP_ChatListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_ChatListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JP_ChatListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JP_FriendList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JP_GroupList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JP_ChatListLayout.createSequentialGroup()
                        .addGroup(JP_ChatListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_GroupList)
                            .addComponent(lbl_FriendList))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_ChatListLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbl_AddfriendIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_Addfriend, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        JP_ChatListLayout.setVerticalGroup(
            JP_ChatListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_ChatListLayout.createSequentialGroup()
                .addGroup(JP_ChatListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cb_Addfriend)
                    .addComponent(lbl_AddfriendIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addComponent(lbl_FriendList)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JP_FriendList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_GroupList)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JP_GroupList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout JP_MainLayout = new javax.swing.GroupLayout(JP_Main);
        JP_Main.setLayout(JP_MainLayout);
        JP_MainLayout.setHorizontalGroup(
            JP_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_Info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JP_ChatList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JP_MainLayout.setVerticalGroup(
            JP_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_MainLayout.createSequentialGroup()
                .addComponent(JP_Info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JP_ChatList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mn_MyProfile.setText("My Profile");

        mn_ChangeAccountInfo.setText("Account Info");
        mn_ChangeAccountInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_ChangeAccountInfoActionPerformed(evt);
            }
        });
        mn_MyProfile.add(mn_ChangeAccountInfo);

        mn_Status.setText("Change Status");

        mn_StatusOnline.setIcon(new javax.swing.ImageIcon(file_path + "online-icon.png"));
        mn_StatusOnline.setText("Online");
        mn_StatusOnline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_StatusOnlineActionPerformed(evt);
            }
        });
        mn_Status.add(mn_StatusOnline);

        mn_StatusAway.setIcon(new javax.swing.ImageIcon(file_path + "away-icon.png"));
        mn_StatusAway.setText("Away");
        mn_StatusAway.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_StatusAwayActionPerformed(evt);
            }
        });
        mn_Status.add(mn_StatusAway);

        mn_StatusBusy.setIcon(new javax.swing.ImageIcon(file_path + "busy-icon.png"));
        mn_StatusBusy.setText("Busy");
        mn_StatusBusy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_StatusBusyActionPerformed(evt);
            }
        });
        mn_Status.add(mn_StatusBusy);

        mn_StatusInvisble.setIcon(new javax.swing.ImageIcon(file_path + "invisible-icon.png"));
        mn_StatusInvisble.setText("Invisible");
        mn_StatusInvisble.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_StatusInvisbleActionPerformed(evt);
            }
        });
        mn_Status.add(mn_StatusInvisble);

        mn_MyProfile.add(mn_Status);

        mn_ChangePassword.setText("Change Password");
        mn_ChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_ChangePasswordActionPerformed(evt);
            }
        });
        mn_MyProfile.add(mn_ChangePassword);
        mn_MyProfile.add(jSeparator1);

        mn_SignOut.setText("Sign out...");
        mn_SignOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_SignOutActionPerformed(evt);
            }
        });
        mn_MyProfile.add(mn_SignOut);

        mn_Exit.setText("Exit...");
        mn_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_ExitActionPerformed(evt);
            }
        });
        mn_MyProfile.add(mn_Exit);

        JMenu.add(mn_MyProfile);

        mn_Conversation.setText("Conversation");

        mn_CreateGroup.setText("Create Group");
        mn_CreateGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_CreateGroupActionPerformed(evt);
            }
        });
        mn_Conversation.add(mn_CreateGroup);

        JMenu.add(mn_Conversation);

        mn_Help.setText("Help");

        mn_About.setText("About Us");
        mn_Help.add(mn_About);
        mn_Help.add(jSeparator2);

        mn_Version.setText("Version 1.01");
        mn_Version.setEnabled(false);
        mn_Help.add(mn_Version);

        JMenu.add(mn_Help);

        setJMenuBar(JMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_Main, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mn_ChangeAccountInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_ChangeAccountInfoActionPerformed
        // TODO add your handling code here:
        if (checkMyAccountInfoView == true) {
            myAccountInfoView.setVisible(true);
        } else {
            openAccountInfoView();
        }
    }//GEN-LAST:event_mn_ChangeAccountInfoActionPerformed

    private void mn_StatusOnlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_StatusOnlineActionPerformed
        // TODO add your handling code here:
        status = 0;
        userController.changeStatus();
    }//GEN-LAST:event_mn_StatusOnlineActionPerformed

    private void mn_StatusAwayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_StatusAwayActionPerformed
        // TODO add your handling code here:
        status = 1;
        userController.changeStatus();
    }//GEN-LAST:event_mn_StatusAwayActionPerformed

    private void mn_StatusBusyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_StatusBusyActionPerformed
        // TODO add your handling code here:
        status = 2;
        userController.changeStatus();
    }//GEN-LAST:event_mn_StatusBusyActionPerformed

    private void mn_StatusInvisbleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_StatusInvisbleActionPerformed
        // TODO add your handling code here:
        status = 3;
        userController.changeStatus();
    }//GEN-LAST:event_mn_StatusInvisbleActionPerformed

    private void mn_ChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_ChangePasswordActionPerformed
        // TODO add your handling code here:
        if (checkMyChangePasswordView == true) {
            myChangePasswordView.setVisible(true);
        } else {
            openChangePassWordView();
        }
    }//GEN-LAST:event_mn_ChangePasswordActionPerformed

    private void txt_DescriptionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_DescriptionFocusGained
        // TODO add your handling code here:
        if (txt_Description.getText().equals(" Tell everyone what do you think... ?")) {
            txt_Description.setText("");
            txt_Description.setForeground(Color.DARK_GRAY);
        }
    }//GEN-LAST:event_txt_DescriptionFocusGained

    private void txt_DescriptionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_DescriptionFocusLost
        // TODO add your handling code here:
        if (txt_Description.getText().trim().isEmpty()) {
            txt_Description.setText(" Tell everyone what do you think... ?");
            txt_Description.setForeground(Color.GRAY);
        }
        userController.changeDescription();
    }//GEN-LAST:event_txt_DescriptionFocusLost

    private void txt_DescriptionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_DescriptionKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            userController.changeDescription();
        }
    }//GEN-LAST:event_txt_DescriptionKeyPressed

    private void mn_SignOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_SignOutActionPerformed
        // TODO add your handling code here:
        int output = JOptionPane.showConfirmDialog(rootPane, "Are you sure?", "Sign out", JOptionPane.YES_NO_OPTION);
        if (output == JOptionPane.YES_OPTION) {
            try {
                ClientStartView clientStartView = new ClientStartView(this.ois, this.oos);
                clientStartView.setVisible(true);
                if (checkMyAccountInfoView) {
                    myAccountInfoView.dispose();
                }
                if (checkMyChangePasswordView) {
                    myChangePasswordView.dispose();
                }
                if (checkCreateGroupView) {
                    createGroupView.dispose();
                }
                for (ChatBox cb : friendChatBoxList) {
                    cb.dispose();
                }
                for (GroupChatBox gcb : groupChatBoxList) {
                    gcb.dispose();
                }
                for (OtherUserInfoView ouiv : otherUserInfoViewsList) {
                    ouiv.dispose();
                }
                status = 4;
                userController.changeStatus();
                this.dispose();
            } catch (IOException ex) {
                Logger.getLogger(ClientMainView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_mn_SignOutActionPerformed

    private void mn_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_ExitActionPerformed
        // TODO add your handling code here:
        int output = JOptionPane.showConfirmDialog(rootPane, "Are you sure?", "Exit", JOptionPane.YES_NO_OPTION);
        if (output == JOptionPane.YES_OPTION) {
            if (checkMyAccountInfoView) {
                myAccountInfoView.dispose();
            }
            if (checkMyChangePasswordView) {
                myChangePasswordView.dispose();
            }
            if (checkCreateGroupView) {
                createGroupView.dispose();
            }
            for (ChatBox cb : friendChatBoxList) {
                cb.dispose();
            }
            for (GroupChatBox gcb : groupChatBoxList) {
                gcb.dispose();
            }
            for (OtherUserInfoView ouiv : otherUserInfoViewsList) {
                ouiv.dispose();
            }
            status = 4;
            userController.changeStatus();
            this.dispose();
        }
    }//GEN-LAST:event_mn_ExitActionPerformed

    private void JListFriendListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JListFriendListMouseClicked
        // TODO add your handling code here:
        JList list = (JList) evt.getSource();
        try {
            if (SwingUtilities.isLeftMouseButton(evt)) {
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    conversationController.openFriendConversation(friendList.get(index).getId());
                }
            } else if (SwingUtilities.isRightMouseButton(evt)) {
                int index = list.locationToIndex(evt.getPoint());
//                showMessage(index + " right click");
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }
    }//GEN-LAST:event_JListFriendListMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        status = 4;
        userController.changeStatus();
    }//GEN-LAST:event_formWindowClosing

    private void mn_CreateGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_CreateGroupActionPerformed
        // TODO add your handling code here:
        if (checkCreateGroupView == true) {
            createGroupView.setVisible(true);
        } else {
            openCreateGroupView();
        }
    }//GEN-LAST:event_mn_CreateGroupActionPerformed

    private void JListGroupListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JListGroupListMouseClicked
        // TODO add your handling code here:
        JList list = (JList) evt.getSource();
        try {
            if (SwingUtilities.isLeftMouseButton(evt)) {
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    conversationController.openGroupConversation(groupList.get(index).getId());
                }
            } else if (SwingUtilities.isRightMouseButton(evt)) {
                int index = list.locationToIndex(evt.getPoint());
//                showMessage(index + " right click");
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }
    }//GEN-LAST:event_JListGroupListMouseClicked

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ClientMainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ClientMainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ClientMainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ClientMainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                User user = new User();
//                new ClientMainView(user).setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<User> JListFriendList;
    private javax.swing.JList<Conversation> JListGroupList;
    private javax.swing.JMenuBar JMenu;
    private javax.swing.JPanel JP_ChatList;
    private javax.swing.JPanel JP_FriendList;
    private javax.swing.JPanel JP_GroupList;
    private javax.swing.JPanel JP_Info;
    private javax.swing.JPanel JP_Main;
    private javax.swing.JScrollPane JScrollPaneFriendList;
    private javax.swing.JScrollPane JScrollPaneGroupList;
    private javax.swing.JComboBox<String> cb_Addfriend;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JLabel lbl_AddfriendIcon;
    private javax.swing.JLabel lbl_Avatar;
    private javax.swing.JLabel lbl_DisplayName;
    private javax.swing.JLabel lbl_FriendList;
    private javax.swing.JLabel lbl_GroupList;
    private javax.swing.JMenuItem mn_About;
    private javax.swing.JMenuItem mn_ChangeAccountInfo;
    private javax.swing.JMenuItem mn_ChangePassword;
    private javax.swing.JMenu mn_Conversation;
    private javax.swing.JMenuItem mn_CreateGroup;
    private javax.swing.JMenuItem mn_Exit;
    private javax.swing.JMenu mn_Help;
    private javax.swing.JMenu mn_MyProfile;
    private javax.swing.JMenuItem mn_SignOut;
    private javax.swing.JMenu mn_Status;
    private javax.swing.JMenuItem mn_StatusAway;
    private javax.swing.JMenuItem mn_StatusBusy;
    private javax.swing.JMenuItem mn_StatusInvisble;
    private javax.swing.JMenuItem mn_StatusOnline;
    private javax.swing.JMenuItem mn_Version;
    private javax.swing.JTextField txt_Description;
    // End of variables declaration//GEN-END:variables
}
