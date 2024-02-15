package de.domjos.gift_app.services

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BibleAPIServiceTest {
    private lateinit var api: BibleAPIService

    @Before
    fun useAppContext() {
        api = BibleAPIService()
    }

    @Test
    fun testGetBible() {
        val bibleSummaryResponse = api.getBibles(false)
        assertNotNull(bibleSummaryResponse)
        val bibleSummary = bibleSummaryResponse!!.data[0]
        val bibleResponse = api.getBible(bibleSummary.id)
        assertNotNull(bibleResponse)
    }

}