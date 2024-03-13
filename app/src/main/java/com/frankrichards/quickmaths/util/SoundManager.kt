package com.frankrichards.quickmaths.util

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import android.media.VolumeShaper
import com.frankrichards.quickmaths.R
import kotlinx.coroutines.delay

class SoundManager(private val context: Context) {

    private val TIMER_45: Int = 15000
    private val TIMER_30: Int = 30000

    private var soundPool: SoundPool = SoundPool.Builder().setMaxStreams(3).build()
    private var click = soundPool.load(context, R.raw.click3, 1)
    private var numberClick = soundPool.load(context, R.raw.click1, 1)
    private var buttonClick = soundPool.load(context, R.raw.click2, 1)
    private var correct = soundPool.load(context, R.raw.correct, 1)
    private var pop = soundPool.load(context, R.raw.pop, 1)

    private var countdownTrack = MediaPlayer.create(context, R.raw.countdown_track)

    private var fadeInConfig = VolumeShaper.Configuration.Builder()
        .setDuration(3000)
        .setCurve(floatArrayOf(0f, 1f), floatArrayOf(0f, 1f))
        .setInterpolatorType(VolumeShaper.Configuration.INTERPOLATOR_TYPE_LINEAR)
        .build()

    private var fadeOutConfig = VolumeShaper.Configuration.Builder()
        .setDuration(1500)
        .setCurve(floatArrayOf(0f, 1f), floatArrayOf(1f, 0f))
        .setInterpolatorType(VolumeShaper.Configuration.INTERPOLATOR_TYPE_LINEAR)
        .build()

    private var shaper = countdownTrack.createVolumeShaper(fadeInConfig)

    private fun SoundPool.play(id: Int){
        play(id, 0.5f, 0.5f, 1, 0, 1f)
    }

    fun click(sfxOn: Boolean = true){
        if(sfxOn) soundPool.play(click)
    }
    fun numberClick(sfxOn: Boolean = true){
        if(sfxOn) soundPool.play(numberClick)
    }

    fun buttonClick(sfxOn: Boolean = true){
        if(sfxOn) soundPool.play(buttonClick)
    }

    fun correct(sfxOn: Boolean = true){
        if(sfxOn) soundPool.play(correct)
    }

    fun pop(sfxOn: Boolean = true){
        if(sfxOn) soundPool.play(pop)
    }

    suspend fun stopCountdownMusic(){
        shaper.close()
        shaper = countdownTrack.createVolumeShaper(fadeOutConfig)
        shaper.apply(VolumeShaper.Operation.PLAY)
        delay(1500)
        countdownTrack.stop()
    }

    fun resetCountdownMusic(){
        if(countdownTrack.isPlaying) {
            countdownTrack.reset()
        }
        countdownTrack = MediaPlayer.create(context, R.raw.countdown_track)
        shaper.close()
        shaper = countdownTrack.createVolumeShaper(fadeInConfig)
    }

    fun startCountdown(timer: Int){
        when(timer){
            45 -> countdownTrack.seekTo(TIMER_45)
            30 -> countdownTrack.seekTo(TIMER_30)
        }
        if(timer < 60){
            shaper.apply(VolumeShaper.Operation.PLAY)
        }
        countdownTrack.start()
    }

}