package com.example.denghaoqin.virtualstock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class sellbuy extends AppCompatActivity {


    Stock gamemodels[]=new Stock[6];
    double fund;
    int stocknumber;
    int difficulty;
    int fromwhicharchive;
    EditText stocksold;
    TextView stockowned;
    TextView moneyspent;
    TextView moneygot;
    Button up;
    Button down;
    EditText stockbought;
    long buying=0;
    long selling=0;
    Button tosell;
    Button tobuy;
    TextView fundtospend;

    class Stock
    {
        double thisprice;
        double lastprice;
        String name;
        int number;
        double fluctuation;
        double risk;
        long owning;
        double interest;
        boolean notbankrupt;
        boolean sellable;
        boolean stoprising;
        boolean stopdeclining;
        long initiallybought;
        Stock(String called,int no,int difficulty)
        {
            name=called;
            Random r=new Random();
            thisprice=150-150*r.nextDouble();
            lastprice=0;
            number=no;
            risk=0.5*r.nextDouble()-(2-(double)difficulty)*0.15;
            fluctuation=0.2*r.nextDouble();
            owning=0;
            interest=r.nextDouble();
            notbankrupt=true;
            sellable=false;
            stoprising=false;
            stopdeclining=false;
        }
    }
    public Stock[] receivenecessaryinfo(Bundle extras)
    {
        Stock stock[]=new Stock[6];
        int difficulty;

        difficulty=extras.getInt("difficulty");
        for(int i=0;i<6;i++)
        {
            stock[i]=new Stock("",i,difficulty);
            stock[i].thisprice=extras.getDouble("thisprice"+i);
            stock[i].owning=extras.getLong("owning"+i);
            stock[i].fluctuation=extras.getDouble("fluctuation"+i);
            stock[i].initiallybought=extras.getLong("initiallybought"+i);
            stock[i].interest=extras.getDouble("interest"+i);
            stock[i].notbankrupt=extras.getBoolean("notbankrupt"+i);
            stock[i].risk=extras.getDouble("risk"+i);
            stock[i].sellable=extras.getBoolean("sellable"+i);
            stock[i].stoprising=extras.getBoolean("stoprising"+i);
            stock[i].stopdeclining=extras.getBoolean("stopdeclining"+i);

        }
        return stock;


    }


    public void necessaryinfo(Stock stock[],double fund,int thestock,int difficulty,int fromwhicharchive)
    {
        Intent intent=new Intent(sellbuy.this,selectstock.class);
        for(int i=0;i<6;i++)
        {
            intent.putExtra("thisprice" + stock[i].number, stock[i].thisprice);
            intent.putExtra("owning"+stock[i].number,stock[i].owning);
            intent.putExtra("fluctuation"+stock[i].number,stock[i].fluctuation);
            intent.putExtra("initiallybought"+stock[i].number,stock[i].initiallybought);
            intent.putExtra("interest"+stock[i].number,stock[i].interest);
            intent.putExtra("notbankrupt"+stock[i].number,stock[i].notbankrupt);
            intent.putExtra("risk"+stock[i].number,stock[i].risk);
            intent.putExtra("sellable"+stock[i].number,stock[i].sellable);
            intent.putExtra("stoprising"+stock[i].number,stock[i].stoprising);
            intent.putExtra("stopdeclining"+stock[i].number,stock[i].stopdeclining);
        }
        intent.putExtra("fund",fund);
        intent.putExtra("stocknumber",thestock);
        intent.putExtra("difficulty",difficulty);
        intent.putExtra("fromwhicharchive",fromwhicharchive);
        startActivity(intent);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellbuy);
        stockowned=(TextView)findViewById(R.id.stockowned);


        tosell=(Button)findViewById(R.id.tosell);
        Bundle extras=getIntent().getExtras();
        gamemodels=receivenecessaryinfo(extras);
        fund=extras.getDouble("fund");
        stocknumber=extras.getInt("stocknumber");
        difficulty=extras.getInt("difficulty");
        fromwhicharchive=extras.getInt("fromwhicharchive");
        stockowned.setText(""+gamemodels[stocknumber].owning);
        stocksold = (EditText)findViewById(R.id.stocksold);
        stockbought = (EditText)findViewById(R.id.stockbought);
        moneyspent = (TextView) findViewById(R.id.moneyspent);
        moneygot = (TextView) findViewById(R.id.moneygot);
        tobuy = (Button)findViewById(R.id.tobuy);
        fundtospend=(TextView)findViewById(R.id.fundtospend);

        fundtospend.setText(""+fund);


        stockbought.addTextChangedListener(new TextWatcher()
        {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
            public void afterTextChanged(Editable s)
            {

                    buying=Long.parseLong(stockbought.getText().toString());
                    moneyspent.setText(gamemodels[stocknumber].thisprice*(double)buying+"$");
                    if(gamemodels[stocknumber].thisprice*(double)buying>fund)
                    {
                        stockbought.setTextColor(getResources().getColorStateList(R.color.colorAlert));
                        moneyspent.setTextColor(getResources().getColorStateList(R.color.colorAlert));
                        tobuy.setEnabled(false);
                    }
                    else
                    {
                        stockbought.setTextColor(getResources().getColorStateList(R.color.colorNormal));
                        moneyspent.setTextColor(getResources().getColorStateList(R.color.colorNormal));
                        tobuy.setEnabled(true);
                    }





            }

        });

        stocksold.addTextChangedListener(new TextWatcher()
        {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
            public void afterTextChanged(Editable s)
            {
                selling=Long.parseLong(stocksold.getText().toString());
                moneygot.setText(gamemodels[stocknumber].thisprice*(double)selling+"$");
                if(selling>gamemodels[stocknumber].owning)
                {
                    stocksold.setTextColor(getResources().getColorStateList(R.color.colorAlert));
                    moneygot.setTextColor(getResources().getColorStateList(R.color.colorAlert));
                    tosell.setEnabled(false);
                }
                else
                {
                    stocksold.setTextColor(getResources().getColorStateList(R.color.colorNormal));
                    moneygot.setTextColor(getResources().getColorStateList(R.color.colorNormal));
                    tosell.setEnabled(true);
            }}

        });

       /*stockbought.addTextChangedListener(new TextWatcher() {
           public void afterTextChanged(Editable s) {
               buying=Long.parseLong(stocksold.getText().toString());
               moneyspent.setText(gamemodels[stocknumber].thisprice*(double)buying+"$");
           }

           public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

           public void onTextChanged(CharSequence s, int start, int before, int count) {
              if(gamemodels[stocknumber].thisprice*(double)buying>fund)
               {
                   stocksold.setTextColor(getResources().getColorStateList(R.color.colorAlert));
                   tosell.setEnabled(false);
                   up.setEnabled(false);

               }

           }

       });*/




    }
}
