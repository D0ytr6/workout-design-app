package com.example.samurairoad.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.samurairoad.databinding.FragmentLogin2Binding
import com.example.samurairoad.databinding.FragmentLoginBinding
import com.example.samurairoad.ui.auth.models.Auth
import com.example.samurairoad.utils.ApiResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

//    TODO add logout button

    private var _binding: FragmentLogin2Binding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogin2Binding.inflate(inflater, container, false)
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

//        TODO add validation
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

        binding.btnOffline.setOnClickListener{
            tokenViewModel.setOfflineMode()
//            TODO make constant
            tokenViewModel.saveRefreshToken("TestTokenOffline")
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
                    binding.topTv.text = "Success"
                    tokenViewModel.saveRefreshToken(it.data.refreshToken)
                }
            }
        }

//        tokenViewModel.isOffline.observe(viewLifecycleOwner){
//            if (it == true){
//                tokenViewModel.saveRefreshToken("TestTokenOffline")
//            }
//        }
    }

}