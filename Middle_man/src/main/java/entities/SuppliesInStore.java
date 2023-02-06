package entities;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class SuppliesInStore {
    private Supply supply;
    private int amount;
    private BigDecimal price;

    private SuppliesInStore(SuppliesInStoreBuilder builder){

        this.supply = builder.supplies;
        this.amount = builder.amount;
        this.price = builder.price;
    }
    public static class SuppliesInStoreBuilder{

        private Supply supplies;
        private int amount;
        private BigDecimal price;

        public SuppliesInStoreBuilder(Supply supply){
            this.supplies = supply;
        }

        public SuppliesInStoreBuilder setAmount(int amount){
            this.amount = amount;
            return this;
        }
        public SuppliesInStoreBuilder setPrice(BigDecimal price){
            this.price = price;
            return this;
        }
        public SuppliesInStore build(){
            return new SuppliesInStore(this);
        }
    }


    public Supply getSupplies() {
        return supply;
    }

    public void setSupplies(Supply supplies) {
        this.supply = supplies;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
