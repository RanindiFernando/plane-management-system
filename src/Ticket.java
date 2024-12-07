import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private char row;
    private int seat;
    private double price;
    private Person person;


    public Ticket(char row, int seat, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = TicketPrice(row, seat);
        this.person = person;
    }

    public char getRow() {

        return row;
    }

    public void setRow(char row) {


        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {

        this.seat = seat;
    }

    public double getPrice() {

        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {

        return person;
    }

    public void setPerson(Person person) {

        this.person = person;
    }

    public void TicketInfo() {
        System.out.println("Ticket Information:");
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: £" + price);
        System.out.println("Personal Information:");
        person.PersonInfo();
    }


    private double TicketPrice(char row, int seat) {
        double TicketPrice;
        if (seat >= 1 && seat <= 5) {
            TicketPrice = 200;
        } else if (seat >= 6 && seat <= 9) {
            TicketPrice = 150;
        } else {
            TicketPrice = 180;
        }
        return TicketPrice;

    }

    public void save() {
        String filename = row + String.valueOf(seat) + ".txt";
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("Ticket Information:\n");
            writer.write("Row: " + row + "\n");
            writer.write("Seat: " + seat + "\n");
            writer.write("Price: £" + price + "\n");
            writer.write("Personal Information:\n");
            writer.write("Name: " + person.getName() + "\n");
            writer.write("Surname: " + person.getSurname() + "\n");
            writer.write("Email: " + person.getEmail() + "\n");
            writer.close();
            System.out.println("Ticket information saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the ticket information.");

        }
    }
}