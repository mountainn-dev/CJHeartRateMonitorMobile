package com.san.heartratemonitormobile.view.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
        initBottomNav(account)
    }

    private fun initBottomNav(account: AccountModel) {
        if (account.admin) {
            add(UrgentFragment(account))
            binding.btmNav.inflateMenu(R.menu.menu_admin_bottom_navigation)
        } else {
            add(ReportFragment(account))
            binding.btmNav.inflateMenu(R.menu.menu_worker_bottom_navigation)
        }

        binding.btmNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navWorking -> replaceTo(UrgentFragment(account))
                R.id.navRecord -> replaceTo(ReportFragment(account))
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
}