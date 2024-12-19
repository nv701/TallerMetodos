package com.mycompany.metod;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa el formulario para crear una cuenta de trabajador.
 * Extiende JFrame para crear una ventana gráfica con campos para ingresar los datos del trabajador.
 * Permite ingresar el nombre, correo electrónico, usuario, contraseña y un código de seguridad.
 */
public class WorkerForm extends JFrame {

    /**
     * Constructor de la clase WorkerForm.
     * Inicializa el formulario con los campos necesarios para crear una cuenta de trabajador.
     * 
     * @param parentFrame El JFrame padre que será cerrado al abrir este formulario.
     */
    public WorkerForm(JFrame parentFrame) {
        parentFrame.dispose(); // Cierra el formulario padre al abrir este formulario.

        // Configuración de la ventana.
        setTitle("Formulario de Trabajador");
        setLayout(new GridLayout(6, 2, 5, 5)); // Configuración del diseño de la ventana (6 filas y 2 columnas).
        setSize(450, 230); // Establece el tamaño de la ventana.
        setLocationRelativeTo(null); // Centra la ventana en la pantalla.
        getContentPane().setBackground(new Color(180, 235, 150)); // Establece el color de fondo.

        // Agregar etiquetas y campos de texto para los datos del trabajador.
        add(new JLabel("NOMBRE DE TRABAJADOR:"));
        JTextField workerNameField = new JTextField(); // Campo de texto para el nombre del trabajador.
        add(workerNameField);

        add(new JLabel("CORREO ELECTRONICO:"));
        JTextField emailField = new JTextField(); // Campo de texto para el correo electrónico.
        add(emailField);

        add(new JLabel("USUARIO:"));
        JTextField userField = new JTextField(); // Campo de texto para el nombre de usuario.
        add(userField);

        add(new JLabel("CONTRASEÑA:"));
        JPasswordField passwordField = new JPasswordField(); // Campo para la contraseña.
        add(passwordField);

        add(new JLabel("CODIGO DE SEGURIDAD:"));
        JTextField securityCodeField = new JTextField(); // Campo para el código de seguridad.
        add(securityCodeField);

        // Botón para crear la cuenta de trabajador.
        JButton createWorkerButton = new JButton("Crear Cuenta Trabajador");
        add(createWorkerButton);

        // Acción cuando el usuario hace clic en el botón para crear la cuenta.
        createWorkerButton.addActionListener(new ActionListener() {
            /**
             * Método que se ejecuta cuando el usuario hace clic en el botón para crear la cuenta.
             * Guarda los datos del trabajador en un archivo y muestra un mensaje de éxito.
             * 
             * @param e El evento de acción generado por el botón.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamar al gestor de archivos para guardar los detalles del trabajador.
                FileManager.saveUser(
                    "Trabajador", 
                    workerNameField.getText(), 
                    emailField.getText(),
                    userField.getText(), 
                    new String(passwordField.getPassword()), // Obtener la contraseña como texto.
                    securityCodeField.getText() // Obtener el código de seguridad.
                );
                // Mostrar mensaje de éxito y cerrar el formulario.
                JOptionPane.showMessageDialog(WorkerForm.this, "Cuenta de Trabajador creada");
                dispose(); // Cerrar la ventana del formulario.
            }
        });

        setVisible(true); // Hacer visible el formulario.
    }
}
