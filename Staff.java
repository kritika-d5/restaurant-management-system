abstract class Staff {
    int employeeID;
    String employeeName;

    public Staff(int employeeID, String employeeName) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
    }

    abstract public String toString() {
    }
}

class Waiter extends Staff {
    public Waiter(int employeeID, String employeeName) {
        super(employeeID, employeeName);
    }

    public String toString() {
        return "Waiter - " + "ID: " + employeeID + ", Name: " + employeeName;
    }
}

class Chef extends Staff {
    public Chef(int employeeID, String employeeName) {
        super(employeeID, employeeName);
    }

    public String toString() {
        return "Chef - " + "ID: " + employeeID + ", Name: " + employeeName;
    }
}

class Cleaner extends Staff {
    public Cleaner(int employeeID, String employeeName) {
        super(employeeID, employeeName);
    }

    public String toString() {
        return "Cleaner - " + "ID: " + employeeID + ", Name: " + employeeName;
    }
}

class Manager extends Staff {
    public Manager(int employeeID, String employeeName) {
        super(employeeID, employeeName);
    }

    public String toString() {
        return "Manager - " + "ID: " + employeeID + ", Name: " + employeeName;
    }
}
