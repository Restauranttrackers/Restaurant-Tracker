package com.bignerdranch.android.maptest


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import pl.droidsonroids.gif.GifImageView
import models.ProfileController
import android.widget.Button
import models.Rewards

/**
 * A simple [Fragment] subclass.
 * Use the [profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class profile : Fragment() {
    private var maxScore: Int = 20
    private var score: Int = 0
    private var picture = R.drawable.ic_baseline_person_24 // TODO From Json/db = NULL
    private var border = R.drawable.simple_border_animation // TODO From Json/db = NULL
    private var rewardContainer = ArrayList<Rewards>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    /**
     * Initializes all drawable objects
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = view.findViewById<ProgressBar>(R.id.pbscore)
        val user = ProfileController(picture,border)
        this.score = user.getScore()
        val rewards = Rewards(progress = this.score)

        rewardContainer = rewards.createRewards(rewardContainer)
        // Routine to update / Add reward to user
        rewardContainer.forEach{
            val name: String = it.name
            val tracker: String = it.tracker
            val progress: Int = it.progress
            val requirement: Int = it.requirement
            val complete: Boolean = it.complete

            if( name == "reward1"){
                val pb = view.findViewById<ProgressBar>(R.id.pbreward1)
                view.findViewById<TextView>(R.id.reward1).text = tracker
                pb.max = requirement
                pb.progress = progress
                if (complete)
                {
                    // TODO Add reward to user
                    user.setPicture(R.drawable.cat_glassesgif)
                }
            }
            if( name == "reward2"){
                val pb = view.findViewById<ProgressBar>(R.id.pbreward2)
                view.findViewById<TextView>(R.id.reward2).text = tracker
                pb.max = requirement
                pb.progress = progress
                if (complete)
                {
                    // TODO Add reward to user
                    user.setBorder(R.drawable.sparkle_border)
                }
            }
            if( name == "reward3"){
                val pb = view.findViewById<ProgressBar>(R.id.pbreward3)
                view.findViewById<TextView>(R.id.reward3).text = tracker
                pb.max = requirement
                pb.progress = progress
                if (complete)
                {
                    // TODO Add reward to user
                    user.setPicture(R.drawable.bavatogay_ricardo_milos)
                    user.setBorder(R.drawable.rainbow_color_colorful)
                }
            }
        }
        progressBar.max = maxScore
        progressBar.progress = this.score
        view.findViewById<TextView>(R.id.textUser).text = user.getUsername()
        "Current Score: $score/$maxScore".also { view.findViewById<TextView>(R.id.textScore)
            .text = it }
        view.findViewById<TextView>(R.id.textTitle).text = user.getTitle()
        view.findViewById<GifImageView>(R.id.imageUser).setImageResource(user.getPicture())
        view.findViewById<GifImageView>(R.id.imageUserBorder).setImageResource(user.getBorder())

        val settingsFragmentButton: Button = view.findViewById(R.id.buttonSettings)
        settingsFragmentButton.setOnClickListener {
            val fragmentManager = fragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            if (fragmentTransaction != null) {
                fragmentTransaction.replace(R.id.frame_layout, Setting(), "currentFragment")
                fragmentTransaction.commit()
            }
        }
    }

}

