package com.example.appka;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import static com.example.appka.MainActivity.myDB;

public class WebActivity extends Activity {
    //private DataBaseHelper listOfTickets;
    private TextView lblResult;
    private static String NAMESPACE = "http://tempuri.org/";

//    static void skanujKod(String kod) {
//        String URL = "http://sekob.toliko.pl/Web/ScannerAPI.asmx";
//        String SOAP_ACTION = "http://tempuri.org/ValidateBarcodeEntry";
//        String METHOD_NAME = "ValidateBarcodeEntry";
//        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//        request.addProperty("code", kod);// Parameter for Method
//        request.addProperty("scannerId", MainActivity.DEVICEID);// Parameter for Method
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        envelope.dotNet = true;
//        envelope.setOutputSoapObject(request);
//
//
//        try {
//            int timeOut = 1000;
//            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, timeOut);
//            androidHttpTransport.call(SOAP_ACTION, envelope);
//            Object resultsRequestSOAP = envelope.bodyIn;
//
//
//            Log.d("Testing", "Response::" + resultsRequestSOAP.toString());
//            MainActivity.tvresult.setText("Response::" + resultsRequestSOAP.toString());
//            // Stuff }
//
//        } catch (HttpResponseException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    static void getBarcodes(String deviceId) {
        String URL = "http://sekob.toliko.pl/WebAS/Service.asmx";
        String SOAP_ACTION = "http://tempuri.org/GetBarcodes";
        String METHOD_NAME = "GetBarcodes";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("deviceId", deviceId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        try {
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject resultRequestSoap = (SoapObject) envelope.bodyIn;
            Log.d("***********", request.toString());
            Log.d("***********", resultRequestSoap.toString());
            extractDataFromXml(resultRequestSoap);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void extractDataFromXml(SoapObject resultRequestSoap) {
        //SoapObject root = (SoapObject) resultRequestSoap.getProperty(0);
        SoapObject s_deals = (SoapObject) resultRequestSoap.getProperty("GetBarcodesResult");

        //System.out.println("********Count : "+ s_deals.getPropertyCount());

        for (int i = 0; i < s_deals.getPropertyCount(); i++)
        {
            Object property = s_deals.getProperty(i);
            if (property instanceof SoapObject)
            {
                SoapObject category_list = (SoapObject) property;
                String code =           category_list.getProperty("C" ).toString();
                String maxEntryCount =  category_list.getProperty("E" ).toString();
                String curEntryCount =  category_list.getProperty("CE").toString();
                String validFrom =      category_list.getProperty("F" ).toString();
                String validTo =        category_list.getProperty("T" ).toString();
                String sektor =         category_list.getProperty("Se").toString();
                String rzad =           category_list.getProperty("Rz").toString();
                String miejsce =        category_list.getProperty("Mi").toString();
                String pesel =          category_list.getProperty("PE").toString();
                String imie =           category_list.getProperty("Im").toString();
                String nazwisko =       category_list.getProperty("Na").toString();
                String opis =           category_list.getProperty("O" ).toString();
                String rodzaj =         category_list.getProperty("Ro").toString();
                String numer =          category_list.getProperty("No").toString();
                String status =         category_list.getProperty("St").toString();
                String event =          category_list.getProperty("Ev").toString();
                if(myDB.checkCodIsInDb(curEntryCount, code))
                     myDB.inserData(code, maxEntryCount, curEntryCount, validFrom, validTo, sektor, rzad, miejsce, pesel, imie, nazwisko, opis, rodzaj, numer, status, event);
                else System.out.println("juz jest");
            }
        }
    }

}



