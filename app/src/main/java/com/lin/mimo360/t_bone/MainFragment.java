package com.lin.mimo360.t_bone;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {
    RecyclerView rv;
    MyAdapter myAdapter;
    RecyclerView.LayoutManager lm;
     List<String> stringList;
    ProgressDialog pD;
    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pD = ProgressDialog.show(getActivity(), "訊息", "資料讀取中,請稍後");
        rv = (RecyclerView) getActivity().findViewById(R.id.recycleview);
        lm = new StaggeredGridLayoutManager(2, 1);
        rv.setLayoutManager(lm);
        MyApplication myApplication = (MyApplication) getActivity().getApplicationContext();
        myAdapter = new MyAdapter(getActivity(), myApplication.parseObjects,MainActivity.stridlist);
        myAdapter.setmOnRecyclerViewItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List<ParseObject> parseObjects) {
               MoneyCardFragment moneyCardFragment = new MoneyCardFragment();
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.flay,moneyCardFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        rv.setAdapter(myAdapter);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ParseQuery query = ParseQuery.getQuery("itemId");
                try {
                    List<ParseObject> s = query.find();

                    for (ParseObject pq : s){
                        String str = pq.getString("name");
                        stringList.add(str);
                    }
                    myAdapter.notifyDataSetChanged();


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        pD.dismiss();
    }

}
