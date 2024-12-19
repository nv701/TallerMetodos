package com.mycompany.metod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase PasswordRecovery para la recuperación de contraseñas de usuario.
 * Proporciona una interfaz gráfica para que los usuarios ingresen su información
 * y restablezcan su contraseña si los datos de seguridad son válidos.
 */
public class PasswordRecovery extends JFrame {

    /**
     * Constructor de PasswordRecovery.
     * 
     * @param parentFrame La ventana principal que se cerrará al abrir esta ventana.
     */
    public PasswordRecovery(JFrame parentFrame) {
        parentFrame.dispose();  // Cierra la ventana anterior

        setTitle("Recuperar Contraseña");
        setLayout(new GridLayout(7, 2, 5, 5));  // Diseño de la ventana con una cuadrícula
        setSize(450, 230);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(180, 235, 150)); // Fondo verde claro

        // Etiquetas y campos de texto para la entrada de datos del usuario
        add(new JLabel("NOMBRE COMPLETO:"));
        JTextField fullNameField = new JTextField();
        add(fullNameField);

        add(new JLabel("NOMBRE DE USUARIO:"));
        JTextField usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("CÓDIGO DE SEGURIDAD:"));
        JPasswordField securityCodeField = new JPasswordField();  // Campo de contraseña para mayor seguridad
        add(securityCodeField);

        add(new JLabel("NUEVA CONTRASEÑA:"));
        JPasswordField newPasswordField = new JPasswordField(); // Campo para la nueva contraseña
        add(newPasswordField);

        // Botón para restablecer la contraseña
        JButton resetPasswordButton = new JButton("Restablecer Contraseña");
        add(resetPasswordButton);

        // Botón para volver a la ventana de inicio de sesión
        JButton backButton = new JButton("Retroceder");
        add(backButton);

        // Acción del botón "Restablecer Contraseña"
        resetPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validar la seguridad usando el método de FileManager
                boolean valid = FileManager.validateSecurity(
                        fullNameField.getText(), 
                        usernameField.getText(), 
                        new String(securityCodeField.getPassword()));
                if (valid) {
                    // Restablecer la contraseña si los datos son válidos
                    boolean reset = FileManager.resetPassword(
                            usernameField.getText(), 
                            new String(newPasswordField.getPassword()));
                    if (reset) {
                        JOptionPane.showMessageDialog(PasswordRecovery.this, "Contraseña restablecida correctamente.");
                        dispose();  // Cerrar esta ventana
                        new Login();  // Volver a la ventana de inicio de sesión
                    } else {
                        JOptionPane.showMessageDialog(PasswordRecovery.this, "No se pudo restablecer la contraseña.");
                    }
                } else {
                    JOptionPane.showMessageDialog(PasswordRecovery.this, "Datos incorrectos o código de seguridad inválido.");
                }
            }
        });

        // Acción del botón "Retroceder"
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();  // Crear una nueva instancia de la ventana de inicio de sesión
                dispose();  // Cerrar esta ventana
            }
        });

        setVisible(true);  // Hacer visible la ventana
    }
}
