package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Payment {

    @SerializedName("payment")
    @Expose
    private List<Payment_> payment = null;

    public List<Payment_> getPayment() {
        return payment;
    }

    public void setPayment(List<Payment_> payment) {
        this.payment = payment;
    }

}
