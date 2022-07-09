package com.example.simpleknowledgebase.fragments

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.simpleknowledgebase.R
import com.example.simpleknowledgebase.activities.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UpdateDeleteEntryFragmentTest{

    @get: Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun `search_for_entries_by_keyword_and_display_hits_in_recycler_view`(){

        //testing: delete entry
        Espresso.onView(ViewMatchers.withId(R.id.keyword_et_search))
            .perform(ViewActions.typeText("oracle"))
        Espresso.onView(ViewMatchers.withId(R.id.keyword_btn_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.keyword_et_search))
            .perform(ViewActions.clearText())
        //click on the tile within the recycler view
        Espresso.onView(ViewMatchers.withId(R.id.keyword_rec_view_tv_title)).perform(ViewActions.click())
        //delete the current entry
        Espresso.onView(ViewMatchers.withId(R.id.updateDelete_btn_delete)).perform(ViewActions.click())


        //testing: update entry
        Espresso.onView(ViewMatchers.withId(R.id.keyword_et_search))
            .perform(ViewActions.typeText("oracle"))
        Espresso.onView(ViewMatchers.withId(R.id.keyword_btn_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.keyword_et_search))
            .perform(ViewActions.clearText())
        //click on the tile within the recycler view
        Espresso.onView(ViewMatchers.withId(R.id.keyword_rec_view_tv_title)).perform(ViewActions.click())
        // clear and edit the description of the entry
        Espresso.onView(ViewMatchers.withId(R.id.updateDelete_tv_description))
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText("new description"))
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.updateDelete_btn_update)).perform(ViewActions.click())


    }

}

