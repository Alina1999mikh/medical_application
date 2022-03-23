import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myapplication.model.FullName
import com.myapplication.model.Profile
import com.myapplication.tools.DateParser

@Composable
fun BottomNavGraph(navController: NavHostController) {
    val profile = remember {
        //XmlHelper.read()
        Profile(FullName("Evgeniy","Ignatenko"), DateParser.parseStringToDate("2000-02-20"), 'M')
    }
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Result.route
    ) {
        composable(route = BottomBarScreen.Document.route) {
            DocumentScreen()
        }
        composable(route = BottomBarScreen.Result.route) {
            ResultScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            System.out.println(profile)
            ProfileScreen(profile)
        }
    }
}