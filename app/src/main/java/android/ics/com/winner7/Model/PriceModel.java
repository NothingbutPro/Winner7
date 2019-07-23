package android.ics.com.winner7.Model;

import android.ics.com.winner7.Utils.SessionManager;

import java.io.Serializable;

public class PriceModel implements Serializable {

    private String id;
    private String amount;
    private String remark;
    private String winners;
    private String status;

    public PriceModel(String id, String amount, String remark, String winners, String status) {
        this.id = id;
        this.amount = amount;
        this.remark = remark;
        this.winners = winners;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWinners() {
        return winners;
    }

    public void setWinners(String winners) {
        this.winners = winners;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
