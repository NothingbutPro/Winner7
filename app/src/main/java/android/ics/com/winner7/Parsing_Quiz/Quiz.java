package android.ics.com.winner7.Parsing_Quiz;

public class Quiz {

    private String id;

    private String que;

    private String ans1;

    private String ans2;

    private String ans3;

    private String ans4;

    private String ans;

    private String marks;

    private String createdate;

    private String status;
    private  String myeans;

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    private  String endtime;

    public String getMyeans() {
        return myeans;
    }

    public void setMyeans(String myeans) {
        this.myeans = myeans;
    }



    public Quiz(String id, String que, String ans1, String ans2, String ans3, String ans4, String ans, String marks, String createdate, String status ,String myeans
    ,String endtime) {
        this.id = id;
        this.que = que;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans4 = ans4;
        this.ans = ans;
        this.marks = marks;
        this.createdate = createdate;
        this.status = status;
        this.myeans = myeans;
        this.endtime = endtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQue() {
        return que;
    }

    public void setQue(String que) {
        this.que = que;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    public String getAns4() {
        return ans4;
    }

    public void setAns4(String ans4) {
        this.ans4 = ans4;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
