package app.vcampus.client.viewmodel

import app.vcampus.client.repository.FakeRepository
import moe.tlaster.precompose.viewmodel.ViewModel

class MainPanelViewModel(
    private val id: Int,
) : ViewModel() {
    val note by lazy {
        FakeRepository.getLiveData(id)
    }
}