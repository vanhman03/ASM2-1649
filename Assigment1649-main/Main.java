import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static final String RESET_Color = "\u001B[0m";
    public static final String RED_Color = "\u001B[31m";
    public static final String BLUE_Color = "\u001B[34m";
    public static final String YELLOW_Color = "\u001B[33m";

    public static void main(String[] args) {
        OrderQueue orderQueue = new OrderQueue();
        ArrayList<Book> books = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        int orderID = 0;

        books.add(new Book("Old Father", "An"));
        books.add(new Book("Young Father", "Binh"));
        books.add(new Book("Big brother", "Chien"));
        books.add(new Book("Small brother", "Duong"));
        books.add(new Book("Old Wolf Wall street", "Manh"));

        while (running) {
            try {
                System.out.println(BLUE_Color + "-------Chọn một tùy chọn-------");
                System.out.println("1. Thêm đơn hàng");
                System.out.println("2. Xử lý đơn hàng");
                System.out.println("3. Sắp xếp danh sách sách trong đơn hàng");
                System.out.println("4. Tìm kiếm đơn hàng");
                System.out.println("5. Xem danh sách sách");
                System.out.println("6. Thêm sách");
                System.out.println("7. Thoát");
                System.out.print("Lựa chọn của bạn: " + RESET_Color);

                int choice = scanner.nextInt();
                scanner.nextLine();  // Đọc bỏ dòng newline

                switch (choice) {
                    case 1:
                        // Đo thời gian bắt đầu
                        long startTimeAdd = System.nanoTime();
                        // Thêm đơn hàng
                        System.out.print("Nhập tên khách hàng: ");
                        String customerName = scanner.nextLine();
                        System.out.print("Nhập địa chỉ giao hàng: ");
                        String shippingAddress = scanner.nextLine();
                        showBookList(books);

                        ArrayList<Book> orderBooks= new ArrayList<>();
                        int j = 1;
                        while (j==1){
                            System.out.print("Nhập ID sách cần đặt: ");
                            int id = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Nhập số lượng: ");
                            int quantity = scanner.nextInt();
                            scanner.nextLine();
                            Book newBook = new Book(books.get(id-1).getTitle(), books.get(id-1).getAuthor(),quantity );
                            orderBooks.add(newBook);

                            System.out.println("Thêm thành công. Tiếp tục hay dừng lại:");
                            System.out.println("1. Tiếp tục.");
                            System.out.println("2. Dừng lại.");
                            System.out.print("Nhập lựa chọn: ");
                            j = scanner.nextInt();
                            scanner.nextLine();
                        }
                        orderID++;
                        Order order = new Order(customerName, shippingAddress, orderBooks, orderID);
                        orderQueue.addOrder(order);



                    // Đo thời gian kết thúc
                    long endTimeAdd = System.nanoTime();
                    long durationAdd = endTimeAdd - startTimeAdd; // Thời gian thực thi trong nano giây

                    System.out.println(YELLOW_Color + "Đơn hàng đã được thêm thành công." + RESET_Color);
                    System.out.println("Thời gian thêm đơn hàng: " + durationAdd + " nanoseconds");
                    break;

                    case 2:
                        // Đo thời gian bắt đầu
                        long startTimeProcess = System.nanoTime();

                        // Xử lý đơn hàng
                        Order processedOrder = orderQueue.processOrder();

                        // Đo thời gian kết thúc
                        long endTimeProcess = System.nanoTime();
                        long durationProcess = endTimeProcess - startTimeProcess; // Thời gian thực thi trong nano giây


                        if (processedOrder != null) {
                            System.out.println(YELLOW_Color + "Đơn hàng đã được xử lý: " + processedOrder.getOrderID() + RESET_Color);
                        } else {
                            System.out.println("Không có đơn hàng nào trong hàng đợi.");
                        }
                        // In thời gian thực thi và độ phức tạp
                        System.out.println("Thời gian xử lý đơn hàng: " + durationProcess + " nanoseconds");
                        System.out.println("Độ phức tạp thời gian của việc xử lý đơn hàng: O(1)"); // Xử lý đơn hàng là O(1)
                        break;

                    case 3:

                        // Sắp xếp danh sách sách trong đơn hàng
                        System.out.print("Nhập mã đơn hàng để sắp xếp sách: ");
                        int searchOrderNumber = scanner.nextInt();
                        scanner.nextLine();
                        Order foundOrder = orderQueue.searchOrder(searchOrderNumber);

                        if (foundOrder != null) {
                            System.out.println(BLUE_Color + "Sắp xếp theo tác giả hay tiêu đề: ");
                            System.out.println("1. Tiêu đề.");
                            System.out.println("2. Tác giả." + RESET_Color);
                            System.out.print("Lựa chọn của bạn: ");
                            int orderType = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println(BLUE_Color + "Lựa chọn kiểu sắp xếp: ");
                            System.out.println("1. Từ a-z");
                            System.out.println("2. Từ z-a" + RESET_Color);
                            System.out.print("Lựa chọn của bạn: ");
                            int orderBy = scanner.nextInt();
                            scanner.nextLine();
                            if (orderType == 1) {  //Sắp xếp theo tiêu đề
                                if (orderBy == 1) {         //Sắp xếp a-z
                                    foundOrder.sortBooksByTitle(true);
                                } else
                                    foundOrder.sortBooksByTitle(false);
                            } else //Sắp xếp theo tác giả
                                if ((orderBy == 1)) {        //Sắp xếp z-a
                                    foundOrder.sortBooksByAuthor(true);
                                } else foundOrder.sortBooksByAuthor(false);

                            System.out.println(YELLOW_Color + "Danh sách sách trong đơn hàng sau khi sắp xếp:" + RESET_Color);
                            for (Book book : foundOrder.getBooks()) {
                                System.out.println(YELLOW_Color + "Sách: " + book.getTitle() + " bởi " + book.getAuthor() + RESET_Color);
                            }
                        } else {
                            System.out.println(RED_Color + "Không tìm thấy đơn hàng với mã: " + searchOrderNumber + RESET_Color);
                        }
                        break;

                    case 4:

                        // Tìm kiếm đơn hàng
                        System.out.print("Nhập mã đơn hàng cần tìm: ");
                        int searchNumber = scanner.nextInt();
                        scanner.nextLine();
                        Order searchResult = orderQueue.searchOrder(searchNumber);


                        if (searchResult != null) {
                            System.out.println("Đơn hàng được tìm thấy: ");
                            System.out.print(YELLOW_Color);
                            searchResult.showOrderInfor();
                            System.out.print(RESET_Color);
                        } else {
                            System.out.println(RED_Color + "Không tìm thấy đơn hàng." + RESET_Color);
                        }
                        break;
                    case 5:
                        showBookList(books);
                        break;
                    case 6:
                        System.out.print("Nhập số sách cần thêm: ");
                        int numberOfBooks = scanner.nextInt();
                        scanner.nextLine();  // Đọc bỏ dòng newline

                        for (int i = 0; i < numberOfBooks; i++) {
                            System.out.print("Nhập tiêu đề sách: ");
                            String title = scanner.nextLine();
                            System.out.print("Nhập tên tác giả: ");
                            String author = scanner.nextLine();
                            books.add(new Book(title, author));
                            System.out.println();
                        }
                        break;
                    case 7:
                        // Thoát chương trình
                        running = false;
                        System.out.println("Đã thoát chương trình.");
                        break;
                    default:
                        System.out.println(RED_Color + "Lựa chọn không hợp lệ. Vui lòng thử lại." + RESET_Color);
                }
            } catch (InputMismatchException e) {
                System.out.println(RED_Color + "Đầu vào không hợp lệ. Vui lòng nhập một số nguyên." + RESET_Color);
                scanner.nextLine();  // Đọc bỏ dòng nhập sai
            }
        }

        scanner.close();
    }

    private static void showBookList(ArrayList<Book> books){
        System.out.println(BLUE_Color+ "Danh sách sách: ");
        for (int i = 0; i < books.size(); i++) {
            System.out.print(i + 1 + ". ");
            books.get(i).showBookInfor();
            System.out.println(".");
        }
        System.out.print(RESET_Color);
    }
}