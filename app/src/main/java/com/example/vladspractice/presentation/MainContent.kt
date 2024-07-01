package com.example.vladspractice.presentation

import android.content.res.AssetManager
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vladspractice.Data.util.Java
import kotlinx.coroutines.delay
import java.io.FileInputStream
import java.io.InputStream
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import org.intellij.lang.annotations.Language
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList




@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavHostController,
) {
    val isInsert by viewModel.isInsert.collectAsState()
    val valueLoading by viewModel.valueLoading.collectAsState()

    val orientationState by viewModel.orientation.observeAsState("Vertical")
    val context = LocalContext.current


    LaunchedEffect(valueLoading) {
        if (isInsert) {
            if ((valueLoading /100f)>=1) {
                viewModel.setInsert(false)
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Header()

        Column(
            modifier = modifier.align(Alignment.Center),
        ) {
            if (isInsert) {
                CustomCircle(
                    modifier = Modifier
                        .size(250.dp),
                    initialValue = valueLoading,
                    primaryColor = Color(15,112,255),
                    secondaryColor = Color.Gray,
                    circleRadius = 230f,
                    onPositionChange = {
                        viewModel.updateValueLoading(it)
                    }
                )
            } else {
                Text(text = orientationState)

                MyButton(text = "Click me") {
                    viewModel.rotateScreen()
                }
                MyButton(text = "Table") {
                    navController.navigate("table_screen")
                }

                MyButton(text = "Setting Table") {
                    viewModel.getListOfTableBd()
                    viewModel.getListTableField()
                    navController.navigate("setting_page")
                }

                MyButton(text = "Read XML") {
                    viewModel.writeToBd(context = context, fileName = "NS_MC.xml", viewModel)
                    Log.d("Read XML", "Success")
                }

                MyButton(text = "Clear BD") {
                    viewModel.deleteDataFromBd(viewModel.selectedTable, null, null)
                }

                MyButton(text = "Create Table") {
                    viewModel.createTable(context, "request.json")
                    Log.d("Create Table", "End")
                }
            }
        }
    }
}



@Composable
fun CustomCircularProgressIndicator(
    valueLoading: Float
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(80.dp)) {
        CircularProgressIndicator(
            modifier = Modifier.size(80.dp),
            progress = { valueLoading / 100f }
        )
        Text(
            text = "${valueLoading.toInt()}%",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp
        )
    }
}
@Composable
fun Header() {
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .background(Color.Blue)
            .padding(start = 10.dp, top = 5.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Parcer", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}

@Composable
fun MyButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(width = 130.dp, height = 40.dp)
            .padding(top = 5.dp),
        shape = RoundedCornerShape(17.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color.Blue
        ),
    ) {
        Text(text)
    }
}