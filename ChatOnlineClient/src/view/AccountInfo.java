/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import model.User;

/**
 *
 * @author Valdez
 */
public class AccountInfo extends javax.swing.JFrame {

    private final String file_path = "file/default/";
    private final User user;

    /**
     * Creates new form AccountInfo
     */
    public AccountInfo(User user) {
        this.user = user;
        this.setTitle("User account: " + user.getUsername());
        initComponents();
        displayFixedInfo();
        displayAvatar();
        displayFormField();
    }

    public void displayAvatar() {
        try {
            String avatar_path = file_path + "default_avatar.jpg";
            if (user.getAvatar_path() != null) {
                avatar_path = user.getAvatar_path();
            }
            BufferedImage img = ImageIO.read(new File(avatar_path));
            Image img_rs = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(img_rs);
            lbl_Avatar.setText("");
            lbl_Avatar.setIcon(icon);
        } catch (IOException ex) {
        }
    }

    public void displayFixedInfo() {
        txt_Username.setText(user.getUsername());
        if (user.getGender() == 0) {
            txt_Gender.setText("Male");
        } else if (user.getGender() == 0) {
            txt_Gender.setText("Female");
        }
    }

    public void displayFormField() {
        if (user.getDisplay_name() != null && !user.getDisplay_name().equals("")) {
            txt_Display_name.setText(user.getDisplay_name());
        }
        txt_Email.setText(user.getEmail());
        if (user.getPhone_number() != null && !user.getPhone_number().equals("")) {
            txt_Phone_number.setText(user.getPhone_number());
        }
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        JP_Main.setBackground((new java.awt.Color(120, 36, 111)).brighter());
        JP_Main.setMaximumSize(new java.awt.Dimension(500, 300));
        JP_Main.setMinimumSize(new java.awt.Dimension(500, 300));
        JP_Main.setPreferredSize(new java.awt.Dimension(500, 300));

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
        lbl_ForUsername.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ForUsername.setText("Username:");

        txt_Username.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_Username.setForeground(new java.awt.Color(255, 255, 255));
        txt_Username.setText("username");

        lbl_ForGender.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_ForGender.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ForGender.setText("Gender:");

        txt_Gender.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_Gender.setForeground(new java.awt.Color(255, 255, 255));
        txt_Gender.setText("gender");

        javax.swing.GroupLayout jp_BasicLayout = new javax.swing.GroupLayout(jp_Basic);
        jp_Basic.setLayout(jp_BasicLayout);
        jp_BasicLayout.setHorizontalGroup(
            jp_BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_BasicLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Avatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jp_BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_ForUsername)
                    .addComponent(lbl_ForGender))
                .addGap(18, 18, 18)
                .addGroup(jp_BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Gender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_Username, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                            .addComponent(txt_Gender)))
                    .addComponent(lbl_Avatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lbl_ForDisplay_name.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_ForDisplay_name.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ForDisplay_name.setText("Display name:");

        txt_Display_name.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        txt_Display_name.setForeground(new java.awt.Color(153, 153, 153));
        txt_Display_name.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 0, 153), new java.awt.Color(153, 0, 153), new java.awt.Color(153, 0, 153), new java.awt.Color(153, 0, 153)));

        lbl_ForEmail.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_ForEmail.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ForEmail.setText("Email:");

        txt_Email.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        txt_Email.setForeground(new java.awt.Color(153, 153, 153));
        txt_Email.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 0, 153), new java.awt.Color(153, 0, 153), new java.awt.Color(153, 0, 153), new java.awt.Color(153, 0, 153)));

        lbl_ForPhone_number.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_ForPhone_number.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ForPhone_number.setText("Phone:");

        txt_Phone_number.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        txt_Phone_number.setForeground(new java.awt.Color(153, 153, 153));
        txt_Phone_number.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 0, 153), new java.awt.Color(153, 0, 153), new java.awt.Color(153, 0, 153), new java.awt.Color(153, 0, 153)));

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
                    .addComponent(txt_Display_name, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                    .addComponent(txt_Email))
                .addGap(127, 127, 127))
        );
        jp_FormLayout.setVerticalGroup(
            jp_FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_FormLayout.createSequentialGroup()
                .addContainerGap()
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
                .addContainerGap(21, Short.MAX_VALUE))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(JP_Main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_Main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AccountInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AccountInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AccountInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AccountInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                User user = new User();
                new AccountInfo(user).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JP_Main;
    private javax.swing.JPanel jp_Basic;
    private javax.swing.JPanel jp_Form;
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