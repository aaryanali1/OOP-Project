package types;

public abstract class User {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getRole() { return role; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public void setRole(String role) { this.role = role; }

    public abstract boolean canAdd();
    public abstract boolean canDelete();
    public abstract boolean canUpdate();
    public abstract boolean canMarkDelivered();
    public abstract boolean canFlag();
    public abstract boolean canUpdatePrices();
    public abstract boolean canReorder();
    public abstract boolean canSell();
    public abstract boolean canViewAuditLog();
    public abstract boolean canClearPay();
    public abstract boolean canMarkInactive();
    public abstract String toFileString();
}
