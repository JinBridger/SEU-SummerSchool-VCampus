package app.vcampus.client.scene.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * role chip components, shows all role and could be selected
 *
 * @param roles original roles
 * @param onChange when change the role
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun roleChip(roles: List<String> = listOf(), onChange: (List<String>) -> Unit = {}) {
    val selectedAdmin = remember { mutableStateOf(roles.contains("admin")) }
    val selectedStudent = remember { mutableStateOf(roles.contains("student")) }
    val selectedTeacher = remember { mutableStateOf(roles.contains("teacher")) }
    val selectedTeachingAffair = remember { mutableStateOf(roles.contains("affairs_staff")) }
    val selectedLibraryUser = remember { mutableStateOf(roles.contains("library_user")) }
    val selectedLibraryStaff = remember { mutableStateOf(roles.contains("library_staff")) }
    val selectedShopUser = remember { mutableStateOf(roles.contains("shop_user")) }
    val selectedShopStaff = remember { mutableStateOf(roles.contains("shop_staff")) }
    val selectedFinanceUser = remember { mutableStateOf(roles.contains("finance_user")) }
    val selectedFinanceStaff = remember { mutableStateOf(roles.contains("finance_staff")) }

    val callback = {
        onChange(
            listOfNotNull(
                if (selectedAdmin.value) "admin" else null,
                if (selectedStudent.value) "student" else null,
                if (selectedTeacher.value) "teacher" else null,
                if (selectedTeachingAffair.value) "affairs_staff" else null,
                if (selectedLibraryUser.value) "library_user" else null,
                if (selectedLibraryStaff.value) "library_staff" else null,
                if (selectedShopUser.value) "shop_user" else null,
                if (selectedShopStaff.value) "shop_staff" else null,
                if (selectedFinanceUser.value) "finance_user" else null,
                if (selectedFinanceStaff.value) "finance_staff" else null,
            )
        )
    }

    FlowRow(Modifier.fillMaxWidth()) {
        selectableChip(selectedAdmin, "管理员", callback)
        Spacer(Modifier.width(10.dp))
        selectableChip(selectedStudent, "学生", callback)
        Spacer(Modifier.width(10.dp))
        selectableChip(selectedTeacher, "教师", callback)
        Spacer(Modifier.width(10.dp))
        selectableChip(selectedTeachingAffair, "教务", callback)
        Spacer(Modifier.width(10.dp))
        selectableChip(selectedLibraryUser, "图书馆使用权限", callback)
        Spacer(Modifier.width(10.dp))
        selectableChip(selectedLibraryStaff, "图书馆管理权限", callback)
        Spacer(Modifier.width(10.dp))
        selectableChip(selectedShopUser, "商店使用权限", callback)
        Spacer(Modifier.width(10.dp))
        selectableChip(selectedShopStaff, "商店管理权限", callback)
        Spacer(Modifier.width(10.dp))
        selectableChip(selectedFinanceUser, "财务使用权限", callback)
        Spacer(Modifier.width(10.dp))
        selectableChip(selectedFinanceStaff, "财务管理权限", callback)
    }
}