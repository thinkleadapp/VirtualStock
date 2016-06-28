package com.example.denghaoqin.virtualstock;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;

public class selectstock extends AppCompatActivity {

    final double dt=0.00000026;
    int difficulty;
    String stocknames[]=new String[6];
    int fromwhicharchive;
    File temporarystock[]=new File[6];
    File totemporarystock[]=new File[6];
    Vtime vtnow=new Vtime();
    Vtime vtmemo=new Vtime();
    Stock gamemodels[]=new Stock[6];
    Button fivemin;
    Button fifteenmin;
    Button thirtymin;
    Button nextday;
    Button save;
    Button stockbuttons[]=new Button[6];
    int indicate;
    double asset;
    double fund;
    TextView prices[]=new TextView[6];


    class Vtime
    {
        long year;
        long month;
        long day;
        long hour;
        long min;
        long absolutetime;

        Vtime()
        {
            year=0;
            month=1;
            day=1;
            hour=8;
            min=0;
            absolutetime=0;
        }

        void timeplus(long plusmin)
        {
            min=min+plusmin;


            if(min>=60)
            {
                hour++;
                min=min-60;
            }


            if(hour>14||(hour==14&&min>0))
            {
                hour=8;
                day++;
            }


            if(day>daysofmonth(month,year))
            {
                day=day-daysofmonth(month,year);
                month++;
            }

            if(month>12)
            {
                month=month-12;
                year++;
            }}


        void nextday()
        {
            hour=8;
            min=0;
            day++;
            if(day>daysofmonth(month,year))
            {
                day=1;
                month++;
            }

            if(month>12)
            {
                month=1;
                year++;
            }


        }

    }

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




    public void equaltime()
    {
        vtmemo.absolutetime=vtnow.absolutetime;
        vtmemo.day=vtnow.day;
        vtmemo.hour=vtnow.hour;
        vtmemo.min=vtnow.min;
        vtmemo.month=vtnow.month;
        vtmemo.year=vtnow.year;
        
        
    }
    public void equaltimereverse()
    {
        vtnow.absolutetime=vtmemo.absolutetime;
        vtnow.day=vtmemo.day;
        vtnow.hour=vtmemo.hour;
        vtnow.min=vtmemo.min;
        vtnow.month=vtmemo.month;
        vtnow.year=vtmemo.year;




    }


    public double roundup (double i)
    {
        int a=(int)(i*100);
        double b=(double)a/100;
        return b;

    }

    public double calassets(Stock[] stock,double fund)
    {
        double asset=fund;
        for(int i=0;i<=5;i++) asset=asset+stock[i].owning*stock[i].thisprice;
        return asset;
    }

    public Stock[] receivenecessaryinfo(Bundle extras)
    {
        Stock stock[]=new Stock[6];
        int difficulty;

        difficulty=extras.getInt("difficulty");
        for(int i=0;i<=5;i++)
        {
            stock[i]=new Stock("",i,difficulty);
            stock[i].thisprice=extras.getDouble("thisprice"+stock[i].number);
            stock[i].owning=extras.getLong("owning"+stock[i].number);
            stock[i].fluctuation=extras.getDouble("fluctuation"+stock[i].number);
            stock[i].initiallybought=extras.getLong("initiallybought"+stock[i].number);
            stock[i].interest=extras.getDouble("interest"+stock[i].number);
            stock[i].notbankrupt=extras.getBoolean("notbankrupt"+stock[i].number);
            stock[i].risk=extras.getDouble("risk"+stock[i].number);
            stock[i].sellable=extras.getBoolean("sellable"+stock[i].number);
            stock[i].stoprising=extras.getBoolean("stoprising"+stock[i].number);
            stock[i].stopdeclining=extras.getBoolean("stopdeclining"+stock[i].number);

        }
        return stock;


    }

