// ProductCRUD.java
import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    static final String URL = "jdbc:mysql://localhost:3306/your_database";
    static final String USER = "root";
    static final String PASS = "your_password";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            conn.setAutoCommit(false);
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("\n1. Create Product");
                System.out.println("2. Read Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();
                sc.nextLine();

                try {
                    switch (choice) {
                        case 1:
                            System.out.print("Enter Product Name: ");
                            String name = sc.nextLine();
                            System.out.print("Enter Price: ");
                            double price = sc.nextDouble();
                            System.out.print("Enter Quantity: ");
                            int qty = sc.nextInt();
                            PreparedStatement ps1 = conn.prepareStatement("INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)");
                            ps1.setString(1, name);
                            ps1.setDouble(2, price);
                            ps1.setInt(3, qty);
                            ps1.executeUpdate();
                            conn.commit();
                            System.out.println("Product added.");
                            break;

                        case 2:
                            Statement stmt = conn.createStatement();
                            ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
                            while (rs.next()) {
                                System.out.printf("%d | %s | %.2f | %d%n", rs.getInt("ProductID"), rs.getString("ProductName"), rs.getDouble("Price"), rs.getInt("Quantity"));
                            }
                            break;

                        case 3:
                            System.out.print("Enter Product ID to update: ");
                            int pid = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Enter New Name: ");
                            String newName = sc.nextLine();
                            System.out.print("Enter New Price: ");
                            double newPrice = sc.nextDouble();
                            System.out.print("Enter New Quantity: ");
                            int newQty = sc.nextInt();
                            PreparedStatement ps2 = conn.prepareStatement("UPDATE Product SET ProductName=?, Price=?, Quantity=? WHERE ProductID=?");
                            ps2.setString(1, newName);
                            ps2.setDouble(2, newPrice);
                            ps2.setInt(3, newQty);
                            ps2.setInt(4, pid);
                            ps2.executeUpdate();
                            conn.commit();
                            System.out.println("Product updated.");
                            break;

                        case 4:
                            System.out.print("Enter Product ID to delete: ");
                            int delId = sc.nextInt();
                            PreparedStatement ps3 = conn.prepareStatement("DELETE FROM Product WHERE ProductID=?");
                            ps3.setInt(1, delId);
                            ps3.executeUpdate();
                            conn.commit();
                            System.out.println("Product deleted.");
                            break;

                        case 5:
                            System.out.println("Exiting...");
                            return;

                        default:
                            System.out.println("Invalid option.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    conn.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
