package com.dharani.aicodegenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ExplainerGUI extends JFrame {

    private final JTextField topicField;
    private final JTextArea resultArea;
    private final GeminiClient client;

    public ExplainerGUI() {
        setTitle("💻 AI Code Generator");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Monochrome palette
        Color bgColor = Color.decode("#1e1e1e");
        Color fgColor = Color.decode("#d4d4d4");
        Color accent = Color.decode("#3c3c3c");

        getContentPane().setBackground(bgColor);
        setLayout(new BorderLayout(10, 10));

        client = new GeminiClient();

        JLabel label = new JLabel("📝 Describe what code you want:");
        label.setForeground(fgColor);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));

        topicField = new JTextField(30);
        topicField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        topicField.setBackground(accent);
        topicField.setForeground(fgColor);
        topicField.setCaretColor(fgColor);
        topicField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JButton explainButton = new JButton("⚙️ Generate Code");
        JButton clearButton = new JButton("🧹 Clear");

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        explainButton.setFont(buttonFont);
        clearButton.setFont(buttonFont);
        explainButton.setBackground(accent);
        clearButton.setBackground(accent);
        explainButton.setForeground(fgColor);
        clearButton.setForeground(fgColor);

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setEditable(false);
        resultArea.setBackground(bgColor);
        resultArea.setForeground(fgColor);
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(accent));

        // Event actions
        explainButton.addActionListener(this::handleExplain);
        topicField.addActionListener(e -> handleExplain(null));
        clearButton.addActionListener(e -> resultArea.setText(""));

        // Layout
        JPanel topPanel = new JPanel();
        topPanel.setBackground(bgColor);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(label);
        topPanel.add(topicField);
        topPanel.add(explainButton);
        topPanel.add(clearButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void handleExplain(ActionEvent e) {
        String topic = topicField.getText().trim();
        if (topic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a topic!", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        resultArea.append("🟦 You asked: " + topic + "\n");
        resultArea.append("⏳ Generating code...\n");

        SwingUtilities.invokeLater(() -> {
            String response = client.getExplanation(topic);
            resultArea.append("🧠 AI Says:\n" + response + "\n\n");
        });

        topicField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExplainerGUI gui = new ExplainerGUI();
            gui.setVisible(true);
        });
    }
}
