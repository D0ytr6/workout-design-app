package com.example.samurairoad.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.samurairoad.adapters.ParentWorkoutAdapter
import com.example.samurairoad.databinding.FragmentLoginBinding
import com.example.samurairoad.databinding.FragmentWorkoutsListBinding
import com.example.samurairoad.ui.activities.WorkoutsListViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.btnLogIn.setOnClickListener{
            if (binding.emailEt.text.toString() != "" && binding.etPassword.text.toString() != ""){
                findNavController().popBackStack()
                viewModel.isAuth.value = true
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}