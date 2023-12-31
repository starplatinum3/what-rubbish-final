//package com.example.whatrubbish.activity
//import android.animation.ValueAnimator
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.TextView
//import com.bullfrog.particle.IParticleManager
//import com.bullfrog.particle.Particles
//import com.bullfrog.particle.animation.ParticleAnimation
//import com.bullfrog.particle.particle.configuration.Shape
//import com.bullfrog.particle.particle.configuration.Rotation
//import com.bullfrog.particle.path.*
//import com.example.whatrubbish.R
//import kotlin.math.*
//import kotlin.random.Random
//
//class Rotate3dActivityKt : AppCompatActivity() {
//
//    private lateinit var button: TextView
//
//    private lateinit var resetButton: Button
//
//    private lateinit var container: ViewGroup
//
//    private var particleManager: IParticleManager? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        button = findViewById(R.id.button)
//        resetButton = findViewById(R.id.reset_button)
//        container = findViewById(R.id.container)
//
//        particleManager = Particles.with(this, container)
//
//        button.setOnClickListener {
//            particleManager!!.colorFromView(button)
//                .particleNum(500)
//                .anchor(it)
//                .shape(Shape.CIRCLE, Shape.HOLLOW_RECTANGLE)
//                .radius(8, 14)
//                .strokeWidth(8f)
//                .size(40, 40)
//                .rotation(
//                    Rotation(
//                        600
//                    )
//                )
//                .bitmap(R.drawable.ic_thumbs_up)
//                .anim(ParticleAnimation.with({
//                    createAnimator()
//                }, {
//                    createPathGenerator()
//                }))
//            particleManager!!.start()
//            button.visibility = View.GONE
//        }
//
//        resetButton.setOnClickListener {
//            button.visibility = View.VISIBLE
//            particleManager?.cancel()
//        }
//    }
//
//    private fun createPathGenerator(): IPathGenerator {
//        return object : LinearPathGenerator() {
//            val cos = Random.nextDouble(-1.0, 1.0)
//            val sin = Random.nextDouble(-1.0, 1.0)
//
//            override fun getCurrentCoord(
//                progress: Float,
//                duration: Long,
//                outCoord: IntArray
//            ): Unit {
//                val originalX = distance * progress
//                val originalY = 100 * sin(originalX / 50)
//                val x = originalX * cos - originalY * sin
//                val y = originalX * sin + originalY * cos
//                outCoord[0] = (0.01 * x * originalY).toInt()
//                outCoord[1] = -(0.0001 * y.pow(2) * originalX).toInt()
//            }
//        }
//    }
//
//    private fun createAnimator(): ValueAnimator {
//        val animator = ValueAnimator.ofInt(0, 1)
//        animator.repeatCount = -1
//        animator.repeatMode = ValueAnimator.REVERSE
//        animator.duration = 4000L
//        return animator
//    }
//}