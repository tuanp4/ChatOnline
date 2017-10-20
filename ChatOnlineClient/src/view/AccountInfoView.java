/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.UserController;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.User;

/**
 *
 * @author Valdez
 */
public class AccountInfoView extends javax.swing.JFrame {

    private final String file_path = "file/default/";
    private final String upload_domain = "http://uploads.chatonline.com";

    private UserController userController = new UserController(this);
    private ClientMainView clientMainView;
    private User user;
    private String avatar_path;

    /**
     * Creates new form AccountInfo
     */
    public AccountInfoView(User user, ClientMainView clientMainView) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }
        this.user = user;
        this.clientMainView = clientMainView;
        this.setTitle("User account: " + user.getUsername());
        initComponents();
        displayAvatar();
        displayStaticInfo();
        displayFormInfo();
    }

    public void returnAvatar(User user) {
        this.user = user;
        displayAvatar();
        clientMainView.setUser(user);
        clientMainView.displayAvatar(user);
        showMessage("your avatar has been updated.");
    }

    public void returnInfo(User user) {
        this.user = user;
        clientMainView.setUser(user);
        clientMainView.displayName(user);
        clientMainView.displayDescription(user);
        showMessage("your information has been updated.");
    }

    public User getUserChangedAvatar() throws FileNotFoundException, IOException {
        User model = new User();
        model.setId(user.getId());
        model.setAvatarImageExtenstion(avatar_path.substring(avatar_path.lastIndexOf(".") + 1));
        File fileSend = new File(avatar_path);
        byte[] byteSend = new byte[(int) fileSend.length()];
        FileInputStream fis = new FileInputStream(fileSend);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(byteSend, 0, byteSend.length);
        model.setAvatarImageByte(byteSend);
        fis.close();
        bis.close();
        return model;
    }

    public User getUserChangedInfo() {
        User model = new User();
        model.setId(user.getId());
        model.setDisplay_name(txt_Display_name.getText().trim());
        model.setEmail(txt_Email.getText().trim());
        model.setPhone_number(txt_Phone_number.getText().trim());
        txt_Display_name.requestFocusInWindow();
        return model;
    }

    public void displayStaticInfo() {
        txt_Username.setText(user.getUsername());
        if (user.getGender() == 0) {
            txt_Gender.setText("Male");
        } else if (user.getGender() == 1) {
            txt_Gender.setText("Female");
        }
    }

    public void displayAvatar() {
        try {
            BufferedImage avatar = ImageIO.read(new File(file_path + "default_avatar.png"));
            if (user.getAvatar_path() != null) {
                URL url = new URL(upload_domain + user.getAvatar_path());
                avatar = ImageIO.read(url);
            }
            Image img_rs = avatar.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(img_rs);
            lbl_Avatar.setText("");
            lbl_Avatar.setIcon(icon);
        } catch (IOException ex) {
        }
    }

    public void displayFormInfo() {
        if (user.getDisplay_name() != null && !user.getDisplay_name().equals("")) {
            txt_Display_name.setText(user.getDisplay_name());
        }
        txt_Email.setText(user.getEmail());
        if (user.getPhone_number() != null && !user.getPhone_number().equals("")) {
            txt_Phone_number.setText(user.getPhone_number());
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

        JFileChooser = new javax.swing.JFileChooser();
        JP_Main = new javax.swing.JPanel();
        jp_Basic = new javax.swing.JPanel() {
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
        lbl_ForUsername = new javax.swing.JLabel();
        txt_Username = new javax.swing.JLabel();
        lbl_ForGender = new javax.swing.JLabel();
        txt_Gender = new javax.swing.JLabel();
        jp_ChangeAvatar = new javax.swing.JPanel();
        btn_ChangeAvatar = new javax.swing.JLabel();
        jp_Form = new javax.swing.JPanel() {
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
        lbl_ForDisplay_name = new javax.swing.JLabel();
        txt_Display_name = new javax.swing.JTextField();
        lbl_ForEmail = new javax.swing.JLabel();
        txt_Email = new javax.swing.JTextField();
        lbl_ForPhone_number = new javax.swing.JLabel();
        txt_Phone_number = new javax.swing.JTextField();
        jp_Action = new javax.swing.JPanel();
        jp_Save = new javax.swing.JPanel();
        btn_Save = new javax.swing.JLabel();
        jp_Cancel = new javax.swing.JPanel();
        btn_Cancel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        JP_Main.setBackground((new java.awt.Color(120, 36, 111)).brighter());
        JP_Main.setMaximumSize(new java.awt.Dimension(500, 300));
        JP_Main.setMinimumSize(new java.awt.Dimension(500, 300));

        lbl_Avatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Avatar.setText("Avatar");
        lbl_Avatar.setToolTipText("");
        lbl_Avatar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.lightGray, java.awt.Color.lightGray));
        lbl_Avatar.setMaximumSize(new java.awt.Dimension(120, 120));
        lbl_Avatar.setMinimumSize(new java.awt.Dimension(120, 120));
        lbl_Avatar.setOpaque(true);
        lbl_Avatar.setPreferredSize(new java.awt.Dimension(120, 120));
        lbl_Avatar.setRequestFocusEnabled(false);

        lbl_ForUsername.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_ForUsername.setForeground(java.awt.Color.yellow);
        lbl_ForUsername.setText("Username:");

        txt_Username.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_Username.setForeground(java.awt.Color.yellow);
        txt_Username.setText("username");

        lbl_ForGender.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_ForGender.setForeground(java.awt.Color.yellow);
        lbl_ForGender.setText("Gender:");

        txt_Gender.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_Gender.setForeground(java.awt.Color.yellow);
        txt_Gender.setText("gender");

        jp_ChangeAvatar.setBackground(new java.awt.Color(93, 53, 176));
        jp_ChangeAvatar.setBorder(new javax.swing.border.MatteBorder(null));
        jp_ChangeAvatar.setMaximumSize(new java.awt.Dimension(75, 28));
        jp_ChangeAvatar.setMinimumSize(new java.awt.Dimension(75, 28));

        btn_ChangeAvatar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_ChangeAvatar.setForeground(new java.awt.Color(255, 255, 255));
        btn_ChangeAvatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_ChangeAvatar.setText("Change Avatar");
        btn_ChangeAvatar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.lightGray, java.awt.Color.lightGray));
        btn_ChangeAvatar.setMaximumSize(new java.awt.Dimension(75, 25));
        btn_ChangeAvatar.setMinimumSize(new java.awt.Dimension(75, 25));
        btn_ChangeAvatar.setPreferredSize(new java.awt.Dimension(75, 25));
        btn_ChangeAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ChangeAvatarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ChangeAvatarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ChangeAvatarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jp_ChangeAvatarLayout = new javax.swing.GroupLayout(jp_ChangeAvatar);
        jp_ChangeAvatar.setLayout(jp_ChangeAvatarLayout);
        jp_ChangeAvatarLayout.setHorizontalGroup(
            jp_ChangeAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_ChangeAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
        );
        jp_ChangeAvatarLayout.setVerticalGroup(
            jp_ChangeAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_ChangeAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jp_BasicLayout = new javax.swing.GroupLayout(jp_Basic);
        jp_Basic.setLayout(jp_BasicLayout);
        jp_BasicLayout.setHorizontalGroup(
            jp_BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_BasicLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Avatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jp_BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp_BasicLayout.createSequentialGroup()
                        .addGroup(jp_BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_ForUsername)
                            .addComponent(lbl_ForGender))
                        .addGap(18, 18, 18)
                        .addGroup(jp_BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_Gender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_Username, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)))
                    .addGroup(jp_BasicLayout.createSequentialGroup()
                        .addComponent(jp_ChangeAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jp_BasicLayout.setVerticalGroup(
            jp_BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_BasicLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jp_BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jp_BasicLayout.createSequentialGroup()
                        .addGroup(jp_BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_ForUsername)
                            .addComponent(txt_Username))
                        .addGap(18, 18, 18)
                        .addGroup(jp_BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_ForGender)
                            .addComponent(txt_Gender))
                        .addGap(18, 18, 18)
                        .addComponent(jp_ChangeAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_Avatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lbl_ForDisplay_name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_ForDisplay_name.setForeground(java.awt.Color.yellow);
        lbl_ForDisplay_name.setText("Display name:");

        txt_Display_name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_Display_name.setForeground(java.awt.Color.darkGray);
        txt_Display_name.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204)));
        txt_Display_name.setCaretColor(java.awt.Color.darkGray);

        lbl_ForEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_ForEmail.setForeground(java.awt.Color.yellow);
        lbl_ForEmail.setText("Email:");

        txt_Email.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_Email.setForeground(java.awt.Color.darkGray);
        txt_Email.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204)));
        txt_Email.setCaretColor(java.awt.Color.darkGray);

        lbl_ForPhone_number.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_ForPhone_number.setForeground(java.awt.Color.yellow);
        lbl_ForPhone_number.setText("Phone:");

        txt_Phone_number.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_Phone_number.setForeground(java.awt.Color.darkGray);
        txt_Phone_number.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204)));
        txt_Phone_number.setCaretColor(java.awt.Color.darkGray);

        jp_Action.setOpaque(false);

        jp_Save.setBackground(new java.awt.Color(93, 53, 176));
        jp_Save.setBorder(new javax.swing.border.MatteBorder(null));
        jp_Save.setMaximumSize(new java.awt.Dimension(75, 28));
        jp_Save.setMinimumSize(new java.awt.Dimension(75, 28));

        btn_Save.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_Save.setForeground(new java.awt.Color(255, 255, 255));
        btn_Save.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_Save.setText("Save");
        btn_Save.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.lightGray, java.awt.Color.lightGray));
        btn_Save.setMaximumSize(new java.awt.Dimension(75, 25));
        btn_Save.setMinimumSize(new java.awt.Dimension(75, 25));
        btn_Save.setPreferredSize(new java.awt.Dimension(75, 25));
        btn_Save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_SaveMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_SaveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_SaveMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jp_SaveLayout = new javax.swing.GroupLayout(jp_Save);
        jp_Save.setLayout(jp_SaveLayout);
        jp_SaveLayout.setHorizontalGroup(
            jp_SaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jp_SaveLayout.setVerticalGroup(
            jp_SaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jp_Cancel.setBackground(new java.awt.Color(93, 53, 176));
        jp_Cancel.setBorder(new javax.swing.border.MatteBorder(null));
        jp_Cancel.setMaximumSize(new java.awt.Dimension(75, 28));
        jp_Cancel.setMinimumSize(new java.awt.Dimension(75, 28));

        btn_Cancel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_Cancel.setForeground(new java.awt.Color(255, 255, 255));
        btn_Cancel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_Cancel.setText("Cancel");
        btn_Cancel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.lightGray, java.awt.Color.lightGray));
        btn_Cancel.setMaximumSize(new java.awt.Dimension(75, 25));
        btn_Cancel.setMinimumSize(new java.awt.Dimension(75, 25));
        btn_Cancel.setPreferredSize(new java.awt.Dimension(75, 25));
        btn_Cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_CancelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_CancelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_CancelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jp_CancelLayout = new javax.swing.GroupLayout(jp_Cancel);
        jp_Cancel.setLayout(jp_CancelLayout);
        jp_CancelLayout.setHorizontalGroup(
            jp_CancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jp_CancelLayout.setVerticalGroup(
            jp_CancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jp_ActionLayout = new javax.swing.GroupLayout(jp_Action);
        jp_Action.setLayout(jp_ActionLayout);
        jp_ActionLayout.setHorizontalGroup(
            jp_ActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_ActionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jp_ActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jp_Save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jp_Cancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );
        jp_ActionLayout.setVerticalGroup(
            jp_ActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_ActionLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jp_Save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jp_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jp_FormLayout = new javax.swing.GroupLayout(jp_Form);
        jp_Form.setLayout(jp_FormLayout);
        jp_FormLayout.setHorizontalGroup(
            jp_FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_FormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jp_FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_ForDisplay_name)
                    .addComponent(lbl_ForEmail)
                    .addComponent(lbl_ForPhone_number))
                .addGap(18, 18, 18)
                .addGroup(jp_FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Phone_number)
                    .addComponent(txt_Display_name, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addComponent(txt_Email))
                .addGap(22, 22, 22)
                .addComponent(jp_Action, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jp_FormLayout.setVerticalGroup(
            jp_FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_FormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jp_FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jp_Action, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jp_FormLayout.createSequentialGroup()
                        .addGroup(jp_FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_ForDisplay_name)
                            .addComponent(txt_Display_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jp_FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_ForEmail)
                            .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jp_FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_ForPhone_number)
                            .addComponent(txt_Phone_number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout JP_MainLayout = new javax.swing.GroupLayout(JP_Main);
        JP_Main.setLayout(JP_MainLayout);
        JP_MainLayout.setHorizontalGroup(
            JP_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jp_Basic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jp_Form, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JP_MainLayout.setVerticalGroup(
            JP_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_MainLayout.createSequentialGroup()
                .addComponent(jp_Basic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jp_Form, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_Main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_Main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ChangeAvatarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ChangeAvatarMouseClicked
        // TODO add your handling code here:
        try {
            int fileDailog = JFileChooser.showOpenDialog(this);
            FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
            JFileChooser.setFileFilter(imageFilter);
            File avatar_img = JFileChooser.getSelectedFile();
            if (fileDailog == JFileChooser.CANCEL_OPTION) {
                showMessage("No file selected!");
            } else {
                avatar_path = avatar_img.getPath();
                userController.changeAvatar();
            }
        } catch (Exception ex) {
            showMessage("Invalid file format!");
        }
    }//GEN-LAST:event_btn_ChangeAvatarMouseClicked

    private void btn_ChangeAvatarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ChangeAvatarMouseEntered
        // TODO add your handling code here:
        jp_ChangeAvatar.setBackground(jp_ChangeAvatar.getBackground().brighter());
    }//GEN-LAST:event_btn_ChangeAvatarMouseEntered

    private void btn_ChangeAvatarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ChangeAvatarMouseExited
        // TODO add your handling code here:
        jp_ChangeAvatar.setBackground(new Color(93, 53, 176));
    }//GEN-LAST:event_btn_ChangeAvatarMouseExited

    private void btn_SaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SaveMouseClicked
        // TODO add your handling code here:
        if (txt_Email.getText().trim().equals("")) {
            showMessage("please fill the required fields (email).");
        } else if (!txt_Email.getText().trim().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            showMessage("Invalid email!");
        } else if (!txt_Phone_number.getText().trim().equals("")) {
            if (!txt_Phone_number.getText().trim().matches("^\\+?\\d{1,3}?[- .]?\\(?(?:\\d{2,3})\\)?[- .]?\\d\\d\\d[- .]?\\d\\d\\d\\d$")) {
                showMessage("Invalid phone number!");
            }
            userController.changeInfo();
        } else {
            userController.changeInfo();
        }
    }//GEN-LAST:event_btn_SaveMouseClicked

    private void btn_SaveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SaveMouseEntered
        // TODO add your handling code here:
        jp_Save.setBackground(jp_Save.getBackground().brighter());
    }//GEN-LAST:event_btn_SaveMouseEntered

    private void btn_SaveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SaveMouseExited
        // TODO add your handling code here:
        jp_Save.setBackground(new Color(93, 53, 176));
    }//GEN-LAST:event_btn_SaveMouseExited

    private void btn_CancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_CancelMouseClicked
        // TODO add your handling code here:
        displayFormInfo();
    }//GEN-LAST:event_btn_CancelMouseClicked

    private void btn_CancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_CancelMouseEntered
        // TODO add your handling code here:
        jp_Cancel.setBackground(jp_Cancel.getBackground().brighter());
    }//GEN-LAST:event_btn_CancelMouseEntered

    private void btn_CancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_CancelMouseExited
        // TODO add your handling code here:
        jp_Cancel.setBackground(new Color(93, 53, 176));
    }//GEN-LAST:event_btn_CancelMouseExited

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        clientMainView.setCheckMyAccountInfoView(false);
    }//GEN-LAST:event_formWindowClosed

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
//            java.util.logging.Logger.getLogger(AccountInfoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(AccountInfoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(AccountInfoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(AccountInfoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                User user = new User();
//                ClientMainView clientMainView = new ClientMainView(user);
//                new AccountInfoView(user, clientMainView).setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser JFileChooser;
    private javax.swing.JPanel JP_Main;
    private javax.swing.JLabel btn_Cancel;
    private javax.swing.JLabel btn_ChangeAvatar;
    private javax.swing.JLabel btn_Save;
    private javax.swing.JPanel jp_Action;
    private javax.swing.JPanel jp_Basic;
    private javax.swing.JPanel jp_Cancel;
    private javax.swing.JPanel jp_ChangeAvatar;
    private javax.swing.JPanel jp_Form;
    private javax.swing.JPanel jp_Save;
    private javax.swing.JLabel lbl_Avatar;
    private javax.swing.JLabel lbl_ForDisplay_name;
    private javax.swing.JLabel lbl_ForEmail;
    private javax.swing.JLabel lbl_ForGender;
    private javax.swing.JLabel lbl_ForPhone_number;
    private javax.swing.JLabel lbl_ForUsername;
    private javax.swing.JTextField txt_Display_name;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JLabel txt_Gender;
    private javax.swing.JTextField txt_Phone_number;
    private javax.swing.JLabel txt_Username;
    // End of variables declaration//GEN-END:variables
}
