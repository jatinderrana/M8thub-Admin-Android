package com.lifelineconnect.m8thubadmin.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lifelineconnect.m8thubadmin.R;
import com.lifelineconnect.m8thubadmin.Utils.Settings;
import com.lifelineconnect.m8thubadmin.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Country.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Country#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Country extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static ArrayList<HashMap<String, Object>> db_list_country =new ArrayList<HashMap<String,Object>>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static CountryListAdapter adapter;
    public static ListView listview;
    public TextView cancelbutton;

    private OnFragmentInteractionListener mListener;

    public Country() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contacts.
     */
    // TODO: Rename and change types and number of parameters
    public static Country newInstance(String param1, String param2) {
        Country fragment = new Country();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_country, container, false);
        listview = v.findViewById(R.id.list);

        cancelbutton = v.findViewById(R.id.cancel_button);
        cancelbutton.setOnClickListener(this);
        return v;
        //return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cancel_button:
                FragmentTransaction ft;
                Fragment mFragment = new RegisterFragment();
                ft  = getActivity().getSupportFragmentManager().beginTransaction();
               //ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                ft.replace(android.R.id.content, mFragment);
                try {
                    ft.commit();
                } catch (IllegalStateException ignored) {

                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
         super.onStart();
        Utils.getCountrylist(getActivity());
    }

    @Override
    public void onResume() {
        setAdapter();
        super.onResume();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public class CountryListAdapter extends BaseAdapter {
        // Declare Variables
        Context context;
        ArrayList<HashMap<String, Object>> DrawerList;
        View itemView;
        String Title,code;
        TextView countrycode,countryname;
        LayoutInflater inflater;

        public CountryListAdapter(FragmentActivity activity, ArrayList<HashMap<String, Object>> musicList) {
            context = activity;
            DrawerList = musicList;
        }

        @Override
        public int getCount() {
            return DrawerList.size();
        }

        @Override
        public Object getItem(int position) {
            return DrawerList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {

            return getCount();
        }

        @Override
        public int getItemViewType(int position) {

            return position;
        }
        public View getView(final int position, final View convertView, ViewGroup parent) {
            // Declare Variables
            itemView = convertView;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.country_row, null);
            countrycode = itemView.findViewById(R.id.countrycode);
            countryname = itemView.findViewById(R.id.countryname);
            if (db_list_country.get(position).get("name") != null) {
                Title = db_list_country.get(position).get("name").toString();
                countryname.setText(Title);
            }
            if (db_list_country.get(position).get("cPrefix") != null) {
                code = db_list_country.get(position).get("cPrefix").toString();
                countrycode.setText("+"+code);
            }
            return itemView;

        }

    }

    void setAdapter() {

        if (db_list_country != null && db_list_country.size() > 0) {
            adapter = new CountryListAdapter(getActivity(),db_list_country);
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Utils.showlog("adapter", "position------->>>>" + position);

                if (db_list_country!=null && db_list_country.size()>0)
                {
                    Settings.prefixreg = "+"+db_list_country.get(position).get("cPrefix").toString();
                    Settings.countryreg = db_list_country.get(position).get("name").toString();
                    FragmentTransaction ft;
                    Fragment mFragment = new RegisterFragment();
                    ft  = getActivity().getSupportFragmentManager().beginTransaction();
                   //ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                    ft.replace(android.R.id.content, mFragment);
                    try {
                        ft.commit();
                    } catch (IllegalStateException ignored) {

                    }
                }

            }
        });

    }
}
