import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ToDoList {
    public static final String ADD = "add";
    public static final String PRINT = "print";
    public static final String TOGGLE = "toggle";
    public static final String QUIT = "quit";
    static String task = "";
    static boolean completed = false;
    static int id = 1;

    public static void main(String[] args) throws IOException {
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
                    add(command);
                    break;
                case PRINT:
                    print(command);
                    break;
                case TOGGLE:
                    toggle(command);
                    break;
                case QUIT:
                    return;
                default:
                    System.out.println("Введена некорректная команда");
                    break;
            }
        }
    }

    private static void add(String[] command){
        if (command.length == 1){
            System.out.println("Не было ввода задачи");
            return;
        }
        StringBuilder sbTask = new StringBuilder();
        completed = false;
        for (int i = 1; i < command.length; i++){
            sbTask.append(command[i]).append(" ");
        }
        task = sbTask.toString().trim();
    }

    private static void print(String[] command){
        if (command.length == 2 && command[1].equals("all")){
            if (task.equals("")) {
                System.out.println("Задачи отсутствуют");
                return;
            }
            System.out.printf("%d. [%s] %s%n",id, completed ? "X" : " ", task);
        } else if (command.length == 1){
            if (task.equals("") || completed) {
                System.out.println("Задачи отсутствуют");
                return;
            }
            System.out.println("Невыполненные задачи:");
            System.out.printf("%d. [ ] %s%n", id, task);
        } else {
            System.out.println("Неверные агрументы команды 'print'");
        }
    }

    private static void toggle(String[] command){
        if (command.length != 2){
            System.out.println("Неверные агрументы команды 'toggle'");
            return;
        }
        int toggleId;
        try {
            toggleId = Integer.parseInt(command[1]);
            if (toggleId == id){
                completed = !completed;
            } else {
                System.out.println("Задачи с таким id не существует");
            }
        } catch (NumberFormatException e) {
            System.out.println("Введенное id не является числом");
        }
    }
}
