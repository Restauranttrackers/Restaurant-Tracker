package com.bignerdranch.android.maptest


import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import pl.droidsonroids.gif.GifImageView
import com.bignerdranch.android.maptest.MapsActivity.Companion.data



import android.widget.Button

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class profile : Fragment() {
    private var maxScore: Int = 100
    private var score: Int = 0
    private var userName: String? = null
    private var title: String? = null
    private var picture = R.drawable.bavatogay_ricardo_milos // TODO From Json/db = NULL
    private var border = R.drawable.rainbow_color_colorful // TODO From Json/db = NULL
    private var rewardContainer = ArrayList<Rewards>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = view.findViewById<ProgressBar>(R.id.pbscore)


        data.getUser()
        setName()
        setScore()
        setTitle()
        setPicture()
        setBorder()
        createRewards(rewardContainer)
        progressBar.max = maxScore
        progressBar.progress = score
        view.findViewById<TextView>(R.id.textUser).text = userName
        "Current Score: $score/$maxScore".also { view.findViewById<TextView>(R.id.textScore)
            .text = it }
        view.findViewById<TextView>(R.id.textTitle).text = title
        view.findViewById<GifImageView>(R.id.imageUser).setImageResource(picture)
        view.findViewById<GifImageView>(R.id.imageUserBorder).setImageResource(border)
        // Routine to update / Add reward to user
        rewardContainer.forEach{
            val name: String = it.name
            val tracker: String = it.tracker
            val progress: Int = it.progress
            val requirement: Int = it.requirement
            val complete: Boolean = it.complete
            if (complete)
            {
                // TODO Add reward to user
            }
            if( name == "reward1"){
                val pb = view.findViewById<ProgressBar>(R.id.pbreward3)
                view.findViewById<TextView>(R.id.reward1).text = tracker
                pb.max = requirement
                pb.progress = progress
            }
            if( name == "reward2"){
                val pb = view.findViewById<ProgressBar>(R.id.pbreward2)
                view.findViewById<TextView>(R.id.reward2).text = tracker
                pb.max = requirement
                pb.progress = progress
            }
            if( name == "reward3"){
                val pb = view.findViewById<ProgressBar>(R.id.pbreward3)
                view.findViewById<TextView>(R.id.reward3).text = tracker
                pb.max = requirement
                pb.progress = progress
            }
        }

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
    /**
     * Initializes the name from database
     */
    private fun setName(){
    this.userName = (data.listUser[0].firstname as String) + " " + (data.listUser[0].lastname as String)
    }
    /**
     * Initializes the score from database
     */
    private fun setScore(){
    this.score = data.listUser[0].score as Int
    }
    /**
     * Initializes the title from database
     */
    private fun setTitle(){
    this.title = data.listUser[0].banner as String
    }
    /**
     * Initializes the picture from settings
     */
    private fun setPicture(){
    //TODO: Get from settings json
    }
    /**
     * Initializes the border from settings
     */
    private fun setBorder(){
    //TODO: Get from settings json
    }

    // TODO: Move to separate module
    class Rewards(
        var name: String = "", var tracker: String = "",
        var progress: Int = 0, var requirement: Int = 0, var complete: Boolean = false
    )

    /**
    * Creates Rewards objects and pushes them to the container.
    * @param ArrayList of type Rewards.
     */
    private fun createRewards(rewardContainer: ArrayList<Rewards>) {
        var requirement= 20
        var tracker= ""
        if (score >= requirement) {
            val reward1 = Rewards("reward1", "Completed!", score, requirement, true)
            rewardContainer.add(reward1)
        } else {
            tracker = "$score/$requirement"
            val reward1 = Rewards("reward1", tracker, score, requirement, false)
            rewardContainer.add(reward1)
        }
        requirement = 40
        tracker = ""
        if (score >= requirement) {
            val reward2 = Rewards("reward2", "Completed!", score, requirement, true)
            rewardContainer.add(reward2)
        } else {
            tracker = "$score/$requirement"
            val reward2 = Rewards("reward2", tracker, score, requirement, false)
            rewardContainer.add(reward2)
        }
        requirement = 75
        tracker = ""
        if (score >= requirement) {
            val reward3 = Rewards("reward3", "Completed!", score, requirement, true)
            rewardContainer.add(reward3)
        } else {
            tracker = "$score/$requirement"
            val reward3 = Rewards("reward3", tracker, score, requirement, false)
            rewardContainer.add(reward3)
        }
    }

}

