package com.example.samurairoad.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.samurairoad.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()

    // retrofit api
    private var apiInterface: WorkoutApiService? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
//        initRetrofit()

        viewModel.token.observe(viewLifecycleOwner, Observer {
            findNavController().popBackStack()
            viewModel.isAuth.value = true
        })

        binding.btnLogIn.setOnClickListener{
            if (binding.emailEt.text.toString() != "" && binding.etPassword.text.toString() != ""){
                viewModel.createUser(apiInterface!!, RegisterUserModel("kminchelle", "0lelplR"))
//                viewModel.getAllUsers(apiInterface!!)
//                findNavController().popBackStack()
//                viewModel.isAuth.value = true
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

//    //TODO remove init
//    private fun initRetrofit(){
//        if(apiInterface == null){
//            val retrofit = Retrofit.Builder()
//                .baseUrl("https://dummyjson.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//            apiInterface = retrofit.create(WorkoutApi::class.java)
//        }
//    }
}