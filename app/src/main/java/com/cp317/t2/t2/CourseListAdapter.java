package com.cp317.t2.t2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CourseListAdapter extends ArrayAdapter<String> implements Filterable {

    private Context mContext;
    int mResource;
    ValueFilter valueFilter;
    ArrayList<String> courseList;
    ArrayList<String> courseFilterList;


    public CourseListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        courseList = objects;
        courseFilterList = courseList;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public String getItem(int i) {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String course = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvCourse = (TextView) convertView.findViewById(R.id.course_textView);
        //TODO: create layout for this view

        tvCourse.setText(course);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<String> filterList = new ArrayList<>();
                for (int i = 0; i < courseFilterList.size(); i++) {
                    if ((courseFilterList.get(i).toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        String course = courseFilterList.get(i);
                        filterList.add(course);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = courseFilterList.size();
                results.values = courseFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            courseList = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }
}
