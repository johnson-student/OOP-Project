public class PricingService {

    static double calculateFee(Parcel parcel) {
        if (parcel == null) {
            return ;
        }

        double baseFee = 2.0;

        if (parcel.getType().equals("liquid") || parcel.getType().equals("glass")) {
            return parcel.getWeight() * 1.0 + baseFee;
        }

        return parcel.getWeight() * 0.5 + baseFee;
    }
}
