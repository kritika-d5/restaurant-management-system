import javax.swing.*;
import java.util.Vector;

class ReservationException extends Exception{
    int tableNumber;
    ReservationException(int table){
        tableNumber=table;
    }
    public String toString(){
        return "Table "+ tableNumber+ " is already reserved.";
    }
}
class Table {
    int tableNumber;
    boolean isReserved;
    Vector<String> orders = new Vector<>();
    Vector<Double> prices = new Vector<>();
    Vector<Staff> assignedStaff = new Vector<>();

    Table(int tableNumber) {                   //Constructor for Table Class
        this.tableNumber = tableNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void placeOrder(String item, double price) {
        orders.add(item);
        prices.add(price);
    }

    public void displayOrder(JTextArea textArea) {
        textArea.append("Table " + tableNumber + " (Reserved: " + (isReserved ? "Yes" : "No") + ")\n");
        if (orders.isEmpty()) {
            textArea.append("No orders placed yet.\n");
        } else {
            textArea.append("Orders:\n");
            for (int i = 0; i < orders.size(); i++) {
                textArea.append(orders.get(i) + " - ₹" + prices.get(i) + "\n");
            }
        }
    }

    public void clearTable() {
        orders.clear();
        prices.clear();
    }

    public double calculateTotalBill() {
        double sum=0;
        for(int i=0; i<prices.size();i++){
            sum=sum+prices.get(i);
        }

        return sum; 
    }

    public void makeReservation() {
        ReservationException error = new ReservationException(tableNumber);
        try{
        if (!isReserved) {
            isReserved = true;
            JOptionPane.showMessageDialog(null, "Table " + tableNumber + " has been reserved.", "Reservation", JOptionPane.INFORMATION_MESSAGE);
        } else {
            throw error;
            }
        }
        catch(ReservationException a){
            JOptionPane.showMessageDialog(null, a.toString(),"Reservation Error", JOptionPane.ERROR_MESSAGE);
            }
        
    }

    public void cancelReservation() {
        if (isReserved) {
            isReserved = false;
            JOptionPane.showMessageDialog(null, "Reservation for Table " + tableNumber + " has been cancelled.", "Cancellation", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Table " + tableNumber + " is not reserved.", "Cancellation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean assignStaff(Staff staff) {
        if (!assignedStaff.contains(staff)) {
            assignedStaff.add(staff);
            return true;
        }
        return false;
    }

    public Vector<Staff> getAssignedStaff() {
        return assignedStaff;
    }
}
