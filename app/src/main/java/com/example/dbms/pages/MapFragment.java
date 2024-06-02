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
import com.example.dbms.client.ReconnectDialog;
import com.example.dbms.drawer.drawerItem_adapter;
import com.example.dbms.drawer.drawer_item;
import com.example.dbms.map.Intersection;
import com.example.dbms.map.Map;
import com.example.dbms.map.MapElement;
import com.example.dbms.map.Shelf;
import com.example.dbms.map.Walkway;
import com.example.dbms.route.Route;

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
    //private Map map;
    private Client client;
    private GridLayout gridLayout;
    private DrawerLayout drawerLayout;
    private int total_Row, total_Col;
    private ArrayList<drawer_item> items;
    private RecyclerView recyclerView;
    private ArrayList<String> targetShelves;
    private ReconnectDialog dialog;
    private Map map;

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
        targetShelves = ((MainActivity) getActivity()).targetShelves;
        dialog = new ReconnectDialog(client);
        map = ((MainActivity) getActivity()).map;

        total_Row = Integer.parseInt(map.getStoreInfo()[0]);
        total_Col = Integer.parseInt(map.getStoreInfo()[1]);

        gridLayout = getView().findViewById(R.id.MapGrid);
        gridLayout.setRowCount(total_Row);
        gridLayout.setColumnCount(total_Col);

        drawerLayout = getView().findViewById(R.id.itemlist);

        constructMap();

        paintPath();
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

    private void resetMap() {
        for (MapElement[] li: map.getMapElements()) {
            for (MapElement mE: li) {
                View view = gridLayout.getChildAt(mE.getRow() * total_Col + mE.getCol());
                CardView cv = view.findViewById(R.id.HorizontalWalkwayCard);

                if (cv != null) {
                    cv.setCardBackgroundColor(Color.GRAY);
                } else {
                    cv = view.findViewById(R.id.VerticalWalkwayCard);

                    if (cv != null) {
                        cv.setCardBackgroundColor(Color.GRAY);
                    } else {
                        cv = view.findViewById(R.id.IntersectionCard);

                        if (cv != null) {
                            cv.setCardBackgroundColor(Color.GRAY);
                        } else {
                            cv = view.findViewById(R.id.ShelfCard);
                            cv.setCardBackgroundColor(Color.WHITE);
                        }
                    }
                }
            }
        }
    }

    private void paintPath() {
        if (!targetShelves.isEmpty()) {
            ArrayList<Shelf> order = ((MainActivity) getActivity()).order;

            if (!((MainActivity) getActivity()).mapUpdated) {
                try {
                    ArrayList<String> temp = client.getPath(targetShelves);

                    for (String s: temp) {
                        MapElement mE = map.getMapElement(s);
                        order.add((Shelf) mE);
                    }

                    ((MainActivity) getActivity()).mapUpdated = true;
                } catch (Exception e) {
                    if (!dialog.isAdded()) {
                        dialog.show(getActivity().getFragmentManager(), "Reconnect");
                    }
                }
            }

            resetMap();

            String testOutput = "";
            for (Shelf s: order) {
                testOutput += (s.getName() + "->");
            }
            for (Shelf s: order) {
                int index = s.getRow() * total_Col + s.getCol();

                View view = gridLayout.getChildAt(index);
                CardView cv = view.findViewById(R.id.ShelfCard);
                cv.setCardBackgroundColor(Color.RED);
            }
            //Send product list to back end to save it in the database
            Route route = new Route(total_Row, total_Col, map.getStart(), map.getEnd(), order);

            boolean[][] isRoute = route.getIsRoute();

            for (int i = 0; i < total_Row; i++) {
                for (int j = 0; j < total_Col; j++) {
                    if (isRoute[i][j]) {
                        View view = gridLayout.getChildAt(i * total_Col + j);
                        CardView cv = view.findViewById(R.id.HorizontalWalkwayCard);

                        if (cv != null) {
                            cv.setCardBackgroundColor(Color.YELLOW);
                        } else {
                            cv = view.findViewById(R.id.VerticalWalkwayCard);

                            if (cv != null) {
                                cv.setCardBackgroundColor(Color.YELLOW);
                            } else {
                                cv = view.findViewById(R.id.IntersectionCard);

                                if (cv != null) {
                                    cv.setCardBackgroundColor(Color.YELLOW);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}