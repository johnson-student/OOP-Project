package user;

@FunctionalInterface
public interface StaffFilter {
    boolean apply(Staff staff);
}