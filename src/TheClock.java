import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
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
    private ArrayList<LocalTime> timeTable = new ArrayList<>(Arrays.asList(
            LocalTime.of(7, 45),  // 7:45
            LocalTime.of(8, 40),  // 8:40
            LocalTime.of(9, 30),  // 9:30
            LocalTime.of(10, 35), // 10:35
            LocalTime.of(11, 30), // 11:30
            LocalTime.of(12, 25), // 12:25
            LocalTime.of(13, 20), // 13:20
            LocalTime.of(14, 10), // 14:10
            LocalTime.of(15, 0)
    ));
    private LocalTime displayedTime;


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
            textField1.setForeground(Color.BLACK);

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
                textField1.setText(roundToTwoDigits(minutesToDegrees(minutes)) + "°"+":" + secondsToDegrees(seconds)+"°");
            }
            else {
                textField1.setText("GO GO GO");
                Blik(textField1, new Color(238, 130, 238), Color.black);


            }
        }
        else {

            textField1.setText("GO TO SLEEP WEIRDO");

        }
    }
    public static String roundToTwoDigits(Double value) {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(value);
    }
    public double minutesToDegrees(long minutes) {
        // Conversion factor: 6 degrees per minute
        double degreesPerMinute = 6.0;
        // Convert minutes to degrees
        return  minutes * degreesPerMinute;
    }
    public double secondsToDegrees(long seconds) {
        // Conversion factor: 1/10 degrees per second
        double degreesPerSecond = 1.0 / 10.0;
        // Convert seconds to degrees
        return seconds * degreesPerSecond;
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

        JDialog dialog = new JDialog((Frame) null, "Enter Time", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter Time (HH:MM):");
        JTextField timeField = new JTextField(10);

        JButton submitButton = new JButton("Submit");


        submitButton.addActionListener(e -> {
                String enteredTime = timeField.getText();

                if(isValidTime(enteredTime)){
                    int h = Integer.parseInt(enteredTime.substring(0,1));
                    int m = Integer.parseInt(enteredTime.substring(4,5));
                    timeTable.add(LocalTime.of(h,m));
                }
                else {
                    System.out.println("Bad Time " + enteredTime);
                }


                dialog.dispose();

        });


        dialog.add(label);
        dialog.add(timeField);
        dialog.add(submitButton);


        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public boolean isValidTime(String time){

        if (time == null || time.length() != 5) {
            return false;
        }


        if (time.charAt(2) != ':') {
            return false;
        }

        try {
            // Parse the hours and minutes
            int hours = Integer.parseInt(time.substring(0, 2));
            int minutes = Integer.parseInt(time.substring(3, 5));


            if (hours < 0 || hours > 23  || minutes < 0 || minutes > 59) {
                return false;
            }

        } catch (NumberFormatException e) {

            return false;
        }


        return true;
    }


}
