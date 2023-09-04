package app.vcampus.client.scene.subscene.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.LibraryViewModel
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.TableColumnWidth
import com.seanproctor.datatable.material.DataTable
import java.awt.SystemColor.text

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun myBookSubscene(viewModel: LibraryViewModel) {
    var isTextClicked by remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("我的书籍", "查看已借阅书籍")
            }

            item{
                Spacer(Modifier.height(20.dp))

                val annotatedString = buildAnnotatedString {
                    append("当前借阅(")
                    withStyle(style = SpanStyle(color = Blue)) {
                        append("10")
                    }
                    append(")/最大借阅(")
                    withStyle(style = SpanStyle(color = Blue)) {
                        append("30")
                    }
                    append(")")
                }

                Text(text = annotatedString, fontSize = 14.sp)

                Spacer(Modifier.height(8.dp))
            }

            item {
                Row (
                    modifier=Modifier.fillMaxWidth().background(Color.LightGray).padding(8.dp)
                ){
                    Text(
                        text="条码号",
                        fontWeight = FontWeight(700),
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(10.dp))

                    Text(
                        text="题名",
                        fontWeight = FontWeight(700),
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(Modifier.width(10.dp))
                    Text(
                        text="借阅日期",
                        fontWeight = FontWeight(700),
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text="应还日期",
                        fontWeight = FontWeight(700),
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text="续借量",
                        fontWeight = FontWeight(700),
                        fontSize = 14.sp,
                        modifier = Modifier.weight(0.8f)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text="馆藏地",
                        fontWeight = FontWeight(700),
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text="续借",
                        fontWeight = FontWeight(700),
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            (0..10).forEach {
                item{
                    Row(modifier=Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(8.dp)
                        .heightIn(min= 60.dp),
                        horizontalArrangement =Arrangement.Center) {
                        Text(
                            text="07281527",
                            fontWeight = FontWeight(700),
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f).widthIn(max=20.dp).wrapContentHeight()
                        )
                        Spacer(Modifier.width(10.dp))


                        Text(
                            text="C++ Primer 中文版",
                            fontWeight = FontWeight(700),
                            fontSize = 14.sp,
                            color = Color.Blue,
                            modifier = Modifier
                                .weight(1f)
                                .widthIn(max=20.dp)
                                .wrapContentHeight()
                                .clickable {
                                    isTextClicked=!isTextClicked
                                }
                        )



                        Spacer(Modifier.width(10.dp))
                        Text(
                            text="2023-08-21",
                            fontWeight = FontWeight(700),
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f).widthIn(max=20.dp).wrapContentHeight()
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text="2023-09-20",
                            fontWeight = FontWeight(700),
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f).widthIn(max=20.dp).wrapContentHeight()
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text="0",
                            fontWeight = FontWeight(700),
                            fontSize = 14.sp,
                            modifier = Modifier.weight(0.8f).widthIn(max=20.dp).wrapContentHeight()
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text="工业技术图书阅览室(九龙湖A401)",
                            fontWeight = FontWeight(700),
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f).widthIn(max=20.dp).wrapContentHeight()
                        )
                        Spacer(Modifier.width(10.dp))


                        Text(
                            text="续借",
                            fontWeight = FontWeight(700),
                            fontSize = 14.sp,
                            color = Color.Blue,
                            modifier = Modifier
                                .weight(1f)
                                .clickable{
                                    isTextClicked=!isTextClicked
                                }
                        )




                    }
            }

            }


        }
    }
}

