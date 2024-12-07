import java.util.Scanner;
import java.io.File;

// Main class for PlaneManagement
public class PlaneManagement {
    // Arrays to represent seats in different rows
    private static final boolean[] seats_A = new boolean[14];
    private static final boolean[] seats_B = new boolean[12];
    private static final boolean[] seats_C = new boolean[12];
    private static final boolean[] seats_D = new boolean[14];

    // Array to store sold tickets
    private static final Ticket[] tickets = new Ticket[52];
    private static int ticketsCount = 0; // to keep track of the number of tickets sold

    /* Main method of the PlaneManagement class.
       It is the entry point of the application. */
    public static void main(String[] args) {
        System.out.println("Welcome to the Plane Management application");
        Scanner scanner = new Scanner(System.in);


        int option;
        // Main Menu loop
        do {
            printMenu();
            System.out.println("Please select an option:");
            try {
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        buySeat(scanner);
                        break;
                    case 2:
                        cancelSeat(scanner);
                        break;
                    case 3:
                        findFirstAvailableSeat();
                        break;
                    case 4:
                        show_seating_plan();
                        break;
                    case 5:
                        print_tickets_info();
                        break;
                    case 6:
                        search_ticket(scanner);
                        break;
                    case 0:
                        System.out.println("Exiting the program. Have a great day!");
                        break;
                    default:
                        System.out.println("Invalid option. Please select again.");
                        break;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input type. Please enter a number.");
                scanner.next(); // Clear the invalid input
                option = -1; // Set option to -1 to force the loop to repeat
            }


        } while (option != 0); // Continue loop until user selects 0
    }

