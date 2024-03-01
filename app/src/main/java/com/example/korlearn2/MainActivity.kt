package com.example.korlearn2

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusTargetModifierNode
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.korlearn2.ViewModel.LocationViewModel
//import com.example.korlearn2.ViewModel.LocationVmFactory
import com.example.korlearn2.ui.theme.KorLearn2Theme
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.korlearn2.database.AppDatabase
import com.example.korlearn2.database.LocalRuler
import com.example.korlearn2.database.Location
import com.example.korlearn2.database.LocationsDao
import com.example.korlearn2.database.Squad
import com.example.korlearn2.database.generateAll
import kotlinx.coroutines.launch
import kotlin.random.Random


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<LocationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //checkURIonImage(intent,viewModel)
        val dao = AppDatabase.getInstance(this).locationDao

        val locations = listOf(
            Location(1,"London", "Gerold"),
            Location(2,"Paris", "null"),
            Location(3,"Milan", "null")
        )
        val localRulers = listOf(

            LocalRuler(1,"Gerold"),
            LocalRuler(2,"Andrew"),
            LocalRuler(3,"Toolchain"),
            LocalRuler(4,"Jon"),
            LocalRuler(6,"Jac"),
            LocalRuler(7,"Lori"),
            LocalRuler(8,"Mads"),
            LocalRuler(9,"Henry")
        )
        val squads = listOf(
            Squad(1,"null","null",30),
            Squad(2,"null","null",20),
            Squad(3,"null","null",24),
            Squad(4,"null","null",15)
        )


        lifecycleScope.launch {
            locations.forEach { dao.insertLocation(it) }
            localRulers.forEach { dao.insertLocalRuler(it) }
            squads.forEach { dao.insertSquad(it) }
        }

        println("onCreate")
        setContent {
            KorLearn2Theme {
                Column (modifier = Modifier.fillMaxSize(),

                ) {
                    Text(text = viewModel.text)
                    TextField(
                        value = viewModel.text,
                        onValueChange = {viewModel.text = it},

                    )
                    InfoButtons(lifecycleScope = lifecycleScope, dao = dao, viewModel = viewModel)
                    Button(
                        onClick =
                        {

                            generateAll(lifecycleScope,dao,viewModel)

                        }
                    ) {
                        Text(text = "generate")
                    }
                    Text(text = viewModel.text1)
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

            lifecycleScope.launch {
                var s: String = "Locations"
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
                var s: String = "Leaders:"
                dao.getLocalRuler().forEach {
                    s = s.plus("\n").plus(it.id).plus(" ").plus(it.rulerName)
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
                var s: String = "Squads:"
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








