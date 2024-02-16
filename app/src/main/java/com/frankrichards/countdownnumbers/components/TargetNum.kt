package com.frankrichards.countdownnumbers.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.frankrichards.countdownnumbers.ui.theme.targetNum

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TargetNum(
    num: Int,
    modifier: Modifier = Modifier,
    color: Color? = null
) {
    var lastNum by remember {
        mutableStateOf(num)
    }

    SideEffect {
        lastNum = num
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        val numString = String.format("%03d", num)
        val lastNumString = String.format("%03d", lastNum)

        for(i in numString.indices) {
            val lastChar = lastNumString.getOrNull(i)
            val newChar = numString[i]
            val char = if(lastChar == newChar){
                lastNumString[i]
            }else{
                numString[i]
            }

            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    slideInVertically { it } togetherWith slideOutVertically { -it }
                },
                label = "Target digit $i"
            ) {
                if(color == null) {
                    Text(
                        text = it.toString(),
                        style = MaterialTheme.typography.targetNum,
                        softWrap = false
                    )
                }else{
                    Text(
                        text = it.toString(),
                        style = MaterialTheme.typography.targetNum,
                        softWrap = false,
                        color = color
                    )
                }
                
            }
        }
    }

}

@Preview
@Composable
fun TargetNum_Preview() {
    TargetNum(
        1
    )
}