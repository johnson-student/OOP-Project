package user;
public class ActiveStaff implements StaffFilter {
    @Override
    public boolean apply(Staff staff) {
        return staff.isActive();
    }

}
