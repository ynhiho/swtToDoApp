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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NewListElementDialog.NewListElementDialogListener, NewCategoryDialog.NewCategoryDialogListener {

    private DrawerLayout drawer;
    private ArrayList<Category1Fragment> allCategories = new ArrayList<>();
    private NavigationView navView;
    private Menu menu;
    private String categoryName = "";

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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewListElementDialog();
            }
        });

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
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_category1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Category1Fragment()).commit();
                getSupportActionBar().setTitle("Kategorie 1");
                break;
            case R.id.nav_category2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Category2Fragment()).commit();
                getSupportActionBar().setTitle("Kategorie 2");
                break;
            case R.id.nav_category3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Category3Fragment()).commit();
                getSupportActionBar().setTitle("Kategorie 3");
                break;
            case R.id.nav_category_new:
                openNewCategoryDialog();
                break;
            case R.id.nav_category_edit:
                Toast.makeText(this, "Kategorie bearbeiten", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void openNewCategoryDialog(){
        NewCategoryDialog newCategoryDialog = new NewCategoryDialog();
        newCategoryDialog.show(getSupportFragmentManager(), "new category dialog");
    }

    private void openNewListElementDialog(){
        NewListElementDialog newListElementDialog = new NewListElementDialog();
        newListElementDialog.show(getSupportFragmentManager(), "new list element dialog");
    }

    @Override
    public void addNewCategory(String newCategoryName){
        navView = (NavigationView) findViewById(R.id.nav_view);
        menu = navView.getMenu();
        menu.add(R.id.group_categories, Menu.NONE, 1, newCategoryName).setIcon(R.drawable.ic_category);
    }

    @Override
    public void addNewListElement(String new_list_element){
        Toast.makeText(this, "Neuer Listeneintrag: " + new_list_element, Toast.LENGTH_SHORT).show();
    }
}
