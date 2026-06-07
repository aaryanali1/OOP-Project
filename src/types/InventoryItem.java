package types;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InventoryItem implements FileWrite {
    public static int GOOD_THRESHOLD;
    public static int OK_THRESHOLD;
    public static int BAD_THRESHOLD;
    private int id;
    private String productName;
    private double buyPrice;
    private double sellPrice;
    private int totalSales;
    private int stockQuantity;
    private String inventoryStatus;
    private String category;
    private boolean staffFlagged;
    private boolean reordered;
    private int reorderQuantity;
    private int deliveryTime;
    private boolean delivered;

    public InventoryItem(int id, String productName, double buyPrice, double sellPrice, int totalSales, int stockQuantity, String category, boolean staffFlagged, int deliveryTime, boolean delivered, boolean reordered, int reorderQuantity) {
        this.id = id;
        this.productName = productName;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.totalSales = totalSales;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.staffFlagged = staffFlagged;
        this.deliveryTime = deliveryTime;
        this.delivered = delivered;
        this.reordered = reordered;
        this.reorderQuantity = reorderQuantity;
        calculateStatus();
    }

    public static int getGoodThreshold() { return GOOD_THRESHOLD; }

    public static int getOkThreshold() { return OK_THRESHOLD; }

    public static int getBadThreshold() { return BAD_THRESHOLD; }

    public int getId() { return id; }

    public String getProductName() { return productName; }

    public double getBuyPrice() { return buyPrice; }

    public double getSellPrice() { return sellPrice; }

    public int getTotalSales() { return totalSales; }

    public int getStockQuantity() { return stockQuantity; }

    public String getInventoryStatus() { return inventoryStatus; }

    public String getCategory() { return category; }

    public boolean isStaffFlagged() { return staffFlagged; }

    public boolean isReordered() { return reordered; }

    public int getReorderQuantity() { return reorderQuantity; }

    public int getDeliveryTime() { return deliveryTime; }

    public boolean isDelivered() { return delivered; }

    public static void setGoodThreshold(int goodThreshold) { GOOD_THRESHOLD = goodThreshold; }

    public static void setOkThreshold(int okThreshold) { OK_THRESHOLD = okThreshold; }

    public static void setBadThreshold(int badThreshold) { BAD_THRESHOLD = badThreshold; }

    public void setProductName(String productName) { this.productName = productName; }

    public void setBuyPrice(double buyPrice) { this.buyPrice = buyPrice; }

    public void setSellPrice(double sellPrice) { this.sellPrice = sellPrice; }

    public void setTotalSales(int totalSales) { this.totalSales = totalSales; }

    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public void setInventoryStatus(String inventoryStatus) { this.inventoryStatus = inventoryStatus; }

    public void setCategory(String category) { this.category = category; }

    public void setStaffFlagged(boolean staffFlagged) { this.staffFlagged = staffFlagged; }

    public void setReordered(boolean reordered) { this.reordered = reordered; }

    public void setReorderQuantity(int reorderQuantity) { this.reorderQuantity = reorderQuantity; }

    public void setDeliveryTime(int deliveryTime) { this.deliveryTime = deliveryTime; }

    public void setDelivered(boolean delivered) { this.delivered = delivered; }

    public void calculateStatus() {
        if (stockQuantity > GOOD_THRESHOLD) this.inventoryStatus = "GOOD";
        else if (stockQuantity > OK_THRESHOLD) this.inventoryStatus = "OK";
        else this.inventoryStatus = "BAD";
        if (staffFlagged) this.inventoryStatus += " (FLAGGED)";
    }

    public int estimatedDailySales() {
        if (totalSales <= 0) return -1;
        int estimatedDailySales = Math.max(1, totalSales / 30);
        return stockQuantity / estimatedDailySales;
    }

    public String getPredictedOutOfStock() {
        int days = estimatedDailySales();
        if (days < 0) return "N/A";
        if (days == 0) return "Out of Stock";
        LocalDate date = LocalDate.now().plusDays(days);
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void recordSale(int quantity) {
        totalSales += quantity;
        stockQuantity = Math.max(0, stockQuantity - quantity);
        calculateStatus();
    }

    @Override
    public String toFileString() {
        return id + "|" + productName + "|" + buyPrice + "|" + sellPrice + "|" + totalSales + "|" + stockQuantity + "|" + inventoryStatus + "|" + category + "|" + staffFlagged + "|" + reordered + "|" + reorderQuantity + "|" + deliveryTime + "|" + delivered;
    }
}
