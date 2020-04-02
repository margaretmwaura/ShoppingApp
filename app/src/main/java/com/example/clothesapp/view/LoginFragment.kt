package com.example.clothesapp.view

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.clothesapp.R
import com.example.clothesapp.databinding.FragmentLoginBinding
import com.example.clothesapp.presenter.UserSessionManager
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.*
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.concurrent.TimeUnit


class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding
    private var mCallBacks: OnVerificationStateChangedCallbacks? = null
    private var resendToken: ForceResendingToken? = null
    private var auth: FirebaseAuth? = null
    private var enteredPhoneNumber: String? = " "
    private var mVerificationId: String? = null
    private var userId : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.lifecycleOwner = this

        enteredPhoneNumber = LoginFragmentArgs.fromBundle(arguments!!).phoneNumber

        auth = FirebaseAuth.getInstance();
        binding.buttonLogin.setOnClickListener {
            setUpTheCallBacks()
            verifyPhoneNumber()
            object : CountDownTimer(12000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    button_login.text = "seconds remaining: " + millisUntilFinished / 1000
                    //here you can have your logic to set text to edittext
                }

                override fun onFinish() {
                    binding.buttonLogin.visibility = View.VISIBLE
                    binding.buttonLogin.setOnClickListener(View.OnClickListener {
                        resendVerificationCode(enteredPhoneNumber!!, resendToken!!)
                        object : CountDownTimer(12000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {
                                binding.buttonLogin.text  = "seconds remaining: " + millisUntilFinished / 1000
                            }

                            override fun onFinish() {
                                binding.buttonLogin.text = "Resend code"
                            }
                        }.start()
                    })
                }
            }.start()

        }

        return binding.root
    }

    private fun resendVerificationCode(
        phoneNumber: String,
        token: ForceResendingToken
    ) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60,TimeUnit.SECONDS,activity!!,mCallBacks!!,token)
    }


    private fun setUpTheCallBacks() { //        This is the code that automatically sets the code to the edit text
        mCallBacks = object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode!!
                Log.e("VerificationCode",code)

                verifyVerificationCode(code)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(activity, "Verification failed " + e.message, Toast.LENGTH_LONG)
                    .show()
                Log.d("VerificationFailed", "This is the message " + e.message)
            }

            override fun onCodeSent(
                s: String,
                forceResendingToken: ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                mVerificationId = s
                resendToken = forceResendingToken
            }
        }
    }

    private fun verifyVerificationCode(code: String) { //creating the credential
        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, code)
        Log.e("Credential",credential.toString())
        //signing the user
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        auth?.signInWithCredential(credential)!!.addOnCompleteListener {

            if (it.isSuccessful) {
                val mine = UserSessionManager(activity!!)
                mine.createUserLoginSession()
                userId = auth?.currentUser!!.uid

                Toast.makeText(activity, "We did it ",Toast.LENGTH_SHORT).show()
            } else {
                if (it.exception is FirebaseAuthInvalidCredentialsException)
                {
                    Log.d("ErrorVerifying", "This is the error " + it.exception)
                    Toast.makeText(activity, "Damn man I failed  ",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun verifyPhoneNumber() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(enteredPhoneNumber!!, 120, TimeUnit.SECONDS, activity!!, mCallBacks!!
        )
    }


}