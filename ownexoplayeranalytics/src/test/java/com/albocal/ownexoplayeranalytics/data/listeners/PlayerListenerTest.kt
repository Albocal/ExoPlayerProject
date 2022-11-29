package com.albocal.ownexoplayeranalytics.data.listeners

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.albocal.ownexoplayeranalytics.data.events.fake.FakeOnEventPlayerStateChangedSuccess
import com.google.android.exoplayer2.Player
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock

/** TODO Implement Test for PlayerListener **/
//@RunWith(RobolectricTestRunner::class)
class PlayerListenerTest {

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    var fakeEvent0 = FakeOnEventPlayerStateChangedSuccess(0)
    var fakeEvent1 = FakeOnEventPlayerStateChangedSuccess(1)
    var fakeEvent2 = FakeOnEventPlayerStateChangedSuccess(1)

    private lateinit var listener: PlayerListener

    @Mock
    private lateinit var player: Player

    @Before
    fun setUp() {
        listener = PlayerListener(listOf(fakeEvent0, fakeEvent1, fakeEvent2))
    }

    /*@Test
    fun  `Player Listener 1 event Test`() {
        val flagsBuilder = FlagSet.Builder()
        flagsBuilder.add(fakeEvent0.eventId)
        listener.onEvents(player, Player.Events(flagsBuilder.build()))
    }*/

}