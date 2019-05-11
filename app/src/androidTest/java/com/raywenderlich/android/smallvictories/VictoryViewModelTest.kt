package com.raywenderlich.android.smallvictories

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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

class VictoryViewModelTest {

  @Rule
  @JvmField
  var instantTaskExecutorRule = InstantTaskExecutorRule()

  private val viewStateObserver: Observer<VictoryUiModel> = mock()
  private val mockVictoryRepository: VictoryRepository = mock()
  private val viewModel = VictoryViewModel()

  @Before
  fun setUpTaskDetailViewModel() {
    viewModel.viewState.observeForever(viewStateObserver)
    viewModel.repository = mockVictoryRepository
  }

  @Test
  fun setVictoryTitleSavesTitle() {
    val title = "New title"
    viewModel.setVictoryTitle(title)

    verify(mockVictoryRepository).setVictoryTitle(title)
  }

  @Test
  fun setVictoryTitleReturnsTitle() {
    val title = "New title"
    viewModel.setVictoryTitle(title)

    verify(viewStateObserver).onChanged(VictoryUiModel.TitleUpdated(title))
  }

  private fun stubVictoryRepositoryGetVictoryTitleAndCount(titleAndCount: Pair<String, Int>) {
    stubVictoryRepositoryGetVictoryTitle(titleAndCount.first)
    stubVictoryRepositoryGetVictoryCount(titleAndCount.second)
    whenever(mockVictoryRepository.getVictoryTitleAndCount())
        .thenReturn(titleAndCount)
  }

  private fun stubVictoryRepositoryGetVictoryTitle(title: String) {
    whenever(mockVictoryRepository.getVictoryTitle())
        .thenReturn(title)
  }

  private fun stubVictoryRepositoryGetVictoryCount(count: Int) {
    whenever(mockVictoryRepository.getVictoryCount())
        .thenReturn(count)
  }
}
