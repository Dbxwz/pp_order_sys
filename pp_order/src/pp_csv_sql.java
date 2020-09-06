import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class pp_csv_sql {
    public static ArrayList<String> read_info(String filePath){
        //filePath = "data/test1.csv";
        ArrayList<String> allString = new ArrayList<>();
        try {
            CsvReader csvReader = new CsvReader(filePath);

            // 读取时忽略表头,如果不写默认是读取表头的
            //System.out.println(csvReader.getHeaders());
            while (csvReader.readRecord()){
                String instr = csvReader.getRawRecord();
                //System.out.println(instr);
                allString.add(instr);
                //System.out.println(csvReader.get(1));
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allString;
    }

    public static void write_info(String filePath, String[] headers, String[] content) {
        //filePath = "data/test1.csv";

        try {
            CsvReader csvReader = new CsvReader(filePath);//先读一遍
            ArrayList<String> allString = new ArrayList<>();
            //System.out.println(csvReader.readHeaders());
            while (csvReader.readRecord()){
                String instr = csvReader.getRawRecord();
                //System.out.println(instr);
                allString.add(instr);
            }
            csvReader.close();

            // 创建CSV写对象
            CsvWriter csvWriter = new CsvWriter(filePath,',', Charset.forName("UTF-8"));
            //headers = new String[]{"num", "name", "age"};// 写表头
            csvWriter.writeRecord(headers);
            for (int i=0;i < allString.size(); i++){
                csvWriter.writeRecord(allString.get(i).split(","));
            }
            //String[] content = {"12365","shan","34"};
            csvWriter.writeRecord(content);
            csvWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void rm_info(String filePath, String[] headers, String content){
        //filePath = "data/test1.csv";
        try {
            CsvReader csvReader = new CsvReader(filePath);//先读一遍
            ArrayList<String> allString = new ArrayList<>();
            //System.out.println(csvReader.readHeaders());
            while (csvReader.readRecord()){
                String instr = csvReader.getRawRecord();
                if (instr != content){
                    allString.add(instr);
                }
            }
            csvReader.close();

            // 创建CSV写对象
            CsvWriter csvWriter = new CsvWriter(filePath,',', Charset.forName("UTF-8"));
            csvWriter.writeRecord(headers);
            for (int i=0;i < allString.size(); i++){
                csvWriter.writeRecord(allString.get(i).split(","));
            }
            csvWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
