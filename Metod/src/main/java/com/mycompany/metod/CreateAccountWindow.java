package com.mycompany.metod;

/**
 * Clase que representa la ventana para la creación de cuentas.
 * Permite al usuario elegir entre crear una cuenta de Administrador o Trabajador.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccountWindow extends JFrame {

    /**
     * Constructor que inicializa y configura la ventana de creación de cuentas.
     * La ventana permite al usuario elegir entre crear una cuenta de Administrador
     * o de Trabajador.
     */
    public CreateAccountWindow() {
        // Configuración de la ventana principal
        setTitle("Crear Cuenta"); // Título de la ventana
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Disposición en formato vertical
        setSize(300, 180); // Dimensiones de la ventana
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        getContentPane().setBackground(new Color(180, 235, 150)); // Color de fondo de la ventana

        // Botón para crear cuenta como Administrador
        JButton btnAdmin = new JButton("Crear cuenta como Administrador");
        btnAdmin.setAlignmentX(Component.CENTER_ALIGNMENT); // Alinear el botón al centro

        // Botón para crear cuenta como Trabajador
        JButton btnWorker = new JButton("Crear cuenta como Trabajador");
        btnWorker.setAlignmentX(Component.CENTER_ALIGNMENT); // Alinear el botón al centro

        // Espaciado y adición de componentes
        add(Box.createVerticalStrut(30)); // Espaciado vertical inicial
        add(btnAdmin); // Agregar botón de Administrador
        add(Box.createVerticalStrut(20)); // Espaciado entre botones
        add(btnWorker); // Agregar botón de Trabajador

        // Acción para el botón de Administrador
        btnAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir formulario de Administrador y cerrar esta ventana
                new AdminForm(CreateAccountWindow.this);
            }
        });

        // Acción para el botón de Trabajador
        btnWorker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir formulario de Trabajador y cerrar esta ventana
                new WorkerForm(CreateAccountWindow.this);
            }
        });

        // Hacer visible la ventana
        setVisible(true);
    }
}
