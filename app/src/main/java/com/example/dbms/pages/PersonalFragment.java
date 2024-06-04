package com.example.dbms.pages;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dbms.R;
import com.example.dbms.cart.cartItem_adapter;
import com.example.dbms.cart.cart_item;
import com.example.dbms.client.Client;
import com.example.dbms.comment.commentItem_adapter;
import com.example.dbms.comment.comment_item;
import com.example.dbms.drawer.drawerItem_adapter;
import com.example.dbms.drawer.drawer_item;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Client client;

    public PersonalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalFragment newInstance(String param1, String param2) {
        PersonalFragment fragment = new PersonalFragment();
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
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.client = ((MainActivity) getActivity()).client;

        RecyclerView recyclerview = getView().findViewById(R.id.mycommentview);
        ArrayList<comment_item> items = new ArrayList<comment_item>();

        for (String s: client.getUserComments(((MainActivity) getActivity()).user)) {
            if (!s.equals("NotFound")) {
                String[] temp = s.split("/AND/");
                items.add(new comment_item(temp[0],temp[1]));
            }
        }

        recyclerview.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerview.setAdapter(new commentItem_adapter(this.getContext().getApplicationContext(),items));

        TextView userID = getView().findViewById(R.id.userid);
        TextView username = getView().findViewById(R.id.username);
        userID.setText(((MainActivity) getActivity()).user);
        username.setText(((MainActivity) getActivity()).username);

        RecyclerView history = getView().findViewById(R.id.carthistory);
        ArrayList<drawer_item> historyitems = new ArrayList<>();

        for (String s: client.getHistoryCart(userID.getText().toString())) {
            if (!s.equals("NotFound")) {
                historyitems.add(new drawer_item(s));
            }
        }

        history.setLayoutManager(new LinearLayoutManager(this.getContext()));
        history.setAdapter(new drawerItem_adapter(this.getContext().getApplicationContext(), historyitems));


        Button logoutButton = getView().findViewById(R.id.logoutbutton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((MainActivity) getActivity()).warning.isAdded()) {
                    ((MainActivity) getActivity()).warning.show(getActivity().getFragmentManager(), "");
                }
            }
        });
    }
}