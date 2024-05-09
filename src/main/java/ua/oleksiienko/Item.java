package ua.oleksiienko;

public class Item {
    private final String producedBy;
    private final String serialNumber;

    public Item(String producedBy, int serialNumber) {
        this.producedBy = producedBy;
        this.serialNumber = producedBy.charAt(0) + "#" + serialNumber;
    }

    public String serialNumber() {
        return serialNumber;
    }

    public String producedBy() {
        return producedBy;
    }
}
