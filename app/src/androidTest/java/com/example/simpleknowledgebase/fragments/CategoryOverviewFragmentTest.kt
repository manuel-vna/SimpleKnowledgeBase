package com.example.simpleknowledgebase.fragments



import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.simpleknowledgebase.R
import com.example.simpleknowledgebase.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.EnumSet.allOf


@RunWith(AndroidJUnit4::class)
class CategoryOverviewFragmentTest {

    @get: Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun `display_category_recycler_view`() {

        var categoryClickCheck: String = "club"
        
        onView(withId(R.id.keyword_btn_categoryOverview)).perform(ViewActions.click())

        // probably there are be better options to click on a recyclerView row without a text string as input
        onView(withText(categoryClickCheck)).perform(click());

    }


}