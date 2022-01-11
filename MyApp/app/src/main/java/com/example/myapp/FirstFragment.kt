package com.example.myapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapp.databinding.FragmentFirstBinding
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private var imgUrl = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.shuffleButton.setOnClickListener{
            val url = URL("https://api.thecatapi.com/v1/images/search")
            lifecycleScope.launchWhenCreated {
                withContext(Dispatchers.IO) {
                    var txt = url.readText()
                    txt = txt.subSequence(1, txt.lastIndex) as String
                    val json = JSONObject(txt)
                    imgUrl = json.getString("url")
                    lifecycleScope.launch {
                        Picasso.with(context)
                            .load(imgUrl)
                            .into(binding.imageView);
                    }
                }
            }


            //TODO : Method call should happen from the main thread; -> changer le lifecyclescope.
            //"""[{"breeds":[],"id":"3kg","url":"https://cdn2.thecatapi.com/images/3kg.jpg","width":500,"height":346}]"""

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}