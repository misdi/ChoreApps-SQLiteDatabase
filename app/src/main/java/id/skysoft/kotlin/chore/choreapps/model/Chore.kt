package id.skysoft.kotlin.chore.choreapps.model

class Chore(){
    var choreName: String? = null
    var assignBy: String? = null
    var assignTo: String? = null
    var timeAssigned: Long? = null
    var Id:Int? = null

    constructor(choreName: String, assignBy:String,
                assignTo:String, timeAssigned:Long,
                Id:Int) : this(){
        this.choreName = choreName
        this.assignBy = assignBy
        this.assignTo = assignTo
        this.timeAssigned = timeAssigned
        this.Id = Id
    }


}