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
        allCategoriesName.add("SWT");
        datenbank.add(new ArrayList<String>());
        currentCategoryName = "SWT";

        drawerMenu.add(R.id.group_categories, Menu.NONE, 1, "SWT").setIcon(R.drawable.ic_category);

        addListToCategoryInDB(0, "Klausur");
        addListToCategoryInDB(0, "Projekt");
        addListToCategoryInDB(0, "Vorbereitung");

        Liste1Fragment newList = new Liste1Fragment();
        newList.setParentCategory("SWT");
        newList.setName("Klausur");
        allLists.add(newList);

        Liste1Fragment newList2 = new Liste1Fragment();
        newList2.setParentCategory("SWT");
        newList2.setName("Projekt");
        allLists.add(newList2);

        Liste1Fragment newList3 = new Liste1Fragment();
        newList3.setParentCategory("SWT");
        newList3.setName("Vorbereitung");
        allLists.add(newList3);



        allCategoriesName.add("OOP");
        datenbank.add(new ArrayList<String>());
        drawerMenu.add(R.id.group_categories, Menu.NONE, 1, "OOP").setIcon(R.drawable.ic_category);

        addListToCategoryInDB(1, "Testat");
        addListToCategoryInDB(1, "Üben");

        Liste1Fragment newList4 = new Liste1Fragment();
        newList4.setParentCategory("OOP");
        newList4.setName("Testat");
        allLists.add(newList4);

        Liste1Fragment newList5 = new Liste1Fragment();
        newList5.setParentCategory("OOP");
        newList5.setName("Üben");
        allLists.add(newList5);

        allCategoriesName.add("GDV");
        datenbank.add(new ArrayList<String>());
        drawerMenu.add(R.id.group_categories, Menu.NONE, 1, "GDV").setIcon(R.drawable.ic_category);
        addListToCategoryInDB(2, "Shading");
        addListToCategoryInDB(2, "Lighting");

        Liste1Fragment newList6 = new Liste1Fragment();
        newList6.setParentCategory("GDV");
        newList6.setName("Shading");
        allLists.add(newList6);

        Liste1Fragment newList7 = new Liste1Fragment();
        newList7.setParentCategory("GDV");
        newList7.setName("Lighting");
        allLists.add(newList7);

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
        allCategoriesName.add(newCategoryName);
        datenbank.add(new ArrayList<String>());
        drawerMenu.add(R.id.group_categories, Menu.NONE, 1, newCategoryName).setIcon(R.drawable.ic_category);
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

    private void addListToCategoryInDB(int indexOfCategory, String newListName){
        datenbank.get(indexOfCategory).add(newListName);
        Log.i("MainActivity", "Neue Liste " + datenbank.get(indexOfCategory).get(0)  + " eingefügt in Kategorie " + allCategoriesName.get(indexOfCategory) + "\n");
    }
}
