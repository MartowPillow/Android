package com.example.myapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapp.databinding.FragmentFirstBinding
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import coil.load
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import org.json.JSONObject



/**
 * Display random cats
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private var imgUrl = ""


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        //first time
        val sharedPref = context?.getSharedPreferences("global", Context.MODE_PRIVATE)
        if(!sharedPref?.all.toString().contains("json")){
            val edit = sharedPref?.edit()
            edit?.putString("json", "[]")
            edit?.apply()
        }

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Go to 'favorites' page
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        //Show a random cat
        binding.shuffleButton.setOnClickListener{
            val url = URL("https://api.thecatapi.com/v1/images/search")
            lifecycleScope.launchWhenCreated {
                withContext(Dispatchers.IO) {
                    var txt = url.readText() //get json string from api
                    txt = txt.subSequence(1, txt.lastIndex) as String
                    val json = JSONObject(txt)
                    imgUrl = json.getString("url") //retrieve cat image url
                    lifecycleScope.launch {
                        binding.imageView.load(imgUrl) //display image on ImageView
                    }
                }
            }
        }

        //Go to 'add to favorites' page, with the image Url
        binding.addButton.setOnClickListener{
            val bundle = bundleOf("imgUrl" to imgUrl)
            findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}