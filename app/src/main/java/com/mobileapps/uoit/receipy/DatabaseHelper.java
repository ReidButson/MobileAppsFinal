package com.mobileapps.uoit.receipy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    // Constants
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "receipy.db";
    // Store table
    private static final String STORE_TABLE = "STORE";
    private static final String STORE_ID = "id";
    private static final String STORE_NAME = "name";
    private static final String STORE_ADDRESS = "address";
    // Recipe table
    private static final String RECIPE_TABLE = "RECIPE";
    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    // Ingredients table
    private static final String INGREDIENTS_TABLE = "INGREDIENTS";
    private static final String INGREDIENTS_ID = "id";
    private static final String INGREDIENTS_NAME = "name";
    private static final String INGREDIENTS_MEASUREMENT_TYPE = "measure_type";
    // Recipe Ingredients table
    private static final String RECIPE_INGREDIENTS_TABLE = "RECIPE_INGREDIENTS";
    private static final String RECIPE_INGREDIENTS_RECIPE_ID = "recipe_id";
    private static final String RECIPE_INGREDIENTS_INGREDIENT_ID = "ingredient_id";
    private static final String RECIPE_INGREDIENTS_AMOUNT = "amount";
    // Store Ingredients table
    private static final String STORE_INGREDIENTS_TABLE = "STORE_INGREDIENTS";
    private static final String STORE_INGREDIENTS_STORE_ID = "store_id";
    private static final String STORE_INGREDIENTS_INGREDIENT_ID = "ingredient_id";
    private static final String STORE_INGREDIENTS_PRICE = "price";
    private static final String STORE_INGREDIENTS_AMOUNT = "amount";

    public DatabaseHelper(
            Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** Creates the database.
     *
     * @param db The database to create the tables on.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // The strings to create all the tables
        String create_store = "CREATE TABLE " + STORE_TABLE + " (" +
                STORE_ID + " Integer PRIMARY KEY AUTOINCREMENT, " +
                STORE_NAME + " Text NOT NULL, " +
                STORE_ADDRESS + " Text NOT NULL);";
        String create_recipe = "CREATE TABLE " + RECIPE_TABLE + " (" +
                RECIPE_ID + " Integer PRIMARY KEY AUTOINCREMENT, " +
                RECIPE_NAME + " Text UNIQUE);";
        String create_ingredients = "CREATE TABLE " + INGREDIENTS_TABLE + " (" +
                INGREDIENTS_ID + " Integer PRIMARY KEY AUTOINCREMENT, " +
                INGREDIENTS_NAME + " Text UNIQUE NOT NULL, " +
                INGREDIENTS_MEASUREMENT_TYPE + " Text NOT NULL);";
        String create_recipe_ingredients = "CREATE TABLE " + RECIPE_INGREDIENTS_TABLE + " (" +
                RECIPE_INGREDIENTS_RECIPE_ID + " Integer PRIMARY KEY, " +
                RECIPE_INGREDIENTS_INGREDIENT_ID + " Integer PRIMARY KEY, " +
                RECIPE_INGREDIENTS_AMOUNT + " Real NOT NULL, " +
                "FOREIGN KEY (" + RECIPE_INGREDIENTS_RECIPE_ID +
                ") REFERENCES " + RECIPE_TABLE + "(" + RECIPE_ID + "), " +
                "FOREIGN KEY (" + RECIPE_INGREDIENTS_INGREDIENT_ID +
                ") REFERENCES " + INGREDIENTS_ID + "(" + INGREDIENTS_ID + ")" +
                ");";
        String create_store_ingredients = "CREATE TABLE " + STORE_INGREDIENTS_TABLE + " (" +
                STORE_INGREDIENTS_STORE_ID + " Integer PRIMARY KEY, " +
                STORE_INGREDIENTS_INGREDIENT_ID + " Integer PRIMARY KEY, " +
                STORE_INGREDIENTS_PRICE + " Real NOT NULL, " +
                STORE_INGREDIENTS_AMOUNT + " Real NOT NULL), " +
                "FOREIGN KEY (" + STORE_INGREDIENTS_STORE_ID +
                ") REFERENCES " + STORE_TABLE + "(" + STORE_ID + "), " +
                "FOREIGN KEY (" + STORE_INGREDIENTS_INGREDIENT_ID +
                ") REFERENCES " + INGREDIENTS_TABLE + "(" + INGREDIENTS_ID + ")" +
                ");";
        // Create the tables in the database
        db.execSQL(create_store);
        db.execSQL(create_recipe);
        db.execSQL(create_ingredients);
        db.execSQL(create_recipe_ingredients);
        db.execSQL(create_store_ingredients);
    }

    /** Updates the database.
     *
     * @param db The database to upgrade.
     * @param old_version The old version of the database.
     * @param new_version The new version of the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
        db.execSQL("DROP table " + STORE_TABLE + ";");
        db.execSQL("DROP table " + RECIPE_TABLE + ";");
        db.execSQL("DROP table " + INGREDIENTS_TABLE + ";");
        db.execSQL("DROP table " + RECIPE_INGREDIENTS_TABLE + ";");
        db.execSQL("DROP table " + STORE_INGREDIENTS_TABLE + ";");
        this.onCreate(db);
    }
}
