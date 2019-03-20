package luijdelmar.fueljuice.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import luijdelmar.fueljuice.R;
import luijdelmar.fueljuice.database.BooksDatabaseHelper;
import luijdelmar.fueljuice.providers.BookContentProvider;

public class RightFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "3";
    private Button btnRefresh;
    private Button btnDelete;
    private SimpleCursorAdapter adapter;
    private ListView listView;
    private long selectedId = -1;

    public RightFragment() {
    }

    public static RightFragment newInstance(int sectionNumber) {
        RightFragment fragment = new RightFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_right, container, false);

        listView = rootView.findViewById(R.id.list_view_right);
        btnRefresh = rootView.findViewById(R.id.btn_refresh);
        btnDelete = rootView.findViewById(R.id.btn_delete);

        String[] columns = {BooksDatabaseHelper.COLUMN_FUEL_LITER,
                BooksDatabaseHelper.COLUMN_DISTANCE,
                BooksDatabaseHelper.COLUMN_FUEL_PRICE,
                BooksDatabaseHelper.COLUMN_COST};
        int[] viewIds = {R.id.tv_list_fuel_liters, R.id.tv_list_distance, R.id.tv_list_fuel_price_per_liter, R.id.tv_list_cost};
        adapter = new SimpleCursorAdapter(getContext(), R.layout.list, null, columns, viewIds, 0);
        listView.setAdapter(adapter);

        refreshList();

        clickListeners();

        return rootView;
    } // End of onCreateView

    private void refreshList() {
        CursorLoader cursorLoader = new CursorLoader(getContext(), BookContentProvider.CONTENT_URI, null, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        adapter.swapCursor(cursor);
    }

    private void clickListeners() {
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshList();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemSelected()) {
                    deleteBook();
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    selectedId = id;
                    return true;
                } else {
                    Toast.makeText(getContext(), "Please select data to delete", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        });
    }

    private boolean isItemSelected() {
        if (selectedId == -1) {
            Toast.makeText(getContext(), "Please select calculation", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void deleteBook() {
        Uri uri = Uri.parse(BookContentProvider.CONTENT_URI + "/" + selectedId);

        getContext().getContentResolver().delete(uri, null, null);
        refreshList();
    }

}
