package com.tonosseum.framework.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public class HTTPRequest {
	public static String requestUrl(String url) throws IOException {
        HttpConnection connection = null;
        InputStream is = null;
        int rc;

        try {
            connection = (HttpConnection)Connector.open(url);

            // Getting the response code will open the connection,
            // send the request, and read the HTTP response headers.
            // The headers are stored until requested.
            rc = connection.getResponseCode();
            if (rc != HttpConnection.HTTP_OK) {
                throw new IOException("HTTP response code: " + rc);
            }

            is = connection.openInputStream();

            // Get the ContentType
            String type = connection.getType();

            // Get the length and process the data
            int len = (int)connection.getLength();
        	String retVal = "";
            if (len > 0) {
                int actual = 0;
                int bytesread = 0;
                byte[] data = new byte[len];
                while ((bytesread != len) && (actual != -1)) {
                   actual = is.read(data, bytesread, len - bytesread);
                   bytesread += actual;
                }
                retVal = new String(data);
            } else {
                int ch;
                while ((ch = is.read()) != -1) {}
            }
            return retVal;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Not an HTTP URL");
        } finally {
            if (is != null)
                is.close();
            if (connection != null)
                connection.close();
        }
    }
}
