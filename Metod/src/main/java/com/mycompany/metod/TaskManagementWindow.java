package com.mycompany.metod;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

public class TaskManagementWindow extends JFrame {

    private JTable taskTable; // Tabla donde se mostrarán las tareas
    private DefaultTableModel tableModel; // Modelo de la tabla
    private String userRole; // Rol del usuario (Admin o Trabajador)
    private String username; // Nombre de usuario
    private JTextField searchField; // Campo de búsqueda para filtrar tareas
    private Map<String, Color> userColors; // Mapa que guarda los colores asociados a cada trabajador
    private static final String COLOR_FILE = "userColors.dat"; // Archivo donde se guardan los colores de los trabajadores

    // Constructor de la ventana principal
    public TaskManagementWindow(String username, String userRole) {
        this.username = username;
        this.userRole = userRole;
        this.userColors = loadUserColors(); // Carga los colores previamente guardados

        setTitle("Gestión de Tareas");
        setSize(950, 400); // Tamaño de la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al cerrar la ventana
        getContentPane().setBackground(new Color(173, 216, 230)); // Establece el color de fondo

        // Configura la fuente de la tabla
        Font garamondFont = new Font("Garamond", Font.PLAIN, 14);
        String[] columnNames = {"TRABAJADOR ASIGNADO", "TAREA", "DESCRIPCION", "PRIORIDAD", "FECHA", "FECHA LIMITE", "ESTADO"};
        tableModel = new DefaultTableModel(columnNames, 0); // Modelo de la tabla con los nombres de las columnas
        taskTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                String user = (String) getValueAt(row, 0); // Obtiene el trabajador asignado a la tarea
                c.setBackground(userColors.getOrDefault(user, Color.WHITE)); // Establece el color de fondo según el trabajador
                setUpRowColors(); // Ajusta los colores de las filas

                return c;
            }
        };
        taskTable.setFont(garamondFont); // Establece la fuente de la tabla

        // Panel que contiene la tabla
        JScrollPane scrollPane = new JScrollPane(taskTable);
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(new Color(180, 235, 150));
        textPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton assignTaskButton = new JButton("ASIGNAR TAREA");
        JButton modifyTaskButton = new JButton("MODIFICAR TAREA");
        JButton deleteTaskButton = new JButton("ELIMINAR TAREA");
        JButton logoutButton = new JButton("CERRAR SESIÓN");

        // Establece la fuente de los botones
        assignTaskButton.setFont(garamondFont);
        deleteTaskButton.setFont(garamondFont);
        logoutButton.setFont(garamondFont);
        modifyTaskButton.setFont(garamondFont);

        // Campo de búsqueda
        searchField = new JTextField(20);
        searchField.setFont(garamondFont);
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                String searchTerm = searchField.getText(); // Obtiene el término de búsqueda
                updateTable(searchTasks(searchTerm)); // Actualiza la tabla con los resultados de la búsqueda
            }
        });

        // Agrega los botones y el campo de búsqueda al panel de botones
        buttonPanel.add(new JLabel("Buscar:"));
        buttonPanel.add(searchField);
        buttonPanel.add(assignTaskButton);
        buttonPanel.add(modifyTaskButton);
        buttonPanel.add(deleteTaskButton);

        // Deshabilita algunos botones si el usuario es un "Trabajador"
        if (userRole.equals("Trabajador")) {
            assignTaskButton.setEnabled(true); // Permitido para tareas propias
            modifyTaskButton.setEnabled(true); // Solo puede modificar el estado de las tareas
            deleteTaskButton.setEnabled(false); // Deshabilitado para Trabajadores
        } else {
            assignTaskButton.setEnabled(true);
            modifyTaskButton.setEnabled(true);
            deleteTaskButton.setEnabled(true); // Administrador puede eliminar tareas
        }

        // Agrega el botón de cierre de sesión
        buttonPanel.add(logoutButton);
        buttonPanel.setBackground(new Color(180, 235, 150));

        // Agrega los paneles al marco
        add(textPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Cargar tareas desde el archivo
        List<Task> tasks = FileManager.loadTasks(username, userRole);
        assignColorsToUsers(tasks); // Asigna colores a los trabajadores
        updateTable(tasks); // Carga las tareas en la tabla

        // Acción para asignar tarea
        assignTaskButton.addActionListener(e -> {
            // Lógica para asignar una nueva tarea
            // Se abre un cuadro de diálogo para ingresar los detalles de la tarea
            // Se validan las fechas y se agrega la tarea al archivo
        });

        // Acción para modificar tarea
        modifyTaskButton.addActionListener(e -> {
            // Lógica para modificar una tarea seleccionada
            // Solo el administrador puede modificar todos los campos, el trabajador solo puede cambiar el estado
        });

        // Acción para eliminar tarea
        deleteTaskButton.addActionListener(e -> {
            // Lógica para eliminar una tarea seleccionada
            // Solo se puede eliminar si se ha seleccionado una tarea
        });

        // Acción para cerrar sesión
        logoutButton.addActionListener(e -> {
            new Login(); // Cierra la ventana de gestión de tareas y abre la ventana de login
            dispose(); // Cierra la ventana actual
        });

        setVisible(true); // Muestra la ventana
    }

    // Actualiza la tabla con una lista de tareas
    private void updateTable(List<Task> tasks) {
        tableModel.setRowCount(0); // Limpiar la tabla
        for (Task task : tasks) {
            tableModel.addRow(new Object[]{task.getWorker(), task.getTask(), task.getDescription(), task.getPriority(), task.getDate(), task.getDeadline(), task.getStatus()});
        }
    }

    // Realiza una búsqueda de tareas que contengan el término ingresado
    private List<Task> searchTasks(String searchTerm) {
        List<Task> tasks = FileManager.loadTasks(username, userRole);
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getTask().toLowerCase().contains(searchTerm.toLowerCase()) || task.getWorker().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    // Asigna colores a los trabajadores
    private void assignColorsToUsers(List<Task> tasks) {
        Random random = new Random();
        for (Task task : tasks) {
            userColors.putIfAbsent(task.getWorker(), new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        }
        saveUserColors(userColors); // Guarda los colores en el archivo
    }

    // Carga los colores de los trabajadores desde un archivo
    private Map<String, Color> loadUserColors() {
        Map<String, Color> colors = new HashMap<>();
        File file = new File(COLOR_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                colors = (Map<String, Color>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return colors;
    }

    // Guarda los colores de los trabajadores en un archivo
    private void saveUserColors(Map<String, Color> userColors) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COLOR_FILE))) {
            oos.writeObject(userColors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Actualiza el archivo de tareas con los cambios realizados
    private void updateTasksFile() {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String worker = (String) tableModel.getValueAt(i, 0);
            String task = (String) tableModel.getValueAt(i, 1);
            String description = (String) tableModel.getValueAt(i, 2);
            String priority = (String) tableModel.getValueAt(i, 3);
            String date = (String) tableModel.getValueAt(i, 4);
            String deadline = (String) tableModel.getValueAt(i, 5);
            String status = (String) tableModel.getValueAt(i, 6);

            tasks.add(new Task(worker, task, description, priority, date, deadline, status));
        }

        // Sobrescribe el archivo de tareas
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (Task task : tasks) {
                writer.write(task.getWorker() + "|" +
                             task.getTask() + "|" +
                             task.getDescription() + "|" +
                             task.getPriority() + "|" +
                             task.getDate() + "|" +
                             task.getDeadline() + "|" +
                             task.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Valida si una fecha es válida (hoy o posterior)
    private boolean isDateValid(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false); // No permite fechas inválidas como "30/02/2024"
            Date inputDate = dateFormat.parse(date);
            Date currentDate = new Date(); // Fecha actual
            return !inputDate.before(currentDate); // La fecha debe ser hoy o en el futuro
        } catch (Exception e) {
            return false; // Fecha no válida si ocurre una excepción
        }
    }

    // Genera colores aleatorios para las filas según el trabajador asignado
    private void setUpRowColors() {
        taskTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String worker = (String) table.getValueAt(row, 0); // Obtener el trabajador asignado
                c.setBackground(userColors.getOrDefault(worker, Color.WHITE)); // Cambiar color de fondo
                return c;
            }
        });
    }
}
