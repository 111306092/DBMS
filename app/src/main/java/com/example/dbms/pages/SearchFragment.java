package com.example.dbms.pages;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dbms.R;
import com.example.dbms.client.Client;
import com.example.dbms.comment.commentItem_adapter;
import com.example.dbms.comment.comment_item;
import com.example.dbms.search_item.searchItem_adapter;
import com.example.dbms.search_item.search_item;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Client client;
    private String selectedStore;
    private ArrayList<String> targetItems;
    private DrawerLayout drawerLayout;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        client = ((MainActivity) getActivity()).client;
        selectedStore = ((MainActivity) getActivity()).selectedStore;

        targetItems = ((MainActivity) getActivity()).targetItems;

        RecyclerView recyclerView = getView().findViewById(R.id.search_recyclerview);
        ArrayList<search_item> items = new ArrayList<search_item>();

        for (String s: client.getStoreProducts(selectedStore)) {
            items.add(new search_item(s));
        }

        drawerLayout = getView().findViewById(R.id.productList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new searchItem_adapter(this.getContext().getApplicationContext(),items, this));
    }

    public Client getClient() {
        return client;
    }

    public ArrayList<String> getProductComments(String name) {
        return client.getProductComments(name, ((MainActivity) getActivity()).selectedStore);
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public ArrayList<String> getTargetItems() {
        return targetItems;
    }

    public void generateDrawerLayout(String productInfo) {
        //The Labels
        String[] temp = productInfo.split("/AND/");

        TextView name = getView().findViewById(R.id.itemname);
        name.setText(temp[0]);

        TextView price = getView().findViewById(R.id.itemprice);
        if (!temp[3].equals("0")) {
            int p = Integer.parseInt(temp[1]) - Integer.parseInt(temp[3]);
            temp[1] = String.valueOf(p);

            price.setTextColor(Color.RED);
        } else {
            price.setTextColor(Color.BLACK);
        }
        price.setText(String.format("NT$ %s", temp[1]));

        TextView des = getView().findViewById(R.id.iteminfo);
        des.setText(temp[2]);

        //The Comments
        RecyclerView commentView = getView().findViewById(R.id.com_recyclerview);
        ArrayList<comment_item> items = new ArrayList<>();

        for (String s: getProductComments(name.getText().toString())) {
            if (!(s.equals("NotFound") || s.isEmpty())) {
                items.add(new comment_item(name.getText().toString(), s));
            }
        }

        commentView.setLayoutManager(new LinearLayoutManager(getContext()));
        commentView.setAdapter(new commentItem_adapter(getContext().getApplicationContext(),items));

        //Write Comment
        EditText mycomment = getView().findViewById(R.id.mycomment);
        Button sendComment = getView().findViewById(R.id.sendbutton);
        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mycomment.getText().toString().isEmpty()) {
                    client.writeComments(((MainActivity) getActivity()).user, temp[0], selectedStore, mycomment.getText().toString());

                    mycomment.setText("");
                    mycomment.clearFocus();
                }
            }
        });

        //Price History
        RecyclerView priceHistory = getView().findViewById(R.id.pricehistory);
        ArrayList<comment_item> prices = new ArrayList<>();

        for (String s: client.getPriceHistory(temp[0], selectedStore)) {
            if (!(s.equals("NotFound") || s.isEmpty())) {
                String[] priceInfo = s.split("/AND/");

                prices.add(new comment_item(priceInfo[0], priceInfo[1]));
            }
        }

        priceHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        priceHistory.setAdapter(new commentItem_adapter(getContext().getApplicationContext(),prices));

        getDrawerLayout().openDrawer(GravityCompat.START);
    }
}