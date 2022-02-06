package com.m77777_888.myapplication.screens.game_fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.m77777_888.myapplication.R
import kotlinx.android.synthetic.main.fragment_game.view.*

const val TAG = "TTT"

class GameFragment : Fragment()  {

    private lateinit var animator: ObjectAnimator
    private lateinit var circleImageView: ImageView
    private var currentCircleValue: Long = 0
    private var totalCircleValue: Long = 0
    private var rotation = 0f
    private var circleIsRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.apply {
            currentCircleValue = getLong("currentCircleValue")
            totalCircleValue = getLong("totalCircleValue")
            rotation = getFloat("circleRotation")
            circleIsRunning = getBoolean("circleIsRunning")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        circleImageView = view.circleImageView
        circleImageView.rotation = rotation

        view.nowScoreTextView.text = currentCircleValue.toString()
        view.inTotalScoreTextView.text = totalCircleValue.toString()

        animator = ObjectAnimator.ofFloat(circleImageView, View.ROTATION, rotation, 360000f)

        animator.apply {
            duration = 1000
            repeatCount = ObjectAnimator.INFINITE
        }

        if (circleIsRunning) animator.start()

        view.goButton.setOnClickListener {
            start()
        }

        view.stopButton.setOnClickListener {
            stop(view)
        }

        view.resetButton.setOnClickListener {
            currentCircleValue = 0
            view.nowScoreTextView.text = currentCircleValue.toString()

            totalCircleValue = 0
            view.inTotalScoreTextView.text = totalCircleValue.toString()
        }

        view.quiteButton.setOnClickListener {
            requireActivity().finish()
        }

        return view
    }

    private fun start() {
        if (animator.isPaused) {
            animator.resume()
        } else if (!animator.isRunning) {
            animator.start()
        }
        circleIsRunning = true
    }

    private fun stop(view: View) {
        if (!animator.isRunning || animator.isPaused) return

        animator.pause()
        circleIsRunning = false

        val valueInDegrees = (animator.animatedValue as Float).toInt() % 360

        currentCircleValue = when (valueInDegrees) {
            in 346..360, in 0..15 -> 60000
            in 16..45 -> 50000
            in 46..75 -> 40000
            in 76..105 -> 30000
            in 106..135 -> 20000
            in 136..165 -> 40000
            in 166..195 -> 60000
            in 196..225 -> 50000
            in 226..255 -> 40000
            in 256..285 -> 30000
            in 286..315 -> 20000
            in 316..345 -> 40000
            else -> 0
        }

        totalCircleValue += currentCircleValue

        view.nowScoreTextView.text = currentCircleValue.toString()
        view.inTotalScoreTextView.text = totalCircleValue.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.apply {
            putLong("currentCircleValue", currentCircleValue)
            putLong("totalCircleValue", totalCircleValue)
            putFloat("circleRotation", circleImageView.rotation)
            putBoolean("circleIsRunning", circleIsRunning)
        }

        super.onSaveInstanceState(outState)
    }


//    companion object {
//        val circleValues = listOf<Long>(60000, 50000, 40000, 30000, 20000,40000,60000,50000, 40000,
//                                        30000, 20000, 40000)
//    }

}