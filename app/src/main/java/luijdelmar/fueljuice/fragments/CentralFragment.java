package luijdelmar.fueljuice.fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import luijdelmar.fueljuice.R;
import luijdelmar.fueljuice.database.BooksDatabaseHelper;
import luijdelmar.fueljuice.providers.BookContentProvider;

public class CentralFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "2";
    private EditText etFuelLiters;
    private EditText etDistance;
    private EditText etFuelPricePerLiter;
    private EditText etCost;
    private Button btnAdd;
    private long selectedId;
    private SimpleCursorAdapter adapter;

    public CentralFragment() {
    }

    public static CentralFragment newInstance(int sectionNumber) {
        CentralFragment fragment = new CentralFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_central, container, false);

        btnAdd = rootView.findViewById(R.id.btn_add);

        etFuelLiters = rootView.findViewById(R.id.et_fuel_liters);
        etDistance = rootView.findViewById(R.id.et_distance);
        etFuelPricePerLiter = rootView.findViewById(R.id.et_fuel_price_per_liter);
        etCost = rootView.findViewById(R.id.et_cost);

        String[] columns = {BooksDatabaseHelper.COLUMN_FUEL_LITER,
                BooksDatabaseHelper.COLUMN_DISTANCE,
                BooksDatabaseHelper.COLUMN_FUEL_PRICE,
                BooksDatabaseHelper.COLUMN_COST};
        int[] viewIds = {R.id.tv_list_fuel_liters, R.id.tv_list_distance, R.id.tv_list_fuel_price_per_liter, R.id.tv_list_cost};
        adapter = new SimpleCursorAdapter(getContext(), R.layout.list, null, columns, viewIds, 0);

        clickListeners();

        return rootView;
    } // end onCreate

    private void refreshList() {
        CursorLoader cursorLoader = new CursorLoader(getContext(), BookContentProvider.CONTENT_URI, null, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        adapter.swapCursor(cursor);
    }

    private void clickListeners() {

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputOK()) {
                    String sFuelLiters = etFuelLiters.getText().toString();
                    String sDistance = etDistance.getText().toString();
                    String sFuelPricePerLiter = etFuelPricePerLiter.getText().toString();
                    String sCost = etCost.getText().toString();
                    insertBook(sFuelLiters, sDistance, sFuelPricePerLiter, sCost);
                }
            }
        });

    }

    private boolean inputOK() {
        if (etFuelLiters.getText().length() == 0) {
            Toast.makeText(getContext(), "Please type liters of fuel", Toast.LENGTH_SHORT).show();

            etFuelLiters.requestFocus();
            return false;
        }
        if (etDistance.getText().length() == 0) {
            Toast.makeText(getContext(), "Please type distance in kilometers", Toast.LENGTH_SHORT).show();

            etDistance.requestFocus();
            return false;
        }
        if (etDistance.getText().length() == 0) {
            Toast.makeText(getContext(), "Please type fuel price per liter", Toast.LENGTH_SHORT).show();

            etFuelPricePerLiter.requestFocus();
            return false;
        }
        if (etDistance.getText().length() == 0) {
            Toast.makeText(getContext(), "Please type cost", Toast.LENGTH_SHORT).show();

            etCost.requestFocus();
            return false;
        }
        return true;
    }

    private void insertBook(String litersOfFuel, String distance, String fuelPrice, String cost) {
        ContentValues values = new ContentValues();

        values.put(BooksDatabaseHelper.COLUMN_FUEL_LITER, litersOfFuel);
        values.put(BooksDatabaseHelper.COLUMN_DISTANCE, distance);
        values.put(BooksDatabaseHelper.COLUMN_FUEL_PRICE, fuelPrice);
        values.put(BooksDatabaseHelper.COLUMN_COST, cost);

        getContext().getContentResolver().insert(BookContentProvider.CONTENT_URI, values);
        refreshList();
        clearForm();
    }

    private void clearForm() {
        etFuelLiters.setText("");
        etDistance.setText("");
        etFuelPricePerLiter.setText("");
        etCost.setText("");
        selectedId = -1;
    }
}

