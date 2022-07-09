package com.example.simpleknowledgebase.fragments


import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.clearText
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
class AddEntryFragmentTest {

    @get: Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun `add_entries_to_database`(){

        onView(withId(R.id.keyword_btn_addEntry)).perform(ViewActions.click())

        // first entry
        onView(withId(R.id.addentry_actv_addTitle))
            .perform(ViewActions.typeText("google"))
        onView(withId(R.id.addentry_actv_addCategory))
            .perform(ViewActions.typeText("company"))
        onView(withId(R.id.addentry_actv_addDescription))
            .perform(ViewActions.typeText("a very big company"))
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.addentry_actv_addSource))
            .perform(ViewActions.typeText("abc")) //"https://www.google.de"))
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.addentry_btn_add)).perform(ViewActions.click())

        // back to:  fragment_add_entry
        onView(withId(R.id.keyword_btn_addEntry)).perform(ViewActions.click())

        //second entry
        Espresso.onView(ViewMatchers.withId(R.id.addentry_actv_addTitle))
            .perform(clearText())
            .perform(ViewActions.typeText("oracle"))
        Espresso.onView(ViewMatchers.withId(R.id.addentry_actv_addCategory))
            .perform(clearText())
            .perform(ViewActions.typeText("company"))
        Espresso.onView(ViewMatchers.withId(R.id.addentry_actv_addDescription))
            .perform(clearText())
            .perform(ViewActions.typeText("headquarters moved to Austin,Texas"))
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.addentry_actv_addSource))
            .perform(clearText())
            .perform(ViewActions.typeText("https://www.oracle.com/"))
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.addentry_btn_add)).perform(ViewActions.click())


    }

}