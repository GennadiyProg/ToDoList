package ru.snapgot.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ToDoList{
    public static void main(String[] args) {
        SpringApplication.run(ToDoList.class,  args);
    }
}

