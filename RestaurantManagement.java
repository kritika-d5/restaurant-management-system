import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class RestaurantManagement extends JFrame implements ActionListener{
    JPanel top_panel,bottom_panel,bottom1,bottom2,bottom3,bottom4,middle_panel;
    JLabel lbl1,lbl2,lbl3,lbl4;
    JTextField itemField,priceField;
    JButton addOrderButton,clearTableButton,calculateBillButton,makeReservationButton ,cancelReservationButton ,addStaffButton,assignDutyButton,viewAssignmentsButton;
    JComboBox<Integer> tableSelector;
    JComboBox<String> staffSelector;
    JTextArea displayArea;
    Vector<Table> tables = new Vector<>();
    Vector<Staff> staff = new Vector<>();

    public RestaurantManagement() {

        //Setting up JFrame window
        JFrame jf=new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(600,500);
        jf.setVisible(true);
        jf.setTitle(("Restaurant Management System"));
        jf.setLayout(new BorderLayout());

        //Creating Jpanels
        top_panel=new JPanel(new FlowLayout(FlowLayout.LEFT));
        middle_panel=new JPanel();
        bottom_panel=new JPanel(new GridLayout(4, 1));
        
        //Creating table objects
        for (int i=1; i<= 4; i++) {
            Table table = new Table(i);
            tables.add(table);
        }

        //Creating waiter objects and adding to staff
        Waiter w1 = new Waiter(234,"Sam");
        Waiter w2 = new Waiter(111,"ABC");
        Waiter w3 = new Waiter(234,"Priya");
        staff.add(w1);
        staff.add(w2);
        staff.add(w3);

       //Creating chef object and adding to staff
        Chef c = new Chef(276, "Yash");
        staff.add(c);
        
        //Creating Manager objects and adding to staff
        Manager m = new Manager(78,"Josh");
        staff.add(m);
        
        
        //Initializing JFrame components
        tableSelector = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        staffSelector = new JComboBox<>(staff.stream().map(s -> s.employeeName).toArray(String[]::new));
        lbl1=new JLabel("Select Table:");
        lbl2=new JLabel("Select Staff:");
        lbl3=new JLabel("Item:");
        lbl4=new JLabel("Price:");
        displayArea = new JTextArea(40,40);
        displayArea.setEditable(false);
        itemField = new JTextField(15);
        priceField = new JTextField(5);
        addOrderButton = new JButton("Add Order");
        clearTableButton = new JButton("Clear Table");
        calculateBillButton = new JButton("Calculate Bill");
        makeReservationButton = new JButton("Make Reservation");
        cancelReservationButton = new JButton("Cancel Reservation");
        addStaffButton = new JButton("Add Staff");
        assignDutyButton = new JButton("Assign Duty");
        viewAssignmentsButton = new JButton("View Assignments");
        bottom1=new JPanel();
        bottom2=new JPanel();
        bottom3=new JPanel();
        bottom4=new JPanel();
        
        //adding components to panels
        top_panel.add(lbl1);
        top_panel.add(tableSelector);
        top_panel.add(lbl2);
        top_panel.add(staffSelector);
        bottom1.add(lbl3);
        bottom1.add(itemField);
        bottom1.add(lbl4);
        bottom1.add(priceField);
        bottom1.add(addOrderButton);
        bottom2.add(clearTableButton);
        bottom2.add(calculateBillButton);
        bottom3.add(addStaffButton);
        bottom3.add(assignDutyButton);
        bottom3.add(viewAssignmentsButton);
        bottom4.add(makeReservationButton);
        bottom4.add(cancelReservationButton);
        bottom_panel.add(bottom1);
        bottom_panel.add(bottom2);
        bottom_panel.add(bottom3);
        bottom_panel.add(bottom4);
        middle_panel.add(displayArea);

        //adding panels to main frame
        jf.add(top_panel, BorderLayout.NORTH);
        jf.add(middle_panel, BorderLayout.CENTER);
        jf.add(bottom_panel, BorderLayout.SOUTH);

        //adding action listener to buttons
        tableSelector.addActionListener(this);
        addOrderButton.addActionListener(this);
        clearTableButton.addActionListener(this);
        calculateBillButton.addActionListener(this);
        makeReservationButton.addActionListener(this);
        cancelReservationButton.addActionListener(this);
        addStaffButton.addActionListener(this);
        assignDutyButton.addActionListener(this);
        viewAssignmentsButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==tableSelector)
        {
            updateDisplay();
        }
        else if(e.getSource()==addOrderButton)
        {
            addOrder();
        }
        else if(e.getSource()==clearTableButton)
        {
            clearTable();
        }
        else if(e.getSource()==calculateBillButton)
        {
            calculateBill();
        }
        else if(e.getSource()==makeReservationButton)
        {
            makeReservation();
        }
        else if(e.getSource()==cancelReservationButton)
        {
            cancelReservation();
        }
        else if(e.getSource()==addStaffButton)
        {
            addStaff();
        }
        else if(e.getSource()==assignDutyButton)
        {
            assignDuty();
        }
        else
        {
            viewAssignments();
        }
        
    }
    
    private void updateDisplay() {
        Table table = getSelectedTable();
        displayArea.setText("");
        table.displayOrder(displayArea);
        displayArea.append("\nStaff assigned to this table:\n");
        for (Staff assignedStaff : table.getAssignedStaff()) {
            displayArea.append(assignedStaff.toString() + "\n");
        }
    }

    private void addOrder() {
        Table table = getSelectedTable();
        try {
            String item = itemField.getText();
            double price = Double.parseDouble(priceField.getText());
            table.placeOrder(item, price);
            updateDisplay();
            itemField.setText("");
            priceField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearTable() {
        getSelectedTable().clearTable();
        updateDisplay();
    }

    private void calculateBill() {
        Table table = getSelectedTable();
        double totalBill = table.calculateTotalBill();
        JOptionPane.showMessageDialog(this, String.format("Total Bill: ₹%.2f", totalBill), "Bill", JOptionPane.INFORMATION_MESSAGE);
    }

    private void makeReservation() {
        getSelectedTable().makeReservation();
        updateDisplay();
    }

    private void cancelReservation() {
        getSelectedTable().cancelReservation();
        updateDisplay();
    }

    private void addStaff() {
        String name = JOptionPane.showInputDialog(this, "Enter staff name:");
        String[] roles = {"Waiter", "Chef","Manager"};
        String role = (String) JOptionPane.showInputDialog(this, "Select staff role:", "Add Staff", JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);
        if (name != null && !name.isEmpty() && role != null) {
            int id = staff.size() + 1;
            Staff newStaff;
            switch (role) {
                case "Waiter":
                    newStaff = new Waiter(id, name);
                    break;
                case "Chef":
                    newStaff = new Chef(id, name);
                    break;
                case "Manager":
                    newStaff = new Manager(id, name);
                    break;
                default:
                    return;
            }
            staff.add(newStaff);
            staffSelector.addItem(name);
            JOptionPane.showMessageDialog(this, "Staff added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void assignDuty() {
        Table table = getSelectedTable();
        Staff selectedStaff = staff.get(staffSelector.getSelectedIndex());
        if (table.assignStaff(selectedStaff)) {
            JOptionPane.showMessageDialog(this, selectedStaff.employeeName + " assigned to Table " + table.getTableNumber(), "Duty Assigned", JOptionPane.INFORMATION_MESSAGE);
            updateDisplay();
        } else {
            JOptionPane.showMessageDialog(this, "Staff already assigned to this table", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAssignments() {
        StringBuilder sb = new StringBuilder("Staff Assignments:\n\n");
        for (Table table : tables) {
            sb.append("Table ").append(table.getTableNumber()).append(":\n");
            Vector<Staff> assignedStaff = table.getAssignedStaff();
            if (assignedStaff.isEmpty()) {
                sb.append(" No staff assigned\n");
            } else {
                for (Staff staff : assignedStaff) {
                    sb.append(" ").append(staff.toString()).append("\n");
                }
            }
            sb.append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Staff Assignments", JOptionPane.INFORMATION_MESSAGE);
    }

    private Table getSelectedTable() {
        int selectedTable = (Integer) tableSelector.getSelectedItem();
        return tables.get(selectedTable - 1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RestaurantManagement::new);
    }
}
