package com.pjiproject.genix;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

// import org.json.*;
// import org.json.simple.parser.*;


public class Trivia implements Runnable {

    private String questions;

    public String getQuestions() {
        return questions;
    }
    // public void setQuestions(String questions) { this.questions = questions; }


    // Get HTTP data from OpenTriviaDB
    public static String makeRequest(String urlParameters) {

        HttpURLConnection URLconnection = null;

        try {

            // Define request
            URL triviaUrl = new URL("https://opentdb.com/api.php?amount=10");

            URLconnection = (HttpURLConnection) triviaUrl.openConnection();
            URLconnection.setRequestProperty("accept", "application/json");
            URLconnection.setRequestMethod("POST");
            URLconnection.setUseCaches(false);
            URLconnection.setDoOutput(true);


            //Send request
            DataOutputStream wr = new DataOutputStream(URLconnection.getOutputStream());

            wr.writeBytes(urlParameters);
            wr.close();


            //Get Response in JSON format
            InputStream is = URLconnection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            // If using json to parse...
            // JSONParser jsonParser = new JSONParser();
            // Object httpData = jsonParser.parse(rd.readLine());

            StringBuilder response = new StringBuilder();

            String line;
            while ((line = rd.readLine()) != null) {

                response.append(line);
                response.append('\r');

            }

            rd.close();

            // DEBUG TOOL LOOOOOL: System.out.println(response.toString());

            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if (URLconnection != null) {

                URLconnection.disconnect();

            }

        }

    }


    // Threadding
    public void run() {

        try {

            questions = makeRequest("");

        } catch (Exception e) {

            System.out.println("Error in makeRequest()\n");

        }

    }

}
