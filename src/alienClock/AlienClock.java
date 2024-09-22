package alienClock;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AlienClock {
    private static final int SECONDS_IN_ALIEN_MINUTE = 90;
    private static final int MINUTES_IN_ALIEN_HOUR = 90;
    private static final int HOURS_IN_ALIEN_DAY = 36;
    private static final int MONTHS_IN_ALIEN_YEAR = 18;
    private static final int[] DAYS_IN_ALIEN_MONTH = {44, 42, 48, 40, 48, 44, 40, 44, 42, 40, 40, 42, 44, 48, 42, 40, 44, 38};

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    private int alarmYear;
    private int alarmMonth;
    private int alarmDay;
    private int alarmHour;
    private int alarmMinute;
    private int alarmSecond;

    private Scanner scanner;

    // Stored Earth time
    private int earthYear;
    private int earthMonth;
    private int earthDay;
    private int earthHour;
    private int earthMinute;
    private int earthSecond;

    public AlienClock() {
        this.scanner = new Scanner(System.in);
        this.year = convertEarthYearToAlienYear(LocalDate.now().getYear());
        this.month = convertEarthMonthToAlienMonth(LocalDate.now().getMonthValue());
        this.day = convertEarthDayToAlienDay(LocalDate.now().getDayOfMonth(), month);
        this.hour = convertEarthHourToAlienHour(LocalTime.now().getHour());
        this.minute = convertEarthMinuteToAlienMinute(LocalTime.now().getMinute());
        this.second = convertEarthSecondToAlienSecond(LocalTime.now().getSecond());

        this.earthYear = LocalDate.now().getYear();
        this.earthMonth = LocalDate.now().getMonthValue();
        this.earthDay = LocalDate.now().getDayOfMonth();
        this.earthHour = LocalTime.now().getHour();
        this.earthMinute = LocalTime.now().getMinute();
        this.earthSecond = LocalTime.now().getSecond();
    }

    public void run() {
        while (true) {
            displayAlienClock();
            displayEarthClock();
            System.out.println("Enter 's' to set date and time, 'a' to set alarm, or 'q' to quit:");
            String input = scanner.next();
            if (input.equals("s")) {
                setDateAndTime();
            } else if (input.equals("a")) {
                setAlarm();
            } else if (input.equals("q")) {
                break;
            }
            updateAlienTime();
            checkAlarm();
        }
    }

    private void displayAlienClock() {
        System.out.println("Alien Clock:");
        System.out.println(String.format("%02d/%02d/%04d %02d:%02d:%02d", day, month, year, hour, minute, second));
        System.out.println();
    }

    private void displayEarthClock() {
        System.out.println("Earth Clock:");
        System.out.println(String.format("%02d/%02d/%04d %02d:%02d:%02d", earthDay, earthMonth, earthYear, earthHour, earthMinute, earthSecond));
        System.out.println();
    }

    private void setDateAndTime() {
        System.out.println("Enter year:");
        year = scanner.nextInt();
        System.out.println("Enter month:");
        month = scanner.nextInt();
        System.out.println("Enter day:");
        day = scanner.nextInt();
        System.out.println("Enter hour:");
        hour = scanner.nextInt();
        System.out.println("Enter minute:");
        minute = scanner.nextInt();
        System.out.println("Enter second:");
        second = scanner.nextInt();
        validateInput();

        
        earthYear = convertAlienYearToEarthYear(year);
        earthMonth = convertAlienMonthToEarthMonth(month);
        earthDay = convertAlienDayToEarthDay(day, month);
        earthHour = convertAlienHourToEarthHour(hour);
        earthMinute = convertAlienMinuteToEarthMinute(minute);
        earthSecond = convertAlienSecondToEarthSecond(second);
    }

    private void setAlarm() {
        System.out.println("Enter alarm year:");
        alarmYear = scanner.nextInt();
        System.out.println("Enter alarm month:");
        alarmMonth = scanner.nextInt();
        System.out.println("Enter alarm day:");
        alarmDay = scanner.nextInt();
        System.out.println("Enter alarm hour:");
        alarmHour = scanner.nextInt();
        System.out .println("Enter alarm minute:");
        alarmMinute = scanner.nextInt();
        System.out.println("Enter alarm second:");
        alarmSecond = scanner.nextInt();
        validateInput();
    }

    private void updateAlienTime() {
        second++;
        if (second >= SECONDS_IN_ALIEN_MINUTE) {
            second = 0;
            minute++;
        }
        if (minute >= MINUTES_IN_ALIEN_HOUR) {
            minute = 0;
            hour++;
        }
        if (hour >= HOURS_IN_ALIEN_DAY) {
            hour = 0;
            day++;
        }
        if (day > DAYS_IN_ALIEN_MONTH[month - 1]) {
            day = 1;
            month++;
        }
        if (month > MONTHS_IN_ALIEN_YEAR) {
            month = 1;
            year++;
        }
    }

    private void checkAlarm() {
        if (year == alarmYear && month == alarmMonth && day == alarmDay && hour == alarmHour && minute == alarmMinute && second == alarmSecond) {
            System.out.println("Alarm triggered!");
        }
    }

    private void validateInput() {
        if (year < 1 || month < 1 || month > MONTHS_IN_ALIEN_YEAR || day < 1 || day > DAYS_IN_ALIEN_MONTH[month - 1] || hour < 0 || hour >= HOURS_IN_ALIEN_DAY || minute < 0 || minute >= MINUTES_IN_ALIEN_HOUR || second < 0 || second >= SECONDS_IN_ALIEN_MINUTE) {
            System.out.println("Invalid input. Please try again.");
            setDateAndTime();
        }
    }

    private int convertEarthYearToAlienYear(int earthYear) {
        return earthYear / 2;
    }

    private int convertEarthMonthToAlienMonth(int earthMonth) {
        return (earthMonth + 1) / 2;
    }

    private int convertEarthDayToAlienDay(int earthDay, int alienMonth) {
        return (earthDay + 1) / 2;
    }

    private int convertEarthHourToAlienHour(int earthHour) {
        return earthHour / 2;
    }

    private int convertEarthMinuteToAlienMinute(int earthMinute) {
        return earthMinute / 2;
    }

    private int convertEarthSecondToAlienSecond(int earthSecond) {
        return earthSecond / 2;
    }

    private int convertAlienYearToEarthYear(int alienYear) {
        return alienYear * 2;
    }

    private int convertAlienMonthToEarthMonth(int alienMonth) {
        return (alienMonth - 1) * 2;
    }

    private int convertAlienDayToEarthDay(int alienDay, int alienMonth) {
        return (alienDay - 1) * 2;
    }

    private int convertAlienHourToEarthHour(int alienHour) {
        return alienHour * 2;
    }

    private int convertAlienMinuteToEarthMinute(int alienMinute) {
        return alienMinute * 2;
    }

    private int convertAlienSecondToEarthSecond(int alienSecond) {
        return alienSecond * 2;
    }

    public static void main(String[] args) {
        AlienClock clock = new AlienClock();
        clock.run();
    }
}