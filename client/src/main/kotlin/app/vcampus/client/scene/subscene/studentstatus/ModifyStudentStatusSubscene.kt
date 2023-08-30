package app.vcampus.client.scene.subscene.studentstatus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.StudentStatusViewModel

@Composable
fun modifyStudentStatusSubscene(viewModel: StudentStatusViewModel) {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("修改学籍信息", "修改个人学籍信息")
            }
        }
    }
}