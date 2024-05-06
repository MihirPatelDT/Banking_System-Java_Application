import javax.swing.JOptionPane;

class BankDetails {
    private String accno;
    private String name;
    private String acc_type;
    private long balance;
    private long loanAmount;
    private boolean hasLoan;
    static String username = "MihirPatelDT";
    static String password = "Mihir";
    static boolean loggedIn = false;

    public void openAccount() {
        accno = JOptionPane.showInputDialog(null, "Enter Account No:");
        acc_type = JOptionPane.showInputDialog(null, "Enter Account type:");
        name = JOptionPane.showInputDialog(null, "Enter Name:");
        balance = Long.parseLong(JOptionPane.showInputDialog(null, "Enter Balance:"));
        hasLoan = false;
        loanAmount = 0;
    }

    public void showAccount() {
        String accountDetails = "******************************* My Bank Account Details *******************************\n";
        accountDetails += "+-----------------------------------------------------------+\n";
        accountDetails += "| Name of account holder: " + name + "\n";
        accountDetails += "| Account no.: " + accno + "\n";
        accountDetails += "| Account type: " + acc_type + "\n";
        accountDetails += "| Balance: " + balance + "\n";
        if (hasLoan) {
            accountDetails += "| Loan Amount: " + loanAmount + "\n";
        }
        accountDetails += "+-----------------------------------------------------------+";
        JOptionPane.showMessageDialog(null, accountDetails);
    }

    public void deposit() {
        long amt = Long.parseLong(JOptionPane.showInputDialog(null, "Enter the amount you want to deposit:"));
        balance = balance + amt;
    }

    public void withdrawal() {
        long amt = Long.parseLong(JOptionPane.showInputDialog(null, "Enter the amount you want to withdraw:"));
        if (balance >= amt) {
            balance = balance - amt;
            JOptionPane.showMessageDialog(null, "Balance after withdrawal: " + balance);
        } else {
            JOptionPane.showMessageDialog(null, "Your balance is less than " + amt + ". Transaction failed...!!");
        }
    }

    public void requestLoan() {
        if (hasLoan) {
            JOptionPane.showMessageDialog(null, "You already have an active loan. Please repay the existing loan before requesting a new one.");
        } else {
            long requestedLoanAmount = Long.parseLong(JOptionPane.showInputDialog(null, "Enter the loan amount you want to request:"));
            if (requestedLoanAmount <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid loan amount. Loan request failed.");
            } else {
                hasLoan = true;
                loanAmount = requestedLoanAmount;
                balance += requestedLoanAmount;
                JOptionPane.showMessageDialog(null, "Loan of " + requestedLoanAmount + " has been approved and added to your account balance.");
            }
        }
    }

    public void repayLoan() {
        if (hasLoan) {
            long repayAmount = Long.parseLong(JOptionPane.showInputDialog(null, "Enter the amount you want to repay:"));
            if (repayAmount <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid repay amount. Loan repayment failed.");
            } else if (repayAmount > loanAmount) {
                JOptionPane.showMessageDialog(null, "Repay amount cannot exceed the loan amount. Loan repayment failed.");
            } else {
                loanAmount -= repayAmount;
                balance -= repayAmount;
                JOptionPane.showMessageDialog(null, "Loan repayment of " + repayAmount + " has been processed successfully.");
                if (loanAmount == 0) {
                    hasLoan = false;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "You don't have an active loan to repay.");
        }
    }

    public boolean search(String ac_no) {
        if (accno.equals(ac_no)) {
            showAccount();
            return true;
        }
        return false;
    }

    public void deleteAccount() {
        accno = null;
        name = null;
        acc_type = null;
        balance = 0;
        hasLoan = false;
        loanAmount = 0;
        JOptionPane.showMessageDialog(null, "Account deleted successfully.");
    }

    public static void login() {
        String inputUsername = JOptionPane.showInputDialog(null, "Enter username:");
        String inputPassword = JOptionPane.showInputDialog(null, "Enter password:");
        if (inputUsername.equals(username) && inputPassword.equals(password)) {
            loggedIn = true;
            JOptionPane.showMessageDialog(null, "Login successful!");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password. Login failed.");
        }
    }
}

public class BankingApp {
    public static void main(String arg[]) {
        JOptionPane.showMessageDialog(null, "******************************* Banking System Application *******************************");
        BankDetails.login();

        int ch;
        BankDetails[] C = new BankDetails[10]; // Assume max 10 accounts

        do {
            String choice = JOptionPane.showInputDialog(null,
                    "1. Open Account\n2. Display all account details\n3. Search by Account number\n4. Deposit the amount\n5. Withdraw the amount\n6. Request Loan\n7. Repay Loan\n8. Delete Account\n9. Exit");
            try {
                ch = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid choice.");
                ch = 0;
            }

            switch (ch) {
                case 1:
                    for (int i = 0; i < C.length; i++) {
                        if (C[i] == null) {
                            C[i] = new BankDetails();
                            C[i].openAccount();
                            break;
                        }
                    }
                    break;
                case 2:
                    for (BankDetails account : C) {
                        if (account != null) {
                            account.showAccount();
                        }
                    }
                    break;
                case 3:
                    String ac_no = JOptionPane.showInputDialog(null, "Enter account no. you want to search:");
                    boolean found = false;
                    for (BankDetails account : C) {
                        if (account != null) {
                            found = account.search(ac_no);
                            if (found) {
                                break;
                            }
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(null, "Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 4:
                    ac_no = JOptionPane.showInputDialog(null, "Enter Account no.:");
                    found = false;
                    for (BankDetails account : C) {
                        if (account != null) {
                            found = account.search(ac_no);
                            if (found) {
                                account.deposit();
                                break;
                            }
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(null, "Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 5:
                    ac_no = JOptionPane.showInputDialog(null, "Enter Account No:");
                    found = false;
                    for (BankDetails account : C) {
                        if (account != null) {
                            found = account.search(ac_no);
                            if (found) {
                                account.withdrawal();
                                break;
                            }
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(null, "Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 6:
                    ac_no = JOptionPane.showInputDialog(null, "Enter Account No to request a loan:");
                    found = false;
                    for (BankDetails account : C) {
                        if (account != null) {
                            found = account.search(ac_no);
                            if (found) {
                                account.requestLoan();
                                break;
                            }
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(null, "Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 7:
                    ac_no = JOptionPane.showInputDialog(null, "Enter Account No to repay a loan:");
                    found = false;
                    for (BankDetails account : C) {
                        if (account != null) {
                            found = account.search(ac_no);
                            if (found) {
                                account.repayLoan();
                                break;
                            }
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(null, "Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 8:
                    ac_no = JOptionPane.showInputDialog(null, "Enter Account No you want to delete:");
                    found = false;
                    for (BankDetails account : C) {
                        if (account != null) {
                            found = account.search(ac_no);
                            if (found) {
                                account.deleteAccount();
                                break;
                            }
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(null, "Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 9:
                    JOptionPane.showMessageDialog(null, "See you soon...Please Visit Again...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please enter a number from 1 to 9.");
            }
        } while (ch != 9);
    }
}
