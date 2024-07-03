package secureflow;

import secureflow.auth.UserAuth;
import secureflow.network.PeerDiscovery;
import secureflow.transfer.FileTransfer;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // Load environment variables
        Dotenv dotenv = Dotenv.load();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the P2P File Sharing Network");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 1) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                if (UserAuth.register(username, password)) {
                    System.out.println("Registration successful!");
                } else {
                    System.out.println("Registration failed.");
                }
            } else if (choice == 2) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                if (UserAuth.login(username, password)) {
                    System.out.println("Login successful!");
                    break;
                } else {
                    System.out.println("Invalid credentials.");
                }
            } else if (choice == 3) {
                System.exit(0);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        // Start peer discovery
        Thread discoveryThread = new Thread(() -> {
            try {
                PeerDiscovery.listenForPeers();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        discoveryThread.start();

        // Broadcast presence
        PeerDiscovery.broadcastPresence();

        // File transfer options
        while (true) {
            System.out.println("1. Send File");
            System.out.println("2. Receive File");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 1) {
                System.out.print("Enter file path to send: ");
                String filePath = scanner.nextLine();
                System.out.print("Enter peer host: ");
                String host = scanner.nextLine();
                System.out.print("Enter port: ");
                int port = scanner.nextInt();
                scanner.nextLine(); // consume newline
                FileTransfer.sendFile(filePath, host, port);
            } else if (choice == 2) {
                System.out.print("Enter file path to save: ");
                String filePath = scanner.nextLine();
                System.out.print("Enter port: ");
                int port = scanner.nextInt();
                scanner.nextLine(); // consume newline
                FileTransfer.receiveFile(filePath, port);
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
