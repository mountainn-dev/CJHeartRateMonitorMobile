package com.san.heartratemonitormobile.view.screen

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.san.heartratemonitormobile.BuildConfig
import com.san.heartratemonitormobile.R
import com.san.heartratemonitormobile.databinding.ActivityHomeBinding
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.domain.utils.Const

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val account = intent.getSerializableExtra(Const.TAG_ACCOUNT) as AccountModel
        val userId = intent.getStringExtra(Const.TAG_ID) ?: ""
        initBottomNav(account, userId)
        saveToken(this, account.idToken)
    }

    private fun initBottomNav(
        account: AccountModel, userId: String
    ) {
        if (account.admin) {
            add(UrgentFragment(userId))
            binding.btmNav.inflateMenu(R.menu.menu_admin_bottom_navigation)
        } else {
            add(ReportFragment(account, userId))
            binding.btmNav.inflateMenu(R.menu.menu_worker_bottom_navigation)
        }

        binding.btmNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navWorking -> replaceTo(UrgentFragment(userId))
                R.id.navReport -> replaceTo(ReportFragment(account, userId))
                R.id.navUser -> replaceTo(UserFragment(account))
            }

            return@setOnItemSelectedListener true
        }
    }

    private fun add(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(binding.flHome.id, fragment)
        transaction.commit()
    }

    private fun replaceTo(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.flHome.id, fragment)
        transaction.commit()
    }

    private fun saveToken(context: Context, token: String) {
        val preference = context.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)

        preference.edit().putString(Const.TAG_ID_TOKEN, token).apply()
    }
}