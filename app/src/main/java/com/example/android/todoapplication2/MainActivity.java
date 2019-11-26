package com.example.android.todoapplication2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NewListElementDialog.NewListElementDialogListener, NewCategoryDialog.NewCategoryDialogListener, NewListDialog.NewListDialogListener {

    ViewPager viewPager;
    private DrawerLayout drawer;
    private NavigationView navView;
    private Menu drawerMenu;
    private SimpleFragmentPagerAdapter adapter;

    private final String PrefName = "Todo_App_Pref_Name";
    private final String TodoIdPrefName = "Todo_Id";
    private SharedPreferences SharedPreferences;


    ArrayList<String> categoryDatabase = new ArrayList<>();
    ArrayList<TodoTab> tabDatabase = new ArrayList<>();
    static ArrayList<Todo> todoDatabase = new ArrayList<>();


    String currentCategory;
    String currentTab;


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

        navView = findViewById(R.id.nav_view);
        drawerMenu = navView.getMenu();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.bringToFront();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewListElementDialog();
            }
        });

        /* Tab Layout */
        viewPager = findViewById(R.id.viewpager);
        adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                adapter.UpdateTodoList(queryTodos(adapter.getPageTitle(i).toString()));
                currentTab = adapter.getPageTitle(i).toString();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        SharedPreferences = getSharedPreferences(PrefName, Context.MODE_PRIVATE);

        setupTestDatabase();
        currentCategory = categoryDatabase.get(0);
        switchCategory(currentCategory);

        currentTab = queryTodoTabs(currentCategory).get(0).Name;

        // doesn't activate when device is rotated for example
        if (savedInstanceState == null) {
            FragmentManager fm = MainActivity.this.getSupportFragmentManager();
            fm.executePendingTransactions();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, adapter.mTodoList).commit();
        }
    }

    // Only for Debug Purpose
    void setupTestDatabase() {
        addNewCategory("SWT");
        addNewCategory("AV");
        addNewCategory("GDV");

        tabDatabase.add(new TodoTab("Klausur", "SWT"));
        tabDatabase.add(new TodoTab("Projekt", "SWT"));

        tabDatabase.add(new TodoTab("Vorlesung", "AV"));
        tabDatabase.add(new TodoTab("Praktikum", "AV"));
        tabDatabase.add(new TodoTab("Studientagebuch", "AV"));

        tabDatabase.add(new TodoTab("Shading", "GDV"));

        int id = GetCurrentTodoId();
        todoDatabase.add(new Todo(++id, "Projekt", "Dokumentation schreiben", true));
        todoDatabase.add(new Todo(++id,"Projekt", "Diagramme erstellen", false));
        todoDatabase.add(new Todo(++id,"Projekt", "Prototypen entwickeln", true));
        todoDatabase.add(new Todo(++id,"Klausur", "Notizen zusammenfassen", false));
        todoDatabase.add(new Todo(++id,"Klausur", "Mit Lerngruppe treffen", false));
        todoDatabase.add(new Todo(++id,"Klausur", "Lernzettel schreiben", true));
        todoDatabase.add(new Todo(++id,"Klausur", "Aufgaben bearbeiten", false));

        todoDatabase.add(new Todo(++id,"Vorlesung", "Notizen machen", false));
        todoDatabase.add(new Todo(++id,"Vorlesung", "Vorlesung besuchen", false));
        todoDatabase.add(new Todo(++id,"Praktikum", "Aufnahmen machen",true));
        todoDatabase.add(new Todo(++id,"Praktikum", "Locations finden", false));
        todoDatabase.add(new Todo(++id,"Praktikum", "Storyboard schreiben", false));
        todoDatabase.add(new Todo(++id,"Studientagebuch", "Nach Kamerasystemen recherchieren", false));
        todoDatabase.add(new Todo(++id,"Studientagebuch", "Aufsatz schreiben", false));

        todoDatabase.add(new Todo(++id,"Shading", "Shader erstellen", false));
        todoDatabase.add(new Todo(++id,"Shading", "Texturen finden",true));

        SetCurrentTodoId(id);
    }

    int GetCurrentTodoId()
    {
        return SharedPreferences.getInt(TodoIdPrefName, 0);
    }

    void SetCurrentTodoId(int value)
    {
        SharedPreferences.Editor editor = SharedPreferences.edit();
        editor.putInt(TodoIdPrefName, value);
        editor.commit();
    }

    void switchCategory(String category)
    {
        currentCategory = category;
        getSupportActionBar().setTitle(category);

        adapter.clearView();
        ArrayList<TodoTab> filteredTodoTabs = queryTodoTabs(currentCategory);
        for (TodoTab todoTab : filteredTodoTabs)
            adapter.addPage(todoTab.Name);

        if(filteredTodoTabs.size() > 0)
            adapter.UpdateTodoList(queryTodos(filteredTodoTabs.get(0).Name));

        adapter.notifyDataSetChanged();
        viewPager.setCurrentItem(0);
    }

    // Provisionally Database query
    ArrayList<TodoTab> queryTodoTabs(String category)
    {
        ArrayList<TodoTab> todoTabs = new ArrayList<>();
        for (TodoTab todoTab : tabDatabase) {
            if(todoTab.Category.equals(category))
            {
                todoTabs.add(todoTab);
            }
        }

        return todoTabs;
    }

    // Provisionally Database Query
    ArrayList<Todo> queryTodos(String tab)
    {
        ArrayList<Todo> todos = new ArrayList<>();
        for (Todo todo : todoDatabase) {
            if(todo.tab.equals(tab))
                todos.add(todo);
        }

        return todos;
    }

    // Provisionally Database Query
    public static void EditTodoDatabaseEntry(int id, boolean isChecked)
    {
        for (Todo todo : todoDatabase) {
            if(todo.Id == id)
            {
                todo.isChecked = isChecked;
            }
        }
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
                switchCategory(item.getTitle().toString());
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

    private void openNewCategoryDialog() {
        NewCategoryDialog newCategoryDialog = new NewCategoryDialog();
        newCategoryDialog.show(getSupportFragmentManager(), "new category dialog");
    }

    @Override
    public void addNewCategory(String name) {
        categoryDatabase.add(name);

        drawerMenu.add(R.id.group_categories, Menu.NONE, 1, name).setIcon(R.drawable.ic_category);
    }

    public void openNewListElementDialog() {
        NewListElementDialog newListElementDialog = new NewListElementDialog();
        newListElementDialog.show(getSupportFragmentManager(), "new list element dialog");
    }

    @Override
    public void addNewListElement(String newListElementName) {
        Toast.makeText(this, "Neuer Listeneintrag: " + newListElementName, Toast.LENGTH_SHORT).show();

        addNewListElementDB(newListElementName);
        adapter.UpdateTodoList(queryTodos(currentTab));
    }

    private void addNewListElementDB(String newListElementName) {
        int id = GetCurrentTodoId();
        id++;
        todoDatabase.add(new Todo(id, currentTab, newListElementName, false));
        SetCurrentTodoId(id);
    }

    @Override
    public void addNewList(String newListName) {
        Log.i("MY", "addNewList called on MainActivity!");
        adapter.addPage(newListName);
        addNewListDB(newListName);
    }

    private void addNewListDB(String newListName){
        tabDatabase.add(new TodoTab(newListName, currentCategory));
    }
}
