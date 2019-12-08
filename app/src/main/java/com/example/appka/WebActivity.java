package com.example.appka;

import android.app.Activity;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class WebActivity extends Activity {

    private TextView lblResult;
    private static int timeOut=1000;


    static void  skanujKod(String kod) {
        String NAMESPACE = "http://tempuri.org/";
        String URL = "http://sekob.toliko.pl/Web/ScannerAPI.asmx";
        String SOAP_ACTION = "http://tempuri.org/ValidateBarcodeEntry";
        String METHOD_NAME = "ValidateBarcodeEntry";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("code", kod);// Parameter for Method
        request.addProperty("scannerId", "test");// Parameter for Method
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        try {
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, timeOut);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            Object resultsRequestSOAP = envelope.bodyIn;
            ;


            MainActivity.tvresult.setText("Response::" + resultsRequestSOAP.toString());
            // Stuff }


        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



