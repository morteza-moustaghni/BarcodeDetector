package com.example.morta.barcodedetector;

import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EanToProductName extends AsyncTask<String, Void, String> {
    private String product_name;
    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL("http://api.ean-search.org/api?token=tciea15&op=barcode-lookup&ean=" + params[0]);
            InputStream is = url.openStream();
            int ptr = 0;
            StringBuffer apiResult = new StringBuffer();
            while ((ptr = is.read()) != -1) {
                apiResult.append((char) ptr);
            }
            product_name = apiResult.toString();
            Pattern p = Pattern.compile("<name>(.*)</name>");
            Matcher m = p.matcher(apiResult);
            if (m.find()) {
                product_name = m.group(1);
            } else { product_name = "product name not found"; }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return product_name;
    }
}
