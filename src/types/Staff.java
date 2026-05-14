package types;

public class Staff extends User {
    public Staff(String username, String password) {
        super(username, password, "Staff");
    }

    @Override
    public boolean canReorder() {
        return false;
    }

    @Override
    public boolean canViewAuditLog() {
        return false;
    }

    @Override
    public boolean canManagePrices() {
        return false;
    }

    @Override
    public boolean canApproveRequests() {
        return false;
    }

    @Override
    public String toFileFormat() {
        return getUsername() + "|" + getPassword() + "|Staff";
    }
}
