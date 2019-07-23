package android.ics.com.winner7.Model;

import java.io.Serializable;

public class RefferModel implements Serializable {

    private String userId;
    private String name;
    private String email;
    private String password;
    private String mobile;
    private String address;
    private String type;
    private String reffercode;
    private String refferby;
    private String bankstatus;
    private String walletbal;
    private String image;
    private String status;

    public RefferModel(String userId, String name, String email, String password, String mobile, String address, String type, String reffercode, String refferby, String bankstatus, String walletbal, String image, String status) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.address = address;
        this.type = type;
        this.reffercode = reffercode;
        this.refferby = refferby;
        this.bankstatus = bankstatus;
        this.walletbal = walletbal;
        this.image = image;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReffercode() {
        return reffercode;
    }

    public void setReffercode(String reffercode) {
        this.reffercode = reffercode;
    }

    public String getRefferby() {
        return refferby;
    }

    public void setRefferby(String refferby) {
        this.refferby = refferby;
    }

    public String getBankstatus() {
        return bankstatus;
    }

    public void setBankstatus(String bankstatus) {
        this.bankstatus = bankstatus;
    }

    public String getWalletbal() {
        return walletbal;
    }

    public void setWalletbal(String walletbal) {
        this.walletbal = walletbal;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
