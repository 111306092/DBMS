package com.example.dbms.pages;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.dbms.R;
import com.example.dbms.client.Client;
import com.example.dbms.drawer.drawerItem_adapter;
import com.example.dbms.drawer.drawer_item;
import com.example.dbms.map.Intersection;
import com.example.dbms.map.Map;
import com.example.dbms.map.MapElement;
import com.example.dbms.map.Shelf;
import com.example.dbms.map.Walkway;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String storeID;
    private Map map;
    private Client client;
    private GridLayout gridLayout;
    private DrawerLayout drawerLayout;
    private int total_Row, total_Col;
    private ArrayList<drawer_item> items;
    private RecyclerView recyclerView;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
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
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getView().findViewById(R.id.itemsinshelf);
        items = new ArrayList<drawer_item>();
        items.add(new drawer_item("1"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new drawerItem_adapter(this.getContext().getApplicationContext(),items));

        client = ((MainActivity) getActivity()).client;
        storeID = (((MainActivity) getActivity()).selectedStore);

        map = new Map(client);
        map.constructMap(storeID);

        total_Row = Integer.parseInt(map.getStoreInfo()[0]);
        total_Col = Integer.parseInt(map.getStoreInfo()[1]);

        gridLayout = getView().findViewById(R.id.MapGrid);
        gridLayout.setRowCount(total_Row);
        gridLayout.setColumnCount(total_Col);

        drawerLayout = getView().findViewById(R.id.itemlist);

        constructMap();
    }

    private void constructMap() {
        int i = 0;
        for (MapElement[] li: map.getMapElements()) {
            int j = 0;
            for (MapElement mE: li) {
                View v = new View(getActivity().getBaseContext());

                if (mE instanceof Intersection) {
                    v = getLayoutInflater().inflate(R.layout.intersection, null);
                } else if (mE instanceof Shelf) {
                    v = getLayoutInflater().inflate(R.layout.shelf, null);

                    CardView cv = v.findViewById(R.id.ShelfCard);

                    View finalV = v;
                    cv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.i("Debug", "Triggered");
                            //debugText.setText("Shelf Selected");
                            int index = gridLayout.indexOfChild(finalV);
                            int row = (int) Math.floor(index / total_Col);
                            int col = index%total_Col;
                            MapElement element = map.getMapElement(row, col);

                            //Change after this
                            //if (targetShelves.size() < 20 && !targetShelves.contains(element.getName())) {
                                //targetShelves.add(element.getName());
                            //}
                            items = new ArrayList<>();

                            for (String s: client.getProduct(element.getName().substring(1))) {
                                items.add(new drawer_item(s));
                            }
                            ((drawerItem_adapter) recyclerView.getAdapter()).changeItems(items);
                            recyclerView.getAdapter().notifyDataSetChanged();

                            drawerLayout.openDrawer(GravityCompat.START);
                        }
                    });
                } else if (mE instanceof Walkway) {
                    if (((Walkway) mE).getAisle().getAisleRow() != -1) {
                        v = getLayoutInflater().inflate(R.layout.horizontalwalkway, null);
                    } else {
                        v = getLayoutInflater().inflate(R.layout.vertcalwalkway, null);
                    }
                }

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

                params.columnSpec = GridLayout.spec(j, 1, 1);
                params.rowSpec = GridLayout.spec(i, 1, 1);

                gridLayout.addView(v, params);

                j++;
            }

            i++;
        }
    }
}