package com.mycompany.metod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa un formulario para crear cuentas de administrador.
 * Extiende JFrame y proporciona una interfaz de usuario para ingresar detalles
 * como nombre, correo, usuario, contraseña y código de seguridad.
 */
public class AdminForm extends JFrame {

    /**
     * Constructor que inicializa el formulario de administrador y reemplaza el marco padre.
     * 
     * @param parentFrame El marco principal que se debe cerrar al abrir este formulario.
     */
    public AdminForm(JFrame parentFrame) {
        // Cierra el marco principal.
        parentFrame.dispose();

        // Configuración del marco de la ventana.
        setTitle("Formulario de Administrador");
        setLayout(new GridLayout(6, 2, 5, 5)); // Diseño de cuadrícula con 6 filas y 2 columnas.
        setSize(450, 230); // Dimensiones del marco.
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla.
        getContentPane().setBackground(new Color(180, 235, 150)); // Color de fondo.

        // Agregar campos y etiquetas al formulario.
        add(new JLabel("NOMBRE DE ADMINISTRADOR:"));
        JTextField adminNameField = new JTextField(); // Campo para el nombre del administrador.
        add(adminNameField);

        add(new JLabel("CORREO ELECTRONICO:"));
        JTextField emailField = new JTextField(); // Campo para el correo electrónico.
        add(emailField);

        add(new JLabel("USUARIO:"));
        JTextField userField = new JTextField(); // Campo para el nombre de usuario.
        add(userField);

        add(new JLabel("CONTRASEÑA:"));
        JPasswordField passwordField = new JPasswordField(); // Campo para la contraseña.
        add(passwordField);

        add(new JLabel("CODIGO DE SEGURIDAD:"));
        JTextField securityCodeField = new JTextField(); // Campo para el código de seguridad.
        add(securityCodeField);

        // Botón para crear una cuenta de administrador.
        JButton createAdminButton = new JButton("Crear Cuenta de Administrador");
        add(createAdminButton);

        // Acción del botón para guardar los detalles del administrador.
        createAdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamar al gestor de archivos para guardar los datos del usuario.
                FileManager.saveUser(
                    "Administrador", 
                    adminNameField.getText(), 
                    emailField.getText(),
                    userField.getText(), 
                    new String(passwordField.getPassword()), // Obtener la contraseña como texto.
                    securityCodeField.getText() // Obtener el código de seguridad.
                );

                // Mostrar mensaje de éxito y cerrar el formulario.
                JOptionPane.showMessageDialog(AdminForm.this, "Cuenta de Administrador creada");
                dispose();
            }
        });

        // Hacer visible el formulario.
        setVisible(true);
    }
}
