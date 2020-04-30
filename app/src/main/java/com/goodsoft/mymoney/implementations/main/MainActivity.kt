package com.goodsoft.mymoney.implementations.main

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.goodsoft.mymoney.MainActivityBinding
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.core.findBehavior
import com.goodsoft.mymoney.widgets.BackdropBehavior
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: MainActivityBinding
    private lateinit var backdropBehavior: BackdropBehavior

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.backdropExpandIcon.setOnClickListener {
            backdropBehavior.close()
        }

        //init backdrop
        backdropBehavior = binding.frontLayout.findBehavior()
        backdropBehavior.attachBackLayout(R.id.backLayout)
        backdropBehavior.addOnDropListener(object : BackdropBehavior.OnDropListener {
            override fun onDrop(dropState: BackdropBehavior.DropState, fromUser: Boolean) {
                viewModel.backdropExpandIconIsVisible.set(dropState == BackdropBehavior.DropState.OPEN)
            }
        })

        // init navigation
        navigationView.setNavigationItemSelectedListener { item ->
            selectMenuItem(item)
        }
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.menuMain)
            selectMenuItem(navigationView.checkedItem)
        }
    }

    private fun selectMenuItem(menuItem: MenuItem?): Boolean {
        backdropBehavior.close()
        when (menuItem?.itemId) {
            R.id.menuMain -> findNavController(R.id.nav_host_fragment).navigate(R.id.mainFragment)
            R.id.menuCategories -> findNavController(R.id.nav_host_fragment).navigate(R.id.categoriesFragment)
            R.id.menuAccounts -> findNavController(R.id.nav_host_fragment).navigate(R.id.accountsFragment)
            R.id.menuSmsParser -> findNavController(R.id.nav_host_fragment).navigate(R.id.smsParsersFragment)
            R.id.menuHistory -> findNavController(R.id.nav_host_fragment).navigate(R.id.categoriesFragment)
            R.id.menuSettings -> findNavController(R.id.nav_host_fragment).navigate(R.id.categoriesFragment)
        }
        viewModel.backdropTitle.set(menuItem?.title?.toString())
        return true
    }

    override fun onBackPressed() {
        if (!backdropBehavior.close()) {
            finish()
        }
    }

}