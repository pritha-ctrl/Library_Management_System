package org.example;
import java.util.*;

class Book {
    private String bookID;
    private String title;
    private String author;
    int totalCopies;
    int availableCopies;
    int avail;
    private long issueTime=0;

    public Book(String bookID, String title, String author, int totalCopies) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.avail=0;

    }

    public long getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(long newTime){
        issueTime = newTime;
    }

    public String getTitle() {
        return title;
    }
    public int getAvail(){
        return avail;
    }
    public int setAvail(){
        if(avail==1){
            return avail=0;
        }
        else{
            return avail=1;
        }
    }
    public String getAuthor() {
        return author;
    }
    public String getBookID() {
        return bookID;
    }
    public String toString() {
        return "Book ID: " + bookID + ", Title: " + title + ", Author: " + author +
                ", Total Copies: " + totalCopies + ", Available Copies: " + availableCopies + ", Avail: " + avail;
    }
}
class Member {
    private String phoneNumber;
    private String name;
    private int age;
    private String MemberID;
    List<Book> borrowedBooks;
    private long fine;
    public Member(String MemberID,String phoneNumber, String name, int age) {
        this.MemberID=MemberID;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.age = age;
        this.fine=0;
        this.borrowedBooks = new ArrayList<>();
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public long getFine(){
        return fine;
    }
    public void setFine(long newValue) {
        fine = newValue;
    }

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getMemberID() {
        return MemberID;
    }
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Phone Number: " + phoneNumber;
    }
}
class Library {
    List<Book> books;
    List<Member> members;
    String librarianPassword;
    Map<String, Book> bookDictionary;
    Map<String, Member>Memberdictionary;
    int id_num = 0;
    public static long initial_date = System.currentTimeMillis()/1000;
    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        bookDictionary = new HashMap<>();
        Memberdictionary = new HashMap<>();
        librarianPassword = "1234567"; // Set your desired librarian password here
    }

    // Add a new book to the library
    public void addBook(String title, String author, int totalCopies) {

        for (int i = 1; i <= totalCopies; i++, id_num++) {
            String bookID = "LIB00" + Integer.toString(id_num);
            //String bookID = "hellew";
//            System.out.println(bookID);
            Book newBook = new Book(bookID, title, author, totalCopies);
            newBook.avail=1;
            bookDictionary.put(bookID, newBook);
            books.add(newBook);
        }
    }
    // Remove a book from the library by book ID
    public void removeBook(String bookID) {
        int check_flag2=0;
        String title_temp = "";

        Iterator<Map.Entry<String, Book>> bookcheck = bookDictionary.entrySet().iterator();
        while(bookcheck.hasNext()) {
            Map.Entry<String, Book> next_book = bookcheck.next();
            if(bookID.equals(next_book.getKey())) {
                check_flag2 = 1;
                title_temp = next_book.getValue().getTitle();
                bookcheck.remove();
            }
        }

        if(check_flag2==1) {
            for (Map.Entry<String, Book> x : bookDictionary.entrySet()) {
                Book temp = x.getValue();
                if (title_temp.equals(temp.getTitle())) {
                    temp.availableCopies--;
                    temp.totalCopies--;
                }
            }
        }

        if (check_flag2 == 1) {
            System.out.println("Book with ID " + bookID + " has been removed.");
        }
        else {
            System.out.println("Book with ID " + bookID + " not found in the library.");
        }
    }
    // Display all books in the library
    public void displayBooks() {
        System.out.println("Books in the library:");
        for (Map.Entry<String, Book> x : bookDictionary.entrySet()) {
            System.out.println(x.getValue());
        }
    }
    int memberIDCounter=0;
    public void addMember(String phoneNumber, String name, int age) {
        String memberID = "M" + String.format("%03d", memberIDCounter);
        Member newMember = new Member(memberID, phoneNumber, name, age);

        members.add(newMember);
        Memberdictionary.put(memberID, newMember);

        System.out.println("Member with MemberID " + memberID + " has been registered.");
        memberIDCounter++;
    }
    public void removeMember(String memberID) {
        int check_flag1=0;
        // Member memberToRemove = Memberdictionary.get(memberID);
        // String PhoneNumber = memberToRemove.getPhoneNumber();
        for (Member member : members) {
            if (member.getMemberID().equals(memberID)) {
                check_flag1=1;
                members.remove(member);
                Memberdictionary.remove(memberID);
                break;
            }
        }
        if (check_flag1 == 1) {
//            members.remove(memberToRemove);
            // Remove from the dictionary
            System.out.println("Member with Phone Number " + memberID + " has been removed from the library.");
        }
        else {
            System.out.println("Member with Phone Number " + memberID + " not found in the library.");
        }
    }
    public void displayMembers() {
        System.out.println("Registered Members in the library:");
        for (Map.Entry<String,Member> x :Memberdictionary.entrySet()) {
            System.out.print(x.getKey()+" ");
            System.out.println(x.getValue());
        }
    }
    public void displayAvailableBooks() {
        System.out.println("Available Books:");
        for (Map.Entry<String, Book> x : bookDictionary.entrySet()) {
            if (x.getValue().avail == 1) {
                System.out.println("Book ID: " + x.getValue().getBookID() + ", Title: " + x.getValue().getTitle() + ", Author: " + x.getValue().getAuthor() + ", Available: Yes");
            }
            else {
                System.out.println("Book ID: " + x.getValue().getBookID() + ", Title: " + x.getValue().getTitle() + ", Author: " + x.getValue().getAuthor() + ", Available: No");
            }
        }
    }
    public void clearPenalty(Member temp2) {
        if (temp2.getFine()!=0) {
            System.out.println("There is a total of " + temp2.getFine() + " to be paid!");
            temp2.setFine(0);
            System.out.println("Penalty cleared successfully.");
        }
    }



    public void issueBook(Member temp ,String bookID) {
        if (bookDictionary.containsKey(bookID)) {
            Book book = bookDictionary.get(bookID);
            if (book.getAvail()==1) {
                if (temp.getFine() > 0) {
                    System.out.println("You have a penalty to clear before borrowing a new book.");
                    System.out.println("Your current penalty amount: " + temp.getFine());
                    clearPenalty(temp);
                }else if(temp.borrowedBooks.size()>=2) System.out.println("You have borrowed maximum amount of books!");
                else {
                    book.setAvail();
                    temp.borrowedBooks.add(book);
                    book.setIssueTime(System.currentTimeMillis()/1000 - initial_date);
                    for (Map.Entry<String, Book> x : bookDictionary.entrySet()) {
                        String temp_title = x.getValue().getTitle();
                        if (temp_title.equals(book.getTitle())) {
                            x.getValue().availableCopies--;
                        }
                    }
                    System.out.println("Book " + book.getTitle() + " has been borrowed by Member " + temp.getMemberID() );
                }
            }
            else {
                System.out.println("Book with BookID " + bookID + " is not available.");
            }
        }
        else {
            System.out.println("Book ID not found in the library.");
        }
    }
    public void returnBook(Member tempmem, String bookID) {
        if(tempmem.borrowedBooks.size()==0) System.out.println("No books have to be returned.");
        else {
            int check_bookFound = 0;
            for (Book borrowedBook : tempmem.borrowedBooks) {
                if (borrowedBook.getBookID().equals(bookID)) {
                    check_bookFound = 1;
                    Book book = borrowedBook;
                    book.setAvail();
                    tempmem.borrowedBooks.remove(book);
                    long time_gap = System.currentTimeMillis()/1000 - (book.getIssueTime() + initial_date);
                    if(time_gap > 10) {
                        tempmem.setFine(time_gap*3);
                        System.out.println("Fine has to be payed!");
                    }
                    System.out.println("Book " + book.getTitle() + " has been returned by Member " + tempmem.getMemberID());
                    for (Map.Entry<String, Book> x : bookDictionary.entrySet()) {
                        String temp_title = x.getValue().getTitle();
                        if (temp_title.equals(book.getTitle())) {
                            x.getValue().availableCopies++;
                        }
                    }
                    break;
                }
            }
            if (check_bookFound == 0) {
                System.out.println("Book ID not found in your borrowed books.");
            }
        }
    }




    // Librarian login
    public boolean librarianLogin(String password) {
        return password.equals(librarianPassword);
    }

    // Get the list of books in the library
    public List<Book> getBooks() {
        return books;
    }

    // Get the list of members in the library
    public List<Member> getMembers() {
        return members;
    }
}

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System:");
            System.out.println("1. Sign in as Member");
            System.out.println("2. Sign in as Librarian");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                //member sign in
                case 1:
                    System.out.print("Enter your name: ");
                    String memberName = scanner.nextLine();
                    System.out.print("Enter your phone number: ");
                    String memberPhoneNumber = scanner.nextLine();
                    Member loggedinMember = new Member("", "", "", 0);
                    int member_found = 0;
                    for (Member member : library.getMembers()) {
                        if (member.getName().equals(memberName) && member.getPhoneNumber().equals(memberPhoneNumber)) {
                            loggedinMember = member;
                            member_found = 1;
                            break;
                        }
                    }
                    if (member_found == 1) {
                        memberMenu(scanner, loggedinMember, library);
                    }
                    else {
                        System.out.println("Member not found. Please check your details.");
                    }
                    break;

                case 2:
                    //Librarian sign in
                    System.out.print("Enter the librarian password: ");
                    String librarianPassword = scanner.nextLine();
                    if (library.librarianLogin(librarianPassword)) {
                        librarianMenu(scanner, library);
                    }
                    else
                    {
                        System.out.println("Invalid librarian password. Please try again.");
                    }
                    break;
                case 3:
                    //program exit
                    scanner.close();
                    System.out.println("\n----------------------------\nThank you for Visiting Us!\n----------------------------\n");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    public static void memberMenu(Scanner scanner, Member member, Library library) {
        while (true) {
            System.out.println("\nMember Menu:");
            System.out.println("1. List Available Books");
            System.out.println("2. List My Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Pay Fine");
            System.out.println("6. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    //List Available Books
                    // library.displayBooks();
                    library.displayAvailableBooks();
                    break;

                case 2:
                    //List My Books
                    System.out.println("Books borrowed by " + member.getName() + ":");
                    for (Book book : member.borrowedBooks) {
                        System.out.print(book);
                        System.out.println(", issue time : " + book.getIssueTime());
                    }
                    break;

                case 3:
                    //Issue Book
                    library.displayAvailableBooks();
                    System.out.print("Enter the Book ID to borrow: ");
                    String bookIDToBorrow = scanner.nextLine();
                    library.issueBook(member,bookIDToBorrow);
                    break;

                case 4:
                    //Return Book
                    System.out.println("Books borrowed by " + member.getName() + ":");
                    for (Book book : member.borrowedBooks) {
                        System.out.print(book);
                        System.out.println(", issue time : " + book.getIssueTime());
                    }
                    System.out.print("Enter the Book ID to return: ");
                    String bookIDToReturn = scanner.nextLine();
                    library.returnBook(member,bookIDToReturn);
                    break;

//

                case 5:
                    //Payfine
                    library.clearPenalty(member);
                    break;

                case 6:
                    // Logout
                    System.out.println("Logged out.");
                    return;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    public static void librarianMenu(Scanner scanner, Library library) {
        while (true) {
            System.out.println("\nLibrarian Menu:");
            System.out.println("1. Add a New Book");
            System.out.println("2. Remove a Book");
            System.out.println("3. Add a New Member");
            System.out.println("4. Remove a Member");
            System.out.println("5. View All Books");
            System.out.println("6. View All Members with Fines");
            System.out.println("7. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    //Add a New Book
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter Total Copies: ");
                    int totalCopies = scanner.nextInt();
                    scanner.nextLine();
                    library.addBook(title, author, totalCopies);
                    System.out.println("Book added successfully.");
                    break;

                case 2:
                    //Remove a Book
                    System.out.print("Enter Book ID to remove: ");
                    String bookIDToRemove = scanner.nextLine();
                    library.removeBook(bookIDToRemove);
                    break;

                case 3:
                    //Add a New Member
                    System.out.print("Enter Member's Name: ");
                    String memberName = scanner.nextLine();
                    System.out.print("Enter Member's Age: ");
                    int memberAge = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Member's Phone Number: ");
                    String phoneNumber = scanner.nextLine();
                    library.addMember(phoneNumber, memberName, memberAge);
                    System.out.println("Member registered successfully.");
                    break;

                case 4:
                    //Remove a Member
                    System.out.print("Enter Member's Member ID to remove: ");
                    String MemberID = scanner.nextLine();
                    library.removeMember(MemberID);
                    break;

                case 5:
                    //View All Books
                    library.displayBooks();
                    break;

                case 6:
                    //View All Members with Fines

                    library.displayMembers();
                    break;

                case 7:
                    //Logout
                    System.out.println("Logged out.");
                    return;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}