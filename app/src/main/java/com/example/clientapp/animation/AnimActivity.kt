package com.example.clientapp.animation

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.clientapp.databinding.ActivityAnimationBinding

class AnimActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAnimationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            ObjectAnimator.ofFloat(square1,"translationY", 200f).apply {
                repeatCount = ObjectAnimator.INFINITE
                interpolator = AccelerateInterpolator()
                repeatMode = ValueAnimator.REVERSE
                duration = 1000
                start()
            }
            ObjectAnimator.ofFloat(square2,"translationY", 400f).apply {
                repeatCount = ObjectAnimator.INFINITE
                interpolator = AccelerateDecelerateInterpolator()
                repeatMode = ValueAnimator.REVERSE
                duration = 700
                start()
            }
            ObjectAnimator.ofFloat(square3,"translationY", 600f).apply {
                repeatCount = ObjectAnimator.INFINITE
                interpolator = LinearInterpolator()
                repeatMode = ValueAnimator.REVERSE
                duration = 500
                start()
            }
        }
    }
}