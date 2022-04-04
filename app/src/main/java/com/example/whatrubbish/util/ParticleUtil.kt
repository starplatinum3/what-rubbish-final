package com.example.whatrubbish.util

import android.animation.ValueAnimator
import android.util.Log
import android.view.View
import com.bullfrog.particle.IParticleManager
//import com.bullfrog.particle.R
import com.bullfrog.particle.animation.ParticleAnimation
import com.bullfrog.particle.particle.configuration.Rotation
import com.bullfrog.particle.particle.configuration.Shape
import com.bullfrog.particle.path.IPathGenerator
import com.bullfrog.particle.path.LinearPathGenerator
import com.example.whatrubbish.R
import kotlin.math.pow
import kotlin.math.sin
import kotlin.random.Random

class ParticleUtil {
    companion object {
        fun drawParticle(particleManager: IParticleManager?, view: View) {
            Log.i("particleManager", "drawParticle: $particleManager")
            particleManager!!.colorFromView(view)
                .particleNum(500)
                .anchor(view)
                .shape(Shape.CIRCLE, Shape.HOLLOW_RECTANGLE)
                .radius(8, 14)
                .strokeWidth(8f)
                .size(40, 40)
                .rotation(
                    Rotation(
                        600
                    )
                )
//       ic_thumbs_up
                .bitmap(R.drawable.ic_thumbs_up)
                .anim(ParticleAnimation.with({
                    createAnimator()
                }, {
                    createPathGenerator()
                }))
            particleManager!!.start()
        }

//        估计版本问题？
        private fun createPathGenerator(): IPathGenerator {
            return object : LinearPathGenerator() {
                val cos = Random.nextDouble(-1.0, 1.0)
                val sin = Random.nextDouble(-1.0, 1.0)

                override fun getCurrentCoord(
                    progress: Float,
                    duration: Long,
                    outCoord: IntArray
                ): Unit {
                    val originalX = distance * progress
                    val originalY = 100 * sin(originalX / 50)
                    val x = originalX * cos - originalY * sin
                    val y = originalX * sin + originalY * cos
                    outCoord[0] = (0.01 * x * originalY).toInt()
                    outCoord[1] = -(0.0001 * y.pow(2) * originalX).toInt()
                }
            }
        }

        private fun createAnimator(): ValueAnimator {
            val animator = ValueAnimator.ofInt(0, 1)
            animator.repeatCount = -1
            animator.repeatMode = ValueAnimator.REVERSE
            animator.duration = 4000L
            return animator
        }
    }
}