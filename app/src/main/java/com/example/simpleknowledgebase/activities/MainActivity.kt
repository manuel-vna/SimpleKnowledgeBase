package com.example.simpleknowledgebase.activities



import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.simpleknowledgebase.R
import com.example.simpleknowledgebase.databinding.ActivityMainBinding
import com.example.simpleknowledgebase.fragments.ExportDatabaseDialogFragment
import com.example.simpleknowledgebase.utils.ImportDatabase
import com.example.simpleknowledgebase.viewmodels.MainActivityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    //variable that refers to the class: ImportDatabase
    lateinit var observerImportDatabase : ImportDatabase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mainActivityViewModel =
            ViewModelProvider(this).get(MainActivityViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view) // NEW: Bottom Navigation Bar
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_categoryOverviewFragment, R.id.nav_advanced_search, R.id.nav_add_entry
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        bottomNavigationView.setupWithNavController(navController)  // NEW: Bottom Navigation Bar

        setNavigationDrawerHeader()

    }


    private fun setNavigationDrawerHeader(){

        var navigationView: NavigationView= binding.navView
        var headerView: View = navigationView.getHeaderView(0)
        var navigationDrawerEntriesTotal: TextView = headerView.findViewById(R.id.nav_header_title)

        mainActivityViewModel.observeTotalRowNumberLiveData().observe(this,object: Observer<Int> {
            override fun onChanged(rowCount: Int) {
                navigationDrawerEntriesTotal.setText("$rowCount entries")
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.action_settings_Export -> {
            ExportDatabaseDialogFragment.newInstance().show(supportFragmentManager, ExportDatabaseDialogFragment.TAG)
            true
        }
        R.id.action_settings_Import -> {
            findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_to_importDatabaseDialogFragment)
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



    //the context is passed from the caller in the class 'ImportDatabase'
    fun importFinishedMessage(context: Context,
                              importSuccess: Boolean,
                              lineCountSuccess: Int,
                              lineCountError: Int ){

        if (importSuccess) {
            try {


                Toast.makeText(context,
                    "Imported Lines: $lineCountSuccess \n" +
                        "Lines with Errors: $lineCountError",
                        Toast.LENGTH_LONG).show()


                //method1
                //val navHostFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
                //navHostFragment?.childFragmentManager?.fragments?.get(0)
                //Log.i("Debug_A","abc")
                //findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_nav_home_to_importDatabaseDialogFragment)


                //method2
                //val navController: NavController = Navigation.findNavController(this,R.id.nav_host_fragment_content_main)
                //Log.i("Debug_A", "abc")
                //navController.navigate(R.id.action_nav_home_to_nav_add_entry)
                //navHostFragment.findNavController().navigate(R.id.action_to_updateDeleteEntryFragment)



            }
            catch(e:Exception){
                Log.i("Debug_A", "Exception: $e")
            }

        }
        else {
            Log.i("Debug_A",R.string.importErrorMessage.toString())
        }
    }


}