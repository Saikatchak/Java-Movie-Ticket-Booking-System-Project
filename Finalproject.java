import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.*;

class User {
    private List<List<String>> users;

    public User() {
        users = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                if (fields.length >= 2) {
                    List<String> user = new ArrayList<>();
                    user.add(fields[0]);
                    user.add(fields[1]);
                    users.add(user);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading from file");
            e.printStackTrace();
        }
    }

    public void create_user(String username, String password) {
        List<String> user = new ArrayList<>();
        user.add(username);
        user.add(password);
        users.add(user);
        try {
            FileWriter writer = new FileWriter("users.txt", true);
            writer.write(username + "," + password + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }

    public boolean authenticate_user(String username, String password) {
        for (List<String> user : users) {
            if (user.get(0).equals(username) && user.get(1).equals(password)) {
                return true;
            }
        }
        return false;
    }
}

class Movie {
    private String[][] movieDetails;
    private int count;

    public Movie() {
        movieDetails = new String[100][4]; // Initialize with space for 100 movies
        count = 0;
    }

    public void addMovie() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter movie name: ");
        String name = scanner.nextLine();
        System.out.print("Enter theater name: ");
        String theater = scanner.nextLine();
        System.out.print("Enter number of tickets: ");
        int numTickets = scanner.nextInt();
        System.out.print("Enter cost per ticket: ");
        double costPerTicket = scanner.nextDouble();

        movieDetails[count][0] = name;
        movieDetails[count][1] = theater;
        movieDetails[count][2] = Integer.toString(numTickets);
        movieDetails[count][3] = Double.toString(costPerTicket);

        count++;

        // Write to file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("movie_details.txt", true));
            writer.write(name + "," + theater + "," + numTickets + "," + costPerTicket + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }

    public void displayMovies() {
        //System.out.println("Movie:\nTheater:\nTickets:\nCost per ticket:");
        for (int i = 0; i < count; i++) {
  System.out.println("Movie: " + movieDetails[i][0] + "\n" + "Theater: " + movieDetails[i][1] + "\n" +"Tickets: " + movieDetails[i][2] + "\n" +"Cost per ticket: $" +movieDetails[i][3]);
        }
    }
}
class Admin {
    private String username;
    private String password;
    private String filename;
    public Admin(String username, String password, String filename) 
    {
        this.username = username;
        this.password = password;
        this.filename = filename;
    }
    
    public boolean login(String username, String password) {
        return (username.equals(this.username) && password.equals(this.password));
    }
    
    public boolean updateRecord(String movieName, String theaterName, int numOfTickets, double ticketCost) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            List<String> lines = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts[0].equals(movieName) && parts[1].equals(theaterName)) {
                    line = movieName + "," + theaterName + "," + numOfTickets + "," + ticketCost;
                }
                lines.add(line);
            }
            scanner.close();
            FileWriter writer = new FileWriter(file);
            for (String line : lines) {
                writer.write(line + "\n");
            }
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteRecord(String movieName, String theaterName) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            List<String> lines = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (!parts[0].equals(movieName) || !parts[1].equals(theaterName)) {
                    lines.add(line);
                }
            }
            scanner.close();
            FileWriter writer = new FileWriter(file);
            for (String line : lines) {
                writer.write(line + "\n");
            }
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}


public class Finalproject {
    
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("...Enter Your Choice...\n1.User Page\n2.Movie booking\n3.Admin Page");
        int select=sc.nextInt();
        sc.nextLine(); 
        if(select==1){
            User user = new User();
            boolean c=true;
            while(c==true){
                System.out.println("Enter 1 to Create User and 2 to Login");
                int choice=sc.nextInt();
                sc.nextLine();
                if(choice==1)
                {
                    System.out.print("Enter Username: ");
                    String newname=sc.nextLine();
                    System.out.print("Enter Password: ");
                    String newpass=sc.nextLine();
                    user.create_user(newname, newpass);
                }
                else if(choice==2)
                {
			
                    System.out.print("Enter Username: ");
                    String uname=sc.nextLine();
                    System.out.print("Enter Password: ");
                    String pass=sc.nextLine();
                    // Authenticate a user
                    if (user.authenticate_user(uname, pass)) {
                        System.out.println("Authentication successful");
                    } else {
                        System.out.println("Authentication failed");
                    }
                }
                else{
                    System.out.println("wrong choice");
                }
                System.out.println("yould you like to continue? 1. Yes 2.No ");
                int con=sc.nextInt();
                if(con==1)
                {
                }
                else
                {
                    c=false;
                }
    
            }
        }
        else if(select==2){
            boolean c=true;
        while(c==true)
         {
            Movie movie = new Movie();
            movie.addMovie();
            movie.displayMovies();
            System.out.println("Yould you like to continue? 1. Yes 2. No");
            int con=sc.nextInt();
            if(con==1)
            {
            }
            else{
                c=false;
            }
         }
        }
        else if(select==3){
            Admin admin = new Admin("admin12", "a123", "movie_details.txt");
            System.out.print("Enter Username: ");
            String Username =sc.nextLine();
            System.out.print("Enter Password: ");
            String Password = sc.nextLine();
            if (admin.login("admin12", "a123")) {
                System.out.println("....Enter Your Choice....\n1.Update \n2.Delete ");
                int x1=sc.nextInt();
                sc.nextLine();
                if(x1==1){
                    System.out.print("Enter movie name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter theater name: ");
                    String theater = sc.nextLine();
                    System.out.print("Enter number of tickets: ");
                    int numTickets = sc.nextInt();
                    System.out.print("Enter cost per ticket: ");
                    double costPerTicket = sc.nextDouble();
                    if (admin.updateRecord(name, theater, numTickets, costPerTicket)) {
                        System.out.println("Record updated successfully");
                    } else {
                        System.out.println("Failed to update record");
                    }
                }
                else if(x1==2){
                    System.out.print("Enter movie name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter theater name: ");
                    String theater = sc.nextLine();
                    if (admin.deleteRecord(name,theater )) {
                        System.out.println("Record deleted successfully");
                    } else {
                        System.out.println("Failed to delete record");
                    }
                }
                else{
                    System.out.println("wrong choice");
                }
                
            } else {
                System.out.println("Invalid login credentials");
            }
            
        }
        else{
            System.out.println("wrong choice");
        }
    }
    
}