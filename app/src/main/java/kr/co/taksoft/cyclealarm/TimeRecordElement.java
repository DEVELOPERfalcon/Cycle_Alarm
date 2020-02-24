package kr.co.taksoft.cyclealarm;

public class TimeRecordElement {
    private int num;
    private String mainTime;
    private String subTime;

    public TimeRecordElement(int num, String mainTime, String subTime) {
        this.num = num;
        this.mainTime = mainTime;
        this.subTime = subTime;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getMainTime() {
        return mainTime;
    }

    public void setMainTime(String mainTime) {
        this.mainTime = mainTime;
    }

    public String getSubTime() {
        return subTime;
    }

    public void setSubTime(String subTime) {
        this.subTime = subTime;
    }
}
