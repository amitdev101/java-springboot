package dsa;

public class BuilderPatternOrder {
    private final String productId;
    private final int quantity;
    private final String userId;
    private final String shippingAddress;

    private BuilderPatternOrder(Builder builder) {
        this.productId = builder.productId;
        this.quantity = builder.quantity;
        this.userId = builder.userId;
        this.shippingAddress = builder.shippingAddress;
    }

    public static class Builder {
        private final String productId; // Required
        private final int quantity;     // Required
        private String userId;          // Optional
        private String shippingAddress; // Optional

        public Builder(String productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder shippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }

        public BuilderPatternOrder build() {
            return new BuilderPatternOrder(this);
        }
    }
}

