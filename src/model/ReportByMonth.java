package model;

public class ReportByMonth {

    private String reportMonth;
    int reportCount;
    private String reportType;


    public ReportByMonth(){}

    public void setReportType(String type){
        this.reportType = type;
    }

    public String getReportType(){
        return reportType;
    }

    public void setReportMonth(String month){
        this.reportMonth = month;
    }

    public String getReportMonth(){
        return reportMonth;
    }

    public void setReportCount(int count){
        this.reportCount = count;
    }

    public int getReportCount(){
        return reportCount;
    }


}
