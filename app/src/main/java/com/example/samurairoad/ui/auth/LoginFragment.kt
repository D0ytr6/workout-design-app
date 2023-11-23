package com.example.samurairoad.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.samurairoad.databinding.FragmentLoginBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()

    // retrofit api
    private var apiInterface: WorkoutApi? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        initRetrofit()

        binding.btnLogIn.setOnClickListener{
            if (binding.emailEt.text.toString() != "" && binding.etPassword.text.toString() != ""){
                viewModel.createUser(apiInterface!!, RegisterUserModel("test@gmail.com", "kavelduk32"))
                viewModel.getAllUsers(apiInterface!!)
//                findNavController().popBackStack()
//                viewModel.isAuth.value = true
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    //TODO remove init
    private fun initRetrofit(){
        if(apiInterface == null){
            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            apiInterface = retrofit.create(WorkoutApi::class.java)
        }
    }
}