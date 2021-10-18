import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ToDoList {
    public static final String ADD = "add";
    public static final String PRINT = "print";
    public static final String TOGGLE = "toggle";
    public static final String QUIT = "quit";

    public static void main(String[] args) throws IOException {
        String task = "";
        boolean completed = false;
        while (true){
            System.out.print("Ввод команды: ");
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String[] command = console.readLine().trim().split(" ");
            if (command[0].equals("")){
                System.out.println("Отсутствует команда");
                continue;
            }
            switch (command[0]){
                case ADD:
                    StringBuilder sbTask = new StringBuilder();
                    completed = false;
                    for (int i = 1; i < command.length; i++){
                        sbTask.append(command[i]).append(" ");
                    }
                    task = sbTask.toString().trim();
                    break;
                case PRINT:
                    if (command.length == 2 && command[1].equals("all")){
                        if (task.equals("")) {
                            System.out.println("Задачи отсутствуют");
                            break;
                        }
                        if(completed){
                            System.out.println("1. [X] " + task);
                        } else {
                            System.out.println("1. [] " + task);
                        }
                    } else if (command.length == 1){
                        if (task.equals("") || completed) {
                            System.out.println("Задачи отсутствуют");
                            break;
                        } else {
                            System.out.println("Невыполненные задачи:");
                            System.out.println("1. [] " + task);
                        }
                    } else {
                        System.out.println("Неверные агрументы команды 'print'");
                    }
                    break;
                case TOGGLE:
                    if (command.length != 2){
                        System.out.println("Неверные агрументы команды 'toggle'");
                    }
                    int id;
                    try {
                        id = Integer.parseInt(command[1]);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Введенное id не является числом");
                    }
                    completed = !completed;
                    break;
                case QUIT:
                    return;
                default:
                    System.out.println("Введена некорректная команда");
                    break;
            }
        }
    }
}
