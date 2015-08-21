package com.hornettao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hornettao.MyClass.MyPoiDetailResult;
import com.hornettao.commentapp.R;

import java.util.List;

/**
 * Created by hornettao on 14/10/19.
 */
public class SearchResultAdapter extends BaseAdapter {

    private Context context;
    private List<MyPoiDetailResult> myPoiDetailResultList;
    public SearchResultAdapter(Context context, List<MyPoiDetailResult> myPoiDetailResultList) {
        this.context = context;
        this.myPoiDetailResultList = myPoiDetailResultList;
    }

    @Override
    public int getCount() {
        return myPoiDetailResultList.size();
    }

    @Override
    public Object getItem(int position) {
        return myPoiDetailResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null || convertView.getTag() ==null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search_result_listview, null);
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            viewHolder.priceTextView = (TextView) convertView.findViewById(R.id.priceTextView);
            viewHolder.addressTextView = (TextView) convertView.findViewById(R.id.addressTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameTextView.setText(myPoiDetailResultList.get(position).getName());
        viewHolder.ratingBar.setRating((float) myPoiDetailResultList.get(position).getOverallRating());
        viewHolder.priceTextView.setText("价格: " + myPoiDetailResultList.get(position).getPrice());
        viewHolder.addressTextView.setText(myPoiDetailResultList.get(position).getAddress());
        return convertView;
    }

    class ViewHolder {
        TextView nameTextView;
        RatingBar ratingBar;
        TextView priceTextView;
        TextView addressTextView;
    }
}
