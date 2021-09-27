package com.pjiproject.genix;

import java.io.*;
import java.net.*;

public class Trivia implements Runnable {

    private String questions;

    public String getQuestions() {
        return questions;
    }

    // Get HTTP data from OpenTriviaDB
    public static String makeRequest(String urlParameters) {

        HttpURLConnection URLconnection = null;

        try {

            // Define request
            URL triviaUrl = new URL("https://opentdb.com/api.php?amount=10&encode=base64");

            URLconnection = (HttpURLConnection) triviaUrl.openConnection();
            URLconnection.setRequestProperty("accept", "application/json");
            URLconnection.setRequestMethod("POST");
            URLconnection.setUseCaches(false);
            URLconnection.setDoOutput(true);


            //Send request
            DataOutputStream wr = new DataOutputStream(URLconnection.getOutputStream());

            wr.writeBytes(urlParameters);
            wr.close();


            //Get Response
            InputStream is = URLconnection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();

            String line;
            while ((line = rd.readLine()) != null) {

                response.append(line);
                response.append('\r');

            }

            rd.close();

            //System.out.println(decodedString);

            return response.toString();

        } catch (Exception e) {

            System.out.println("Error in Trivia.makeRequest(): " + e.getMessage());
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

            System.out.println("Error in Run.makeRequest(): " + e.getMessage() + "\n");

        }

    }

}
