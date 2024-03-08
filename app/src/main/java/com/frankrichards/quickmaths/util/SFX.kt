package com.frankrichards.quickmaths.util

import android.content.Context
import android.media.MediaPlayer
import android.os.AsyncTask
import com.frankrichards.quickmaths.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

object SFX {

    private var debounced = false

    private suspend fun debounce(){
        debounced = true
        coroutineScope {
            delay(100)
            debounced = false
        }
    }

    private suspend fun playSound(id: Int, context: Context, sfxOn: Boolean){
        if(sfxOn && !debounced) {
            debounce()
            val mp = MediaPlayer.create(context, id)
            mp.setVolume(0.6f, 0.5f)
            mp.start()
        }
    }

    suspend fun click(context: Context, sfxOn: Boolean = true){
        playSound(R.raw.click3, context, sfxOn)
    }
    suspend fun numberClick(context: Context, sfxOn: Boolean = true){
        playSound(R.raw.click1, context, sfxOn)
    }

    suspend fun buttonClick(context: Context, sfxOn: Boolean = true){
        playSound(R.raw.click2, context, sfxOn)
    }

    suspend fun correct(context: Context, sfxOn: Boolean = true){
        playSound(R.raw.correct, context, sfxOn)
    }

    suspend fun pop(context: Context, sfxOn: Boolean = true){
        playSound(R.raw.pop, context, sfxOn)
    }

}