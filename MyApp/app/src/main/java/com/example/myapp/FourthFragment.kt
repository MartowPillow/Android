package com.example.myapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.myapp.databinding.FragmentFourthBinding

/**
 * View fullscreen picture of a favorite cat
 */
class FourthFragment : Fragment() {

    private var _binding: FragmentFourthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFourthBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get image Url from navigation
        val imgUrl: String = arguments?.getString("imgUrl").toString()
        if(imgUrl != null && imgUrl != "") {
            binding.pictureView.load(imgUrl) //Display image on screen
        }

        //Go back to recyler view page
        binding.backButton.setOnClickListener{
            findNavController().navigate(R.id.action_FourthFragment_to_SecondFragment)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}