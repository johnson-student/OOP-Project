public interface InStaff {
    String getStaffId();
    String getUsername();
    String getPosition();
    boolean checkPassword(String input);
    boolean can(String action);
}
