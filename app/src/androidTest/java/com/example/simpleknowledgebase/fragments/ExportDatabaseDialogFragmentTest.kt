package com.example.simpleknowledgebase.fragments


import android.app.PendingIntent.getActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.simpleknowledgebase.R
import com.example.simpleknowledgebase.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExportDatabaseDialogFragmentTest {

    @get: Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun `search_for_entries_by_keyword_and_display_hits_in_recycler_view`() {

        //open right sided menu in top action bar
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        // click on the menu item 'Settings'
        onView(withText("Export")).perform(click());

        // cancel the pop up export window
        onView(withId(R.id.export_btn_cancel)).perform(ViewActions.click())

    }



}