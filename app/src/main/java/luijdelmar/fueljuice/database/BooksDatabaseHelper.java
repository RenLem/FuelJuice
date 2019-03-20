package luijdelmar.fueljuice.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class BooksDatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "calculations";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FUEL_LITER = "liters_of_fuel";
    public static final String COLUMN_DISTANCE = "distance";
    public static final String COLUMN_FUEL_PRICE = "fuel_price";
    public static final String COLUMN_COST = "cost";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "calculations.db";
    private static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_FUEL_LITER + " text not null, "
            + COLUMN_DISTANCE + " text not null, "
            + COLUMN_FUEL_PRICE + " text not null, "
            + COLUMN_COST + " text not null );";

    private static final String DROP_SQL = "DROP TABLE " + TABLE_NAME + ";";

    public BooksDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_SQL);
        onCreate(db);
    }

    public long insert(ContentValues values) {
        long id = getWritableDatabase().insert(TABLE_NAME, null, values);
        return id;
    }

    public Cursor query(String id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(TABLE_NAME);

        if (id != null) {
            builder.appendWhere("_id" + " = " + id);
        }

        Cursor cursor = builder.query(getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        return cursor;
    }

    public int update(String id, ContentValues values) {
        if (id == null) {
            return getWritableDatabase().update(TABLE_NAME, values, null, null);
        } else {
            return getWritableDatabase().update(TABLE_NAME, values, "_id=?", new String[]{id});
        }
    }

    public int delete(String id) {
        if (id == null) {
            return getWritableDatabase().delete(TABLE_NAME, null, null);
        } else {
            return getWritableDatabase().delete(TABLE_NAME, "_id=?", new String[]{id});
        }
    }

}
