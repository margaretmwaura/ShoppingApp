package com.example.clothesapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.clothesapp.R
import kotlinx.android.synthetic.main.activity_first_navigation.*
import kotlin.math.abs

class FirstNavigation : AppCompatActivity(), GestureDetector.OnGestureListener {

    lateinit var gestureDetector: GestureDetector
    lateinit var  navController : NavController
    var x2:Float = 0.0F
    var x1:Float = 0.0F
    var y1:Float = 0.0F
    var y2:Float = 0.0f

    companion object{
        const val MIN_DISTANCE = 150
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_navigation)

        gestureDetector = GestureDetector(this,this)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)

        when(event?.action)
        {
            0->{
                x1 = event.x
                y1 = event.y
            }

            1->{
                x2 = event.x
                y2 = event.y

                var valueX:Float = (x2 - x1)
                var valueY:Float = (y2 - y1)

                if(abs(valueX) > MIN_DISTANCE)
                {
                    val currentfraagment= NavHostFragment.findNavController(nav_host_fragment).currentDestination!!.id
                    if(currentfraagment == R.id.descriptionFragment)
                    {
                       navController.navigate(R.id.action_descriptionFragment_to_introFragment)
                    }
                    if(currentfraagment == R.id.introFragment)
                    {
                        navController.navigate(R.id.action_introFragment_to_signupFragment)
                    }
                    if(currentfraagment == R.id.signupFragment)
                    {
                        navController.navigate(R.id.action_signupFragment_to_loginFragment)
                    }

                }

            }
        }
        return super.onTouchEvent(event)
    }

    override fun onShowPress(e: MotionEvent?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return false;
    }

    override fun onDown(e: MotionEvent?): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return false;
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}