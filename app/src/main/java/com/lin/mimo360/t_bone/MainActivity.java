package com.lin.mimo360.t_bone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.allpay.tw.mobilesdk.CreateTrade;
import com.allpay.tw.mobilesdk.ENVIRONMENT;
import com.allpay.tw.mobilesdk.PAYMENTTYPE;
import com.allpay.tw.mobilesdk.PaymentActivity;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    //FloatingActionButton fab;
    TabLayout tab;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    List<ParseObject>todos = new ArrayList<>();
    private MainFragment mf = null;
    private AboutFragment af = null;
    private MoneyCardFragment moneyCardFragment = null;
    public static List<String>stringList =new ArrayList<>();
    public static List<String>stridlist = new ArrayList<>();
    public static List<Bitmap> bitmaps = new ArrayList<>();
    ProgressDialog pD;
    String ss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settoolbar();
        setFragment(mf);
        setnavigation();
        new task().execute();
        pD = ProgressDialog.show(this, "訊息", "下載資料中,請稍等");

//        Button button = (Button)findViewById(R.id.button2);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PAYMENTTYPE paymentType = null;
//                Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
//                paymentType = PAYMENTTYPE.CREDIT;
//                CreateTrade oCreateTrade = new CreateTrade(
//                        Config.MerchantID_test,                //廠商編號
//                        Config.AppCode_test,                //App代碼
//                        Config.getMerchantTradeNo(),        //廠商交易編號
//                        Config.getMerchantTradeDate(),        //廠商交易時間
//                        Config.TotalAmount_test,            //交易金額
//                        Config.TradeDesc_test,                //交易描述
//                        Config.ItemName_test,                //商品名稱
//                        paymentType,                        //預設付款方式
//                        ENVIRONMENT.STAGE);                //介接環境 : STAGE為測試，OFFICIAL為正式
//                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, oCreateTrade);
//                startActivityForResult(intent, Config.REQUEST_CODE);
//
//
//            }
//        });



        Runnable r = new Runnable() {
            @Override
            public void run() {
                ParseQuery query = ParseQuery.getQuery("itemId");
                try {
                    List<ParseObject> s = query.find();

                    for (ParseObject pq : s){
                        String str = pq.getString("url");
                        stridlist.add(str);
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        runOnUiThread(r);
        //test();
       //setupFab();
       setTab();
    }



    private void setnavigation() {
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        navigationView = (NavigationView)findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            MenuItem premenuItem;

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (premenuItem != null) {
                    premenuItem.setChecked(false);
                }
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                premenuItem = menuItem;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_1:
                        mf =new MainFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.flay, mf)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.navigation_item_2:


                        break;
                    case R.id.navigation_item_3:
                        af = new AboutFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.flay, af, "AboutFragment")
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.navigation_item_4:
                        moneyCardFragment = new MoneyCardFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.flay, moneyCardFragment,"MoneyCardFragment")
                                .addToBackStack(null)
                                .commit();
                }
                return true;
            }
        });
    }

    public class task extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] params) {
            ParseQuery<ParseObject> parseQuery1 = ParseQuery.getQuery("itemId");
            try {
                todos = parseQuery1.find();
                for (ParseObject p : todos) {
                    ss = p.getString("name");
                    stringList.add(ss);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {

            pD.dismiss();

        }
    }


    private void setFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
       // ft.add(R.id.flay,fragment,"MainFragment");
       //ft.commit();

    }

    private void setTab() {
        tab = (TabLayout)findViewById(R.id.tab);
        tab.setTabGravity(TabLayout.GRAVITY_FILL);
        tab.addTab(tab.newTab().setText("新上市"));
        tab.addTab(tab.newTab().setText("好東西"));
    }

//    private void setupFab() {
//        fab = (FloatingActionButton)findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(findViewById(R.id.drawer),
//                        "這是訊息",
//                        Snackbar.LENGTH_LONG )
//                        .show();
//            }
//        });
//    }

    private void settoolbar() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toobar);
        toolbar.setTitle("丁骨工作室T-bone");
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle1 = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        toggle1.syncState();
        drawer.setDrawerListener(toggle1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
//			super.onActivityResult(requestCode, resultCode, data);
        Log.d(Config.LOGTAG, "requestCode=" + requestCode + " , resultCode=" + resultCode);
        if (requestCode == Config.REQUEST_CODE) {
            if (resultCode == PaymentActivity.RESULT_EXTRAS_NULL) {
                Log.d(Config.LOGTAG, "EXTRA_PAYMENT is NULL ");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_CANCEL) {

                Log.d(Config.LOGTAG, "The user canceled.");
            }
        }

    }


}
