package com.lin.mimo360.t_bone;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.security.ProtectionDomain;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {
    ProgressDialog pd;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setParse();

    }

    private void setParse() {
        pd = ProgressDialog.show(getActivity(),"訊息","下載中");
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("about");
        parseQuery.getInBackground("BEszgYXCLU", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null ){
                    String s = parseObject.getString("contcnt");
                    TextView textView = (TextView)getView().findViewById(R.id.abouttext);
                    textView.setText(s);
                }

            }
        });
        pd.dismiss();
    }
}
