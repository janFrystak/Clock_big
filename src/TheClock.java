import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class TheClock extends JFrame {
    private JTextField textField1;
    private JPanel panel;
    private JButton resetButton;
    private JButton addButton;
    private boolean color = true;
    private boolean toRepeat = true;
    private ArrayList<LocalTime> timeTable = new ArrayList<>(Arrays.asList(
            LocalTime.of(7, 45),  // 7:45
            LocalTime.of(8, 40),  // 8:40
            LocalTime.of(9, 30),  // 9:30
            LocalTime.of(10, 35), // 10:35
            LocalTime.of(11, 30), // 11:30
            LocalTime.of(12, 25), // 12:25
            LocalTime.of(13, 20), // 13:20
            LocalTime.of(14, 10), // 14:10
            LocalTime.of(15, 0),
            LocalTime.of(22,25)
    ));
    private LocalTime displayedTime;

    //private int repeatFreq = 1;

    /*
* 1.More imaginatime
* 2.Makes you do better backflips
* No firearm!!
* 4.you can draw
* friend :)
*
*
* he kicked her in the head with big soccer shoes :(
* */





    public TheClock() {

        displayedTime = getClosestTime();
        setContentPane(panel);
        setVisible(true);
        setTitle("TIME");
        setSize(875, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        textField1.setEditable(false);
        textField1.setPreferredSize(new Dimension(500, 100)); // Width: 300px, Height: 50px
        textField1.setHorizontalAlignment(SwingConstants.CENTER);
        textField1.setFont(new Font("Arial", Font.PLAIN, 75));
        resetButton.addActionListener(e -> {
            displayedTime = getClosestTime();
            panel.setBackground(Color.WHITE);
            textField1.setBackground(Color.WHITE);
            textField1.setForeground(Color.WHITE);

        });
        addButton.addActionListener(e -> showTimeInput());
    }

    public LocalTime getClosestTime(){


        LocalTime currTime = LocalTime.now();
        for(LocalTime time : timeTable){
            if(time.isAfter(currTime)){
                return time;
            }
        }
        return null;
    }
    public void displayTime(){



        if (displayedTime != null) {
            Duration duration = Duration.between(LocalTime.now(),displayedTime);
            //long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60; // Remainder of minutes after hours
            long seconds = duration.getSeconds() % 60; // Remainder of seconds after minutes


            if(seconds >= 0) {
                textField1.setText(convertTime(minutes) + ":" + convertTime(seconds));
            }
            else {
                textField1.setText("GO GO GO");
                Blik(textField1, new Color(238, 130, 238), Color.black);

                //repeatFreq = 500;
            }
        }
        else {

            textField1.setText("GO TO SLEEP WEIRDO");

        }
    }
    public String convertTime(long time){
        if(time <10){
            return "0"+time;
        }
        else return String.valueOf(time);

    }
    public void Blik(JTextField field, Color color1, Color color2){

            if (this.color) {
                panel.setBackground(color1);
                field.setBackground(color1);
                field.setForeground(color2);
            } else {
                panel.setBackground(color2);
                field.setBackground(color2);
                field.setForeground(color1);
            }
            this.color = !this.color;

    }
    public void showTimeInput(){

        // Create a new JDialog (popup window)
        JDialog dialog = new JDialog((Frame) null, "Enter Time", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new FlowLayout());

        // Create a label and text field for time input
        JLabel label = new JLabel("Enter Time (HH:MM):");
        JTextField timeField = new JTextField(10);

        // Create a submit button
        JButton submitButton = new JButton("Submit");

        // Add an action listener to handle the input when the submit button is clicked
        submitButton.addActionListener(e -> {
                String enteredTime = timeField.getText();
                // Validate or process the entered time here
                //return enteredTime
                if(isValidTime(enteredTime)){
                    timeTable.add(LocalTime.of(Integer.parseInt(enteredTime.substring(0,1)),Integer.parseInt(enteredTime.substring(4,5))));
                }
                else System.out.println("Bad Time " + enteredTime);


                // Close the popup window
                dialog.dispose();

        });

        // Add components to the dialog
        dialog.add(label);
        dialog.add(timeField);
        dialog.add(submitButton);

        // Show the dialog in the center of the screen
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    public boolean isValidTime(String time){
        // Check if the input has exactly 5 characters (HH:MM)
        if (time == null || time.length() != 5) {
            return false;
        }

        // Check if the third character is a colon ':'
        if (time.charAt(2) != ':') {
            return false;
        }

        try {
            // Parse the hours and minutes
            int hours = Integer.parseInt(time.substring(0, 2));
            int minutes = Integer.parseInt(time.substring(3, 5));

            // Validate the range for hours (0-23) and minutes (0-59)
            if (hours < 0 || hours > 23  || minutes < 0 || minutes > 59) {
                return false;
            }

        } catch (NumberFormatException e) {
            // Catch any parsing errors (non-numeric values in hours or minutes)
            return false;
        }

        // If all checks pass, the input is valid
        return true;
    }


}
