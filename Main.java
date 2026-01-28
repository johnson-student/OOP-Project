public class Main {
    public static void main(String[] args) {

        // 1. Create Address
        Address senderAddress = new Address(
                "Phnom Penh",
                "Toul Kork",
                "Street 2004"
        );

        Address receiverAddress = new Address(
                "Phnom Penh",
                "Dong Kao",
                "123"
        );

        // 2. Create User
        User sender = new User(
                "Son",
                "012345678",
                senderAddress
        );

        User receiver = new User(
                "Dom",
                "098765432",
                receiverAddress
        );

        // 3. Create Courier
        Courier courier = new Courier(
                "Neng",
                "011223344"
        );

        // 4. Create Parcel
        Parcel parcel = new Parcel(
                "Clothes",
                2.5
        );

        // 5. Print information (testing)
        System.out.println("Sender Name: " + sender.getName());
        System.out.println("Sender Address: " + sender.getAddress());
        System.out.println("receiver Name: " + receiver.getName());
        System.out.println("receiver Address: " + receiver.getAddress());
        System.out.println("Courier Name: " + courier.getName());
        parcel.print();
    }
}
