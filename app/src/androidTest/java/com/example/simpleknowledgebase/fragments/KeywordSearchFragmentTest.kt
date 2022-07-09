package com.example.simpleknowledgebase.fragments

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.simpleknowledgebase.R
import com.example.simpleknowledgebase.activities.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KeywordSearchFragmentTest {

    @get: Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun `search_for_entries_by_keyword_and_display_hits_in_recycler_view`(){

        onView(withId(R.id.keyword_et_search)).perform(typeText("google"))
        onView(withId(R.id.keyword_btn_search)).perform(click())

        onView(withId(R.id.keyword_et_search)).perform(clearText())

        onView(withId(R.id.keyword_et_search)).perform(typeText("oracle"))
        onView(withId(R.id.keyword_btn_search)).perform(click())

    }

}