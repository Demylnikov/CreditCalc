package com.example.denis.creditcalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static double creditSum;
    private static int payment;
    private static double zinsen;
    private static int months = 0;
    static Calendar calendar = Calendar.getInstance();
    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
    static String formattedDate;



    ListView list;
    private List<String> List_file;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText inputCreditSum = (EditText) findViewById(R.id.editTextKreditsumme);
        final EditText inputProzent = (EditText) findViewById(R.id.editTextProzent);
        final EditText inputPayment = (EditText) findViewById(R.id.editTextPayment);
        button = (Button) findViewById(R.id.calculateButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String creditSumString = inputCreditSum.getText().toString();
                String paymentString = inputPayment.getText().toString();
                String prozentString = inputProzent.getText().toString();
                try {
                    creditSum = Double.parseDouble(creditSumString);
                    zinsen = Double.parseDouble(prozentString);
                    payment = Integer.parseInt(paymentString);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                calculateCredit();
            }
        });

        List_file = new ArrayList<String>();
        list = (ListView)findViewById(R.id.listView);

        //DrawListView();

    }

    private static String calculateDate(int kreditdauerM) {
        try {
            Date d = sdf.parse("08/2016");
            calendar.setTime(d);
            calendar.add(Calendar.MONTH, kreditdauerM);
            formattedDate = sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    private void calculateCredit() {

        List_file.clear();

        //String creditSumString = inputCreditSum.getText().toString();
        //String paymentString = inputPayment.getText().toString();
        //String prozentString = inputProzent.getText().toString();
        try {
          //  creditSum = Double.parseDouble(creditSumString);
          //  zinsen = Double.parseDouble(prozentString);
          //  payment = Integer.parseInt(paymentString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (creditSum > payment) {
            double tilgung = payment - (creditSum*(zinsen/100))/12;
            creditSum = creditSum - tilgung;
            months++;
            System.out.println(months + " Restbatrag " + round(creditSum,2) + "Tilgung" + round(tilgung,2) + " Zinsen " + round((payment-tilgung),2));
            List_file.add(months + " Restbetrag " + round(creditSum,2) + " Tilgung " + round(tilgung,2) + " Zinsen " + round((payment-tilgung),2));
        }

        int kreditdauerM = months+1;
        double kreditdauerJ = ((double)kreditdauerM)/12;

        System.out.println(kreditdauerM + " Letzter Monat mit Restbetrag von " + round(creditSum,2) + " Kredit ausbezahlt");
        System.out.println("----------------------");
        System.out.println("Kreditdauer " + kreditdauerM + " Monate oder ca. " + round(kreditdauerJ,1) + " Jahre");
        System.out.println("Kredit endet am " + calculateDate(kreditdauerM));
        months = 0;
        List_file.add("lololo");
        DrawListView();
        list.setSelection(List_file.size() - 1); //should it be like that?

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void DrawListView() {


        list.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, List_file));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //something should happen here
            }
        });
    }
}
