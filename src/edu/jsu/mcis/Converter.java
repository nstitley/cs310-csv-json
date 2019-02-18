package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings, and which values should be encoded as integers!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
    
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including example code.
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {

        String strFinal = "";

        try {
            CSVReader reader = new CSVReader(new StringReader(csvString));
            
            List<String[]> full = reader.readAll();

            JSONObject jsonObj = new JSONObject();
            JSONArray row = new JSONArray();
            JSONArray col = new JSONArray();
            JSONArray dataHeaders = new JSONArray();
            JSONArray data = new JSONArray();

            for(int i = 0; i < full.get(0).length; ++i) {

                col.add(full.get(0)[i]);

            }

            for (int z = 1; z < full.size(); ++z) {

                row.add(full.get(z)[0]);
                
            }
            
            int info;
            
            for(int i = 1; i < full.size(); i++) {

                for(int z = 1; z < full.get(0).length; ++z) {

                    info = Integer.parseInt(full.get(i)[z]);
                    dataHeaders.add(info);

                }

                data.add(dataHeaders.clone());
                
                dataHeaders.clear();

            }

            jsonObj.put("colHeaders", col);
            jsonObj.put("rowHeaders", row);
            jsonObj.put("data", data);
            
            strFinal = JSONValue.toJSONString(jsonObj);
        }        
        catch(Exception e) { return e.toString(); }

        return strFinal.trim();
    }

    public static String jsonToCsv(String jsonString) {

        String strFinal = "";

        try {
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            JSONParser parser = new JSONParser();
            
            JSONObject jsonObj = (JSONObject)parser.parse(jsonString);
            JSONArray col = (JSONArray) (jsonObj.get("colHeaders"));
            JSONArray row = (JSONArray) (jsonObj.get("rowHeaders"));
            JSONArray data = (JSONArray) (jsonObj.get("data"));
            
            String[] arrayC = new String[col.size()];
            String[] arrayD = new String[col.size()];

            for(int i = 0; i < col.size(); ++i) {

                arrayC[i] = (String) col.get(i);

            }

            csvWriter.writeNext(arrayC);

            for(int i = 0; i < row.size(); ++i) {

                arrayD[0] = (String) row.get(i);
                JSONArray rowArray = (JSONArray)data.get(i);

                    for(int z = 0; z < rowArray.size() ; ++z) {

                        arrayD[z + 1] = rowArray.get(z).toString();

                    }

            csvWriter.writeNext(arrayD);
            }

            strFinal = writer.toString();
        }
        catch(Exception e) { return e.toString(); }

        return strFinal.trim();
    }
} 