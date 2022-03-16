package ru.snapgot.todolist.service;

public interface PersonalTaskService extends TaskService{
    boolean supported(String id);
}
