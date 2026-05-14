package types;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Item {

    public static int GOOD_THRESHOLD = 100;
    public static int OK_THRESHOLD = 80;
    public static int BAD_THRESHOLD = 50;

    private String id;
    private String productName;
    private double buyPrice;
    private double sellPrice;
    private int totalSales;
    private int monthlySales;
    private int weeklySales;
    private int dailySales;
    private int stockQuantity;
    private String supplier;
    private String inventoryStatus;
    private String category;
    private boolean staffFlagged;
    private boolean reordered;
    private LocalDateTime reorderTime;
    private int deliveryTime;
    private boolean delivered;

    public Item(String id, String productName, double buyPrice, double sellPrice, int totalSales, int monthlySales, int weeklySales, int dailySales, int stockQuantity, String supplier, String category) {
        this.id = id;
        this.productName = productName;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.totalSales = totalSales;
        this.monthlySales = monthlySales;
        this.weeklySales = weeklySales;
        this.dailySales = dailySales;
        this.stockQuantity = stockQuantity;
        this.supplier = supplier;
        this.category = category;
        this.staffFlagged = false;
        this.reordered = false;
        this.deliveryTime = 0;
        this.delivered = true;
        calcuateStatus();
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public int getMonthlySales() {
        return monthlySales;
    }

    public int getWeeklySales() {
        return weeklySales;
    }

    public int getDailySales() {
        return dailySales;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public String getCategory() {
        return category;
    }

    public boolean isStaffFlagged() {
        return staffFlagged;
    }

    public boolean isReordered() {
        return reordered;
    }

    public LocalDateTime getReorderTime() {
        return reorderTime;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public void setMonthlySales(int monthlySales) {
        this.monthlySales = monthlySales;
    }

    public void setWeeklySales(int weeklySales) {
        this.weeklySales = weeklySales;
    }

    public void setDailySales(int dailySales) {
        this.dailySales = dailySales;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStaffFlagged(boolean staffFlagged) {
        staffFlagged = staffFlagged;
    }

    public void setReordered(boolean reordered) {
        this.reordered = reordered;
    }

    public void setReorderTime(LocalDateTime reorderTime) {
        this.reorderTime = reorderTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public void calcuateStatus() {
        if(stockQuantity > GOOD_THRESHOLD) this.inventoryStatus = "Good";
        else if (stockQuantity > OK_THRESHOLD) this.inventoryStatus = "Ok";
        else this.inventoryStatus = "Bad";
        if(staffFlagged) this.inventoryStatus += " (Flagged)";
    }

    public int predictOutofStock() {
        if(weeklySales <= 0) return -1;
        return (int) (stockQuantity / (weeklySales / 7.0));
    }

    public String getPredictedOutofStock() {
        int days = predictOutofStock();
        if(days < 0) return "n/a";
        if(days == 0) return "Out of Stock";
        LocalDate date = LocalDate.now().plusDays(days);
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public boolean isDeliveryOverdue() {
        if(reorderTime == null || delivered) return false;
        LocalDateTime expectedDelivery = reorderTime.plusDays(deliveryTime);
        return LocalDateTime.now().isAfter(expectedDelivery);
    }

    public void recordSale(int quantity) {
        totalSales += quantity;
        monthlySales += quantity;
        weeklySales += quantity;
        dailySales += quantity;
        stockQuantity = Math.max(0, stockQuantity - quantity);
        calcuateStatus();
    }

    public String toFileFormat() {
        return id + "|" + productName + "|" + buyPrice + "|" + sellPrice + "|" + totalSales + "|" + monthlySales + "|" + weeklySales + "|" + dailySales + "|" + stockQuantity + "|" + supplier + "|" + inventoryStatus + "|" + category + "|" +staffFlagged + "|" + reordered + "|" + reorderTime + "|" + deliveryTime + "|" + delivered;
    }

}
