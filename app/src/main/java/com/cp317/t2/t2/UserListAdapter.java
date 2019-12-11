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

public class UserListAdapter extends ArrayAdapter<User> implements Filterable {

    private Context mContext;
    int mResource;
    ValueFilter valueFilter;
    ArrayList<User> userList;
    ArrayList<User> userFilterList;
    String searchType;


    public UserListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        userList = objects;
        userFilterList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public User getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String firstName = getItem(position).getUserFirstName();
        String lastName = getItem(position).getUserLastName();
        String name = firstName + " " + lastName;
        String program = getItem(position).getProgram();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ImageView ivAvatar = (ImageView) convertView.findViewById(R.id.avatar_imageView);
        TextView tvName = (TextView) convertView.findViewById(R.id.name_textView);
        TextView tvProgram = (TextView) convertView.findViewById(R.id.program_textView);

        ivAvatar.setImageResource(R.drawable.prof1);
        tvName.setText(name);
        tvProgram.setText(program);

        return convertView;
    }

//    @Override
//    public Filter getFilter() {
//        return object : Filter() {
//            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
//                mPois = filterResults.values as List<PoiDao>
//                        notifyDataSetChanged()
//            }
//
//            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
//                val queryString = charSequence?.toString()?.toLowerCase()
//
//                val filterResults = Filter.FilterResults()
//                filterResults.values = if (queryString==null || queryString.isEmpty())
//                    allPois
//                else
//                    allPois.filter {
//                    it.name.toLowerCase().contains(queryString) ||
//                            it.city.toLowerCase().contains(queryString) ||
//                            it.category_name.toLowerCase().contains(queryString)
//                }
//                return filterResults
//            }
//        }
//    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
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
                ArrayList<User> filterList = new ArrayList<User>();
                for (int i = 0; i < userFilterList.size(); i++) {
                    if(searchType.equals("name")){
                        String name = userFilterList.get(i).getUserFirstName().toUpperCase() +" "+ userFilterList.get(i).getUserLastName().toUpperCase();
                        if (name
                                .startsWith(constraint.toString().toUpperCase())) {
                            User user = userFilterList.get(i);
                            filterList.add(user);
                        }
                    } else if(searchType.equals("course")){
                        if (userFilterList.get(i).getCourses()!=null && (userFilterList.get(i).getCourses().toUpperCase())
                                .contains(constraint.toString().toUpperCase())) {
                            User user = userFilterList.get(i);
                            filterList.add(user);
                        }
                    } else if(searchType.equals("program")){
                        if (userFilterList.get(i).getProgram()!=null && (userFilterList.get(i).getProgram().toUpperCase())
                                .startsWith(constraint.toString().toUpperCase())) {
                            User user = userFilterList.get(i);
                            filterList.add(user);
                        }
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = userFilterList.size();
                results.values = userFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            userList = (ArrayList<User>) results.values;
            notifyDataSetChanged();
        }
    }
}