    // Method to print Menu options
    public static void printMenu() {
        for (int i = 0; i < 50; i++) {
            System.out.print("*");
        }
        System.out.println();
        System.out.println("*                MENU OPTIONS                    *");
        for (int i = 0; i < 50; i++) {
            System.out.print("*");
        }
        System.out.println();
        System.out.println("1) Buy a seat");
        System.out.println("2) Cancel a seat");
        System.out.println("3) Find first available seat");
        System.out.println("4) Show seating plan");
        System.out.println("5) Print tickets information and total sales");
        System.out.println("6) Search ticket");
        System.out.println("0) Quit");
        for (int i = 0; i < 50; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    /* Method to buy a seat.
       Accepts a Scanner object as a parameter to receive user input.*/
    public static void buySeat(Scanner scanner) {
        while (true) {
            // Prompt user to input row letter
            System.out.println("Enter row letter (A, B, C, D):");
            char rowLetter = scanner.next().toUpperCase().charAt(0);
            // Find the array corresponding to the selected row
            boolean[] selectedRowSeats;
            switch (rowLetter) {
                case 'A':
                    selectedRowSeats = seats_A;
                    break;
                case 'B':
                    selectedRowSeats = seats_B;
                    break;
                case 'C':
                    selectedRowSeats = seats_C;
                    break;
                case 'D':
                    selectedRowSeats = seats_D;
                    break;
                default:
                    System.out.println("Invalid row letter. Please select again.");
                    continue;   // Move to the next iteration of the loop
            }
            int seatNumber;
            while (true) {
                // Prompt user to input seat number
                System.out.println("Enter seat number (1-" + selectedRowSeats.length + "):");
                seatNumber = scanner.nextInt();
                scanner.nextLine();
                int seatIndex = seatNumber - 1; // Convert seat number to array index

                // Validate seat number
                if (seatIndex < 0 || seatIndex >= selectedRowSeats.length) {
                    System.out.println("Invalid seat number. Please select again.");
                } else {
                    break; // Exit the loop if valid row letter is entered
                }
            }


            // Check if the selected seat is available
            if (selectedRowSeats[seatNumber-1]) {
                System.out.println("Seat " + rowLetter + seatNumber + " is already taken.");
            } else {
                // Input information for the Person object
                System.out.println("Enter name:");
                String name = scanner.nextLine();

                System.out.println("Enter surname:");
                String surname = scanner.nextLine();

                System.out.println("Enter email:");
                String email = scanner.nextLine();

                // Create a Person object with the input information
                Person person = new Person(name, surname, email);

                // Create a Ticket object with the input information
                Ticket ticket = new Ticket(rowLetter, seatNumber, person);

                // Add the ticket to the array of tickets
                tickets[ticketsCount] = ticket;
                ticketsCount++;

                // Mark the selected seat as sold
                selectedRowSeats[seatNumber-1] = true;
                // Save the ticket information to a file
                ticket.save();
                System.out.println("Seat " + rowLetter + seatNumber + " purchased successfully.");
                break;
            }
        }
    }

    /* Method to cancel a previously purchased seat.
       Accepts a Scanner object as a parameter to receive user input.  */
    public static void cancelSeat(Scanner scanner) {
        while (true) {
            // Prompt user to input row letter
            System.out.println("Enter row letter (A, B, C, D):");
            char rowLetter = scanner.next().toUpperCase().charAt(0);

            // Determine the array corresponding to the selected row
            boolean[] selectedRowSeats;
            switch (rowLetter) {
                case 'A':
                    selectedRowSeats = seats_A;
                    break;
                case 'B':
                    selectedRowSeats = seats_B;
                    break;
                case 'C':
                    selectedRowSeats = seats_C;
                    break;
                case 'D':
                    selectedRowSeats = seats_D;
                    break;
                default:
                    System.out.println("Invalid row letter. Please select again.");
                    continue;
            }

            int seatNumber;
            while (true) {
                // Prompt user to input seat number
                System.out.println("Enter seat number (1-" + selectedRowSeats.length + "):");
                seatNumber = scanner.nextInt();
                int seatIndex = seatNumber - 1; // Convert seat number to array index

                // Validate seat number
                if (seatIndex < 0 || seatIndex >= selectedRowSeats.length) {
                    System.out.println("Invalid seat number. Please select again.");
                } else {
                    break;
                }

            }

            // Check if the selected seat is already available
            if (!selectedRowSeats[seatNumber - 1]) {
                System.out.println("Seat cancellation failed. The seat " + rowLetter + seatNumber + " has not been purchased yet.");
            } else {
                // Mark the selected seat as available
                selectedRowSeats[seatNumber - 1] = false;
                System.out.println("Seat " + rowLetter + seatNumber + " canceled successfully.");

                // Delete the corresponding text file
                String filename = rowLetter + String.valueOf(seatNumber) + ".txt";
                File file = new File(filename);
                if (file.exists() && file.delete()) {
                    System.out.println("Text file " + filename + " associated with the canceled seat has been deleted.");
                } else {
                    System.out.println("Failed to delete text file " + filename + ".");
                }
                // Remove the corresponding ticket from the array of tickets
                for (int i = 0; i < ticketsCount; i++) {
                    if (tickets[i].getRow() == rowLetter && tickets[i].getSeat() == seatNumber) {
                        // Shift tickets array to remove the canceled ticket
                        for (int j = i; j < ticketsCount - 1; j++) {
                            tickets[j] = tickets[j + 1];
                        }
                        ticketsCount--;
                        break;// breaks the inner loop once the canceled ticket has been found and removed from the tickets array.
                    }
                }
                break;// breaks the outer loop after  canceling the seat reservation.
            }
        }
    }

    // Method to find the first available seat.
    public static void findFirstAvailableSeat() {
        boolean found = false;
        for (int i = 0; i < seats_A.length; i++) {
            if (!seats_A[i]) {
                System.out.println("First available seat: A" + (i + 1));
                found = true;
                break;
            }
        }
        if (!found) {
            for (int i = 0; i < seats_B.length; i++) {
                if (!seats_B[i]) {
                    System.out.println("First available seat: B" + (i + 1));
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            for (int i = 0; i < seats_C.length; i++) {
                if (!seats_C[i]) {
                    System.out.println("First available seat: C" + (i + 1));
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            for (int i = 0; i < seats_D.length; i++) {
                if (!seats_D[i]) {
                    System.out.println("First available seat: D" + (i + 1));
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            System.out.println("No available seats.");
        }
    }
    // Method to print the seating plan
    public static void show_seating_plan() {
        System.out.println("Seating Plan:");
        for (boolean seat : seats_A) {
            if (seat) {
                System.out.print("X ");
            } else {
                System.out.print("O ");
            }
        }
        System.out.println();
        for (boolean seat : seats_B) {

            if (seat) {
                System.out.print("X ");
            } else {
                System.out.print("O ");
            }
        }
        System.out.println();
        for (boolean seat : seats_C) {
            if (seat) {
                System.out.print("X ");
            } else {
                System.out.print("O ");
            }
        }
        System.out.println();
        for (boolean seat : seats_D) {

            if (seat) {
                System.out.print("X ");
            } else {
                System.out.print("O ");
            }
        }
        System.out.println();
    }

    //Method to print information about sold tickets and total sales.
    public static void print_tickets_info() {
        double totalAmount = 0;
        System.out.println("Tickets Information:");
        for (Ticket ticket : tickets) {
            if (ticket != null) {
                System.out.println("Name : " + ticket.getPerson().getName());
                System.out.println("Surname : " + ticket.getPerson().getSurname());
                System.out.println("Email : " + ticket.getPerson().getEmail());
                System.out.println("Row : " + ticket.getRow());
                System.out.println("Seat : " + ticket.getSeat());
                System.out.println("Price : £" + ticket.getPrice());
                totalAmount += ticket.getPrice();

            }
        }
        System.out.println("Total Amount: £" + totalAmount);
    }
    /* Method to search for a ticket based on user input row and seat number.
       Accepts a Scanner object as a parameter to receive user input. */
    public static void search_ticket(Scanner scanner) {
        // Prompt user to input row letter
        System.out.println("Enter row letter (A, B, C, D):");
        char rowLetter = scanner.next().toUpperCase().charAt(0);

        // Determine the array corresponding to the selected row
        boolean[] selectedRowSeats;
        switch (rowLetter) {
            case 'A':
                selectedRowSeats = seats_A;
                break;
            case 'B':
                selectedRowSeats = seats_B;
                break;
            case 'C':
                selectedRowSeats = seats_C;
                break;
            case 'D':
                selectedRowSeats = seats_D;
                break;
            default:
                System.out.println("Invalid row letter. Please select again.");
                return; // Return without further processing
        }

        // Prompt user to input seat number
        System.out.println("Enter seat number (1-" + selectedRowSeats.length + "):");
        int seatNumber = scanner.nextInt();
        int seatIndex = seatNumber - 1; // Convert seat number to array index

        // Validate seat number
        if (seatIndex < 0 || seatIndex >= selectedRowSeats.length) {
            System.out.println("Invalid seat number. Please select again.");
            return; // Return without further processing
        }

        // Check if the selected seat is sold
        if (selectedRowSeats[seatIndex]) {
            // Search for the ticket in the soldTickets ArrayList
            for (Ticket ticket : tickets) {
                if (ticket != null && ticket.getRow() == rowLetter && ticket.getSeat() == seatNumber) {
                    System.out.println("Ticket Information:");
                    System.out.println(ticket);
                    return; // Exit the method
                }
            }
        }

        // If the seat is not sold or not found display a message
        System.out.println("This seat is available.");
    }
}


