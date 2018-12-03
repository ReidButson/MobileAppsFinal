package com.mobileapps.uoit.receipy;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
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
                STORE_INGREDIENTS_AMOUNT + " Real NOT NULL, " +
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

    public void insertStoreIngredients(Store store, ArrayList<Ingredient> ingredients){
        for(Ingredient i: ingredients){
            insertStoreIngredient(store, i);
        }
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

    /** Gets an ingredient by checking the database for a given name
     *
     * @param name The name of the ingredient.
     * @return The ingredient from the database or null if it was not found.
     */
    public Ingredient getIngredientByName(String name) {
        String sql = String.format("SELECT %s FROM %s WHERE %s=?",
                INGREDIENTS_ID, INGREDIENTS_TABLE, INGREDIENTS_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[] {name});

        // Check to make sure only 1 ingredient was returned
        if (cursor.getCount() == 1) {
            // TODO BRADON CONTINUE HERE
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

    /** Gets a recipe from the database with the given name
     *
     * @param name The name of the recipe.
     * @return The recipe with it's ingredients.
     */
    public Recipe getRecipeByName(String name) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(String.format(
                "SELECT %s FROM %s WHERE %s=?", RECIPE_ID, RECIPE_TABLE, RECIPE_NAME),
                new String[] {name});
        if (cursor.getCount() == 1) {
            int recipe_id = cursor.getInt(0);
            return getRecipeIngredients(recipe_id);
        }
        else {
            return null;
        }
    }

    /** Gets all of the ingredients for the given recipe.
     *
     * @param id The given recipe id.
     * @return The recipe with the ingredients added.
     */
    public Recipe getRecipeIngredients(int id) {
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
                " WHERE r.id = " + id + ";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(get_recipe_ingredients, null);
        System.out.println(get_recipe_ingredients);

        // The recipe to be returned
        Recipe recipe = null;

        // Loop through the returned cursor
        if (cursor.moveToFirst()) {
            // Create the recipe
            recipe = new Recipe(id, cursor.getString(0));
            do {
                // Get the ingredient information
                int recipe_id = cursor.getInt(1);
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
        return recipe;
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

    public void deleteStore(Store store){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(STORE_TABLE, STORE_ID + " = ?",
                new String[] { String.valueOf(store.getId()) });

        db.delete(STORE_INGREDIENTS_TABLE, STORE_ID + " = ?", new String[] {String.valueOf(store.getId())});
        db.close();
    }

    public void clearStoreIngredients(Store store){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(STORE_INGREDIENTS_TABLE,  STORE_ID + " = ?",
                new String[] { String.valueOf(store.getId()) });
        db.close();
    }

    public void clearShit(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + STORE_INGREDIENTS_TABLE);
        db.close();
    }

    public void deleteStores(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ STORE_TABLE);
        db.close();
    }
}
