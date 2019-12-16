package com.example.appka;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

import static com.example.appka.MainActivity.buffer;

public class ValidateCode extends TimerTask {

    private static String NAMESPACE = "http://tempuri.org/";
    String URL = "http://sekob.toliko.pl/Web/ScannerAPI.asmx";
    String SOAP_ACTION = "http://tempuri.org/ValidateBarcodeEntry";
    String METHOD_NAME = "ValidateBarcodeEntry";

    @Override
    public void run() {
        List<String> myBuffer = buffer.getAllData();
        System.out.println(myBuffer);
        if (myBuffer != null) {
            for (String code : myBuffer) {
                System.out.println(code);
                validateCode(code);
                buffer.delteData(code);
                MainActivity.status.setText("ilosc kodow w buforze: " + buffer.iloscWBuforze());
            }
        }
    }

    private void validateCode(String code) {
        System.out.println(code);
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("code", code);// Parameter for Method
        request.addProperty("scannerId", MainActivity.DEVICEID);// Parameter for Method
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        try {
            int timeOut = 1000;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, timeOut);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            Object resultsRequestSOAP = envelope.bodyIn;

            Log.d("Web", "Response::" + resultsRequestSOAP.toString());

        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
