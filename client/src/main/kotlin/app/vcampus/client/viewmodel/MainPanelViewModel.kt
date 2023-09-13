package app.vcampus.client.viewmodel

import moe.tlaster.precompose.viewmodel.ViewModel
import java.time.LocalTime

class MainPanelViewModel : ViewModel() {
    var currentTime: LocalTime
    var greetings = ""

    init {
        currentTime = LocalTime.now()
        if(currentTime.hour < 6 || currentTime.hour > 18) {
            greetings = "晚上好"
        }
        if (currentTime.hour in 6..11) {
            greetings = "上午好"
        }
        if(currentTime.hour in 12..18) {
            greetings = "下午好"
        }
    }
}