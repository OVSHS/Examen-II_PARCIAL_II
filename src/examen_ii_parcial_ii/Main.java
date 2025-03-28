/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examen_ii_parcial_ii;

/**
 *
 * @author Mario
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main extends JFrame {
    private PSNUsers psnUsers;
    private JTextField userField;
    private JTextField gameField;
    private JTextField trophyField;
    private JComboBox<Trophy> comboTrophy;
    private JTextArea infoArea;
    private JButton agregarBtn;
    private JButton desactivarBtn;
    private JButton agregarTrofeoBtn;
    private JButton infoBtn;

    public Main() {
        super("GUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints Grid = new GridBagConstraints();
        Grid.insets = new Insets(5,5,5,5);
        Grid.fill = GridBagConstraints.HORIZONTAL;

        psnUsers = new PSNUsers();

        getContentPane().setBackground(new Color(40, 40, 40));

        Font gameFont = new Font("Impact", Font.PLAIN, 14);

        Grid.gridx = 0; 
        Grid.gridy = 0; 
        Grid.gridwidth = 3;
        Grid.gridwidth = 1;

        JLabel userLabel = crearLabel("Usuario:", gameFont);
        userField = crearTextField(gameFont);

        Grid.gridx = 0; 
        Grid.gridy = 1; 
        add(userLabel, Grid);
        Grid.gridx = 1; 
        Grid.gridwidth = 2;
        add(userField, Grid);
        Grid.gridwidth = 1;

        JLabel gameLabel = crearLabel("Juego:", gameFont);
        gameField = crearTextField(gameFont);

        Grid.gridx = 0; 
        Grid.gridy = 2;
        add(gameLabel, Grid);
        Grid.gridx = 1; 
        Grid.gridwidth = 2;
        add(gameField, Grid);
        Grid.gridwidth = 1;

        JLabel trophyLabel = crearLabel("Nombre Trofeo:", gameFont);
        trophyField = crearTextField(gameFont);

        Grid.gridx = 0; 
        Grid.gridy = 3; 
        add(trophyLabel, Grid);
        Grid.gridx = 1; 
        Grid.gridwidth = 2;
        add(trophyField, Grid);
        Grid.gridwidth = 1;

        JLabel tipoLabel = crearLabel("Tipo Trofeo:", gameFont);
        comboTrophy = new JComboBox<>(Trophy.values());
        comboTrophy.setFont(gameFont);
        comboTrophy.setBackground(new Color(80, 80, 80));
        comboTrophy.setForeground(Color.WHITE);

        Grid.gridx = 0; 
        Grid.gridy = 4; 
        add(tipoLabel, Grid);
        Grid.gridx = 1; 
        Grid.gridwidth = 2;
        add(comboTrophy, Grid);
        Grid.gridwidth = 1;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        buttonPanel.setBackground(new Color(40, 40, 40));

        agregarBtn = crearBoton("Agregar Usuario", gameFont, new Color(0,102,153));
        desactivarBtn = crearBoton("Desactivar Usuario", gameFont, new Color(153,0,0));
        agregarTrofeoBtn = crearBoton("Agregar Trofeo", gameFont, new Color(0,153,0));
        infoBtn = crearBoton("Info Usuario", gameFont, new Color(102,0,153));

        buttonPanel.add(agregarBtn);
        buttonPanel.add(desactivarBtn);
        buttonPanel.add(agregarTrofeoBtn);
        buttonPanel.add(infoBtn);

        Grid.gridx = 0; 
        Grid.gridy = 5; 
        Grid.gridwidth = 3;
        add(buttonPanel, Grid);
        Grid.gridwidth = 1;

        infoArea = new JTextArea(8, 50);
        infoArea.setEditable(false);
        infoArea.setFont(gameFont);
        infoArea.setBackground(new Color(30,30,30));
        infoArea.setForeground(new Color(220,220,220));
        JScrollPane scrollPane = new JScrollPane(infoArea,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Grid.gridx = 0; 
        Grid.gridy = 6; 
        Grid.gridwidth = 3;
        Grid.weighty = 1.0;
        Grid.fill = GridBagConstraints.BOTH;
        add(scrollPane, Grid);

        agregarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText().trim();
                psnUsers.addUser(user);
                infoArea.setText("Se agrego a: " + user);
            }
        });

        desactivarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText().trim();
                psnUsers.deactivateUser(user);
                infoArea.setText("Se desactivo: " + user);
            }
        });

        agregarTrofeoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText().trim();
                String juego = gameField.getText().trim();
                String nomTrofeo = trophyField.getText().trim();
                Trophy trofeo = (Trophy) comboTrophy.getSelectedItem();
                psnUsers.addTrophieTo(user, juego, nomTrofeo, trofeo);
                infoArea.setText("Trofeo agregado al usuario: " + user);
            }
        });

        infoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText().trim();
                String info = psnUsers.playerInfo(user);
                infoArea.setText(info);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JLabel crearLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(new Color(60,60,60));
        return label;
    }

    private JTextField crearTextField(Font font) {
        JTextField field = new JTextField(10);
        field.setFont(font);
        field.setBackground(new Color(80,80,80));
        field.setForeground(Color.WHITE);
        return field;
    }

    private JButton crearBoton(String text, Font font, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(font);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        return btn;
    }

    public static void main(String[] args) {
        new Main();
    }
}