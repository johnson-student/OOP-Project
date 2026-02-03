class Main {
    public static void main(String[] args) {

        // Create system (like CoffeeShop)
        DeliverySystem system = new DeliverySystem("CADT Delivery",5);

        // Add courier to system
        system.addCourier(new Courier("Neng", "011223344"));
        system.addCourier(new Courier("john", "012222222"));

        // Addresses
        Address senderAddress = new Address(
                "Phnom Penh", "Toul Kork", "Street 2004");

        Address receiverAddress = new Address(
                "Phnom Penh", "Dong Kao", "123");

        // Users
        User sender = new User(
                "Son", "012345678", senderAddress);

        User receiver = new User(
                "Dom", "098765432", receiverAddress);

        // Parcel
        Parcel parcel = new Parcel("Clothes", 2.5);

        // Create delivery request
        DeliveryRequest request =
                system.createRequest(sender, receiver, parcel);

        if (request != null) {
            request.printInfo();
        }
    }
}
