package app.vcampus.client.scene.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.vcampus.server.entity.LibraryTransaction
import app.vcampus.server.utility.DateUtility
import java.util.*

/**
 * book list component, used in `MyBookSubscene` and `ReturnSubscene`
 *
 * @param lt library transaction, stores the information of transaction
 * @param renewBook renew book function
 * @param returnBook return book function
 */
@Composable
fun bookList(lt: LibraryTransaction, renewBook: (UUID) -> Unit, returnBook: ((UUID) -> Unit)? = null) {
    Surface(
        modifier = Modifier.fillMaxWidth().border(
            1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(4.dp)
        ).padding(10.dp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Column {
                Text(lt.book.name, fontWeight = FontWeight(700))
                Text("书籍标识号：${lt.book.uuid}")
                Text("馆藏地：${lt.book.place}")
                Text("借阅日期：${DateUtility.fromDate(lt.borrowTime)}")
                Text("应还日期：${DateUtility.fromDate(lt.dueTime)}")
            }
            Spacer(Modifier.weight(1F))
            Column {
                Button(onClick = {
                    renewBook(lt.uuid)
                }) {
                    Text("续借")
                }

                if (returnBook != null) {
                    Button(onClick = {
                        returnBook(lt.uuid)
                    }) {
                        Text("还书")
                    }
                }
            }
        }
    }
}