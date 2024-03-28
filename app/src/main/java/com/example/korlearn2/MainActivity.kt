package com.example.korlearn2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import com.example.korlearn2.ViewModel.LocationViewModel
//import com.example.korlearn2.ViewModel.LocationVmFactory
import com.example.korlearn2.ui.theme.KorLearn2Theme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.korlearn2.database.AppDatabase
import com.example.korlearn2.database.LocationsDao
import com.example.korlearn2.database.generateAll
import com.example.korlearn2.database.nextMonth
import com.example.korlearn2.view.Screen
import com.example.korlearn2.view.SetupNavGraph
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<LocationViewModel>()
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //checkURIonImage(intent,viewModel)
        val dao = AppDatabase.getInstance(this).locationDao
        lifecycleScope.launch {viewModel.locationsID =dao.getLocationsIdList() }

        println("onCreate")



        setContent {
            KorLearn2Theme {
                navController = rememberNavController()
                SetupNavGraph(
                    navController = navController,
                    dao = dao,
                    viewModel = viewModel,
                    lifecycleScope = lifecycleScope,
                    context = applicationContext
                )



        }
        }

    }

    override fun onResume() {
        super.onResume()
        println("onResume")

    }
    override fun onStart() {
        super.onStart()
        println("onStart")
    }
    override fun onPause() {
        super.onPause()
        println("onPause")
    }
    override fun onStop() {
        super.onStop()
        println("onStop")
    }
    override fun onRestart() {
        super.onRestart()
        println("onRestart")
    }
    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy")
    }

    /*override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        println("onNewIntent")
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM)
        }
        viewModel.updateUri(uri)
        viewModel.changeImage(5)
    }*/

}
@Composable
fun InfoButtons(lifecycleScope: LifecycleCoroutineScope, dao: LocationsDao, viewModel: LocationViewModel){

    Button(
        onClick =
        {

            lifecycleScope.launch {


                viewModel.text1 = "Your stats: ${dao.getYourStats()[0].showAllData()} \n Enemy stats: ${dao.getEnemyStats()[0].showAllData()}"
            }
        }
    ) {
        Text(text = "Stats")
    }
    Button(
        onClick =
        {

            lifecycleScope.launch {
                var s = "Locations"
                dao.getLocation().forEach {
                    s = s.plus("\n").plus(it.id).plus(" ").plus(it.locationName)
                        .plus(" ").plus(it.rulerName)
                }
                viewModel.text1 = s
            }
        }
    ) {
        Text(text = "Loc. list")
    }
    Button(
        onClick =
        {

            lifecycleScope.launch {
                var s = "Leaders:"
                dao.getLocalRuler().forEach {
                    s = s.plus("\n").plus(it.showFullInfo())
                }
                viewModel.text1 = s
            }
        }
    ) {
        Text(text = "Ruler list")
    }

    Button(
        onClick =
        {
            lifecycleScope.launch {
                var s = "Squads:"
                dao.getSquad().forEach {
                    s+= "\n${it.id}st. Leader: ${it.rulerName}, Location: ${it.locationId}, ${it.number} soldiers."
                }
                viewModel.text1 = s
            }
        }
    ) {
        Text(text = "Squad list")
    }
}

@Composable
fun BackGround(id: Int){
    Box(modifier = Modifier.fillMaxSize()){
        val p: Painter = when (id) {
            1-> painterResource(id = R.drawable.im1)
            2-> painterResource(id = R.drawable.im2)
            3-> painterResource(id = R.drawable.im3)
            4-> painterResource(id = R.drawable.im4)
            else -> painterResource(id = R.drawable.im5)
        }
        Image(
            painter = p,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f),
            contentScale = ContentScale.Crop

        )

    }
}

@Composable
fun BackButton(navController: NavController){
    Button(
        onClick = { navController.navigate(Screen.MainInfo.route) }) {
        Text(
            text = "Back",
            fontSize = 25.sp
        )
    }
}
/*@Composable
fun ShowImage(id: Int) {
    if (id==1) {
        Image(
            painter = painterResource(id = R.drawable.im1),
            contentDescription = null,
            alignment = Alignment.TopCenter
        )
    }
    if (id==2) {
        Image(
            painter = painterResource(id = R.drawable.im2),
            contentDescription = null,
            alignment = Alignment.TopCenter
        )
    }
    if (id==5) {
        Image(
            painter = painterResource(id = R.drawable.im5),
            contentDescription = null,
            alignment = Alignment.TopCenter
        )
    }
}*/

/*fun checkURIonImage(imt1: Intent, lvm: LocationViewModel){
    val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        imt1.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
    } else {
        imt1.getParcelableExtra(Intent.EXTRA_STREAM)
    }
    uri?.let {
        println("uri let")
        lvm.updateUri(uri)
    }
}*/








