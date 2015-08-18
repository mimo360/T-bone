package com.lin.mimo360.t_bone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

/**
 * Created by mimo360 on 2015/7/21.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHoider> implements View.OnClickListener {
    Context context;
    List<Integer> heights;
    List<ParseObject> parseObjects = new ArrayList<>() ;
    public List<String> id = null;
    List<String> idList = new ArrayList<>();
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = null;

    public MyAdapter(Context context, List<ParseObject> po, List<String> idList) {
        this.context = context;
        this.parseObjects = po;
        this.idList = idList;
        getRandomHeight(this.parseObjects);
    }

    @Override
    public void onClick(View v) {
        if (mOnRecyclerViewItemClickListener != null)
            mOnRecyclerViewItemClickListener.onItemClick(v, (List<ParseObject>) v.getTag());
    }

    public static interface OnRecyclerViewItemClickListener{
        void onItemClick(View view, List<ParseObject> parseObjects);
    }

    public void setmOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mOnRecyclerViewItemClickListener = listener;
    }

    private void getRandomHeight(List<ParseObject> parseObjects){
        heights = new ArrayList<>();
        for (int i=0; i<=parseObjects.size(); i++){
            heights.add((int) (400 + Math.random() * 150));
        }
    }


    @Override
    public ViewHoider onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item,parent,false);
       ViewHoider vh = new ViewHoider(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHoider vh,int position) {

        ViewGroup.LayoutParams params = vh.itemView.getLayoutParams();
        params.height = heights.get(position);
        vh.itemView.setLayoutParams(params);
        vh.itemView.getTag(position);
        Picasso.with(context).load(parseObjects.get(position).getString("url1")).into(vh.imageView);
        vh.textView.setText(parseObjects.get(position).getString("name"));
    }

    @Override
    public int getItemCount() {
        return MainActivity.stringList.size();
    }

    public static class ViewHoider extends RecyclerView.ViewHolder{
        private  TextView textView, textView1;
        private ImageView imageView;
        public ViewHoider(View view){
            super(view);
            textView = (TextView)view.findViewById(R.id.item1);
            textView1 = (TextView)view.findViewById(R.id.item2);
            imageView = (ImageView)view.findViewById(R.id.icon);

        }
    }

}
