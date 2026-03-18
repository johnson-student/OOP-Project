package user;

@FunctionalInterface
public interface StaffFilter {
    boolean filter(Staff staff);
}