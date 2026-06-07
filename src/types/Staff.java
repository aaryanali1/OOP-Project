package types;

public class Staff extends User {
    public Staff(String username, String password) {
        super(username, password, "STAFF");
    }

    @Override
    public boolean canAdd() { return false; }

    @Override
    public boolean canDelete() { return false; }

    @Override
    public boolean canUpdate() { return false; }

    @Override
    public boolean canMarkDelivered() { return true; }

    @Override
    public boolean canFlag() { return true; }

    @Override
    public boolean canUpdatePrices() { return false; }

    @Override
    public boolean canReorder() { return false; }

    @Override
    public boolean canSell() { return true; }

    @Override
    public boolean canViewAuditLog() { return false; }

    @Override
    public boolean canClearPay() { return false; }

    @Override
    public boolean canMarkInactive() { return false; }

    @Override
    public String toString() {
        return getUsername() + "|" + getPassword() + "|" + "|STAFF";
    }
}
