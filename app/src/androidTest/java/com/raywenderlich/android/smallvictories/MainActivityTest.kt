/*
 *
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.raywenderlich.android.smallvictories


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.widget.EditText
import android.widget.TextView
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Rule
import org.junit.Test

@LargeTest
class MainActivityTest {

  @Rule
  @JvmField
  var rule = ActivityTestRule(MainActivity::class.java)

  @Test
  fun tappingOnTitleOpensEditDialog() {
    onView(withId(R.id.textVictoryTitle))
        .perform(click())

    onView(withId(R.id.alertTitle))
        .check(matches(isDisplayed()))

    onView(withId(android.R.id.button2))
        .perform(click())
  }

  @Test
  fun editingDialogUpdatesTitle() {
    onView(withId(R.id.textVictoryTitle))
        .perform(click())

    val newTitle = "Made the bed"
    onView(instanceOf(EditText::class.java))
        .perform(clearText())
        .perform(typeText(newTitle))

    onView(withText(R.string.dialog_ok))
        .perform(click())

    onView(allOf(withId(R.id.textVictoryTitle), withText(newTitle)))
        .check(matches(isDisplayed()))
  }

    @Test
    fun onFabClickedUpdatesCount() {
        //arrange
        val previousCountString = rule.activity.findViewById<TextView>(R.id.textVictoryCount).text.toString()
        val previousCount = if (previousCountString.isBlank()) {
            0
        } else {
            previousCountString.toInt()
        }

        //act
        onView(withId(R.id.fab)).perform(click())

        //assert
        onView(allOf(withId(R.id.textVictoryCount), withText((previousCount + 1).toString())))
                .check(matches(isDisplayed()))

    }

    @Test
    fun onFabClickedOnEditingTitleDoesntChangeCount() {

        //arrage
        onView(withId(R.id.fab)).perform(click())

        //act
        onView(withId(R.id.textVictoryTitle)).perform(click())

        val updatedTitle = "Updated victory title"
        onView(instanceOf(EditText::class.java))
                .perform(clearText())
                .perform(typeText(updatedTitle))

        onView(withText(R.string.dialog_ok)).perform(click())

        //assert
        onView(allOf(withId(R.id.textVictoryCount), withText("0")))
                .check(doesNotExist())


    }

    @Test
    fun onUpdatedTitleOnUpdatedCountOnResetClickedChangesTitle() {

        onView(withId(R.id.textVictoryTitle)).perform(click())

        val updatedTitle = "Updated Title"
        onView(instanceOf(EditText::class.java))
                .perform(clearText())
                .perform(typeText(updatedTitle))
        onView(withText(R.string.dialog_ok)).perform(click())

        onView(withId(R.id.fab)).perform(click())
        val countInString = rule.activity.findViewById<TextView>(R.id.textVictoryCount).text.toString()


        onView(withId(R.id.action_reset)).perform(click()) // act

        onView(allOf(withId(R.id.textVictoryTitle), withText(updatedTitle)))
                .check(doesNotExist())

    }

    @Test
    fun onUpdatedTitleOnUpdatedCountOnResetClickedResetsTitle() {

        onView(withId(R.id.textVictoryTitle)).perform(click())

        val updatedTitle = "Updated Title"
        onView(instanceOf(EditText::class.java))
                .perform(clearText())
                .perform(typeText(updatedTitle))
        onView(withText(R.string.dialog_ok)).perform(click())

        onView(withId(R.id.fab)).perform(click())
        val countInString = rule.activity.findViewById<TextView>(R.id.textVictoryCount).text.toString()


        onView(withId(R.id.action_reset)).perform(click()) // act

        onView(allOf(withId(R.id.textVictoryTitle), withText("Victory title")))
                .check(matches(isDisplayed()))

    }


    @Test
    fun onUpdatedTitleOnUpdatedCountOnResetClickedChangesCount() {

        onView(withId(R.id.textVictoryTitle)).perform(click())

        val updatedTitle = "Updated Title"
        onView(instanceOf(EditText::class.java))
                .perform(clearText())
                .perform(typeText(updatedTitle))
        onView(withText(R.string.dialog_ok)).perform(click())

        onView(withId(R.id.fab)).perform(click())
        val countInString = rule.activity.findViewById<TextView>(R.id.textVictoryCount).text.toString()


        onView(withId(R.id.action_reset)).perform(click()) // act

        onView(allOf(withId(R.id.textVictoryCount), withText(countInString)))
                .check(doesNotExist()) //assert

    }

    @Test
    fun onUpdatedTitleOnUpdatedCountOnResetClickedResetsCount() {

        onView(withId(R.id.textVictoryTitle)).perform(click())

        val updatedTitle = "Updated Title"
        onView(instanceOf(EditText::class.java))
                .perform(clearText())
                .perform(typeText(updatedTitle))
        onView(withText(R.string.dialog_ok)).perform(click())

        onView(withId(R.id.fab)).perform(click())
        val countInString = rule.activity.findViewById<TextView>(R.id.textVictoryCount).text.toString()


        onView(withId(R.id.action_reset)).perform(click()) // act

        onView(allOf(withId(R.id.textVictoryCount), withText("0")))
                .check(matches(isDisplayed())) //assert

    }
}
