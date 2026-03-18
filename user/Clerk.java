package user;
import controller.DeliverySystem;;

public class Clerk extends Staff {

    

    public Clerk(String fullName, String username, String phone, String password, double rating ,float salary) {
        super(fullName, username, phone, password, rating, salary);
    }

    @Override
    public boolean can(String action) {
        if(action.equals(DeliverySystem.CREATE_REQUEST)||action.equals(DeliverySystem.VIEW_CUSTOMERS)) {
            return true;
        }
        return false;
    }


}
