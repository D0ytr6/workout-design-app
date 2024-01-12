package com.example.samurairoad.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.samurairoad.R
import com.example.samurairoad.databinding.FragmentLoginBinding
import com.example.samurairoad.ui.auth.models.Auth
import com.example.samurairoad.utils.ApiResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

//    TODO add logout button

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
//        initRetrofit()

//        viewModel.token.observe(viewLifecycleOwner, Observer {
//            findNavController().popBackStack()
//            viewModel.isAuth.value = true
//        })

//        binding.btnLogIn.setOnClickListener{
//            if (binding.emailEt.text.toString() != "" && binding.etPassword.text.toString() != ""){
//                viewModel.createUser(apiInterface!!, RegisterUserModel("kminchelle", "0lelplR"))
////                viewModel.getAllUsers(apiInterface!!)
////                findNavController().popBackStack()
////                viewModel.isAuth.value = true
//            }
//        }

////        TODO hide load token by splash screen
//        tokenViewModel.tokenLiveData.observe(viewLifecycleOwner) { token ->
//            if (token != null)
//                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//        }

        binding.btnLogIn.setOnClickListener{
            authViewModel.login(
                Auth("test12", "test12"),
                object: CoroutinesErrorHandler {
                    override fun onError(message: String) {
                        binding.topTv.text = "Error! $message"
                    }
                }
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
        authViewModel.loginResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> binding.topTv.text = it.errorMessage
                is ApiResponse.Loading -> binding.topTv.text = "Loading"
                is ApiResponse.Success -> {
                    tokenViewModel.saveToken(it.data.refreshToken)
                }
            }
        }
    }

}