    public void saveinfo(Stock stock[],double fund,int difficulty,int fromwhicharchive)
    {
        Intent intent = new Intent(selectstock.this, archive.class);
        for(int i=0;i<=5;i++) {
            intent.putExtra("thisprice" + stock[i].number, stock[i].thisprice);
            intent.putExtra("owning" + stock[i].number, stock[i].owning);
            intent.putExtra("fluctuation" + stock[i].number, stock[i].fluctuation);
            intent.putExtra("initiallybought" + stock[i].number, stock[i].initiallybought);
            intent.putExtra("interest" + stock[i].number, stock[i].interest);
            intent.putExtra("notbankrupt" + stock[i].number, stock[i].notbankrupt);
            intent.putExtra("risk" + stock[i].number, stock[i].risk);
            intent.putExtra("sellable" + stock[i].number, stock[i].sellable);
            intent.putExtra("stoprising" + stock[i].number, stock[i].stoprising);
            intent.putExtra("stopdeclining" + stock[i].number, stock[i].stopdeclining);
        }
        intent.putExtra("fund",fund);

        intent.putExtra("difficulty",difficulty);
        intent.putExtra("fromwhicharchive",fromwhicharchive);
        startActivity(intent);
    }
    public void necessaryinfo(Stock stock[],double fund,int thestock,int difficulty,int fromwhicharchive)
    {
        Intent intent=new Intent(selectstock.this,sellbuy.class);
        for(int i=0;i<=5;i++)
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

    public void alert(String title,String message)
    {
        AlertDialog.Builder build= new AlertDialog.Builder(this);
        build.setCancelable(true);
        build.setMessage(message);
        build.setTitle(title);
        build.setPositiveButton("OK",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int i)
            {
                dialog.dismiss();
            }
        });

