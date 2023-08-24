package com.exeapp.checheritox.utils

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.Button
import androidx.core.content.ContextCompat
import com.exeapp.checheritox.R

class UIUtils {
    companion object {
        fun setRoundedBorders(btn:Button, context:Context, roudedFactor: Float =8f, colorResId:Int=R.color.colorAccent) {
            val gradientDrawable = GradientDrawable()
            gradientDrawable.shape = GradientDrawable.RECTANGLE
            gradientDrawable.cornerRadii = floatArrayOf(roudedFactor,roudedFactor,roudedFactor, roudedFactor,
                roudedFactor,
                roudedFactor,
                roudedFactor,
                roudedFactor
            ) // Ajusta los valores de los radios según el tamaño de los bordes redondeados deseados
            gradientDrawable.setColor(ContextCompat.getColor(context, R.color.colorAccent)) // Ajusta el color del fondo del botón
            btn.setBackground(gradientDrawable)
        }
    }
}