package com.example.android.todoapplication2;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NewListElementDialog.NewListElementDialogListener, NewCategoryDialog.NewCategoryDialogListener, NewListDialog.NewListDialogListener{

    private DrawerLayout drawer;
    private Category1Fragment categoryFragment;
    private NavigationView navView;
    private Menu drawerMenu;
    private SimpleFragmentPagerAdapter adapter;

    private ArrayList<String> allCategoriesName = new ArrayList<>();
    private ArrayList<ArrayList<String>> datenbank = new ArrayList<>();
    private ArrayList<Liste1Fragment> allLists = new ArrayList<>();

    private String currentCategoryName;

    public String GetCurrentCategory() { return currentCategoryName; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navView = (NavigationView) findViewById(R.id.nav_view);
        drawerMenu = navView.getMenu();

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewListElementDialog();
            }
        });*/

        // doesn't activate when device is rotated for example
        if (savedInstanceState == null) {
            // Fragment that is shown when app is started
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Category1Fragment()).commit();
            getSupportActionBar().setTitle("Kategorie 1");
            /*navigationView.setCheckedItem(R.id.nav_category1);*/
        }


        /* Tab Layout */

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        setDefaultState();
    }

    private void setDefaultState(){
        int indexOfCategoryInArray;
        indexOfCategoryInArray = setNewCategory("SWT");
        addListToCategoryInDB(indexOfCategoryInArray, "Klausur");
        addListToCategoryInDB(indexOfCategoryInArray, "Projekt");
        addListToCategoryInDB(indexOfCategoryInArray, "Vorbereitung");

        setNewList("SWT", "Klausur");
        setNewList("SWT", "Projekt");
        setNewList("SWT", "Vorbereitung");


        indexOfCategoryInArray = setNewCategory("OOP");
        addListToCategoryInDB(indexOfCategoryInArray, "Vorlesung");
        addListToCategoryInDB(indexOfCategoryInArray, "Labor");

        setNewList("OOP", "Vorlesung");
        setNewList("OOP", "Labor");


        indexOfCategoryInArray = setNewCategory("GDV");
        addListToCategoryInDB(indexOfCategoryInArray, "Shading");
        addListToCategoryInDB(indexOfCategoryInArray, "Lighting");

        setNewList("GDV", "Shading");
        setNewList("GDV", "Lighting");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add_new_list:
                NewListDialog newListDialog = new NewListDialog();
                newListDialog.show(getSupportFragmentManager(), "new list dialog");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_category_new:
                openNewCategoryDialog();
                break;
            case R.id.nav_category_edit:
                Toast.makeText(this, "Kategorie bearbeiten", Toast.LENGTH_SHORT).show();
                break;
            default:
                currentCategoryName = item.getTitle().toString();
                getSupportActionBar().setTitle(currentCategoryName);

                adapter.removeAllLists();
                adapter.notifyDataSetChanged();

                for(Liste1Fragment list : allLists) {
                    if (list.getParentCategory().equals(currentCategoryName)) {
                        adapter.addList(list, list.getName());
                        adapter.notifyDataSetChanged();
                    }
                }

                populateListView();
                populateListElements();
                Log.i("MainActivity", "Selected Category: " + item.getTitle());
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void populateListView() {}
    private void populateListElements() {}

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void openNewCategoryDialog() {
        NewCategoryDialog newCategoryDialog = new NewCategoryDialog();
        newCategoryDialog.show(getSupportFragmentManager(), "new category dialog");
    }

    private void openNewListElementDialog() {
        NewListElementDialog newListElementDialog = new NewListElementDialog();
        newListElementDialog.show(getSupportFragmentManager(), "new list element dialog");
    }

    @Override
    public void addNewCategory(String newCategoryName) {
        setNewCategory(newCategoryName);
    }

    @Override
    public void addNewListElement(String new_list_element) {
        Toast.makeText(this, "Neuer Listeneintrag: " + new_list_element, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addNewList(String newListName) {
        Liste1Fragment newList = new Liste1Fragment();
        newList.setParentCategory(currentCategoryName);
        newList.setName(newListName);
        allLists.add(newList);
        adapter.addList(newList, newListName);
        adapter.notifyDataSetChanged();

        /* add list to category in DB*/
        for(int i = 0; i < allCategoriesName.size(); i++){
            if(allCategoriesName.get(i).equals(currentCategoryName)){
                addListToCategoryInDB(i,newListName);
            }
        }

        /*Show all lists of a category*/
        for (int i = 0; i < datenbank.size(); i++){
            int elementsCount = datenbank.get(i).size();
            for(int j = 0; j < elementsCount; j++){
                int categoryIndex = i;
                String listName = datenbank.get(i).get(j);
                Log.i("MainActivity", "Category " + categoryIndex + " besitzt Liste: " + listName + "\n");
            }
        }
    }

    private int setNewCategory(String categoryName){
        allCategoriesName.add(categoryName);
        datenbank.add(new ArrayList<String>());
        currentCategoryName = categoryName;

        drawerMenu.add(R.id.group_categories, Menu.NONE, 1, categoryName).setIcon(R.drawable.ic_category);

        /* return index of category */
        return allCategoriesName.size() - 1;
    }

    private void setNewList(String parentCategory, String listName){
        Liste1Fragment newList = new Liste1Fragment();
        newList.setParentCategory(parentCategory);
        newList.setName(listName);
        allLists.add(newList);
    }

    private void addListToCategoryInDB(int indexOfCategory, String newListName){
        datenbank.get(indexOfCategory).add(newListName);
        Log.i("MainActivity", "Neue Liste " + datenbank.get(indexOfCategory).get(0)  + " eingefügt in Kategorie " + allCategoriesName.get(indexOfCategory) + "\n");
    }
}
