package types;

public class Admin extends User implements FileWrite {
    public Admin(String username, String password) {
        super(username, password, "ADMIN");
    }

    @Override public boolean canAdd() { return true; }

    @Override public boolean canDelete() { return true; }

    @Override public boolean canUpdate() { return true; }

    @Override public boolean canMarkDelivered() { return true; }

    @Override public boolean canFlag() { return false; }

    @Override public boolean canUpdatePrices() { return true; }

    @Override public boolean canReorder() { return true; }

    @Override public boolean canSell() { return true; }

    @Override public boolean canViewAuditLog() { return true; }

    @Override public boolean canClearPay() { return true; }

    @Override public boolean canMarkInactive() { return true; }

    @Override
    public String toFileString() {
        return getUsername() + "|" + getPassword() + "|ADMIN";
    }
}

