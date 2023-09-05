package app.vcampus.client.scene.subscene.library

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import kotlinx.coroutines.launch
import java.awt.SystemColor.text

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun myBookSubscene(viewModel: LibraryViewModel) {
//    var isText1Clicked by remember { mutableStateOf(false) }
//    var isText2Clicked by remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("我的书籍", "查看已借阅书籍")
            }

            item {
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
                Row(
                    modifier = Modifier.fillMaxWidth().background(Color.LightGray).padding(8.dp)
                ) {
                    Text(
                        text = "条码号",
                        fontWeight = FontWeight(700),
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(10.dp))

                    Text(
                        text = "题名",
                        fontWeight = FontWeight(700),
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "借阅日期",
                        fontWeight = FontWeight(700),
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "应还日期",
                        fontWeight = FontWeight(700),
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "续借量",
                        fontWeight = FontWeight(700),
                        fontSize = 14.sp,
                        modifier = Modifier.weight(0.8f)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "馆藏地",
                        fontWeight = FontWeight(700),
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "续借",
                        fontWeight = FontWeight(700),
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            (0..10).forEach {
                item {
                    var isText1Clicked by remember { mutableStateOf(false) }
                    var isText2Clicked by remember { mutableStateOf(false) }
                    val scope = rememberCoroutineScope()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(8.dp)
                            .heightIn(min = 60.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "07281527",
                            fontWeight = FontWeight(700),
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f).widthIn(max = 20.dp).wrapContentHeight()
                        )
                        Spacer(Modifier.width(10.dp))


                        Text(
                            text = "C++ Primer 中文版",
                            fontWeight = FontWeight(700),
                            fontSize = 14.sp,
                            color = Color.Blue,
                            modifier = Modifier
                                .weight(1f)
                                .widthIn(max = 20.dp)
                                .wrapContentHeight()
                                .clickable {
                                    isText1Clicked = !isText1Clicked
                                    if (isText1Clicked && isText2Clicked) {
                                        isText2Clicked = false
                                    }
                                }
                        )

                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = "2023-08-21",
                            fontWeight = FontWeight(700),
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f).widthIn(max = 20.dp).wrapContentHeight()
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = "2023-09-20",
                            fontWeight = FontWeight(700),
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f).widthIn(max = 20.dp).wrapContentHeight()
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = "0",
                            fontWeight = FontWeight(700),
                            fontSize = 14.sp,
                            modifier = Modifier.weight(0.8f).widthIn(max = 20.dp).wrapContentHeight()
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = "工业技术图书阅览室(九龙湖A401)",
                            fontWeight = FontWeight(700),
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f).widthIn(max = 20.dp).wrapContentHeight()
                        )
                        Spacer(Modifier.width(10.dp))


                        Text(
                            text = "续借",
                            fontWeight = FontWeight(700),
                            fontSize = 14.sp,
                            color = Color.Blue,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    isText2Clicked = !isText2Clicked
                                    if (isText1Clicked && isText2Clicked) {
                                        isText1Clicked = false
                                    }
                                }
                        )


                    }
                    if (isText1Clicked) {
                        Spacer(Modifier.height(6.dp))
                        Divider()
                        Spacer(Modifier.height(6.dp))
                        Surface(
                            modifier = Modifier.fillMaxWidth().border(
                                1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(4.dp)
                            ).animateContentSize (
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = LinearOutSlowInEasing
                                )
                            ), // 添加合适的布局参数
                            color = Color.White, // 自定义背景颜色
                            elevation = 2.dp, // 阴影
                            shape = RoundedCornerShape(4.dp) // 圆角形状
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                   // horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(Modifier.fillMaxWidth().weight(1f)){
                                        Text("书名: ", fontWeight = FontWeight(700))
                                        Text("C++ Primer 中文版")
                                    }
                                    Spacer(Modifier.width(10.dp))
                                    Row(Modifier.fillMaxWidth().weight(1f)){
                                        Text("ISBN: ", fontWeight = FontWeight(700))
                                        Text("9787302517597")
                                    }

                                }
                                Spacer(Modifier.height(4.dp))
                                Row(Modifier.fillMaxWidth()) {
                                    Row(Modifier.fillMaxWidth().weight(1f)){
                                        Text("作者: ", fontWeight = FontWeight(700))
                                        Text("Stanley B. LippmanBarbara E. Moo JoséeLaJoie")
                                    }
                                    Spacer(Modifier.width(10.dp))
                                    Row(Modifier.fillMaxWidth().weight(1f)){
                                        Text("出版社: ", fontWeight = FontWeight(700))
                                        Text("电子工业出版社")
                                    }
                                }
                                Spacer(Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                   // horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("简介: ", fontWeight = FontWeight(700))
                                    Text("本书是久负盛名的C++经典教程,其内容是C++大师Stanley B. Lippman丰富的实践经验和C++标准委员会原负责人Josée Lajoie对C++标准深入理解的完美结合，已经帮助全球无数程序员学会了C++。 对C++基本概念和技术全面而且权威的阐述，对现代C++编程风格的强调，使本书成为C++初学者的最佳指南；对于中高级程序员，本书也是不可或缺的参考书。")
                                }
                                Spacer(Modifier.height(4.dp))
                            }
                        }
                    }


                    if (isText2Clicked) {
                        Box(contentAlignment = Alignment.Center){
                            Spacer(Modifier.height(16.dp))

                            val state = remember {
                                SnackbarHostState()
                            }
                            SnackbarHost(hostState = state)

                            Crossfade("") {
                                    scope.launch {
                                        state.showSnackbar(
                                            "未到续借时间，不可续借（仿学校的“我的图书馆”，可能有点鸡肋，可去）", "关闭"
                                        )
                                    }
                            }

                        }
                    }
                }


            }
        }
    }
}

