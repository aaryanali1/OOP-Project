package types;

public class Supplier {

    private String name;
    private String contact;
    private int deliveryTime;
    private boolean pendingPayment;
    private int reviewScore;

    public Supplier(String name, String contact, int deliveryTime, boolean pendingPayment, int reviewScore) {
        this.name = name;
        this.contact = contact;
        this.deliveryTime = deliveryTime;
        this.pendingPayment = pendingPayment;
        this.reviewScore = reviewScore;
        this.reviewScore = Math.max(1, Math.min(5, reviewScore));
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public boolean isPendingPayment() {
        return pendingPayment;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setPendingPayment(boolean pendingPayment) {
        this.pendingPayment = pendingPayment;
    }

    public void setReviewScore(int reviewScore) {
        this.reviewScore = reviewScore;
    }

    public String toFileFormat() {
        return name + "|" + contact + "|" + deliveryTime + "|" + pendingPayment + "|" + reviewScore;
    }
}
