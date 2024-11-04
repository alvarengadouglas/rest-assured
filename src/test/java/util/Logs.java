package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.http.Header;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Logs {
   public ExtentReports extent = new ExtentReports();
   ExtentTest et = null;

   String lineSeparator = "=========================================================";
   String div = "<div style='text-align:center; font-weight:bold;'>";
   String divError = "<div style='text-align:center;font-weight:bold;font-size: 16px; color:red;'>";
   String fechaDiv = "</div>";
   String quebraLibra = "<br>";
   String h1 = "<h1>#</h1>";
   String badge = "<span class='badge badge-info'>#</span>";


   public void createReport(String reportName) {
      ExtentSparkReporter spark = new ExtentSparkReporter("target/Reports/" + reportName + ".html");
      spark.config().setTheme(Theme.DARK);
      extent.attachReporter(spark);
   }

   public void createTest(String testName){
      et = extent.createTest(testName);
   }

   public void logInfo(String messageLog){
      et.info(messageLog);
   }

   public void logInfoSeparator(String messageLog){
      et.info(div + lineSeparator + quebraLibra + messageLog + quebraLibra + lineSeparator +  fechaDiv);
   }

   public void logInfoAlignCenter(String messageLog){
      et.info(div + messageLog + fechaDiv);
   }

   public void logPass(String messageLog){
      et.pass(messageLog);
   }

   public void logInfoJson(String json){
      et.info(MarkupHelper.createCodeBlock(json, CodeLanguage.JSON));
   }

   public void logInfoResponseHeaders(List<Header> headerList){
      String[][] arrayHeaders = headerList.stream().map( header -> new String[] {header.getName(), header.getValue()})
              .toArray(String[][] :: new);
      et.info(MarkupHelper.createTable(arrayHeaders));
   }

   public void logInfoTable(Map<String, ?> dataTable){
      List<Header> headerList = new ArrayList<>();
      dataTable.forEach( (k, v) -> headerList.add(new Header(k, String.valueOf(v))));

      String[][] arrayData;
      arrayData = headerList.stream().map( header -> new String[] {header.getName(), header.getValue()}).toArray(String[][]::new);
      et.info(MarkupHelper.createTable(arrayData));
   }

   public void logError(String messageLog, AssertionError a) throws Exception {
      et.fail(messageLog.trim().replace(":", "") + ": " + a.getMessage());
      throw new Exception(a.getMessage());
   }

   public void logError(String messageLog, Exception e) throws Exception {
      et.fail(messageLog.trim().replace(":", "") + ": " + e.getMessage());
      throw new Exception(e.getMessage());
   }
}
