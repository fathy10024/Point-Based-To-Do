import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main {

    static int yPosition = 160;

    public static void main(String[] args) {

        JFrame frame = new JFrame("TODO List");
        frame.setSize(1000, 700);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // ===== Background =====
        JPanel background = new JPanel();
        background.setLayout(null);
        background.setBackground(new Color(30, 30, 40));
        frame.setContentPane(background);

        // ===== Title =====
        JLabel title = new JLabel("TODO LIST", SwingConstants.CENTER);
        title.setBounds(350, 20, 300, 50);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        // ===== Task Input =====
        JLabel taskLabel = new JLabel("Task:");
        taskLabel.setBounds(50, 110, 100, 30);
        taskLabel.setForeground(Color.LIGHT_GRAY);

        JTextField tasks = new JTextField();
        tasks.setBounds(120, 110, 250, 30);

        // ===== Points Input =====
        JLabel pointsLabel = new JLabel("Points (0-5):");
        pointsLabel.setBounds(400, 110, 120, 30);
        pointsLabel.setForeground(Color.LIGHT_GRAY);

        JTextField points = new JTextField();
        points.setBounds(520, 110, 50, 30);

        // Allow only one digit 0-5
        points.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (points.getText().length() >= 1 || c < '0' || c > '5') {
                    e.consume();
                }
            }
        });

        // ===== Buttons =====
        JButton addButton = new JButton("Add");
        addButton.setBounds(600, 110, 100, 30);

        JButton doneButton = new JButton("Done");
        doneButton.setBounds(750, 110, 100, 30);

        // Styling Buttons
        addButton.setBackground(new Color(0, 122, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        doneButton.setBackground(new Color(46, 204, 113));
        doneButton.setForeground(Color.WHITE);
        doneButton.setFocusPainted(false);

        // ===== Lists =====
        ArrayList<Integer> totalPoints = new ArrayList<>();
        ArrayList<Integer> donePoints = new ArrayList<>();

        // ===== Add Action =====
        addButton.addActionListener(e -> {

            String taskText = tasks.getText();
            String pointText = points.getText();

            if (!taskText.isEmpty() && !pointText.isEmpty()) {

                int pointValue = Integer.parseInt(pointText);

                JCheckBox checkBox = new JCheckBox("   " + taskText + "   (" + pointValue + ")");
                checkBox.setBounds(80, yPosition, 800, 30);
                checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                checkBox.setBackground(new Color(30, 30, 40));
                checkBox.setForeground(Color.WHITE);
                checkBox.setFocusPainted(false);

                // When task checked/unchecked
                checkBox.addActionListener(ev -> {

                    if (checkBox.isSelected()) {
                        donePoints.add(pointValue);
                    } else {
                        donePoints.remove(Integer.valueOf(pointValue));
                    }

                });

                background.add(checkBox);

                yPosition += 40;

                totalPoints.add(pointValue);

                tasks.setText("");
                points.setText("");

                frame.repaint();
            }
        });

        // ===== Done Action =====
        doneButton.addActionListener(e -> {

            int totalSum = 0;
            int doneSum = 0;

            for (int n : totalPoints)
                totalSum += n;

            for (int n : donePoints)
                doneSum += n;

            double percentage = 0;

            if (totalSum > 0)
                percentage = (doneSum * 100.0) / totalSum;

            String message;

            if (percentage == 100) {
                message = "🏆 Perfect!\nYou completed everything!";
            } else if (percentage >= 75) {
                message = "🔥 Excellent!\nAlmost there!\nTake a short break!";
            } else if (percentage >= 50) {
                message = "💪 Good Job!\nKeep pushing forward!";
            } else if (percentage > 0) {
                message = "👍 Nice effort!\nYou can do better tomorrow!";
            } else {
                message = " No tasks completed.\nTomorrow is your last chance!";
            }

            JOptionPane.showMessageDialog(frame, message,
                    "Result",
                    JOptionPane.INFORMATION_MESSAGE);

            // frame.dispose(); 
        });

        // ===== Add Components =====
        background.add(title);
        background.add(taskLabel);
        background.add(tasks);
        background.add(pointsLabel);
        background.add(points);
        background.add(addButton);
        background.add(doneButton);

        frame.setVisible(true);
    }
}