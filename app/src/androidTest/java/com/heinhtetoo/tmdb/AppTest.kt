package com.heinhtetoo.tmdb

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.heinhtetoo.tmdb.ui.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class AppTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun completePath() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.rv_upcoming)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, MyViewAction.clickChildViewWithId(R.id.btn_fav)
            )
        )

        onView(withId(R.id.rv_upcoming)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, MyViewAction.clickChildViewWithId(R.id.cl_contents)
            )
        )

        onView(withId(R.id.btn_fav)).perform(click())
    }
}