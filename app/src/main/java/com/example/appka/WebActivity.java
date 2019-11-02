package com.example.appka;

import android.app.Activity;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebActivity extends Activity {

    private TextView lblResult;



    static void  skanujKod(String kod) {
        String NAMESPACE = "http://tempuri.org/";
        String URL = "http://sekob.toliko.pl/Web/ScannerAPI.asmx";
        String SOAP_ACTION = "http://tempuri.org/ValidateBarcodeEntry";
        String METHOD_NAME = "ValidateBarcodeEntry";
        System.out.println("LOOOOOOOOOOOOOOOOOOO");
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("code", kod);// Parameter for Method
        request.addProperty("scannerId", "test");// Parameter for Method

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        System.out.println("Response::" + request.toString());


        try {
            System.out.println("LOOOOOOOOOOOOOOOOOOO2");
            androidHttpTransport.call(SOAP_ACTION, envelope);  // w tym miejscu cos sie pierdoli
            System.out.println("LOOOOOOOOOOOOOOOOOOO3");

            Object resultsRequestSOAP = envelope.bodyIn;
            System.out.println("LOOOOOOOOOOOOOOOOOOO4");


            MainActivity.tvresult.setText("Response::" + resultsRequestSOAP.toString());
            System.out.println("Response::" + resultsRequestSOAP.toString());
            // Stuff }


        } catch (Exception e) {


            System.out.println("Error" + e);

        }
    }
}



