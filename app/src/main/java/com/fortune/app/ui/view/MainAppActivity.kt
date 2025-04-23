package com.fortune.app.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.MainActivity
import com.fortune.app.R
import com.fortune.app.domain.model.bank_data.CardModel
import com.fortune.app.domain.model.user.UProfileModel
import com.fortune.app.domain.state.AccountState
import com.fortune.app.domain.state.CardState
import com.fortune.app.domain.state.UProfileState
import com.fortune.app.ui.adapters.cards.CardAdapter
import com.fortune.app.ui.adapters.cards.CardItem
import com.fortune.app.ui.viewmodel.user.User_ViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainAppActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_app)
        adjustScreenInsets()

        val userViewModel: User_ViewModel by viewModels()

        slideMenuConfiguration()
        loadUserViewData()
    }

    private fun loadUserViewData() {

        generalActionListeners()

        setProfile()
        setAccount()
        setCards()
    }

    private fun generalActionListeners() {
        findViewById<ImageButton>(R.id.add_money).setOnClickListener {
            Toast.makeText(this, "Add money", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.make_bizum).setOnClickListener {
            Toast.makeText(this, "Bizum", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.more).setOnClickListener {
            Toast.makeText(this, "More", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.config).setOnClickListener {
            Toast.makeText(this, "Config", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setCards() {
        val userViewModel: User_ViewModel by viewModels()

        userViewModel.cards.observe(this) { cardState ->
            when(cardState) {
                is CardState.Success -> {
                    val cardItems: MutableList<CardItem> = mutableListOf()
                    cardState.cards.forEach { cardModel: CardModel ->
                        cardItems.add(
                            CardItem(cardModel.cardType, cardModel.cardNumber.takeLast(4).toInt(), cardModel.balance)
                        )
                    }

                    findViewById<RecyclerView>(R.id.card_recycler_view).adapter = CardAdapter(cardItems)
                }

                is CardState.Error -> {

                }
            }
        }

        userViewModel.getCards()
    }

    private fun setAccount() {
        val userViewModel: User_ViewModel by viewModels()

        userViewModel.account.observe(this) { accountState ->
            when(accountState) {
                is AccountState.Success -> {
                    findViewById<TextView>(R.id.account_total_balance).text = "${accountState.accountModel.totalBalance} €"
                }

                is AccountState.Error -> {
                    val openMain = Intent(this@MainAppActivity, MainActivity::class.java)
                    startActivity(openMain)
                    finish()
                }
            }
        }

        userViewModel.getAccount()
    }

    private fun setProfile() {
        val userViewModel: User_ViewModel by viewModels()

        getProfile {
            findViewById<TextView>(R.id.client_name).text =
                it?.name
        }
        userViewModel.getProfile()
    }

    private fun getProfile(callback: (UProfileModel?) -> Unit) {
        val userViewModel: User_ViewModel by viewModels()

        userViewModel.profile.observe(this) { userProfileState ->
            when (userProfileState) {
                is UProfileState.Success -> {
                    callback(userProfileState.uProfileModel)
                }

                is UProfileState.Error -> {
                    val openMain = Intent(this@MainAppActivity, MainActivity::class.java)
                    startActivity(openMain)
                    finish()
                    callback(null)
                }
            }
        }

        userViewModel.getProfile()
    }

    private fun slideMenuConfiguration() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.main_drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        val navHeader = LayoutInflater.from(this).inflate(R.layout.nav_header_layout, null)

        getProfile { profileModel ->
            navHeader.findViewById<TextView>(R.id.nav_view_header_uname).text = profileModel?.name
        }
//      navHeader.findViewById<TextView>(R.id.nav_view_header_upicture).text = getProfile().pfp <- Default picture temporally

        navView.addHeaderView(navHeader)

        toggle = ActionBarDrawerToggle(
            this, drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)

        findViewById<ImageButton>(R.id.slide_menu_button).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        manageMenuItemSelection(navView)
    }

    private fun manageMenuItemSelection(navView: NavigationView?) {
        navView?.setNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.personal_data -> {
                    Toast.makeText(this, "Personal", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.app_config -> {
                    Toast.makeText(this, "Personal1", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.security -> {
                    Toast.makeText(this, "Personal2", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item)) true
        else super.onOptionsItemSelected(item)
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_drawer_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}