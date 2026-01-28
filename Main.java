public class Main {
    public static void main(String[] args) {

        // 1. Create Address
        Address userAddress = new Address(
                "Phnom Penh",
                "Toul Kork",
                "Street 2004"
        );

        // 2. Create User
        User user = new User(
                "Johnson",
                "012345678",
                userAddress
        );

        // 3. Create Courier
        Courier courier = new Courier(
                "Dara",
                "098765432"
        );

        // 4. Create Parcel
        Parcel parcel = new Parcel(
                "clothes",
                2.5
        );

        // 5. Print information (testing)
        System.out.println("User Name: " + user.getName());
        System.out.println("User Address: " + user.getAddress());
        System.out.println("Courier Name: " + courier.getName());
        parcel.print();
    }
}
