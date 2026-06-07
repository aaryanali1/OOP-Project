package types;

public class Supplier implements FileWrite {
    private int id;
    private String supplierName;
    private String contact;
    private int deliveryTime;
    private String category;
    private String paymentStatus;
    private int reviewScore;
    private boolean active;

    public Supplier(int id, String supplierName, String contact, int deliveryTime, String category, String paymentStatus, int reviewScore, boolean active) {
        this.id = id;
        this.supplierName = supplierName;
        this.contact = contact;
        this.deliveryTime = deliveryTime;
        this.category = category;
        this.paymentStatus = paymentStatus;
        this.reviewScore = Math.max(1, Math.min(5, reviewScore));
        this.active = active;
    }

    public int getId() { return id; }

    public String getSupplierName() { return supplierName; }

    public String getContact() { return contact; }

    public int getDeliveryTime() { return deliveryTime; }

    public String getCategory() { return category; }

    public String getPaymentStatus() { return paymentStatus; }

    public int getReviewScore() { return reviewScore; }

    public boolean isActive() { return active; }

    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public void setContact(String contact) { this.contact = contact; }

    public void setDeliveryTime(int deliveryTime) { this.deliveryTime = deliveryTime; }

    public void setCategory(String category) { this.category = category; }

    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public void setReviewScore(int reviewScore) { this.reviewScore = Math.max(1, Math.min(5, reviewScore)); }

    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toFileString() {
        return id + "|" + supplierName + "|" + contact + "|" + deliveryTime + "|" + category + "|" + paymentStatus + "|" + reviewScore + "|" + active;
    }

    public String getPriorityScore() {
        double score = Math.max(0, (reviewScore * 2.0) - (deliveryTime * 0.1));
        if (score >= 8) return String.format("%.1f - PRIORITY", score);
        if (score >= 4) return String.format("%.1f - STANDARD", score);
        return String.format("%.1f - AVOID", score);
    }

    public String getActiveStatus() {
        return active ? "Yes" : "No";
    }
}