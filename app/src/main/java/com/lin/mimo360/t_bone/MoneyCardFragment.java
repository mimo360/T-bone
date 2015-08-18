package com.lin.mimo360.t_bone;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.allpay.tw.mobilesdk.CreateTrade;
import com.allpay.tw.mobilesdk.ENVIRONMENT;
import com.allpay.tw.mobilesdk.PAYMENTTYPE;
import com.allpay.tw.mobilesdk.PaymentActivity;



/**
 * A simple {@link Fragment} subclass.
 */
public class MoneyCardFragment extends Fragment {


    public MoneyCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_money_card, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button button = (Button)getView().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PAYMENTTYPE paymentType = null;
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                paymentType = PAYMENTTYPE.CREDIT;
                CreateTrade oCreateTrade = new CreateTrade(
                        Config.MerchantID_test,                //廠商編號
                        Config.AppCode_test,                //App代碼
                        Config.getMerchantTradeNo(),        //廠商交易編號
                        Config.getMerchantTradeDate(),        //廠商交易時間
                        Config.TotalAmount_test,            //交易金額
                        Config.TradeDesc_test,                //交易描述
                        Config.ItemName_test,                //商品名稱
                        paymentType,                        //預設付款方式
                        ENVIRONMENT.STAGE);                //介接環境 : STAGE為測試，OFFICIAL為正式
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, oCreateTrade);
                startActivityForResult(intent, Config.REQUEST_CODE);


            }
        });



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