        AlertDialog dialog= build.create();
        dialog.show();
    }


    public boolean isleapyear(long year)
    {
        boolean isleapyear;
        if(year%100==0)
        {
            if(year%400==0) isleapyear=true;
            else isleapyear=false;
        }
        else
        {
            if(year%4==0) isleapyear=true;
            else isleapyear=false;
        }
        return isleapyear;
    }

    public long daysofmonth(long month,long year)
    {
        long days;
        if(month==1||month==3||month==5||month==7||month==8||month==10||month==12) days=31;
        else if(month==4||month==6||month==9||month==11) days=30;
        else
        {
            if(isleapyear(year)==true) days=29;
            else days=28;
        }
        return days;
    }


    public double random6()
    {
        double r6=0;
        Random random=new Random();
        for(int i=0;i<=11;i++) r6=r6+random.nextDouble();

        r6=r6-6;
        return r6;
    }

    public double nextprice(double p0,double fluctuation, double risk)
    {
        double dp=p0*dt*fluctuation+risk*0.1*p0*random6();
        double Pnext;
        Pnext=p0+dp;
        return Pnext;

    }


    public void wrapupgame()
    {
        Intent intent = new Intent();
        intent.setClass(selectstock.this, MainActivity.class);
        startActivity(intent);
    }

    public long countdays(long year)
    {
        long i=1;
        long days=0;
        for(;i<=year;i++)
        {
            if(isleapyear(i)) days=days+366;
            else days=days+365;
        }
        return days;
    }


    public long countdays(long month,long year)
    {
        long i=1;
        long days=0;
        for(;i<month;i++)
        {
            days=days+daysofmonth(i,year);
        }
        return days;
    }

    public long absolutemin(Vtime timeahead)
    {
        long min=0;
        min = countdays(timeahead.year)*1440+countdays(timeahead.month,timeahead.year)*1440+(timeahead.day-1)*1440+timeahead.hour*60+timeahead.min;
        return min;
    }





    public long timeminus(Vtime timeahead,Vtime timeafter)
    {
        long actualtimeahead;
        long actualtimeafter;

        actualtimeahead=countdays(timeahead.year)*1440+countdays(timeahead.month,timeahead.year)*1440+(timeahead.day-1)*1440+timeahead.hour*60+timeahead.min;
        actualtimeafter=countdays(timeafter.year)*1440+countdays(timeafter.month,timeafter.year)*1440+(timeafter.day-1)*1440+timeafter.hour*60+timeafter.min;

        long timediff=actualtimeahead-actualtimeafter;
        return timediff;
    }


    public void appendtemstock(File tem, Vtime vt, Stock stock)
    {
        if(stock.notbankrupt){
            try{
                FileWriter writer = new FileWriter(tem, true);
                writer.append(absolutemin(vt)+stock.thisprice+"\t"+stock.owning+"\n");
                writer.flush();
                writer.close();
            }

            catch(java.io.IOException e){}

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectstock);
        stocknames[0]="NEW ORIEANT";
        stocknames[1]="MR GREEN";
        stocknames[2]="ATOM";
        stocknames[3]="ROBOT C";
        stocknames[4]="GOLDRUSH";
        stocknames[5]="NELSONMURDOCK";
        fivemin=(Button)findViewById(R.id.fivemin);
        fifteenmin=(Button)findViewById(R.id.fifteenmin);
        thirtymin=(Button)findViewById(R.id.thirtymin);
        save=(Button)findViewById(R.id.save) ;
        nextday=(Button)findViewById(R.id.nextday);

        stockbuttons[0]=(Button)findViewById(R.id.stock0);
        stockbuttons[1]=(Button)findViewById(R.id.stock1);
        stockbuttons[2]=(Button)findViewById(R.id.stock2);
        stockbuttons[3]=(Button)findViewById(R.id.stock3);
        stockbuttons[4]=(Button)findViewById(R.id.stock4);
        stockbuttons[5]=(Button)findViewById(R.id.stock5);
        prices[0]=(TextView)findViewById(R.id.orientation);
        prices[1]=(TextView)findViewById(R.id.mrgreen);
        prices[2]=(TextView)findViewById(R.id.atom);
        prices[3]=(TextView)findViewById(R.id.robotc);
        prices[4]=(TextView)findViewById(R.id.goldrush);
        prices[5]=(TextView)findViewById(R.id.nelson);




        Bundle extras=getIntent().getExtras();

        fromwhicharchive=extras.getInt("loadfromarchive");
        difficulty=(extras.getInt("difficulty"));
        asset = (double)(extras.getInt("difficulty"))*30000;
        fund=asset;

        for(int i=0;i<=5;i++)
        {
            gamemodels[i]=new Stock(stocknames[i],i,difficulty);
        }


        if(extras.getDouble("fund")!=0.0d)
        {
            fund=extras.getDouble("fund");
            gamemodels=receivenecessaryinfo(extras);
            asset=calassets(gamemodels,fund);

        }







        for(int i=0; i<=5;i++)
        {
            temporarystock[i]= new File(Environment.getExternalStorageDirectory(), "temporarystock"+i);
            if(!temporarystock[i].exists())temporarystock[i].mkdirs();
            totemporarystock[i]=new File(temporarystock[i],"temporarystock"+i+".txt");

        }
        for(int t=0;t<=5;t++)
        {
            prices[t].setText(roundup(gamemodels[t].thisprice)+"$");


        }

        fivemin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

               equaltime();
                vtnow.timeplus(5);
                if(vtnow.day!=vtmemo.day)
                {

                    equaltimereverse();
                    vtnow.min=0;
                    vtnow.hour=14;
                    fivemin.setVisibility(View.GONE);
                    fifteenmin.setVisibility(View.GONE);
                    thirtymin.setVisibility(View.GONE);
                    nextday.setVisibility(View.VISIBLE);


                }
                for(long i=0;i<10*timeminus(vtnow,vtmemo);i++)
                {
                    for(int j=0;j<=5;j++)
                    {
                        gamemodels[j].lastprice=gamemodels[j].thisprice;
                        gamemodels[j].thisprice=nextprice(gamemodels[j].lastprice,gamemodels[j].fluctuation,gamemodels[j].risk);

                        if(i%50==0&&i!=0)
                        {
                            vtmemo.timeplus(5);
                            appendtemstock(totemporarystock[j],vtmemo,gamemodels[j]);
                        }
                    }
                }
                prices[0].setText(roundup(gamemodels[0].thisprice)+"$");
                prices[1].setText(roundup(gamemodels[1].thisprice)+"$");
                prices[2].setText(roundup(gamemodels[2].thisprice)+"$");
                prices[3].setText(roundup(gamemodels[3].thisprice)+"$");
                prices[4].setText(roundup(gamemodels[4].thisprice)+"$");
                prices[5].setText(roundup(gamemodels[5].thisprice)+"$");

            }
        });


        fifteenmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                equaltime();
                vtnow.timeplus(15);
                if(vtnow.day!=vtmemo.day)
                {

                    equaltimereverse();
                    vtnow.min=0;
                    vtnow.hour=14;
                    fivemin.setVisibility(View.GONE);
                    fifteenmin.setVisibility(View.GONE);
                    thirtymin.setVisibility(View.GONE);
                    nextday.setVisibility(View.VISIBLE);


                }
                for(long i=0;i<10*timeminus(vtnow,vtmemo);i++)
                {
                    for(int j=0;j<=5;j++)
                    {
                        gamemodels[j].lastprice=gamemodels[j].thisprice;
                        gamemodels[j].thisprice=nextprice(gamemodels[j].lastprice,gamemodels[j].fluctuation,gamemodels[j].risk);

                        if(i%50==0&&i!=0)
                        {
                            vtmemo.timeplus(5);
                            appendtemstock(totemporarystock[j],vtmemo,gamemodels[j]);}


                        }

                    }
                prices[0].setText(roundup(gamemodels[0].thisprice)+"$");
                prices[1].setText(roundup(gamemodels[1].thisprice)+"$");
                prices[2].setText(roundup(gamemodels[2].thisprice)+"$");
                prices[3].setText(roundup(gamemodels[3].thisprice)+"$");
                prices[4].setText(roundup(gamemodels[4].thisprice)+"$");
                prices[5].setText(roundup(gamemodels[5].thisprice)+"$");
            }
        });

            thirtymin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    equaltime();
                    vtnow.timeplus(30);
                    if(vtnow.day!=vtmemo.day)
                    {

                        equaltimereverse();
                        vtnow.min=0;
                        vtnow.hour=14;
                        fivemin.setVisibility(View.GONE);
                        fifteenmin.setVisibility(View.GONE);
                        thirtymin.setVisibility(View.GONE);
                        nextday.setVisibility(View.VISIBLE);


                    }

                    for(long i=0;i<10*timeminus(vtnow,vtmemo);i++)
                    {
                        for(int j=0;j<=5;j++)
                        {
                            gamemodels[j].lastprice=gamemodels[j].thisprice;
                            gamemodels[j].thisprice=nextprice(gamemodels[j].lastprice,gamemodels[j].fluctuation,gamemodels[j].risk);

                            if(i%50==0&&i!=0)
                            {vtmemo.timeplus(5);
                                appendtemstock(totemporarystock[j],vtmemo,gamemodels[j]);}


                        }

                    }
                    prices[0].setText(roundup(gamemodels[0].thisprice)+"$");
                    prices[1].setText(roundup(gamemodels[1].thisprice)+"$");
                    prices[2].setText(roundup(gamemodels[2].thisprice)+"$");
                    prices[3].setText(roundup(gamemodels[3].thisprice)+"$");
                    prices[4].setText(roundup(gamemodels[4].thisprice)+"$");
                    prices[5].setText(roundup(gamemodels[5].thisprice)+"$");
                }
            });




            nextday.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View arg0){
                    equaltime();
                    vtnow.nextday();
                    for(long i=0;i<=10*timeminus(vtnow,vtmemo);i++)
                    {

                        for(int j=0;j<=5;j++)
                        {

                            gamemodels[j].lastprice=gamemodels[j].thisprice;
                            gamemodels[j].thisprice=nextprice(gamemodels[j].lastprice,gamemodels[j].fluctuation,gamemodels[j].risk);

                        }


                    }
                    for(int j=0;j<=5;j++) appendtemstock(totemporarystock[j],vtnow,gamemodels[j]);

                    fivemin.setVisibility(View.VISIBLE);
                    fifteenmin.setVisibility(View.VISIBLE);
                    thirtymin.setVisibility(View.VISIBLE);
                    nextday.setVisibility(View.GONE);


                }});







        stockbuttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                necessaryinfo(gamemodels,fund,0,difficulty,fromwhicharchive);

            }});

        stockbuttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                necessaryinfo(gamemodels,fund,1,difficulty,fromwhicharchive);

            }});
        stockbuttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                necessaryinfo(gamemodels,fund,2,difficulty,fromwhicharchive);

            }});
        stockbuttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                necessaryinfo(gamemodels,fund,3,difficulty,fromwhicharchive);

            }});
        stockbuttons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                necessaryinfo(gamemodels,fund,4,difficulty,fromwhicharchive);

            }});
        stockbuttons[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                necessaryinfo(gamemodels,fund,5,difficulty,fromwhicharchive);

            }});

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                saveinfo(gamemodels,fund,difficulty,fromwhicharchive);
                

            }});






    }
}
