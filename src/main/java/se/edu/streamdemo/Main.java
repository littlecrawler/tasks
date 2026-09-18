package se.edu.streamdemo;

import se.edu.streamdemo.data.Datamanager;
import se.edu.streamdemo.task.Deadline;
import se.edu.streamdemo.task.Task;

import java.util.ArrayList;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Task manager (using streams)");

        Datamanager dataManager = new Datamanager("./data/data.txt");
        ArrayList<Task> tasksData = dataManager.loadData();

        System.out.println("Printing all data ...");
        printAllData(tasksData);

        System.out.println("Printing deadlines ...");
        printDeadlines(tasksData);

        System.out.println("Printing all data using streams...");
        printAllDataUsingStreams(tasksData);

        System.out.println("Printing deadlines using streams...");
        printDeadlinesUsingStreams(tasksData);

        System.out.println(
                "Total number of deadlines: "
                        + countDeadlines(tasksData));

        System.out.println(
                "Total number of deadlines (using streams): "
                        + countDeadlinesUsingStreams(tasksData));

        ArrayList<Task> filteredList = filterTasksByString(tasksData, "10");
        printAllData(filteredList);
    }

    private static int countDeadlines(ArrayList<Task> tasksData) {
        int count = 0;

        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                count++;
            }
        }

        return count;
    }

    private static long countDeadlinesUsingStreams(ArrayList<Task> tasks) {
        return tasks.stream()
                .filter(task -> task instanceof Deadline)
                .count();
    }

    public static void printAllData(ArrayList<Task> tasksData) {
        for (Task t : tasksData) {
            System.out.println(t);
        }
    }

    public static void printAllDataUsingStreams(ArrayList<Task> tasks) {
        tasks.stream()
                .forEach(System.out::println);
    }

    public static void printDeadlines(ArrayList<Task> tasksData) {
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                System.out.println(t);
            }
        }
    }

    public static void printDeadlinesUsingStreams(ArrayList<Task> tasks) {
        tasks.parallelStream()
                .filter(task -> task instanceof Deadline)
                .forEach(System.out::println);
    }

    public static ArrayList<Task> filterTasksByString(
            ArrayList<Task> tasks, String filterString) {

        ArrayList<Task> filteredList = new ArrayList<>(
                tasks.stream()
                        .filter(t -> t.getDescription().contains(filterString))
                        .collect(toList())
        );

        return filteredList;
    }
}