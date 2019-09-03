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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NewListElementDialog.NewListElementDialogListener, NewCategoryDialog.NewCategoryDialogListener, NewListDialog.NewListDialogListener{

    private DrawerLayout drawer;
    private ArrayList<Category1Fragment> allCategories = new ArrayList<>();
    private Category1Fragment mainFragment;
    private NavigationView navView;
    private Menu menu;
    private SimpleFragmentPagerAdapter adapter;

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

        initializeDefaultCategory();

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
            navigationView.setCheckedItem(R.id.nav_category1);
        }


        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());

        adapter.addList(new Liste1Fragment(), "Drogerie");
        adapter.addList(new Liste1Fragment(), "Lebensmittel");

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initializeDefaultCategory(){
        Category1Fragment newCategory = new Category1Fragment();
        newCategory.setName("SWT");
        allCategories.add(newCategory);
        mainFragment = newCategory;

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

        Category1Fragment newCategory = new Category1Fragment();
        newCategory.setName(newCategoryName);
        allCategories.add(newCategory);

        navView = (NavigationView) findViewById(R.id.nav_view);
        menu = navView.getMenu();
        menu.add(R.id.group_categories, Menu.NONE, 1, newCategoryName).setIcon(R.drawable.ic_category);
    }

    @Override
    public void addNewListElement(String new_list_element) {
        Toast.makeText(this, "Neuer Listeneintrag: " + new_list_element, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addNewList(String newListName) {
        adapter.addList(new Liste1Fragment(), newListName);
        adapter.notifyDataSetChanged();
    }
}
