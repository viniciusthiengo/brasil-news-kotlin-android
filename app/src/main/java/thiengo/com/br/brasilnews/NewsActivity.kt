package thiengo.com.br.brasilnews

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.app_bar_news.*
import thiengo.com.br.brasilnews.data.Database

class NewsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        initList()
    }

    private fun initList(){
        rv_news.setHasFixedSize(true)

        val layoutManager = GridLayoutManager( this, 2 )
        rv_news.layoutManager = layoutManager

        rv_news.adapter = NewsAdapter(this, Database.getNews())
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.news, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        /*
         * Foi deixado aqui dentro somente o necessário para
         * fechar o menu gaveta quando algum item for acionado.
         * */
        drawer_layout.closeDrawer( GravityCompat.START )
        return false /* Para não mudar o item selecionado em menu gaveta */
    }
}
