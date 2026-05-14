package types;

public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password, "Admin");
    }

    @Override
    public boolean canReorder() {
        return true;
    }

    @Override
    public boolean canViewAuditLog() {
        return true;
    }

    @Override
    public boolean canManagePrices() {
        return true;
    }

    @Override
    public boolean canApproveRequests() {
        return true;
    }

    @Override
    public String toFileFormat() {
        return getUsername() + "|" + getPassword() + "|Admin";
    }
}
