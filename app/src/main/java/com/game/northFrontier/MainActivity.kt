package com.game.northFrontier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.game.northFrontier.ViewModel.LocationViewModel
import com.game.northFrontier.ui.theme.NFTheme
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.game.northFrontier.database.AppDatabase
import com.game.northFrontier.database.LocationsDao
import com.game.northFrontier.view.Screen
import com.game.northFrontier.view.SetupNavGraph
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<LocationViewModel>()
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //checkURIonImage(intent,viewModel)
        val dao = AppDatabase.getInstance(this).locationDao
        lifecycleScope.launch {viewModel.locationsID =dao.getLocationsIdList() }





        setContent {
            NFTheme {
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








