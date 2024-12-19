package com.mycompany.metod;

/**
 * Clase que representa una tarea asignada a un trabajador.
 * Contiene detalles como el trabajador asignado, la tarea, fecha, descripción, prioridad, 
 * fecha límite y el estado actual de la tarea.
 */
public class Task {

    // Atributos de la clase

    /** Nombre del trabajador asignado a la tarea. */
    private String worker;

    /** Nombre o título de la tarea. */
    private String task;

    /** Fecha en que se creó o asignó la tarea. */
    private String date;

    /** Descripción detallada de la tarea. */
    private String description;

    /** Prioridad de la tarea (e.g., Alta, Media, Baja). */
    private String priority;

    /** Fecha límite para completar la tarea. */
    private String deadline;

    /** Estado actual de la tarea (e.g., En proceso, Completada, Pendiente). */
    private String status; // NUEVO ATRIBUTO PARA EL ESTADO DE LA TAREA

    /**
     * Constructor principal para inicializar todos los atributos de la tarea.
     * @param worker Nombre del trabajador asignado.
     * @param task Nombre o título de la tarea.
     * @param description Descripción detallada de la tarea.
     * @param priority Prioridad de la tarea.
     * @param date Fecha en que se asignó la tarea.
     * @param deadline Fecha límite para completar la tarea.
     * @param status Estado actual de la tarea.
     */
    public Task(String worker, String task, String description, String priority, String date, String deadline, String status) {
        this.worker = worker;
        this.task = task;
        this.description = description;
        this.priority = priority;
        this.date = date;
        this.deadline = deadline;
        this.status = status; // Inicializar estado
    }

    /**
     * Constructor alternativo para tareas sin estado inicial definido.
     * Asigna "En proceso" como estado predeterminado.
     * @param worker Nombre del trabajador asignado.
     * @param task Nombre o título de la tarea.
     * @param description Descripción detallada de la tarea.
     * @param priority Prioridad de la tarea.
     * @param date Fecha en que se asignó la tarea.
     * @param deadline Fecha límite para completar la tarea.
     */
    public Task(String worker, String task, String description, String priority, String date, String deadline) {
        this(worker, task, description, priority, date, deadline, "En proceso"); // Estado predeterminado
    }

    // Métodos Getters y Setters

    /**
     * Obtiene el nombre del trabajador asignado.
     * @return Nombre del trabajador.
     */
    public String getWorker() {
        return worker;
    }

    /**
     * Establece el nombre del trabajador asignado.
     * @param worker Nombre del trabajador.
     */
    public void setWorker(String worker) {
        this.worker = worker;
    }

    /**
     * Obtiene el nombre o título de la tarea.
     * @return Nombre de la tarea.
     */
    public String getTask() {
        return task;
    }

    /**
     * Establece el nombre o título de la tarea.
     * @param task Nombre de la tarea.
     */
    public void setTask(String task) {
        this.task = task;
    }

    /**
     * Obtiene la fecha de asignación de la tarea.
     * @return Fecha de asignación.
     */
    public String getDate() {
        return date;
    }

    /**
     * Establece la fecha de asignación de la tarea.
     * @param date Fecha de asignación.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Obtiene la descripción detallada de la tarea.
     * @return Descripción de la tarea.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Establece la descripción detallada de la tarea.
     * @param description Descripción de la tarea.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtiene la prioridad de la tarea.
     * @return Prioridad de la tarea.
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Establece la prioridad de la tarea.
     * @param priority Prioridad de la tarea.
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Obtiene la fecha límite de la tarea.
     * @return Fecha límite.
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Establece la fecha límite de la tarea.
     * @param deadline Fecha límite.
     */
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    /**
     * Obtiene el estado actual de la tarea.
     * @return Estado de la tarea.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Establece el estado actual de la tarea.
     * @param status Estado de la tarea.
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
