package com.bullseyedevs.tableview;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;

import com.bullseyedevs.tableview.adapter.ClubAdapter;
import com.bullseyedevs.tableview.model.Club;
import com.bullseyedevs.tableview.util.FixedGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    int scrollX = 0;

    List<Club> clubList = new ArrayList<>();

    RecyclerView rvClub;

    HorizontalScrollView headerScroll;

    SearchView searchView;

    ClubAdapter clubAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        prepareClubData();

        setUpRecyclerView();

        rvClub.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);

                scrollX += dx;

                headerScroll.scrollTo(scrollX, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                // filter recycler view when query submitted
                clubAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query)
            {
                // filter recycler view when text is changed
                clubAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        // close search view on back button pressed
        if (!searchView.isIconified())
        {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void initViews()
    {
        rvClub = findViewById(R.id.rvClub);
        headerScroll = findViewById(R.id.headerScroll);
    }

    /**
     * Prepares dummy data
     */
    private void prepareClubData()
    {
        clubList.add(new Club("Galatasaray", "https://tmssl.akamaized.net/images/wappen/head/141.png", "Istanbul, Turkey", "Ali Sami Yen", "Süper Lig", "Fatih Terim", "Bafetimbi Gomis"));
        clubList.add(new Club("Real Madrid", "https://tmssl.akamaized.net//images/wappen/head/418.png", "Madrid, Spain", "Santiago Barnabeu", "La Liga", "Zidane", "Cristiano Ronaldo"));
        clubList.add(new Club("Barcelona", "https://tmssl.akamaized.net//images/wappen/head/131.png", "Barcelona, Spain", "Camp Nou", "La Liga", "Ernesto Valverde", "Lionel Messi"));
        clubList.add(new Club("Bayern München", "https://tmssl.akamaized.net//images/wappen/head/27.png", "München, Germany", "Allianz Arena", "Bundesliga", "Jupp Heynckes", "Robert Lewandowski"));
        clubList.add(new Club("Manchester United", "https://tmssl.akamaized.net//images/wappen/head/985.png", "Manchester, England", "Old Trafford", "Premier League", "Jose Mourinho", "Paul Pogba"));
        clubList.add(new Club("Manchester City", "https://tmssl.akamaized.net//images/wappen/head/281.png", "Manchester, England", " Etihad Stadium", "Premier League", "Pep Guardiola", "Kevin de Bruyne"));
        clubList.add(new Club("Atletico Madrid", "https://tmssl.akamaized.net//images/wappen/head/13.png", "Madrid, Spain", "Estadio Metropolitano de Madrid ", "La Liga", "Diego Simeone", "Antoine Griezmann"));
        clubList.add(new Club("Liverpool", "https://tmssl.akamaized.net//images/wappen/head/31.png", "Liverpool, Spain", "Anfield", "Premier League", "Klopp", "Mo Salah"));
        clubList.add(new Club("Juventus", "https://tmssl.akamaized.net//images/wappen/head/506.png", "Turin, Italy", "Allianz Stadium", "Serie A", "Massimiliano Allegri", "Paulo Dybala"));
        clubList.add(new Club("Arsenal", "https://tmssl.akamaized.net//images/wappen/head/11.png", "London, England", "Emirates Stadium", "Premier League", "Arsene Wenger", "Mesut Özil"));
        clubList.add(new Club("Roma", "https://tmssl.akamaized.net//images/wappen/head/12.png", "Rome, Italy", " Olimpico di Roma", "Serie A", "Eusebio Di Francesco", "Cengiz Ünder"));
        clubList.add(new Club("PSG", "https://tmssl.akamaized.net//images/wappen/head/583.png", "Paris, France", "Parc des Princes ", "Ligue 1", "Unai Emery", "Neymar"));
        clubList.add(new Club("Chelsea", "https://tmssl.akamaized.net//images/wappen/head/631.png", "London, England", "Stamford Bridge", "Premier League", "Conte", "Eden Hazard"));
        clubList.add(new Club("Tottenham", "https://tmssl.akamaized.net//images/wappen/head/148.png", "London, England", "Wembley Stadium ", "Premier League", "Mauricio Pochettino", "Harry Kane"));
    }

    /**
     * Handles RecyclerView for the action
     */
    private void setUpRecyclerView()
    {
        clubAdapter = new ClubAdapter(MainActivity.this, clubList);

        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvClub.setLayoutManager(manager);
        rvClub.setAdapter(clubAdapter);
        rvClub.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
    }
}
