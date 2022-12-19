package com.bignerdranch.android.maptest


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import pl.droidsonroids.gif.GifImageView

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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var maxScore: Int = 100
    private var score: Int = 33 // TODO From database object setScore == NULL
    private var userName: String = "User Usington" // TODO From database object getName = NULL
    private var title: String = "Enjoyer" // TODO From json/database =NULL
    private var picture = R.drawable.bavatogay_ricardo_milos // TODO From Json/db = NULL
    private var border = R.drawable.rainbow_color_colorful // TODO From Json/db = NULL
    private var rewardContainer = ArrayList<Rewards>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

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

        createRewards(rewardContainer)
        progressBar.max = maxScore
        progressBar.progress = score
        view.findViewById<TextView>(R.id.textUser).text = userName
        "Current Score: $score/$maxScore".also { view.findViewById<TextView>(R.id.textScore)
            .text = it }
        view.findViewById<TextView>(R.id.textTitle).text = title
        view.findViewById<GifImageView>(R.id.imageUser).setImageResource(picture)
        view.findViewById<GifImageView>(R.id.imageUserBorder).setImageResource(border)
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
                val pb = view.findViewById<ProgressBar>(R.id.pbreward3)
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
    }

    class Rewards(
        var name: String = "", var tracker: String = "",
        var progress: Int = 0, var requirement: Int = 0, var complete: Boolean = false
    )

    /*
    * Creates Rewards objects and pushes them to the container.
    * @param ArrayList of type Rewards.
     */
    private fun createRewards(rewardContainer: ArrayList<Rewards>) {
        var requirement= 20
        var tracker= ""
        if (score >= requirement) {
            val reward1 = Rewards("reward1", "Completed!", score, 40, true)
            rewardContainer.add(reward1)
        } else {
            tracker = "$score/$requirement"
            val reward1 = Rewards("reward1", tracker, score, 40, false)
            rewardContainer.add(reward1)
        }
        requirement = 40
        tracker = ""
        if (score >= requirement) {
            val reward2 = Rewards("reward2", "Completed!", score, 80, true)
            rewardContainer.add(reward2)
        } else {
            tracker = "$score/$requirement"
            val reward2 = Rewards("reward2", tracker, score, 80, false)
            rewardContainer.add(reward2)
        }
        requirement = 60
        tracker = ""
        if (score >= requirement) {
            val reward3 = Rewards("reward3", "Completed!", score, 80, true)
            rewardContainer.add(reward3)
        } else {
            tracker = "$score/$requirement"
            val reward3 = Rewards("reward3", tracker, score, 80, false)
            rewardContainer.add(reward3)
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment map.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            map().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

