package com.mycompany.metod;

import java.io.*;
import java.util.*;

/**
 * Clase que gestiona la lectura y escritura de usuarios y tareas en archivos de texto.
 * Proporciona métodos para guardar, cargar, validar y modificar usuarios y tareas.
 */
public class FileManager {

    /**
     * Guarda un nuevo usuario en el archivo "users.txt".
     * Los datos se almacenan en formato: rol|nombre|correo|usuario|contraseña|código de seguridad.
     * 
     * @param role El rol del usuario (ej. "Trabajador", "Administrador").
     * @param name El nombre completo del usuario.
     * @param email El correo electrónico del usuario.
     * @param username El nombre de usuario para iniciar sesión.
     * @param password La contraseña del usuario.
     * @param securityCode El código de seguridad para la recuperación de cuenta.
     */
    public static void saveUser(String role, String name, String email, String username, String password, String securityCode) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(role + "|" + name + "|" + email + "|" + username + "|" + password + "|" + securityCode);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga todos los usuarios desde el archivo "users.txt".
     * Cada usuario se representa como un array de String.
     * 
     * @return Una lista de arrays, donde cada array contiene los datos de un usuario.
     */
    public static List<String[]> loadUsers() {
        List<String[]> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(line.split("\\|"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Valida las credenciales de inicio de sesión (nombre de usuario y contraseña).
     * 
     * @param username El nombre de usuario.
     * @param password La contraseña del usuario.
     * @return true si las credenciales son correctas, false en caso contrario.
     */
    public static boolean validateLogin(String username, String password) {
        List<String[]> users = loadUsers();
        for (String[] user : users) {
            if (user.length >= 5) {
                String savedUsername = user[3];
                String savedPassword = user[4];
                if (savedUsername.equals(username) && savedPassword.equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Obtiene el rol de un usuario dado su nombre de usuario.
     * 
     * @param username El nombre de usuario.
     * @return El rol del usuario, o null si no se encuentra el usuario.
     */
    public static String getUserRole(String username) {
        List<String[]> users = loadUsers();
        for (String[] user : users) {
            if (user.length >= 4) {
                String savedUsername = user[3];
                if (savedUsername.equals(username)) {
                    return user[0];
                }
            }
        }
        return null;
    }

    /**
     * Restablece la contraseña de un usuario específico.
     * 
     * @param username El nombre de usuario cuya contraseña se va a restablecer.
     * @param newPassword La nueva contraseña para el usuario.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean resetPassword(String username, String newPassword) {
        List<String[]> users = loadUsers();
        boolean userFound = false;

        for (String[] user : users) {
            if (user.length >= 5 && user[3].equals(username)) {
                user[4] = newPassword;
                userFound = true;
                break;
            }
        }

        if (userFound) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))) {
                for (String[] user : users) {
                    writer.write(String.join("|", user));
                    writer.newLine();
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Valida los datos de seguridad para recuperar una contraseña.
     * 
     * @param fullName El nombre completo del usuario.
     * @param username El nombre de usuario.
     * @param securityCode El código de seguridad proporcionado por el usuario.
     * @return true si los datos coinciden, false en caso contrario.
     */
    public static boolean validateSecurity(String fullName, String username, String securityCode) {
        List<String[]> users = loadUsers();
        for (String[] user : users) {
            if (user.length >= 6) {
                String savedFullName = user[1];
                String savedUsername = user[3];
                String savedSecurityCode = user[5];
                if (savedFullName.equals(fullName) && savedUsername.equals(username) && savedSecurityCode.equals(securityCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Guarda una tarea en el archivo "tasks.txt".
     * 
     * @param task El objeto Task que contiene los detalles de la tarea.
     */
    public static void saveTask(Task task) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt", true))) {
            writer.write(task.getWorker() + "|" +
                         task.getTask() + "|" +
                         task.getDescription() + "|" +
                         task.getPriority() + "|" +
                         task.getDate() + "|" +
                         task.getDeadline() + "|" +
                         task.getStatus());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga las tareas desde el archivo "tasks.txt" según el rol del usuario.
     * 
     * @param username El nombre de usuario para verificar las tareas asignadas.
     * @param userRole El rol del usuario, usado para determinar si el usuario puede ver todas las tareas.
     * @return Una lista de objetos Task que corresponden al usuario.
     */
    public static List<Task> loadTasks(String username, String userRole) {
        List<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7) {
                    String worker = parts[0];
                    String taskName = parts[1];
                    String description = parts[2];
                    String priority = parts[3];
                    String date = parts[4];
                    String deadline = parts[5];
                    String status = parts[6];

                    if (userRole.equals("Administrador") || worker.equals(username)) {
                        tasks.add(new Task(worker, taskName, description, priority, date, deadline, status));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * Elimina una tarea del archivo "tasks.txt" según el trabajador y el nombre de la tarea.
     * 
     * @param worker El nombre del trabajador asignado a la tarea.
     * @param taskName El nombre de la tarea a eliminar.
     * @return true si la tarea fue eliminada, false en caso contrario.
     */
    public static boolean deleteTask(String worker, String taskName) {
        List<Task> tasks = loadTasks(worker, "Administrador"); // Cargar todas las tareas
        boolean taskFound = false;

        // Filtra las tareas eliminando la que se desea borrar
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getWorker().equals(worker) && task.getTask().equals(taskName)) {
                taskFound = true;
            } else {
                filteredTasks.add(task);
            }
        }

        // Reescribe el archivo si se encontró y eliminó la tarea
        if (taskFound) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
                for (Task task : filteredTasks) {
                    writer.write(task.getWorker() + "|" +
                                 task.getTask() + "|" +
                                 task.getDescription() + "|" +
                                 task.getPriority() + "|" +
                                 task.getDate() + "|" +
                                 task.getDeadline() + "|" +
                                 task.getStatus());
                    writer.newLine();
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
