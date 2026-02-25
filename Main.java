class Main {
    public static void main(String[] args) {

        DeliverySystem system = new DeliverySystem("CADT Delivery","0125485");

        // Add courier to system
        system.addStaff(new Courier("Neng", "011223344", "neng123","Courier", 4.0));
        system.addStaff(new Courier("john", "012222222", "john123", "Manager", 4.5));

        // Addresses
        Address senderAddress = new Address(
                "Phnom Penh", "Toul Kork", "Street 2004");

        Address receiverAddress = new Address(
                "Phnom Penh", "Dong Kao", "123");

        // Users
        User sender = new User(
                "Son", "012345678","12345678", senderAddress, false);

        User receiver = new User(
                "Dom", "098765432", receiverAddress);

        // Parcel
        Parcel parcel = new Parcel("glass", 2.5 , 50.0, sender.getID());

        // Create delivery request
        DeliveryRequest request =
                system.createRequest(sender, receiver, parcel);

        if (request != null) {
            request.printInfo();
        }
    }
}
