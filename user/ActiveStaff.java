package user;
public class ActiveStaff implements StaffFilter {
    @Override
    public boolean filter(Staff staff) {
        return staff.isActive();
    }

}
