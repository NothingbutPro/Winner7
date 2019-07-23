package android.ics.com.winner7.Model;

import java.io.Serializable;

public class TestModel implements Serializable {

    private String id;
    private String pdf;
    private String date;
    private String type;

    public TestModel(String id, String pdf, String date, String type) {
        this.id = id;
        this.pdf = pdf;
        this.date = date;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
