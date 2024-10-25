import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class TheClock extends JFrame {
    private JTextField textField1;
    private JPanel panel;
    private JButton resetButton;
    private JButton addButton;
    private JButton selectButton;
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
        textField1.setPreferredSize(new Dimension(500,300)); // Width: 300px, Height: 50px
        textField1.setHorizontalAlignment(SwingConstants.CENTER);
        textField1.setFont(new Font("Arial", Font.PLAIN, 75));
        resetButton.addActionListener(e -> {
            displayedTime = getClosestTime();
            panel.setBackground(new Color(255,255,255));
            textField1.setBackground(new Color(255,255,255));
            textField1.setForeground(new Color(0,0,0));

        });
        addButton.addActionListener(e -> showTimeInput());
        selectButton.addActionListener(e ->selectTimeInput());
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
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            long seconds = duration.getSeconds() % 60;


            if(seconds >= 0) {
                if(hours > 0){
                    textField1.setText(convertTime(hours) + ":" + convertTime(minutes) + ":" + convertTime(seconds));
                }
                else textField1.setText(convertTime(minutes) + ":" + convertTime(seconds));

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
    public void selectTimeInput(){
        JDialog dialog = new JDialog((Frame) null, "Enter Time", true);
        dialog.setSize(300, 500);
        dialog.setLayout(new FlowLayout());

        for(LocalTime time: timeTable){
            JButton button = new JButton(String.valueOf(time));
            dialog.add(button);
            button.addActionListener(e -> {
                displayedTime = time;
            });
        }
        JButton submitButton = new JButton("Done");
        submitButton.addActionListener(e -> dialog.dispose());
        dialog.add(submitButton);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

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
