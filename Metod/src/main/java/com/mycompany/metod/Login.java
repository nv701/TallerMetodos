package com.mycompany.metod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la ventana de inicio de sesión para un Gestor de Tareas.
 * Incluye un formulario de usuario y contraseña, opciones para crear cuenta y recuperar contraseña,
 * así como validación de credenciales.
 */
public class Login extends JFrame {

    // Componentes principales de la interfaz
    private ImageIcon backgroundImage;
    private JLabel backgroundLabel;

    /**
     * Constructor de la clase Login.
     * Configura la ventana principal y los elementos visuales de la interfaz.
     */
    public Login() {
        // Configuración de la ventana principal
        setTitle("Gestor de Tareas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(850, 300);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setMinimumSize(new Dimension(850, 300));

        // Panel para la imagen de fondo
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setOpaque(false); // Hacer transparente el panel
        backgroundImage = new ImageIcon("C:\\Users\\CyrixComp\\Desktop\\2.jpeg"); // Ruta de la imagen de fondo
        backgroundLabel = new JLabel(new ImageIcon(backgroundImage.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH)));
        imagePanel.add(backgroundLabel, BorderLayout.CENTER);

        // Panel para los elementos del login
        JPanel loginPanel = new JPanel();
        loginPanel.setOpaque(false); // Hacer transparente el panel
        loginPanel.setLayout(new GridBagLayout());

        // Configuración de GridBagConstraints para organizar los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Título del formulario de inicio de sesión
        JLabel title = new JLabel("GESTOR DE TAREAS", JLabel.LEFT);
        title.setFont(new Font("Garamond", Font.BOLD, 36));
        title.setForeground(Color.BLACK);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(title, gbc);

        // Etiqueta y campo de texto para el usuario
        JLabel userLabel = new JLabel("USUARIO:");
        userLabel.setFont(new Font("Garamond", Font.PLAIN, 18));
        userLabel.setForeground(Color.BLACK);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(userLabel, gbc);

        JTextField userField = new JTextField();
        userField.setFont(new Font("Garamond", Font.PLAIN, 18));
        userField.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        loginPanel.add(userField, gbc);

        // Etiqueta y campo de texto para la contraseña
        JLabel passwordLabel = new JLabel("CONTRASEÑA:");
        passwordLabel.setFont(new Font("Garamond", Font.PLAIN, 18));
        passwordLabel.setForeground(Color.BLACK);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Garamond", Font.PLAIN, 18));
        passwordField.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginPanel.add(passwordField, gbc);

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
        JButton btnLogin = new JButton("Iniciar sesión");
        JButton btnCreateAccount = new JButton("Crear cuenta");
        JButton btnRecoverPassword = new JButton("Olvidé la contraseña");

        // Configuración de los botones
        btnLogin.setBackground(Color.WHITE);
        btnCreateAccount.setBackground(Color.WHITE);
        btnRecoverPassword.setBackground(Color.WHITE);

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCreateAccount);
        buttonPanel.add(btnRecoverPassword);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        loginPanel.add(buttonPanel, gbc);

        // Contenedor principal para organizar los paneles
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());

        // Panel para el fondo y los componentes de texto
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.setBackground(new Color(180, 235, 150)); // Color de fondo

        containerPanel.add(imagePanel, BorderLayout.WEST); // Imagen de fondo a la izquierda
        textPanel.add(loginPanel, BorderLayout.CENTER); // Panel de login en el centro
        containerPanel.add(textPanel, BorderLayout.CENTER);

        add(containerPanel);

        // Acción para el botón de iniciar sesión
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passwordField.getPassword());

                // Validar credenciales
                if (FileManager.validateLogin(username, password)) {
                    String role = FileManager.getUserRole(username);

                    // Abrir la ventana correspondiente según el rol
                    new TaskManagementWindow(username, role);
                    dispose(); // Cerrar la ventana actual
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Usuario o contraseña incorrectos.");
                }
            }
        });

        // Acción para el botón de crear cuenta
        btnCreateAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCreateAccountWindow();
            }
        });

        // Acción para el botón de recuperar contraseña
        btnRecoverPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PasswordRecovery(Login.this);
            }
        });

        setVisible(true);
    }

    /**
     * Abre la ventana de creación de cuenta.
     */
    private void openCreateAccountWindow() {
        new CreateAccountWindow();
    }

    /**
     * Método principal para ejecutar la aplicación.
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
