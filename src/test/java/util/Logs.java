package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

   public void logError(String messageLog) throws Exception {
      et.fail(messageLog);
      throw new Exception(messageLog);
   }

   public void logError(String messageLog, Exception e) throws Exception {
      et.fail(messageLog.trim().replace(":", "") + ": " + e.getMessage());
      throw new Exception(e.getMessage());
   }

   public void logFail(String messageLog) {
      et.fail(messageLog);
   }

   public boolean logAssertJsonValues(String responseJson, Map<String, Object> expectedValues) {
      try {
         ObjectMapper objectMapper = new ObjectMapper();
         JsonNode jsonNode = objectMapper.readTree(responseJson);

         for (Map.Entry<String, Object> entry : expectedValues.entrySet()) {
            String key = entry.getKey();
            Object expectedValue = entry.getValue();

            if (jsonNode.has(key)) {
               JsonNode jsonValueNode = jsonNode.get(key);
               Object jsonValue = objectMapper.treeToValue(jsonValueNode, expectedValue.getClass());

               if (!jsonValue.equals(expectedValue)) {
                  logFail("Erro: Valor para a chave ['" + key + "'] não corresponde. ESPERADO: [" + expectedValue + "] - ATUAL: [" + jsonValue + "].");
                  return false;
               }
               logPass("Valores do reponse body para key [" + key + "] - ESPERADO: [" + expectedValue + "] - ATUAL: [" + jsonValue + "]");
            } else {
               logFail("Erro: A chave ['" + key + "'] não existe no JSON.");
               return false;
            }
         }
         return true;
      } catch (Exception e) {
         logFail("Exception in logAssertJsonValues(): " + e.getMessage());
         return false;
      }
   }
}
