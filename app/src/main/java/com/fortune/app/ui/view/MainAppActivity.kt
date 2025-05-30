package com.fortune.app.ui.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.MainActivity
import com.fortune.app.R
import com.fortune.app.data.secure.TokenManager
import com.fortune.app.domain.model.bank_data.CardModel
import com.fortune.app.domain.model.user.UProfileModel
import com.fortune.app.domain.state.AccountBalanceState
import com.fortune.app.domain.state.AccountState
import com.fortune.app.domain.state.CardState
import com.fortune.app.domain.state.FastContactsState
import com.fortune.app.domain.state.ProfileImageState
import com.fortune.app.domain.state.UProfileState
import com.fortune.app.ui.adapters.cards.CardAdapter
import com.fortune.app.ui.adapters.cards.CardItem
import com.fortune.app.ui.adapters.fastContacts.FastContactAdapter
import com.fortune.app.ui.adapters.fastContacts.FastContactItem
import com.fortune.app.ui.dialogs.AddMoney_Dialog
import com.fortune.app.ui.dialogs.SuccessOrFail_Dialog
import com.fortune.app.ui.view.app.AccountActivity
import com.fortune.app.ui.view.app.BizumActivity
import com.fortune.app.ui.view.app.ConfigActivity
import com.fortune.app.ui.view.app.EditProfileActivity
import com.fortune.app.ui.view.app.SecurityActivity
import com.fortune.app.ui.viewmodel.bank_data.Account_ViewModel
import com.fortune.app.ui.viewmodel.user.User_ViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainAppActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var loadingDialog: AlertDialog
    private var currentAppAmount = 0.0
    private val accountViewModel: Account_ViewModel by viewModels()


    override fun onResume() {
        super.onResume()
        loadUserViewData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_app)
        adjustScreenInsets()

        loadingDialog = AlertDialog.Builder(this@MainAppActivity)
            .setView(R.layout.dialog_loading)
            .create()
        loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialog.setCancelable(false)

        findViewById<ImageView>(R.id.balance_info).setOnClickListener {
            it.performLongClick() // Simulate long click
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {override fun handleOnBackPressed() {}})

        findViewById<ImageView>(R.id.open_profile).setOnClickListener{
            val openProfile = Intent(this@MainAppActivity, EditProfileActivity::class.java)
            startActivity(openProfile)
        }

        slideMenuConfiguration()
        manageBottomNavigation()
        loadUserViewData()
    }

    private fun loadUserViewData() {
        loadingDialog.show()

        generalActionListeners()

        setProfile()
        setAccountBalance()
        setCards()
        setFastContacts()
        loadingDialog.dismiss()
    }

    private fun generalActionListeners() {
        findViewById<TextView>(R.id.account_total_balance).setOnClickListener {
            val openAccountDetails = Intent(this@MainAppActivity, AccountActivity::class.java)
            startActivity(openAccountDetails)
        }

        findViewById<ImageButton>(R.id.add_money).setOnClickListener {
            AddMoney_Dialog{ balanceAdded, newAmount ->
                if (balanceAdded) {
                    SuccessOrFail_Dialog(false, "Se han añadido ${newAmount} correctamente.").show(supportFragmentManager, "Founds added")
                    loadUserViewData()
                } else {
                    SuccessOrFail_Dialog(true, "Ha ocurrido un error, el importe ${newAmount} no ha sido ingresado.").show(supportFragmentManager, "Founds added")
                }

            }.show(supportFragmentManager, "Add money dialog")
        }

        findViewById<ImageButton>(R.id.make_bizum).setOnClickListener {
            getAccountBalance { balance ->
                currentAppAmount = balance
            }

            val openBizum = Intent(this, BizumActivity::class.java)
            openBizum.putExtra("currentAmount", currentAppAmount)
            startActivity(openBizum)
        }

        findViewById<ImageButton>(R.id.security).setOnClickListener {
            val openSecurity = Intent(this@MainAppActivity, SecurityActivity::class.java)
            startActivity(openSecurity)
        }

        findViewById<ImageButton>(R.id.config).setOnClickListener {
            val openConfig = Intent(this, ConfigActivity::class.java)
            startActivity(openConfig)
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
                            CardItem(cardModel.uuid, cardModel.cardType, cardModel.cardNumber.takeLast(4).toInt(), cardModel.balance)
                        )
                    }

                    findViewById<RecyclerView>(R.id.card_recycler_view).adapter = CardAdapter(this, cardItems)
                }

                is CardState.Error -> {
                    // Show error dialog
                }
            }
        }

        userViewModel.getCards()
    }

    private fun setAccountBalance() {
        getAccountBalance { balance ->
            findViewById<TextView>(R.id.account_total_balance).text = "${balance} €"
            currentAppAmount = balance
        }
    }

    private fun getAccountBalance(callback: (Double) -> Unit) {
        accountViewModel.accountBalanceState.observe(this) { accountBalanceState ->
            when(accountBalanceState) {
                is AccountBalanceState.Success -> {
                    callback(accountBalanceState.accountBalanceResponse.accountBalance)
                }

                is AccountBalanceState.Error -> {
                    callback(0.0)
                }
            }
        }

        accountViewModel.getAccountBalance()
    }

    private fun setProfile() {
        val userViewModel: User_ViewModel by viewModels()

        getProfile {
            findViewById<TextView>(R.id.client_name).text = it?.name
            val base64String = it?.pfp
            if (!base64String.isNullOrEmpty()) {
                val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                findViewById<ImageView>(R.id.open_profile).setImageBitmap(bitmap)
            } else {
                findViewById<ImageView>(R.id.open_profile)
                    .setImageResource(R.drawable.profile_default_profile_icon)
            }
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

    private fun setFastContacts() {
        val accountViewmodel: Account_ViewModel by viewModels()
        accountViewmodel.fastContacts.observe(this) { fastContactState ->
            when(fastContactState) {
                is FastContactsState.Success -> {
                    val contactListItem: MutableList<FastContactItem> = mutableListOf()
                    fastContactState.contactResponseList.forEach { item ->
                        contactListItem.add(
                            FastContactItem(
                                item.pfp,
                                item.name,
                                item.to_id
                            )
                        )
                    }

                    findViewById<RecyclerView>(R.id.fast_contacts_rview).adapter = FastContactAdapter(contactListItem)
                }

                is FastContactsState.Error -> {}
            }
        }

        accountViewmodel.getFastContacts()
    }

    private fun slideMenuConfiguration() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.main_drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        val navHeader = LayoutInflater.from(this).inflate(R.layout.nav_header_layout, null)
        val userViewmodel: User_ViewModel by viewModels()

        userViewmodel.navProfile.observe(this) { profileState ->
            when(profileState) {
                is UProfileState.Success -> {
                    navHeader.findViewById<TextView>(R.id.nav_view_header_uname).text = profileState.uProfileModel.name
                    val base64String = profileState.uProfileModel.pfp
                    if (!base64String.isNullOrEmpty()) {
                        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                        navHeader.findViewById<ImageView>(R.id.nav_view_header_upicture).setImageBitmap(bitmap)
                    } else {
                        navHeader.findViewById<ImageView>(R.id.nav_view_header_upicture)
                            .setImageResource(R.drawable.profile_default_profile_icon)
                    }
                }

                is UProfileState.Error -> {
                    navHeader.findViewById<ImageView>(R.id.nav_view_header_upicture)
                        .setImageResource(R.drawable.profile_default_profile_icon)
                }
            }
        }

        userViewmodel.getNavProfile()

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
                    val openProfile = Intent(this@MainAppActivity, EditProfileActivity::class.java)
                    startActivity(openProfile)
                    true
                }

                R.id.app_config -> {
                    val openConfig = Intent(this, ConfigActivity::class.java)
                    startActivity(openConfig)
                    true
                }

                R.id.security -> {
                    val openSecurity = Intent(this, SecurityActivity::class.java)
                    startActivity(openSecurity)
                    true
                }
                R.id.logout_lm -> {
                    val openDefault = Intent(this@MainAppActivity, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(openDefault)


                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun manageBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.selectedItemId = R.id.home_bm

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bizum_bm -> {
                    bottomNavigationView.selectedItemId = R.id.home_bm
                    val openBizum = Intent(this@MainAppActivity, BizumActivity::class.java)
                    startActivity(openBizum)
                    true
                }
                R.id.home_bm -> {
                    true
                }
                R.id.security_bm -> {
                    bottomNavigationView.selectedItemId = R.id.home_bm
                    val openSec = Intent(this@MainAppActivity, SecurityActivity::class.java)
                    startActivity(openSec)
                    true
                }
                R.id.logout_bm -> {
                    bottomNavigationView.selectedItemId = R.id.home_bm
                    val openDefault = Intent(this@MainAppActivity, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(openDefault)
                    true
                }
                else -> false
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