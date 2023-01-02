package models

/**
 * Loads Rewards to Profile.
 */
class Rewards(

    var name: String = "", var tracker: String = "",
    var progress: Int = 0, var requirement: Int = 0, var complete: Boolean = false
){

/**
 * Creates Rewards objects and pushes them to the container.
 * @param ArrayList<> of type Rewards.
 */
 fun createRewards(rewardContainer: ArrayList<Rewards>) : ArrayList<Rewards>{
    var requirement= 20
    var tracker= ""
    if (progress >= requirement) {
        val reward1 = Rewards("reward1", "Completed!", progress, requirement, true)
        rewardContainer.add(reward1)
    } else {
        tracker = "$progress/$requirement"
        val reward1 = Rewards("reward1", tracker, progress, requirement, false)
        rewardContainer.add(reward1)
    }
    requirement = 40
    tracker = ""
    if (progress >= requirement) {
        val reward2 = Rewards("reward2", "Completed!", progress, requirement, true)
        rewardContainer.add(reward2)
    } else {
        tracker = "$progress/$requirement"
        val reward2 = Rewards("reward2", tracker, progress, requirement, false)
        rewardContainer.add(reward2)
    }
    requirement = 75
    tracker = ""
    if (progress >= requirement) {
        val reward3 = Rewards("reward3", "Completed!", progress, requirement, true)
        rewardContainer.add(reward3)
    } else {
        tracker = "$progress/$requirement"
        val reward3 = Rewards("reward3", tracker, progress, requirement, false)
        rewardContainer.add(reward3)
    }
    return rewardContainer
}
}
