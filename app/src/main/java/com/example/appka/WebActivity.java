package com.example.appka;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebActivity extends Activity {
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String URL = "http://sekob.toliko.pl/Web/ScannerAPI.asmx";
    private static final String SOAP_ACTION = "http://tempuri.org/ValidateBarcodeEntry";
    private static final String METHOD_NAME = "ValidateBarcodeEntry";
    private TextView lblResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        MainActivity.tvresult.setText("dza ");
        super.onCreate(savedInstanceState);
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo weightProp =new PropertyInfo();
        request.addProperty("scannerId", "test");// Parameter for Method
        request.addProperty("code", "sadfascas");// Parameter for Method

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);

            //SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
          //  SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
            SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;

            System.out.println(resultsRequestSOAP.toString());
            MainActivity.tvresult.setText("Response::"+resultsRequestSOAP.toString());
            System.out.println("Response::"+resultsRequestSOAP.toString());




        } catch (Exception e) {
            System.out.println("Error"+e);

        }
    }
}