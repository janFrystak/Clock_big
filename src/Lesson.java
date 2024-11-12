import java.time.LocalTime;

public class Lesson {
    private LocalTime endTime;
    private int number;
    private int classroom;
    private String name;

    public Lesson(LocalTime time, int number, int classroom, String name) {
        this.endTime = time;
        this.number = number;
        this.classroom = classroom;
        this.name = name;
    }

    public Lesson(LocalTime time) {
        this.endTime = time;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getClassroom() {
        return classroom;
    }

    public void setClassroom(int classroom) {
        this.classroom = classroom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
