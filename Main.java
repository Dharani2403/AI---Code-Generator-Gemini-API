package com.dharani.aicodegenerator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GeminiClient client = new GeminiClient();

        System.out.println("🔍 Enter a topic to generate code for:");
        String topic = scanner.nextLine();

        String response = client.getExplanation(topic);
        System.out.println("🧠 AI Says:\n" + response);
    }
}