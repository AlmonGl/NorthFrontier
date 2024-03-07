package com.example.korlearn2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.korlearn2.ViewModel.LocationViewModel
//import com.example.korlearn2.ViewModel.LocationVmFactory
import com.example.korlearn2.ui.theme.KorLearn2Theme
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.korlearn2.database.AppDatabase
import com.example.korlearn2.database.LocationsDao
import com.example.korlearn2.database.generateAll
import com.example.korlearn2.database.nextMonth
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<LocationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //checkURIonImage(intent,viewModel)
        val dao = AppDatabase.getInstance(this).locationDao
        lifecycleScope.launch {viewModel.locationsID =dao.getLocationsIdList() }

        println("onCreate")
        setContent {
            KorLearn2Theme {
                Column (modifier = Modifier.fillMaxSize(),

                ) {



                    LocationList(locationsID = viewModel.locationsID, viewModel = viewModel )

                    InfoButtons(lifecycleScope = lifecycleScope, dao = dao, viewModel = viewModel)
                    Row {
                        Button(
                            onClick =
                            {

                                generateAll(lifecycleScope, dao, viewModel,applicationContext)

                            }
                        ) {
                            Text(text = "generate new game")
                        }
                        Button(
                            onClick =
                            {

                               nextMonth(lifecycleScope, dao, viewModel)

                            }
                        ) {
                            Text(text = "next month")
                        }
                    }
                    Text(text = viewModel.text1, modifier = Modifier.verticalScroll(ScrollState(0)))
                    /*Text(text = viewModel.text)
                    Button(
                        onClick =
                        { viewModel.compute() }
                    ) {
                        Text(text = "Do something")
                    }*/
                    /*viewModel.uri?.let {
                        AsyncImage(
                            model = viewModel.uri,
                            contentDescription = null
                        )
                    }*/
                    /*ShowImage(viewModel.imageID)
                    Button(
                        onClick = {
                            viewModel.changeBGcolor()
                            viewModel.changeImage(2)

                        },
                        modifier = Modifier
                            .height(100.dp)
                            .width(100.dp),
                    ) {
                        Text(text = "Change Color")
                    }*/
                    /*Button(
                        onClick = {
                            /*Intent(this, SecondActivity::class.java).also {
                            startActivity(it)
                            }
                            */
                            /*Intent(Intent.ACTION_MAIN).also {
                                it.`package` = "com.google.android.youtbe"
                                try {
                                    startActivity(it)
                                } catch (e: ActivityNotFoundException){
                                    e.printStackTrace()
                                }
                            }*/
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_EMAIL, arrayOf("meil@meil.ro"))
                                putExtra(Intent.EXTRA_SUBJECT, "arrayOf")
                                putExtra(Intent.EXTRA_TEXT, "HI")
                            }
                            if (intent.resolveActivity(packageManager) != null) {
                                startActivity(intent)
                            }
                        },
                        modifier = Modifier
                            .size(100.dp, 100.dp)

                    ) {
                        Text(text = "Another screen")
                    }*/
                }



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


            val locationId = try {
                    viewModel.locationID.toInt()
            }
            catch (e: Exception){
                0
            }


                if (locationId in 1..25) {
                    lifecycleScope.launch {

                        viewModel.text1 = dao.getLocationById(locationId).showAllData()
                    }
                }


        }
    ) {
        Text(text = "Display location X")
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
        Text(text = "Display locations")
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
        Text(text = "Display rulers")
    }
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
                var s = "Squads:"
                dao.getSquad().forEach {
                    s+= "\n${it.id}st. Leader: ${it.rulerName}, Location: ${it.locationName}, ${it.number} soldiers."
                }
                viewModel.text1 = s
            }
        }
    ) {
        Text(text = "Display squads")
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LocationList(
    locationsID: List<Int>,
    viewModel: LocationViewModel

) {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Select Location") }
    LocalSoftwareKeyboardController.current?.hide()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {



                    locationsID.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item.toString()) },

                            onClick = {
                                selectedText = item.toString()
                                viewModel.locationID=item.toString()
                                expanded = false

                            }
                        )


                }
            }
        }
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








