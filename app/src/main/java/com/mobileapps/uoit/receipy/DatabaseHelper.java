package com.mobileapps.uoit.receipy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
    private static final String RECIPE_INGREDIENTS_ID = "id";
    private static final String RECIPE_INGREDIENTS_RECIPE_ID = "recipe_id";
    private static final String RECIPE_INGREDIENTS_INGREDIENT_ID = "ingredient_id";
    private static final String RECIPE_INGREDIENTS_AMOUNT = "amount";
    // Store Ingredients table
    private static final String STORE_INGREDIENTS_TABLE = "STORE_INGREDIENTS";
    private static final String STORE_INGREDIENTS_ID = "id";
    private static final String STORE_INGREDIENTS_STORE_ID = "store_id";
    private static final String STORE_INGREDIENTS_INGREDIENT_ID = "ingredient_id";
    private static final String STORE_INGREDIENTS_PRICE = "price";
    private static final String STORE_INGREDIENTS_AMOUNT = "amount";

    /** Creates the database helper.
     *
     * @param context The context of the application.
     */
    public DatabaseHelper(Context context) {
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
                STORE_NAME + " Text NOT NULL UNIQUE, " +
                STORE_ADDRESS + " Text NOT NULL);";
        String create_recipe = "CREATE TABLE " + RECIPE_TABLE + " (" +
                RECIPE_ID + " Integer PRIMARY KEY AUTOINCREMENT, " +
                RECIPE_NAME + " Text UNIQUE);";
        String create_ingredients = "CREATE TABLE " + INGREDIENTS_TABLE + " (" +
                INGREDIENTS_ID + " Integer PRIMARY KEY AUTOINCREMENT, " +
                INGREDIENTS_NAME + " Text UNIQUE NOT NULL, " +
                INGREDIENTS_MEASUREMENT_TYPE + " Text NOT NULL);";
        String create_recipe_ingredients = "CREATE TABLE " + RECIPE_INGREDIENTS_TABLE + " (" +
                RECIPE_INGREDIENTS_ID + " Integer PRIMARY KEY AUTOINCREMENT, " +
                RECIPE_INGREDIENTS_RECIPE_ID + " Integer NOT NULL, " +
                RECIPE_INGREDIENTS_INGREDIENT_ID + " Integer NOT NULL, " +
                RECIPE_INGREDIENTS_AMOUNT + " Real NOT NULL, " +
                "FOREIGN KEY (" + RECIPE_INGREDIENTS_RECIPE_ID +
                ") REFERENCES " + RECIPE_TABLE + "(" + RECIPE_ID + "), " +
                "FOREIGN KEY (" + RECIPE_INGREDIENTS_INGREDIENT_ID +
                ") REFERENCES " + INGREDIENTS_ID + "(" + INGREDIENTS_ID + ")" +
                ");";
        String create_store_ingredients = "CREATE TABLE " + STORE_INGREDIENTS_TABLE + " (" +
                STORE_INGREDIENTS_ID + " Integer PRIMARY KEY AUTOINCREMENT, " +
                STORE_INGREDIENTS_STORE_ID + " Integer NOT NULL, " +
                STORE_INGREDIENTS_INGREDIENT_ID + " Integer NOT NULL, " +
                STORE_INGREDIENTS_PRICE + " Real NOT NULL, " +
                STORE_INGREDIENTS_AMOUNT + " Real, " +
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

    /** Inserts a new store into the database.
     *
     * @param store The store to insert into the database.
     * @return The id of the inserted store.
     */
    public long insertStore(Store store) {
        // Create the values from the given store
        ContentValues values = new ContentValues();
        values.put(STORE_NAME, store.getName());
        values.put(STORE_ADDRESS, store.getAddress());
        // Inserts the new store
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(STORE_TABLE, null, values);
        db.close();
        return id;
    }

    /** Inserts a new ingredient into the database.
     *
     * @param ingredient The ingredient to insert.
     * @return The id of the inserted ingredient.
     */
    public long insertIngredient(Ingredient ingredient) {
        // Create the values from the given ingredient
        ContentValues values = new ContentValues();
        values.put(INGREDIENTS_NAME, ingredient.getName());
        values.put(INGREDIENTS_MEASUREMENT_TYPE, ingredient.getUnits());
        // Inserts the new ingredient
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(INGREDIENTS_TABLE, null, values);
        db.close();
        return id;
    }

    /** Inserts a new recipe into the database.
     *
     * @param recipe The recipe to insert.
     * @return The id of the inserted recipe.
     */
    public long insertRecipe(Recipe recipe) {
        // Create the values from the given recipe
        ContentValues values = new ContentValues();
        values.put(RECIPE_NAME, recipe.getName());
        // Inserts the new recipe
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(RECIPE_TABLE, null, values);
        db.close();
        return id;
    }

    /** Inserts a new recipe ingredient into the database.
     *
     * @param recipe The recipe the ingredient belongs to.
     * @param ingredient The ingredient to add to the recipe.
     * @return The id of the new recipe.
     */
    public long insertRecipeIngredient(Recipe recipe, Ingredient ingredient) {
        // Create the values from the given recipe and ingredient
        ContentValues values = new ContentValues();
        values.put(RECIPE_INGREDIENTS_RECIPE_ID, recipe.getId());
        values.put(RECIPE_INGREDIENTS_INGREDIENT_ID, ingredient.getId());
        values.put(RECIPE_INGREDIENTS_AMOUNT, ingredient.getAmount());

        // Inserts the new recipe ingredient
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(RECIPE_INGREDIENTS_TABLE, null, values);

        // Close the database and return
        db.close();
        return id;
    }

    /** Inserts all the ingredients in the recipe into the database.
     *
     * @param recipe The recipe to have the ingredients inserted for.
     */
    public void insertRecipeIngredients(Recipe recipe) {
        // Loop through the recipe's ingredients
        for (Ingredient ingredient: recipe.ingredients) {
            insertRecipeIngredient(recipe, ingredient);
        }
    }

    /** Inserts a new store ingredient into the database.
     *
     * @param store The store the ingredient is to be added to.
     * @param ingredient The ingredient to add to the store.
     * @return The id of the inserted store ingredient.
     */
    public long insertStoreIngredient(Store store, Ingredient ingredient) {
        // Create the values from the given store and ingredient
        ContentValues values = new ContentValues();
        values.put(STORE_INGREDIENTS_STORE_ID, store.getId());
        values.put(STORE_INGREDIENTS_INGREDIENT_ID, ingredient.getId());
        values.put(STORE_INGREDIENTS_AMOUNT, ingredient.getAmount());
        values.put(STORE_INGREDIENTS_PRICE, ingredient.getPrice());

        // Inserts the new store ingredient
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(STORE_INGREDIENTS_TABLE, null, values);

        // Close the database and return
        db.close();
        return id;
    }

    /** Gets a list of recipes from the database.
     *
     * @return An ArrayList of Recipe objects.
     */
    public ArrayList<Recipe> getRecipes() {
        // Query the database
        String get_recipes = "SELECT id, name FROM " + RECIPE_TABLE;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(get_recipes, null);

        // Get the recipes from the result
        ArrayList<Recipe> recipes = new ArrayList<>();

        // Loop through the returned cursor
        if (cursor.moveToFirst()) {
            do {
                // Adds the new recipe to the list
                recipes.add(new Recipe(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }

        // Close the db connections
        cursor.close();
        db.close();

        // Return the recipe list
        return recipes;
    }

    /** Get the list of ingredients that exist in the database.
     *
     * @return An ArrayList of the ingredients.
     */
    public ArrayList<Ingredient> getIngredients() {
        // Query the database
        String get_ingredients = "SELECT " + INGREDIENTS_ID + ", " + INGREDIENTS_NAME + ", " +
                INGREDIENTS_MEASUREMENT_TYPE +
                " FROM " + INGREDIENTS_TABLE + ";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(get_ingredients, null);

        // The ArrayList of ingredients to be returned
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        // Loop through the returned cursor
        if (cursor.moveToFirst()) {
            do {
                // Get the information on the ingredient
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String units = cursor.getString(2);
                // Add the new ingredient to the list
                ingredients.add(new Ingredient(id, name, units));
            } while (cursor.moveToNext());
        }

        // Close the db connection
        cursor.close();
        db.close();

        // Return the list of ingredients from the database
        return ingredients;
    }


    public ArrayList<Store> getStores() {
        // Query the database
        String get_store = "SELECT " +
                STORE_ID + ", " +
                STORE_NAME +
                " FROM " + STORE_TABLE + ";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(get_store, null);

        // The ArrayList of ingredients to be returned
        ArrayList<Store> stores = new ArrayList<>();

        // Loop through the returned cursor
        if (cursor.moveToFirst()) {
            do {
                // Get the information on the ingredient
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                // Add the new ingredient to the list
                stores.add(new Store(id, name, null));
            } while (cursor.moveToNext());
        }

        // Close the db connection
        cursor.close();
        db.close();

        // Return the list of ingredients from the database
        return stores;
    }

        public Ingredient getIngredientByName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("SELECT i.%s, i.%s, i.%s\n" +
                "FROM %s as i\n" +
                "WHERE i.%s = ?", INGREDIENTS_ID, INGREDIENTS_NAME, INGREDIENTS_MEASUREMENT_TYPE,
                INGREDIENTS_TABLE, INGREDIENTS_NAME);
        Cursor cursor = db.rawQuery(sql, new String[] {name});
        if (cursor.moveToFirst() && cursor.getCount() == 1) {
            int ingredient_id = cursor.getInt(0);
            String ingredient_name = cursor.getString(1);
            String measure = cursor.getString(2);
            cursor.close();
            db.close();
            return new Ingredient(ingredient_id, ingredient_name, measure);
        }
        else {
            cursor.close();
            db.close();
            return null;
        }
    }

    /** Gets the price of a list of ingredients at a given store.
     *
     * @param store The store.
     * @param ingredients The ingredients.
     * @return
     */
    public Store getPriceAtStore(Store store, ArrayList<Ingredient> ingredients) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("SELECT %s\n" +
                "FROM %s\n" +
                "WHERE %s = ? AND %s = ?", STORE_INGREDIENTS_PRICE, STORE_INGREDIENTS_TABLE,
                STORE_INGREDIENTS_STORE_ID, STORE_INGREDIENTS_INGREDIENT_ID);
        // The total price to be returned to be returned in the store
        double total_price = 0;
        // The total number of not found ingredients to be returned in the store
        int total_not_found = 0;
        // Loop through all the ingredients and attempt to add it to the total
        for (Ingredient ingredient: ingredients) {
            Cursor cursor = db.rawQuery(sql, new String[] {Integer.toString(store.getId()),
                    Integer.toString(ingredient.getId())});
            // If the ingredient was found for the store
            if (cursor.moveToFirst() && cursor.getCount() == 1) {
                total_price += cursor.getDouble(0);
            }
            // If the ingredient was not found for the store
            else {
                total_not_found++;
            }
        }
        // Create a new store object to avoid memory issues
        Store return_recipe = new Store(store.getId(), store.getName(), store.getAddress());
        return_recipe.setPrice(total_price);
        return_recipe.setItems_not_found(total_not_found);
        return return_recipe;
    }

    public Recipe getRecipeByName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("SELECT r.%s, i.%s, i.%s, ri.%s, i.%s\n" +
                "FROM %s\n as ri\n" +
                "INNER JOIN %s AS r ON ri.%s = r.%s\n" +
                "INNER JOIN %s AS i ON ri.%s = i.%s\n" +
                "WHERE r.%s = ?", RECIPE_ID, INGREDIENTS_ID, INGREDIENTS_NAME,
                RECIPE_INGREDIENTS_AMOUNT, INGREDIENTS_MEASUREMENT_TYPE,
                RECIPE_INGREDIENTS_TABLE,
                RECIPE_TABLE, RECIPE_INGREDIENTS_RECIPE_ID, RECIPE_ID,
                INGREDIENTS_TABLE, RECIPE_INGREDIENTS_INGREDIENT_ID, INGREDIENTS_ID,
                RECIPE_NAME);
        Cursor cursor = db.rawQuery(sql, new String[] {name});
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            do {
                int ingredient_id = cursor.getInt(1);
                String ingredient_name = cursor.getString(2);
                double ingredient_amount = cursor.getDouble(3);
                String measure = cursor.getString(4);
                Ingredient ingredient = new Ingredient(ingredient_id, ingredient_name,
                        ingredient_amount, measure);
                ingredients.add(ingredient);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
            return new Recipe(id, name, ingredients);
        }
        else {
            cursor.close();
            db.close();
            return null;
        }
    }

    public ArrayList<Ingredient> getStoreIngredients(Store store){
        // Query the database
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        String get_store_products = "SELECT s." + STORE_NAME + ", i." +
                INGREDIENTS_ID + ", i." + INGREDIENTS_NAME + ", i." + INGREDIENTS_MEASUREMENT_TYPE +
                ", si." + STORE_INGREDIENTS_PRICE +  ", si." + STORE_INGREDIENTS_AMOUNT +
                " FROM " + STORE_TABLE + " AS s" +
                " INNER JOIN " + STORE_INGREDIENTS_TABLE + " AS si" +
                " ON s." + STORE_ID + " = si." + STORE_INGREDIENTS_STORE_ID +
                " INNER JOIN " + INGREDIENTS_TABLE + " AS i" +
                " ON i." + INGREDIENTS_ID + " = si." + STORE_INGREDIENTS_INGREDIENT_ID +
                " WHERE s.id = " + store.getId() + ";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(get_store_products, null);
        System.out.println(get_store_products);

        // Loop through the returned cursor
        if (cursor.moveToFirst()) {
            // Set the cursor name
            //store.setName(cursor.getString(0));
            do {
                // Get the ingredient information
                int id = cursor.getInt(1);
                String name = cursor.getString(2);
                String measurement_type = cursor.getString(3);
                double price = cursor.getDouble(4);
                double amount = cursor.getDouble(5);
                // Create and insert the ingredient into the recipe object
                Ingredient new_ingredient = new Ingredient(id, name, amount, measurement_type, price);
                ingredients.add(new_ingredient);
                //recipe.addIngredient(new_ingredient);
            } while (cursor.moveToNext());
        }

        // Close the db connection
        cursor.close();
        db.close();

        // Return the recipe
        return ingredients;
    }

    /** Gets all of the ingredients for the given recipe.
     *
     * @param recipe The given recipe.
     * @return The recipe with the ingredients added.
     */
    public ArrayList<Ingredient> getRecipeIngredients(Recipe recipe) {
        // Query the database
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        String get_recipe_ingredients = "SELECT r." + RECIPE_NAME + ", i." +
                INGREDIENTS_ID + ", i." + INGREDIENTS_NAME + ", i." + INGREDIENTS_MEASUREMENT_TYPE +
                ", ri." + RECIPE_INGREDIENTS_AMOUNT +
                " FROM " + RECIPE_TABLE + " AS r" +
                " INNER JOIN " + RECIPE_INGREDIENTS_TABLE + " AS ri" +
                " ON r." + RECIPE_ID + " = ri." + RECIPE_INGREDIENTS_RECIPE_ID +
                " INNER JOIN " + INGREDIENTS_TABLE + " AS i" +
                " ON i." + INGREDIENTS_ID + " = ri." + RECIPE_INGREDIENTS_INGREDIENT_ID +
                " WHERE r.id = " + recipe.getId() + ";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(get_recipe_ingredients, null);
        System.out.println(get_recipe_ingredients);

        // Loop through the returned cursor
        if (cursor.moveToFirst()) {
            // Set the cursor name
            recipe.setName(cursor.getString(0));
            do {
                // Get the ingredient information
                int id = cursor.getInt(1);
                String name = cursor.getString(2);
                String measurement_type = cursor.getString(3);
                double amount = cursor.getDouble(4);
                // Create and insert the ingredient into the recipe object
                Ingredient new_ingredient = new Ingredient(id, name, amount, measurement_type);
                ingredients.add(new_ingredient);
                //recipe.addIngredient(new_ingredient);
            } while (cursor.moveToNext());
        }

        // Close the db connection
        cursor.close();
        db.close();

        // Return the recipe
        return ingredients;
    }

    public void deleteRecipe(Recipe recipe){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(RECIPE_TABLE, RECIPE_ID + " = ?",
                new String[] { String.valueOf(recipe.getId()) });
        db.close();
    }

    public void clearRecipeIngredients(Recipe recipe){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(RECIPE_INGREDIENTS_TABLE, RECIPE_INGREDIENTS_RECIPE_ID + " = ?",
                new String[] { String.valueOf(recipe.getId()) });
        db.close();
    }

    /** Writes both new values and updated values into the database.
     *
     * @param id
     * @param ingredients
     */
    public void writePrices(int id, ArrayList<Ingredient> ingredients) {
        SQLiteDatabase db = getWritableDatabase();
        // Check if the item already exists in the database
        String check_sql = String.format("SELECT %s, %s FROM %s WHERE %s = ? AND %s = ?",
                STORE_INGREDIENTS_INGREDIENT_ID, STORE_INGREDIENTS_PRICE, STORE_INGREDIENTS_TABLE,
                STORE_INGREDIENTS_STORE_ID, STORE_INGREDIENTS_INGREDIENT_ID);
        // Loop through all the ingredients
        for (Ingredient ingredient: ingredients) {
            Cursor check_cursor = db.rawQuery(check_sql, new String[] {Integer.toString(id),
                    Integer.toString(ingredient.getId())});
            // If it exist already, get the id and update
            if (check_cursor.getCount() == 1) {
                check_cursor.moveToFirst();
                int row_id = check_cursor.getInt(0);
                double stored_price = check_cursor.getDouble(1);
                if (ingredient.getPrice() < stored_price) {
                    ContentValues values = new ContentValues();
                    values.put(STORE_INGREDIENTS_PRICE, ingredient.getPrice());
                    db.update(STORE_INGREDIENTS_TABLE, values,
                            STORE_INGREDIENTS_ID + "=" + row_id, null);
                }
            }
            // If it doesn't exist already, insert it
            else {
                ContentValues values = new ContentValues();
                values.put(STORE_INGREDIENTS_STORE_ID, id);
                values.put(STORE_INGREDIENTS_INGREDIENT_ID, ingredient.getId());
                values.put(STORE_INGREDIENTS_PRICE, ingredient.getPrice());
                db.insert(STORE_INGREDIENTS_TABLE, null, values);
            }
        }
    }

    public void deleteStores(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ STORE_TABLE);
        db.close();
    }

    public void deleteStore(Store store){
        SQLiteDatabase db = getWritableDatabase();
        db.close();
    }
}
