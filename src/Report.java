// Abstract class for all report types — demonstrates abstract class requirement
public abstract class Report {

    protected String reportId;
    protected String generatedOn;
    protected String generatedBy;

    private static int reportCounter = 800;

    public Report(String generatedBy) {
        this.reportId = "RPT-" + reportCounter++;
        this.generatedBy = generatedBy;
        this.generatedOn = java.time.LocalDate.now().toString();
    }

    // Abstract method — each report defines its own generation logic
    public abstract void generateReport();

    // Abstract method — summary varies by report type
    public abstract String getSummary();

    public String getReportId()    { return reportId; }
    public String getGeneratedOn() { return generatedOn; }
    public String getGeneratedBy() { return generatedBy; }
}