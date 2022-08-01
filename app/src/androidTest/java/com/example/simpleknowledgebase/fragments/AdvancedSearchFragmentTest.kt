package com.example.simpleknowledgebase.fragments

import android.view.KeyEvent
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.simpleknowledgebase.R
import com.example.simpleknowledgebase.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AdvancedSearchFragmentTest {

    @get: Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)



    @Test
    fun `search_field_title_via_search_button`(){
        Espresso.onView(ViewMatchers.withId(R.id.keyword_btn_advancedSearch)).perform(ViewActions.click())
        onView(withId(R.id.advancedSearch_rb_ByTextField)).perform(click());
        onView(withId(R.id.advancedSearch_et_Search)).perform(ViewActions.typeText("google"))
        onView(withId(R.id.advancedSearch_btn_Search)).perform(click())
        onView(withId(R.id.advancedSearch_btn_clear)).perform(click())
    }

    @Test
    fun `search_field_title_via_pressing_enter`(){
        onView(ViewMatchers.withId(R.id.keyword_btn_advancedSearch)).perform(ViewActions.click())
        onView(withId(R.id.advancedSearch_rb_ByTextField)).perform(click());
        onView(withId(R.id.advancedSearch_et_Search)).perform(ViewActions.typeText("oracle"))
        onView(withId(R.id.advancedSearch_et_Search)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.advancedSearch_btn_clear)).perform(click())
    }

    @Test
    fun `search_field_title_no_hits_test`(){
        onView(ViewMatchers.withId(R.id.keyword_btn_advancedSearch)).perform(ViewActions.click())
        onView(withId(R.id.advancedSearch_rb_ByTextField)).perform(click());
        onView(withId(R.id.advancedSearch_et_Search)).perform(ViewActions.typeText("awesome"))
        onView(withId(R.id.advancedSearch_et_Search)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.advancedSearch_btn_clear)).perform(click())
    }

    @Test
    fun `search_field_date_no_hits_test`(){
        onView(ViewMatchers.withId(R.id.keyword_btn_advancedSearch)).perform(ViewActions.click())
        onView(withId(R.id.advancedSearch_rb_DateSearch)).perform(click())
        onView(withId(R.id.advancedSearch_btn_Search)).perform(click())
    }



}